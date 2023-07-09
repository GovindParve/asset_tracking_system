import React, { Component } from 'react'
import './AddGatway.css'
import moment from "moment";
import { getPageWiseOrgGatewayList } from '../../Service/getPageWiseOrgGatewayList'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";
import Swal from 'sweetalert2'
import axios from "../../utils/axiosInstance";
import fileSaver from "file-saver";

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
            Role: "",
            loader: false,
        };

        this.handlePageClick = this
            .handlePageClick
            .bind(this);
    }
    handlePageClick = async (e) => {
        const selectedPage = e.selected;
        let result = await getPageWiseOrgGatewayList(selectedPage);
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
        let result = await getPageWiseOrgGatewayList(this.state.currentPage);
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

    redirectToUploadGatewayExcel = () => {
        this.props.history.push("/upload-gateway-excel")
    }

    downloadGatewayExcel = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["excel"];
        let category = ["BLE"];
        axios.get(`/gateway/get-gateway-From-SuperAdmin-excel-download?fileType=${fileType}&fkUserId=${fkUserId}&role=${role}&category=${category}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                fileSaver.saveAs(blob, `GatewayExcel.xls`);
            });
    }

    render() {
        const { allData, tableData, loader } = this.state
        return (
            <>
                <div>
                    <div className="device__btn__wrapper">
                        <a onClick={this.downloadGatewayExcel}><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download GateWay List</span></a>
                        <button className="btn btn-primary" onClick={this.redirectToUploadGatewayExcel}>Upload Excel</button>&nbsp;&nbsp;
                    </div>
                    <br />
                    <br />
                    <br />
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table__scroll table-hover table-bordered table-striped text-center">
                            <tr className="tablerow">
                                <th>Sr_No</th>
                                <th>Gateway_Name</th>
                                <th>Gateway_Barcode_No.</th>
                                <th>Asset_Gateway_Mac_Id</th>
                                <th>Gateway_Type</th>
                            </tr>
                            <tbody>
                                {tableData.map((obj, key) => (
                                    <>
                                        <tr className="Device__table__col">
                                            <td>
                                                {this.state.offset + key + 1}
                                            </td>                     
                                            <td>{obj.gatewayName}</td>
                                            <td>
                                                {obj.gatewayBarcodeSerialNumber}
                                            </td>
                                            <td>{obj.gatewayUniqueCodeMacId}</td>
                                            <td>
                                                {obj.assetTagCategory}
                                            </td>
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
