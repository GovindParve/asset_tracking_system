import React, { Component } from 'react'
import './reports.css'
import moment from "moment";
import ReactPaginate from 'react-paginate';
import { Tabs, Tab } from 'react-bootstrap'
import axios from '../../utils/axiosInstance'
import fileSaver from "file-saver"
import Select from "react-select";
import * as Yup from 'yup';
import { listGateway } from "../../Service/listGateway"
import { getGatewayGraphData } from "../../Service/getGatewayGraphData"
import { getDateWiseGateway } from "../../Service/getDateWiseGateway"

import Highcharts from 'highcharts'
import getDateWiseGatewayoptions from './highChartDataDatewiseGateway'
import HighchartsReact from 'highcharts-react-official'
import { allGatewayWiseGraphData } from "../../Service/allGatewayWiseGraphData"

import swal from "sweetalert";


const SignupSchema = Yup.object().shape({
    type_of: Yup.string().required('Required'),
});

export default class DatewiseGatewayReport extends Component {
    constructor(props) {
        super(props);
        this.state = {
            offset: 0,
            data: [],
            perPage: 10,
            currentPage: 0,
            show: true,
            tableData: [],
            tableTagData: [],
            tableGatewayData: [],
            tableDateData: [],
            orgtableData: [],
            tableTimeData: [],
            Role: "",
            loader: false,
            reportload: false,
            startDate: "",
            endDate: "",
            startDateReport: "",
            endDateReport: "",
            startTime: "",
            endTime: "",
            fileType: "",
            durationList: [],
            tagList: [],
            gatewayList: [],
            allData: [],
            allTime: [],
            allColumnList: [],
            allParameter: [],
            tagName: [],
            gatewayName: [],
            newCount: [],
            agedCount: [],
            graph: [],
            Taggraph: [],
            allColumn: [],
            selectedDevice: "",
            selectedColumn: "",
            selectedTagDevice: "",
            selectedGateDevice: "",
            selectedGateway: "",
            selectedAssetTag: "",
            selectDuration: "",
            selectGatewayDuration: "",
            AssetTag: "",
            AssetGateway: "",
            selectedGatewayDevice: "",
            AssetGatway: "",
            selectedTag: "",
            gatewayTagList: [],
            newageGraphdata: [],
            newageGatewayGraphdata: [],
            isOpen: false
        };

        this.handlePageClick = this.handlePageClick.bind(this);

    }

    openModal = () => this.setState({ isOpen: true });
    closeModal = () => this.setState({ isOpen: false });

    handlePageClick = async (e) => {
        const selectedPage = e.selected;

        if (this.state.startDate !== "" && this.state.endDate !== "" && this.state.AssetGateway !== "") {
            let result = await getDateWiseGateway(selectedPage, this.state.startDate, this.state.endDate, this.state.AssetGateway);
            console.log("Date wise Gateway List", result);
            if (result && result.data && result.data.length !== 0) {
                const data = result.data.content;
                this.setState({
                    currentPage: selectedPage,
                    pageCount: result.data.totalPages,
                    tableDateData: data,
                    offset: result.data.pageable.offset,
                    loader: false,
                });
            }
        }

    };


    async componentDidMount() {
        try {
            this.setState({ loader: true })

            this.receivedDateGatewayData()


            let [resultAssetGatewayList, setRole] = await Promise.all([listGateway(), localStorage.getItem('role')]);

            let tempallGatewayArray = []
            if (resultAssetGatewayList && resultAssetGatewayList.data && resultAssetGatewayList.data.length != 0) {
                resultAssetGatewayList.data.map((obj) => {
                    let tempGatewayObj = {
                        value: obj,
                        label: obj
                    }
                    tempallGatewayArray.push(tempGatewayObj)
                })
            }

            this.setState({ gatewayList: tempallGatewayArray, Role: setRole, loader: false }, () => {
                console.log('All date wise Gateway List', this.state.gatewayList)
            })

            let data = await allGatewayWiseGraphData();
            console.log('gatewayGraphData', data.data)
            this.setState({ newageGraphdata: data.data }, () => {
                //  console.log('newageGraphdata', this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0])
            })
            this.setState({ gatewayName: this.state.newageGraphdata[0].gatewayNames, newCount: this.state.newageGraphdata[0].newTagCount, agedCount: this.state.newageGraphdata[0].agedTagCount })
            console.log('Gateway Graph Data', this.state.newageGraphdata, this.state.gatewayName, this.state.newCount, this.state.agedCount)




            //*********Datewise tag Graph Madal***********
            var modalDateGateway = document.getElementById("myModal-DateWiseGateway");
            var buuton = document.getElementById("myBtn-DateWiseGateway");
            var span = document.getElementsByClassName("close-Gatewaywise")[0];
            buuton.onclick = function () {
                modalDateGateway.style.display = "block";
            }
            span.onclick = function () {
                modalDateGateway.style.display = "none";
            }
            window.onclick = function (event) {
                if (event.target == modalDateGateway) {
                    modalDateGateway.style.display = "block";
                }
            }
            //*********End Graph Madal***********
        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }

    }


