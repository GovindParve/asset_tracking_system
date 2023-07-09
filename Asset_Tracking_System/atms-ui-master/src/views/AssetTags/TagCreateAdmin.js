import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import moment from "moment";
import './AssetTags.css'
import { getDeviceUser } from '../../Service/getDeviceUser'
import { getAssetCategory } from '../../Service/getAssetCategory'
import { postTag } from '../../Service/postTag'
import { getAdmin } from '../../Service/getAdmin'
import { getOrgnization } from '../../Service/getOrgnization'
import Select from "react-select";
import { getTagValidation } from '../../Service/getTagValidation'
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';
import "moment-timezone";

import { postDevice } from '../../Service/postDevice'

const SignupSchema = Yup.object().shape({
    assetTag: Yup.string()
        .required('Enter AssetTag Name'),
    barcode: Yup.string()
        .required('Enter Barcode No'),
    assetUniqueCode: Yup.string()
        .min(17, 'It should have maximum 12 characters!')
        .max(17, 'It should have maximum 12 characters!')
        .required('Enter Asset Unique Code'),
});

const timezones = timezoneNames()
    .filter((l) => l.indexOf("/") > -1)
    .sort((a, b) => a.localeCompare(b));
export default class TagCreateAdmin extends Component {

    // interval;
    state = {
        timezone: "Asia/Kolkata",
        date: null,
        result: null,
        allData: [],
        allAdminData: [],
        allOrgnizationData: [],
        selectedUser: "",
        selectedUserId: "",
        allCategory: [],
        selectedCategory: "",
        selectedCategoryId: "",
        selectedAdmin: "",
        selectedAdminId: "",
        selectedOrgId: "",
        selectedOrg: "",
        Role: "",
        status: "",
        category: "",
        isMacId: false,
        createdBy: localStorage.getItem("username") + ' / ' + localStorage.getItem('role'),
        userType: [
            {
                label: "User",
                value: "user"
            },
            {
                label: "None",
                value: "none"
            }
        ],
        optionUser: "",
    }

    async componentDidMount() {
        let role = localStorage.getItem("role");
        this.setState({ Role: role, category: localStorage.getItem("categoryname") });
        let result = await getDeviceUser();
        console.log('UserResult', result);

        let tempArray = []
        result && result.data && result.data.map((obj) => {
            console.log('objresult', obj);
            let tempObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`
            }
            console.log('tempObj', tempObj);
            tempArray.push(tempObj);
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
        this.setState({ allData: tempArray, allCategory: tempCategoryArray, });

        let resultAdmin = await getAdmin();
        console.log('admin', resultAdmin)
        let tempAdminArray = []
        resultAdmin && resultAdmin.data && resultAdmin.data.map((obj) => {
            let tempadminObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`
            }
            tempAdminArray.push(tempadminObj)
        })
        this.setState({ allAdminData: tempAdminArray }, () => {
            console.log('Alladmin', this.state.allAdminData)
        })

