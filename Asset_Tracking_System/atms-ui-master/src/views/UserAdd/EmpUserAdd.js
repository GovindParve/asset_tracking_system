import React, { Component } from 'react'
import './UserAdd.css'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import { postEmpUser } from '../../Service/postEmpUser'
import { getUser } from "../../Service/getUser";
import { getAdminWiseUserList } from '../../Service/getAdminWiseUserList'
import { getAssetCategory } from '../../Service/getAssetCategory'
import Select from "react-select";
import swal from 'sweetalert';

const SignupSchema = Yup.object().shape({
    firstName: Yup.string()
        .min(2, 'Too Short!')
        .max(50, 'Too Long!')
        .required('Enter First Name'),
    lastName: Yup.string()
        .min(2, 'Too Short!')
        .max(50, 'Too Long!')
        .required('Enter Last Name'),
    phone: Yup.string()
        .min(10, 'Too Short!')
        .max(10, 'Too Long!')
        .required('Enter Phone No'),
    email: Yup.string().email('Invalid Email_Id').required('Enter Email_id'),
    password: Yup.string().required('Enter Password'),
    address: Yup.string().required('Enter Address'),
    // type_of: Yup.string().required('Select Role'),
    userName: Yup.string().required('Enter User Name'),
    companyname: Yup.string().required('Enter Company Name'),
});

