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

//<script src="//code.tidio.co/o8rk9uytps64ic5pp82bbu14pv3o1qi5.js" async></script>

const SignupSchema = Yup.object().shape({
  type_of: Yup.string().required('Required'),
});

export default class DateWiseTagReport extends Component {
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
      reportload:false,
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
      graphStayTime: [],
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
   
    if (this.state.startDate !== "" && this.state.endDate !== "" && this.state.AssetTag !== "") {
      let result = await getDateWiseTag(selectedPage, this.state.startDate, this.state.endDate, this.state.AssetTag);
      console.log("Date wise Tag List", result);
      if (result && result.data && result.data.length !== 0) {
        const data = result.data.content;
        this.setState({
          currentPage: selectedPage,
          pageCount: result.data.totalPages,
          tableTagData: data,
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

      this.receivedTagData()
      // this.ColumnData()

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

     

      let data = await allGatewayWiseGraphData();
      console.log('gatewayGraphData', data.data)
      this.setState({ newageGraphdata: data.data }, () => {
        //  console.log('newageGraphdata', this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0])
      })
      this.setState({ gatewayName: this.state.newageGraphdata[0].gatewayNames, newCount: this.state.newageGraphdata[0].newTagCount, agedCount: this.state.newageGraphdata[0].agedTagCount })
      console.log('Gateway Graph Data', this.state.newageGraphdata, this.state.gatewayName, this.state.newCount, this.state.agedCount)




      //*********Datewise tag Graph Madal***********
      var modalDateTag = document.getElementById("myModal-DateWiseTag");
      var buuton = document.getElementById("myBtn-DateWiseTag");
      var span = document.getElementsByClassName("close-Tagwise")[0];
      buuton.onclick = function () {
        modalDateTag.style.display = "block";
      }
      span.onclick = function () {
        modalDateTag.style.display = "none";
      }
      window.onclick = function (event) {
        if (event.target == modalDateTag) {
          modalDateTag.style.display = "block";
        }
      }
    }, 200);
      //*********End Graph Madal***********
    } catch (error) {
      console.log(error)
      this.setState({ loader: false })
    }

  }

  
  async receivedTagData() {
    let result = await getDateWiseTag(this.state.currentPage, this.state.startDate, this.state.endDate, this.state.selectedDevice);
    console.log("Date Wise Tag Data", result.data)
    if (result && result.data && result.data.content && result.data.content.length !== 0) {
      const data = result.data.content;
      this.setState(
        {
          pageCount: result.data.totalPages,
          tableTagData: data,
          loader: false,
        },
      );
    }

    else {
      this.setState(
        {
          pageCount: {},
          tableTagData: [],
        }
      );
      //  swal("Sorry", "Data is not present", "warning");
    }
  }

  changeTagDevice = async (selectedOptions) => {
    console.log(selectedOptions.value)

    if (selectedOptions.value == "Select Tag device") {
      this.receivedData()
    }
    else {
      this.setState({ selectedDevice: selectedOptions.value }

      );
    }

  }

