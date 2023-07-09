import React, { Component } from 'react'
import './AssetTags.css'

import { getTagAllocateHistory } from '../../Service/getTagAllocateHistory'
import moment from "moment";
import swal from "sweetalert";


export default class TagAllocateHistory extends Component {
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

    }

   
    async componentDidMount() {
        try {
            this.setState({ loader: true })
            

            if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
                const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;
                // console.log('Tag Allocat History',this.props.location.state.deviceId)
                let result = await getTagAllocateHistory(propId)
                console.log('Allocate History',result)
            }
        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }


    }

    render() {
        const { allData, loader } = this.state

        return (
            <>

                {/* {loader ? <div class="loader"></div> : */}
                    <div>
                   
                        <br />
                        <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                            <table className="table table__scroll table-hover table-bordered table-striped text-center">
                                <tr className="tablerow">
                                    <th>Sr_No</th>
                                    <th>Asset_Barcode_No.</th>
                                    <th>Asset_Tag_Name</th>
                                    {(this.state.Role === 'admin') ? <><th style={{ display: 'none' }}>Asset Unique Code</th>
                                        <th style={{ display: 'none' }}>SIM</th>  <th style={{ display: 'none' }}>IMSI</th> </> :
                                        (this.state.Role === 'user') ? <> <th style={{ display: 'none' }}>Asset Unique Code</th>
                                            <th style={{ display: 'none' }}>SIM</th>  <th style={{ display: 'none' }}>IMSI</th></> : <>
                                            <th>Asset_Mac_Id</th>
                                        </>}

                                    {(this.state.selectedCategory === 'BLE') ? <></> : <><th >SIM</th>
                                        <th>IMSI</th>
                                        <th>IMEI</th></>}
                                    <th>Wakeup_Time</th>
                                    <th>TimeZone</th>
                                    <th>Date&Time</th>
                                    <th>Asset_Technology_Type</th>
                                    <th>User</th>
                                    <th>Admin</th>
                                    <th>Status</th>
                                </tr>
                                <tbody>
                                    {allData.map((obj, key) => (
                                        <>
                                            <tr className="Device__table__col">
                                                <td>
                                                    { key + 1}
                                                </td>
                                                <td>
                                                    {obj.assetBarcodeSerialNumber}
                                                </td>
                                                <td>{obj.assetTagName}</td>
                                                {(this.state.Role === 'admin') ? <>
                                                    <td style={{ display: 'none' }}>{obj.assetUniqueCodeMacId}</td>  <td style={{ display: 'none' }}> {obj.assetSimNumber === "" ? 'N/A' : obj.assetSimNumber}</td>
                                                    <td style={{ display: 'none' }}>{obj.assetIMSINumber === "" ? 'N/A' : obj.assetIMSINumber}</td>

                                                </> : (this.state.Role === 'user') ? <>
                                                    <td style={{ display: 'none' }}> {obj.assetUniqueCodeMacId}</td>  <td style={{ display: 'none' }}> {obj.assetSimNumber === "" ? 'N/A' : obj.assetSimNumber}</td>
                                                    <td style={{ display: 'none' }}>{obj.assetIMSINumber === "" ? 'N/A' : obj.assetIMSINumber}</td>
                                                </> : <>
                                                    <td> {obj.assetUniqueCodeMacId}</td>


                                                </>}
                                                {(this.state.selectedCategory === 'BLE') ? <></> : <><td> {obj.assetSimNumber === "" ? 'N/A' : obj.assetSimNumber === "0" ? 'N/A' : obj.assetSimNumber}</td>
                                                    <td>{obj.assetIMSINumber === "" ? 'N/A' : obj.assetIMSINumber === "0" ? 'N/A' : obj.assetIMSINumber}</td>
                                                    <td>{obj.assetIMEINumber === "" ? 'N/A' : obj.assetIMEINumber === "0" ? 'N/A' : obj.assetIMEINumber}</td>
                                                </>}

                                                <td>
                                                    {obj.wakeupTime}
                                                </td>
                                                <td>
                                                    {obj.timeZone}
                                                </td>
                                                <td>
                                                    {moment(obj.dateTime).format("DD/MM/YYYYTHH:mm:ss")}
                                                </td>

                                                <td>{obj.assetTagCategory}</td>
                                                <td>{obj.user}</td>
                                                <td>{obj.admin}</td>
                                                <td className={obj.status === 'Not-allocated' ? "colorRed" : "colorBlue"}>{obj.status}</td>

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
                            activeClassName={"active"} />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br /> */}

                    </div>
                {/* } */}
            </>
        )
    }
}
