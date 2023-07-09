import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import moment from "moment";
import './Product.css'
import { getDeviceUser } from '../../Service/getDeviceUser'
import { getAssetTagList } from '../../Service/getAssetTagList'
import { getProductListForAlloc } from '../../Service/getProductListForAlloc'
import { getDispatchList } from '../../Service/getDispatchList'

import { getAdmin } from '../../Service/getAdmin'
import Select from "react-select";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';


import "moment-timezone";

import { postProductAllocation } from '../../Service/postProductAllocation'

const SignupSchema = Yup.object().shape({

    productname: Yup.string()
        .required('Select Product Name'),

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
        selectedUser: "",
        selectedUserId: "",
        dispatchList: "",
        allCategory: [],
        selectedCategory: "",
        selectedCategoryId: "",
        allProduct: [],
        selectedProduct: "",
        selectedProductId: "",
        selectedUniqueCode: '',
        selectedAdmin: "",
        selectedAdminId: "",
        selectedLocation: ''
    }
    async componentDidMount() {

        let resultcategory = await getAssetTagList();
        console.log('resultcategory', resultcategory);
        let resultProduct = await getProductListForAlloc();
        console.log('resultProduct', resultProduct);
        let result = await getDeviceUser();
        let resultDispatchList = await getDispatchList();
        console.log('result', resultDispatchList);
        // this.tick();
        // this.interval = setInterval(() => this.tick(), 1000);
        let tempArray = []
        result && result.data && result.data.map((obj) => {
            console.log('objresult', obj);
            let tempObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.firstName + " " + obj.lastName}`,
                label: `${obj.firstName + " " + obj.lastName}`
            }
            console.log('tempObj', tempObj);

            tempArray.push(tempObj);
        })
        let tempCategoryArray = []
        resultcategory && resultcategory.data && resultcategory.data.map((obj) => {
            console.log('objresultcategory', obj);
            let tempCategoryObj = {
                id: `${obj.assetTagId}`,
                value: `${obj.assetTagName}`,
                label: `${obj.assetTagName}`,
                Uniquecode: `${obj.assetUniqueCodeMacId}`
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

        this.setState({ allData: tempArray, allCategory: tempCategoryArray, allProduct: tempProductArray, dispatchList: tempDispatchListArray });

        let resultAdmin = await getAdmin();
        console.log('admin', resultAdmin)
        let tempAdminArray = []
        resultAdmin && resultAdmin.data && resultAdmin.data.map((obj) => {
            console.log('resultAdmin', obj);
            let tempadminObj = {
                id: `${obj.pkuserId}`,
                value: `${obj.firstName + " " + obj.lastName}`,
                label: `${obj.firstName + " " + obj.lastName}`
            }

            tempAdminArray.push(tempadminObj)
        })

        this.setState({ allAdminData: tempAdminArray }, () => {
            console.log('admin', this.state.allAdminData)
        })
    }


    handleTimezoneChange = (event) => {
        this.setState({ timezone: event.target.value });
    };

    changeUser = (selectedUser) => {

        this.setState({ selectedUser: selectedUser.value, selectedUserId: selectedUser.id }, () => {
            console.log('fkuserid', this.state.selectedUser)
            console.log('selectedUserId', this.state.selectedUserId)
        })
    }

    changeProductTag = (selectedCategory) => {
        console.log('selectedCategory', selectedCategory)
        this.setState({ selectedCategory: selectedCategory.value, selectedCategoryId: selectedCategory.id, selectedUniqueCode: selectedCategory.Uniquecode }, () => {
            console.log('pkCategoryid', this.state.selectedCategory)
            console.log('selectedCategoryId', this.state.selectedCategoryId)
            // assetUniqueCodeMacId
        })
    }
    changeDisptachName = (selectedLocation) => {
        console.log('selectedLocation', selectedLocation)
        this.setState({ selectedLocation: selectedLocation.value }, () => {
            console.log('selectedLocation', this.state.selectedLocation)


        })
    }
    changeProduct = (selectedProduct) => {
        console.log('selectedProduct', selectedProduct)
        this.setState({ selectedProduct: selectedProduct.value, selectedProductId: selectedProduct.id, selectedUniqueCode: selectedProduct.Uniquecode }, () => {
            console.log('pkProductid', this.state.selectedProduct)
            console.log('selectedProductId', this.state.selectedProductId)
            // assetUniqueCodeMacId
        })

    }
    changeAdmin = (selectedAdmin) => {
        console.log('selectedAdmin', selectedAdmin)
        this.setState({ selectedAdmin: selectedAdmin.value, selectedAdminId: selectedAdmin.id }, () => {

            console.log('admin', this.state.selectedAdmin);
            console.log('adminid', this.state.selectedAdminId);
        })
    }


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
                        productname: ''
                    }}
                    //  validationSchema={SignupSchema}

                    onSubmit={async values => {

                        const resultDate = moment()
                            .tz(this.state.timezone)
                            .format()
                        // const tempVal = resultDate.tz(this.state.timezone)
                        // const wakeTime = moment(values.wakeupTime).format("HH:mm")

                        let fkUserId = await localStorage.getItem("fkUserId")
                        const payload = {
                            productId: this.state.selectedProductId,
                            productName: this.state.selectedProduct,
                            allocatedTagName: this.state.selectedCategory,
                            allocatedTagId: this.state.selectedCategoryId,
                            assetUniqueCodeOrMacId: this.state.selectedUniqueCode,
                            fkUserId: fkUserId,
                            dispatchLocation: this.state.selectedLocation
                        }

                        console.log('payload', payload);
                       let result = await postProductAllocation(payload)

                        if (result.status === 200) {
                            swal("Great", "Data added successfully", "success");

                            this.props.history.push("/product-list")
                        }
                        else {
                            swal("Failed", "Something went wrong please check your internet", "error");
                        }
                    }}
                >
                    {({ errors, touched }) => (
                        <Form >

                            <div className="Device__form__wrapper">
                                <div>
                                    <Select options={this.state.allProduct} name="productName" onChange={this.changeProduct} placeholder="Select Product Name" required="true" />
                                    {errors.productName && touched.productName ? (
                                        <div className="error__msg">{errors.productName}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <Select options={this.state.allCategory} onChange={this.changeProductTag} placeholder="Select Asset Tag Name" />


                                    {errors.AssetTagType && touched.AssetTagType ? (
                                        <div className="error__msg">{errors.AssetTagType}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <Select options={this.state.dispatchList} onChange={this.changeDisptachName} placeholder="Select Dispatch Location" />


                                    {errors.AssetTagType && touched.AssetTagType ? (
                                        <div className="error__msg">{errors.AssetTagType}</div>
                                    ) : null}
                                </div>
                            </div>


                            <br />
                            <div align="center">
                                <button type="submit" className="btn btn-primary" >Submit</button>
                            </div>

                        </Form>
                    )}
                </Formik>
            </div>
        )
    }
}


