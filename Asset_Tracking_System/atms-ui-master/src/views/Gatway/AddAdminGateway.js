import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import moment from "moment";
import './AddGatway.css'
import { getDeviceUser } from '../../Service/getDeviceUser'
import { getAssetCategory } from '../../Service/getAssetCategory'
import { getOrgnization } from '../../Service/getOrgnization'
import { getAdmin } from '../../Service/getAdmin'
import { postGatway } from '../../Service/postGatway'
import { getGatewayValidation } from '../../Service/getGatewayValidation'

import Select from "react-select";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';


import "moment-timezone";
import { getAdminwiseUserDropDown } from "../../Service/getAdminwiseUserDropDown";

import { postDevice } from '../../Service/postDevice'

const SignupSchema = Yup.object().shape({
    assetGatway: Yup.string()
        .required('Enter Asset Gatway Name'),
    barcode: Yup.string()
        .required('Enter Asset Barcode Name'),
    assetUniqueCode: Yup.string()
        .required('Enter Asset Unique Code Name'),
    location: Yup.string()
        .required('Enter location Name'),
});

const timezones = timezoneNames()
    .filter((l) => l.indexOf("/") > -1)
    .sort((a, b) => a.localeCompare(b));

export default class AddAdminGateway extends Component {

    // interval;
    state = {
        timezone: "Asia/Kolkata",
        date: null,
        result: null,
        allData: [],
        allCategory: [],
        selectedCategory: "",
        allAdminData: [],
        selectedUser: "",
        selectedUserId: "",
        selectedAdmin: "",
        selectedAdminId: "",
        selectedOrgId: "",
        selectedOrg: "",
        Role: "",
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
        this.setState({ Role: role });
        let resultcategory = await getAssetCategory();
        console.log('resultcategory', resultcategory);
        let result = await getDeviceUser();
        console.log('result', result);
        // this.tick();
        // this.interval = setInterval(() => this.tick(), 1000);
        let tempArray = []
        result && result.data && result.data.map((obj) => {
            console.log('objresult', obj);
            let tempObj = {
                id: `${obj.pkuserId}`,
                // value: `${obj.firstName + " " + obj.lastName}`,
                // label: `${obj.firstName + " " + obj.lastName}`
                value: `${obj.username}`,
                label: `${obj.username}`
            }
            console.log('tempObj', tempObj);

            tempArray.push(tempObj);
        })
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

        this.setState({ allData: tempArray, allCategory: tempCategoryArray, }, () => {
            console.log('category', this.state.allCategory,)
        });

        let resultAdmin = await getAdmin();

        let tempAdminArray = []
        resultAdmin && resultAdmin.data && resultAdmin.data.map((obj) => {
            console.log('resultAdmin', obj);
            let tempadminObj = {
                id: `${obj.pkuserId}`,
                // value: `${obj.firstName + " " + obj.lastName}`,
                // label: `${obj.firstName + " " + obj.lastName}`
                value: `${obj.username}`,
                label: `${obj.username}`
            }
            tempAdminArray.push(tempadminObj)
        })

        this.setState({ allAdminData: tempAdminArray })

        let resultOrg = await getOrgnization();
        console.log('Orgnization', resultOrg)
        let tempOrgArray = []
        resultOrg && resultOrg.data && resultOrg.data.map((obj) => {
            //  console.log('resultAdmin', obj);
            let tempOrgObj = {
                id: `${obj.pkuserId}`,
                // value: `${obj.firstName + " " + obj.lastName}`,
                // label: `${obj.firstName + " " + obj.lastName}`
                value: `${obj.username}`,
                label: `${obj.username}`
            }

            tempOrgArray.push(tempOrgObj)
        })

        this.setState({ allOrgnizationData: tempOrgArray }, () => {
            console.log('AllOrgnization', this.state.allOrgnizationData)
        })
    }