export default class EmpUserAdd extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isRevealPwd: false,
            Role: "",
            username: "",
            selectedCategoryId: [],
            selectedCategory: "",
            allCategory: [],
            allAdminList: [],
            optionUser: "",
            selectedAdmin: "",
            selectedAdminId: "",
            status: "Active",
            allUser: [
                {
                    label: "Emp User",
                    value: "empuser"
                },

            ],
            optionAllUser: "",
            selectedOrg: "",
            disabled: false
        }
        this.toggleShow = this.toggleShow.bind(this);
    };
    async componentDidMount() {
        try {
            this.setState({
                loader: true,
                Role: localStorage.getItem('role'),
                username: localStorage.getItem("username")
            })
            let resultcategory = await getAssetCategory();
            console.log('resultcategory', resultcategory);
            let tempCategoryArray = []
            resultcategory && resultcategory.data && resultcategory.data.map((obj) => {
                console.log('objresultcategory', obj);
                let tempCategoryObj = {
                    id: `${obj.pkCatId}`,
                    value: `${obj.categoryName}`,
                    label: `${obj.categoryName}`
                }
                console.log('tempCategoryObj', tempCategoryObj);

                tempCategoryArray.push(tempCategoryObj);
            })

            this.setState({ allCategory: tempCategoryArray, });

            let resultAdmin = await getAdminWiseUserList();
            console.log('Admin result', resultAdmin);
            let tempAdminArray = []
            if (resultAdmin && resultAdmin.data && resultAdmin.data.length != 0) {
                resultAdmin.data.map((obj) => {
                    let tempAdminObj = {
                        id: `${obj.pkuserId}`,
                        value: `${obj}`,
                        label: `${obj}`
                    }
                    tempAdminArray.push(tempAdminObj)
                })
            }

            this.setState({ allAdminList: tempAdminArray, }, () => {
                console.log('allAdmin List', this.state.allAdminList)
            })

        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }
    }
    async validateUserName(value) {
        let errors;
        let resultUser = await getUser();
        if (resultUser && resultUser.data && resultUser.data.length !== 0) {
            let tempUser = resultUser.data.filter((obj) => {
                return obj.username === value;
            });
            if (tempUser.length !== 0) {
                errors = "This user name is already exist";
            }
        }
        return errors;
    }

    async validateEmail(value) {
        let error;
        let resultUser = await getUser();
        if (resultUser && resultUser.data && resultUser.data.length !== 0) {
            let tempUser = resultUser.data.filter((obj) => {
                return obj.emailId === value;
            });
            if (tempUser.length !== 0) {
                error = "This Email Id is already exist";
            }
        }
        return error;
    }
    changeCategory = (selectedCategory) => {

        this.setState({ selectedCategory: selectedCategory.value, selectedCategoryId: selectedCategory.id }, () => {
            console.log('pkCategoryid', this.state.selectedCategory)
            console.log('selectedCategoryId', this.state.selectedCategoryId)
        })
    }
    //   changeAdminName = (selectAdmin) => {

    //     this.setState({ selectedAdmin: selectAdmin.value }, () => {
    //         console.log('selectedAdmin', this.state.selectedAdmin)
    //     })
    // }
    changeAdminName = (selectedAdmin) => {
        console.log('selectedAdmin', selectedAdmin)
        this.setState({ selectedAdmin: selectedAdmin.value, selectedAdminId: selectedAdmin.id }, () => {
            console.log('admin', this.state.selectedAdmin);
            console.log('adminid', this.state.selectedAdminId);
        })
    }
    changeOrgName = async (selectOrg) => {
        console.log('Selected Organization Name--->', selectOrg.value)
        this.setState({ selectedOrg: selectOrg.value }, () => {
            console.log('selected Organization', this.state.selectedOrg)
        })
    }
    toggleShow() {
        this.setState({ isRevealPwd: !this.state.isRevealPwd });
    }
    render() {
        let show = (<i className="icon-eye"></i>);
        let hide = (<i className="fa fa-eye-slash"></i>);
        return (
            <div className="userAdd__wrapper">
                <h3>Employee User Information</h3>
                <br />
                <Formik
                    initialValues={{
                        firstName: '',
                        lastName: '',
                        email: '',
                        phone: '',
                        password: '',
                        userName: '',
                        address: '',
                        companyname: '',
                        type_of: '',
                    }}

                    validationSchema={SignupSchema}
                    onSubmit={async values => {
                        this.setState({ disabled: true })
                        const data = {
                            phoneNumber: values.phone,
                            emailId: values.email,
                            firstName: values.firstName,
                            lastName: values.lastName,
                            password: values.password,
                            username: values.userName,
                            address: values.address,
                            companyName: values.companyname,
                            role: values.type_of,
                            fkuserId: "",
                            category: this.state.selectedCategory,
                            status: this.state.status,
                            createdby: this.state.username,
                            admin: this.state.Role === "admin" ? this.state.username : this.state.selectedAdmin,
                        }

                        console.log('data', data);
                        let result = await postEmpUser(data);
                        console.log("Add User",result)
                        if (result.data === 'User Created ') {
                            swal("Great", "User Created Successfully", "success");
                            this.props.history.push("/emp-users")
                            //window.location.reload()
                        }
                        else if (result.data === ' User Alrady Exits ') {
                            swal("Opps!", "Data already present", "warning")
                        }
                        else {
                            swal("Failed", "Something went wrong please check your internet", "error");
                        }
                        // return await postEmpUser(data)
                        //     .then(result => {
                        //         console.log("Add User", result);
                        //         swal("Great", "Data added successfully", "success");
                        //         window.location.reload()
                        //         return Promise.resolve();
                        //     })
                        //     .catch(() => {
                        //         swal("Failed", "Something went wrong please check your internet", "error");
                        //     });
                    }}
                >
                    {({ errors, touched, setFieldValue }) => (
                        <Form>
                            <div className="user__form__wrapper">
                                <div>
                                    <label className='label'>First Name</label>
                                    <Field className="form-control" name="firstName" placeholder="First Name" />
                                    {errors.firstName && touched.firstName ? (
                                        <div className="error__msg">{errors.firstName}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label className='label'>Last Name</label>
                                    <Field className="form-control" name="lastName" placeholder="Last Name" />
                                    {errors.lastName && touched.lastName ? (
                                        <div className="error__msg">{errors.lastName}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label className='label'>Company Name</label>
                                    <Field className="form-control" name="companyname" placeholder="Enter Company Name" />
                                    {errors.companyname && touched.companyname ? <div className="error__msg">{errors.companyname}</div> : null}
                                </div>
                                <div>
                                    <label className='label'>Phone No</label>
                                    <Field className="form-control" name="phone" placeholder="Phone" maxlength="10" />
                                    {errors.phone && touched.phone ? <div className="error__msg">{errors.phone}</div> : null}
                                </div>
                                <div>
                                    <label className='label'>User Name</label>
                                    <Field className="form-control" name="userName" placeholder="Username" 
                                    //validate={this.validateUserName} 
                                    />
                                    {errors.userName && touched.userName ? <div className="error__msg">{errors.userName}</div> : null}
                                </div>
                                <div>
                                    <label className='label'>Password</label>
                                    <Field className="form-control" name="password" type={this.state.isRevealPwd ? "text" : "password"} placeholder="Password" />
                                    {/* <span className='show-icon' onClick={this.toggleShow}>{this.state.isRevealPwd ? hide:show}</span> */}
                                    {errors.password && touched.password ? <div className="error__msg">{errors.password}</div> : null}

                                </div>
                                <div>
                                    <label className='label'>Email Id</label>
                                    <Field className="form-control" name="email" type="email" placeholder="Email_Id" 
                                    //validate={this.validateEmail} 
                                    />
                                    {errors.email && touched.email ? <div className="error__msg">{errors.email}</div> : null}
                                </div>
                                <div>
                                    <label className='label'>Address</label>
                                    <Field className="form-control" name="address" placeholder="Enter Address" />
                                    {errors.address && touched.address ? <div className="error__msg">{errors.address}</div> : null}
                                </div>
                                <div>
                                    <label className='label'>Select Category Name</label>
                                    <Select options={this.state.allCategory} onChange={this.changeCategory} placeholder="Select Category Name" />

                                </div>
                                {/* <div>
                                    <label className='label'>User Type</label>
                                    <Field className="form-control" name="type_of" value={"Emp User"} />
                                    {errors.type_of && touched.type_of ? <div className="error__msg">{errors.type_of}</div> : null}
                                </div>
                                <div>
                                    <div>
                                        <label className='label' >Select Admin Name</label>
                                        <Select options={this.state.allAdminList} onChange={this.changeAdminName} placeholder="Select Admin" />
                                    </div>
                                </div> */}
                                <div>
                                    <label className='label'>User Type</label> 
                                    <Select options={this.state.allUser} value={
                                        this.state.allUser
                                            ? this.state.allUser.find(
                                                (option) => option.value === this.state.optionAllUser === "empuser"
                                            )
                                            : ""
                                    } onChange={(e) => {
                                        setFieldValue(
                                            "type_of",
                                            e.value
                                        );
                                        this.setState({ optionAllUser: e.value });
                                    }} 
                                    placeholder="Select User Type" />
                                    {errors.type_of && touched.type_of ? <div className="error__msg">{errors.type_of}</div> : null}
                                </div>
                                {this.state.Role === "organization" ? <>
                                    {this.state.optionAllUser === "empuser" ?
                                        <>
                                            <div>
                                                <label className='label' >Select Admin Name</label>
                                                <Select options={this.state.allAdminList} onChange={this.changeAdminName} placeholder="Select Admin" />
                                            </div>
                                        </>
                                        : ""}
                                </> : <>
                                    {this.state.optionAllUser === "empuser" ?
                                        <>
                                            <div style={{ display: 'none' }}>
                                                <label className='label' >Select Admin Name</label>
                                                <Select options={this.state.allAdminList} onChange={this.changeAdminName} placeholder="Select Admin" />
                                            </div>
                                        </>
                                        : ""}
                                </>}
                            </div>
                            <br />
                            <div className='btn-user' align="center">
                                <button type="submit" className="btn btn-primary" disabled={this.state.disabled}>SUBMIT</button>
                            </div>
                            <br />
                            <br />
                        </Form>
                    )}
                </Formik>
            </div>
        )
    }
}