    async receivedDateGatewayData() {
        let result = await getDateWiseGateway(this.state.currentPage, this.state.startDate, this.state.endDate, this.state.selectedGateDevice);
        console.log("Date Wise Gateway Data", result)
        if (result && result.data && result.data.content && result.data.content.length !== 0) {
            const data = result.data.content;
            this.setState(
                {
                    pageCount: result.data.totalPages,
                    tableDateData: data,
                    loader: false,
                },
            );
        }
        else {
            this.setState(
                {
                    pageCount: {},
                    tableDateData: [],
                }
            );
            // swal("Sorry", "Data is not present", "warning");
        }
    }

    changeGatewayDevice = (selectedGateDevice) => {
        this.setState({ AssetGateway: selectedGateDevice.value }, async () => {
            console.log('Asset Gateway', this.state.AssetGateway)
            let result = await getDateWiseGateway(this.state.currentPage, this.state.startDate, this.state.endDate, this.state.AssetGateway);
            console.log("Date Wise Gateway Data", result.data)
            if (result && result.data && result.data.content && result.data.content.length !== 0) {
                const data = result.data.content;
                this.setState(
                    {
                        pageCount: result.data.totalPages,
                        tableDateData: data,
                        loader: false,
                    },
                );
            } else {
                this.setState(
                    {
                        pageCount: {},
                        tableDateData: [],
                    }
                );
                swal("Sorry", "Data is not present", "warning");
            }

            let resultGatewaygraph = await getGatewayGraphData(this.state.startDate, this.state.endDate, this.state.AssetGateway)
            console.log('Date wise GatewayGraph', resultGatewaygraph.data)
            let newgraph = [resultGatewaygraph.data.lTotal]
            this.setState({ newageGatewayGraphdata: newgraph }, () => {
                console.log('Newage Gatway Graphdata', this.state.newageGatewayGraphdata)

            })
        })
    }

    startDateValue = (e) => {
        this.setState({ startDate: moment(e.target.value).format("yyyy-MM-DD") }, () => {
            console.log('startdate', this.state.startDate)
        })
    }

    endDateValue = (e) => {
        this.setState({ endDate: moment(e.target.value).format("yyyy-MM-DD") }, () => {
            console.log('enddate', this.state.endDate)
        })
    }

    startDate = (e) => {
        this.setState({ startDateReport: moment(e.target.value).format("yyyy-MM-DD") }, () => {
            console.log('startDateReport', this.state.startDateReport)
        })
    }

    endDate = (e) => {
        this.setState({ endDateReport: moment(e.target.value).format("yyyy-MM-DD") }, () => {
            console.log('endDateReport', this.state.endDateReport)
        })
    }

