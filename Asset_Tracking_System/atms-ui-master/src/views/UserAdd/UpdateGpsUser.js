import React, { Component } from "react";
import "./UserAdd.css";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import { putUser } from "../../Service/putUser";
import { getSingleUser } from "../../Service/getSingleUser";
import { Link, NavLink } from "react-router-dom";
import { getAssetCategory } from "../../Service/getAssetCategory";
import { getAdminWiseGPSUserList } from "../../Service/getAdminWiseGPSUserList";
import { getOnlyGpsAdminDrowpdownList } from "../../Service/getOnlyGpsAdminDrowpdownList";
import { getGpsOrgWiseList } from "../../Service/getGpsOrgWiseList";
import swal from "sweetalert";
import Select from "react-select";
import axios from "../../utils/axiosInstance";

const SignupSchema = Yup.object().shape({
  firstName: Yup.string()
    .min(2, "Too Short!")
    .max(50, "Too Long!")
    .required("Enter First Name"),
  lastName: Yup.string()
    .min(2, "Too Short!")
    .max(50, "Too Long!")
    .required("Enter Last Name"),
  phone: Yup.string()
    .min(10, "Too Short!")
    .max(10, "Too Long!")
    .required("Enter Phone No"),
  email: Yup.string().email("Invalid Email_Id").required("Enter Email_id"),
  password: Yup.string().required("Enter Password"),
  address: Yup.string().required("Enter Address"),
  type_of: Yup.string().required("Select Role"),
  userName: Yup.string().required("Enter User Name"),
  companyname: Yup.string().required("Enter Company Name"),
});
export default class UpdateGpsUsers extends Component {
  state = {
    result: [],
    id: 0,
    loader: false,
    Role: "",
    username: "",
    category: "",
    selectedCategory: "",
    allCategory: [],
    allAdminList: [],
    allOnlyAdminList: [],
    selectedAdmin: "",
    selectedCategoryId: [],
    selectedAdminId: "",
    allAdminUser: [
      {
        label: "User",
        value: "user",
      },
    ],
    allUser: [
      {
        label: "User",
        value: "user",
      },
      {
        label: "Admin",
        value: "admin",
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
    selectedOrg: "",
    selectedOrgId: "",
  };
  componentDidMount = async () => {
    try {
      setTimeout(async () => {
        this.setState(
          {
            loader: true,
            Role: localStorage.getItem("role"),
            username: localStorage.getItem("username"),
            selectedCategory: localStorage.getItem("SelectedCategory"),
          },
          () => {
            console.log("Role", this.state.Role);
            console.log("username", this.state.username);
            console.log("SelectedCategory", this.state.selectedCategory);
          }
        );
        if (
          this.props.location &&
          this.props.location.state &&
          this.props.location.state.userId
        ) {
          const propId =
            this.props.location &&
            this.props.location.state &&
            this.props.location.state.userId;

          let result = await getSingleUser(propId);
          console.log("result", result.data);
          this.setState({
            result: result && result.data,
            id: propId,
            loader: false,
            category: result.data.category,
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

          this.setState({ allCategory: tempCategoryArray });
        } else if (
          this.props.match &&
          this.props.match.params &&
          this.props.match.params.id
        ) {
          const propParamId =
            this.props.match &&
            this.props.match.params &&
            this.props.match.params.id;
          let result = await getSingleUser(propParamId);
          this.setState({
            result: result && result.data,
            id: propParamId,
            loader: false,
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

          this.setState({ allCategory: tempCategoryArray });
        } else {
          this.props.history.push("/users");
        }
        let resultOrg = await getGpsOrgWiseList();
        console.log("result", resultOrg);
        let tempOrgArray = [];
        if (resultOrg && resultOrg.data && resultOrg.data.length != 0) {
          resultOrg.data.map((obj) => {
            let tempObj = {
              id: `${obj.pkuserId}`,
              value: `${obj.username}`,
              label: `${obj.username}`,
            };
            tempOrgArray.push(tempObj);
          });
        }

        this.setState({ allOrgList: tempOrgArray }, () => {
          console.log("allOrgList List", this.state.allOrgList);
        });

        let resultAdmin = await getAdminWiseGPSUserList();
        console.log("Admin result", resultAdmin);
        let tempAdminArray = [];
        if (resultAdmin && resultAdmin.data && resultAdmin.data.length != 0) {
          resultAdmin.data.map((obj) => {
            let tempAdminObj = {
              id: `${obj.pkuserId}`,
              value: `${obj}`,
              label: `${obj}`,
            };
            tempAdminArray.push(tempAdminObj);
          });
        }

        this.setState(
          {
            allAdminList: tempAdminArray,
            selectedOrg: this.state.result.organization,
            selectedAdmin: this.state.result.admin,
          },
          () => {
            console.log("allAdmin List", this.state.allAdminList);
            if (this.state.selectedOrg !== "") {
              let tempOrgId = this.state.allOrgList.filter(
                (obj) => obj.value === this.state.selectedOrg
              );
              console.log("tempOrgId", tempOrgId);
              this.setState({ selectedOrgId: tempOrgId[0].id }, async () => {
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
                      this.setState({ allAdminList: tempAdminArray }, () => {
                        console.log("allAdmin List", this.state.allAdminList);
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
              });
            }
          }
        );
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
            this.setState({ allOnlyAdminList: tempAdminArray }, () => {
              console.log("Alladmin", this.state.allOnlyAdminList);
            });
          }
        });
      }, 200);
    } catch (error) {
      console.log(error);
      this.setState({ loader: false });
    }
  };
  changeCategory = (selectedCategory) => {
    this.setState({ selectedCategory: selectedCategory.value }, () => {
      console.log("selectedCategory", this.state.selectedCategory);
      //console.log('selectedCategoryId', this.state.selectedCategoryId)
    });
  };

  changeAdminName = (selectAdmin) => {
    this.setState({ selectedAdmin: selectAdmin.value }, () => {
      console.log("selectedAdmin", this.state.selectedAdmin);
    });
  };
  changeOrgName = async (selectOrg) => {
    console.log("Selected Organization Name--->", selectOrg.value);
    this.setState(
      { selectedOrg: selectOrg.value, selectedOrgId: selectOrg.id },
      async () => {
        console.log("selected Organization", this.state.selectedOrg);
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
              this.setState({ allAdminList: tempAdminArray }, () => {
                console.log("allAdmin List", this.state.allAdminList);
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
  render() {
    const { loader } = this.state;
    return (
      <>
        {loader ? (
          <div class="loader"></div>
        ) : (
          <div className="userAdd__wrapper">
            <h3>Update User Information</h3>
            <Formik
              enableReinitialize={true}
              initialValues={{
                firstName:
                  this.state &&
                  this.state.result &&
                  this.state.result.firstName,
                lastName:
                  this.state && this.state.result && this.state.result.lastName,
                email:
                  this.state && this.state.result && this.state.result.emailId,
                phone:
                  this.state &&
                  this.state.result &&
                  this.state.result.phoneNumber,
                userName:
                  this.state && this.state.result && this.state.result.username,
                password:
                  this.state && this.state.result && this.state.result.passWord,
                type_of:
                  this.state && this.state.result && this.state.result.role,
                companyname:
                  this.state &&
                  this.state.result &&
                  this.state.result.companyName,
                address:
                  this.state && this.state.result && this.state.result.address,
                category:
                  this.state && this.state.result && this.state.result.category,
              }}
              validationSchema={SignupSchema}
              onSubmit={async (values) => {
                const data = {
                  phoneNumber: values.phone,
                  emailId: values.email,
                  firstName: values.firstName,
                  lastName: values.lastName,
                  password: values.password,
                  username: values.userName,
                  address: values.address,
                  companyName: values.companyname,
                  pkuserId: this.state.id,
                  role:
                    values.type_of === "user"
                      ? "user"
                      : values.type_of === "admin"
                      ? "admin"
                      : values.type_of === "organization"
                      ? "organization"
                      : "super-admin",
                  status:
                    this.state && this.state.result && this.state.result.status,
                  // organization: this.state && this.state.result && this.state.result.organization,
                  // admin: this.state && this.state.result && this.state.result.admin,
                  // user: this.state && this.state.result && this.state.result.user,
                  organization:
                    this.state.Role === "organization"
                      ? this.state.username
                      : this.state.selectedOrg,
                  admin:
                    this.state.Role === "admin"
                      ? this.state.username
                      : this.state.selectedAdmin,
                  user: this.state.Role === "user" ? this.state.username : "",
                  category: values.category,
                  
                  //category: this.state.category
                };
                console.log("data", data);
                let result = await putUser(data);
                console.log("result", result);
                if (result.status === 200) {
                  swal("Great", "User Update Successfully", "success");
                  //this.props.history.push("/users")
                  if (this.state.selectedCategory === "BLE") {
                    this.props.history.push("/users");
                  } else {
                    this.props.history.push("/gps-users");
                  }
                } else {
                  swal(
                    "Failed",
                    "Something went wrong please check your internet",
                    "error"
                  );
                }
              }}
            >
              {({ errors, touched, values, setFieldValue }) => (
                <Form>
                  <div className="user__form__wrapper">
                    <div>
                      <label className="label">First Name</label>
                      <Field
                        className="form-control"
                        name="firstName"
                        placeholder="First Name"
                      />
                      {errors.firstName && touched.firstName ? (
                        <div className="error__msg">{errors.firstName}</div>
                      ) : null}
                    </div>
                    <div>
                      <label className="label">Last Name</label>
                      <Field
                        className="form-control"
                        name="lastName"
                        placeholder="Last Name"
                      />
                      {errors.lastName && touched.lastName ? (
                        <div className="error__msg">{errors.lastName}</div>
                      ) : null}
                    </div>
                    <div>
                      <label className="label">Company Name</label>
                      <Field
                        className="form-control"
                        name="companyname"
                        placeholder="Enter Company Name"
                      />
                      {errors.companyname && touched.companyname ? (
                        <div className="error__msg">{errors.companyname}</div>
                      ) : null}
                    </div>
                    <div>
                      <label className="label">Phone No</label>
                      <Field
                        className="form-control"
                        name="phone"
                        placeholder="Phone"
                        maxlength="10"
                      />
                      {errors.phone && touched.phone ? (
                        <div className="error__msg">{errors.phone}</div>
                      ) : null}
                    </div>
                    <div>
                      <label className="label">User Name</label>
                      <Field
                        className="form-control"
                        name="userName"
                        type="text"
                        placeholder="Username"
                      />
                      {errors.userName && touched.userName ? (
                        <div className="error__msg">{errors.userName}</div>
                      ) : null}
                    </div>
                    <div>
                      <label className="label">Password</label>
                      <Field
                        className="form-control"
                        name="password"
                        type="password"
                        placeholder="Password"
                      />
                      {errors.password && touched.password ? (
                        <div className="error__msg">{errors.password}</div>
                      ) : null}
                    </div>
                    <div>
                      <label className="label">Email Id</label>
                      <Field
                        className="form-control"
                        name="email"
                        type="email"
                        placeholder="Email_Id"
                      />
                      {errors.email && touched.email ? (
                        <div className="error__msg">{errors.email}</div>
                      ) : null}
                    </div>
                    <div>
                      <label className="label">Address</label>
                      <Field
                        className="form-control"
                        name="address"
                        placeholder="Enter Address"
                      />
                      {errors.address && touched.address ? (
                        <div className="error__msg">{errors.address}</div>
                      ) : null}
                    </div>
                    <div>
                      <label className="label">Select Category Name</label>
                      <Select
                        value={
                          this.state.allCategory
                            ? this.state.allCategory.find(
                                (option) => option.value === values.category
                              )
                            : ""
                        }
                        onChange={(e) => {
                          setFieldValue("category", e.value);
                          // this.setState({ category: e.value }, () => {
                          //     console.log("selectedCat", this.state.category)
                          // });
                        }}
                        options={this.state.allCategory}
                      />
                    </div>
                    {this.state.Role === "admin" ? (
                      <>
                        <div>
                          <label className="label">User Type</label>
                          {/* <Field
                            as="select"
                            className="form-control"
                            name="type_of"
                          >
                            <option>Select User Type</option>
                            <option value="user">User</option>
                          </Field> */}
                          <Select
                            options={this.state.allAdminUser}
                            value={
                              this.state.allAdminUser
                                ? this.state.allAdminUser.find(
                                    (option) => option.value === values.type_of
                                  )
                                : ""
                            }
                            onChange={(e) => {
                              setFieldValue("type_of", e.value);
                              this.setState({ optionUser: e.value });
                            }}
                          />
                          {errors.type_of && touched.type_of ? (
                            <div className="error__msg">{errors.type_of}</div>
                          ) : null}
                        </div>
                      </>
                    ) : this.state.Role === "organization" ? (
                      <>
                        <div>
                          <label className="label">User Type</label>
                          <Select
                            options={this.state.allUser}
                            value={
                              this.state.allUser
                                ? this.state.allUser.find(
                                    (option) => option.value === values.type_of
                                  )
                                : ""
                            }
                            onChange={(e) => {
                              setFieldValue("type_of", e.value);
                              this.setState({ optionUser: e.value });
                            }}
                            placeholder="Select User Type"
                          />
                          {errors.type_of && touched.type_of ? (
                            <div className="error__msg">{errors.type_of}</div>
                          ) : null}
                        </div>
                        {values.type_of === "user" ? (
                          <div>
                            <label className="label">Select Admin Name</label>
                            <Select
                              options={this.state.allAdminList}
                              value={
                                this.state.allAdminList
                                  ? this.state.allAdminList.find(
                                      (option) =>
                                        option.value ===
                                        this.state.selectedAdmin
                                    )
                                  : ""
                              }
                              onChange={this.changeAdminName}
                              placeholder="Select Admin"
                            />
                          </div>
                        ) : (
                          ""
                        )}
                      </>
                    ) : (
                      <>
                        <div>
                          <label className="label">User Type</label>
                          <Select
                            options={this.state.allSuperUser}
                            value={
                              this.state.allSuperUser
                                ? this.state.allSuperUser.find(
                                    (option) => option.value === values.type_of
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
                        {values.type_of === "user" ? (
                          this.state.result.createdby === "superadmin" &&
                          this.state.result.organization === "" ? (
                            <div>
                              <label className="label">Select Admin Name</label>
                              <Select
                                options={this.state.allOnlyAdminList}
                                value={
                                  this.state.allOnlyAdminList
                                    ? this.state.allOnlyAdminList.find(
                                        (option) =>
                                          option.value ===
                                          this.state.selectedAdmin
                                      )
                                    : ""
                                }
                                onChange={this.changeAdminName}
                                placeholder="Select Admin"
                              />
                            </div>
                          ) : (
                            <>
                              <div>
                                <label className="label">
                                  Select Organization Name
                                </label>
                                <Select
                                  options={this.state.allOrgList}
                                  value={
                                    this.state.allOrgList
                                      ? this.state.allOrgList.find(
                                          (option) =>
                                            option.value ===
                                            this.state.selectedOrg
                                        )
                                      : ""
                                  }
                                  onChange={this.changeOrgName}
                                  placeholder="Select Organization"
                                />
                              </div>
                              <div>
                                <label className="label">
                                  Select Admin Name
                                </label>
                                <Select
                                  options={this.state.allAdminList}
                                  value={
                                    this.state.allAdminList
                                      ? this.state.allAdminList.find(
                                          (option) =>
                                            option.value ===
                                            this.state.selectedAdmin
                                        )
                                      : ""
                                  }
                                  onChange={this.changeAdminName}
                                  placeholder="Select Admin"
                                />
                              </div>
                            </>
                          )
                        ) : (
                          ""
                        )}
                        {values.type_of === "admin" ? (
                          this.state.result.createdby === "superadmin" &&
                          this.state.result.organization === "" ? (
                            ""
                          ) : (
                            <div>
                              <label className="label">
                                Select Organization Name
                              </label>
                              <Select
                                options={this.state.allOrgList}
                                value={
                                  this.state.allOrgList
                                    ? this.state.allOrgList.find(
                                        (option) =>
                                          option.value ===
                                          this.state.selectedOrg
                                      )
                                    : ""
                                }
                                onChange={this.changeOrgName}
                                placeholder="Select Organization"
                              />
                            </div>
                          )
                        ) : (
                          ""
                        )}
                      </>
                    )}
                  </div>
                  <br />
                  <div className="btn-user" align="center">
                    <button type="submit" className="btn btn-primary">
                      Submit
                    </button>
                    {this.state.selectedCategory === "BLE" ? (
                      <button type="cancel" className="btn btn-primary">
                        {" "}
                        <Link to={{ pathname: "/users" }}>Cancel</Link>
                      </button>
                    ) : (
                      <button type="cancel" className="btn btn-primary">
                        {" "}
                        <Link to={{ pathname: "/gps-users" }}>Cancel</Link>
                      </button>
                    )}
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
