import React, { Component } from 'react'
import './AddGatway.css'
import { deleteGateway } from "../../Service/deleteGateway"
import { getGatwayList } from '../../Service/getGatwayList'
import moment from "moment";
import { getPageWiseGatewayList } from '../../Service/getPageWiseGatewayList'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";
import Swal from 'sweetalert2'
import Axios from "../../utils/axiosInstance";

export default class GatwayList extends Component {
    constructor(props) {
        super(props);
        this.state = {
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
        let result = await getPageWiseGatewayList(selectedPage);
        if (result && result.data && result.data.length !== 0) {
            const data = result.data.content;
            this.setState({
                currentPage: selectedPage,
                pageCount: result.data.totalPages,
                tableData: data,
                offset: result.data.pageable.offset,
                loader: false,
            }, () => {
                let temp = [];
                this.state.tableData.map((obj, key) => {
                    const objTemp = { [key]: false };
                    temp.push(objTemp);
                });
                this.setState({ checkedStatus: temp, checkedValue: [] });
            }
            );
        }
        else {
            this.setState(
                {
                    pageCount: {},
                    tableData: [],
                }
            );
            swal("Sorry", "Data is not present", "warning");
        }
    };

    async componentDidMount() {
        try {
            this.setState({ loader: true, Role: localStorage.getItem('role') })

            this.getGatewayData();

        } catch (error) {
            console.log(error)
            swal("Sorry", "Data is not present", "warning");

        }

    }
    async getGatewayData() {

        let result = await getPageWiseGatewayList(this.state.currentPage);
        console.log("GateWay List", result);
        if (result && result.data && result.data.content && result.data.content.length !== 0) {
            const data = result.data.content;
            this.setState(
                {
                    pageCount: result.data.totalPages,
                    tableData: data,
                    loader: false,
                },
                () => {
                    let temp = [];
                    this.state.tableData.map((obj, key) => {
                        const objTemp = { [key]: false };
                        temp.push(objTemp);
                    });
                    this.setState({ checkedStatus: temp });
                }
            );
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

    redirectToCreateUser = () => {
        this.props.history.push("/add-gatway")
    }
    redirectToCreateTagOrg = () => {
        this.props.history.push("/add-Org-gatway")
    }
    redirectToCreateTagAdmin = () => {
        this.props.history.push("/add-Admin-gatway")
    }
    redirectToUploadGatewayExcel = () => {
        this.props.history.push("/upload-gateway-excel")
    }
    redirectToUploadOrgGatewayExcel = () => {
        this.props.history.push("/upload-gateway-excel-for-org")
    }
    redirectToGatewayList = () => {
        this.props.history.push("/org_gatway_list");
    }

    clickCard = async (id) => {
        this.props.history.push({
            pathname: `/update-gatway/${id}`,
            state: { deviceId: id },
        });
    }
    clickOrgCard = async (id) => {
        this.props.history.push({
            pathname: `/update-org-gatway/${id}`,
            state: { deviceId: id },
        });
    }
    clickAdminCard = async (id) => {
        this.props.history.push({
            pathname: `/update-admin-gatway/${id}`,
            state: { deviceId: id },
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
                checkedArray.push(parseInt(obj.pkDeviceDetails))

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

    deleteGateway = async () => {
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
                return Axios.post('/gateway/delete-multiple-gateway', this.state.checkedValue, { headers: { "Authorization": `Bearer ${token}` } })
                    .then(resultResponse => {
                        console.log("Delete User", resultResponse);
                        Swal.fire({
                            position: 'top-center',
                            icon: 'success',
                            title: 'Gateway Is Deleted...!',
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
                {/* {loader ? <div class="loader"></div> : */}
                <div>
                    {(this.state.Role === 'admin') ? (<div className="device__btn__wrapper" >
                        <button className="btn btn-primary" onClick={this.redirectToCreateTagAdmin}>Create Gateway</button>&nbsp;&nbsp;
                        <a href="Gateway-generation-excel.xlsx" download="Gateway-generation-excel.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                        <button className="btn btn-primary" onClick={this.redirectToUploadGatewayExcel}>Upload Excel</button>&nbsp;&nbsp;

                    </div>)
                        : (this.state.Role === 'user' || this.state.Role === 'empuser') ? (<div className="device__btn__wrapper" style={{ display: 'none' }}>
                            <button className="btn btn-primary" onClick={this.redirectToCreateTagOrg}>Create Gateway</button>&nbsp;&nbsp;
                            <a href="Gateway-generation-excel.xlsx" download="Gateway-generation-excel.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                            <button className="btn btn-primary" onClick={this.redirectToUploadGatewayExcel}>Upload Excel</button>&nbsp;&nbsp;

                        </div>)
                            : (this.state.Role === 'organization') ? (<div className="device__btn__wrapper">
                                <button className="btn btn-primary" onClick={this.redirectToCreateTagOrg}>Create Gateway</button>&nbsp;&nbsp;
                                <a href="Gateway-generation-excel.xlsx" download="Gateway-generation-excel.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                                <button className="btn btn-primary" onClick={this.redirectToUploadGatewayExcel}>Upload Excel</button>&nbsp;&nbsp;
                                <button className="btn btn-primary" onClick={this.redirectToGatewayList}>Unallocated Gateway List</button>&nbsp;&nbsp;
                            </div>)
                                : (<div className="device__btn__wrapper">
                                    <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create Gateway</button>&nbsp;&nbsp;
                                    <a href="Gateway-generation-excel.xlsx" download="Gateway-generation-excel.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                                    <button className="btn btn-primary" onClick={this.redirectToUploadGatewayExcel}>Upload Excel</button>&nbsp;&nbsp;
                                    <a href="GatewayStockFromSuperAdmin.xlsx" download="GatewayStockFromSuperAdmin.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template For Organization</span></a>
                                    <button className="btn btn-primary" onClick={this.redirectToUploadOrgGatewayExcel}>Upload Excel For Org</button>&nbsp;&nbsp;
                                </div>)}

                    <div className="payload__btn__wrapper">
                        <div>
                            &nbsp;&nbsp;&nbsp;
                            {this.state.checkedValue.length === 0 ?
                                "" :
                                <button className="btn btn-danger" onClick={this.deleteGateway}>Delete</button>}
                        </div>
                    </div>
                    <br />
                    <br />
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table__scroll table-hover table-bordered table-striped text-center">

                            <tr className="tablerow">
                                {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <><th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                </> : (this.state.Role === 'user' || this.state.Role === 'empuser') ? <> <th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                </> : <><th><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                </>}
                                <th>Sr_No</th>
                                <th>Gateway_Barcode_No.</th>
                                <th>Gateway_Name</th>
                                <th>Gateway_Mac_Id</th>
                                <th>Wakeup_Time</th>
                                <th>TimeZone</th>
                                <th>Date&Time</th>
                                {/* <th>Time</th> */}
                                <th>Gateway_Location</th>
                                <th>Gateway_Type</th>
                                {/* {(this.state.Role === 'admin' || this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                    <th style={{ display: 'none' }}>Created By</th>
                                </> : <> */}
                                <th>Created By</th>
                                {/* </>} */}
                                {(this.state.Role === 'super-admin' || this.state.Role === 'organization' || this.state.Role === 'admin') ? <>
                                    <th>User</th>
                                </> : ""}
                                {(this.state.Role === 'super-admin' || this.state.Role === 'organization' || this.state.Role === 'user') ? <>
                                    <th>Admin</th>
                                </> : ""}
                                {(this.state.Role === 'super-admin') ? <>
                                    <th>Organization</th>
                                </> : ""}
                                {(this.state.Role === 'super-admin' || this.state.Role === 'admin' || this.state.Role === 'organization') ? <>
                                    <th>Edit</th>
                                </> : ""}
                            </tr>
                            <tbody>

                                {tableData.map((obj, key) => (
                                    <>
                                        <tr className="Device__table__col">
                                            {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <> <th style={{ display: 'none' }}><div key={key}>
                                                <input type="checkbox" value={obj.gatewayId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                            </div></th>
                                                <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.gatewayId)}><button className="btn btn-info">EDIT</button></td></>
                                                : (this.state.Role === 'user' || this.state.Role === 'empuser') ? <><th style={{ display: 'none' }}><div key={key}>
                                                    <input type="checkbox" value={obj.gatewayId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                                </div></th>
                                                    <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.gatewayId)}><button className="btn btn-info">EDIT</button></td></>
                                                    : <>
                                                        <th><div key={key}>
                                                            <input type="checkbox" value={obj.gatewayId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                                        </div></th>

                                                    </>}
                                            <td>
                                                {this.state.offset + key + 1}
                                            </td>
                                            <td>
                                                {obj.gatewayBarcodeOrSerialNumber}
                                            </td>
                                            <td>{obj.gatewayName}</td>
                                            <td> {obj.gatewayUniqueCodeMacId}</td>
                                            <td>{obj.wakeupTime}</td>
                                            <td>{obj.timeZone}</td>
                                            <td>{moment(obj.dateTime).format("DD/MM/YYYYTHH:mm:ss")}</td>
                                            {/* <td>{obj.time}</td> */}
                                            <td>
                                                {obj.gatewayLocation}
                                            </td>
                                            <td>
                                                {obj.assetTagCategory}

                                            </td>
                                            {/* {(this.state.Role === 'admin' || this.state.Role === 'user' || this.state.Role === 'empuser') ? <> */}
                                            <td>
                                                {obj.createdBy}
                                            </td>
                                            {/* </> : ""} */}
                                            {(this.state.Role === 'super-admin' || this.state.Role === 'organization' || this.state.Role === 'admin') ? <>
                                                <td>
                                                    {obj.user === "" ? 'N/A' : obj.user === "0" ? 'N/A' : obj.user}
                                                </td>
                                            </> : ""}
                                            {(this.state.Role === 'super-admin' || this.state.Role === 'organization' || this.state.Role === 'user') ? <>
                                                <td>
                                                    {obj.admin === "" ? 'N/A' : obj.admin === "0" ? 'N/A' : obj.admin}
                                                </td>
                                            </> : ""}
                                            {(this.state.Role === 'super-admin') ? <>
                                                <td>
                                                    {obj.organization === "" ? 'N/A' : obj.organization === "0" ? 'N/A' : obj.organization}
                                                </td>
                                            </> : ""}

                                            {(this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                                <td onClick={() => this.clickCard(obj.gatewayId)} style={{ display: 'none' }}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                    <i className="fa fa-edit fa-lg "></i>
                                                </td>
                                            </> : (this.state.Role === 'organization') ? <>
                                                <td onClick={() => this.clickOrgCard(obj.gatewayId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                    <i className="fa fa-edit fa-lg "></i>
                                                </td>
                                            </> : (this.state.Role === 'admin') ? <>
                                                <td onClick={() => this.clickAdminCard(obj.gatewayId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                    <i className="fa fa-edit fa-lg "></i>
                                                </td>
                                            </> : <>
                                                <td onClick={() => this.clickCard(obj.gatewayId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
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
                        // previousLabel={"Prev"}
                        // nextLabel={"Next"}
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
                {/* } */}
            </>
        )
    }
}