        let resultOrg = await getOrgnization();
        console.log('Orgnization', resultOrg)
        let tempOrgArray = []
        resultOrg && resultOrg.data && resultOrg.data.map((obj) => {
            let tempOrgObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`
            }
            tempOrgArray.push(tempOrgObj)
        })

        this.setState({ allOrgnizationData: tempOrgArray }, () => {
            console.log('AllOrgnization', this.state.allOrgnizationData)
        })
    }
    async validateAssetTag(value) {
        let errors;
        let resultTag = await getTagValidation();
        console.log("Tag Val List", resultTag);
        if (resultTag && resultTag.data && resultTag.data.length !== 0) {
            let tempUser = resultTag.data.filter((obj) => {
                return obj.assetTagName === value;
            });
            if (tempUser.length !== 0) {
                errors = "This Tag name is already exist";
            }
        }
        return errors;
    }
    handleTimezoneChange = (event) => {
        this.setState({ timezone: event.target.value });
    };

    changeUser = (selectedUser) => {
        this.setState({ selectedUser: selectedUser.value, selectedUserId: selectedUser.id }, () => {
            console.log('Users', this.state.selectedUser)
            console.log('selectedUserId', this.state.selectedUserId)
        })
    }

    changeCategory = (selectedCategory) => {
        this.setState({ selectedCategory: selectedCategory.value, selectedCategoryId: selectedCategory.id }, () => {
            console.log('pkCategoryid', this.state.selectedCategory)
            console.log('selectedCategoryId', this.state.selectedCategoryId)
        })
    }
    changeAdmin = (selectedAdmin) => {
        console.log('selectedAdmin', selectedAdmin)
        this.setState({ selectedAdmin: selectedAdmin.value, selectedAdminId: selectedAdmin.id }, () => {
            console.log('admin', this.state.selectedAdmin);
            console.log('adminid', this.state.selectedAdminId);
        })
    }
    changeOrgnization = (selectedOrg) => {
        console.log('selectedOrgnization', selectedOrg)
        this.setState({ selectedOrg: selectedOrg.value }, () => {
            console.log('Orgnization', this.state.selectedOrg);
            // console.log('Orgnizationid', this.state.selectedOrgId);
        })
    }
    toggleShow = async () => {
        this.setState({ isMacId: !this.state.isMacId });
      }
    render() {

        const { result, date } = this.state;
        return (
            <div className="userAdd__wrapper">
                <h3>Create Asset Tag</h3>
                <br />
                <Formik
                    initialValues={{
                        assetTag: '',
                        assetUniqueCode: '',
                        sim: '',
                        imsi: '',
                        imei: '',
                        AssetTagType: '',
                        barcode: '',
                        wakeupTime: '',
                        timezone: '',
                        datetime: '',
                        user: '',
                        admin: '',
                        organization: '',
                        location: '',
                        //status:'',
                        // adminName: '',
                        // userName: ''
                    }}
                    validationSchema={SignupSchema}
                    onSubmit={async values => {
                        const resultDate = moment()
                            .tz(this.state.timezone)
                            .format()
                        let fkUserId = await localStorage.getItem("fkUserId")
                        let admin = await localStorage.getItem("username")
                        const payload = {
                            assetTagName: values.assetTag,
                            user: values.user,
                            admin: admin,
                            fkUserId: this.state.selectedUserId,
                            fkAdminId: fkUserId,
                            // fkAdminId: this.state.selectedAdminId,
                            organization: this.state.selectedOrg,
                            //fkOrganizationId:fkUserId,
                            fkOrganizationId: this.state.selectedOrgId,
                            wakeupTime: moment().tz(values.wakeupTime).format("YYYY-MM-DD HH:mm:ss"),
                            timeZone: this.state.timezone,
                            datetime: resultDate,
                            assetUniqueCodeMacId: values.assetUniqueCode,
                            assetSimNumber: values.sim,
                            assetIMSINumber: values.imsi,
                            assetIMEINumber: values.imei,
                            assetBarcodeSerialNumber: values.barcode,
                            assetLocation: values.location,
                            assetTagCategory: this.state.selectedCategory,
                            status: this.state.status,
                            createdBy: this.state.createdBy
                        }
                        console.log('Add Tag', payload);
                        let result = await postTag(payload)
                        console.log('Post Tag', result);
                        if (result.data === 'Asset Tag generated successfully...!' ) {
                            swal("Great", "Tag Created Successfully", "success");
                            if (this.state.category === "BLE") {
                                this.props.history.push("/asset-tag");
                              } else if (this.state.category === "GPS") {
                                this.props.history.push("/asset-gps");
                              }
                            //window.location.reload();
                          } else if (result.data === "Dublicate!")
                          {
                            swal("Opps!", "Data already present", "warning")
                            //window.location.reload();
                          }
                          else {
                            swal(
                              "Failed",
                              "Something went wrong please check your internet",
                              "error"
                            );
                         }
                    }}
                >
                    {({ errors, touched, setFieldValue }) => (
                        <Form>
                            <div className="assetTag_form">
                                <div>
                                    <label className='label'>Asset Tag Name</label>
                                    <Field className="form-control" name="assetTag" placeholder="Enter Asset Tag Name" validate={this.validateAssetTag} />
                                    {errors.assetTag && touched.assetTag ? (
                                        <div className="error__msg">{errors.assetTag}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label className='label'>Select Tag Category Name</label>

                                    <Select options={this.state.allCategory} onChange={this.changeCategory} placeholder="Select Asset Tag Category Name" />
                                    {/* {errors.AssetTagType && touched.AssetTagType ? (
                                        <div className="error__msg">{errors.AssetTagType}</div>
                                    ) : null} */}
                                </div>
                                <div>
                                    <label>Wakeup Time</label>
                                    <Field className="form-control" type="time" step='1' name="wakeupTime" />
                                </div>

                                <div>
                                    <label>Timezone</label>
                                    <select
                                        className="form-control"
                                        onChange={this.handleTimezoneChange}
                                        value={this.state.timezone}>
                                        {timezones.map((zone, i) => (
                                            <option key={i}>{zone}</option>
                                        ))}
                                    </select>
                                    {moment()
                                        .tz(this.state.timezone)
                                        .format()}
                                </div>
                                {(this.state.selectedCategory === 'BLE') ? <>
                                    <div>
                                        <label className='label'>Barcode No./ Serial No</label>
                                        <Field className="form-control" name="barcode" placeholder="Enter Barcode No./ Serial No." 
                                       // validate={this.validateBarcode} 
                                        />
                                        {errors.barcode && touched.barcode ? (
                                            <div className="error__msg">{errors.barcode}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label className="label">Asset Unique Code/Mac_id</label>
                                        <Field
                                            className="form-control"
                                            name="assetUniqueCode"
                                            placeholder="Enter Asset Unique Code/Mac_id"
                                            //validate={this.validateMac}
                                        />

                                        {errors.assetUniqueCode && touched.assetUniqueCode ? (
                                            <div className="error__msg">{errors.assetUniqueCode}</div>
                                        ) : null}

                                    </div>
                                    <div>
                                        <label>Created By</label>
                                        <Field className="form-control" name="createdBy" value={localStorage.getItem("username") + '/' + localStorage.getItem('role')} readOnly="true" />
                                    </div>
                                    <div>
                                        <label>User Type</label>
                                        <Select options={this.state.userType} value={
                                            this.state.userType
                                                ? this.state.userType.find(
                                                    (option) => option.value === this.state.optionUser
                                                )
                                                : ""
                                        } onChange={(e) => {
                                            setFieldValue(
                                                "type_of",
                                                e.value
                                            );
                                            this.setState({ optionUser: e.value });
                                        }} placeholder="Select User Type" />
                                    </div>
                                    {(this.state.optionUser === "user") ?
                                        <div>
                                            <label>Select User</label>
                                            <Select options={this.state.allData} placeholder="Select User" onChange={(value) => {
                                                setFieldValue('user', value.value);
                                                this.setState({ selectedUserId: value.id })
                                            }} />
                                            {/* {errors.user && touched.user ? (
                                            <div className="error__msg">{errors.user}</div>
                                        ) : null} */}
                                        </div>
                                        : ""
                                    }

                                </> : (this.state.selectedCategory === 'GPS') ? <>
                                    <div>
                                        <label className='label'>Barcode No./ Serial No</label>
                                        <Field className="form-control" name="barcode" placeholder="Enter Barcode No./ Serial No." 
                                        //validate={this.validateBarcode} 
                                        />
                                        {errors.barcode && touched.barcode ? (
                                            <div className="error__msg">{errors.barcode}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label className="label">Asset Unique Code/Mac_id</label>
                                        <Field
                                            className="form-control"
                                            name="assetUniqueCode"
                                            placeholder="Enter Asset Unique Code/Mac_id"
                                            //validate={this.validateMac}
                                            onClick={this.toggleShow} />
                                        <span className="macHide" id="displaytable6" >
                                            {this.state.isMacId ? 'If you dont have MACID, you can use IMEI No' : ''}
                                        </span>
                                        {errors.assetUniqueCode && touched.assetUniqueCode ? (
                                            <div className="error__msg">{errors.assetUniqueCode}</div>
                                        ) : null}

                                    </div>
                                    <div>
                                        <label>SIM No</label>
                                        <Field className="form-control" name="sim" placeholder="Enter SIM No." />
                                        {errors.sim && touched.sim ? (
                                            <div className="error__msg">{errors.sim}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label>IMEI No</label>
                                        <Field className="form-control" name="imei" placeholder="Enter IMEI No." />
                                        {errors.imei && touched.imei ? (
                                            <div className="error__msg">{errors.imei}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label>IMSI No</label>
                                        <Field className="form-control" name="imsi" placeholder="Enter IMSI No." />
                                        {errors.imsi && touched.imsi ? (
                                            <div className="error__msg">{errors.imsi}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label>Created By</label>
                                        <Field className="form-control" name="createdBy" value={localStorage.getItem("username") + '/' + localStorage.getItem('role')} readOnly="true" />
                                    </div>
                                    <div>
                                        <label>User Type</label>
                                        <Select options={this.state.userType} value={
                                            this.state.userType
                                                ? this.state.userType.find(
                                                    (option) => option.value === this.state.optionUser
                                                )
                                                : ""
                                        } onChange={(e) => {
                                            setFieldValue(
                                                "type_of",
                                                e.value
                                            );
                                            this.setState({ optionUser: e.value });
                                        }} placeholder="Select User Type" />
                                    </div>
                                    {(this.state.optionUser === "user") ?
                                        <div>
                                            <label>Select User</label>
                                            <Select options={this.state.allData} placeholder="Select User" onChange={(value) => {
                                                setFieldValue('user', value.value);
                                                this.setState({ selectedUserId: value.id })
                                            }} />
                                            {/* {errors.user && touched.user ? (
                                            <div className="error__msg">{errors.user}</div>
                                        ) : null} */}
                                        </div>
                                        : ""
                                    }


                                </> : (this.state.selectedCategory === 'LoRaWan') ? <>
                                </> : <>
                                    <div>
                                        <label>Created By</label>
                                        <Field className="form-control" name="createdBy" value={localStorage.getItem("username") + '/' + localStorage.getItem('role')} readOnly="true" />
                                    </div>
                                    <div>
                                        <label>User Type</label>
                                        <Select options={this.state.userType} value={
                                            this.state.userType
                                                ? this.state.userType.find(
                                                    (option) => option.value === this.state.optionUser
                                                )
                                                : ""
                                        } onChange={(e) => {
                                            setFieldValue(
                                                "type_of",
                                                e.value
                                            );
                                            this.setState({ optionUser: e.value });
                                        }} placeholder="Select User Type" />
                                    </div>
                                    {(this.state.optionUser === "user") ?
                                        <div>
                                            <label>Select User</label>
                                            <Select options={this.state.allData} placeholder="Select User" onChange={(value) => {
                                                setFieldValue('user', value.value);
                                                this.setState({ selectedUserId: value.id })
                                            }} />
                                            {/* {errors.user && touched.user ? (
                                            <div className="error__msg">{errors.user}</div>
                                        ) : null} */}
                                        </div>
                                        : ""
                                    }
                                </>}
                            </div>
                            <br />
                            <div align="center">
                                <button type="submit" className="btn btn-primary">Submit</button>
                            </div>
                        </Form>
                    )}
                </Formik>
            </div>
        )
    }
}