  changeDevice = (selectedTagDevice) => {
    this.setState({ AssetTag: selectedTagDevice.value }, async () => {
      console.log('AssetTag', this.state.AssetTag)
     
      let result = await getDateWiseTag(this.state.currentPage, this.state.startDate, this.state.endDate, this.state.AssetTag);
      console.log("Date Wise Tag Data", result)
      if (result && result.data && result.data.content && result.data.content.length !== 0) {
        const data = result.data.content;
        this.setState(
          {
            pageCount: result.data.totalPages,
            tableTagData: data,
            loader: false,
          },
        );
      }

      else {
        this.setState(
          {
            pageCount: {},
            tableTagData: [],
          }
        );
        swal("Sorry", "Data is not present", "warning")
      }
   
      let resultStay = await getTagStayTimeGraph(this.state.startDate, this.state.endDate, this.state.AssetTag);
      console.log('resultStay', resultStay.data);
      // let staytime = Object.entries(resultStay.data);
      // console.log('Stay Time', staytime);
      // this.setState({ Taggraph: staytime, loader: false },
      //   () => {
      //     console.log('Stay Time Date Wise Tag Data', this.state.Taggraph)
      //   })

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
    });
   
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
  startTimeValue = (e) => {
    this.setState({ startTime: moment(e.target.value, 'hh:mm:ss').format('hh:mm:ss') }, () => {
      console.log('startTime', this.state.startTime)
    })
  }

  downloadDateWiseTagFile = async () => {
    this.setState ({reportload: true} , () =>{
      console.log('reportload', this.state.reportload)
    })
    if (this.state.startDate !== '' && this.state.endDate !== '') {
      let token = await localStorage.getItem("token")
      let fkUserId = await localStorage.getItem('fkUserId');
      let role = await localStorage.getItem('role');
      let fileType = ["excel"];
      let category = ["BLE"];
      axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-between-date?fileType=${fileType}&fromDate=${this.state.startDate}&toDate=${this.state.endDate}&tagName=${this.state.AssetTag}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
        headers: { "Authorization": `Bearer ${token}` },
        responseType: 'arraybuffer'
      })
        .then((response) => {
          
          var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
          fileSaver.saveAs(blob, 'TagDetailsReportExcelBetweenDate.xls');
          this.setState ({reportload: false})
        });
    
  }else {
    swal("Sorry", "Please select Date", "warning");
    this.setState({reportload:false})
  }
  }

  downloadDateWiseTagPDF = async () => {
    this.setState ({reportload: true} , () =>{
      console.log('reportload', this.state.reportload)
    })
    if (this.state.startDate !== '' && this.state.endDate !== '') {
    let token = await localStorage.getItem("token");
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let fileType = ["pdf"];
    let category = ["BLE"];
    axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-between-date?fileType=${fileType}&fromDate=${this.state.startDate}&toDate=${this.state.endDate}&tagName=${this.state.AssetTag}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
      headers: { "Authorization": `Bearer ${token}` },
      responseType: 'arraybuffer'
    })
      .then((response) => {
        var blob = new Blob([response.data], { type: 'application/pdf' });
        fileSaver.saveAs(blob, `TagDetailsReportPDFBetweenDate.pdf`);
        this.setState({reportload:false})
      });
    }else {
      swal("Sorry", "Please select Date", "warning");
      this.setState({reportload:false})
    }
  }

  changeTagWiseDateview() {
    // if (this.state.AssetTag === "") {
    //   swal("Sorry", "Please select asset tag", "warning");
    // }
    if (document.getElementById("displaytable3").style.display === "none")
      document.getElementById("displaytable3").style.display = "block";
    else
      document.getElementById("displaytable3").style.display = "none";
  }

  render() {
    const { tableTagData, allColumn,loader} = this.state
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
                      <h5>Date Wise Tag Report</h5>
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
                          <label>SELECT TAG</label>
                          <Select options={this.state.tagList} name="selectDevice" className="tag_select" onChange={this.changeDevice} />
                        </div>
                      </div>
                      <br />
                      {/* <p>For Date Wise Tag Export Click on Download Button</p> */}
                      <button onClick={this.downloadDateWiseTagFile}>EXCEL</button>
                      <button onClick={this.downloadDateWiseTagPDF}>PDF</button>
                      <button onClick={this.changeTagWiseDateview}> VIEW</button>
                      <button id="myBtn-DateWiseTag">VIEW GRAPH</button>
                    </div>
                    <div id="displaytable3" style={{ display: 'none' }} >
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
                            {tableTagData.map((obj, key) => (
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
                      <div id="myModal-DateWiseTag" class="modal-DatewiseTag">
                        <div class="modal-content-Tagwise">
                          <div class="graphclose">
                            <span class="close-Tagwise">&times;</span>
                          </div>
                          <HighchartsReact
                            className="mscalculator-chart"
                            highcharts={Highcharts}
                            options={getDateWiseTagoptions(this.state.graphGateway,this.state.graphStayTime, this.state.selectedTagDevice)}
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
