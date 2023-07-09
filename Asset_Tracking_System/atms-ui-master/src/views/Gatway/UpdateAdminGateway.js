import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import moment from "moment";
import { Link, NavLink } from 'react-router-dom';
import { getDeviceUser } from '../../Service/getDeviceUser'
import { getAdmin } from '../../Service/getAdmin'
import './AddGatway.css'
import { getAssetCategory } from '../../Service/getAssetCategory'
import { getEditGateway } from '../../Service/getEditGateway'
import { getOrgnization } from '../../Service/getOrgnization'
import { getGatewayValidation } from '../../Service/getGatewayValidation'
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';
import Select from "react-select";
import "moment-timezone";

import { getSingleDevice } from "../../Service/getSingleDevice"
import { putGateway } from "../../Service/putGateway"
const SignupSchema = Yup.object().shape({
    assetGatway: Yup.string()
        .required('Enter AssetTag Name'),
    assetUniqueCode: Yup.string()
        .required('Enter Asset Unique Code'),
    // timezone: Yup.string()
    // .required('Required'),

});
const timezones = timezoneNames()
    .filter((l) => l.indexOf("/") > -1)
    .sort((a, b) => a.localeCompare(b));
export default class UpdateAdminGateway extends Component {
    state = {
        timezone: "Asia/Kolkata",
        result: [],
        allDataUser: [],
        allDataAdmin: [],
        allCategory: [],
        selectedCategory: "",
        id: 0,
        selectedUserId: "",
        selectedUser: "",
        selectedAdminId: "",
        selectedAdmin: "",
        selectedOrgId: "",
        selectedOrg: "",
        allOrgnizationData: [],
        loader: false,
        Role: ""
    }
    componentDidMount = async () => {
        setTimeout(async () => {
            try {

                let role = localStorage.getItem("role");
                this.setState({ Role: role });
                this.setState({ loader: true })
                setTimeout(async () => {
                    if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
                        const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;

                        let resultcategory = await getAssetCategory();
                        console.log('resultcategory', resultcategory);
                        let result = await getEditGateway(propId)
                        console.log('resultGateway', result)


                        let resultUser = await getDeviceUser();
                        console.log('resultUser', resultUser)
                        let tempArray = []
                        resultUser && resultUser.data && resultUser.data.map((obj) => {
                            let tempObj = {
                                UserId: `${obj.pkuserId}`,
                                // value: `${obj.firstName + " " + obj.lastName}`,
                                // label: `${obj.firstName + " " + obj.lastName}`
                                value: `${obj.username}`,
                                label: `${obj.username}`
                            }
                            console.log('tempObj', tempObj);

                            tempArray.push(tempObj);
                        })

                        let resultAdmin = await getAdmin();
                        console.log('resultAdmin', resultAdmin)
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
                        this.setState({ timezone: result.data[0].timeZone, selectedCategory: result.data[0].assetTagCategory }, () => {
                            console.log('timezone', result.data[0].assetTagCategory)
                        })

                        console.log('ress1', this.state.timezone)
                        console.log('ress2', result)

                        let resultOrg = await getOrgnization();
                        console.log('Orgnization', resultOrg)
                        let tempOrgArray = []
                        resultOrg && resultOrg.data && resultOrg.data.map((obj) => {
                            //  console.log('resultAdmin', obj);
                            let tempOrgObj = {
                                OrgId: `${obj.pkuserId}`,
                                // value: `${obj.firstName + " " + obj.lastName}`,
                                // label: `${obj.firstName + " " + obj.lastName}`
                                value: `${obj.username}`,
                                label: `${obj.username}`
                            }
                            tempOrgArray.push(tempOrgObj)
                        })

                        this.setState({ allDataUser: tempArray, allDataAdmin: tempAdminArray, result: result && result.data, id: propId, loader: false, allCategory: tempCategoryArray, allOrgnizationData: tempOrgArray }, () => {

                            console.log("user", this.state.allDataUser);
                            console.log("admin...", this.state.allDataAdmin);
                            console.log('result', this.state.result)
                            console.log("allOrgnization...", this.state.allOrgnizationData);
                            this.setState({ selectedUserId: this.state.result[0].fkUserId }, () => {
                                console.log("selectedUserId", this.state.selectedUserId);
                            })
                        });


                    } else if (this.props.match && this.props.match.params && this.props.match.params.id) {
                        const propParamId = this.props.match && this.props.match.params && this.props.match.params.id;
                        setTimeout(async () => {
                            let resultcategory = await getAssetCategory();
                            console.log('resultcategory', resultcategory);
                            let result = await getSingleDevice(propParamId)


                            let resultUser = await getDeviceUser();
                            console.log('resultUser', resultUser)
                            let tempArray = []
                            resultUser && resultUser.data && resultUser.data.map((obj) => {
                                let tempObj = {
                                    UserId: `${obj.pkuserId}`,
                                    // value: `${obj.firstName + " " + obj.lastName}`,
                                    // label: `${obj.firstName + " " + obj.lastName}`
                                    value: `${obj.username}`,
                                    label: `${obj.username}`
                                }
                                console.log('tempObj', tempObj);

                                tempArray.push(tempObj);
                            })

                            let resultAdmin = await getAdmin();
                            console.log('resultAdmin', resultAdmin)

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

                            console.log('ress2', result)
                            this.setState({ timezone: result.data[0].timeZone })
                            console.log('ress1', result.data[0].timeZone)

                            let resultOrg = await getOrgnization();
                            console.log('Orgnization', resultOrg)
                            let tempOrgArray = []
                            resultOrg && resultOrg.data && resultOrg.data.map((obj) => {
                                let tempOrgObj = {
                                    OrgId: `${obj.pkuserId}`,
                                    // value: `${obj.firstName + " " + obj.lastName}`,
                                    // label: `${obj.firstName + " " + obj.lastName}`
                                    value: `${obj.username}`,
                                    label: `${obj.username}`
                                }
                                tempOrgArray.push(tempOrgObj)
                            })

                            this.setState({ allDataUser: tempArray, allDataAdmin: tempAdminArray, result: result && result.data, id: propParamId, loader: false, allCategory: tempCategoryArray, allOrgnizationData: tempOrgArray })
                            console.log('AllOrgnization', this.state.allOrgnizationData)
                        }, 100)
                    } else {
                        this.props.history.push("/gatway_list");
                    }
                }, 100)

            } catch (error) {
                console.log(error)
                this.setState({ loader: false })
            }
        }, 200)
    }

    // async validateGateway(value) {
    //     let errors;
    //     let resultTag = await getGatewayValidation();
    //     console.log("Tag Val List", resultTag);
    //     if (resultTag && resultTag.data && resultTag.data.length !== 0) {
    //         let tempUser = resultTag.data.filter((obj) => {
    //             return obj.gatewayName === value;
    //         });
    //         if (tempUser.length !== 0) {
    //             errors = "This Gateway name is already exist";
    //         }
    //     }
    //     return errors;
    // }
    // async validateBarcode(value) {
    //     let errors;
    //     let result = await getGatewayValidation();
    //     console.log("Barcode Val List", result);
    //     if (result && result.data && result.data.length !== 0) {
    //         let tempBar = result.data.filter((obj) => {
    //             return obj.gatewayBarcodeSerialNumber === value;
    //         });
    //         if (tempBar.length !== 0) {
    //             errors = "This Barcode is already exist";
    //         }
    //     }
    //     return errors;
    // }
    // async validateMac(value) {
    //     let errors;
    //     let result = await getGatewayValidation();
    //     console.log("Mac Val List", result);
    //     if (result && result.data && result.data.length !== 0) {
    //         let tempMac = result.data.filter((obj) => {
    //             return obj.gatewayUniqueCodeMacId === value;
    //         });
    //         if (tempMac.length !== 0) {
    //             errors = "This MacId is already exist";
    //         }
    //     }
    //     return errors;
    // }

    changeCategory = (selectedCategory) => {

        this.setState({ selectedCategory: selectedCategory.target.value, }, () => {
            console.log('pkCategoryid', this.state.selectedCategory)

        })
    }
    handleTimezoneChange = (event) => {
        this.setState({ timezone: event.target.value });
    };
    // changeUser = (selectedUser) => {
    //     console.log('selectedUser--------.', selectedUser.UserId);
    //     this.setState({ selectedUserId: selectedUser.UserId }, () => {
    //         console.log('UserId', this.state.selectedUserId);
    //     })
    // }
    // changeAdmin = (selectedAdmin) => {
    //     console.log('selectedAdmin', selectedAdmin.AdminId);
    //     this.setState({ selectedAdminId: selectedAdmin.AdminId }, () => {
    //         console.log('AdminId', this.state.selectedAdminId);
    //     })
    // }
    // changeOrgnization = (selectedOrg) => {
    //     console.log('selected Orgnization', selectedOrg.OrgId)
    //     this.setState({ selectedOrgId: selectedOrg.OrgId }, () => {
    //         console.log('Orgnization', this.state.selectedOrgId);
    //     })
    // }

    changeUser = (selectedUser) => {

        this.setState({ selectedUser: selectedUser.value, selectedUserId: selectedUser.id }, () => {
            console.log('Users', this.state.selectedUser)
            console.log('selectedUserId', this.state.selectedUserId)
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
        this.setState({ selectedOrg: selectedOrg.value, selectedOrgId: selectedOrg.id }, () => {
            console.log('Orgnization', this.state.selectedOrg);
            console.log('Orgnizationid', this.state.selectedOrgId);
        })
    }




    render() {

        console.log('initialValues', this.state && this.state.result[0] && this.state.result[0].wakeupTime);
        console.log(this.state.allDataUser)
        const { loader } = this.state
        return (
            <>
                {loader ? <div class="loader"></div> :
                    <div className="userAdd__wrapper">

                        <h3>Update Asset Gateway</h3>
                        <br />
                        <br />
                        <Formik
                            enableReinitialize={true}
                            initialValues={{
                                assetGatway: this.state && this.state.result[0] && this.state.result[0].gatewayName,
                                assetUniqueCode: this.state && this.state.result[0] && this.state.result[0].gatewayUniqueCodeMacId,
                                AssetTagType: this.state && this.state.result[0] && this.state.result[0].assetTagCategory,
                                barcode: this.state && this.state.result[0] && this.state.result[0].gatewayBarcodeOrSerialNumber,
                                location: this.state && this.state.result[0] && this.state.result[0].gatewayLocation,
                                wakeupTime: this.state && this.state.result[0] && this.state.result[0].wakeupTime,
                                timeZone: this.state && this.state.result[0] && this.state.result[0].timeZone,
                                dateTime: this.state && this.state.result[0] && this.state.result[0].dateTime,
                                fkAdminId: this.state && this.state.result[0] && this.state.result[0].fkAdminId,
                                admin: this.state && this.state.result[0] && this.state.result[0].admin,
                                user: this.state && this.state.result[0] && this.state.result[0].user,
                                fkUserId: this.state && this.state.result[0] && this.state.result[0].fkUserId,
                                fkOrganizationId: this.state && this.state.result[0] && this.state.result[0].fkOrganizationId,
                                organization: this.state && this.state.result[0] && this.state.result[0].organization,
                                status: this.state && this.state.result[0] && this.state.result[0].status,
                                createdBy: this.state && this.state.result[0] && this.state.result[0].createdBy
                            }}
                            validationSchema={SignupSchema}
                            onSubmit={async values => {
                                // const resultDate = moment(values.datetime).format("YYYY-MM-DDTHH:mm:ss.SSS[Z]");
                                const resultDate = moment()
                                    .tz(this.state.timezone)
                                    .format("YYYY-MM-DDTHH:mm:ss.SSS[Z]")

                                // let UserData = this.state.allDataUser;
                                // var selectUserId = UserData.filter(UserId => UserId.value == values.user);
                                // var UpdateUserId = selectUserId.id;

                                // let AdminData = this.state.allDataAdmin;
                                // var selectAdminId = AdminData.filter(AdminId => AdminId.value == values.admin);
                                // var UpdateAdminId = selectAdminId.id;

                                // let OrgData = this.state.allOrgnizationData;
                                // var selectOrgId = OrgData.filter(OrgId => OrgId.value == values.orgnization);
                                // var UpdateOrgId = selectOrgId.OrgId;
                                let fkUserId = await localStorage.getItem("fkUserId")
                                const payload = {
                                    gatewayName: values.assetGatway,
                                    gatewayUniqueCodeMacId: values.assetUniqueCode,
                                    gatewayBarcodeOrSerialNumber: values.barcode,
                                    gatewayLocation: values.location,
                                    assetTagCategory: values.AssetTagType,
                                    user: values.user,
                                    admin: values.admin,
                                    organization: values.organization,
                                    fkUserId: this.state.selectedUserId,
                                    fkAdminId: fkUserId,
                                    //fkOrganizationId:fkUserId,
                                    fkOrganizationId: this.state.selectedOrgId,
                                    dateTime: resultDate,
                                    gatewayId: this.state.id,
                                    timeZone: this.state.timezone,
                                    //wakeupTime:  moment().tz(values.wakeupTime).format("YYYY-MM-DD HH:mm:ss"),
                                    wakeupTime: values.wakeupTime,
                                    createdBy: this.state && this.state.result[0] && this.state.result[0].createdBy
                                    // date: resultDate,
                                    // time: values.time,
                                }

                                console.log("Updated data------>", payload)
                                let result = await putGateway(payload)
                                console.log('payload', payload)
                                if (result.status === 200) {
                                    swal("Great", "Data added successfully", "success");

                                    //this.props.history.push("/gatway_list")
                                } else {
                                    swal("Failed", "Something went wrong please check your internet", "error");
                                }
                            }}
                        >
                            {({ errors, touched, setFieldValue, values }) => (
                                <Form>
                                    <div className="gatewayUpdate_form">
                                        <div>
                                            <label>Asset Gateway Name</label>
                                            <Field className="form-control" name="assetGatway" placeholder="Enter Asset Gateway Name" validate={this.validateGateway} />
                                            {errors.assetGatway && touched.assetGatway ? (
                                                <div className="error__msg">{errors.assetGatway}</div>
                                            ) : null}
                                        </div>
                                        <div>
                                            <label>Select Asset Gateway Category Name</label>
                                            <Field className="form-control" name="AssetTagType" placeholder="Enter Asset Tag Name" readOnly="true" />
                                        </div>


                                        {(this.state.selectedCategory === 'BLE') ? <>
                                            <div>
                                                <label>Wakeup Time</label>
                                                <Field className="form-control" type="datetime-local" step="1" name="wakeupTime" />
                                            </div>
                                            <div>
                                                <label>Timezone</label>
                                                {/* <Field className="form-control" name="timezone" placeholder="TimeZone" /> */}
                                                <select
                                                    className="form-control"
                                                    onChange={this.handleTimezoneChange}
                                                    value={this.state.timezone} name="timeZone"
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
                                                <label>Asset Gateway Unique Code / Mac_Id</label>
                                                <Field className="form-control" name="assetUniqueCode" placeholder="Enter Asset Gateway Unique Code/Mac_Id" validate={this.validateMac} />
                                                {errors.assetUniqueCode && touched.assetUniqueCode ? (
                                                    <div className="error__msg">{errors.assetUniqueCode}</div>
                                                ) : null}
                                            </div>

                                            <div>
                                                <label>Asset Gateway Barcode No./ Serial No.</label>
                                                <Field className="form-control" name="barcode" placeholder="Enter Gateway Barcode No./Gateway Serial No." validate={this.validateBarcode} />
                                            </div>
                                            <div>
                                                <label>Asset Gateway Location</label>
                                                <Field className="form-control" name="location" placeholder="Enter Location" />
                                            </div>
                                                <div>
                                                    <label>User</label>
                                                    <Select
                                                        value={
                                                            this.state.allDataUser
                                                                ? this.state.allDataUser.find(
                                                                    (option) => option.value === values.user
                                                                )
                                                                : ""
                                                        }
                                                        onChange={(e) => {
                                                            setFieldValue("user", e.value);
                                                            this.setState(
                                                                { selectedUser: e.value, selectedUserId: e.UserId },
                                                                () => {
                                                                    console.log("Users", this.state.selectedUser);
                                                                    console.log("selectedUserId", this.state.selectedUserId);
                                                                }
                                                            );
                                                        }}
                                                        options={this.state.allDataUser}
                                                    />
                                                </div>
                            

                                        </> : (this.state.selectedCategory === 'GPS') ? <>

                                        </> : (this.state.selectedCategory === 'LoRaWan') ? <>
                                            <div>
                                                <label>Wakeup Time</label>
                                                <Field className="form-control" type="datetime-local" name="wakeupTime" />

                                            </div>
                                            <div>
                                                <label>Timezone</label>
                                                {/* <Field className="form-control" name="timezone" placeholder="TimeZone" /> */}
                                                <select
                                                    className="form-control"
                                                    onChange={this.handleTimezoneChange}
                                                    value={this.state.timezone} name="timeZone"
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
                                                <label>Asset Gateway Unique Code / Mac_Id</label>
                                                <Field className="form-control" name="assetUniqueCode" placeholder="Enter Asset Gateway Unique Code/Mac_Id" validate={this.validateMac} />
                                                {errors.assetUniqueCode && touched.assetUniqueCode ? (
                                                    <div className="error__msg">{errors.assetUniqueCode}</div>
                                                ) : null}
                                            </div>
                                            <div>
                                                <label>Asset Gateway Barcode No./ Serial No.</label>
                                                <Field className="form-control" name="barcode" placeholder="Enter Gateway Barcode No./Gateway Serial No." validate={this.validateBarcode} />
                                            </div>
                                            <div>
                                                <label>Asset Gateway Location</label>
                                                <Field className="form-control" name="location" placeholder="Enter Location" />
                                            </div>
                                                <div>
                                                    <label>User</label>
                                                    <Select
                                                        value={
                                                            this.state.allDataUser
                                                                ? this.state.allDataUser.find(
                                                                    (option) => option.value === values.user
                                                                )
                                                                : ""
                                                        }
                                                        onChange={(e) => {
                                                            setFieldValue("user", e.value);
                                                            this.setState(
                                                                { selectedUser: e.value, selectedUserId: e.UserId },
                                                                () => {
                                                                    console.log("Users", this.state.selectedUser);
                                                                    console.log("selectedUserId", this.state.selectedUserId);
                                                                }
                                                            );
                                                        }}
                                                        options={this.state.allDataUser}
                                                    />
                                                </div>
                            
                                        </> : <>
                                            <div>
                                                <label>User</label>
                                                <Field as="select" className="form-control" name="user" >
                                                    {this.state && this.state.allDataUser && this.state.allDataUser.map((obj) => (

                                                        <option value={`${obj.value}`}>{obj.value}</option>
                                                    ))}
                                                </Field>
                                            </div>

                                        </>}
                                    </div>
                                    <br />
                                    {(this.state.selectedCategory === 'GPS') ? <>
                                        <div align="center"><h1>No Need To Create Gateway For GPS</h1></div>
                                        <div align="center" style={{ display: 'none' }}><button type="submit" className="btn btn-primary">SUBMIT</button></div></>
                                        : <><div className='btn-gateway' align="center">
                                            <button type="submit" className="btn btn-primary">SUBMIT</button>
                                            <button type="cancel" className="btn btn-primary"> <Link to={{ pathname: "/product-list" }}>Cancel</Link></button>
                                        </div></>}


                                </Form>
                            )}
                        </Formik>
                    </div>
                }
            </>
        )
    }
}
