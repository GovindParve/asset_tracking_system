import React, { Component } from "react";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import moment from "moment";
import "./AssetTags.css";
import { getDeviceUser } from "../../Service/getDeviceUser";
import { getGpsDeviceUser } from '../../Service/getGpsDeviceUser'
import { getAssetCategory } from "../../Service/getAssetCategory";
import { postTag } from "../../Service/postTag";
import { getAdmin } from "../../Service/getAdmin";
import { getGpsAdmin } from '../../Service/getGpsAdmin'
import { getOrgnization } from "../../Service/getOrgnization";
import { getGpsOrgnization } from "../../Service/getGpsOrgnization";
import { getTagValidation } from "../../Service/getTagValidation";
import { getOnlyAdminDrowpdownList } from "../../Service/getOnlyAdminDrowpdownList";
import { getOnlyGpsAdminDrowpdownList } from "../../Service/getOnlyGpsAdminDrowpdownList";
import Select from "react-select";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import swal from "sweetalert";
import "moment-timezone";
import axios from "../../utils/axiosInstance";

import { getAdminwiseUserDropDown } from "../../Service/getAdminwiseUserDropDown";

import { postDevice } from "../../Service/postDevice";

const SignupSchema = Yup.object().shape({
  assetTag: Yup.string().required("Enter AssetTag Name"),
  barcode: Yup.string().required("Enter Barcode No"),
  assetUniqueCode: Yup.string()
    .min(17, "It should have maximum 12 characters!")
    .max(17, "It should have maximum 12 characters!")
    .required("Enter Asset Unique Code"),
  // imsi: Yup.string()
  //     .required('Enter IMSI No'),
  // location: Yup.string()
  //      .required('Enter Location'),
  // sim: Yup.string()
  //      .required('Enter SIM No'),
  // AssetTagType: Yup.string()
  //     .required('Select Asset Tag Type'),

  // mac_id: Yup.string()
  //      .required('Enter MAC_ID No'),
  // admin: Yup.string().required("Please Select Admin  Name"),
  // user: Yup.string()
  //     .required('Please Select User  Name'),
});

const timezones = timezoneNames()
  .filter((l) => l.indexOf("/") > -1)
  .sort((a, b) => a.localeCompare(b));
export default class CreateAssetTag extends Component {
  // interval;
  state = {
    timezone: "Asia/Kolkata",
    date: null,
    result: null,
    allData: [],
    allAdminData: [],
    allAdminList: [],
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
    SelectedCategory: "",
    status: "",
    allOnlyAdminList: [],
    allGpsAdminList: [],
    allGpsOrgnizationData: [],
    isMacId: false,
    createdBy:
      localStorage.getItem("username") + " / " + localStorage.getItem("role"),
    userType: [
      {
        label: "User",
        value: "user",
      },
      {
        label: "None",
        value: "none",
      },
    ],
    allSuperUser: [
      {
        label: "User",
        value: "user",
      },
      {
        label: "Admin",
        value: "admin",
      },
      {
        label: "Organization",
        value: "organization",
      },
    ],
    optionUser: "",
    optionAllUser: "",
    category: "",
  };

