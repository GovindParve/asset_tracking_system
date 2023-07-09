import React, { Component } from "react";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import moment from "moment";
import "./Product.css";
import { getDeviceUser } from "../../Service/getDeviceUser";
import { getAssetTagList } from "../../Service/getAssetTagList";
import { getProductListForAlloc } from "../../Service/getProductListForAlloc";
import { getSubProductList } from "../../Service/getSubProductList";
import { getDispatchList } from "../../Service/getDispatchList";

import { getAdmin } from "../../Service/getAdmin";
import Select from "react-select";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import { postProductAllocation } from "../../Service/postProductAllocation";
import swal from "sweetalert";
import "moment-timezone";

import { getAdminwiseTagDropDown } from "../../Service/getAdminwiseTagDropDown";

const SignupSchema = Yup.object().shape({
  productName: Yup.string().required("Select Product Name"),
  allocatedTagName: Yup.string().required("Select Tag Name"),
});

const timezones = timezoneNames()
  .filter((l) => l.indexOf("/") > -1)
  .sort((a, b) => a.localeCompare(b));

export default class AllocateProduct extends Component {
  // interval;
  state = {
    timezone: "Asia/Kolkata",
    Role: "",
    date: null,
    result: null,
    allData: [],
    allAdminData: [],
    selectedUser: "",
    selectedUserId: "",
    dispatchList: "",
    allCategory: [],
    allSubProduct: [],
    selectedCategory: "",
    selectedCategoryId: "",
    allProduct: [],
    allProductDetails: [],
    selectedProduct: "",
    selectedProductId: "",
    selectedUniqueCode: "",
    selectedassetimei: "",
    selectedAdmin: "",
    selectedAdminId: "",
    category: "",
    selectedLocation: "",
    selectedSubProduct: "",
    disabled: false
  };
  async componentDidMount() {
    this.setState({
      loader: true,
      Role: localStorage.getItem("role"),
      username: localStorage.getItem("username"),
      category: localStorage.getItem("categoryname")
    });
    let resultcategory = await getAssetTagList();
    console.log("resultcategory", resultcategory);
    let resultProduct = await getProductListForAlloc();
    console.log("Product Name", resultProduct);
    let resultSubProduct = await getSubProductList();
    console.log("Sub-Product", resultSubProduct);
    let result = await getDeviceUser();
    let resultDispatchList = await getDispatchList();
    console.log("result", resultDispatchList);
    let tempArray = [];
    result &&
      result.data &&
      result.data.map((obj) => {
        let tempObj = {
          id: `${obj.pkuserId}`,
          value: `${obj.firstName + " " + obj.lastName}`,
          label: `${obj.firstName + " " + obj.lastName}`,
        };
        tempArray.push(tempObj);
      });
    let tempCategoryArray = [];
    resultcategory &&
      resultcategory.data &&
      resultcategory.data.map((obj) => {
        let tempCategoryObj = {
          id: `${obj.assetTagId}`,
          value: `${obj.assetTagName}`,
          label: `${obj.assetTagName}`,
          Uniquecode: `${obj.assetUniqueCodeMacId}`,
          imeino: `${obj.assetIMEINumber}`
        };
        tempCategoryArray.push(tempCategoryObj);
      });

    let tempProductArray = [];
    resultProduct &&
      resultProduct.data &&
      resultProduct.data.map((obj) => {

        let tempProductObj = {
          id: `${obj.productId}`,
          value: `${obj.productId},${obj.productName}`,
          label: `${obj.productName}`,
        };
        tempProductArray.push(tempProductObj);
      });

    let tempSubProductArray = [];
    resultSubProduct &&
      resultSubProduct.data &&
      resultSubProduct.data.map((obj) => {
        let tempSubProductObj = {
          // id: `${obj.productId}`,
          value: `${obj.subproductName}`,
          label: `${obj.subproductName}`,
        };
        tempSubProductArray.push(tempSubProductObj);
      });

    let tempDispatchListArray = [];
    resultDispatchList &&
      resultDispatchList.data &&
      resultDispatchList.data.map((obj) => {
        let tempDispatchObj = {
          value: `${obj}`,
          label: `${obj}`,
        };

        tempDispatchListArray.push(tempDispatchObj);
      });

    this.setState({
      allData: tempArray,
      allCategory: tempCategoryArray,
      allProduct: tempProductArray,
      allSubProduct: tempSubProductArray,
      dispatchList: tempDispatchListArray,
      allProductDetails: resultSubProduct,
      Role: localStorage.getItem("role"),
    });

    let resultAdmin = await getAdmin();
    let tempAdminArray = [];
    resultAdmin &&
      resultAdmin.data &&
      resultAdmin.data.map((obj) => {

        let tempadminObj = {
          id: `${obj.pkuserId}`,
          value: `${obj.username}`,
          label: `${obj.username}`,
        };
        tempAdminArray.push(tempadminObj);
      });
    this.setState({ allAdminData: tempAdminArray });
  }