    async validateGateway(value) {
        let errors;
        let resultTag = await getGatewayValidation();
        console.log("Tag Val List", resultTag);
        if (resultTag && resultTag.data && resultTag.data.length !== 0) {
            let tempUser = resultTag.data.filter((obj) => {
                return obj.gatewayName === value;
            });
            if (tempUser.length !== 0) {
                errors = "This Gateway name is already exist";
            }
        }
        return errors;
    }
    async validateBarcode(value) {
        let errors;
        let result = await getGatewayValidation();
        console.log("Barcode Val List", result);
        if (result && result.data && result.data.length !== 0) {
            let tempBar = result.data.filter((obj) => {
                return obj.gatewayBarcodeSerialNumber === value;
            });
            if (tempBar.length !== 0) {
                errors = "This Barcode is already exist";
            }
        }
        return errors;
    }
    async validateMac(value) {
        let errors;
        let result = await getGatewayValidation();
        console.log("Mac Val List", result);
        if (result && result.data && result.data.length !== 0) {
            let tempMac = result.data.filter((obj) => {
                return obj.gatewayUniqueCodeMacId === value;
            });
            if (tempMac.length !== 0) {
                errors = "This MacId is already exist";
            }
        }
        return errors;
    }

    async validateLacation(value) {
        let errors;
        let resultLoc = await getGatewayValidation();
        console.log("Mac Val List", resultLoc);
        if (resultLoc && resultLoc.data && resultLoc.data.length !== 0) {
            let tempLoc = resultLoc.data.filter((obj) => {
                return obj.gatewayLocation === value;
            });
            if (tempLoc.length !== 0) {
                errors = "This Location is already exist";
            }
        }
        return errors;
    }

    handleTimezoneChange = (event) => {
        this.setState({ timezone: event.target.value });
    };

