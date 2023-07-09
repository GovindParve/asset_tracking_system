import React, { Component } from "react";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import moment from "moment";
import { Link, NavLink } from "react-router-dom";
import { getDeviceUser } from "../../Service/getDeviceUser";
import { getGpsDeviceUser } from '../../Service/getGpsDeviceUser'
import { getAdmin } from "../../Service/getAdmin";
import { getGpsAdmin } from '../../Service/getGpsAdmin'
import { getOrgnization } from "../../Service/getOrgnization";
import { getGpsOrgnization } from "../../Service/getGpsOrgnization";
import "./AssetTags.css";
import Select from "react-select";
import { getEditTag } from "../../Service/getEditTag";
import { putTag } from "../../Service/putTag";
import { getAssetCategory } from "../../Service/getAssetCategory";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import { getTagValidation } from "../../Service/getTagValidation";
import { getOnlyAdminDrowpdownList } from "../../Service/getOnlyAdminDrowpdownList";
import { getOnlyGpsAdminDrowpdownList } from "../../Service/getOnlyGpsAdminDrowpdownList";
import { getAdminwiseUserDropDown } from "../../Service/getAdminwiseUserDropDown";
import "@progress/kendo-date-math/tz/all";
import axios from '../../utils/axiosInstance';
import swal from "sweetalert";

import "moment-timezone";

const SignupSchema = Yup.object().shape({
  assetTag: Yup.string().required("Enter AssetTag Name"),
  assetUniqueCode: Yup.string().required("Enter Asset Unique Code"),
});
const timezones = timezoneNames()
  .filter((l) => l.indexOf("/") > -1)
  .sort((a, b) => a.localeCompare(b));
