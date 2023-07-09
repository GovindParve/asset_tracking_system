import React, { Component } from "react";
import "./UserAdd.css";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import { postUser } from "../../Service/postUser";
import { getUser } from "../../Service/getUser";
import { getAdminWiseUserList } from "../../Service/getAdminWiseUserList";
import { getOnlyAdminDrowpdownList } from "../../Service/getOnlyAdminDrowpdownList";
import { getOrgWiseList } from "../../Service/getOrgWiseList";
import { getAssetCategory } from "../../Service/getAssetCategory";
import swal from "sweetalert";
import Select from "react-select";
import axios from "../../utils/axiosInstance";
//import localStorage from 'redux-persist/es/storage';

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

export default class AddGpsUser extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isRevealPwd: false,
      Role: "",
      username: "",
      category: "",
      selectedCategory: "",
      allCategory: [],
      allAdminList: [],
      selectedAdmin: "",
      selectedCategoryId: [],
      selectedAdminId: "",
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
      selectedRole: "",
      // createdBy: localStorage.getItem("username") + ' / ' + localStorage.getItem('role')
    };
    this.toggleShow = this.toggleShow.bind(this);
  }
  async componentDidMount() {
    try {
      this.setState(
        {
          loader: true,
          Role: localStorage.getItem("role"),
          username: localStorage.getItem("username"),
          category: localStorage.getItem("SelectedCategory"),
        },
        () => {
          console.log("Role", this.state.Role);
          console.log("username", this.state.username);
        }
      );

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

      let resultOrg = await getOrgWiseList();
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
      } else {
        swal(
          "Sorry",
          "Organization is not present. Please create organization first",
          "warning"
        );
      }

      this.setState({ allOrgList: tempOrgArray }, () => {
        console.log("allOrgList List", this.state.allOrgList);
      });

      let resultAdmin = await getAdminWiseUserList();
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
      } else {
        swal(
          "Sorry",
          "Admin is not present. Please create admin first",
          "warning"
        );
      }

      this.setState({ allAdminList: tempAdminArray }, () => {
        console.log("allAdmin List", this.state.allAdminList);
      });
    } catch (error) {
      console.log(error);
      this.setState({ loader: false });
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

  toggleShow() {
    this.setState({ isRevealPwd: !this.state.isRevealPwd });
  }

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

  changeAdminName = (selectedAdmin) => {
    console.log("selectedAdmin", selectedAdmin);
    this.setState(
      { selectedAdmin: selectedAdmin.value, selectedAdminId: selectedAdmin.id },
      () => {
        console.log("admin", this.state.selectedAdmin);
        console.log("adminid", this.state.selectedAdminId);
      }
    );
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
            `user/get-adminlist_for_dropdown?fkUserId=${this.state.selectedOrgId}&role=organization&category=BLE`,
            {
              headers: { Authorization: `Bearer ${token}` },
            }
          )
          .then((result) => {
            console.log("Admin Name", result);
            console.log("this.state.optionUser", this.state.optionUser);
            if ( result && result.data && result.data.length !== 0) {
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
    let show = <i className="icon-eye"></i>;
    let hide = <i className="fa fa-eye-slash"></i>;
    return (
      <div className="userAdd__wrapper">
        <h3>User Information</h3>
        <br />
        <Formik
          initialValues={{
            firstName: "",
            lastName: "",
            email: "",
            phone: "",
            password: "",
            userName: "",
            address: "",
            companyname: "",
            createdBy: "",
            type_of: "",
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
              createdby: this.state.username,
              role:
                values.type_of === "user"
                  ? "user"
                  : values.type_of === "admin"
                  ? "admin"
                  : values.type_of === "organization"
                  ? "organization"
                  : "super-admin",
              organization:
                this.state.Role === "organization"
                  ? this.state.username
                  : this.state.selectedOrg,
              admin:
                this.state.Role === "admin"
                  ? this.state.username
                  : this.state.selectedAdmin,
              user: this.state.Role === "user" ? this.state.username : "",
              category: this.state.selectedCategory,
            };

            console.log("data", data);
            // let result = await postUser(data);
            // console.log("Add User", result)
            // if (result.status === 200) {
            //     swal("Great", "User Created Successfully", "success");
            //     //this.props.history.push("/users")
            //     window.location.reload()
            // }
            // else {
            //     swal("Failed", "Something went wrong please check your internet", "error");
            // }
            return await postUser(data)
              .then((result) => {
                console.log("Add User", result);
                swal("Great", "User Created Successfully", "success");
                // if (this.state.category === "BLE") {
                //   this.props.history.push("/users");
                // } else {
                //   this.props.history.push("/gps-users");
                // }
                window.location.reload()
                return Promise.resolve();
              })
              .catch(() => {
                swal(
                  "Failed",
                  "Something went wrong please check your internet",
                  "error"
                );
              });
          }}
        >
          {({ errors, touched, setFieldValue }) => (
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
                    placeholder="Username"
                    validate={this.validateUserName}
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
                    type={this.state.isRevealPwd ? "text" : "password"}
                    placeholder="Password"
                  />
                  {/* <span className='show-icon' onClick={this.toggleShow}>{this.state.isRevealPwd ? hide:show}</span> */}
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
                    validate={this.validateEmail}
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
                    options={this.state.allCategory}
                    onChange={this.changeCategory}
                    placeholder="Select Category Name"
                  />
                  {/* {errors.AssetTagType && touched.AssetTagType ? (
                                        <div className="error__msg">{errors.AssetTagType}</div>
                                    ) : null} */}
                </div>
                {this.state.Role === "admin" ? (
                  <>
                    <div>
                      <label className="label">User Type</label>
                      <Field
                        as="select"
                        className="form-control"
                        name="type_of"
                      >
                        <option>Select User Type</option>
                        <option value="user">User</option>
                      </Field>
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
                                (option) =>
                                  option.value === this.state.optionUser
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
                    {this.state.optionUser === "user" ? (
                      <div>
                        <label className="label">Select Admin Name</label>
                        <Select
                          options={this.state.allAdminList}
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
                        <label className="label">Create</label>
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
                            options={this.state.allOrgList}
                            onChange={this.changeOrgName}
                            placeholder="Select Organization"
                          />
                        </div>
                        <div>
                          <label className="label">Select Admin Name</label>
                          <Select
                            options={this.state.allAdminList}
                            onChange={this.changeAdminName}
                            placeholder="Select Admin"
                          />
                        </div>
                      </>
                    ) : this.state.optionAllUser === "user" &&
                      this.state.selectedRole === "withoutorganization" ? (
                      <div>
                        <label className="label">Select Admin Name</label>
                        <Select
                          options={this.state.allAdminList}
                          onChange={this.changeAdminName}
                          placeholder="Select Admin"
                        />
                      </div>
                    ) : (
                      ""
                    )}
                    {this.state.optionAllUser === "admin" &&
                    this.state.selectedRole === "withorganization" ? (
                      <div>
                        <label className="label">
                          Select Organization Name
                        </label>
                        <Select
                          options={this.state.allOrgList}
                          onChange={this.changeOrgName}
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
              <div className="btn-user" align="center">
                <button type="submit" className="btn btn-primary">
                  SUBMIT
                </button>
              </div>
              <br />
              <br />
            </Form>
          )}
        </Formik>
      </div>
    );
  }
}
