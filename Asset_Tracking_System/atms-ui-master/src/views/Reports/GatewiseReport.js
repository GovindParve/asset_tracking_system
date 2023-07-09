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
import TagWiseReport from './TagWiseReport';
import UserReport from './UserReport';


import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
//<script src="//code.tidio.co/o8rk9uytps64ic5pp82bbu14pv3o1qi5.js" async></script>

const SignupSchema = Yup.object().shape({
  type_of: Yup.string().required('Required'),
});

export default class GatewiseReport extends Component {
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
      reportload : false,
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

    if (this.state.selectedGateway !== "" && this.state.selectGatewayDuration !== "") {
      let resultGateway = await getViewGatewayReport(selectedPage, this.state.selectedGateway, this.state.selectGatewayDuration);
      console.log("GateWayDuration Pagination List", resultGateway);
      if (resultGateway && resultGateway.data && resultGateway.data.length !== 0) {
        const data = resultGateway.data.content;
        this.setState({
          currentPage: selectedPage,
          pageCount: resultGateway.data.totalPages,
          tableGatewayData: data,
          offset: resultGateway.data.pageable.offset,
          loader: false,
        });
      }
    } else if (this.state.selectedGateway === "" && this.state.selectGatewayDuration === "") {
      let resultGateway = await getViewGatewayReport(selectedPage, this.state.selectedGateway, this.state.selectGatewayDuration);
      console.log("GateWayDuration Pagination List", resultGateway);
      if (resultGateway && resultGateway.data && resultGateway.data.length !== 0) {
        const data = resultGateway.data.content;
        this.setState({
          currentPage: selectedPage,
          pageCount: resultGateway.data.totalPages,
          tableGatewayData: data,
          offset: resultGateway.data.pageable.offset,
          loader: false,
        });
      }
    }


  };


  async componentDidMount() {
    try {
      this.setState({ loader: true })

      this.receivedGatewayData()

      // this.ColumnData()

      let [resultAssetTagList, resultAssetGatewayList, setRole] = await Promise.all([listAssetTagforTrack(), listGateway(), localStorage.getItem('role')]);
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

      let tempGatewayArray = []
      if (resultAssetGatewayList && resultAssetGatewayList.data && resultAssetGatewayList.data.length != 0) {
        resultAssetGatewayList.data.map((obj) => {
          let tempGatewayObj = {
            value: obj,
            label: obj
          }
          tempGatewayArray.push(tempGatewayObj)
        })
      }

      this.setState({ gatewayList: tempGatewayArray, Role: setRole, loader: false }, () => {
        console.log('Gateway List', this.state.gatewayList)
      })

      let data = await allGatewayWiseGraphData();
      console.log('gatewayGraphData', data.data)
      this.setState({ newageGraphdata: data.data }, () => {
        //  console.log('newageGraphdata', this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0])
      })
      this.setState({ gatewayName: this.state.newageGraphdata[0].gatewayNames, newCount: this.state.newageGraphdata[0].newTagCount, agedCount: this.state.newageGraphdata[0].agedTagCount })
      console.log('Gateway Graph Data', this.state.newageGraphdata, this.state.gatewayName, this.state.newCount, this.state.agedCount)


      //*********Gateway Wise Graph Madal***********
      var modalgateway = document.getElementById("myModal-gateway");
      var buuton = document.getElementById("myBtn-gateway");
      var span = document.getElementsByClassName("close-gateway")[0];
      buuton.onclick = function () {
        modalgateway.style.display = "block";
      }
      span.onclick = function () {
        modalgateway.style.display = "none";
      }
      window.onclick = function (event) {
        if (event.target == modalgateway) {
          modalgateway.style.display = "block";
        }
      }

   
      //*********End Graph Madal***********
    } catch (error) {
      console.log(error)
      this.setState({ loader: false })
    }

  }



  async receivedGatewayData() {
    let resultGatway = await getViewGatewayReport(this.state.currentPage, this.state.selectedGateway, this.state.selectGatewayDuration);
    console.log("All Gateway Data", resultGatway.data)
    if (resultGatway && resultGatway.data && resultGatway.data.content && resultGatway.data.content.length !== 0) {
      const data = resultGatway.data.content;
      this.setState(
        {
          pageCount: resultGatway.data.totalPages,
          tableGatewayData: data,
          offset: resultGatway.data.pageable.offset,
          loader: false,
        }
      );
    } else {
      this.setState(
        {
          pageCount: {},
          tableGatewayData: [],
        }
      );
     // swal("Sorry", "Data is not present", "warning");
    }
  }


  changeGatewayView = async (selectedOptions) => {
    console.log(selectedOptions.value)

    if (selectedOptions.value == "Select device") {
      this.receivedGatewayData()
    } else {
      this.setState({ selectedGateway: selectedOptions.value },
        async () => {
          let result = await getViewGatewayReport(this.state.currentPage, this.state.selectedGateway, this.state.selectGatewayDuration);
          console.log("View gateway result", result)
          if (result && result.data && result.data.content && result.data.content.length !== 0) {
            const data = result.data.content;
            this.setState(
              {
                pageCount: result.data.totalPages,
                tableGatewayData: data,
                loader: false,
              }
            );
          } 

        });
    }
  }

  changeGatwayDuration = async (selectedDuration) => {
    console.log(selectedDuration.target.value)
    if (selectedDuration.value == "Select Duration") {
      this.receivedGatewayData()
    } else {
      this.setState({ selectGatewayDuration: selectedDuration.target.value }, async () => {
        console.log('this.state.selectGatewayDuration', this.state.selectGatewayDuration);

        let result = await getViewGatewayReport(this.state.currentPage, this.state.selectedGateway, this.state.selectGatewayDuration);
        console.log('this.state.Duration', this.state.selectedGateway);
        let tempGatewayTagArray = [], tempOnlyTagArray = []
        const tempTag = new Set();

        if (result && result.data && result.data.content && result.data.content.length !== 0) {
          const data = result.data.content;
          console.log('GatewayDuration Pagination list', data)
          tempGatewayTagArray = result.data.content.filter(obj => {
            return obj.assetGatewayName === this.state.selectedGateway
          });
          tempGatewayTagArray.reduce((acc, obj) => {
            tempTag.add(obj.assetTagName);
            return acc;
          }, 0);
          tempOnlyTagArray = [...tempTag];
          this.setState({ newCount: [tempOnlyTagArray.length] });
          console.log('Duration Graph', this.state.newCount)
          this.setState(
            {
              pageCount: result.data.totalPages,
              tableGatewayData: data,
              loader: false,
            }
          );
        } else {
          this.setState(
            {
              pageCount: {},
              tableGatewayData: [],
            }
          );
          swal("Sorry", "Data is not present", "warning");
        }
        let resultgraph = await getSingleGraphData(this.state.selectedGateway)
        console.log('GatewayGraph', resultgraph.data)
        this.setState({ newageGraphdata: resultgraph.data }, () => {
        })
        this.setState({ gatewayName: this.state.newageGraphdata[0].gatewayNames, newCount: this.state.newageGraphdata[0].newTagCount, agedCount: this.state.newageGraphdata[0].agedTagCount })
        console.log('selected Gateway Graph', this.state.newageGraphdata, this.state.gatewayName, this.state.newCount, this.state.agedCount)
      });
    }
  }

 

 
 
  downloadGatewayFile = async () => {
    this.setState ({reportload: true} , () =>{
      console.log('reportload', this.state.reportload)
    })
    if(this.state.selectedGateway !== ""){ 
    let token = await localStorage.getItem("token");
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let fileType = ["excel"];
    let category = ["BLE"];
    axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-gateway-wise?fileType=${fileType}&gatewayName=${this.state.selectedGateway}&duration=${this.state.selectGatewayDuration}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
      headers: { "Authorization": `Bearer ${token}` },
      responseType: 'arraybuffer'
    })
      .then((response) => {
        if (this.state.loader) { 
        // <div>Please wait file is Dowloading....</div>
       }
        else{
        var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        fileSaver.saveAs(blob, `GatewayWiseTagDetailsReportExcel.xls`);
        this.setState ({reportload: false})
        }
      });
    }else {
      swal("Sorry", "Please select gateway", "warning");
      this.setState({reportload:false})
    }
  }

  downloadGatewayPDF = async () => {
    this.setState ({reportload: true} , () =>{
      console.log('reportload', this.state.reportload)
    })
    if(this.state.selectedGateway !== ""){ 
    let token = await localStorage.getItem("token");
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let fileType = ["pdf"];
    let category = ["BLE"];
    axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-gateway-wise?fileType=${fileType}&gatewayName=${this.state.selectedGateway}&duration=${this.state.selectGatewayDuration}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
      headers: { "Authorization": `Bearer ${token}` },
      responseType: 'arraybuffer'
    })
      .then((response) => {
        if (this.state.loader) { 
        // <div>Please wait file is Dowloading....</div> 
      }
        else{
        var blob = new Blob([response.data], { type: 'application/pdf' });
        fileSaver.saveAs(blob, `GatewayWiseTagDetailsReportPDF.pdf`);
        this.setState ({reportload: false})
        }
      });
    }else {
      swal("Sorry", "Please select gateway", "warning");
      this.setState({reportload:false})
    }
  }
 

  viewGateway() {
    if (document.getElementById("displaytable1").style.display === "none")
      document.getElementById("displaytable1").style.display = "block";
    else
      document.getElementById("displaytable1").style.display = "none";
  }

  
  render() {
    const {tableGatewayData, allColumn,loader} = this.state
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
                      <h5>Gateway Wise Report</h5>
                      <br />
                      <div className="Device__form__wrapper">
                        <div>
                          <label>SELECT GATEWAY</label>
                          <Select options={this.state.gatewayList} name="selectDevice" className="tag_select" onChange={this.changeGatewayView} />
                        </div>
                      </div>
                      <br />
                      <button onClick={this.downloadGatewayFile}>EXCEL</button>
                      <button onClick={this.downloadGatewayPDF}>PDF</button>
                      <button onClick={this.viewGateway}> VIEW</button>
                      <button id="myBtn-gateway">VIEW GRAPH</button>
                    </div>
                    <div id="displaytable1" style={{ display: 'none' }} >
                      <hr />
                      <div className="report_form">
                        <div>
                          <div className="select__block">
                            <label >Duration</label>
                            <select className="form-control" onChange={this.changeGatwayDuration}>
                              <option>Select Duration</option>
                              <option value="today">Today's</option>
                              <option value="lastWeek">Last 7 Days</option>
                              <option value="lastMonth">Last 30 Days</option>
                            </select>
                          </div>
                        </div>
                      </div>
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
                            {tableGatewayData.map((obj, key) => (
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
                      <div id="myModal-gateway" class="modal-gateway">
                        <div class="modal-content-gateway">
                          <div class="graphclose">
                            <span class="close-gateway">&times;</span>
                          </div>
                          <HighchartsReact
                            className="mscalculator-chart"
                            highcharts={Highcharts}
                            options={getGatewayoptions(this.state.gatewayName, this.state.newCount, this.state.agedCount)}
                            style={{ with: "100%" }}
                          />
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
