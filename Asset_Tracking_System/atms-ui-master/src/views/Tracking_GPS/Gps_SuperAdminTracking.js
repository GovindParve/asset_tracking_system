import React, { Component } from 'react'
import ReactPaginate from 'react-paginate';
import { listAssetGPSTagforTrack } from "../../Service/listAssetGPSTagforTrack"
import { deleteBatch } from "../../Service/deleteBatch"
import "./Gps_mainTracking.css";
import { getTrackListByTag } from "../../Service/getTrackListByTag"
import { listGateway } from "../../Service/listGateway"
import Select from "react-select";
import axios from '../../utils/axiosInstance'
import moment from "moment";
import fileSaver from "file-saver"
import { getGpsTrackData } from "../../Service/getGpsTrackData"
import { getDuration } from "../../Service/getDuration"
import { getPageWiseGPSTagDuration } from '../../Service/getPageWiseGPSTagDuration'
import { getPageWiseGPSTagTime } from '../../Service/getPageWiseGPSTagTime'
import { getGpsColumnList } from '../../Service/getGpsColumnList'
import { Link} from 'react-router-dom';
import swal from "sweetalert";

export default class Gps_SuperAdminTracking extends Component {
  constructor(props) {
    super(props);
    this.state = {
      offset: 0,
      // data: [],
      tableData: [],
      tableGatewayData: [],
      orgtableData: [],
      perPage: 10,
      currentPage: 0,
      tagList: [],
      checkedStatus: [],
      gatewayList: [],
      allData: [],
      allColumn:[],
      allColumnData:[],
      selectGateway: "",
      checkedValue: [],
      selectDuration:"",
      selectTime:"",
      selectedDevice: "",
      Role: "",
      loader: false,
      battryPercentage: "",
      heading: [
        "Sr_No",
        "Asset_Tag_Name",
        "Date",
        "Tag_Entry_Location",
        "Entry_Time",
        "Last_Location",
        "Exit_Time",
        "Dispatch_Time",
        "Battery (%)"
      ],
      row: [
        "assetTagName",
        // "assetGatewayName",
        "date",
        "imeiNumber",
        "latitude",
        "longitude",
        // "tagEntryLocation",
        // "tagExistLocation",
        // "existTime",
        "dispatchTime",
        "battryPercentage"
      ]
    };
    this.handlePageClick = this.handlePageClick.bind(this);
  }

  handlePageClick = async (e) => {
    const selectedPage = e.selected;
    if (this.state.selectedDevice === "" && this.state.selectDuration === "" && this.state.selectTime === "") {
      let result = await getGpsTrackData(selectedPage);
      console.log("Tag List result", result);
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

    } else if (this.state.selectedDevice !== "" ) {
      let result = await getTrackListByTag(selectedPage, this.state.selectedDevice);
      console.log("Tag wise Pagination", result);
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
    } else if (this.state.selectDuration !== "" ) {
    let result = await getPageWiseGPSTagDuration(selectedPage,this.state.selectedDevice, this.state.selectDuration);
    console.log("Duration wise Pagination", result);
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
    } else if (this.state.selectTime !== "" ) {
    let result = await getPageWiseGPSTagTime(selectedPage,this.state.selectedDevice, this.state.selectTime);
    console.log("Duration wise Pagination", result);
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
    let resultColumn = await getGpsColumnList();
    console.log("All Column Data", resultColumn.data)
    resultColumn.data.map((obj) => {
      console.log('Column', obj);
      //this.state.heading.push(obj.allocatedColumn_ui)
     
      this.state.row.push(obj.columnName)
      
    })
    console.log('All Row', this.state.row)
    this.setState({ allColumn:resultColumn.data }, () => {
      console.log('Column', this.state.allColumn)
    })

    this.receivedData()
    let [resultAssetTagList, resultAssetGatewayList, setRole] = await Promise.all([listAssetGPSTagforTrack(), listGateway(), localStorage.getItem('role')]);
    console.log("Gps Tag List",resultAssetTagList)
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

  }
  }