  handleTimezoneChange = (event) => {
    this.setState({ timezone: event.target.value });
  };

  changeUser = (selectedUser) => {
    this.setState(
      { selectedUser: selectedUser.value, selectedUserId: selectedUser.id },
      () => {
        console.log("fkuserid", this.state.selectedUser);
        console.log("selectedUserId", this.state.selectedUserId);
      }
    );
  };

  changeProductTag = (selectedCategory) => {
    console.log("selectedCategory", selectedCategory);
    this.setState(
      {
        selectedCategory: selectedCategory.value,
        selectedCategoryId: selectedCategory.id,
        selectedUniqueCode: selectedCategory.Uniquecode,
        selectedassetimei: selectedCategory.imeino,
      },
      () => {
        console.log("pkCategoryid", this.state.selectedCategory);
        console.log("selectedCategoryId", this.state.selectedCategoryId);
        console.log("selectedassetimei", this.state.selectedassetimei);
        // assetUniqueCodeMacId
      }
    );
  };
  changeDisptachName = (selectedLocation) => {
    if (document.getElementById("displaytable4").style.display === "none")
      document.getElementById("displaytable4").style.display = "block";
    else document.getElementById("displaytable4").style.display = "none";

    console.log("selectedLocation", selectedLocation);
    this.setState({ selectedLocation: selectedLocation.value }, () => {
      console.log("selectedLocation", this.state.selectedLocation);
    });
  };
  changeSubPeoductName = (selectedSubProduct) => {
    console.log("selected Sub Product", selectedSubProduct);
    this.setState(
      { selectedSubProduct: selectedSubProduct.value },
      async () => {
        console.log("selected Sub Product", this.state.selectedSubProduct);
      }
    );
  };

