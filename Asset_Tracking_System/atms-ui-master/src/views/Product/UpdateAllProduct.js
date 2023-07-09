import React, { Component } from "react";
import "./Product.css";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import { putProductById } from "../../Service/putProductById";
import { getSingleProduct } from "../../Service/getSingleProduct";
import swal from "sweetalert";
import { Link, NavLink } from "react-router-dom";
import { getAssetCategory } from "../../Service/getAssetCategory";
import Select from "react-select";
import { getAdminWiseUserList } from "../../Service/getAdminWiseUserList";

const SignupSchema = Yup.object().shape({
  // firstName: Yup.string()
  //     .min(2, 'Too Short!')
  //     .max(50, 'Too Long!')
  //     .required('Enter First Name'),
  // lastName: Yup.string()
  //     .min(2, 'Too Short!')
  //     .max(50, 'Too Long!')
  //     .required('Enter Last Name'),
  // phone: Yup.string()
  //     .min(10, 'Too Short!')
  //     .max(10, 'Too Long!')
  //     .required('Enter Phone No'),
  // email: Yup.string().email('Invalid Email_Id').required('Enter Email_id'),
  // password: Yup.string().required('Enter Password'),
  // address: Yup.string().required('Enter Address'),
  // type_of: Yup.string().required('Select Role'),
  // userName: Yup.string().required('Enter User Name'),
  // companyname: Yup.string().required('Enter Company Name'),
});
export default class UpdateAllProduct extends Component {
  state = {
    result: [],
    id: 0,
    loader: false,
    allCategory: [],
    Role: "",
    category: "",
    username: "",
    allAdminList: [],
    selectedAdmin: "",
    selectedAdminId: "",
  };

  componentDidMount = async () => {
    try {
      setTimeout(async () => {
        this.setState({
          loader: true,
          Role: localStorage.getItem("role"),
          username: localStorage.getItem("username"),
        });
        if (
          this.props.location &&
          this.props.location.state &&
          this.props.location.state.deviceId
        ) {
          const propId =
            this.props.location &&
            this.props.location.state &&
            this.props.location.state.deviceId;

          let result = await getSingleProduct(propId);
          console.log("result", result.data);
          this.setState({
            result: result && result.data,
            id: propId,
            loader: false,
          });
          // let resultcategory = await getAssetCategory();
          // console.log('resultcategory', resultcategory);
          // let tempCategoryArray = []
          // resultcategory && resultcategory.data && resultcategory.data.map((obj) => {
          //     console.log('objresultcategory', obj);
          //     let tempCategoryObj = {
          //         id: `${obj.pkCatId}`,
          //         value: `${obj.categoryName}`,
          //         label: `${obj.categoryName}`
          //     }
          //     console.log('tempCategoryObj', tempCategoryObj);

          //     tempCategoryArray.push(tempCategoryObj);
          // })

          // this.setState({ allCategory: tempCategoryArray });
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
        } else if (
          this.props.match &&
          this.props.match.params &&
          this.props.match.params.id
        ) {
          const propParamId =
            this.props.match &&
            this.props.match.params &&
            this.props.match.params.id;
          let result = await getSingleProduct(propParamId);
          this.setState({
            result: result && result.data,
            id: propParamId,
            loader: false,
          });
          // let resultcategory = await getAssetCategory();
          // console.log('resultcategory', resultcategory);
          // let tempCategoryArray = []
          // resultcategory && resultcategory.data && resultcategory.data.map((obj) => {
          //     console.log('objresultcategory', obj);
          //     let tempCategoryObj = {
          //         id: `${obj.pkCatId}`,
          //         value: `${obj.categoryName}`,
          //         label: `${obj.categoryName}`
          //     }
          //     console.log('tempCategoryObj', tempCategoryObj);

          //     tempCategoryArray.push(tempCategoryObj);
          // })

          // this.setState({ allCategory: tempCategoryArray, });
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
        } else {
          this.props.history.push("/list-of-product");
        }
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
  changeAdminName = (selectedAdmin) => {
    console.log("selectedAdmin", selectedAdmin);
    this.setState({ selectedAdmin: selectedAdmin.target.value }, () => {
      console.log("admin", this.state.selectedAdmin);
    });
  };

  render() {
    const { loader } = this.state;
    return (
      <>
        {loader ? (
          <div class="loader"></div>
        ) : (
          <div className="userAdd__wrapper">
            <Formik
              enableReinitialize={true}
              initialValues={{
                productName:
                  this.state &&
                  this.state.result &&
                  this.state.result.productName,
                subproductName:
                  this.state &&
                  this.state.result &&
                  this.state.result.subproductName,
                description:
                  this.state &&
                  this.state.result &&
                  this.state.result.description,
                category:
                  this.state && this.state.result && this.state.result.category,
                admin:
                  this.state && this.state.result && this.state.result.admin,
              }}
              validationSchema={SignupSchema}
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
                  this.setState({
                    category: localStorage.getItem("categoryname"),
                  });
                }
                const data = {
                  productId: parseInt(this.state.id),
                  productName: values.productName,
                  subproductname: values.subproductName,
                  description: values.description,
                  createdby:
                    this.state &&
                    this.state.result &&
                    this.state.result.createdby,
                  updatedby: this.state.username,
                  admin:
                    this.state.Role === "admin"
                      ? this.state.username
                      : values.admin,
                  fkUserId: "",
                  category: this.state.category,
                };
                console.log("data", data);
                let result = await putProductById(data);
                console.log("result", result);
                if (result.status === 200) {
                  swal("Great", "Product Update Successfully", "success");
                  // if (this.state.category === "BLE") {
                    this.props.history.push("/list-of-product");
                  // } else if (this.state.category === "GPS") {
                  //   this.props.history.push("/gps-product-list");
                  // }
                  // this.props.history.push("/users")
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
                    <h3>Edit Product</h3>
                    <div>
                      <label className="label1">Product Name</label>
                      <Field
                        className="form-control"
                        name="productName"
                        placeholder="Product Name"
                      />
                    </div>
                    <div>
                      <label className="label1">Sub-Product Name</label>
                      <Field
                        className="form-control"
                        name="subproductName"
                        placeholder="Sub-Product Name"
                      />
                    </div>
                    <div>
                      <label className="label1">Product Description</label>
                      <Field
                        className="form-control"
                        as="textarea"
                        name="description"
                        placeholder="Product Description"
                      />
                      {/* {errors.productDesc && touched.productDesc ? (
                                                <div className="error__msg">{errors.productDesc}</div>
                                            ) : null} */}
                    </div>
                    {this.state.Role !== "admin" ? (
                      <div>
                        <label className="label1">Select Admin Name</label>
                        {/* <Select options={this.state.allAdminList} onChange={this.changeAdminName} placeholder="Select Admin" /> */}
                        <Field
                          as="select"
                          className="form-control"
                          name="admin"
                          onClick={this.changeAdminName}
                          placeholder="Select Admin"
                        >
                          {this.state &&
                            this.state.allAdminList &&
                            this.state.allAdminList.map((obj) => (
                              <option value={`${obj.value}`}>
                                {obj.label}
                              </option>
                            ))}
                        </Field>
                      </div>
                    ) : (
                      ""
                    )}
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
        )}
      </>
    );
  }
}
