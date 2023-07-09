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
import { getOnlyAdminDrowpdownList } from "../../Service/getOnlyAdminDrowpdownList";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import { getAdminwiseUserDropDown } from "../../Service/getAdminwiseUserDropDown";
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';
import Select from "react-select";
import "moment-timezone";
import axios from '../../utils/axiosInstance';
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
export default class UpdateGatway extends Component {
    state = {
        timezone: "Asia/Kolkata",
        result: [],
        allDataUser: [],
        allDataAdmin: [],
        allOnlyAdminList: [],
        allCategory: [],
        allOnlyAdminList: [],
        selectedCategory: "",
        id: 0,
        selectedUserId: "",
        selectedUser: "",
        selectedAdminId: "",
        selectedAdmin: "",
        selectedOrgId: "",
        selectedOrg: "",
        allOrgnizationData: [],
        allBar: [],
        loader: false,
        Role: "",
        allSuperUser: [
            {
                label: "User",
                value: "user",
            },
            {
                label: "Admin",
                value: "admin",
            },
            // {
            //   label: "Organization",
            //   value: "organization",
            // },
        ],
        optionUser: "",
        optionAllUser: "",
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

                        this.setState({ allDataUser: tempArray, result: result && result.data, id: propId, loader: false, allCategory: tempCategoryArray, allOrgnizationData: tempOrgArray }, () => {

                            console.log("user", this.state.allDataUser);
                            console.log('result', this.state.result)
                            console.log("allOrgnization...", this.state.allOrgnizationData);
                            this.setState({ selectedUserId: this.state.result[0].fkUserId }, () => {
                                console.log("selectedUserId", this.state.selectedUserId);
                            })
                            this.setState({ selectedAdminId: this.state.result[0].fkAdminId }, () => {
                                console.log("selectedAdminId", this.state.selectedAdminId);
                            })
                            this.setState({ selectedOrgId: this.state.result[0].fkOrganizationId }, async () => {
                                console.log("selectedOrgId", this.state.selectedOrgId);
                                if (this.state.selectedOrgId !== null) {
                                    let token = localStorage.getItem("token");
                                    axios
                                        .get(
                                            `user/get-admin_list?fkUserId=${this.state.selectedOrgId}&role=organization&category=BLE`,
                                            {
                                                headers: { Authorization: `Bearer ${token}` },
                                            }
                                        )
                                        .then((result) => {
                                            console.log("Admin Name", result);
                                            if (result && result.data && result.data.length !== 0) {
                                                let tempAdminArray = [];

                                                result.data.map((obj) => {
                                                    let tempAdminObj = {
                                                        id: `${obj.pkuserId}`,
                                                        value: `${obj.username}`,
                                                        label: `${obj.username}`,
                                                    };
                                                    tempAdminArray.push(tempAdminObj);
                                                });
                                                this.setState({ allDataAdmin: tempAdminArray }, () => {
                                                    console.log("allAdmin List", this.state.allDataAdmin);
                                                });
                                            } else {
                                                swal("Sorry", "Data is not present", "warning");
                                            }
                                            return Promise.resolve();
                                        })
                                        .catch((result) => {
                                            swal("Failed", "Somthing went wrong", "error");
                                        });

                                } else {
                                    let resultAdmin = await getOnlyAdminDrowpdownList();
                                    console.log('resultAdmin', resultAdmin)
                                    let tempAdminArray = []
                                    resultAdmin && resultAdmin.data && resultAdmin.data.map((obj) => {
                                        console.log('resultAdmin', obj);
                                        let tempadminObj = {
                                            AdminId: `${obj.pkuserId}`,
                                            // value: `${obj.firstName + " " + obj.lastName}`,
                                            // label: `${obj.firstName + " " + obj.lastName}`
                                            value: `${obj.username}`,
                                            label: `${obj.username}`
                                        }

                                        tempAdminArray.push(tempadminObj)
                                    })
                                    this.setState({ allOnlyAdminList: tempAdminArray }, () => {
                                        console.log("allAdmin List", this.state.allOnlyAdminList);
                                    });
                                }

                            })

                        });

                    } else {
                        this.props.history.push("/gatway_list");
                    }