  async componentDidMount() {
    try {
      setTimeout(async () => {
        let role = localStorage.getItem("role");
      
        this.setState({ Role: role, category: localStorage.getItem("categoryname"), SelectedCategory: localStorage.getItem("SelectedCategory")});

        let result = await getDeviceUser();
        console.log("UserResult", result);
        // this.tick();
        // this.interval = setInterval(() => this.tick(), 1000);
        let tempArray = [];
        result &&
          result.data &&
          result.data.map((obj) => {
            console.log("objresult", obj);
            let tempObj = {
              id: `${obj.pkuserId}`,
              value: `${obj.username}`,
              label: `${obj.username}`,
            };
            console.log("tempObj", tempObj);
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
        this.setState({ allGpsUserData: tempGpsArray }, () => {
          console.log('AllGps User', this.state.allGpsUserData)
        });
        let resultcategory = await getAssetCategory();
        console.log("resultcategory", resultcategory);
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

        this.setState({ allData: tempArray, allCategory: tempCategoryArray });

        let resultAdmin = await getAdmin();
        console.log("admin", resultAdmin);
        let tempAdminArray = [];
        resultAdmin &&
          resultAdmin.data &&
          resultAdmin.data.map((obj) => {
            //  console.log('resultAdmin', obj);
            let tempadminObj = {
              id: `${obj.pkuserId}`,
              value: `${obj.username}`,
              label: `${obj.username}`,
            };

            tempAdminArray.push(tempadminObj);
          });

        this.setState({ allAdminData: tempAdminArray }, () => {
          console.log("Alladmin", this.state.allAdminData);
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

        this.setState({ allGpsAdminList: tempGpsAdminArray }, () => {
          console.log('AllGpsadmin', this.state.allGpsAdminList)
        })

        let resultOrg = await getOrgnization();
        console.log("Orgnization", resultOrg);
        let tempOrgArray = [];
        resultOrg &&
          resultOrg.data &&
          resultOrg.data.map((obj) => {
            //  console.log('resultAdmin', obj);
            let tempOrgObj = {
              id: `${obj.pkuserId}`,
              value: `${obj.username}`,
              label: `${obj.username}`,
            };
            tempOrgArray.push(tempOrgObj);
          });

        this.setState({ allOrgnizationData: tempOrgArray }, () => {
          console.log("AllOrgnization", this.state.allOrgnizationData);
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

        this.setState({ allGpsOrgnizationData: tempGpsOrgArray }, () => {
          console.log('AllOrgnization', this.state.allGpsOrgnizationData)
        })
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
            this.setState({ allOnlyAdminList: tempAdminArray }, () => {
              console.log("Alladmin", this.state.allOnlyAdminList);
            });
          }
        });

        await getOnlyGpsAdminDrowpdownList().then((result) => {
          console.log("Only Gps Admin List", result);
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
            this.setState({ allGpsAdminList: tempAdminArray }, () => {
              console.log("GPS Alladmin", this.state.allGpsAdminList);
            });
          }
        });

      }, 200);
    } catch (error) {
      console.log(error);
      this.setState({ loader: false });
    }
  }
  async validateAssetTag(value) {
    let errors;
    let resultTag = await getTagValidation();
    
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
  async validateBarcode(value) {
    let errors;
    let result = await getTagValidation();
  
    if (result && result.data && result.data.length !== 0) {
      let tempBar = result.data.filter((obj) => {
        return obj.assetBarcodeSerialNumber === value;
      });
      if (tempBar.length !== 0) {
        errors = "This Barcode is already exist";
      }
    }
    return errors;
  }
  async validateMac(value) {
    let errors;
    let result = await getTagValidation();
   
    if (result && result.data && result.data.length !== 0) {
      let tempMac = result.data.filter((obj) => {
        return obj.assetUniqueCodeMacId === value;
      });
      if (tempMac.length !== 0) {
        errors = "This MacId is already exist";
      }
    }
    return errors;
  }
  handleTimezoneChange = (event) => {
    this.setState({ timezone: event.target.value });
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

  changeCategory = (selectedCategory) => {
    this.setState(
      {
        selectedCategory: selectedCategory.value,
        selectedCategoryId: selectedCategory.id,
      },
      () => {
        console.log("pkCategoryid", this.state.selectedCategory);
        console.log("selectedCategoryId", this.state.selectedCategoryId);
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
              this.setState({ allAdminData: tempAdminArray }, () => {
                console.log("allAdmin List", this.state.allAdminData);
              });
            } else {
              swal("Sorry", "Data is not present", "warning");
            }
            return Promise.resolve();
          })
          .catch((result) => {
            swal("Failed", "Somthing went wrong", "error");
          });
      }
    );
  };

  changeGpsOrgName = async (selectOrg) => {
    console.log("Selected GPS Organization Name--->", selectOrg.value);
    this.setState(
      { selectedOrg: selectOrg.value, selectedOrgId: selectOrg.id },
      async () => {
        console.log("selected GPS Organization", this.state.selectedOrg);
        let token = localStorage.getItem("token");
        axios
          .get(
            `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=GPS`,
            {
              headers: { Authorization: `Bearer ${token}` },
            }
          )
          .then((result) => {
            console.log("GPS Admin Name", result);
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
                console.log("allAdmin List", this.state.allGpsAdminList);
              });
            } else {
              swal(
                "Sorry",
                "Admin is not present. Please create admin first",
                "warning"
              );
              this.setState({ allAdminList: [] });
            }
            return Promise.resolve();
          })
          .catch((result) => {
            swal("Failed", "Somthing went wrong", "error");
          });
      }
    );
  };
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
  onChangeGpsRole = (e) => {
    this.setState(
      { selectedRole: e.target.value, allGpsAdminList: [] },
      async () => {
        if (this.state.selectedRole === "withoutorganization") {
          await getOnlyGpsAdminDrowpdownList().then((result) => {
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
              this.setState({ allGpsAdminList: tempAdminArray }, () => {
                console.log("Alladmin", this.state.allGpsAdminList);
              });
            } else {
              swal(
                "Sorry",
                "Admin is not present. Please create admin first",
                "warning"
              );
              this.setState({ allGpsAdminList: [] });
            }
          });
        }
      }
    );
  };
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
            assetTag: "",
            assetUniqueCode: "",
            sim: "",
            imsi: "",
            imei: "",
            AssetTagType: "",
            barcode: "",
            wakeupTime: "",
            timezone: "",
            datetime: "",
            user: "",
            admin: "",
            organization: "",
            location: "",
            //status:'',
            // adminName: '',
            // userName: ''
          }}
          validationSchema={SignupSchema}
          onSubmit={async (values) => {
            const resultDate = moment().tz(this.state.timezone).format();
            let fkUserId = await localStorage.getItem("fkUserId");
            this.setState({ disabled: true })
            const payload = {
              assetTagName: values.assetTag,
              user: values.user,
              admin: values.admin,
              fkUserId: this.state.selectedUserId,
              fkAdminId: this.state.selectedAdminId,
              organization: this.state.selectedOrg,
              fkOrganizationId: this.state.selectedOrgId,
              wakeupTime: moment()
                .tz(values.wakeupTime)
                .format("YYYY-MM-DD HH:mm:ss"),
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
              createdBy: this.state.createdBy,
            };
            console.log("Add Tag", payload);
            let result = await postTag(payload);
            console.log("Post Tag", result);
            if (result.data === 'Asset Tag generated successfully...!' ) {
              swal("Great", "Tag Created Successfully", "success");
              //window.location.reload()
              if (this.state.SelectedCategory === "BLE") {
                this.props.history.push("/asset-tag");
              } 
              else if (this.state.SelectedCategory === "GPS") {
                this.props.history.push("/asset-gps");
              }
              //window.location.reload();
            } else if (result.data === 'Dublicate')
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
                  <label className="label">Asset Tag Name</label>
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
                  <label className="label">Select Tag Category Name</label>
                  <Select
                    options={this.state.allCategory}
                    onChange={this.changeCategory}
                    placeholder="Select Asset Tag Category Name"
                  />
                </div>
                <div>
                  <label>Wakeup Time</label>
                  <Field
                    className="form-control"
                    type="time"
                    step="1"
                    name="wakeupTime"
                  />
                </div>
                <div>
                  <label>Timezone</label>
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
                    <div>
                      <label className="label">Barcode No./ Serial No</label>
                      <Field
                        className="form-control"
                        name="barcode"
                        placeholder="Enter Barcode No./ Serial No."
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
                        />
                     
                      {errors.assetUniqueCode && touched.assetUniqueCode ? (
                        <div className="error__msg">{errors.assetUniqueCode}</div>
                      ) : null}
                    </div>

                    <div>
                      <label className="label">User Type</label>
                      <Select
                        options={this.state.allSuperUser}
                        value={
                          this.state.allSuperUser
                            ? this.state.allSuperUser.find(
                              (option) =>
                                option.value === this.state.optionAllUser
                            )
                            : ""
                        }
                        onChange={(e) => {
                          setFieldValue("type_of", e.value);
                          this.setState({ optionAllUser: e.value });
                        }}
                        placeholder="Select User Type"
                      />
                      {errors.type_of && touched.type_of ? (
                        <div className="error__msg">{errors.type_of}</div>
                      ) : null}
                    </div>
                    {this.state.optionAllUser === "user" ||
                      this.state.optionAllUser === "admin" ? (
                      <div>
                        <label className="label">Create Tag </label>
                        <div onChange={this.onChangeRole}>
                          <input
                            type="radio"
                            value="withorganization"
                            name="selectRole"
                          />
                          &nbsp;
                          <label className="label"> With Organization</label>
                          &nbsp;&nbsp;
                          <input
                            type="radio"
                            value="withoutorganization"
                            name="selectRole"
                          />
                          &nbsp;
                          <label className="label"> Without Organization</label>
                        </div>
                      </div>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "user" &&
                      this.state.selectedRole === "withorganization" ? (
                      <>
                        <div>
                          <label className="label">
                            Select Organization Name
                          </label>
                          <Select
                            options={this.state.allOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Organization"
                          />
                        </div>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allAdminData}
                            placeholder="Select Admin"
                            onChange={(value) => {
                              setFieldValue("admin", value.value);
                              this.setState({ selectedAdminId: value.id }, async () => {
                                let resultUserList = await getAdminwiseUserDropDown(value.value);
                                let tempUserArray =
                                  resultUserList &&
                                  resultUserList.data &&
                                  resultUserList.data.map((obj) => ({
                                    id: `${obj.pkuserId}`,
                                    value: `${obj.username}`,
                                    label: `${obj.username}`,
                                  }));
                                this.setState({ allData: tempUserArray });
                              });
                            }}/>                          
                        </div>
                        <div>
                          <label className="label">Select User</label>
                          <Select
                            options={this.state.allData}
                            placeholder="Select User"
                            onChange={(value) => {
                              setFieldValue("user", value.value);
                              this.setState({ selectedUserId: value.id });
                            }}/>
                        </div>
                      </>
                    ) : this.state.optionAllUser === "user" &&
                      this.state.selectedRole === "withoutorganization" ? (
                      <>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allAdminList}
                            placeholder="Select Admin"
                            onChange={(value) => {
                              setFieldValue("admin", value.value);
                              this.setState({ selectedAdminId: value.id }, async () => {
                                let resultUserList = await getAdminwiseUserDropDown(
                                  value.value
                                );
                                let tempUserArray =
                                  resultUserList &&
                                  resultUserList.data &&
                                  resultUserList.data.map((obj) => ({
                                    id: `${obj.pkuserId}`,
                                    value: `${obj.username}`,
                                    label: `${obj.username}`,
                                  }));
                                this.setState({ allData: tempUserArray });
                              });
                            }}
                          />
                          {/* {errors.admin && touched.admin ? (
                        <div className="error__msg">{errors.admin}</div>
                      ) : null} */}
                        </div>
                        <div>
                          <label className="label">Select User</label>
                          <Select
                            options={this.state.allData}
                            placeholder="Select User"
                            onChange={(value) => {
                              setFieldValue("user", value.value);
                              this.setState({ selectedUserId: value.id });
                            }}
                          />
                        </div>
                      </>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "admin" &&
                      this.state.selectedRole === "withorganization" ? (
                      <>
                        <div>
                          <label className="label">
                            Select Organization Name
                          </label>
                          <Select
                            options={this.state.allOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Organization"
                          />
                        </div>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allAdminData}
                            placeholder="Select Admin"
                            onChange={(value) => {
                              setFieldValue("admin", value.value);
                              this.setState({ selectedAdminId: value.id }
                              );
                            }}
                          />
                          {/* {errors.admin && touched.admin ? (
                        <div className="error__msg">{errors.admin}</div>
                      ) : null} */}
                        </div>
                      </>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "admin" &&
                      this.state.selectedRole === "withoutorganization" ? (
                      <div>
                        <label className="label">
                          Select Admin Name
                        </label>
                        <Select
                          options={this.state.allOnlyAdminList}
                          placeholder="Select Admin"
                          onChange={(value) => {
                            setFieldValue("admin", value.value);
                            this.setState({ selectedAdminId: value.id })
                          }}
                        />
                        {/* {errors.admin && touched.admin ? (
                        <div className="error__msg">{errors.admin}</div>
                      ) : null} */}

                      </div>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "organization"
                      ? (
                        <div>
                          <label className="label">
                            Select Organization Name
                          </label>
                          <Select
                            options={this.state.allOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Organization"
                          />
                        </div>
                      ) : (
                        ""
                      )}</>
                ) : this.state.selectedCategory === "GPS" ? (
                  <>
                     <div>
                      <label className="label">Barcode No./ Serial No</label>
                      <Field
                        className="form-control"
                        name="barcode"
                        placeholder="Enter Barcode No./ Serial No."
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
                         <span className="macHide" id="displaytable6" >{ this.state.isMacId ? 'If you dont have MACID, you can use IMEI No' : '' }
                     </span>
                      {errors.assetUniqueCode && touched.assetUniqueCode ? (
                        <div className="error__msg">{errors.assetUniqueCode}</div>
                      ) : null}

                    </div>
                    <div>
                      <label>SIM No</label>
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
                      <label>IMEI No</label>
                      <Field
                        className="form-control"
                        name="imei"
                        placeholder="Enter IMEI No."
                        validate={this.validateImei}
                      />
                      {errors.imei && touched.imei ? (
                        <div className="error__msg">{errors.imei}</div>
                      ) : null}
                    </div>
                    <div>
                      <label>IMSI No</label>
                      <Field
                        className="form-control"
                        name="imsi"
                        placeholder="Enter IMSI No."
                        validate={this.validateImsi}
                      />

                      {errors.imsi && touched.imsi ? (
                        <div className="error__msg">{errors.imsi}</div>
                      ) : null}
                    </div>
                    <div>
                      <label className="label">User Type</label>
                      <Select
                        options={this.state.allSuperUser}
                        value={
                          this.state.allSuperUser
                            ? this.state.allSuperUser.find(
                              (option) =>
                                option.value === this.state.optionAllUser
                            )
                            : ""
                        }
                        onChange={(e) => {
                          setFieldValue("type_of", e.value);
                          this.setState({ optionAllUser: e.value });
                        }}
                        placeholder="Select User Type"
                      />
                      {errors.type_of && touched.type_of ? (
                        <div className="error__msg">{errors.type_of}</div>
                      ) : null}
                    </div>
                    {this.state.optionAllUser === "user" ||
                      this.state.optionAllUser === "admin" ? (
                      <div>
                        <label className="label">Create Tag </label>
                        <div onChange={this.onChangeRole}>
                          <input
                            type="radio"
                            value="withorganization"
                            name="selectRole"
                          />
                          &nbsp;
                          <label className="label"> With Organization</label>
                          &nbsp;&nbsp;
                          <input
                            type="radio"
                            value="withoutorganization"
                            name="selectRole"
                          />
                          &nbsp;
                          <label className="label"> Without Organization</label>
                        </div>
                      </div>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "user" &&
                      this.state.selectedRole === "withorganization" ? (
                      <>
                        <div>
                          <label className="label">
                            Select Organization Name
                          </label>
                          <Select
                            options={this.state.allGpsOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Organization"
                          />
                        </div>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allGpsAdminList}
                            placeholder="Select Admin"
                            onChange={(value) => {
                              setFieldValue("admin", value.value);
                              this.setState({ selectedAdminId: value.id }, async () => {
                                let resultUserList = await getAdminwiseUserDropDown(
                                  value.value
                                );
                                let tempUserArray =
                                  resultUserList &&
                                  resultUserList.data &&
                                  resultUserList.data.map((obj) => ({
                                    id: `${obj.pkuserId}`,
                                    value: `${obj.username}`,
                                    label: `${obj.username}`,
                                  }));
                                this.setState({ allData: tempUserArray });
                              });
                            }}
                          />
                        </div>
                        <div>
                          <label className="label">Select User</label>
                          <Select
                            options={this.state.allGpsUserData}
                            placeholder="Select User"
                            onChange={(value) => {
                              setFieldValue("user", value.value);
                              this.setState({ selectedUserId: value.id });
                            }}
                          />

                        </div>
                      </>
                    ) : this.state.optionAllUser === "user" &&
                      this.state.selectedRole === "withoutorganization" ? (
                      <>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allGpsAdminList}
                            placeholder="Select Admin"
                            onChange={(value) => {
                              setFieldValue("admin", value.value);
                              this.setState({ selectedAdminId: value.id }, async () => {
                                let resultUserList = await getAdminwiseUserDropDown(
                                  value.value
                                );
                                let tempUserArray =
                                  resultUserList &&
                                  resultUserList.data &&
                                  resultUserList.data.map((obj) => ({
                                    id: `${obj.pkuserId}`,
                                    value: `${obj.username}`,
                                    label: `${obj.username}`,
                                  }));
                                this.setState({ allData: tempUserArray });
                              });
                            }}
                          />
                          {/* {errors.admin && touched.admin ? (
                        <div className="error__msg">{errors.admin}</div>
                      ) : null} */}
                        </div>
                        <div>
                          <label className="label">Select User</label>
                          <Select
                            options={this.state.allGpsUserData}
                            placeholder="Select User"
                            onChange={(value) => {
                              setFieldValue("user", value.value);
                              this.setState({ selectedUserId: value.id });
                            }}
                          />
                        </div>
                      </>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "admin" &&
                      this.state.selectedRole === "withorganization" ? (
                      <>
                        <div>
                          <label className="label">
                            Select Organization Name
                          </label>
                          <Select
                            options={this.state.allGpsOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Organization"
                          />
                        </div>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allGpsAdminList}
                            placeholder="Select Admin"
                            onChange={(value) => {
                              setFieldValue("admin", value.value);
                              this.setState({ selectedAdminId: value.id }
                              );
                            }}
                          />
                          {/* {errors.admin && touched.admin ? (
                        <div className="error__msg">{errors.admin}</div>
                      ) : null} */}
                        </div>
                      </>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "admin" &&
                      this.state.selectedRole === "withoutorganization" ? (
                      <div>
                        <label className="label">
                          Select Admin Name
                        </label>
                        <Select
                          options={this.state.allGpsAdminList}
                          placeholder="Select Admin"
                          onChange={(value) => {
                            setFieldValue("admin", value.value);
                            this.setState({ selectedAdminId: value.id })
                          }}
                        />
                        {/* {errors.admin && touched.admin ? (
                        <div className="error__msg">{errors.admin}</div>
                      ) : null} */}

                      </div>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "organization"
                      ? (
                        <div>
                          <label className="label">
                            Select Organization Name
                          </label>
                          <Select
                            options={this.state.allGpsOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Organization"
                          />
                        </div>
                      ) : (
                        ""
                      )}
                  </>
                ) : this.state.selectedCategory === "LoRaWan" ? (
                  <></>
                ) : (
                  <>
                    {/* <div>
                      <label>Sim No</label>
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
                      <label>Location</label>
                      <Field
                        className="form-control"
                        name="location"
                        placeholder="Enter Location"
                      />

                    </div>

                    <div>
                      <label>IMSI No</label>
                      <Field
                        className="form-control"
                        name="imsi"
                        placeholder="Enter IMSI No."
                        validate={this.validateImsi}
                      />
                      {errors.imsi && touched.imsi ? (
                        <div className="error__msg">{errors.imsi}</div>
                      ) : null}
                    </div>*/}
                    <div>
                      <label>Created By</label>
                      <Field
                        className="form-control"
                        name="createdBy"
                        value={
                          localStorage.getItem("username") +
                          "/" +
                          localStorage.getItem("role")
                        }
                        readOnly="true"
                      />
                    </div> 
                    <div>
                      <label className="label">User Type</label>
                      <Select
                        options={this.state.allSuperUser}
                        value={
                          this.state.allSuperUser
                            ? this.state.allSuperUser.find(
                              (option) =>
                                option.value === this.state.optionAllUser
                            )
                            : ""
                        }
                        onChange={(e) => {
                          setFieldValue("type_of", e.value);
                          this.setState({ optionAllUser: e.value });
                        }}
                        placeholder="Select User Type"
                      />
                      {errors.type_of && touched.type_of ? (
                        <div className="error__msg">{errors.type_of}</div>
                      ) : null}
                    </div>
                    {this.state.optionAllUser === "user" ||
                      this.state.optionAllUser === "admin" ? (
                      <div>
                        <label className="label">Create Tag </label>
                        <div onChange={this.onChangeRole}>
                          <input
                            type="radio"
                            value="withorganization"
                            name="selectRole"
                          />
                          &nbsp;
                          <label className="label"> With Organization</label>
                          &nbsp;&nbsp;
                          <input
                            type="radio"
                            value="withoutorganization"
                            name="selectRole"
                          />
                          &nbsp;
                          <label className="label"> Without Organization</label>
                        </div>
                      </div>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "user" &&
                      this.state.selectedRole === "withorganization" ? (
                      <>
                        <div>
                          <label className="label">
                            Select Organization Name
                          </label>
                          <Select
                            options={this.state.allOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Organization"
                          />
                        </div>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allAdminData}
                            placeholder="Select Admin"
                            onChange={(value) => {
                              setFieldValue("admin", value.value);
                              this.setState({ selectedAdminId: value.id }, async () => {
                                let resultUserList = await getAdminwiseUserDropDown(
                                  value.value
                                );
                                let tempUserArray =
                                  resultUserList &&
                                  resultUserList.data &&
                                  resultUserList.data.map((obj) => ({
                                    id: `${obj.pkuserId}`,
                                    value: `${obj.username}`,
                                    label: `${obj.username}`,
                                  }));
                                this.setState({ allData: tempUserArray });
                              });
                            }}
                          />
                          {/* {errors.admin && touched.admin ? (
                        <div className="error__msg">{errors.admin}</div>
                      ) : null} */}
                        </div>
                        <div>
                          <label className="label">Select User</label>
                          <Select
                            options={this.state.allData}
                            placeholder="Select User"
                            onChange={(value) => {
                              setFieldValue("user", value.value);
                              this.setState({ selectedUserId: value.id });
                            }}
                          />

                        </div>
                      </>
                    ) : this.state.optionAllUser === "user" &&
                      this.state.selectedRole === "withoutorganization" ? (
                      <>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allAdminList}
                            placeholder="Select Admin"
                            onChange={(value) => {
                              setFieldValue("admin", value.value);
                              this.setState({ selectedAdminId: value.id }, async () => {
                                let resultUserList = await getAdminwiseUserDropDown(
                                  value.value
                                );
                                let tempUserArray =
                                  resultUserList &&
                                  resultUserList.data &&
                                  resultUserList.data.map((obj) => ({
                                    id: `${obj.pkuserId}`,
                                    value: `${obj.username}`,
                                    label: `${obj.username}`,
                                  }));
                                this.setState({ allData: tempUserArray });
                              });
                            }}
                          />
                          {/* {errors.admin && touched.admin ? (
                        <div className="error__msg">{errors.admin}</div>
                      ) : null} */}
                        </div>
                        <div>
                          <label className="label">Select User</label>
                          <Select
                            options={this.state.allData}
                            placeholder="Select User"
                            onChange={(value) => {
                              setFieldValue("user", value.value);
                              this.setState({ selectedUserId: value.id });
                            }}
                          />
                        </div>
                      </>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "admin" &&
                      this.state.selectedRole === "withorganization" ? (
                      <>
                        <div>
                          <label className="label">
                            Select Organization Name
                          </label>
                          <Select
                            options={this.state.allOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Organization"
                          />
                        </div>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allAdminData}
                            placeholder="Select Admin"
                            onChange={(value) => {
                              setFieldValue("admin", value.value);
                              this.setState({ selectedAdminId: value.id }
                              );
                            }}
                          />

                        </div>
                      </>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "admin" &&
                      this.state.selectedRole === "withoutorganization" ? (
                      <div>
                        <label className="label">
                          Select Admin Name
                        </label>
                        <Select
                          options={this.state.allOnlyAdminList}
                          placeholder="Select Admin"
                          onChange={(value) => {
                            setFieldValue("admin", value.value);
                            this.setState({ selectedAdminId: value.id })
                          }}
                        />
                        {/* {errors.admin && touched.admin ? (
                        <div className="error__msg">{errors.admin}</div>
                      ) : null} */}
                      </div>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "organization"
                      ? (
                        <div>
                          <label className="label">
                            Select Organization Name
                          </label>
                          <Select
                            options={this.state.allOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Organization"
                          />
                        </div>
                      ) : (
                        ""
                      )}
                  </>
                )}

              </div>
              <br />
              <div align="center">
                <button type="submit" className="btn btn-primary" disabled={this.state.disabled}>
                  Submit
                </button>
              </div>
            </Form>
          )}
        </Formik>
      </div>
    );
  }
}
