import React, { Component } from 'react'
import './DynamicColumn.css'
import { deleteColumn } from "../../Service/deleteColumn"
import { getGpsAdminColumnList } from '../../Service/getGpsAdminColumnList'
import Select from "react-select";
//import { getPageWiseProductList } from '../../Service/getPageWiseProductList'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";

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
        };

        // this.handlePageClick = this
        //     .handlePageClick
        //     .bind(this);
    }
    handlePageClick = async (e) => {
        const selectedPage = e.selected;
 
            let result = await getGpsAdminColumnList(selectedPage);
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
            let result = await getGpsAdminColumnList(this.state.currentPage)
            console.log("GPS Column",result)
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
    }catch (error) {
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
            console.log("Delete Column",result)
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
    render() {
        const { allData, tableData, loader } = this.state

        return (
            <>
                <div>
                <div className="device__btn__wrapper">
                                <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Add Parameter For Admin</button>
                            </div>
                    <br />
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table__scroll table-hover table-bordered table-striped text-center">

                            <tr className="tablerow">
                                <th>Sr_No</th>
                                <th>Admin_Name</th>
                                <th>Admin_Allocated_Parameter</th>
                                <th>Unit</th>
                               
                            </tr>
                            <tbody>
                                {allData.map((obj, key) => (
                                    <>
                                        <tr className="Device__table__col">
                                            <td>
                                                {key + 1}
                                            </td>
                                            <td>
                                            {obj.organization === "" ? 'N/A' : obj.organization === "0" ? 'N/A' : obj.organization}
                                               
                                            </td>
                                            <td>
                                               {obj.adminName === "" ? 'N/A' : obj.adminName === "0" ? 'N/A' : obj.adminName}
                                            </td>
                                            <td>
                                                {obj.unit}
                                            </td>
                                           
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
