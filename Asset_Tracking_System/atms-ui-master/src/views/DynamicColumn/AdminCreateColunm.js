import React, { Component } from "react";
import "./DynamicColumn.css";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import { postAdminColumn } from "../../Service/postAdminColumn";
import { getAdminDrowpdownList } from "../../Service/getAdminDrowpdownList";
import { getGPSAdminDrowpdownList } from "../../Service/getGPSAdminDrowpdownList";
import { getOnlyAdminDrowpdownList } from "../../Service/getOnlyAdminDrowpdownList";
import { getOrgnization } from "../../Service/getOrgnization";
import { getGpsOrgnization } from "../../Service/getGpsOrgnization";
import { getAdmin } from "../../Service/getAdmin";
import { getAdminColumnAllList } from "../../Service/getAdminColumnAllList";
import { getGpsAdminColumnAllList } from "../../Service/getGpsAdminColumnAllList";
import { getGpsAdmin } from '../../Service/getGpsAdmin'
import { getOnlyGpsAdminDrowpdownList } from "../../Service/getOnlyGpsAdminDrowpdownList";
import Select from "react-select";
import swal from "sweetalert";

// const SignupSchema = Yup.object().shape({
//     productName: Yup.string().required('Enter Product Name'),
//     productDesc: Yup.string().required('Enter Description Name'),
// });

export default class CreateColunm extends Component {
  state = {
    allColumn: [],
    allAllocColumn: [],
    allGpsAllocColumn: [],
    selectedColumn: "",
    allBLEAdminData: [],
    allOnlyAdminData: [],
    allGpsAdminData: [],
    allGpsOnlyAdminData:[],
    selectedAdmin: "",
    allOrgnizationData: [],
    allGpsOrgnizationData: [],
    selectedOrgId: "",
    selectedOrg: "",
    Role: "",
    category: "",
    selectedRole: "",
    unit: "",
    SelectedCategory: ""
  };
  async componentDidMount() {
    try {
      this.setState({ Role: localStorage.getItem("role"), SelectedCategory: localStorage.getItem("SelectedCategory") });
      let columnList = await getAdminDrowpdownList();
      console.log("Column List", columnList);
      let tempArray = [];
      columnList &&
        columnList.data &&
        columnList.data.length !== 0 &&
        columnList.data.map((obj) => {
          let tempObj = {
            value: `${obj}`,
            label: `${obj}`,
          };
          tempArray.push(tempObj);
        });

      let gpscolumnList = await getGPSAdminDrowpdownList();
      console.log("Column List", gpscolumnList);
      let tempGpsArray = [];
      gpscolumnList &&
        gpscolumnList.data &&
        gpscolumnList.data.length !== 0 &&
        gpscolumnList.data.map((obj) => {
          let tempObj = {
            value: `${obj}`,
            label: `${obj}`,
          };
          tempGpsArray.push(tempObj);
        });
      let allocColumnList = await getAdminColumnAllList();
      console.log("Alloc Column List", allocColumnList);

      let allocGpsColumnList = await getGpsAdminColumnAllList();
      console.log("Alloc Gps Column List", allocGpsColumnList);

      let resultAdmin = await getAdmin();
      console.log("admin", resultAdmin);
      let tempAdminArray = [];
      resultAdmin &&
        resultAdmin.data &&
        resultAdmin.data.length !== 0 &&
        resultAdmin.data.map((obj) => {
          let tempadminObj = {
            id: `${obj.pkuserId}`,
            value: `${obj.username}`,
            label: `${obj.username}`,
          };
          tempAdminArray.push(tempadminObj);
        });
        

      this.setState(
        {
          columnList: tempArray,
          allColumn: columnList,
          allAllocColumn: allocColumnList,
          allBLEAdminData: tempAdminArray,
        },
        () => {
          console.log("Select Column", this.state.columnList);
          console.log("All Column", this.state.allColumn);
          console.log("All Alloc Column", this.state.allAllocColumn);
          console.log("All admin list", this.state.allBLEAdminData);
        }
      );

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

        this.setState({ allGpsAdminData: tempGpsAdminArray }, () => {
          console.log('AllGpsadmin', this.state.allGpsAdminData)
        })

    } catch (error) {
      console.log(error);
    }
  }