  clickCard = async (tag) => {
    this.props.history.push({
      pathname: `/singleTag-tracking`,
      state: { deviceId: tag },
    });
  }

  
  async receivedData() {
    let resultPage = await getGpsTrackData(this.state.currentPage);
    console.log("All Tracking List", resultPage.data)
    if (resultPage && resultPage.data && resultPage.data.content && resultPage.data.content.length !== 0) {
      const data = resultPage.data.content;
      this.setState(
        {
          pageCount: resultPage.data.totalPages,
          tableData: data,
          loader: false,
        },
      );
    }  else {    
      this.setState(
          {
              pageCount:{},
              tableData: [],
          }
      );
      swal("Sorry", "Data is not present", "warning");
  }
  }

  changeDevice = async (selectedOptions) => {
    console.log(selectedOptions.value)
    if (selectedOptions.value == "Select device") {
      this.receivedData()
    } else {
      this.setState({ selectedDevice: selectedOptions.value },
        async () => {
          let resultTag = await getTrackListByTag(this.state.currentPage, this.state.selectedDevice);
          console.log("Viewresult", resultTag)
          if (resultTag && resultTag.data && resultTag.data.content && resultTag.data.content.length !== 0) {
            const data = resultTag.data.content;
            this.setState(
              {
                pageCount: resultTag.data.totalPages,
                tableData: data,
                loader: false,
              }
            );
          }  else {    
            this.setState(
                {
                    pageCount:{},
                    tableData: [],
                }
            );
            swal("Sorry", "Data is not present", "warning");
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
        let result = await getPageWiseGPSTagDuration(this.state.currentPage, this.state.selectedDevice, this.state.selectDuration);
        console.log('this.state.selectedDevice', this.state.selectedDevice);
        if (result && result.data && result.data.length !== 0) {
          const data = result.data.content;
          this.setState(
            {
              pageCount: result.data.totalPages,
              tableData: data,
              loader: false,
            }
          );
        }  else {    
          this.setState(
              {
                  pageCount:{},
                  tableData: [],
              }
          );
          swal("Sorry", "Data is not present", "warning");
      }
      });
    }
  }


  render() {
    const { tableData,allColumn,allColumnData,row,heading} = this.state
    const { data } = this.state
    return (
      <>
        <div>
          <div className='row'>
            <div className='col-md-4'>
              <div className="tag_wiseinput">
                <div>
                  <label>SELECT ASSET TAG</label>
                  <Select options={this.state.tagList} onChange={this.changeDevice} />
                </div>
              </div>
            </div>
            <div className='col-md-4'>
              <div className="select_block">
                <label >Time / DURATION</label>
                <select className="form-control" onChange={this.changeDuration} >
                  <option>Select Duration</option>
                  <option value="15min">Before 15 Minute</option>
                  <option value="30min">Before 30 Minute</option>
                  <option value="45min">Before 45 Minute</option>
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
                  {/* <th>Tag_History</th>
                    {heading.map((obj) => (
                  <th>
                    {obj}
                  </th>
                ))} */}
                {/* <th>Tag_History</th> */}
                <th>Sr_No</th>
                <th>Tag_Name</th>
                {/* <th>Gatway_Name</th> */}
                <th>Date</th>
                {/* <th>SIM</th>
                <th>IMSI</th> */}
                <th>IMEI</th>
                <th>Latitude</th>
                <th>Longitude</th>
                <th>Dispatch_Time</th>
                <th>Battery (%)</th>
                
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
                    <tr key={key} className={obj.prob_tamp || obj.mag_tamp || obj.axis_tamp === 1 ? "" : ""}>
                      {/* {(this.state.Role === 'admin') ? <>
                        <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.assetTagName)}><button className="btn btn-info">Click Me</button></td></>
                        : (this.state.Role === 'user') ? <>
                          <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.assetTagName)}><button className="btn btn-info">Click Me</button></td></>
                          : <>  
                          </>} */}
                        
                          {/* <td> <button className='btn btn-info'><Link to={{ pathname: "/singleTag-tracking", state: { deviceId: obj.assetTagName } }}>Click Me</Link></button></td> */}
                      <td>
                        {this.state.offset + key + 1}
                      </td>
                      {row.map((obj1) => (
                      <td>
                        {obj1 === "date" ? moment(obj[obj1]).format('DD/MM/YYYY') :
                           obj1 === "dispatchTime" ? moment(obj[obj1], 'hh:mm A').format('hh:mm A') :
                            obj[obj1]}
                      </td>
                    ))}
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
        <br />
      </>
    )
  }
}


