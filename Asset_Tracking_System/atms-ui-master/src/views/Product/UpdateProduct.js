import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import moment from "moment";
import './Product.css'
import { getEditProductAlloc } from '../../Service/getEditProduct'
import { getAssetTagList } from '../../Service/getAssetTagList'
import { getProductListForAlloc } from '../../Service/getProductListForAlloc'
import { getDispatchList } from '../../Service/getDispatchList'
import { getSubProductList } from '../../Service/getSubProductList'
import { Link, NavLink } from 'react-router-dom';
import { putProductAllocation } from '../../Service/putProductAllocation'
import Select from "react-select";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';
import "moment-timezone";

export default class UpdateProduct extends Component {
    state = {
        result: [],
        loader: false,
        dispatchList: "",
        allCategory: [],
        allSubProduct: [],
        selectedCategory: "",
        selectedCategoryId: "",
        allProduct: [],
        selectedProduct: "",
        selectedProductId: "",
        selectedUniqueCode: '',
        selectedLocation: '',
        selectedSubProduct: '',
        category: "",
        id: 0

    }
    componentDidMount = async () => {
        try {
            setTimeout(async () => {
                this.setState({ loader: true })
                if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
                    const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;

                    let result = await getEditProductAlloc(propId)
                    console.log('result', result.data)
                    let resultcategory = await getAssetTagList();
                    console.log('resultcategory', resultcategory);
                    let resultSubProduct = await getSubProductList();
                    console.log('resultProduct', resultSubProduct);
                    let resultProduct = await getProductListForAlloc();
                    console.log('resultProduct', resultProduct);
                    let resultDispatchList = await getDispatchList();
                    console.log('result', resultDispatchList);

                    let tempCategoryArray = []
                    resultcategory && resultcategory.data && resultcategory.data.map((obj) => {
                        console.log('objresultcategory', obj);
                        let tempCategoryObj = {
                            id: `${obj.assetTagId}`,
                            value: `${obj.assetTagName}`,
                            label: `${obj.assetTagName}`,
                            Uniquecode: `${obj.assetUniqueCodeMacId}`,
                            imeino: `${obj.assetimei}`
                        }
                        console.log('tempCategoryObj', tempCategoryObj);

                        tempCategoryArray.push(tempCategoryObj);
                    })
                    let tempProductArray = []
                    resultProduct && resultProduct.data && resultProduct.data.map((obj) => {
                        console.log('objresultProduct', obj);
                        let tempProductObj = {
                            id: `${obj.productId}`,
                            value: `${obj.productName}`,
                            label: `${obj.productName}`
                        }
                        console.log('tempProductObj', tempProductObj);
                        tempProductArray.push(tempProductObj);
                    })
                    let tempSubProductArray = []
                    resultSubProduct && resultSubProduct.data && resultSubProduct.data.map((obj) => {
                        console.log('result Sub Product', obj);
                        let tempSubProductObj = {
                            id: `${obj.productId}`,
                            value: `${obj.subproductName}`,
                            label: `${obj.subproductName}`
                        }
                        console.log('Sub Product', tempSubProductObj);
                        tempSubProductArray.push(tempSubProductObj);
                    })
                    let tempDispatchListArray = []
                    resultDispatchList && resultDispatchList.data && resultDispatchList.data.map((obj) => {
                        console.log('objresultProduct', obj);
                        let tempDispatchObj = {
                            value: `${obj}`,
                            label: `${obj}`
                        }
                        console.log('tempDispatchListArray', tempDispatchListArray);
                        tempDispatchListArray.push(tempDispatchObj);
                    })

                    this.setState({ allCategory: tempCategoryArray, selectedLocation: result.data.dispatchLocation, 
                        selectedProduct: result.data.productName, selectedSubProduct: result.data.subproductName, 
                        selectedProductId: result.data.productId, result: result.data, 
                        allProduct: tempProductArray, allSubProduct: tempSubProductArray, 
                        dispatchList: tempDispatchListArray, loader: false, id: propId, 
                        allProductDetails: resultSubProduct 
                    }, () => {
                        console.log('selectedSubProduct', this.state.selectedSubProduct)
                        console.log('allProduct', this.state.allProduct)
                    });
                } else if (this.props.match && this.props.match.params && this.props.match.params.id) {
                    const propParamId = this.props.match && this.props.match.params && this.props.match.params.id;
                    let result = await getEditProductAlloc(propParamId)
                    console.log('result', result.data)
                    let resultcategory = await getAssetTagList();
                    console.log('resultcategory', resultcategory);
                    let resultSubProduct = await getSubProductList();
                    console.log('resultProduct', resultSubProduct);
                    let resultProduct = await getProductListForAlloc();
                    console.log('resultProduct', resultProduct);
                    // let result = await getDeviceUser();
                    let resultDispatchList = await getDispatchList();
                    console.log('resultDispatchList', resultDispatchList);
                    // this.tick();
                    // this.interval = setInterval(() => this.tick(), 1000);
                    let tempCategoryArray = []
                    resultcategory && resultcategory.data && resultcategory.data.map((obj) => {
                        console.log('objresultcategory', obj);
                        let tempCategoryObj = {
                            id: `${obj.assetTagId}`,
                            value: `${obj.assetTagName}`,
                            label: `${obj.assetTagName}`,
                            Uniquecode: `${obj.assetUniqueCodeMacId}`,
                            imeino: `${obj.assetimei}`
                        }
                        console.log('tempCategoryObj', tempCategoryObj);
                        tempCategoryArray.push(tempCategoryObj);
                    })
                    let tempProductArray = []
                    resultProduct && resultProduct.data && resultProduct.data.map((obj) => {
                        console.log('objresultProduct', obj);
                        let tempProductObj = {
                            id: `${obj.productId}`,
                            value: `${obj.productName}`,
                            label: `${obj.productName}`
                        }
                        console.log('tempProductObj', tempProductObj);
                        tempProductArray.push(tempProductObj);
                    })
                    let tempSubProductArray = []
                    resultSubProduct && resultSubProduct.data && resultSubProduct.data.map((obj) => {
                        console.log('result Sub Product', obj);
                        let tempSubProductObj = {
                            id: `${obj.productId}`,
                            value: `${obj.subproductName}`,
                            label: `${obj.subproductName}`
                        }
                        console.log('Sub Product', tempSubProductObj);
                        tempSubProductArray.push(tempSubProductObj);
                    })
                    let tempDispatchListArray = []
                    resultDispatchList && resultDispatchList.data && resultDispatchList.data.map((obj) => {
                        console.log('objresultProduct', obj);
                        let tempDispatchObj = {
                            value: `${obj}`,
                            label: `${obj}`
                        }
                        console.log('tempDispatchListArray', tempDispatchListArray);

                        tempDispatchListArray.push(tempDispatchObj);
                    })

                    this.setState({ allCategory: tempCategoryArray, selectedLocation: result.data.dispatchLocation, 
                        selectedProduct: result.data.productName, selectedSubProduct: result.data.subproductName, 
                        selectedProductId: result.data.productId, result: result.data, 
                        allProduct: tempProductArray, allSubProduct: tempSubProductArray, 
                        dispatchList: tempDispatchListArray, id: propParamId, allProductDetails: resultSubProduct 
                    }, () => {
                       
                        console.log('selectedSubProduct', this.state.selectedSubProduct)
                        console.log('All Column Dropdown', this.state.allProduct)
                    });

                } else {
                    this.props.history.push("/product-list");
                }
            }, 200)

        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }
    }
    handleTimezoneChange = (event) => {
        this.setState({ timezone: event.target.value });
    };


    changeDisptachName = (selectedLocation) => {
        console.log('selectedLocation', selectedLocation)
        this.setState({ selectedLocation: selectedLocation.target.value }, () => {
            console.log('selectedLocation', this.state.selectedLocation)
        })
    }
    changeProduct = (selectedProduct) => {
        console.log('selectedProduct', selectedProduct.target.value)
        this.setState({ selectedProduct: selectedProduct.target.value }, () => {
            console.log('productname', this.state.selectedProduct)
            let tempSubProductArray = this.state.allProductDetails && this.state.allProductDetails.data && this.state.allProductDetails.data.filter((obj) => obj.productName === this.state.selectedProduct);
            console.log('Sub Product', tempSubProductArray);
            let tempSubProductObj = tempSubProductArray && tempSubProductArray.map((obj) => ({
                value: `${obj.subproductName}`,
                label: `${obj.subproductName}`
            }))
            console.log('Sub Product', tempSubProductObj);
            this.setState({ allSubProduct: tempSubProductObj });
        })
    }
    changeSubPeoductName = (selectedSubProduct) => {
        console.log('Sub Product', selectedSubProduct)
        this.setState({ selectedSubProduct: selectedSubProduct.target.value }, () => {
            console.log('selected Sub Product', this.state.selectedSubProduct)
        })
    }

    render() {
        const { loader } = this.state
        return (
            <>
                {loader ? <div class="loader"></div> :
                    <div className="userAdd__wrapper">
                        <h3>Edit Product Allocation</h3>
                        <br />
                        <br />
                        <Formik
                            initialValues={{
                                productname: this.state && this.state.result && this.state.result.productName,
                                allCategory: this.state && this.state.result && this.state.result.allocatedTagName,
                                dispatchLocation: this.state && this.state.result && this.state.result.dispatchLocation,
                                subproductName: this.state && this.state.result && this.state.result.subproductName,
                                category: this.state && this.state.result && this.state.result.category,
                                dispatchtime: this.state && this.state.result && this.state.result.dispatchtime
                            }}
                            // validationSchema={SignupSchema}
                            onSubmit={async values => {
                                var selectedProductId = this.state.allProduct.filter(productName => productName.value == values.productname);
                                var UpdateselectedProductId = selectedProductId[0].id;
                                console.log('UpdateselectedProductId', UpdateselectedProductId)
                                this.setState({ selectedProductId: UpdateselectedProductId })

                                let fkUserId = await localStorage.getItem("fkUserId")
                                if (localStorage.getItem("categoryname") !== "GPS" && localStorage.getItem("categoryname") !== "BLE") {
                                    this.setState({ category: localStorage.getItem("SelectedCategory") })
                                }
                                else {
                                    this.setState({ category: localStorage.getItem("categoryname") })
                                }
                                const payload = {
                                    productId: this.state.selectedProductId,
                                    productName: this.state.selectedProduct,
                                    allocatedTagName: this.state && this.state.result && this.state.result.allocatedTagName,
                                    allocatedTagId: this.state && this.state.result && this.state.result.allocatedTagId,
                                    assetUniqueCodeOrMacId: this.state && this.state.result && this.state.result.assetUniqueCodeOrMacId,
                                    assetimei: this.state && this.state.result && this.state.result.assetimei,
                                    admin: this.state && this.state.result && this.state.result.admin,
                                    fkUserId: fkUserId,
                                    dispatchLocation: this.state.selectedLocation,
                                    productAllocationId: parseInt(this.state.id),
                                    subproductName: this.state.selectedSubProduct,
                                    category: this.state.category,
                                    dispatchtime: values.dispatchtime
                                }
                                console.log('payload', payload);
                                let result = await putProductAllocation(payload)
                                console.log('Update Product Allocation', result);
                                if (result.status === 200) {
                                    swal("Great", "Data added successfully", "success");
                                    this.props.history.push("/product-list")
                                } else {
                                    swal("Failed", "Something went wrong please check your internet", "error");
                                }
                            }}
                        >
                            {({ errors, touched }) => (
                                <Form>
                                    <div className="productUpdate_form">
                                        <div>
                                            {/* <Select options={this.state.allProduct} name="productname" onChange={this.changeProduct} placeholder="Select Product Name" />    */}
                                            <label>Select Product</label>
                                            <Field as="select" className="form-control" name="productname" onClick={this.changeProduct} placeholder="Select Product Name">
                                                {this.state && this.state.allProduct && this.state.allProduct.map((obj) => (
                                                    <option value={`${obj.value}`}>{obj.value}</option>
                                                ))}
                                            </Field>
                                        </div>
                                        <div>
                                            {/* <Select options={this.state.allProduct} name="productname" onChange={this.changeProduct} placeholder="Select Product Name" />    */}
                                            <label>Select Sub-Product</label>
                                            <Field as="select" className="form-control" name="subproductName" onClick={this.changeSubPeoductName} placeholder="Select Sub-Product Name">
                                                {this.state && this.state.allSubProduct && this.state.allSubProduct.map((obj) => (
                                                    <option value={`${obj.value}`}>{obj.value}</option>
                                                ))}
                                            </Field>
                                        </div>
                                        <div>
                                            <label>Tag Name</label>
                                            {/* <Select options={this.state.allCategory} onChange={this.changeProductTag} placeholder="Select Asset Tag Name" /> */}
                                            <Field className="form-control" name="allCategory" readOnly="true" />
                                        </div>
                                        <div>
                                            {/* <Select options={this.state.dispatchList} onChange={this.changeDisptachName} placeholder="Select Dispatch Location" /> */}
                                            <label>Select Dispatch Location</label>
                                            <Field as="select" className="form-control" name="dispatchLocation" onClick={this.changeDisptachName} placeholder="Select Dispatch Location">
                                                {this.state && this.state.dispatchList && this.state.dispatchList.map((obj) => (
                                                    <option value={`${obj.value}`}>{obj.value}</option>
                                                ))}
                                            </Field>
                                        </div>
                                        <div>
                                            <label className='label'>Dispatch Time</label>
                                            <Field className="form-control" name="dispatchtime" placeholder="Enter Dispatch Time" />
                                        </div>
                                    </div>
                                    <br />
                                    <div className='btn-product' align="center">
                                        <button type="submit" className="btn btn-primary">Submit</button>
                                        <button type="cancel" className="btn btn-primary"> <Link to={{ pathname: "/gatway_list" }}>Cancel</Link></button>
                                    </div>
                                </Form>
                            )}
                        </Formik>
                    </div>
                }
            </>
        )
    }
}
