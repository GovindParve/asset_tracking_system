import React, { Component } from 'react'
import ReactPaginate from 'react-paginate';
import { listAssetTagforTrack } from "../../Service/listAssetTagforTrack"
import { deleteBatch } from "../../Service/deleteBatch"
import "./mainTracking.css";
import { getTrackListByTag } from "../../Service/getTrackListByTag"
import { listGateway } from "../../Service/listGateway"
import Select from "react-select";
import axios from '../../utils/axiosInstance'
import moment from "moment";
import fileSaver from "file-saver"
import { getTrackData } from "../../Service/getTrackData"
import { getDuration } from "../../Service/getDuration"
import { getPageWiseTagDuration } from '../../Service/getPageWiseTagDuration'
import { getPageWiseTagTime } from '../../Service/getPageWiseTagTime'
import { getColumnList } from '../../Service/getColumnList'
import { getAdminColumnList } from '../../Service/getAdminColumnList'
import { getDynamicColumnList } from '../../Service/getDynamicColumnList'
import { InputGroup, InputGroupText } from "reactstrap";
import { getSearchTracking } from '../../Service/getSearchTracking'
import { Link } from 'react-router-dom';
import swal from "sweetalert";

export default class OrganizationTrackList extends Component {
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
      allColumn: [],
      allColumnData: [],
      selectGateway: "",
      checkedValue: [],
      selectDuration: "",
      selectTime: "",
      selectedDevice: "",
      Role: "",
      loader: false,
      battryPercentage: "",
      heading: [
        "Sr_No",
        "Tag_Name",
        "Gateway_Name",
        // "Date",
        "Tag_Entry_Location",
        "Entry_Time",
        "Last_Location",
        "Exit_Time",
        // "Dispatch_Time",
        "Battery (%)"
      ],
      row: [
        "assetTagName",
        "assetGatewayName",
        // "date",
        // "tagEntryLocation",
        "entryTime",
        "tagExistLocation",
        "existTime",
        // "dispatchTime",
        "time",
        "battryPercentage",
      ],
      allList: [
        {
          label: "Tag Name",
          value: "assetTagName"
        },
        {
          label: "Gateway Name",
          value: "assetGatewayName"
        },
        // {
        //   label: "Date",
        //   value: "date"
        // },
        {
          label: "Entry Time",
          value: "entryTime"
        },
        {
          label: "Tag Location",
          value: "tagEntryLocation"
        },
        {
          label: "Exit Time",
          value: "existTime"
        },
        // {
        //   label: "Dispatch Time",
        //   value: "dispatchTime"
        // },
        {
          label: "Update Time",
          value: "time"
        },
        {
          label: "Battery %",
          value: "battryPercentage"
        },
      ],

