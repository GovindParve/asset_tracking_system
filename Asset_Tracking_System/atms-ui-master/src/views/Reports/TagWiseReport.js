import React, { Component } from 'react'
import './reports.css'
import moment from "moment";
import ReactPaginate from 'react-paginate';
import { Tabs, Tab } from 'react-bootstrap'
import axios from '../../utils/axiosInstance'
import fileSaver from "file-saver"
import Select from "react-select";
import * as Yup from 'yup';
import { listAssetTagforTrack } from "../../Service/listAssetTagforTrack"
import { listGateway } from "../../Service/listGateway"
import { getTrackListByTag } from "../../Service/getTrackListByTag"
import { getTrackData } from "../../Service/getTrackData"
import { getDateWiseTag } from "../../Service/getDateWiseTag"
import { getGatewayGraphData } from "../../Service/getGatewayGraphData"
import { getDateWiseGateway } from "../../Service/getDateWiseGateway"
import { getGatewayReports } from "../../Service/getGatewayReports"
import { getGatewayCount } from "../../Service/getGatewayCount"
import { getViewGatewayReport } from "../../Service/getViewGatewayReport"
import { getDuration } from "../../Service/getDuration"
import { getStayTimeGraph } from "../../Service/getStayTimeGraph"
import { getTagStayTimeGraph } from "../../Service/getTagStayTimeGraph"
import Highcharts from 'highcharts'
import getoptions from './highChartData'
import getGatewayoptions from './hightChartDataForGateway'
import getDateWiseTagoptions from './highChartDataForDatewiseTag'
import getDateWiseGatewayoptions from './highChartDataDatewiseGateway'
import HighchartsReact from 'highcharts-react-official'
import { allGatewayWiseGraphData } from "../../Service/allGatewayWiseGraphData"
import { getSingleGraphData } from "../../Service/getSingleGraphData"
import { getPageWiseTagDuration } from '../../Service/getPageWiseTagDuration'
import { getTimeWise } from '../../Service/getTimeWise'
import { getTimeTag } from '../../Service/getTimeTag'

import { getColumnList } from '../../Service/getColumnList'
import { getAdminColumnList } from '../../Service/getAdminColumnList'
// import { getParameterList } from '../../Service/getParameterList'
import swal from "sweetalert";
import { Field } from 'formik';
import ParameterReport from './ParameterReport';
import ParameterAdminReport from './ParameterAdminReport';
import UserReport from './UserReport';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
//<script src="//code.tidio.co/o8rk9uytps64ic5pp82bbu14pv3o1qi5.js" async></script>

const SignupSchema = Yup.object().shape({
  type_of: Yup.string().required('Required'),
});

export default class TagWiseReport extends Component {
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
      graphGateway: [],
      graphStayTime:[],
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
    if (this.state.selectedDevice !== "" && this.state.selectDuration !== "") {
      let result = await getPageWiseTagDuration(selectedPage, this.state.selectedDevice, this.state.selectDuration);
      console.log("Duration wise Pagination List", result.data);
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
    } else if (this.state.selectedDevice === "" && this.state.selectDuration === "") {
      let result = await getPageWiseTagDuration(selectedPage, this.state.selectedDevice, this.state.selectDuration);
      console.log("view Tag,Duration wise Pagination List", result.data);
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
  };


  async componentDidMount() {
    try {
      setTimeout(async () => {
        this.setState({ loader: true })

        this.receivedData()
        let [resultAssetTagList, setRole] = await Promise.all([listAssetTagforTrack(), localStorage.getItem('role')]);
        console.log("Tag Drowpdown List", resultAssetTagList)
        let tempTagArray = []
        if (resultAssetTagList && resultAssetTagList.data && resultAssetTagList.data.length != 0) {
          resultAssetTagList.data.map((obj) => {
            let tempTagObj = {
              value: obj,
              label: obj
            }
            tempTagArray.push(tempTagObj)
          })
        }
        this.setState({ tagList: tempTagArray, Role: setRole, loader: false }, () => {
          console.log('Tag List', this.state.tagList)
        })

        var modal = document.getElementById("myModal");
        var buuton = document.getElementById("myBtn");
        var span = document.getElementsByClassName("close")[0];
        buuton.onclick = function () {
          modal.style.display = "block";
        }
        span.onclick = function () {
          modal.style.display = "none";
        }
        window.onclick = function (event) {
          if (event.target == modal) {
            modal.style.display = "block";
          }
        }
      }, 200);
      //*********End Graph Madal***********
    } catch (error) {
      console.log(error)
      this.setState({ loader: false })
    }

  }