  onChangeRole = (e) => {
    this.setState({ selectedRole: e.target.value }, async () => {
      await getOnlyAdminDrowpdownList()
        .then((result) => {
          console.log("Only Admin List", result);
          let tempAdminArray = [];
          result &&
            result.data &&
            result.data.length !== 0 &&
            result.data.map((obj) => {
              let tempadminObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`,
              };
              tempAdminArray.push(tempadminObj);
            });
          this.setState({ allOnlyAdminData: tempAdminArray }, () => {
            console.log("Alladmin", this.state.allOnlyAdminData);
          });
        })
        .catch((result) => {
          swal("Failed", "Somthing went wrong", "error");
        });
      await getOrgnization()
        .then((result) => {
          console.log("Org List", result);
          let tempOrgArray = [];
          result &&
            result.data &&
            result.data.length !== 0 &&
            result.data.map((obj) => {
              let temporgObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`,
              };
              tempOrgArray.push(temporgObj);
            });
          this.setState({ allOrgnizationData: tempOrgArray }, () => {
            console.log("AllOrg", this.state.allOrgnizationData);
          });
        })
        .catch((result) => {
          swal("Failed", "Somthing went wrong", "error");
        });
    });
  };

  onChangeGpsRole = (e) => {
    this.setState({ selectedRole: e.target.value }, async () => {
      await getOnlyGpsAdminDrowpdownList()
        .then((result) => {
          console.log("Only Admin List", result);
          let tempAdminArray = [];
          result &&
            result.data &&
            result.data.length !== 0 &&
            result.data.map((obj) => {
              let tempadminObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`,
              };
              tempAdminArray.push(tempadminObj);
            });
          this.setState({ allGpsOnlyAdminData: tempAdminArray }, () => {
            console.log("Alladmin", this.state.allGpsOnlyAdminData);
          });
        })
        .catch((result) => {
          swal("Failed", "Somthing went wrong", "error");
        });
      await getGpsOrgnization()
        .then((result) => {
          console.log("Org List", result);
          let tempOrgArray = [];
          result &&
            result.data &&
            result.data.length !== 0 &&
            result.data.map((obj) => {
              let temporgObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.username}`,
                label: `${obj.username}`,
              };
              tempOrgArray.push(temporgObj);
            });
          this.setState({ allGpsOrgnizationData: tempOrgArray }, () => {
            console.log("AllOrg", this.state.allGpsOrgnizationData);
          });
        })
        .catch((result) => {
          swal("Failed", "Somthing went wrong", "error");
        });
    });
  };

  changeColumn = (selectedOptions) => {
    console.log(selectedOptions.value);
    this.setState({ selectedColumn: selectedOptions.value }, async () => {
      console.log("selected Column", this.state.selectedColumn);
    });
  };
  changeAdmin = (selectedAdmin) => {
    console.log("selectedAdmin", selectedAdmin);
    this.setState({ selectedAdmin: selectedAdmin.value }, () => {
      console.log("admin", this.state.selectedAdmin);
      let filterAllocColumnList =
        this.state.allAllocColumn &&
        this.state.allAllocColumn.data &&
        this.state.allAllocColumn.data.length !== 0 &&
        this.state.allAllocColumn.data.filter((obj) => {
          return obj.adminName === this.state.selectedAdmin;
        });
      console.log("Filter Alloc Column List", filterAllocColumnList);
      let filterColumnList =
        this.state.allColumn &&
        this.state.allColumn.data &&
        this.state.allColumn.data.length !== 0 &&
        this.state.allColumn.data.filter((obj) => {
          return !filterAllocColumnList.find((obj1) => {
            return obj === obj1.columns;
          });
        });
      console.log("Filter Column List", filterColumnList);
      let tempArray = [];
      filterColumnList &&
        filterColumnList.length != 0 &&
        filterColumnList.map((obj) => {
          let tempObj = {
            value: `${obj}`,
            label: `${obj}`,
          };
          tempArray.push(tempObj);
        });
      this.setState({ columnList: tempArray }, () => {
        console.log("Select Column", this.state.columnList);
      });
    });
  };
  changeOrgnization = (selectedOrg) => {
    console.log("selectedOrgnization", selectedOrg);
    this.setState(
      { selectedOrg: selectedOrg.value, selectedOrgId: selectedOrg.id },
      async () => {
        let filterAllocColumnList =
          this.state.allAllocColumn &&
          this.state.allAllocColumn.data &&
          this.state.allAllocColumn.data.length !== 0 &&
          this.state.allAllocColumn.data.filter((obj) => {
            return obj.organization === this.state.selectedOrg;
          });
        console.log("Filter Alloc Column List", filterAllocColumnList);
        let filterColumnList =
          this.state.allColumn &&
          this.state.allColumn.data &&
          this.state.allColumn.data.length !== 0 &&
          this.state.allColumn.data.filter((obj) => {
            return !filterAllocColumnList.find((obj1) => {
              return obj === obj1.columns;
            });
          });
        console.log("Filter Column List", filterColumnList);
        let tempArray = [];
        filterColumnList &&
          filterColumnList.length != 0 &&
          filterColumnList.map((obj) => {
            let tempObj = {
              value: `${obj}`,
              label: `${obj}`,
            };
            tempArray.push(tempObj);
          });
        this.setState({ columnList: tempArray }, () => {
          console.log("Select Column", this.state.columnList);
        });
      }
    );
  };

  async validateOrgName(value) {
    let errors;
    let result = await getAdminColumnAllList();
    console.log("Column", result);
    if (result && result.data && result.data.length !== 0) {
      let tempUser = result.data.filter((obj) => {
        return obj.columns === value;
      });
      if (tempUser.length !== 0) {
        errors = "This Column Name Is Already Exist";
      }
    }
    return errors;
  }
  render() {
    return (
      <div className="userAdd__wrapper" align="center">
        <h3>Add Parameter For Admin</h3>
        <br />
        <br />
        <Formik
          initialValues={{
            columns: "",
            adminName: "",
            unit: "",
          }}
          //  validationSchema={SignupSchema}
          onSubmit={async (values) => {
            let fkUserId = await localStorage.getItem("fkUserId");
            if (
              localStorage.getItem("categoryname") !== "GPS" &&
              localStorage.getItem("categoryname") !== "BLE"
            ) {
              this.setState({
                category: localStorage.getItem("SelectedCategory"),
              });
            } else {
              this.setState({ category: localStorage.getItem("categoryname") });
            }
            const data = {
              columns: this.state.selectedColumn,
              fkAdminId: fkUserId,
              adminName: this.state.selectedAdmin,
              organization:
                this.state.Role === "organization"
                  ? localStorage.getItem("username")
                  : this.state.selectedOrg,
              //fkOrganizationId:fkUserId,
              fkOrganizationId:
                this.state.Role === "organization"
                  ? localStorage.getItem("fkUserId")
                  : this.state.selectedOrgId,
              category: this.state.category,
              unit: this.state.unit,
              // role: values.role
            };
            console.log("data", data);
            let result = await postAdminColumn(data);
            console.log("Add Column", result);
            if (result.status === 200) {
              // swal("Opps!", "Data already present", "warning");
              this.props.history.push("/admin-column-list");
              //window.location.reload();

            } else {
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
              <div className="Product_form"></div>
              <div className="category_form">
                {this.state.SelectedCategory === "BLE" ? <>
                  {this.state.Role === "organization" ? (
                    <>
                      <div align="left">
                        <label className="addlabel">Select Admin</label>
                        <Select
                          options={this.state.allBLEAdminData}
                          placeholder="Select Admin"
                          onChange={this.changeAdmin}
                        />
                      </div>

                      <div align="left">
                        <label className="addlabel">
                          Allocated Parameter Name
                        </label>
                        <Select
                          options={this.state.columnList}
                          name="columns"
                          onChange={this.changeColumn}
                          placeholder="Select Column Name"
                          validate={this.validateOrgName}
                        />
                        {errors.columns && touched.columns ? (
                          <div className="error__msg">{errors.columns}</div>
                        ) : null}
                      </div>
                    </>
                  ) : (
                    <>
                      <div align="left">
                        <label className="addlabel">Select Role</label>
                        <div onChange={this.onChangeRole}>
                          <input
                            type="radio"
                            value="organization"
                            name="selectRole"
                          />
                          &nbsp;
                          <label className="form-control-label">
                            {" "}
                            Organization
                          </label>
                          &nbsp;&nbsp;
                          <input type="radio" value="admin" name="selectRole" />
                          &nbsp;
                          <label className="form-control-label"> Admin</label>
                        </div>
                      </div>
                      {this.state.selectedRole === "organization" ? (
                        <div align="left">
                          <label className="addlabel">Select Organization</label>
                          <Select
                            options={this.state.allOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Orgnization"
                            name="organization"
                          />
                        </div>
                      ) : this.state.selectedRole === "admin" ? (
                        <div align="left">
                          <label className="addlabel">Select Admin</label>
                          <Select
                            options={this.state.allOnlyAdminData}
                            placeholder="Select Admin"
                            onChange={this.changeAdmin}
                          />
                        </div>
                      ) : (
                        ""
                      )}
                      <div align="left">
                        <label className="addlabel">
                          Allocated Parameter Name
                        </label>
                        <Select
                          options={this.state.columnList}
                          name="columns"
                          onChange={this.changeColumn}
                          placeholder="Select Column Name"
                          validate={this.validateOrgName}
                        />
                        {errors.columns && touched.columns ? (
                          <div className="error__msg">{errors.columns}</div>
                        ) : null}
                      </div>
                    </>
                  )}
                </> : <>
                  {this.state.Role === "organization" ? (
                    <>
                      <div align="left">
                        <label className="addlabel">Select Admin</label>
                        <Select
                          options={this.state.allGpsAdminData}
                          placeholder="Select Admin"
                          onChange={this.changeAdmin}
                        />
                      </div>

                      <div align="left">
                        <label className="addlabel">
                          Allocated Parameter Name
                        </label>
                        <Select
                          options={this.state.columnList}
                          name="columns"
                          onChange={this.changeColumn}
                          placeholder="Select Column Name"
                          validate={this.validateOrgName}
                        />
                        {errors.columns && touched.columns ? (
                          <div className="error__msg">{errors.columns}</div>
                        ) : null}
                      </div>
                    </>
                  ) : (
                    <>
                      <div align="left">
                        <label className="addlabel">Select Role</label>
                        <div onChange={this.onChangeGpsRole}>
                          <input
                            type="radio"
                            value="organization"
                            name="selectRole"
                          />
                          &nbsp;
                          <label className="form-control-label">
                            {" "}
                            Organization
                          </label>
                          &nbsp;&nbsp;
                          <input type="radio" value="admin" name="selectRole" />
                          &nbsp;
                          <label className="form-control-label"> Admin</label>
                        </div>
                      </div>
                      {this.state.selectedRole === "organization" ? (
                        <div align="left">
                          <label className="addlabel">Select Organization</label>
                          <Select
                            options={this.state.allGpsOrgnizationData}
                            onChange={this.changeOrgnization}
                            placeholder="Select Orgnization"
                            name="organization"
                          />
                        </div>
                      ) : this.state.selectedRole === "admin" ? (
                        <div align="left">
                          <label className="addlabel">Select Admin</label>
                          <Select
                            options={this.state.allGpsOnlyAdminData}
                            placeholder="Select Admin"
                            onChange={this.changeAdmin}
                          />
                        </div>
                      ) : (
                        ""
                      )}
                      <div align="left">
                        <label className="addlabel">
                          Allocated Parameter Name
                        </label>
                        <Select
                          options={this.state.columnList}
                          name="columns"
                          onChange={this.changeColumn}
                          placeholder="Select Column Name"
                          validate={this.validateOrgName}
                        />
                        {errors.columns && touched.columns ? (
                          <div className="error__msg">{errors.columns}</div>
                        ) : null}
                      </div>
                    </>
                  )}
                </>}
                <div align="center">
                  <button type="submit" className="btn btn-primary">
                    SUBMIT
                  </button>
                </div>
              </div>
            </Form>
          )}
        </Formik>
      </div>
    );
  }
}
