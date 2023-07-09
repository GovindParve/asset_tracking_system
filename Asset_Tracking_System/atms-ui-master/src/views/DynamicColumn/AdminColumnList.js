import React, { Component } from 'react'
import './DynamicColumn.css'
import { deleteColumn } from "../../Service/deleteColumn"
import { getAdminColumnList } from '../../Service/getAdminColumnList'
import Select from "react-select";
//import { getPageWiseProductList } from '../../Service/getPageWiseProductList'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";
import Swal from "sweetalert2";
import Axios from "../../utils/axiosInstance";

export default class AdminColumnList extends Component {

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
            allStatus: [
                { value: "Active", label: "Active" },
                { value: "DeActive", label: "Deactive" },
              ],
        };

        this.handlePageClick = this
            .handlePageClick
            .bind(this);
    }
    handlePageClick = async (e) => {
        const selectedPage = e.selected;

            let result = await getAdminColumnList(selectedPage);
            console.log("Column List PageWise", result);
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

    };

    async componentDidMount() {
        try {
            this.setState({ loader: true })
            //this.getProductData();
            var setRole = await localStorage.getItem('role');
            this.setState({ Role: setRole });
           
            let result = await getAdminColumnList(this.state.currentPage)
            console.log('Column Data', result)
                    // this.setState({ allData: result && result.data }, () => {
                        if (result && result.data && result.data.length !== 0) {
                            if(this.state.Role === "super-admin"){
                            const data = result.data.content;
                            this.setState(
                                {
                                    pageCount: result.data.totalPages,
                                    tableData: data,
                                    loader: false,
                                }
    
                            );
                        }
                        else {
                            // const data = result.data.filter(obj =>{
                            //     return obj.status === "Active";
                            // })
                            // this.setState({ allData: data }, () => {
                                if (result && result.data && result.data.length !== 0) {
                                    const data = result.data.content.filter(obj =>
                                        {
                                            return obj.status === "Active";
                                        });
                                    this.setState(
                                        {
                                            pageCount: result.data.totalPages,
                                            tableData: data,
                                            loader: false,
                                        }
            
                                    );
                                }
                            // })
                        }
                    }
                    else {
                        this.setState(
                            {
                                tableData: [],
                            }
                        );
                        swal("Sorry", "Data is not present", "warning");
                    }
                    // })
        }
        catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }

    }


    redirectToCreateUser = () => {
        this.props.history.push("/admin-column-create")
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

    deleteColumn = async (selectedId) => {
        console.log('Column delete', selectedId.target.id)
        try {
            let result = await deleteColumn(selectedId.target.id)
            console.log("Delete Column", result)
            if (result.status === 200) {
                alert("Column deleted")
                window.location.reload();
            } else {
                alert("something went wrong please check your connection")
            }
        } catch (error) {
            console.log(error)
        }
    }

    changeActDeactive = async (selectedStatus, pkadminparameterid) => {
        console.log(selectedStatus.value, pkadminparameterid);
        Swal.fire({
          title: "Are you sure want to deactivate or activate?",
          // text: "You won't be able to revert this!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#c01664",
          confirmButtonText: "Yes, Status Update it!",
        })
          .then((resultResponse) => {
            if (resultResponse.isConfirmed) {
              this.setState({ selectStatus: selectedStatus.value }, async () => {
                console.log("this.state.selectStatus", this.state.selectStatus);
                let token = localStorage.getItem("token");
                return await Axios.post(
                  `/asset/tracking/update-status?status=${this.state.selectStatus}&pkadminparameterid=${pkadminparameterid}`,
                  "",
                  { headers: { Authorization: `Bearer ${token}` } }
                ).then((resultResponse) => {
                  console.log("Admin Column Status", resultResponse);
                  Swal.fire({
                    position: "top-center",
                    icon: "success",
                    title: "Status Updated...!",
                    showConfirmButton: false,
                    timer: 3000,
                  });
                  window.location.reload();
                });
              });
              return Promise.resolve();
            }
          })
          .catch((resultResponse) => {
            swal("Failed", "Somthing went wrong", "error");
            console.log("User Status", resultResponse);
          });
      };
    render() {
        const { allData, tableData, loader } = this.state

        return (
            <>
                <div>
                    {/* <div className="device__btn__wrapper">
                        <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Add Parameter For Admin</button>
                    </div> */}
                    {(this.state.Role === 'organization') ? (<div className="device__btn__wrapper">
                        <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Add Parameter For Admin</button>
                    </div>)
                        : (this.state.Role === 'user' || this.state.Role === 'empuser' || this.state.Role === 'admin') ? (<div className="device__btn__wrapper" >
                            <button className="btn btn-primary" onClick={this.redirectToCreateUser} style={{ display: 'none' }}>Add Parameter For Admin</button>
                        </div>)
                            : (<div className="device__btn__wrapper">
                                <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Add Parameter For Organization/Admin</button>
                            </div>)}
                    <br />
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table__scroll table-hover table-bordered table-striped text-center">

                            <tr className="tablerow1">
                                <th>Sr_No</th>
                                {(this.state.Role === 'admin' || this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                    <th style={{ display: 'none' }}>Orgnization_Name</th>
                                </> : <>
                                    <th>Orgnization_Name</th>
                                </>}
                                <th>Admin_Name</th>
                                <th>Allocated_Parameter</th>
                                <th>Unit</th>
                                {(this.state.Role === 'super-admin') ? <>
                                <th>status</th>
                                </>:""}

                            </tr>
                            <tbody>
                                {tableData.map((obj, key) => (
                                    <>
                                        <tr className="Device__table__col">
                                            <td>
                                                {this.state.offset + key + 1}
                                            </td>
                                            {(this.state.Role === 'admin' || this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                                <td style={{ display: 'none' }}>
                                                    {obj.organization === "" ? 'N/A' : obj.organization === "0" ? 'N/A' : obj.organization}
                                                </td>
                                            </> : <>
                                                <td>
                                                    {obj.organization === "" ? 'N/A' : obj.organization === "0" ? 'N/A' : obj.organization}
                                                </td>
                                            </>}
                                            <td>
                                                {obj.adminName === "" ? 'N/A' : obj.adminName === "0" ? 'N/A' : obj.adminName}
                                            </td>
                                            <td>
                                                {obj.columns}
                                            </td>
                                            <td>
                                                {obj.unit}
                                            </td>
                                            {(this.state.Role === 'super-admin') ? <>
                                            <td>
                                                <Select
                                                    className="status_select"
                                                    onChange={async (e) => {
                                                        this.changeActDeactive(e, obj.pkadmincolumnsId);
                                                    }}
                                                    options={this.state.allStatus}
                                                    value={
                                                        this.state.allStatus
                                                            ? this.state.allStatus.find(
                                                                (option) => option.value === obj.status
                                                            )
                                                            : ""
                                                    }
                                                    placeholder="Active"
                                                />
                                            </td>
                                            </>: ""}

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