    downloadDateWiseGatewayFile = async () => {

        this.setState({ reportload: true }, () => {
            console.log('reportload', this.state.reportload)
        })
        if (this.state.startDate !== '' && this.state.endDate !== '') {
            let token = await localStorage.getItem("token")
            let fkUserId = await localStorage.getItem('fkUserId');
            let role = await localStorage.getItem('role');
            let fileType = ["excel"];
            let category = ["BLE"];
            axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-gateway-wise?fileType=${fileType}&fromDate=${this.state.startDate}&toDate=${this.state.endDate}&gatewayName=${this.state.AssetGateway}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
                headers: { "Authorization": `Bearer ${token}` },
                responseType: 'arraybuffer'
            })
                .then((response) => {
                    var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    fileSaver.saveAs(blob, 'DateWiseGatewayReportExcel.xls');
                    this.setState({ reportload: false })
                });

        } else {
            swal("Sorry", "Please select Date", "warning");
            this.setState({ reportload: false })
        }
    }

    downloadDateWiseGatewayPDF = async () => {
        this.setState({ reportload: true }, () => {
            console.log('reportload', this.state.reportload)
        })
        if (this.state.startDate !== '' && this.state.endDate !== '') {
            let token = await localStorage.getItem("token");
            let fkUserId = await localStorage.getItem('fkUserId');
            let role = await localStorage.getItem('role');
            let fileType = ["pdf"];
            let category = ["BLE"];
            axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-gateway-wise?fileType=${fileType}&fromDate=${this.state.startDate}&toDate=${this.state.endDate}&gatewayName=${this.state.AssetGateway}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
                headers: { "Authorization": `Bearer ${token}` },
                responseType: 'arraybuffer'
            })
                .then((response) => {
                    var blob = new Blob([response.data], { type: 'application/pdf' });
                    fileSaver.saveAs(blob, `DateWiseGatewayReportPDF.pdf`);
                    this.setState({ reportload: false })
                });
        } else {
            swal("Sorry", "Please select Date", "warning");
            this.setState({ reportload: false })
        }

    }



    changeGatewayWiseDateview() {
        if (document.getElementById("displaytable4").style.display === "none")
            document.getElementById("displaytable4").style.display = "block";
        else
            document.getElementById("displaytable4").style.display = "none";
    }





    render() {
        const { tableDateData, allColumn, loader } = this.state
        const { data } = this.state
        return (
            <>
                {this.state.reportload ? (
                    <div className="reportload"></div>
                ) : (
                    <div className="tab-wrapper">
                        <div className='' >
                            <div className="row">
                                <div className="col-sm-12">
                                    <div className="tab-item-wrapper">
                                        <h5>Date Wise Gateway Report</h5>
                                        <br />
                                        <div className="report_form">
                                            <div>
                                                <label>START DATE</label>
                                                <input className="form-control" type="date" name="sDate" onChange={this.startDateValue} />
                                            </div>
                                            <div>
                                                <label>END DATE</label>
                                                <input className="form-control" type="date" name="eDate" onChange={this.endDateValue} />
                                            </div>
                                            <div>
                                                <label>SELECT GATEWAY</label>
                                                <Select options={this.state.gatewayList} name="selectDevice" className="tag_select" onChange={this.changeGatewayDevice} />
                                            </div>
                                        </div>
                                        <br />
                                        <button onClick={this.downloadDateWiseGatewayFile}>EXCEL</button>
                                        <button onClick={this.downloadDateWiseGatewayPDF}>PDF</button>
                                        <button onClick={this.changeGatewayWiseDateview}> VIEW</button>
                                        <button id="myBtn-DateWiseGateway">VIEW GRAPH</button>
                                    </div>
                                    <div id="displaytable4" style={{ display: 'none' }} >
                                        <br />
                                        <div style={{ overflowX: "auto" }} className="table-responsive-sm">
                                            <table className=" table table-hover table-bordered table-striped text-center">
                                                <tr className="tablerow">
                                                    <th>Sr_No</th>
                                                    <th>Asset_Tag_Name</th>
                                                    <th>Asset_Gatway_Name</th>
                                                    <th>Date</th>
                                                    <th className='entTime'>Entry_Time</th>
                                                    <th>Tag_Location</th>
                                                    <th className='entTime'>Exit_Time</th>
                                                    <th>Dispatch_Time</th>
                                                    {allColumn.map((obj) => (
                                                        <th>{obj.allocatedColumn_ui}
                                                            <br />
                                                            ({obj.unit})
                                                        </th>
                                                    ))}
                                                </tr>
                                                <tbody>
                                                    {tableDateData.map((obj, key) => (
                                                        <>
                                                            <tr key={key}>
                                                                <td>
                                                                    {this.state.offset + key + 1}
                                                                </td>
                                                                <td>
                                                                    {obj.assetTagName}
                                                                </td>
                                                                <td>
                                                                    {obj.assetGatewayName}
                                                                </td>
                                                                <td>
                                                                    {moment(obj.date).format('DD/MM/YYYY')}
                                                                </td>
                                                                <td>
                                                                    {obj.entryTime !== null ? moment.tz(obj.entryTime, "Asia/Kolkata").format("YYYY-MM-DD HH:mm:ss") : '-'}
                                                                </td>
                                                                <td>
                                                                    {obj.tagEntryLocation !== null ? obj.tagEntryLocation : '-'}
                                                                </td>
                                                                <td >
                                                                    {obj.existTime === "N/A" ? obj.existTime : moment.tz(obj.existTime, "Asia/Kolkata").format("YYYY-MM-DD HH:mm:ss")}
                                                                </td>
                                                                <td>
                                                                    {/* {obj.dispatchTime === "N/A" ? obj.dispatchTime : moment(obj.dispatchTime, "HH:mm").format("HH:mm")} */}
                                                                    {obj.dispatchTime}
                                                                </td>
                                                                {(this.state.Role === 'super-admin') ? <>
                                                                    {allColumn.map((obj) => (
                                                                        <td>

                                                                        </td>
                                                                    ))}
                                                                </>
                                                                    : <>
                                                                    </>}
                                                            </tr>
                                                        </>
                                                    ))}
                                                </tbody>
                                            </table>
                                        </div>
                                        <br />
                                        <br />
                                        <ReactPaginate
                                            previousLabel={<i class="fa fa-angle-left report"></i>}
                                            nextLabel={<i class="fa fa-angle-right report"></i>}
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
                                        <div id="myModal-DateWiseGateway" class="modal-DateWiseGateway">
                                            <div class="modal-content-DateWiseGateway">
                                                <div class="graphclose">
                                                    <span class="close-Gatewaywise">&times;</span>
                                                </div>
                                                <HighchartsReact
                                                    className="mscalculator-chart"
                                                    highcharts={Highcharts}
                                                    options={getDateWiseGatewayoptions([this.state.AssetGateway], this.state.newageGatewayGraphdata)}
                                                    style={{ with: "100%" }} />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br />
                    </div>
                )}
            </>
        )
    }
}
