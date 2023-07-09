import React, { Component } from "react";
import "./DynamicColumn.css";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import { postColumn } from "../../Service/postColumn";
import { listColumn } from "../../Service/listColumn";
import { getColumnList } from "../../Service/getColumnList";
import Select from "react-select";
import swal from "sweetalert";

// const SignupSchema = Yup.object().shape({
//     productName: Yup.string().required('Enter Product Name'),
//     productDesc: Yup.string().required('Enter Description Name'),
// });

export default class CreateColunm extends Component {
  state = {
    allColumn: [],
    selectedDevice: "",
    category: "",
    parameter: "",
    Role: "",
    selectcategory:""
    // selectedColumn: "",
  };
  async componentDidMount() {
    this.setState(
      {
        loader: true,
        Role: localStorage.getItem("role"),
        selectcategory: localStorage.getItem("categoryname"),
      },
      () => {
        console.log("Role", this.state.Role);
        console.log("username", this.state.username);
      }
    );
    let columnList = await listColumn();
    console.log("Column List", columnList);
    let resultUser = await getColumnList();
    console.log("Column Allocated All List", resultUser);
    if (columnList && columnList.data && columnList.data.length !== 0) {
      let tempColumn = columnList.data.filter(
        (obj) =>
          !resultUser.data.find((obj1) => {
            return obj === obj1.columnName;
          })
      );
      console.log("validation Column", tempColumn);
      let tempArray = [];
      if (tempColumn.length !== 0) {
        tempColumn.map((obj) => {
          let tempObj = {
            value: `${obj}`,
            label: `${obj}`,
          };
          console.log("Column", tempObj);
          tempArray.push(tempObj);
        });
        this.setState(
          {
            columnList: tempArray,
            selectedColumn: tempColumn && tempColumn[0],
          },
          () => {
            console.log("Select Column", this.state.columnList);
          }
        );
      }
      // else if (columnList && columnList.data && columnList.data.length != 0){
      //       columnList.data.map((obj) => {
      //         let tempObj = {
      //          value: `${obj}`,
      //          label: `${obj}`
      //         }
      //         console.log('Column', tempObj);
      //         tempArray.push(tempObj)
      //       })
      //     this.setState({columnList: tempArray,selectedColumn: columnList && columnList.data[0]
      //     }, () => {
      //       console.log('Select Column', this.state.columnList)
      //     })
      // }
    }
  }

  changeColumn = (selectedOptions) => {
    console.log(selectedOptions.value);
    this.setState({ selectedDevice: selectedOptions.value }, async () => {
      console.log("selected Column", this.state.selectedDevice);
    });
  };

  // changeParameter = (e) => {
  //     this.setState({ parameter: e.target.value.toUpperCase() });
  // }

  async validateParameter(value) {
    let errors;
    let resultUser = await getColumnList();
    console.log("selected Column", resultUser);
    if (resultUser && resultUser.data && resultUser.data.length !== 0) {
      let tempUser = resultUser.data.filter((obj) => {
        return (
          obj.allocatedColumn_db.toUpperCase() === value.toUpperCase() ||
          obj.allocatedColumn_db.toLowerCase() === value.toLowerCase()
        );
      });
      console.log("selected tempUser", tempUser);
      if (tempUser.length !== 0) {
        errors = "This Parameter name is already allocated";
      }
    }
    return errors;
  }
  render() {
    return (
      <div className="userAdd__wrapper" align="center">
        <h3>Add Parameters</h3>
        <br />
        <br />
        <Formik
          initialValues={{
            columnName: "",
            allocatedColumn_db: "",
            allocatedColumn_ui: "",
            unit: "",
          }}
          //  validationSchema={SignupSchema}
          onSubmit={async (values) => {
            let fkUserId = await localStorage.getItem("fkUserId");
            if (localStorage.getItem("categoryname") !== "BLE") {
              this.setState({
                category: localStorage.getItem("SelectedCategory"),
              });
            } else {
              this.setState({ category: localStorage.getItem("categoryname") });
            }
            //let category = await localStorage.getItem("categoryname")
            const data = {
              columnName: this.state.selectedDevice,
              fkUserId: fkUserId,
              allocatedColumn_db: values.allocatedColumn_db,
              allocatedColumn_ui: values.allocatedColumn_ui,
              unit: values.unit,
              category: this.state.category,
            };

            console.log("data", data);
            let result = await postColumn(data);
            console.log("Add Column", result);
            if (result.status === 200) {
              swal("Great", "Data added successfully", "success");
              // this.props.history.push("/column-list")
              // window.location.reload();
            } else {
              swal(
                "Failed",
                "Something went wrong please check your internet",
                "error"
              );
            }
          }}
        >
          {({ errors, touched }) => (
            <Form>
              <div className="Product_form">
                <div align="left">
                  <label className="addlabel">Column Name</label>
                  <Select
                    options={this.state.columnList}
                    name="columnName"
                    onChange={this.changeColumn}
                    placeholder="Select Column Name"
                  />
                </div>
                <div align="left">
                  <label className="addlabel">Allocated Parameter</label>
                  <Field
                    className="form-control"
                    name="allocatedColumn_db"
                    placeholder="Enter Allocated Parameter"
                    validate={this.validateParameter}
                    //value={this.state.parameter} onChange={this.changeParameter}
                  />
                  <br />
                  {errors.allocatedColumn_db && touched.allocatedColumn_db ? (
                    <div className="error__msg">
                      {errors.allocatedColumn_db}
                    </div>
                  ) : null}
                </div>
                <div align="left">
                  <label className="addlabel">Parameter Display UI</label>
                  <Field
                    className="form-control"
                    name="allocatedColumn_ui"
                    placeholder="Enter Parameter Display UI"
                  />
                </div>
                <div align="left">
                  <label className="addlabel">Unit</label>
                  <Field
                    className="form-control"
                    name="unit"
                    placeholder="Enter Unit"
                  />
                </div>
              </div>
              <br />
              <br />
              <div align="center">
                <button type="submit" className="btn btn-primary">
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
