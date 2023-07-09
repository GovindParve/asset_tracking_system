import React, { Component } from 'react'
import './Product.css'
import { deleteProduct } from "../../Service/deleteProduct"
import { getAllGPSProductList } from '../../Service/getAllGPSProductList'
import Select from "react-select";
import { getGPSProductListForFilter } from '../../Service/getGPSProductListForFilter'
import { getProductListByName } from '../../Service/getProductListByName'
//import { getPageWiseProductList } from '../../Service/getPageWiseProductList'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";
import Swal from 'sweetalert2'
import Axios from "../../utils/axiosInstance";

export default class AddGPSProductList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            allProduct: [],
            selectedProduct: "",
            selectedProductId: "",
            offset: 0,
            data: [],
            tableData: [],
            tableGatewayData: [],
            orgtableData: [],
            perPage: 10,
            currentPage: 0,
            allData: [],
            checkedStatus: [],
            checkedValue: [],
            allCategory: [],
            selectedDevice: "",
            selectedCategory: "",
            Role: "",
            loader: false,
        };

        this.handlePageClick = this
            .handlePageClick
            .bind(this);
    }
    handlePageClick = async (e) => {
        const selectedPage = e.selected;
        if (this.state.selectedProduct === "") {
            let result = await getAllGPSProductList(selectedPage);
            console.log("Product List Result", result.data);
            if (result && result.data && result.data.length !== 0) {
                const data = result.data.content;
                this.setState({
                    currentPage: selectedPage,
                    pageCount: result.data.totalPages,
                    tableData: data,
                    offset: result.data.pageable.offset,
                    loader: false,
                });
            }
        } 
        // else if (this.state.selectedProduct !== "") {
        //     let result = await getProductListByName(selectedPage, this.state.selectedProduct);
        //     console.log("Product List Pagination", result.data);
        //     if (result && result.data && result.data.length !== 0) {
        //         const data = result.data.content;
        //         this.setState({
        //             currentPage: selectedPage,
        //             pageCount: result.data.totalPages,
        //             tableData: data,
        //             offset: result.data.pageable.offset,
        //             loader: false,
        //         });
        //     }
        // }
    };

    async componentDidMount() {
        try {
            this.setState({ loader: true })
            this.getProductData();


            let resultProduct = await getGPSProductListForFilter();
            console.log('result GPS Product', resultProduct.data);
            var setRole = await localStorage.getItem('role');
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
            //console.log("Product Data Pagination",result)
            this.setState({ Role: setRole, loader: false, allProduct: tempProductArray }, () => {


            })
        } catch (error) {
            console.log(error)
            swal("Sorry", "Data is not present", "warning");

        }

    }
    async getProductData() {
        let result = await getAllGPSProductList(this.state.currentPage)
        console.log("Total Product List", result);
        if (result && result.data && result.data.content && result.data.content.length !== 0) {
            const data = result.data.content;
            this.setState(
                {
                    pageCount: result.data.totalPages,
                    tableData: data,
                    loader: false,
                }, () => {
                    console.log("ProductList", result);
                })
        } else {
            this.setState(
                {
                    pageCount: {},
                    tableData: [],
                }
            );
            swal("Sorry", "Data is not present", "warning");
        }
    }

    // changeProduct = async (selectedProduct) => {
    //     console.log('selectedProduct', selectedProduct)
    //     if (selectedProduct.value == "") {
    //         let result = await getAllGPSProductList(this.state.currentPage)
    //         this.setState({ allData: result && result.data }, () => {
    //             let temp = []
    //             this.state.allData.map((obj, key) => {
    //                 const objTemp = { [key]: false }
    //                 temp.push(objTemp)
    //             })
    //             this.setState({ checkedStatus: temp })
    //         })
    //     } else {
    //         this.setState({ selectedProduct: selectedProduct.value }, async () => {
    //             let result = await getProductListByName(this.state.currentPage, this.state.selectedProduct);
    //             // this.setState({ tableData: result && result.data }, () => {                   
    //             if (result && result.data && result.data.length !== 0) {
    //                 const data = result.data.content;
    //                 this.setState(
    //                     {
    //                         pageCount: result.data.totalPages,
    //                         tableData: data,
    //                         loader: false,
    //                     },
    //                     () => {
    //                         let temp = [];
    //                         this.state.tableData.map((obj, key) => {
    //                             const objTemp = { [key]: false };
    //                             temp.push(objTemp);
    //                         });
    //                         this.setState({ checkedStatus: temp });
    //                     }
    //                 );
    //             } else {

    //                 this.setState(
    //                     {
    //                         pageCount: {},
    //                         tableData: [],
    //                     }
    //                 );
    //                 swal("Sorry", "Data is not present", "warning");
    //             }
    //         })
    //         // })
    //     }
    // }
    redirectToCreateUser = () => {
        this.props.history.push("/product-gps-create")
    }
    redirectToAllocateUser = () => {
        this.props.history.push("/product-gps-allocation")
    }

    clickCard = async (id) => {
        this.props.history.push({
            pathname: `/edit-product/${id}`,
            state: { deviceId: id },
        });
    }


    clickHitory = async (tag) => {
        //   console.log("ProductName", product)
        this.props.history.push({
            pathname: `/product-allocate-history/${tag}`,
            state: { deviceId: tag },
        });
    }

    changeStatus = (value, e) => {
        console.log(value, e.target.value)
        let checkedValueTemp = this.state.checkedValue

        if (e.target.checked) {
            checkedValueTemp.push(parseInt(e.target.value))
            this.setState({ checkedValue: checkedValueTemp })

        } else {
            const filteredItems = checkedValueTemp.filter(item => item !== parseInt(e.target.value));
            console.log('filteredItems', filteredItems)
            checkedValueTemp = filteredItems;
            this.setState({ checkedValue: checkedValueTemp });
        }
        let temp = this.state.checkedStatus
        temp[value][`${value}`] = !temp[value][`${value}`]
    }

    allCheckUncheked = (e) => {
        let temp = [];
        let checkedArray = []
        if (e.target.checked) {
            console.log('value', e.target.checked);
            this.state.tableData.map((obj, key) => {
                const objTemp = { [key]: true }
                temp.push(objTemp)
                checkedArray.push(parseInt(obj.productAllocationId))
            })
            this.setState({ checkedStatus: temp, checkedValue: checkedArray })
        } else {
            let temp = []
            this.state.tableData.map((obj, key) => {
                const objTemp = { [key]: false }
                temp.push(objTemp)
            })
            this.setState({ checkedStatus: temp, checkedValue: [] })
        }
    }

    deleteProduct = async (selectedId) => {
        console.log('delete', selectedId.target.id)
        try {
            let result = await deleteProduct(selectedId.target.id)

            if (result.status === 200) {
                alert("Product deleted")
                // window.location.reload();
            } else {
                alert("something went wrong please check your connection")
            }
        } catch (error) {
            console.log(error)
        }
    }
    deleteDevice = async () => {

        console.log('delete', this.state.checkedValue)
        Swal.fire({
            title: 'Are you sure want to Delete?',
           // text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#c01664',
            confirmButtonText: 'Yes, Delete it!'
          }).then(async (resultResponse) => {
            if (resultResponse.isConfirmed) {
            let token = localStorage.getItem("token");
            return Axios.post('/product/delete-multipleproduct', this.state.checkedValue, { headers: { "Authorization": `Bearer ${token}` } })
                .then(resultResponse => {
                    console.log("Delete User", resultResponse);
                          Swal.fire({
                           position: 'top-center',
                            icon: 'success',
                            title: 'Product Is Deleted...!',
                            showConfirmButton: false,
                            timer: 3000
                          })
                          window.location.reload()
                          return Promise.resolve();
                    }) 
                }
                }).catch(resultResponse => {
                    swal("Failed", "Somthing went wrong", "error");
                    console.log("Delete User ", resultResponse);
                });
    }

    redirectToUploadExcel = () => {
        this.props.history.push("/upload-product-excel")
    }

    render() {
        const { allData, tableData, loader } = this.state

        return (
            <>
                <div>
                    {(this.state.Role === 'admin' || this.state.Role === 'organization') ? (<div className="device__btn__wrapper">
                        <button className="btn btn-primary" onClick={this.redirectToCreateUser} >Create Product</button>&nbsp;&nbsp;
                        <a href="Product-bulk-upload.xlsx" download="Product-bulk-upload.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                        <button className="btn btn-primary" onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                    </div>)
                        : (this.state.Role === 'user' || this.state.Role === 'empuser') ? (<div className="device__btn__wrapper" >
                            <button className="btn btn-primary" onClick={this.redirectToCreateUser} style={{ display: 'none' }}>Create Product</button>&nbsp;&nbsp;
                            <a href="Product-bulk-upload.xlsx" download="Product-bulk-upload.xlsx" style={{ display: 'none' }}><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                            <button className="btn btn-primary" onClick={this.redirectToUploadExcel} style={{ display: 'none' }}>Upload Excel</button>&nbsp;&nbsp;
                        </div>)
                            : (<div className="device__btn__wrapper">
                                <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create Product</button>&nbsp;&nbsp;
                                <a href="Product-bulk-upload.xlsx" download="Product-bulk-upload.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                                <button className="btn btn-primary" onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                            </div>)}
                    {/* <br /> */}
                    {/* <div className="deviceList">
                        <div>
                            <label><strong>SELECT PRODUCT NAME</strong></label>
                            <Select options={this.state.allProduct} onChange={this.changeProduct} placeholder="Select Product Name" />
                        </div>
                    </div> */}
                     <div className="payload__btn__wrapper">
                        <div>
                            &nbsp;&nbsp;&nbsp;
                            {this.state.checkedValue.length === 0 ?
                                "" :
                                <button className="btn btn-danger" onClick={this.deleteDevice}>Delete</button>}
                        </div>
                    </div>
                    <br />
                    <br />
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table__scroll table-hover table-bordered table-striped text-center">
                            <tr className="tablerow">
                                {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <>
                                    <th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                    <th style={{ display: 'none' }}>EDIT</th> </> :
                                    (this.state.Role === 'user' || this.state.Role === 'empuser') ? <> <th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                        <th style={{ display: 'none' }}>EDIT</th> </> : <>
                                        <th><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                    </>}
                                <th>Sr_No</th>
                                <th>Product_Name</th>
                                <th>Sub_Product_Name</th>
                                <th>Description</th>
                                <th>User_Name</th>
                                {/* <th>Dispatch_Location</th>
                                <th>Tag-allocate-History</th> */}
                                <th>Edit</th>
                                {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <>
                                    <th style={{ display: 'none' }}>
                                        <input type="checkbox" onChange={this.allCheckUncheked} />
                                    </th>
                                </> : (this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                    <th style={{ display: 'none' }}>
                                        <input type="checkbox" onChange={this.allCheckUncheked} />
                                    </th>
                                </> : <>
                                </>}
                            </tr>
                            <tbody>
                                {tableData.map((obj, key) => (
                                    <>
                                        <tr className="Device__table__col">
                                            {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <> <th style={{ display: 'none' }}><div key={key}>
                                                <input type="checkbox" value={obj.assetTagId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                            </div></th>
                                                <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.assetTagId)}><button className="btn btn-info">EDIT</button></td></>
                                                : (this.state.Role === 'user' || this.state.Role === 'empuser') ? <><th style={{ display: 'none' }}><div key={key}>
                                                    <input type="checkbox" value={obj.assetTagId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                                </div></th>
                                                    <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.assetTagId)}><button className="btn btn-info">EDIT</button></td></>
                                                    : <>
                                                        <th><div key={key}>
                                                            <input type="checkbox" value={obj.assetTagId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                                        </div></th>
                                                    </>}
                                            <td>
                                                {this.state.offset + key + 1}
                                            </td>
                                            <td>
                                                {obj.productName}
                                            </td>
                                            <td>
                                                {obj.subproductName}
                                            </td>
                                            <td>
                                                {obj.description}
                                            </td>
                                            {/* <td>
                                                {obj.dispatchLocation}
                                            </td> */}
                                            <td>
                                                {obj.user}
                                            </td>
                                            {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <>
                                                <td onClick={() => this.clickCard(obj.productId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                    <i className="fa fa-edit fa-lg "></i>
                                                </td>

                                            </> : (this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                                <td onClick={() => this.clickCard(obj.productId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                    <i className="fa fa-edit fa-lg "></i>
                                                </td>
                                            </> : <>
                                                <td onClick={() => this.clickCard(obj.productId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                    <i className="fa fa-edit fa-lg "></i>
                                                </td>
                                            </>}
                                        </tr>
                                    </>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <ReactPaginate
                        previousLabel={<i class="fa fa-angle-left"></i>}
                        nextLabel={<i class="fa fa-angle-right"></i>}
                        breakLabel={"..."}
                        breakClassName={"break-me"}
                        pageCount={this.state.pageCount}
                        marginPagesDisplayed={2}
                        pageRangeDisplayed={5}
                        onPageChange={this.handlePageClick}
                        containerClassName={"pagination"}
                        subContainerClassName={"pages pagination"}
                        activeClassName={"active"} />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                </div>
            </>
        )
    }
}
