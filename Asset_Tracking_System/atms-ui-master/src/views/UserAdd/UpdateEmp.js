import React, { Component } from 'react'
import './UserAdd.css'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import { putEmp } from "../../Service/putEmp"
import { getSingleEmp } from "../../Service/getSingleEmp"
import { getAdminWiseUserList } from '../../Service/getAdminWiseUserList'
import { getAssetCategory } from '../../Service/getAssetCategory'
import Select from "react-select";
import swal from 'sweetalert';
import { Link, NavLink } from 'react-router-dom';
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
export default class UpdateEmp extends Component {
    state = {
        result: [],
        id: 0,
        loader: false,
        Role: "",
        allCategory: [],
        category: "",
        allUser: [
            {
                label: "Emp User",
                value: "empuser"
            },

        ],
        optionAllUser: "",
        selectedOrg: ""
    }

    componentDidMount = async () => {
        try {
            this.setState({ loader: true, Role: localStorage.getItem('role'),
            username: localStorage.getItem("username"),
         })
            if (this.props.location && this.props.location.state && this.props.location.state.empId) {
                const propId = this.props.location && this.props.location.state && this.props.location.state.empId;

                let result = await getSingleEmp(propId)
                console.log('result', result)
                this.setState({ result: result && result.data, id: propId, loader: false, category: result.data.category, selectedAdmin: result.data.admin})
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

                this.setState({ allCategory: tempCategoryArray });

            } else if (this.props.match && this.props.match.params && this.props.match.params.id) {
                    const propParamId = this.props.match && this.props.match.params && this.props.match.params.id;
                    let result = await getSingleEmp(propParamId)
                    this.setState({ result: result && result.data, id: propParamId, loader: false, selectedAdmin: result.data.admin })
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

                } else {
                    this.props.history.push("/emp-users");
                }

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
    changeCategory = (selectedCategory) => {
        this.setState({ selectedCategory: selectedCategory.value }, () => {
            console.log('selectedCategory', this.state.selectedCategory)
            //console.log('selectedCategoryId', this.state.selectedCategoryId)
        })
    }
    changeAdminName = (selectAdmin) => {

        this.setState({ selectedAdmin: selectAdmin.value }, () => {
            console.log('selectedAdmin', this.state.selectedAdmin)
        })
    }
    changeOrgName = async (selectOrg) => {
        console.log('Selected Organization Name--->', selectOrg.value)
        this.setState({ selectedOrg: selectOrg.value }, () => {
            console.log('selected Organization', this.state.selectedOrg)
        })
    }
    render() {
        const { loader } = this.state
        return (
            <>
                {loader ? <div class="loader"></div> :
                    <div className="userAdd__wrapper">
                        <h3>Update Emp User Information</h3>
                        <Formik
                            enableReinitialize={true}
                            initialValues={{
                                firstName: this.state && this.state.result && this.state.result.firstName,
                                lastName: this.state && this.state.result && this.state.result.lastName,
                                email: this.state && this.state.result && this.state.result.emailId,
                                phone: this.state && this.state.result && this.state.result.phoneNumber,
                                userName: this.state && this.state.result && this.state.result.username,
                                password: this.state && this.state.result && this.state.result.passWord,
                                type_of: this.state && this.state.result && this.state.result.role,
                                companyname: this.state && this.state.result && this.state.result.companyName,
                                address: this.state && this.state.result && this.state.result.address,
                                category: this.state && this.state.result && this.state.result.category,
                                admin: this.state && this.state.result && this.state.result.admin
                            }}
                            validationSchema={SignupSchema}
                            onSubmit={async values => {
                                const data = {
                                    pkuserId: this.state.id,
                                    phoneNumber: values.phone,
                                    emailId: values.email,
                                    firstName: values.firstName,
                                    lastName: values.lastName,
                                    password: values.password,
                                    username: values.userName,
                                    address: values.address,
                                    companyName: values.companyname,
                                    role: values.type_of,
                                    fkuserId: localStorage.getItem("fkUserId"),
                                    category: this.state.category,
                                    createdby: this.state.username,
                                    status: this.state && this.state.result && this.state.result.status,
                                    admin: this.state && this.state.result && this.state.result.admin
                                }
                                console.log('data', data);
                                let result = await putEmp(data);
                                console.log('result', result);
                                if (result.status === 200) {
                                    swal("Great", "Employee Update successfully", "success");
                                    this.props.history.push("/emp-users")
                                }
                                else {
                                    swal("Failed", "Something went wrong please check your internet", "error");
                                }

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
                                            <label className='label'>Username</label>
                                            <Field className="form-control" name="userName" type="text" placeholder="Username" />
                                            {errors.userName && touched.userName ? <div className="error__msg">{errors.userName}</div> : null}
                                        </div>
                                        <div>
                                            <label className='label'>Password</label>
                                            <Field className="form-control" name="password" type="password" placeholder="Password" />
                                            {errors.password && touched.password ? <div className="error__msg">{errors.password}</div> : null}
                                        </div>
                                        <div>
                                            <label className='label'>Email Id</label>
                                            <Field className="form-control" name="email" type="email" placeholder="Email_Id" />
                                            {errors.email && touched.email ? <div className="error__msg">{errors.email}</div> : null}
                                        </div>
                                        <div>
                                            <label className='label'>Address</label>
                                            <Field className="form-control" name="address" placeholder="Enter Address" />
                                            {errors.address && touched.address ? <div className="error__msg">{errors.address}</div> : null}
                                        </div>
                                        <div>
                                            <label className='label'>Select Category Name</label>
                                            <Select
                                                value={
                                                    this.state.allCategory
                                                        ? this.state.allCategory.find(
                                                            (option) => option.value === this.state.category
                                                        )
                                                        : ""
                                                }
                                                onChange={(e) => {
                                                    setFieldValue("category", e.value);
                                                    this.setState({ category: e.value }, () => {
                                                        console.log("selectedCat", this.state.category)
                                                    });
                                                }}
                                                options={this.state.allCategory}
                                            />
                                        </div>
                                        <div>
                                            <label className='label'>User Type</label>
                                            {/*  <Field as="select" className="form-control" name="type_of" >
                                  
                                        <option value="empuser">Employee User</option>
                                    </Field> */}
                                            <Select options={this.state.allUser} value={
                                                this.state.allUser
                                                    ? this.state.allUser.find(
                                                        (option) => option.value === this.state.optionAllUser
                                                    )
                                                    : ""
                                            } onChange={(e) => {
                                                setFieldValue(
                                                    "type_of",
                                                    e.value
                                                );
                                                this.setState({ optionAllUser: e.value });
                                            }} placeholder="Select User Type" />
                                            {errors.type_of && touched.type_of ? <div className="error__msg">{errors.type_of}</div> : null}
                                        </div>
                                        {this.state.Role === "admin" ? <>
                                            {this.state.optionAllUser === "empuser" ?
                                                <>
                                                    <div style={{ display: 'none' }}>
                                                        <label className='label' >Select Admin Name</label>
                                                        <Select options={this.state.allAdminList} onChange={this.changeAdminName} placeholder="Select Admin" />
                                                    </div>
                                                </>

                                                : ""}
                                        </> : <>
                                            {this.state.optionAllUser === "empuser" ?
                                                <>
                                                    <div>
                                                        <label className='label' >Select Admin Name</label>
                                                        <Select options={this.state.allAdminList} value={
                                                            this.state.allAdminList
                                                                ? this.state.allAdminList.find(
                                                                    (option) => option.value === this.state.selectedAdmin
                                                                )
                                                                : ""
                                                        } onChange={(e) => {
                                                            setFieldValue(
                                                                "admin",
                                                                e.value
                                                            );
                                                            this.setState({ selectedAdmin: e.value });
                                                        }} placeholder="Select Admin" />
                                                    </div>
                                                </>

                                                : ""}
                                        </>}
                                    </div>
                                    <br />
                                    <div className='btn-user' align="center">
                                        <button type="submit" className="btn btn-primary">Submit</button>
                                        <button type="cancel" className="btn btn-primary"> <Link to={{ pathname: "/users" }}>Cancel</Link></button>
                                    </div>
                                </Form>
                            )}
                        </Formik>
                    </div>
                }
            </>
        )
    }
}
