import React, { Component } from 'react'
import './DynamicColumn.css'
import { deleteColumn } from "../../Service/deleteColumn"
import { getColumnList } from '../../Service/getColumnList'
import Select from "react-select";
//import { getPageWiseProductList } from '../../Service/getPageWiseProductList'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";
import Swal from 'sweetalert2'
import Axios from "../../utils/axiosInstance";

export default class DynamicColumn extends Component {

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

        // this.handlePageClick = this
        //     .handlePageClick
        //     .bind(this);
    }
    // handlePageClick = async (e) => {
    //     const selectedPage = e.selected;
    //     if (this.state.selectedProduct === "") {
    //         let result = await getProductList(selectedPage);
    //         console.log("Product List Result", result);
    //         if (result && result.data && result.data.length !== 0) {
    //             const data = result.data.content;
    //             this.setState({
    //                 currentPage: selectedPage,
    //                 pageCount: result.data.totalPages,
    //                 tableData: data,
    //                 offset: result.data.pageable.offset,
    //                 loader: false,
    //             });
    //         }
    //     } else if (this.state.selectedProduct !== "") {
    //         let result = await getProductListByName(selectedPage, this.state.selectedProduct);
    //         console.log("Product List Pagination", result);
    //         if (result && result.data && result.data.length !== 0) {
    //             const data = result.data.content;
    //             this.setState({
    //                 currentPage: selectedPage,
    //                 pageCount: result.data.totalPages,
    //                 tableData: data,
    //                 offset: result.data.pageable.offset,
    //                 loader: false,
    //             });
    //         }
    //     }
    // };

    async componentDidMount() {
        try {
            this.setState({ loader: true })
            //this.getProductData();
            var setRole = await localStorage.getItem('role');
            let result = await getColumnList()
            this.setState({ allData: result && result.data, Role: setRole, loader: false}, () => {
                console.log('Column Data', this.state.allData)
                // if (result && result.data && result.data.length !== 0) {
                //     const data = result.data.content;
                //     this.setState(
                //         {
                //             pageCount: result.data.totalPages,
                //             tableData: data,
                //             loader: false,
                //         },
                //         () => {
                //             let temp = [];
                //             this.state.tableData.map((obj, key) => {
                //                 const objTemp = { [key]: false };
                //                 temp.push(objTemp);
                //             });
                //             this.setState({ checkedStatus: temp });
                //         }
                //     );
                // }
            })
        } 
        catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }

    }

 
    redirectToCreateUser = () => {
        this.props.history.push("/column-create")
    }
    clickCard = async (id) => {
        this.props.history.push({
            pathname: `/update-column/${id}`,
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

    deleteColumn = async (e) => {
        // console.log('Column delete', selectedId.target.id)
        // try {
        //     let result = await deleteColumn(selectedId.target.id)
        //     console.log("Delete Column",result)
        //     if (result.status === 200) {
        //         alert("Column deleted")
        //         window.location.reload();
        //     } else {
        //         alert("something went wrong please check your connection")
        //     }
        // } catch (error) {
        //     console.log(error)
        // }
        e.persist()
        console.log('delete', e.target.id)
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
            return Axios.delete(`/asset/tracking/delete-mapping-with-column/${e.target.id}`, { headers: { "Authorization": `Bearer ${token}` } })
                .then(resultResponse => {
                    console.log("Delete User", resultResponse);
                          Swal.fire({
                           position: 'top-center',
                            icon: 'success',
                            title: 'Parameter Is Deleted...!',
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
    render() {
        const { allData, tableData, loader } = this.state

        return (
            <>
                <div>
                <div className="device__btn__wrapper">
                                <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create Parameters</button>
                            </div>
                    <br />
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table__scroll table-hover table-bordered table-striped text-center">

                            <tr className="tablerow">
                                <th>Sr_No</th>
                                <th>Column_Name</th>
                                <th>Allocated_Parameters</th>
                                <th>Parameter_Display_UI</th>
                                <th>Unit</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            <tbody>
                                {allData.map((obj, key) => (
                                    <>
                                        <tr className="Device__table__col">
                                            <td>
                                                {this.state.offset + key + 1}
                                            </td>
                                            <td>
                                                {obj.columnName}
                                            </td>
                                            <td>
                                                {obj.allocatedColumn_db}
                                            </td>
                                            <td>
                                                {obj.allocatedColumn_ui}
                                            </td>
                                            <td>
                                                {obj.unit}
                                            </td>
                                            <td onClick={() => this.clickCard(obj.pkId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                            <i className="fa fa-edit fa-lg "></i>
                                                            <span class="tooltip-text">Edit</span>
                                                        </td>
                                            <th><div key={key}>
                                                <button className="btn btn-danger" id={obj.pkId} onClick={this.deleteColumn}>DEL</button>
                                            </div></th>                                      
                                        </tr>
                                    </>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    {/* <ReactPaginate
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
                        activeClassName={"active"} /> */}
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