  async receivedData() {
    // try {
    let result = await getPageWiseTagDuration(this.state.currentPage, this.state.selectedDevice, this.state.selectDuration);
    console.log("All View Tag Data", result.data)
    if (result && result.data && result.data.content && result.data.content.length !== 0) {
      const data = result.data.content;
      this.setState({
          pageCount: result.data.totalPages,
          tableData: data,
          offset: result.data.pageable.offset,
          loader: false,
        },
        );
    }
    else {
      this.setState({pageCount: {},tableData: []});
      swal("Sorry", "Data is not present", "warning");
    }
  }


  changeDeviceView = async (selectedOptions) => {
    console.log(selectedOptions.value)
    if (selectedOptions.value == "Select device") {
      this.receivedData()
    } else {
      this.setState({ selectedDevice: selectedOptions.value },
        async () => {
          let result = await getPageWiseTagDuration(this.state.currentPage, this.state.selectedDevice, this.state.selectDuration);
          console.log("View Tag result", result)
          if (result && result.data && result.data.content && result.data.content.length !== 0) {
            const data = result.data.content;
            this.setState(
              {
                pageCount: result.data.totalPages,
                tableData: data,
                loader: false,
              },
            );
          }
          else {
            this.setState({pageCount: {},tableData: []});
            //swal("Sorry", "Tag DrowpDown Data is not present", "warning");
          }
        });
    }
  }


  changeDuration = async (selectedDuration) => {
    console.log(selectedDuration.target.value)
    if (selectedDuration.value == "Select Duration") {
      this.receivedData()
    } else {
      this.setState({ selectDuration: selectedDuration.target.value }, async () => {
        console.log('this.state.selectDuration', this.state.selectDuration);
        let resultStay = await getStayTimeGraph(this.state.selectDuration, this.state.selectedDevice);
        if (resultStay && resultStay.data && resultStay.data.length !== 0) {
          let staygraph = Object.entries(resultStay.data);
          console.log('Stay Time', staygraph);
          let staytime = staygraph.map((obj) => {
             return parseInt(obj[1]);
          })
          let stayGateway = staygraph.map((obj) => {
            return obj[0];
          })
          console.log('staygraph', staytime)
          this.setState({ graphGateway: stayGateway , graphStayTime: staytime},
            () => {
              console.log('Stay Time Tag Data', this.state.graphGateway,this.state.graphStayTime)
            })
        }
        let result = await getPageWiseTagDuration(this.state.currentPage, this.state.selectedDevice, this.state.selectDuration);
        console.log('this.state.selectedDevice', this.state.selectedDevice);
        if (result && result.data && result.data.content && result.data.content.length !== 0) {
          const data = result.data.content;
          console.log("Tag Duration List", data);
          this.setState(
            {
              pageCount: result.data.totalPages,
              tableData: data,
              loader: false,
            },
          );
        } else {
          this.setState({pageCount: {},tableData: []});
          swal("Sorry", "Duration wise Data is not present", "warning");
        }
        this.setState({ result: result && result.data},() => {
            console.log('data', this.state.result)
          })

      });
    }
  }


