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
import swal from "sweetalert";
import { allGatewayWiseGraphData } from "../../Service/allGatewayWiseGraphData"
import { getTimeWise } from '../../Service/getTimeWise'
import { getTimeTag } from '../../Service/getTimeTag'

const SignupSchema = Yup.object().shape({
  type_of: Yup.string().required('Required'),
});

export default class DateTimeWiseReport extends Component {
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

    if (this.state.startDateReport !== "" && this.state.endDateReport !== "" && this.state.startTime !== "" && this.state.endTime !== "") {
      let resultTime = await getTimeWise(selectedPage, this.state.startDateReport, this.state.endDateReport, this.state.startTime, this.state.endTime);
      console.log("All Time Pagination Data", resultTime)
      if (resultTime && resultTime.data && resultTime.data.content && resultTime.data.content.length !== 0) {
        const data = resultTime.data.content;
        this.setState({
          currentPage: selectedPage,
          pageCount: resultTime.data.totalPages,
          tableTimeData: data,
          offset: resultTime.data.pageable.offset,
          loader: false,
        });
      }
    }

  };


  async componentDidMount() {
    try {
      this.setState({ loader: true })

      this.receivedTimeData()
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

    } catch (error) {
      console.log(error)
      this.setState({ loader: false })
    }
  }
  startDate = (e) => {
    this.setState({ startDateReport: moment(e.target.value).format("yyyy-MM-DD") }, async() => {
      console.log('startDateReport', this.state.startDateReport)
      let resultTime = await getTimeWise(this.state.currentPage, this.state.startDateReport, this.state.endDateReport, this.state.startTime, this.state.endTime);
      console.log("All Time Data", resultTime)
      if (resultTime && resultTime.data && resultTime.data.length !== 0) {
        const data = resultTime.data.content;
        this.setState(
          {
            pageCount: resultTime.data.totalPages,
            tableTimeData: data,
            loader: false,
          }, () => {
            console.log("Time Data", resultTime.data)
          }
        );
      } 
    })
  }

  endDate = (e) => {
    this.setState({ endDateReport: moment(e.target.value).format("yyyy-MM-DD") }, async() => {
      console.log('endDateReport', this.state.endDateReport)
      let resultTime = await getTimeWise(this.state.currentPage, this.state.startDateReport, this.state.endDateReport, this.state.startTime, this.state.endTime);
      console.log("All Time Data", resultTime)
      if (resultTime && resultTime.data && resultTime.data.length !== 0) {
        const data = resultTime.data.content;
        this.setState(
          {
            pageCount: resultTime.data.totalPages,
            tableTimeData: data,
            loader: false,
          }, () => {
            console.log("Time Data", resultTime.data)
          }
        );
      } 
    })
  }
  startTimeValue = (e) => {
    this.setState({ startTime: moment(e.target.value, 'HH:mm:ss').format('HH:mm:ss') }, async() => {
      console.log('startTime', this.state.startTime)
      let resultTime = await getTimeWise(this.state.currentPage, this.state.startDateReport, this.state.endDateReport, this.state.startTime, this.state.endTime);
      console.log("All Time Data", resultTime)
      if (resultTime && resultTime.data && resultTime.data.length !== 0) {
        const data = resultTime.data.content;
        this.setState(
          {
            pageCount: resultTime.data.totalPages,
            tableTimeData: data,
            loader: false,
          }, () => {
            console.log("Time Data", resultTime.data)
          }
        );
      } 
    })
  }
  endTimeValue = (e) => {
    try {

      this.setState({ endTime: moment(e.target.value, 'HH:mm:ss').format('HH:mm:ss') }, async () => {
        console.log('endTime', this.state.endTime)
        // setTimeout(async () => {
        let resultTime = await getTimeWise(this.state.currentPage, this.state.startDateReport, this.state.endDateReport, this.state.startTime, this.state.endTime);
        console.log("All Time Data", resultTime)
        if (resultTime && resultTime.data && resultTime.data.length !== 0) {
          const data = resultTime.data.content;
          this.setState(
            {
              pageCount: resultTime.data.totalPages,
              tableTimeData: data,
              loader: false,
            }, () => {
              console.log("Time Data", resultTime.data)
            }
          );
        } else {
          this.setState(
            {
              pageCount: {},
              tableTimeData: [],
            }
          );
          swal("Sorry", "Data is not present", "warning");
        }
        // }, 200)
      })

    } catch (error) {
      console.log(error)
      this.setState({ loader: false })
    }
  }


  async receivedTimeData() {
    try {
    let resultTimeTag = await getTimeTag();
    console.log("All Time Gateway Data", resultTimeTag.data)
    if (resultTimeTag && resultTimeTag.data && resultTimeTag.data.length !== 0) {
    this.setState({ allTime: resultTimeTag.data }, () => {
      console.log('startdate', this.state.allTime)
    })
  } else {
    this.setState({ allTime: [] }, () => {
      //console.log('startdate', this.state.allTime)
    })
    swal("Sorry", "Data is not present", "warning");
  }
  } catch (error) {
    console.log(error)
    this.setState({ loader: false })
  }
  }

  downloadTime = async () => {
    if (this.state.startDateReport !== '' && this.state.endDateReport !== '' && this.state.startTime !== '' && this.state.endTime !== '') {
      let token = await localStorage.getItem("token");
      let fkUserId = await localStorage.getItem('fkUserId');
      let role = await localStorage.getItem('role');
      let fileType = ["excel"];
      axios.get(`/tag/get-tag-Wise-gateway-View-between-date-and-time-Report?fromdate=${this.state.startDateReport}&todate=${this.state.endDateReport}&fromtime=${this.state.startTime}&totime=${this.state.endTime}&fileType=${fileType}&fkUserId=${fkUserId}&role=${role}`, {
        headers: { "Authorization": `Bearer ${token}` },
        responseType: 'arraybuffer'
      })
        .then((response) => {
          var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
          fileSaver.saveAs(blob, `TagDetailsGetewaywiseReportExcel.xls`);
          this.setState({ reportload: false })
        });
    } else {
      swal("Sorry", "Please select Date and Time", "warning");
      this.setState({ reportload: false })
    }
  }



  changeTimeview() {
    if (document.getElementById("displaytable5").style.display === "none")
      document.getElementById("displaytable5").style.display = "block";
    else
      document.getElementById("displaytable5").style.display = "none";
  }

  render() {
    const { allTime, tableTimeData, loader } = this.state
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
                    <h5>Date & Time Wise Report</h5>
                    <br />
                    <div className="report_form">
                      <div>
                        <label>START DATE</label>
                        <input className="form-control" type="date" name="sDate" onChange={this.startDate} />
                      </div>
                      <div>
                        <label>END DATE</label>
                        <input className="form-control" type="date" name="eDate" onChange={this.endDate} />
                      </div>
                      <div>
                        <label >Start Time</label>
                        <input className="form-control" type="time" step='1' name="fromtime" onChange={this.startTimeValue} defaultValue="00:00:00" />
                      </div>
                      <div>
                        <label >End Time</label>
                        <input className="form-control" type="time" step='1' name="totime" onChange={this.endTimeValue} defaultValue="00:00:00" />
                      </div>
                    </div>
                    <br />
                    <button onClick={this.downloadTime}>EXCEL</button>
                    <button onClick={this.changeTimeview}>VIEW</button>
                  </div>
                  <div id="displaytable5" style={{ display: 'none' }} >
                    <br />
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm">
                      <table className=" table table-hover table-bordered table-striped text-center">
                        <tr className="tablerow">
                          <th>Sr_No</th>
                          <th>Tags / Gateways</th>
                          {allTime.map((obj) => (
                            <th>{obj}</th>
                          ))}
                        </tr>
                        <tbody>
                          {tableTimeData.map((obj, key) => (
                            <tr key={key}>
                              <td>
                                {this.state.offset + key + 1}
                              </td>
                              <td>{obj.assetTagName}</td>
                              {allTime.map((obj1) => (
                                obj.assetGatewayName === obj1 ?
                                  <td>{obj.entryTime}</td>
                                  :
                                  <td>N/A</td>
                              ))
                              }
                            </tr>
                          ))
                          }
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