      searchTerm: ""
    };
    this.handlePageClick = this.handlePageClick.bind(this);
  }

  handlePageClick = async (e) => {
    const selectedPage = e.selected;
    if (this.state.selectedDevice === "" && this.state.selectDuration === "" && this.state.selectTime === "") {
      let result = await getTrackData(selectedPage);
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

    } else if (this.state.selectedDevice !== "" && this.state.selectDuration === "") {
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
      } else {
        this.setState(
          {
            pageCount: {},
            tableData: [],
          }
        );
        swal("Sorry", "Data is not present", "warning");
      }
    } else if (this.state.selectedDevice !== "" && this.state.selectDuration !== "") {
      let result = await getPageWiseTagDuration(selectedPage, this.state.selectedDevice, this.state.selectDuration);
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
      } else {
        this.setState(
          {
            pageCount: {},
            tableData: [],
          }
        );
        swal("Sorry", "Data is not present", "warning");
      }
    } else if (this.state.selectTime !== "") {
      let result = await getPageWiseTagTime(selectedPage, this.state.selectedDevice, this.state.selectTime);
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
      } else {
        this.setState(
          {
            pageCount: {},
            tableData: [],
          }
        );
        swal("Sorry", "Data is not present", "warning");
      }
    } else if (this.state.selectedHeading !== "" && this.state.searchTerm !== "") {
      let result = await getSearchTracking(selectedPage, this.state.selectedHeading, this.state.searchTerm);
      console.log("Search wise Pagination", result);
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

      let resultColumn = await getDynamicColumnList();
      console.log("All Orgnization Column Data", resultColumn.data.list)
      resultColumn.data.list.map((obj) => {
        console.log('Column', obj);
        this.state.row.push(obj.columnName)
      })
      console.log('Row', this.state.row)
      this.setState({ allColumn: resultColumn.data.list }, () => {
        console.log('Column', this.state.allColumn)
      })
    } catch (error) {
      console.log(error)
      // swal("Sorry", "Data is not present", "warning");
    }
    try {
      this.receivedData()
      let [resultAssetTagList, resultAssetGatewayList, setRole] = await Promise.all([listAssetTagforTrack(), listGateway(), localStorage.getItem('role')]);
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
      swal("Sorry", "Data is not present", "warning");
    }
  }

  clickCard = async (tag) => {
    this.props.history.push({
      pathname: `/singleTag-tracking`,
      state: { deviceId: tag },
    });
  }


  async receivedData() {
    setTimeout(async () => {
      let resultPage = await getTrackData(this.state.currentPage);
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
      } else {
        this.setState(
          {
            pageCount: {},
            tableData: [],
          }
        );
        swal("Sorry", "Data is not present", "warning");
      }
    }, 200)
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
          } else {
            this.setState(
              {
                pageCount: {},
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
        if (this.state.selectedDevice === ""){
          swal("Sorry", "Please Select Tag Name", "warning");
        }else{
        let result = await getPageWiseTagDuration(this.state.currentPage, this.state.selectedDevice, this.state.selectDuration);
        console.log('this.state.selectedDevice', this.state.selectedDevice);
        if (result && result.data && result.data.content && result.data.content.length !== 0) {
          const data = result.data.content;
          this.setState(
            {
              pageCount: result.data.totalPages,
              tableData: data,
              loader: false,
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
      });
    }
  }
  changeHeading = async (selectedOptions) => {
    console.log("selected option", selectedOptions.value)
    this.setState({ selectedHeading: selectedOptions.value }

    );
    // }
  }
  changeTracking = async (selectedOptions) => {
    try {
      let result = await getSearchTracking(this.state.currentPage, this.state.selectedHeading, this.state.searchTerm);
      console.log("search data", result.data)
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
        this.setState(
          {
            pageCount: {},
            tableData: [],
          }
        );
        swal("Sorry", "Data is not present", "warning");
      }
    } catch (error) {
      console.log(error)

    }
  }


  render() {
    const { tableData, allColumn, allColumnData, row, heading } = this.state
    const { data } = this.state
    return (
      <>
        <div>
          <div className='row'>
            <div className='col-md-3'>
              <div className="tag_wiseinput">
                <div>
                  <label>SELECT ASSET TAG</label>
                  <Select options={this.state.tagList} onChange={this.changeDevice} />
                </div>
              </div>
            </div>
            <div className='col-md-3'>
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
            <div className='col-md-3'>
              <div className="select_block">
                <label >SELECT OPTION</label>
                <Select options={this.state.allList} value={
                  this.state.allList
                    ? this.state.allList.find(
                      (option) => option.value === this.state.optionUser
                    )
                    : ""
                } onChange={this.changeHeading} placeholder="Select Option" />
              </div>
            </div>
            <div className='col-md-3'>
              <div className="select_block">
                <label >SEARCH</label>
                <InputGroup>
                  <input
                    type="text"
                    className="form-control-alternative form-control"
                    onChange={(e) => {
                      this.setState({ searchTerm: e.target.value });
                    }}
                  />
                  <InputGroupText>
                    <span onClick={this.changeTracking}>
                      <i className="icon-magnifier"></i>
                    </span>
                  </InputGroupText>
                </InputGroup>
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
                <th>Tag_History</th>
                <th>Sr_No</th>
                <th>Tag_Name</th>
                <th>Gatway_Name</th>
                {/* <th className='date'>Date</th> */}
                {/* <th>Tag_Entry_Location</th> */}
                <th className='entrytime'>Entry_Time</th>
                <th>Tag_Location</th>
                <th className='entrytime'>Exit_Time</th>
                {/* <th>Dispatch_Time</th> */}
                <th>Update_Time</th>
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
                      {(this.state.Role === 'admin') ? <>
                        <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.assetTagName)}><button className="btn btn-info">Click Me</button></td></>
                        : (this.state.Role === 'user') ? <>
                          <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.assetTagName)}><button className="btn btn-info">Click Me</button></td></>
                          : <>
                          </>}

                      <td> <button className='btn btn-info'><Link to={{ pathname: "/singleTag-tracking", state: { deviceId: obj.assetTagName } }}>Click Me</Link></button></td>
                      <td>
                        {this.state.offset + key + 1}
                      </td>
                      {row.map((obj1) => (
                        <td>
                          {/* obj1 === "date" ? moment(obj["entryTime"]).format('YYYY-MM-DD') :
                            // obj1 === "dispatchTime" ? moment(obj[obj1], 'HH:mm').format('HH:mm') : */}
                              {obj1 === "entryTime" ? moment(obj[obj1]).format('YYYY-MM-DD HH:mm:ss') :
                                obj1 === "existTime" ? obj[obj1] === "N/A" ? obj[obj1] : moment.tz(obj[obj1], 'YYYY-MM-DD HH:mm:ss').format('YYYY-MM-DD HH:mm:ss') :
                                  // {obj.entryTime !== null ? moment.tz(obj.entryTime ,"Asia/Kolkata").format("hh:mm:ss A") : '-'}
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