  downloadTagFile = async () => {
    this.setState({ reportload: true }, () => {
      console.log('reportload', this.state.reportload)})
    if (this.state.selectedDevice !== "") {
      let token = await localStorage.getItem("token");
      let fkUserId = await localStorage.getItem('fkUserId');
      let role = await localStorage.getItem('role');
      let fileType = ["excel"];
      let category = ["BLE"];
      axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download?tagName=${this.state.selectedDevice}&fileType=${fileType}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
        headers: { "Authorization": `Bearer ${token}` },
        responseType: 'arraybuffer'
      })
        .then((response) => {

          var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
          fileSaver.saveAs(blob, `TagDetailsReportExcel.xls`);
          this.setState({ reportload: false })

        });
    } else {
      swal("Sorry", "Please select tag", "warning");
      this.setState({ reportload: false })
    }
  }

  downloadTagPDF = async () => {
    this.setState({ reportload: true }, () => {
      console.log('reportload', this.state.reportload)
    })
    if (this.state.selectedDevice !== "") {
      let token = await localStorage.getItem("token");
      let fkUserId = await localStorage.getItem('fkUserId');
      let role = await localStorage.getItem('role');
      let fileType = ["pdf"];
      let category = ["BLE"];
      axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download?fileType=${fileType}&tagName=${this.state.selectedDevice}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
        headers: { "Authorization": `Bearer ${token}` },
        responseType: 'arraybuffer'
      })
        .then((response) => {
          var blob = new Blob([response.data], { type: 'application/pdf' });
          fileSaver.saveAs(blob, `TagDetailsReportPDF.pdf`);
          this.setState({ reportload: false })
        });
    } else {
      swal("Sorry", "Please select tag", "warning");
      this.setState({ reportload: false })
    }
  }

  viewTag() {
    if (document.getElementById("displaytable").style.display === "none")
      document.getElementById("displaytable").style.display = "block";
    else
      document.getElementById("displaytable").style.display = "none";
  }

  render() {
    const { tableData, allColumn, loader } = this.state
    const { data } = this.state
    return (
      <>
        {/* {loader ? (
          <div className="loader"></div>
        ) : ( */}

        <div className="tab-wrapper">
          <div className='' >
            <div className="row">
              <div className="col-sm-12">
                <div className="tab-item-wrapper">
                  <h5>Tag Wise Report</h5>
                  <br />
                  <div className="report_form">
                    <div>
                      <label>SELECT TAG</label>
                      <Select options={this.state.tagList} className="tag_select" onChange={this.changeDeviceView} />
                    </div>
                  </div>
                  <br />
                  {this.state.reportload ? (
                    <div className="reportload"></div>
                  ) : (<>
                    <button onClick={this.downloadTagFile}>EXCEL</button>
                    <button onClick={this.downloadTagPDF}>PDF</button>
                    <button onClick={this.viewTag}>VIEW</button>
                    <button id="myBtn"
                    //onClick={this.openModal}
                    >
                      VIEW GRAPH</button>
                  </>
                  )}
                </div>
                <br />

                <div id="displaytable" style={{ display: 'none' }} >
                  <hr />
                  <div className="">
                    <div className="report_form">
                      <div className="select__block">
                        <label >Duration</label>
                        <select className="form-control" onChange={this.changeDuration} >
                          <option>Select Duration</option>
                          <option value="today">Today's</option>
                          <option value="lastWeek">Last 7 Days</option>
                          <option value="lastMonth">Last 30 Days</option>
                        </select>
                      </div>
                      <div>
                      </div>
                    </div>
                    <br />
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
                          {tableData.map((obj, key) => (
                            <>
                              <tr key={key} >
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
                                  {obj.dispatchTime}
                                  {/* {obj.dispatchTime === "N/A" ? obj.dispatchTime : moment(obj.dispatchTime, "HH:mm").format("HH:mm")} */}
                                </td>
                                {(this.state.Role === 'super-admin') ? <>
                                  {allColumn.map((obj) => (
                                    <td>
                                      {/* {obj.allocatedColumn_ui} */}
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
                  </div>
                  <br />
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
                  <div id="myModal" class="modal">
                    <div class="modal-content">
                      <div class="graphclose">
                        <span class="close">&times;</span>
                      </div>
                      <HighchartsReact
                        className="mscalculator-chart"
                        highcharts={Highcharts}
                        options={getoptions(this.state.graphGateway,this.state.graphStayTime, this.state.selectedDevice)}
                        style={{ with: "100%" }}
                      />
                    </div>
                  </div>
                  {/* <Modal show={this.state.isOpen} onHide={this.closeModal}>
                        <div class="graphclose">
                          <span class="close">&times;</span>
                        </div>
                        <Modal.Body>
                          <HighchartsReact
                            className="mscalculator-chart"
                            highcharts={Highcharts}
                            options={getoptions(this.state.graph, this.state.selectedDevice)}
                          />
                        </Modal.Body>
                      </Modal> */}
                </div>

                {/* )}  */}
              </div>
            </div>
          </div>
          <br />
        </div>

      </>
    )
  }
}
