import React, { Component } from "react";
import "./Product.css";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import { postProduct } from "../../Service/postProduct";
import { getAdminWiseUserList } from "../../Service/getAdminWiseUserList";
import { getGpsAdminWiseUserList } from "../../Service/getGpsAdminWiseUserList";
import swal from "sweetalert";
import Select from "react-select";

const SignupSchema = Yup.object().shape({
  productName: Yup.string().required("Enter Product Name"),
  productDesc: Yup.string().required("Enter Description Name"),
});
export default class AddCategory extends Component {
  constructor(props) {
    super(props);
    this.state = {
      Role: "",
      category: "",
      username: "",
      allAdminList: [],
      allGpsAdminList:[],
      selectedAdmin: "",
      selectedAdminId: "",
      disabled: false
    };
  }

  async componentDidMount() {
    try {
      this.setState({
        loader: true,
        Role: localStorage.getItem("role"),
        username: localStorage.getItem("username"),
        category: localStorage.getItem("categoryname")
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
      }

      this.setState({ allAdminList: tempAdminArray }, () => {
        console.log("allAdmin List", this.state.allAdminList);
      });
      let resultGPSAdmin = await getGpsAdminWiseUserList();
      console.log("Admin result", resultGPSAdmin);
      let tempGpsAdminArray = [];
      if (resultGPSAdmin && resultGPSAdmin.data && resultGPSAdmin.data.length != 0) {
        resultGPSAdmin.data.map((obj) => {
          let tempGpsAdminObj = {
            id: `${obj.pkuserId}`,
            value: `${obj}`,
            label: `${obj}`,
          };
          tempGpsAdminArray.push(tempGpsAdminObj);
        });
      }

      this.setState({ allGpsAdminList: tempGpsAdminArray }, () => {
        console.log("allAdmin List", this.state.allGpsAdminList);
      });
    } catch (error) {
      console.log(error);
      this.setState({ loader: false });
    }
  }

  changeAdminName = (selectedAdmin) => {
    console.log("selectedAdmin", selectedAdmin);
    this.setState({ selectedAdmin: selectedAdmin.target.value }, () => {
      console.log("admin", this.state.selectedAdmin);
    });
  };

  render() {
    return (
      <div className="category_heading" align="center">
        <Formik
          initialValues={{
            productName: "",
            subproductName: "",
            productDesc: "",
          }}
          validationSchema={SignupSchema}
          onSubmit={async (values) => {
            this.setState({ disabled: true })
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
              productName: values.productName,
              subproductname: values.subproductName,
              description: values.productDesc,
              createdby: this.state.username,
              admin:
                this.state.Role === "admin"
                  ? this.state.username
                  : this.state.selectedAdmin,
              fkUserId: "",
              category: this.state.category,
            };

            console.log("data", data);
            let result = await postProduct(data);
            console.log("Product", result);
            if (result.status === 200) {
              swal("Great", "Data added successfully", "success");
              // if (this.state.category === "BLE") {
                this.props.history.push("/list-of-product");
              // } else if (this.state.category === "GPS") {
              //   this.props.history.push("/gps-list-of-product");
              // }
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
          {({ errors, touched }) => (
            <Form>
              <div className="category_form">
                <h3>Add Product</h3>
                <div>
                  <label className="label1">Product Name</label>
                  <Field
                    className="form-control"
                    name="productName"
                    placeholder="Enter Product Name"
                  />
                  {errors.productName && touched.productName ? (
                    <div className="error__msg">{errors.productName}</div>
                  ) : null}
                </div>
                <div>
                  <label className="label1">Sub-Product Name</label>
                  <Field
                    className="form-control"
                    name="subproductName"
                    placeholder="Enter Sub-Product Name"
                  />
                </div>
                <div>
                  <label className="label1">Product Description</label>
                  <Field
                    className="form-control"
                    as="textarea"
                    name="productDesc"
                    placeholder="Enter Product Description"
                  />
                  {errors.productDesc && touched.productDesc ? (
                    <div className="error__msg">{errors.productDesc}</div>
                  ) : null}
                </div>
{/*               
                  {this.state.category === "GPS" ? (
                    <div>
                      <label className="label1">Select Admin Name</label>
                     
                      <Field
                        as="select"
                        className="form-control"
                        name="admin"
                        onClick={this.changeAdminName}
                        placeholder="Select Admin"
                      >
                        <option value="" hidden>Select Admin</option>
                        {this.state &&
                          this.state.allGpsAdminList &&
                          this.state.allGpsAdminList.map((obj) => (
                            <option value={`${obj.value}`}>{obj.label}</option>
                          ))}
                      </Field>
                    </div>
                  ) : (
                    <Field
                    as="select"
                    className="form-control"
                    name="admin"
                    onClick={this.changeAdminName}
                    placeholder="Select Admin"
                  >
                    <option value="" hidden>Select Admin</option>
                    {this.state &&
                      this.state.allAdminList &&
                      this.state.allAdminList.map((obj) => (
                        <option value={`${obj.value}`}>{obj.label}</option>
                      ))}
                  </Field>
                  )} */}
                 {this.state.Role !== "admin" ? (
                <div>
                  {/* <label className="label1">Select Admin Name</label> */}
                  <Field
                    as="select"
                    className="form-control"
                    name="admin"
                    onClick={this.changeAdminName}
                    placeholder="Select Admin"
                  >
                    <option value="" hidden>Select Admin</option>
                    {this.state &&
                      this.state.allAdminList &&
                      this.state.allAdminList.map((obj) => (
                        <option value={`${obj.value}`}>{obj.label}</option>
                      ))}
                  </Field>
                </div>
                  ) : (
                    ""
                  )}
             
                <div align="center">
                  <button type="submit" className="btn btn-primary" disabled={this.state.disabled}>
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