  changeProduct = (selectedProduct) => {
    console.log("selectedProduct", selectedProduct);
    this.setState(
      {
        selectedProduct: selectedProduct.value,
        selectedProductId: selectedProduct.id,
        selectedUniqueCode: selectedProduct.Uniquecode,
        selectedassetimei: selectedProduct.imeino,
      },
      () => {
        console.log("pkProductid", this.state.selectedProduct);
        console.log("selectedProductId", this.state.selectedProductId);
        console.log("selectedassetimei", this.state.selectedassetimei);
        // assetUniqueCodeMacId
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

  render() {
    const { result, date } = this.state;
    const { classes } = this.props;
    const { selected, hasError } = this.state;
    return (
      <div className="userAdd__wrapper">
        <h3>Product Allocation</h3>
        <br />
        <Formik
          initialValues={{
            productName: "",
            allocatedTagName: "",
            subproductName: "",
            admin: "",
          }}
          validationSchema={SignupSchema}
          onSubmit={async (values) => {
            this.setState({ disabled: true })
            const resultDate = moment().tz(this.state.timezone).format();
            let fkUserId = localStorage.getItem("fkUserId");
            let role = localStorage.getItem("role");
            let tempProductName = values.productName.split(",");
            let productName = tempProductName[1];
            console.log("values.productName", productName);
            if (localStorage.getItem("categoryname") !== "GPS" && localStorage.getItem("categoryname") !== "BLE") {
              this.setState({ category: localStorage.getItem("SelectedCategory") })
            }
            else {
              this.setState({ category: localStorage.getItem("categoryname") })
            }
            const payload = {
              productId: this.state.selectedProductId,
              productName: productName,
              allocatedTagName: values.allocatedTagName,
              allocatedTagId: this.state.selectedCategoryId,
              assetUniqueCodeOrMacId: this.state.selectedUniqueCode,
              assetimei: this.state.selectedassetimei,
              admin:
                role === "admin"
                  ? localStorage.getItem("username")
                  : values.admin,
              fkUserId: fkUserId,
              dispatchLocation: this.state.selectedLocation,
              subproductName: this.state.selectedSubProduct,
              dispatchtime: values.dispatchtime,
              category: this.state.category,
            };

            console.log("payload", payload);
            let result = await postProductAllocation(payload);
            console.log("Add Product Allocation", result);
            if (result.status === 200) {
              swal("Great", "Data added successfully", "success");
              this.props.history.push("/product-list");
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
              <div className="Product_form">
                <div>
                  <label className="label">Select Product Name</label>
                  <Select
                    options={this.state.allProduct}
                    onChange={(value) => {
                      setFieldValue("productName", value.value);
                      console.log(value.value);
                      this.setState({ selectedProductId: value.id }, () => {
                        let tempSubProductArray =
                          this.state.allProductDetails &&
                          this.state.allProductDetails.data &&
                          this.state.allProductDetails.data.filter(
                            (obj) =>
                              obj.productId == this.state.selectedProductId
                          );
                        console.log("Sub Product", tempSubProductArray);
                        let tempSubProductObj =
                          tempSubProductArray &&
                          tempSubProductArray.map((obj) => ({
                            value: `${obj.subproductName}`,
                            label: `${obj.subproductName}`,
                          }));
                        console.log("Sub Product", tempSubProductObj);
                        this.setState({ allSubProduct: tempSubProductObj });
                      });
                    }}
                    placeholder="Select Product Name"
                  />
                  {errors.productName && touched.productName ? (
                    <div className="error__msg">{errors.productName}</div>
                  ) : null}
                </div>
                <div>
                  <label className="label">Select Sub-Product Name</label>
                  <Select
                    options={this.state.allSubProduct}
                    onChange={this.changeSubPeoductName}
                    placeholder="Select Sub-Product Name"
                  />
                </div>
                {this.state.Role === "super-admin" ||
                  this.state.Role === "organization" ? (
                  <div>
                    <label className="label">Select Admin</label>
                    <Select
                      options={this.state.allAdminData}
                      placeholder="Select Admin"
                      onChange={async (value) => {
                        setFieldValue("admin", value.value);
                        let resultTagList = await getAdminwiseTagDropDown(
                          value.value
                        );
                        let tempTagArray =
                          resultTagList &&
                          resultTagList.data &&
                          resultTagList.data.map((obj) => ({
                            id: `${obj.assetTagId}`,
                            value: `${obj.assetTagName}`,
                            label: `${obj.assetTagName}`,
                            Uniquecode: `${obj.assetUniqueCodeMacId}`,
                            imeino: `${obj.assetIMEINumber}`

                          }));
                        this.setState({ allCategory: tempTagArray });
                      }}
                    />
                  </div>
                ) : (
                  ""
                )}
                <div>
                  <label className="label">Select Asset Tag Name</label>
                  <Select
                    options={this.state.allCategory}
                    onChange={(value) => {
                      setFieldValue("allocatedTagName", value.value);
                      this.setState({
                        selectedCategoryId: value.id,
                        selectedUniqueCode: value.Uniquecode,
                        selectedassetimei: value.imeino,
                      });
                    }}
                    placeholder="Select Asset Tag Name"
                  />
                  {errors.allocatedTagName && touched.allocatedTagName ? (
                    <div className="error__msg">{errors.allocatedTagName}</div>
                  ) : null}
                </div>
                <div>
                  <label className="label">Select Dispatch Location</label>
                  <Select
                    id="ddlPassport"
                    options={this.state.dispatchList}
                    onChange={this.changeDisptachName}
                    placeholder="Select Dispatch Location"
                  />
                  {errors.AssetTagType && touched.AssetTagType ? (
                    <div className="error__msg">{errors.AssetTagType}</div>
                  ) : null}
                </div>
                <div id="displaytable4" style={{ display: "none" }}>
                  <label className="label">Dispatch Time(In Minutes)</label>
                  <Field
                    className="form-control"
                    name="dispatchtime"
                    placeholder="Enter Dispatch Time"
                  />
                </div>
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