export default class UpdateAssetTag extends Component {
  state = {
    timezone: "Asia/Kolkata",
    result: [],
    allDataUser: [],
    allDataAdmin: [],
    
    allOrgnizationData: [],
    id: 0,
    allCategory: [],
    allOnlyAdminList: [],
    selectedCategory: "",
    selectedCategoryId: "",
    selectedUserId: "",
    selectedUser: "",
    selectedAdminId: "",
    selectedAdmin: "",
    selectedOrgId: "",
    selectedOrg: "",
    loader: false,
    Role: "",
    admin: "",
    macId: this.props.location.state.macId,
    createdBy:
      localStorage.getItem("username") + " / " + localStorage.getItem("role"),
    listforvalidation: [],
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
    category: "",
    allAdminList: [],
    allGpsAdminList: [],
    allGpsOrgnizationData: [],
    allOnlyGpsAdminList: [],
    allGpsUserData: []
  };
  componentDidMount = async () => {
    try {
      setTimeout(async () => {
        let role = localStorage.getItem("role");
        this.setState({ Role: role, category: localStorage.getItem("categoryname") });
        this.setState({ loader: true });
        if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
          const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;
          let result = await getEditTag(propId);
          console.log("result", result);
          this.setState({
            timezone: result.data[0].timeZone,
            selectedCategory: result.data[0].assetTagCategory,
            admin: result.data[0].admin,
          });
          console.log("ress1", this.state.timezone);
          console.log("ress2", result);
          let resultUser = await getDeviceUser();
          console.log("resultUser", resultUser);
          let tempArray = [];
          resultUser && resultUser.data && resultUser.data.map((obj) => {
            let tempObj = {
              UserId: `${obj.pkuserId}`,
              value: `${obj.username}`,
              label: `${obj.username}`,
            };
            tempArray.push(tempObj);
          });
          let resultGpsUser = await getGpsDeviceUser();
          console.log('UserResult', resultGpsUser);
          let tempGpsArray = []
          resultGpsUser && resultGpsUser.data && resultGpsUser.data.map((obj) => {
            console.log('objresult', obj);
            let tempGpsUserObj = {
              id: `${obj.pkuserId}`,
              // value: `${obj.firstName + " " + obj.lastName}`,
              // label: `${obj.firstName + " " + obj.lastName}`
              value: `${obj.username}`,
              label: `${obj.username}`
            }
            tempGpsArray.push(tempGpsUserObj);
          })
          let resultcategory = await getAssetCategory();
          console.log("resultcategory", resultcategory);
          let tempCategoryArray = [];
          resultcategory && resultcategory.data && resultcategory.data.map((obj) => {
            let tempCategoryObj = {
              id: `${obj.pkCatId}`,
              value: `${obj.categoryName}`,
              label: `${obj.categoryName}`,
            };
            tempCategoryArray.push(tempCategoryObj);
          });

          let resultOrg = await getOrgnization();
          console.log("Orgnization", resultOrg);
          let tempOrgArray = [];
          resultOrg &&
            resultOrg.data &&
            resultOrg.data.map((obj) => {
              //  console.log('resultAdmin', obj);
              let tempOrgObj = {
                OrgId: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`,
              };
              tempOrgArray.push(tempOrgObj);
            });
          let resultGpsOrg = await getGpsOrgnization();
          console.log('Orgnization', resultGpsOrg)
          let tempGpsOrgArray = []
          resultGpsOrg && resultGpsOrg.data && resultGpsOrg.data.map((obj) => {
            //  console.log('resultAdmin', obj);
            let tempGpsOrgObj = {
              id: `${obj.pkuserId}`,
              value: `${obj.username}`,
              label: `${obj.username}`
            }

            tempGpsOrgArray.push(tempGpsOrgObj)
          })
          this.setState(
            {
              allDataUser: tempArray,
              result: result && result.data,
              id: propId,
              loader: false,
              allCategory: tempCategoryArray,
              allOrgnizationData: tempOrgArray,
              allGpsOrgnizationData: tempGpsOrgArray,
              allGpsUserData: tempGpsArray
            },
            () => {
              console.log("user", this.state.allDataUser);
              console.log("admin...", this.state.allDataAdmin);
              console.log("allOrgnization...", this.state.allOrgnizationData);
              this.setState({ selectedUserId: this.state.result[0].fkUserId }, () => {
                console.log("selectedUserId", this.state.selectedUserId);
              })
              this.setState({ selectedAdminId: this.state.result[0].fkAdminId }, () => {
                console.log("selectedAdminId", this.state.selectedAdminId);
              })
              this.setState({ selectedOrgId: this.state.result[0].fkOrganizationId }, async () => {
                console.log("selectedOrgId", this.state.selectedOrgId);
                if (this.state.category === "BLE") {
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
                        } 
                        // else {
                        //   swal("Sorry", "Data is not present", "warning");
                        // }
                        return Promise.resolve();
                      })
                      .catch((result) => {
                        swal("Failed", "Somthing went wrong", "error");
                      });

                  } else {
                    let resultAdmin = await getOnlyAdminDrowpdownList();
                    console.log('resultAdmin', resultAdmin)
                    let tempOnlyAdminArray = []
                    resultAdmin && resultAdmin.data && resultAdmin.data.map((obj) => {
                      console.log('resultAdmin', obj);
                      let tempadminObj = {
                        AdminId: `${obj.pkuserId}`,
                        value: `${obj.username}`,
                        label: `${obj.username}`
                      }
                      tempOnlyAdminArray.push(tempadminObj)
                    })
                    this.setState({ allOnlyAdminList: tempOnlyAdminArray }, () => {
                      console.log("Alladmin", this.state.allOnlyAdminList);
                    });
                  }
                }
                else {
                  if (this.state.selectedOrgId !== null) {
                    let token = localStorage.getItem("token");
                    axios
                      .get(
                        `user/get-admin_list?fkUserId=${this.state.selectedOrgId}&role=organization&category=GPS`,
                        {
                          headers: { Authorization: `Bearer ${token}` },
                        }
                      )
                      .then((result) => {
                        console.log("GPS Admin Name", result);
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
                          this.setState({ allGpsAdminList: tempAdminArray }, () => {
                            console.log("allAdmin List", this.state.allGpsAdminList);
                          });
                        } 
                        // else {
                        //   swal("Sorry", "Data is not present", "warning");
                        // }
                        return Promise.resolve();
                      })
                      .catch((result) => {
                        swal("Failed", "Somthing went wrong", "error");
                      });

                  } else {
                    let resultAdmin = await getOnlyGpsAdminDrowpdownList();
                    console.log('resultAdmin', resultAdmin)
                    let tempOnlyAdminArray = []
                    resultAdmin && resultAdmin.data && resultAdmin.data.map((obj) => {
                      console.log('resultAdmin', obj);
                      let tempadminObj = {
                        AdminId: `${obj.pkuserId}`,
                        value: `${obj.username}`,
                        label: `${obj.username}`
                      }
                      tempOnlyAdminArray.push(tempadminObj)
                    })
                    this.setState({ allOnlyGpsAdminList: tempOnlyAdminArray }, () => {
                      console.log("Alladmin", this.state.allOnlyGpsAdminList);
                    });
                  }
                }
              })
            }
          );
        } else if (this.props.match && this.props.match.params && this.props.match.params.id) {
          const propParamId = this.props.match && this.props.match.params && this.props.match.params.id;
          let result = await getEditTag(propParamId);
          console.log("ress2", result);
          this.setState({ timezone: result.data[0].deviceTimeZone, admin: result.data.admin });
          console.log("ress1", result.data[0].deviceTimeZone);
          let resultUser = await getDeviceUser();
          let tempArray = [];
          resultUser &&
            resultUser.data &&
            resultUser.data.map((obj) => {
              let tempObj = {
                UserId: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`,
              };
              tempArray.push(tempObj);
            });
          let resultGpsUser = await getGpsDeviceUser();
          console.log('UserResult', resultGpsUser);
          let tempGpsArray = []
          resultGpsUser && resultGpsUser.data && resultGpsUser.data.map((obj) => {
            console.log('objresult', obj);
            let tempGpsUserObj = {
              id: `${obj.pkuserId}`,
              // value: `${obj.firstName + " " + obj.lastName}`,
              // label: `${obj.firstName + " " + obj.lastName}`
              value: `${obj.username}`,
              label: `${obj.username}`
            }
            console.log('tempGpsUserObj', tempGpsUserObj);
            tempGpsArray.push(tempGpsUserObj);
          })
          let resultAdmin = await getAdmin();
          let tempAdminArray = [];
          resultAdmin &&
            resultAdmin.data &&
            resultAdmin.data.map((obj) => {
              let tempAdminObj = {
                AdminId: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`,
              };
              tempAdminArray.push(tempAdminObj);
            });
          let resultGpsAdmin = await getGpsAdmin();
          console.log('admin', resultGpsAdmin)
          let tempGpsAdminArray = []
          resultGpsAdmin && resultGpsAdmin.data && resultGpsAdmin.data.map((obj) => {
            //  console.log('resultAdmin', obj);
            let tempgpsadminObj = {
              id: `${obj.pkuserId}`,
              value: `${obj.username}`,
              label: `${obj.username}`
            }
            tempGpsAdminArray.push(tempgpsadminObj)
          })
          let resultcategory = await getAssetCategory();
          console.log("resultcategory", resultcategory);
          let tempCategoryArray = [];
          resultcategory &&
            resultcategory.data &&
            resultcategory.data.map((obj) => {
              let tempCategoryObj = {
                id: `${obj.pkCatId}`,
                value: `${obj.categoryName}`,
                label: `${obj.categoryName}`,
              };
              tempCategoryArray.push(tempCategoryObj);
            });
          let resultOrg = await getOrgnization();
          console.log("Orgnization", resultOrg);
          let tempOrgArray = [];
          resultOrg &&
            resultOrg.data &&
            resultOrg.data.map((obj) => {
              let tempOrgObj = {
                OrgId: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`,
              };
              tempOrgArray.push(tempOrgObj);
            });
          let resultGpsOrg = await getGpsOrgnization();
          console.log('Orgnization', resultGpsOrg)
          let tempGpsOrgArray = []
          resultGpsOrg && resultGpsOrg.data && resultGpsOrg.data.map((obj) => {
            //  console.log('resultAdmin', obj);
            let tempGpsOrgObj = {
              id: `${obj.pkuserId}`,
              value: `${obj.username}`,
              label: `${obj.username}`
            }
            tempGpsOrgArray.push(tempGpsOrgObj)
          })
          this.setState({
            allDataUser: tempArray,
            allDataAdmin: tempAdminArray,
            result: result && result.data,
            id: propParamId,
            loader: false,
            allCategory: tempCategoryArray,
            allOrgnizationData: tempOrgArray,
            allGpsUserData: tempGpsArray,
            allGpsAdminList: tempGpsAdminArray,
            allGpsOrgnizationData: tempGpsOrgArray
          });
          console.log("AllOrgnization", this.state.allOrgnizationData);
          console.log("AllGpsOrgnization", this.state.allGpsOrgnizationData);
        } else {
          this.props.history.push("/asset-tag");
        }
        let resultTag = await getTagValidation();
        this.setState({ listforvalidation: resultTag });
      }, 200);
    } catch (error) {
      console.log(error);
      this.setState({ loader: false });
    }
  };

  handleTimezoneChange = (event) => {
    this.setState({ timezone: event.target.value });
  };
  changeCategory = (selectedCategory) => {
    this.setState({ selectedCategory: selectedCategory.target.value }, () => {
      console.log("pkCategoryid", this.state.selectedCategory);
    });
  };
  changeUser = (selectedUser) => {
    this.setState(
      { selectedUser: selectedUser.value, selectedUserId: selectedUser.id },
      () => {
        console.log("Users", this.state.selectedUser);
        console.log("selectedUserId", this.state.selectedUserId);
      }
    );
  };

  changeAdmin = (selectedAdmin) => {
    console.log("selectedAdmin", selectedAdmin);
    this.setState(
      { selectedAdmin: selectedAdmin.value, selectedAdminId: selectedAdmin.id },
      () => {
        console.log("admin", this.state.selectedAdmin);
        console.log("adminid", this.state.selectedAdminId);
      }
    );
  };
  changeOrgnization = (selectedOrg) => {
    console.log("selectedOrgnization", selectedOrg);
    this.setState(
      { selectedOrg: selectedOrg.value, selectedOrgId: selectedOrg.id },
      () => {
        console.log("Orgnization", this.state.selectedOrg);
        console.log("Orgnizationid", this.state.selectedOrgId);
      }
    );
  };


  render() {
    console.log(
      "initialValues",
      this.state && this.state.result[0] && this.state.result[0].wakeupTime
    );
    console.log(this.state.allDataUser);
    console.log(this.state.allOrgnizationData);
    const { loader } = this.state;
    return (
      <>
        {loader ? (
          <div class="loader"></div>
        ) : (
          <div className="userAdd__wrapper">
            <h3>Update Asset Tag</h3>
            <br />
            <br />
            <Formik
              enableReinitialize={true}
              initialValues={{
                assetTag: this.state && this.state.result[0] && this.state.result[0].assetTagName,
                assetUniqueCode: this.state && this.state.result[0] && this.state.result[0].assetUniqueCodeMacId,
                sim: this.state && this.state.result[0] && this.state.result[0].assetSimNumber,
                imsi: this.state && this.state.result[0] && this.state.result[0].assetIMSINumber,
                imei: this.state && this.state.result[0] && this.state.result[0].assetIMEINumber,
                AssetTagType: this.state && this.state.result[0] && this.state.result[0].assetTagCategory,
                barcode: this.state && this.state.result[0] && this.state.result[0].assetBarcodeSerialNumber,
                wakeupTime: this.state && this.state.result[0] && this.state.result[0].wakeupTime,
                timezone: this.state && this.state.result[0] && this.state.result[0].timeZone,
                datetime: this.state && this.state.result[0] && this.state.result[0].dateTime,
                user: this.state && this.state.result[0] && this.state.result[0].user,
                admin: this.state && this.state.result[0] && this.state.result[0].admin,
                organization: this.state && this.state.result[0] && this.state.result[0].organization,
                status: this.state && this.state.result[0] && this.state.result[0].status,
                createdBy: this.state && this.state.result[0] && this.state.result[0].createdBy,
              }}
              validationSchema={SignupSchema}
              onSubmit={async (values) => {
                // const resultDate = moment(values.datetime).format("YYYY-MM-DDTHH:mm:ss.SSS[Z]");
                const resultDate = moment()
                  .tz(this.state.timezone)
                  .format("YYYY-MM-DDTHH:mm:ss.SSS[Z]");
                // const wakeTime = values.wakeupTime + ":00";
                let UserData = this.state.allDataUser;
                let fkUserId = await localStorage.getItem("fkUserId");
                const payload = {
                  assetTagName: values.assetTag,
                  user: values.user,
                  admin: values.admin,
                  fkUserId: this.state.selectedUserId,
                  fkAdminId: this.state.selectedAdminId,
                  organization: values.organization,
                  //fkOrganizationId:fkUserId,
                  fkOrganizationId: this.state.selectedOrgId,
                  //wakeupTime: moment().tz(values.wakeupTime).format("YYYY-MM-DD HH:mm:ss"),
                  wakeupTime: values.wakeupTime,
                  timeZone: this.state.timezone,
                  dateTime: resultDate,
                  assetUniqueCodeMacId: values.assetUniqueCode,
                  assetSimNumber: values.sim,
                  assetIMSINumber: values.imsi,
                  assetIMEINumber: values.imei,
                  assetTagId: this.state.id,
                  assetBarcodeSerialNumber: values.barcode,
                  status: values.status,
                  assetTagCategory: values.AssetTagType,
                  createdBy: values.createdBy,
                };

                console.log("Updated data------>", payload);
                let result = await putTag(payload);
                console.log("payload", payload);
                if (
                  this.state.listforvalidation &&
                  this.state.listforvalidation.data &&
                  this.state.listforvalidation.data.length !== 0
                ) {
                  let tempUser = this.state.listforvalidation.data.filter((obj) => {
                    return (
                      obj.assetTagName !== this.state.result[0].assetTagName
                    );
                  });
                  let tempTag = tempUser.filter((obj) => {
                    return (
                      obj.assetTagName === values.assetTag
                    );
                  });
                  console.log("tempTag", tempTag);
                  if (tempTag.length !== 0) {
                    swal("Oops!", "This Tag name is already exist", "warning");
                  } else {
                    if (result.status === 200) {
                      swal("Great", "Data updated successfully", "success");
                      this.props.history.push("/asset-tag")
                    } else {
                      swal("Failed", "Something went wrong please check your internet", "error"
                      );
                    }
                  }
                }
              }}
            >
              {({ errors, touched, setFieldValue, values }) => (
                <Form>
                  <div className="tagUpdate_form">
                    <div>
                      <label>Asset Tag Name</label>
                      <Field
                        className="form-control"
                        name="assetTag"
                        placeholder="Enter Asset Tag Name"
                        validate={this.validateAssetTag}
                      />
                      {errors.assetTag && touched.assetTag ? (
                        <div className="error__msg">{errors.assetTag}</div>
                      ) : null}
                    </div>
                    <div>
                      <label>Select Asset Gateway Category Name</label>
                      <Field
                        className="form-control"
                        name="AssetTagType"
                        readOnly="true"
                      />
                      {errors.AssetTagType && touched.AssetTagType ? (
                        <div className="error__msg">{errors.AssetTagType}</div>
                      ) : null}
                    </div>
                    <div>
                      <label>Asset Tag Barcode No. / Serial No.</label>
                      <Field
                        className="form-control"
                        name="barcode"
                        placeholder="Enter Barcode No./ Serial No."
                        validate={this.validateBarcode}
                      />
                      {errors.barcode && touched.barcode ? (
                        <div className="error__msg">{errors.barcode}</div>
                      ) : null}
                    </div>
                    <div>
                      <label>Asset Tag Unique Code No. / Mac_Id</label>
                      <Field
                        className="form-control"
                        name="assetUniqueCode"
                        placeholder="Enter Asset Unique Code/Mac_id"
                        validate={this.validateMac}
                      />
                      {errors.assetUniqueCode && touched.assetUniqueCode ? (
                        <div className="error__msg">
                          {errors.assetUniqueCode}
                        </div>
                      ) : null}
                    </div>
                    <div>
                      <label>Wakeup Time</label>
                      <Field className="form-control" type="datetime-local"
                        step="1"
                        name="wakeupTime"
                      />
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
                      {moment().tz(this.state.timezone).format()}
                    </div>
                    {this.state.selectedCategory === "BLE" ? (
                      <>
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
                                    });
                                }}
                                options={this.state.allOrgnizationData} />
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
                      </>
                    ) : this.state.selectedCategory === "GPS" ? (
                      <>
                        <div>
                          <label>Sim No.</label>
                          <Field
                            className="form-control"
                            name="sim"
                            placeholder="Enter SIM No."
                          />
                          {errors.sim && touched.sim ? (
                            <div className="error__msg">{errors.sim}</div>
                          ) : null}
                        </div>
                        <div>
                          <label>IMEI No.</label>
                          <Field
                            className="form-control"
                            name="imei"
                            placeholder="Enter IMEI No."
                          />
                          {errors.imei && touched.imei ? (
                            <div className="error__msg">{errors.imei}</div>
                          ) : null}
                        </div>
                        <div>
                          <label>IMSI No.</label>
                          <Field
                            className="form-control"
                            name="imsi"
                            placeholder="Enter IMSI No."
                          />
                          {errors.imsi && touched.imsi ? (
                            <div className="error__msg">{errors.imsi}</div>
                          ) : null}
                        </div>
                        {this.state.selectedOrgId !== null && this.state.selectedAdminId !== null && this.state.selectedUserId === null ? (
                          <>
                            <div>
                              <label>Select Organization</label>
                              <Select
                                value={
                                  this.state.allGpsOrgnizationData
                                    ? this.state.allGpsOrgnizationData.find(
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
                                          `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=GPS`,
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
                                            this.setState({ allGpsAdminList: tempAdminArray }, () => {
                                              console.log("allAdmin Gps List", this.state.allGpsAdminList);
                                            });
                                          } else {
                                            swal(
                                              "Sorry",
                                              "Admin is not present. Please create admin first",
                                              "warning"
                                            );
                                            this.setState({ allGpsAdminList: [] });
                                          }
                                          return Promise.resolve();
                                        })
                                        .catch((result) => {
                                          swal("Failed", "Somthing went wrong", "error");
                                        });
                                    }
                                  );
                                }}
                                options={this.state.allGpsOrgnizationData}
                              />
                            </div>

                            <div>
                              <label>Select Admin</label>
                              <Select
                                value={
                                  this.state.allGpsAdminList
                                    ? this.state.allGpsAdminList.find(
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
                                options={this.state.allGpsAdminList}
                              />
                            </div>
                          </>
                        ) : this.state.selectedOrgId === null && this.state.selectedAdminId !== null && this.state.selectedUserId !== null ? (
                          <>
                            <div>
                              <label>Select Admin</label>
                              <Select
                                value={
                                  this.state.allOnlyGpsAdminList
                                    ? this.state.allOnlyGpsAdminList.find(
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
                                    this.setState({ allGpsUserData: tempUserArray });
                                  });
                                }}
                                options={this.state.allOnlyGpsAdminList}
                              />
                            </div>
                            <div>
                              <label>Select User</label>
                              <Select
                                value={
                                  this.state.allGpsUserData
                                    ? this.state.allGpsUserData.find(
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
                                options={this.state.allGpsUserData} />
                            </div>
                          </>
                        ) : this.state.selectedOrgId === null && this.state.selectedAdminId !== null && this.state.selectedUserId === null ? (
                          <>
                            <div>
                              <label>Select Admin</label>
                              <Select
                                value={
                                  this.state.allOnlyGpsAdminList
                                    ? this.state.allOnlyGpsAdminList.find(
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
                                    this.setState({ allGpsUserData: tempUserArray });
                                  });
                                }}
                                options={this.state.allOnlyGpsAdminList}
                              />
                            </div>
                            <div>
                              <label>Select User</label>
                              <Select
                                value={
                                  this.state.allGpsUserData
                                    ? this.state.allGpsUserData.find(
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
                                options={this.state.allGpsUserData}
                              />
                            </div>
                          </>) : (<>
                            <div>
                              <label>Select Organization</label>
                              <Select
                                value={
                                  this.state.allGpsOrgnizationData
                                    ? this.state.allGpsOrgnizationData.find(
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
                                          `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=GPS`,
                                          {
                                            headers: { Authorization: `Bearer ${token}` },
                                          }
                                        )
                                        .then((result) => {
                                          console.log("Gps Admin Name", result);
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
                                            this.setState({ allGpsAdminList: tempAdminArray }, () => {
                                              console.log("Gps allAdmin List", this.state.allGpsAdminList);
                                            });
                                          } else {
                                            swal(
                                              "Sorry",
                                              "Admin is not present. Please create admin first",
                                              "warning"
                                            );
                                            this.setState({ allGpsAdminList: [] });
                                          }
                                          return Promise.resolve();
                                        })
                                        .catch((result) => {
                                          swal("Failed", "Somthing went wrong", "error");
                                        });
                                    });
                                }}
                                options={this.state.allGpsOrgnizationData} />
                            </div>
                            <div>
                              <label>Select Admin</label>
                              <Select
                                value={
                                  this.state.allGpsAdminList
                                    ? this.state.allGpsAdminList.find(
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
                                    this.setState({ allGpsUserData: tempUserArray });
                                  });
                                }}
                                options={this.state.allGpsAdminList}
                              />
                            </div>
                            <div>
                              <label>Select User</label>
                              <Select
                                value={
                                  this.state.allGpsUserData
                                    ? this.state.allGpsUserData.find(
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
                                options={this.state.allGpsUserData}
                              />
                            </div>
                          </>)}
                      </>
                    ) : this.state.selectedCategory === "LoRaWan" ? (
                      <></>
                    ) : (
                      <>
                        <div>
                          <label>Sim No.</label>
                          <Field
                            className="form-control"
                            name="sim"
                            placeholder="Enter SIM No."
                          />
                          {errors.sim && touched.sim ? (
                            <div className="error__msg">{errors.sim}</div>
                          ) : null}
                        </div>

                        <div>
                          <label>IMSI No.</label>
                          <Field
                            className="form-control"
                            name="imsi"
                            placeholder="Enter IMSI No."
                          />
                          {errors.imsi && touched.imsi ? (
                            <div className="error__msg">{errors.imsi}</div>
                          ) : null}
                        </div>
                      </>
                    )}

                  </div>
                  <br />
                  <div className="btn-asset" align="center">
                    <button type="submit" className="btn btn-primary">
                      Submit
                    </button>
                    <button type="cancel" className="btn btn-primary">
                      {" "}
                      <Link to={{ pathname: "/asset-tag" }}>Cancel</Link>
                    </button>
                  </div>
                </Form>
              )}
            </Formik>
          </div>
        )}
      </>
    );
  }
}