                    // let resultBar = await getGatewayValidation();
                    // console.log("Barcode Val List", resultBar);
                    // let tempBarArray =[]
                    // resultBar && resultBar.data && resultBar.data.map((obj) => {
                    //     let tempBarObj = {
                    //         value: `${obj.gatewayBarcodeSerialNumber}`,
                    //         label: `${obj.gatewayBarcodeSerialNumber}`
                    //     }
                    //     tempBarArray.push(tempBarObj)
                    // })
                    // this.setState({ allBar: tempBarArray },()=>{
                    //     console.log('AllBarcode', this.state.allBar)
                    // })

                }, 100)

            } catch (error) {
                console.log(error)
                this.setState({ loader: false })
            }
        }, 200)
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevState.allBar !== this.state.allBar) {
            // Now fetch the new data here.
        }
    }
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

    onChangeRole = (e) => {
        this.setState(
            { selectedRole: e.target.value, allAdminList: [] },
            async () => {
                if (this.state.selectedRole === "withoutorganization") {
                    await getOnlyAdminDrowpdownList().then((result) => {
                        console.log("Only Admin List", result);
                        if (result && result.data && result.data.length !== 0) {
                            let tempAdminArray = [];
                            result.data.map((obj) => {
                                let tempadminObj = {
                                    id: `${obj.pkuserId}`,
                                    value: `${obj.username}`,
                                    label: `${obj.username}`,
                                };
                                tempAdminArray.push(tempadminObj);
                            });
                            this.setState({ allAdminList: tempAdminArray }, () => {
                                console.log("Alladmin", this.state.allAdminList);
                            });
                        } else {
                            swal(
                                "Sorry",
                                "Admin is not present. Please create admin first",
                                "warning"
                            );
                            this.setState({ allAdminList: [] });
                        }
                    });
                }
            }
        );
    };

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
                                    fkAdminId: this.state.selectedAdminId,
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

                                            {this.state.selectedOrgId !== null && this.state.selectedAdminId !== null && this.state.selectedUserId === null ? (
                                                <>
                                                    <div>
                                                        <label>Select Organization</label>
                                                        <Select
                                                            value={
                                                                this.state.allOrgnizationData
                                                                    ? this.state.allOrgnizationData.find(
                                                                        (option) => option.value === values.organization
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("organization", e.value);
                                                                this.setState(
                                                                    { selectedOrg: e.value, selectedOrgId: e.OrgId },
                                                                    async () => {
                                                                        console.log("selected Organization", this.state.selectedOrg);
                                                                        console.log("selected Organization", this.state.selectedOrgId);
                                                                        let token = localStorage.getItem("token");
                                                                        axios
                                                                            .get(
                                                                                `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=BLE`,
                                                                                {
                                                                                    headers: { Authorization: `Bearer ${token}` },
                                                                                }
                                                                            )
                                                                            .then((result) => {
                                                                                console.log("Admin Name", result);
                                                                                console.log("this.state.optionUser", this.state.optionUser);
                                                                                if (result && result.data && result.data.length !== 0) {
                                                                                    let tempAdminArray = [];
                                                                                    result.data.map((obj) => {
                                                                                        let tempAdminObj = {
                                                                                            id: `${obj.pkuserId}`,
                                                                                            value: `${obj}`,
                                                                                            label: `${obj}`,
                                                                                        };
                                                                                        tempAdminArray.push(tempAdminObj);
                                                                                    });
                                                                                    this.setState({ allDataAdmin: tempAdminArray }, () => {
                                                                                        console.log("allAdmin List", this.state.allDataAdmin);
                                                                                    });
                                                                                } else {
                                                                                    swal(
                                                                                        "Sorry",
                                                                                        "Admin is not present. Please create admin first",
                                                                                        "warning"
                                                                                    );
                                                                                    this.setState({ allDataAdmin: [] });
                                                                                }
                                                                                return Promise.resolve();
                                                                            })
                                                                            .catch((result) => {
                                                                                swal("Failed", "Somthing went wrong", "error");
                                                                            });
                                                                    }
                                                                );
                                                            }}
                                                            options={this.state.allOrgnizationData}
                                                        />
                                                    </div>

                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allDataAdmin
                                                                    ? this.state.allDataAdmin.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                });
                                                            }}
                                                            options={this.state.allDataAdmin}
                                                        />
                                                    </div>
                                                </>
                                            ) : this.state.selectedOrgId === null && this.state.selectedAdminId !== null && this.state.selectedUserId !== null ? (
                                                <>
                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allOnlyAdminList
                                                                    ? this.state.allOnlyAdminList.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, async () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                    let resultUserList = await getAdminwiseUserDropDown(
                                                                        e.value
                                                                    );
                                                                    let tempUserArray =
                                                                        resultUserList &&
                                                                        resultUserList.data &&
                                                                        resultUserList.data.map((obj) => ({
                                                                            id: `${obj.pkuserId}`,
                                                                            value: `${obj.username}`,
                                                                            label: `${obj.username}`,
                                                                        }));
                                                                    this.setState({ allDataUser: tempUserArray });
                                                                });
                                                            }}
                                                            options={this.state.allOnlyAdminList}
                                                        />
                                                    </div>
                                                    <div>
                                                        <label>Select User</label>
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
                                                </>
                                            ) : this.state.selectedOrgId === null && this.state.selectedAdminId !== null && this.state.selectedUserId === null ? (
                                                <>
                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allOnlyAdminList
                                                                    ? this.state.allOnlyAdminList.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, async () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                    let resultUserList = await getAdminwiseUserDropDown(
                                                                        e.value
                                                                    );
                                                                    let tempUserArray =
                                                                        resultUserList &&
                                                                        resultUserList.data &&
                                                                        resultUserList.data.map((obj) => ({
                                                                            id: `${obj.pkuserId}`,
                                                                            value: `${obj.username}`,
                                                                            label: `${obj.username}`,
                                                                        }));
                                                                    this.setState({ allDataUser: tempUserArray });
                                                                });
                                                            }}
                                                            options={this.state.allOnlyAdminList}
                                                        />
                                                    </div>
                                                    <div>
                                                        <label>Select User</label>
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
                                                </>) : (<>
                                                    <div>
                                                        <label>Select Organization</label>
                                                        <Select
                                                            value={
                                                                this.state.allOrgnizationData
                                                                    ? this.state.allOrgnizationData.find(
                                                                        (option) => option.value === values.organization
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("organization", e.value);
                                                                this.setState(
                                                                    { selectedOrg: e.value, selectedOrgId: e.OrgId },
                                                                    async () => {
                                                                        console.log("selected Organization", this.state.selectedOrg);
                                                                        console.log("selected Organization", this.state.selectedOrgId);
                                                                        let token = localStorage.getItem("token");
                                                                        axios
                                                                            .get(
                                                                                `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=BLE`,
                                                                                {
                                                                                    headers: { Authorization: `Bearer ${token}` },
                                                                                }
                                                                            )
                                                                            .then((result) => {
                                                                                console.log("Admin Name", result);
                                                                                console.log("this.state.optionUser", this.state.optionUser);
                                                                                if (result && result.data && result.data.length !== 0) {
                                                                                    let tempAdminArray = [];
                                                                                    result.data.map((obj) => {
                                                                                        let tempAdminObj = {
                                                                                            id: `${obj.pkuserId}`,
                                                                                            value: `${obj}`,
                                                                                            label: `${obj}`,
                                                                                        };
                                                                                        tempAdminArray.push(tempAdminObj);
                                                                                    });
                                                                                    this.setState({ allDataAdmin: tempAdminArray }, () => {
                                                                                        console.log("allAdmin List", this.state.allDataAdmin);
                                                                                    });
                                                                                } else {
                                                                                    swal(
                                                                                        "Sorry",
                                                                                        "Admin is not present. Please create admin first",
                                                                                        "warning"
                                                                                    );
                                                                                    this.setState({ allDataAdmin: [] });
                                                                                }
                                                                                return Promise.resolve();
                                                                            })
                                                                            .catch((result) => {
                                                                                swal("Failed", "Somthing went wrong", "error");
                                                                            });
                                                                    }
                                                                );


                                                            }}
                                                            // onChange={this.changeOrgName}
                                                            options={this.state.allOrgnizationData}
                                                        />
                                                    </div>

                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allDataAdmin
                                                                    ? this.state.allDataAdmin.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, async () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                    let resultUserList = await getAdminwiseUserDropDown(
                                                                        e.value
                                                                    );
                                                                    let tempUserArray =
                                                                        resultUserList &&
                                                                        resultUserList.data &&
                                                                        resultUserList.data.map((obj) => ({
                                                                            id: `${obj.pkuserId}`,
                                                                            value: `${obj.username}`,
                                                                            label: `${obj.username}`,
                                                                        }));
                                                                    this.setState({ allDataUser: tempUserArray });

                                                                });
                                                            }}
                                                            options={this.state.allDataAdmin}
                                                        />
                                                    </div>
                                                    <div>
                                                        <label>Select User</label>
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
                                                </>)}


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
                                            {this.state.selectedOrgId !== null && this.state.selectedAdminId !== null && this.state.selectedUserId === null ? (
                                                <>
                                                    <div>
                                                        <label>Select Organization</label>
                                                        <Select
                                                            value={
                                                                this.state.allOrgnizationData
                                                                    ? this.state.allOrgnizationData.find(
                                                                        (option) => option.value === values.organization
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("organization", e.value);
                                                                this.setState(
                                                                    { selectedOrg: e.value, selectedOrgId: e.OrgId },
                                                                    async () => {
                                                                        console.log("selected Organization", this.state.selectedOrg);
                                                                        console.log("selected Organization", this.state.selectedOrgId);
                                                                        let token = localStorage.getItem("token");
                                                                        axios
                                                                            .get(
                                                                                `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=BLE`,
                                                                                {
                                                                                    headers: { Authorization: `Bearer ${token}` },
                                                                                }
                                                                            )
                                                                            .then((result) => {
                                                                                console.log("Admin Name", result);
                                                                                console.log("this.state.optionUser", this.state.optionUser);
                                                                                if (result && result.data && result.data.length !== 0) {
                                                                                    let tempAdminArray = [];
                                                                                    result.data.map((obj) => {
                                                                                        let tempAdminObj = {
                                                                                            id: `${obj.pkuserId}`,
                                                                                            value: `${obj}`,
                                                                                            label: `${obj}`,
                                                                                        };
                                                                                        tempAdminArray.push(tempAdminObj);
                                                                                    });
                                                                                    this.setState({ allDataAdmin: tempAdminArray }, () => {
                                                                                        console.log("allAdmin List", this.state.allDataAdmin);
                                                                                    });
                                                                                } else {
                                                                                    swal(
                                                                                        "Sorry",
                                                                                        "Admin is not present. Please create admin first",
                                                                                        "warning"
                                                                                    );
                                                                                    this.setState({ allDataAdmin: [] });
                                                                                }
                                                                                return Promise.resolve();
                                                                            })
                                                                            .catch((result) => {
                                                                                swal("Failed", "Somthing went wrong", "error");
                                                                            });
                                                                    }
                                                                );
                                                            }}
                                                            options={this.state.allOrgnizationData}
                                                        />
                                                    </div>

                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allDataAdmin
                                                                    ? this.state.allDataAdmin.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                });
                                                            }}
                                                            options={this.state.allDataAdmin}
                                                        />
                                                    </div>
                                                </>
                                            ) : this.state.selectedOrgId === null && this.state.selectedAdminId !== null && this.state.selectedUserId !== null ? (
                                                <>
                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allOnlyAdminList
                                                                    ? this.state.allOnlyAdminList.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, async () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                    let resultUserList = await getAdminwiseUserDropDown(
                                                                        e.value
                                                                    );
                                                                    let tempUserArray =
                                                                        resultUserList &&
                                                                        resultUserList.data &&
                                                                        resultUserList.data.map((obj) => ({
                                                                            id: `${obj.pkuserId}`,
                                                                            value: `${obj.username}`,
                                                                            label: `${obj.username}`,
                                                                        }));
                                                                    this.setState({ allDataUser: tempUserArray });
                                                                });
                                                            }}
                                                            options={this.state.allOnlyAdminList}
                                                        />
                                                    </div>
                                                    <div>
                                                        <label>Select User</label>
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
                                                </>
                                            ) : this.state.selectedOrgId === null && this.state.selectedAdminId !== null && this.state.selectedUserId === null ? (
                                                <>
                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allOnlyAdminList
                                                                    ? this.state.allOnlyAdminList.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, async () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                    let resultUserList = await getAdminwiseUserDropDown(
                                                                        e.value
                                                                    );
                                                                    let tempUserArray =
                                                                        resultUserList &&
                                                                        resultUserList.data &&
                                                                        resultUserList.data.map((obj) => ({
                                                                            id: `${obj.pkuserId}`,
                                                                            value: `${obj.username}`,
                                                                            label: `${obj.username}`,
                                                                        }));
                                                                    this.setState({ allDataUser: tempUserArray });
                                                                });
                                                            }}
                                                            options={this.state.allOnlyAdminList}
                                                        />
                                                    </div>
                                                    <div>
                                                        <label>Select User</label>
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
                                                </>) : (<>
                                                    <div>
                                                        <label>Select Organization</label>
                                                        <Select
                                                            value={
                                                                this.state.allOrgnizationData
                                                                    ? this.state.allOrgnizationData.find(
                                                                        (option) => option.value === values.organization
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("organization", e.value);
                                                                this.setState(
                                                                    { selectedOrg: e.value, selectedOrgId: e.OrgId },
                                                                    async () => {
                                                                        console.log("selected Organization", this.state.selectedOrg);
                                                                        console.log("selected Organization", this.state.selectedOrgId);
                                                                        let token = localStorage.getItem("token");
                                                                        axios
                                                                            .get(
                                                                                `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=BLE`,
                                                                                {
                                                                                    headers: { Authorization: `Bearer ${token}` },
                                                                                }
                                                                            )
                                                                            .then((result) => {
                                                                                console.log("Admin Name", result);
                                                                                console.log("this.state.optionUser", this.state.optionUser);
                                                                                if (result && result.data && result.data.length !== 0) {
                                                                                    let tempAdminArray = [];
                                                                                    result.data.map((obj) => {
                                                                                        let tempAdminObj = {
                                                                                            id: `${obj.pkuserId}`,
                                                                                            value: `${obj}`,
                                                                                            label: `${obj}`,
                                                                                        };
                                                                                        tempAdminArray.push(tempAdminObj);
                                                                                    });
                                                                                    this.setState({ allDataAdmin: tempAdminArray }, () => {
                                                                                        console.log("allAdmin List", this.state.allDataAdmin);
                                                                                    });
                                                                                } else {
                                                                                    swal(
                                                                                        "Sorry",
                                                                                        "Admin is not present. Please create admin first",
                                                                                        "warning"
                                                                                    );
                                                                                    this.setState({ allDataAdmin: [] });
                                                                                }
                                                                                return Promise.resolve();
                                                                            })
                                                                            .catch((result) => {
                                                                                swal("Failed", "Somthing went wrong", "error");
                                                                            });
                                                                    }
                                                                );


                                                            }}
                                                            // onChange={this.changeOrgName}
                                                            options={this.state.allOrgnizationData}
                                                        />
                                                    </div>

                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allDataAdmin
                                                                    ? this.state.allDataAdmin.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, async () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                    let resultUserList = await getAdminwiseUserDropDown(
                                                                        e.value
                                                                    );
                                                                    let tempUserArray =
                                                                        resultUserList &&
                                                                        resultUserList.data &&
                                                                        resultUserList.data.map((obj) => ({
                                                                            id: `${obj.pkuserId}`,
                                                                            value: `${obj.username}`,
                                                                            label: `${obj.username}`,
                                                                        }));
                                                                    this.setState({ allDataUser: tempUserArray });

                                                                });
                                                            }}
                                                            options={this.state.allDataAdmin}
                                                        />
                                                    </div>
                                                    <div>
                                                        <label>Select User</label>
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
                                                </>)}
                                        </> : <>
                                            {this.state.selectedOrgId !== null && this.state.selectedAdminId !== null && this.state.selectedUserId === null ? (
                                                <>
                                                    <div>
                                                        <label>Select Organization</label>
                                                        <Select
                                                            value={
                                                                this.state.allOrgnizationData
                                                                    ? this.state.allOrgnizationData.find(
                                                                        (option) => option.value === values.organization
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("organization", e.value);
                                                                this.setState(
                                                                    { selectedOrg: e.value, selectedOrgId: e.OrgId },
                                                                    async () => {
                                                                        console.log("selected Organization", this.state.selectedOrg);
                                                                        console.log("selected Organization", this.state.selectedOrgId);
                                                                        let token = localStorage.getItem("token");
                                                                        axios
                                                                            .get(
                                                                                `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=BLE`,
                                                                                {
                                                                                    headers: { Authorization: `Bearer ${token}` },
                                                                                }
                                                                            )
                                                                            .then((result) => {
                                                                                console.log("Admin Name", result);
                                                                                console.log("this.state.optionUser", this.state.optionUser);
                                                                                if (result && result.data && result.data.length !== 0) {
                                                                                    let tempAdminArray = [];
                                                                                    result.data.map((obj) => {
                                                                                        let tempAdminObj = {
                                                                                            id: `${obj.pkuserId}`,
                                                                                            value: `${obj}`,
                                                                                            label: `${obj}`,
                                                                                        };
                                                                                        tempAdminArray.push(tempAdminObj);
                                                                                    });
                                                                                    this.setState({ allDataAdmin: tempAdminArray }, () => {
                                                                                        console.log("allAdmin List", this.state.allDataAdmin);
                                                                                    });
                                                                                } else {
                                                                                    swal(
                                                                                        "Sorry",
                                                                                        "Admin is not present. Please create admin first",
                                                                                        "warning"
                                                                                    );
                                                                                    this.setState({ allDataAdmin: [] });
                                                                                }
                                                                                return Promise.resolve();
                                                                            })
                                                                            .catch((result) => {
                                                                                swal("Failed", "Somthing went wrong", "error");
                                                                            });
                                                                    }
                                                                );
                                                            }}
                                                            options={this.state.allOrgnizationData}
                                                        />
                                                    </div>

                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allDataAdmin
                                                                    ? this.state.allDataAdmin.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                });
                                                            }}
                                                            options={this.state.allDataAdmin}
                                                        />
                                                    </div>
                                                </>
                                            ) : this.state.selectedOrgId === null && this.state.selectedAdminId !== null && this.state.selectedUserId !== null ? (
                                                <>
                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allOnlyAdminList
                                                                    ? this.state.allOnlyAdminList.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, async () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                    let resultUserList = await getAdminwiseUserDropDown(
                                                                        e.value
                                                                    );
                                                                    let tempUserArray =
                                                                        resultUserList &&
                                                                        resultUserList.data &&
                                                                        resultUserList.data.map((obj) => ({
                                                                            id: `${obj.pkuserId}`,
                                                                            value: `${obj.username}`,
                                                                            label: `${obj.username}`,
                                                                        }));
                                                                    this.setState({ allDataUser: tempUserArray });
                                                                });
                                                            }}
                                                            options={this.state.allOnlyAdminList}
                                                        />
                                                    </div>
                                                    <div>
                                                        <label>Select User</label>
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
                                                </>
                                            ) : this.state.selectedOrgId === null && this.state.selectedAdminId !== null && this.state.selectedUserId === null ? (
                                                <>
                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allOnlyAdminList
                                                                    ? this.state.allOnlyAdminList.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, async () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                    let resultUserList = await getAdminwiseUserDropDown(
                                                                        e.value
                                                                    );
                                                                    let tempUserArray =
                                                                        resultUserList &&
                                                                        resultUserList.data &&
                                                                        resultUserList.data.map((obj) => ({
                                                                            id: `${obj.pkuserId}`,
                                                                            value: `${obj.username}`,
                                                                            label: `${obj.username}`,
                                                                        }));
                                                                    this.setState({ allDataUser: tempUserArray });
                                                                });
                                                            }}
                                                            options={this.state.allOnlyAdminList}
                                                        />
                                                    </div>
                                                    <div>
                                                        <label>Select User</label>
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
                                                </>) : (<>
                                                    <div>
                                                        <label>Select Organization</label>
                                                        <Select
                                                            value={
                                                                this.state.allOrgnizationData
                                                                    ? this.state.allOrgnizationData.find(
                                                                        (option) => option.value === values.organization
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("organization", e.value);
                                                                this.setState(
                                                                    { selectedOrg: e.value, selectedOrgId: e.OrgId },
                                                                    async () => {
                                                                        console.log("selected Organization", this.state.selectedOrg);
                                                                        console.log("selected Organization", this.state.selectedOrgId);
                                                                        let token = localStorage.getItem("token");
                                                                        axios
                                                                            .get(
                                                                                `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=BLE`,
                                                                                {
                                                                                    headers: { Authorization: `Bearer ${token}` },
                                                                                }
                                                                            )
                                                                            .then((result) => {
                                                                                console.log("Admin Name", result);
                                                                                console.log("this.state.optionUser", this.state.optionUser);
                                                                                if (result && result.data && result.data.length !== 0) {
                                                                                    let tempAdminArray = [];
                                                                                    result.data.map((obj) => {
                                                                                        let tempAdminObj = {
                                                                                            id: `${obj.pkuserId}`,
                                                                                            value: `${obj}`,
                                                                                            label: `${obj}`,
                                                                                        };
                                                                                        tempAdminArray.push(tempAdminObj);
                                                                                    });
                                                                                    this.setState({ allDataAdmin: tempAdminArray }, () => {
                                                                                        console.log("allAdmin List", this.state.allDataAdmin);
                                                                                    });
                                                                                } else {
                                                                                    swal(
                                                                                        "Sorry",
                                                                                        "Admin is not present. Please create admin first",
                                                                                        "warning"
                                                                                    );
                                                                                    this.setState({ allDataAdmin: [] });
                                                                                }
                                                                                return Promise.resolve();
                                                                            })
                                                                            .catch((result) => {
                                                                                swal("Failed", "Somthing went wrong", "error");
                                                                            });
                                                                    }
                                                                );


                                                            }}
                                                            // onChange={this.changeOrgName}
                                                            options={this.state.allOrgnizationData}
                                                        />
                                                    </div>

                                                    <div>
                                                        <label>Select Admin</label>
                                                        <Select
                                                            value={
                                                                this.state.allDataAdmin
                                                                    ? this.state.allDataAdmin.find(
                                                                        (option) => option.value === values.admin
                                                                    )
                                                                    : ""
                                                            }
                                                            onChange={(e) => {
                                                                setFieldValue("admin", e.value);
                                                                this.setState({ selectedAdmin: e.value, selectedAdminId: e.AdminId }, async () => {
                                                                    console.log("selectedAdminId", this.state.selectedAdminId)
                                                                    let resultUserList = await getAdminwiseUserDropDown(
                                                                        e.value
                                                                    );
                                                                    let tempUserArray =
                                                                        resultUserList &&
                                                                        resultUserList.data &&
                                                                        resultUserList.data.map((obj) => ({
                                                                            id: `${obj.pkuserId}`,
                                                                            value: `${obj.username}`,
                                                                            label: `${obj.username}`,
                                                                        }));
                                                                    this.setState({ allDataUser: tempUserArray });

                                                                });
                                                            }}
                                                            options={this.state.allDataAdmin}
                                                        />
                                                    </div>
                                                    <div>
                                                        <label>Select User</label>
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
                                                </>)}

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