    changeUser = (selectedUser) => {

        this.setState({ selectedUser: selectedUser.value, selectedUserId: selectedUser.id }, () => {
            console.log('fkuserid', this.state.selectedUser)
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
        this.setState({ selectedAdmin: selectedAdmin.value, selectedAdminId: selectedAdmin.id }, () => {
            console.log('admin', this.state.selectedAdmin)
            console.log('selectedAdminId', this.state.selectedAdminId)
        })
    }
    changeOrgnization = (selectedOrg) => {
        console.log('selectedOrgnization', selectedOrg)
        this.setState({ selectedOrg: selectedOrg.value, selectedOrgId: selectedOrg.id }, () => {
            console.log('Orgnization', this.state.selectedOrg);
            console.log('Orgnizationid', this.state.selectedOrgId);
        })
    }

    render() {

        const { result, date } = this.state;
        return (
            <div className="userAdd__wrapper">

                <h3>Create Asset Gateway</h3>
                <br />
                <Formik
                    initialValues={{
                        assetGatway: '',
                        assetUniqueCode: '',
                        sim: '',
                        imsi: '',
                        AssetTagType: '',
                        barcode: '',
                        location: '',
                        mac_id: '',
                        user: '',
                        admin: '',
                        organization: '',
                        // adminName: '',
                        // userName: ''
                    }}

                    validationSchema={SignupSchema}
                    onSubmit={async values => {
                        const resultDate = moment()
                            .tz(this.state.timezone)
                            .format()

                        let fkUserId = await localStorage.getItem("fkUserId")

                        const data = {
                            gatewayName: values.assetGatway,
                            gatewayUniqueCodeMacId: values.assetUniqueCode,
                            gatewayBarcodeOrSerialNumber: values.barcode,
                            gatewayLocation: values.location,
                            assetTagCategory: this.state.selectedCategory,
                            user: values.user,
                            admin: values.admin,
                            organization: this.state.selectedOrg,
                            fkOrganizationId: this.state.selectedOrgId,
                            //fkOrganizationId:fkUserId,
                            fkUserId: this.state.selectedUserId,
                            fkAdminId: fkUserId,
                            //fkAdminId: this.state.selectedAdminId,
                            timeZone: this.state.timezone,
                            wakeupTime: moment().tz(values.wakeupTime).format("YYYY-MM-DD HH:mm:ss"),
                            dateTime: resultDate,
                            createdBy: this.state.createdBy
                        }
                        console.log('data', data);

                        let result = await postGatway(data)
                        console.log('Org data', result);
                        if (result.data === 'saved :') {
                            swal("Great", "Gateway Created Successfully", "success");
                            this.props.history.push("/gatway_list")
                            //window.location.reload() 
                        } else if (result.data === 'Dublicate macid or barcode scerial Number :')
                        {
                            swal("Opps!", "Data already present", "warning")
                        }
                        else {
                            swal("Failed", "Something went wrong please check your internet", "error");
                        }

                    }}
                >
                    {({ errors, touched, setFieldValue }) => (
                        <Form>
                            <div className="gateway_form">
                                <div>
                                    <label className='label'>Asset Gateway Name</label>
                                    <Field className="form-control" name="assetGatway" placeholder="Enter Asset Gateway Name" validate={this.validateGateway} />
                                    {errors.assetGatway && touched.assetGatway ? (
                                        <div className="error__msg">{errors.assetGatway}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label className='label'>Select Asset Gateway Category Name</label>

                                    <Select className="form" options={this.state.allCategory} onChange={this.changeCategory} placeholder="Select Asset Gateway Category Name" />
                                    {errors.AssetTagType && touched.AssetTagType ? (
                                        <div className="error__msg">{errors.AssetTagType}</div>
                                    ) : null}
                                </div>

                                {(this.state.selectedCategory === 'BLE') ? <>
                                    <div>
                                        <label>Wakeup Time</label>
                                        <Field className="form-control" type="time" step="1" name="wakeupTime" />

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
                                    <div>
                                        <label className='label'>Asset Gateway Unique Code/Mac_Id</label>
                                        <Field className="form-control" name="assetUniqueCode" placeholder="Enter Asset Gateway Unique Code/Mac_Id" validate={this.validateMac} />
                                        {errors.assetUniqueCode && touched.assetUniqueCode ? (
                                            <div className="error__msg">{errors.assetUniqueCode}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label className='label'>Gateway Barcode No./Gateway Serial No</label>
                                        <Field className="form-control" name="barcode" placeholder="Enter Gateway Barcode No./Gateway Serial No." validate={this.validateBarcode} />
                                        {errors.barcode && touched.barcode ? (
                                            <div className="error__msg">{errors.barcode}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label className='label'>Location</label>
                                        <Field className="form-control" name="location" placeholder="Enter Location" validate={this.validateLacation} />
                                        {errors.location && touched.location ? (
                                            <div className="error__msg">{errors.location}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label className='label'>Created By</label>
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
                                    <div>
                                        <label>Wakeup Time</label>
                                        <Field className="form-control" type="time" name="wakeupTime" />

                                    </div>
                                    <div>
                                        <label>Timezone</label>
                                        {/* <Field className="form-control" name="timezone" placeholder="TimeZone" /> */}
                                        <select
                                            className="form-control"
                                            onChange={this.handleTimezoneChange}
                                            value={this.state.timezone}
                                        >
                                            {timezones.map((zone, i) => (
                                                <option key={i}>{zone}</option>
                                            ))}
                                        </select>
                                        {moment()
                                            .tz(this.state.timezone)
                                            .format()}
                                    </div>
                                    <div>
                                        <label className='label'>Asset Gateway Unique Code/Mac_Id</label>
                                        <Field className="form-control" name="assetUniqueCode" placeholder="Enter Asset Gateway Unique Code/Mac_Id" validate={this.validateMac} />
                                        {errors.assetUniqueCode && touched.assetUniqueCode ? (
                                            <div className="error__msg">{errors.assetUniqueCode}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label className='label'>Enter Gateway Barcode No./Gateway Serial No</label>
                                        <Field className="form-control" name="barcode" placeholder="Enter Gateway Barcode No./Gateway Serial No." validate={this.validateBarcode} />
                                        {errors.barcode && touched.barcode ? (
                                            <div className="error__msg">{errors.barcode}</div>
                                        ) : null}
                                    </div>
                                    <div>
                                        <label className='label'>Location</label>
                                        <Field className="form-control" name="location" placeholder="Enter Location" validate={this.validateLacation} />
                                        {errors.location && touched.location ? (
                                            <div className="error__msg">{errors.location}</div>
                                        ) : null}
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
                                        </div>
                                        : ""
                                    }
                                </> : <>
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
                                        </div>
                                        : ""
                                    }
                                </>}
                            </div>
                            <br />
                            {(this.state.selectedCategory === 'GPS') ? <>
                                <div align="center"><h1>No Need To Create Gateway For GPS</h1></div>
                                <div align="center" style={{ display: 'none' }}><button type="submit" className="btn btn-primary">SUBMIT</button></div></>
                                : <><div align="center"><button type="submit" className="btn btn-primary">SUBMIT</button></div></>}
                        </Form>
                    )}
                </Formik>
            </div>
        )
    }
}


