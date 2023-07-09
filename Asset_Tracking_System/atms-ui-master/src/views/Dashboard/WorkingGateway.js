import React, { Component } from 'react'
import './WorkingOrNonTag.css'
import { getWorkinGatewayList } from '../../Service/getWorkinGatewayList'
import moment from "moment";
import { getPageWiseGatewayList } from '../../Service/getPageWiseGatewayList'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";

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
            });
        }
    };

    async componentDidMount() {
        try {
            this.setState({ loader: true })
            var setRole = await localStorage.getItem('role');
            let result = await getWorkinGatewayList()
            console.log('all Working Gateway Data', result.data)
            // this.setState({ allData: result && result.data, Role: setRole}, () => {
            //     console.log('Working All Gateway Data', this.state.allData)
            // })
            if (result && result.data && result.data && result.data.length !== 0) {
            this.setState({ allData: result && result.data, Role: setRole}, () => {
                console.log('Working All Gateway Data', this.state.allData)

            })
        }
            else {
                this.setState(
                  {
                 
                    allData: [],
                  }
                );
                swal("Sorry", "Data is not present", "warning");
              }
        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }

    }


    render() {
        const { allData } = this.state
        return (
            <>
                {/* {loader ? <div class="loader"></div> : */}
                <div>
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table__scroll table-hover table-bordered table-striped text-center">
                            <tr className="tablerow">
                                <th>Sr_No</th>
                                <th>Asset_Gateway_Barcode_No.</th>
                                <th>Asset_Gateway_Name</th>
                                {(this.state.Role === 'admin') ? <><th style={{ display: 'none' }}>Asset Gatway Unique Code</th>
                                </> : (this.state.Role === 'user') ? <> <th style={{ display: 'none' }}>Asset Unique Code</th>
                                </> : <><th>Asset_Gateway_Mac_Id</th>
                                </>}
                                <th>TimeZone</th>
                                <th>Date&Time</th>
                                <th>Gateway_Location</th>
                                <th>Asset_Gateway_Type</th>
                                <th>Working</th>
                            </tr>
                            <tbody>
                                {allData.map((obj, key) => (
                                    <>
                                        <tr className="Device__table__col">
                                            <td>
                                                {this.state.offset + key + 1}
                                            </td>
                                            <td>
                                                {obj.gatewayBarcodeSerialNumber}
                                            </td>
                                            {(this.state.Role === 'admin') ? <><td>{obj.gatewayName}</td>
                                                <td style={{ display: 'none' }}> {obj.gatewayUniqueCodeMacId}</td> </> : (this.state.Role === 'user') ? <> <td>{obj.gatewayName}</td>
                                                    <td style={{ display: 'none' }}> {obj.gatewayUniqueCodeMacId}</td></> : <><td>{obj.gatewayName}</td>
                                                <td> {obj.gatewayUniqueCodeMacId}</td> </>}
                                            <td>{obj.timeZone}</td>
                                            <td>{moment(obj.dateTime).format("DD/MM/YYYY  HH:mm:ss")}</td>
                                            <td>
                                                {obj.gatewayLocation}
                                            </td>
                                            <td>
                                                {obj.assetTagCategory}
                                            </td>
                                            <td>
                                                {obj.workingorNonworking}
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
                {/* } */}
            </>
        )
    }
}
