import React, { Component } from "react";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import moment from "moment";
import { Link, NavLink } from "react-router-dom";
import { getDeviceUser } from "../../Service/getDeviceUser";
import { getAdmin } from "../../Service/getAdmin";
import { getOrgnization } from "../../Service/getOrgnization";
import "./AssetTags.css";
import Select from "react-select";
import { getEditTag } from "../../Service/getEditTag";
import { putTag } from "../../Service/putTag";
import { getAssetCategory } from "../../Service/getAssetCategory";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import { getTagValidation } from "../../Service/getTagValidation";
import { getGpsAdmin } from '../../Service/getGpsAdmin'
import { getGpsDeviceUser } from '../../Service/getGpsDeviceUser'
import "@progress/kendo-date-math/tz/all";
import swal from "sweetalert";

import "moment-timezone";

const SignupSchema = Yup.object().shape({
  assetTag: Yup.string().required("Enter AssetTag Name"),
  assetUniqueCode: Yup.string().required("Enter Asset Unique Code"),
});
const timezones = timezoneNames()
  .filter((l) => l.indexOf("/") > -1)
  .sort((a, b) => a.localeCompare(b));
export default class UpdateOrgAssetTag extends Component {
  state = {
    timezone: "Asia/Kolkata",
    result: [],
    allDataUser: [],
    allDataAdmin: [],
    allOrgnizationData: [],
    id: 0,
    allCategory: [],
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
    macId: "",
    category: "",
    allGpsAdminList: [],
    allGpsUserData:[],
    createdBy:
      localStorage.getItem("username") + " / " + localStorage.getItem("role"),
    listforvalidation: [],
  };
  componentDidMount = async () => {
    try {
      setTimeout(async () => {
        let role = localStorage.getItem("role");
        this.setState({ Role: role, category: localStorage.getItem("categoryname") });
        this.setState({ loader: true });
        if (
          this.props.location &&
          this.props.location.state &&
          this.props.location.state.deviceId
        ) {
          const propId =
            this.props.location &&
            this.props.location.state &&
            this.props.location.state.deviceId;

          let result = await getEditTag(propId);
          console.log("result", result);
          let resultcategory = await getAssetCategory();
          console.log("resultcategory", resultcategory);

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
                UserId: `${obj.pkuserId}`,
                // value: `${obj.firstName + " " + obj.lastName}`,
                // label: `${obj.firstName + " " + obj.lastName}`
                value: `${obj.username}`,
                label: `${obj.username}`
              }
              console.log('tempGpsUserObj', tempGpsUserObj);
              tempGpsArray.push(tempGpsUserObj);
            })
          let resultAdmin = await getAdmin();
          console.log("resultAdmin", resultAdmin);
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
          let tempCategoryArray = [];
          resultcategory &&
            resultcategory.data &&
            resultcategory.data.map((obj) => {
              console.log("objresultcategory", obj);
              let tempCategoryObj = {
                id: `${obj.pkCatId}`,
                value: `${obj.categoryName}`,
                label: `${obj.categoryName}`,
              };
              console.log("tempCategoryObj", tempCategoryObj);
              tempCategoryArray.push(tempCategoryObj);
            });
          let resultGpsAdmin = await getGpsAdmin();
          console.log('admin', resultGpsAdmin)
          let tempGpsAdminArray = []
          resultGpsAdmin && resultGpsAdmin.data && resultGpsAdmin.data.map((obj) => {
            //  console.log('resultAdmin', obj);
            let tempgpsadminObj = {
              AdminId: `${obj.pkuserId}`,
              value: `${obj.username}`,
              label: `${obj.username}`
            }

            tempGpsAdminArray.push(tempgpsadminObj)
          })
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

          this.setState(
            {
              allDataUser: tempArray,
              allDataAdmin: tempAdminArray,
              result: result && result.data,
              id: propId,
              loader: false,
              allCategory: tempCategoryArray,
              allOrgnizationData: tempOrgArray,
              allGpsAdminList: tempGpsAdminArray,
              allGpsUserData: tempGpsArray
            },
            () => {
              console.log("user", this.state.allDataUser);
              console.log("admin...", this.state.allDataAdmin);
              console.log("allOrgnization...", this.state.allOrgnizationData);
              console.log("Gps admin...", this.state.allGpsAdminList);
              console.log("Gps user...", this.state.allGpsUserData);
              this.setState({ selectedUserId: this.state.result[0].fkUserId }, () => {
                console.log("selectedUserId", this.state.selectedUserId);
              })
              this.setState({ selectedAdminId: this.state.result[0].fkAdminId }, () => {
                console.log("selectedAdminId", this.state.selectedAdminId);
              })
              this.setState({
                macId: this.state.result[0].assetUniqueCodeMacId,
              });
            }
          );
        } else if (
          this.props.match &&
          this.props.match.params &&
          this.props.match.params.id
        ) {
          const propParamId =
            this.props.match &&
            this.props.match.params &&
            this.props.match.params.id;
          let result = await getEditTag(propParamId);
          let resultUser = await getDeviceUser();
          let resultcategory = await getAssetCategory();
          console.log("resultcategory", resultcategory);
          console.log("ress2", result);
          this.setState({
            timezone: result.data[0].deviceTimeZone,
            admin: result.data.admin,
          });
          console.log("ress1", result.data[0].deviceTimeZone);

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
          let tempCategoryArray = [];
          resultcategory &&
            resultcategory.data &&
            resultcategory.data.map((obj) => {
              console.log("objresultcategory", obj);
              let tempCategoryObj = {
                id: `${obj.pkCatId}`,
                value: `${obj.categoryName}`,
                label: `${obj.categoryName}`,
              };
              console.log("tempCategoryObj", tempCategoryObj);

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
            let resultGpsUser = await getGpsDeviceUser();
            console.log('Gps UserResult', resultGpsUser);
            let tempGpsArray = []
            resultGpsUser && resultGpsUser.data && resultGpsUser.data.map((obj) => {
              let tempGpsUserObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`
              }
              console.log('tempGpsUserObj', tempGpsUserObj);
              tempGpsArray.push(tempGpsUserObj);
            })
          this.setState({
            allDataUser: tempArray,
            allDataAdmin: tempAdminArray,
            result: result && result.data,
            id: propParamId,
            loader: false,
            allCategory: tempCategoryArray,
            allOrgnizationData: tempOrgArray,
            allGpsAdminList: tempGpsAdminArray,
            allGpsUserData: tempGpsArray
          });
          console.log("AllGpsAdmin", this.state.allGpsAdminList);
          console.log("AllOrgnization", this.state.allOrgnizationData);
          console.log("Gps user...", this.state.allGpsUserData);
        } 
        // else {
        //   this.props.history.push("/asset-tag");
        // }
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
                let fkUserId = await localStorage.getItem("fkUserId");
                const payload = {
                  assetTagName: values.assetTag,
                  user: values.user,
                  admin: values.admin,
                  fkUserId: this.state.selectedUserId,
                  fkAdminId: this.state.selectedAdminId,
                  organization: values.organization,
                  fkOrganizationId: fkUserId,
                  //fkOrganizationId: this.state.selectedOrgId,
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
                    swal(
                      "Oops!",
                      "This Tag name is already exist",
                      "warning"
                    );
                  } else {
                    if (result.status === 200) {
                      swal("Great", "Data updated successfully", "success");
                      //this.props.history.push("/asset-tag")
                      if (this.state.category === "BLE") {
                        this.props.history.push("/asset-tag");
                      }
                      else if (this.state.category === "GPS") {
                        this.props.history.push("/asset-gps");
                      }
                    } else {
                      swal(
                        "Failed",
                        "Something went wrong please check your internet",
                        "error"
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
                        onChange={(e) => {
                          this.setState({ macId: e.target.value });
                        }}
                      />
                      {errors.assetUniqueCode && touched.assetUniqueCode ? (
                        <div className="error__msg">
                          {errors.assetUniqueCode}
                        </div>
                      ) : null}
                    </div>
                    <div>
                      <label>Wakeup Time</label>
                      <Field
                        className="form-control"
                        type="datetime-local"
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
                        value={this.state.timezone}>
                        {timezones.map((zone, i) => (
                          <option key={i}>{zone}</option>
                        ))}
                      </select>
                      {moment().tz(this.state.timezone).format()}
                    </div>
                    {this.state.selectedCategory === "BLE" ? (
                      <>
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
