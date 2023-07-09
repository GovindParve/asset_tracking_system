import React, { Component } from 'react'
import './reports.css'
import moment from "moment";
import ReactPaginate from 'react-paginate';
import { Tabs, Tab } from 'react-bootstrap'
import axios from '../../utils/axiosInstance'
import fileSaver from "file-saver"
import Select from "react-select";
import * as Yup from 'yup';
import { getTrackColumnData } from "../../Service/getTrackColumnData"
import { getAdminDrowpdownList } from '../../Service/getAdminDrowpdownList'
import { getColumnList } from '../../Service/getColumnList'
import { getDynamicColumnList } from '../../Service/getDynamicColumnList'
import swal from "sweetalert";
import { Field } from 'formik';


export default class UserReport extends Component {
  constructor(props) {
    super(props);
    this.state = {
      offset: 0,
      data: [],
      perPage: 10,
      currentPage: 0,
      data: [],
      orgtableData: [],
      tableData: [],
      tableColumnData: [],
      Role: "",
      loader: false,
      allColumn: [],
      allColumnAdmin: [],
      selectedDevice: "",
      selectedColumn: "",
      allAdminData: [],
      selectedColumn: "",
      heading: [
        "Sr_No",
        "Asset_Tag_Name",
        "Date",
        // "Asset_Tag_Entry_Location",
        "Entry_Time",
        "Tag_Location",
        "Exit_Time",
        "Dispatch_Time"
      ],
      row: [
        "assetTagName",
        "date",
        // "tagEntryLocation",
        "entryTime",
        "tagEntryLocation",
        "existTime",
        "dispatchTime"
        //"",
      ]

    };
    this.handlePageClick = this.handlePageClick.bind(this);
  }

  handlePageClick = async (e) => {
    const selectedPage = e.selected;
    let result = await getTrackColumnData(selectedPage);
    console.log("All Column List result", result.data);
    if (result && result.data && result.data.length !== 0) {
      const data = result.data.content;
      this.setState({
        currentPage: selectedPage,
        pageCount: result.data.totalPages,
        tableColumnData: data,
        offset: result.data.pageable.offset,
        loader: false,
      });
    }

  }
  async componentDidMount() {
    try {

      let resultColumn = await getDynamicColumnList();
      console.log("All Column Data", resultColumn.data.list)
      resultColumn.data.list.map((obj) => {
        console.log('Column', obj);
        this.state.heading.push(obj.allocatedColumn_ui)
        this.state.row.push(obj.columnName)
      })
      console.log('Row', this.state.row)

      this.receivedData();
      var setRole = await localStorage.getItem('role');
      let columnList = await getAdminDrowpdownList();
      console.log('Column Drowpdown List', columnList)
      let tempArray = []
      if (columnList && columnList.data && columnList.data.length != 0) {
        columnList.data.map((obj) => {
          let tempObj = {
            // id: obj.pkId,
            value: `${obj}`,
            label: `${obj}`
          }
          console.log('Column', tempObj);
          tempArray.push(tempObj)
        })
      }
      this.setState({
        columnList: tempArray, selectedColumn: columnList && columnList.data[0], Role: setRole
      }, () => {
        console.log('Select Column', this.state.columnList)
      })


    } catch (error) {
      console.log(error)
      this.setState({ loader: false })
    }
  }

  async receivedData() {
    let result = await getTrackColumnData(this.state.currentPage);
    console.log("All Column Tracking List", result.data);
    if (result && result.data && result.data.content && result.data.content.length !== 0) {
      const data = result.data.content;
      this.setState({
        pageCount: result.data.totalPages,
        tableColumnData: data,
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
  }

  changeColumn = (selectedOptions) => {
    console.log(selectedOptions.value)
    this.setState({ selectedColumn: selectedOptions.value }, async () => {
      console.log('selected Column', this.state.selectedColumn)
      let resultAllData = await getColumnList();
      let tempTagArray = [
        "Sr_No",
        "Asset_Tag_Name",
        "Date",
        // "Asset_Tag_Entry_Location",
        "Entry_Time",
        "Tag_Location",
        "Exit_Time",
        "Dispatch_Time"
      ]
      let tempRow = [
        "assetTagName",
        "date",
        // "tagEntryLocation",
        "entryTime",
        "tagEntryLocation",
        "existTime",
        "dispatchTime"
      ]
      //const tempTag = new Set();
      if (resultAllData && resultAllData.data && resultAllData.data.length != 0) {
        let tempTagArray2 = resultAllData.data.filter(obj => {
          return obj.allocatedColumn_ui === this.state.selectedColumn
        });
        tempTagArray.push(tempTagArray2[0].allocatedColumn_ui);
        tempRow.push(tempTagArray2[0].columnName);
      }
      this.setState({ row: tempRow, loader: false, heading: tempTagArray }, () => {
        // console.log("selected Column data", this.state.allColumnData);
        console.log('rowNew', this.state.row)
      })
    });

  }

  changeColumnView() {
    if (document.getElementById("displaytable6").style.display === "none")
      document.getElementById("displaytable6").style.display = "block";
    else
      document.getElementById("displaytable6").style.display = "none";
  }
  downloadFile = async () => {
    let token = await localStorage.getItem("token");
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let fileType = ["excel"];
    let category = ["BLE"];
    axios.get(`/asset/report/get-tracking-report-for-dynamic-columns?columnname=${this.state.selectedColumn}&fileType=${fileType}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
      headers: { "Authorization": `Bearer ${token}` },
      responseType: 'arraybuffer'
    })
      .then((response) => {
        var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        fileSaver.saveAs(blob, `ColumnNameWiseReport.xls`);
      });
  }

  downloadPDF = async () => {
    let token = await localStorage.getItem("token");
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let fileType = ["pdf"];
    let category = ["BLE"];
    axios.get(`/asset/report/get-tracking-report-for-dynamic-columns?columnname=${this.state.selectedColumn}&fileType=${fileType}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
      headers: { "Authorization": `Bearer ${token}` },
      responseType: 'arraybuffer'
    })
      .then((response) => {
        var blob = new Blob([response.data], { type: 'application/pdf' });
        fileSaver.saveAs(blob, `ColumnNameWiseReportPDF.pdf`);
      });
  }

  render() {
    const { tableColumnData, heading, row } = this.state
    const { data } = this.state
    return (
      <>

        <div className="tab-item-wrapper">
          <h5>Parameters Wise Report</h5>
          <br />
          <div className="report_form">
            <div>
              <label>SELECT PARAMETER</label>
              <Select options={this.state.columnList} name="columnName" onChange={this.changeColumn} placeholder="Select Parameter Name" />
            </div>
          </div>
          <br />
          <button onClick={this.downloadFile}>EXCEL</button>
          <button onClick={this.downloadPDF}>PDF</button>
          <button onClick={this.changeColumnView}>VIEW</button>
        </div>
        <div id="displaytable6" style={{ display: 'none' }} >
          <br />
          <div style={{ overflowX: "auto" }} className="table-responsive-sm">
            <table className=" table table-hover table-bordered table-striped text-center">
              <tr className="tablerow">
                {heading.map((obj) => (
                  <th>
                    {obj}
                  </th>
                ))}
              </tr>
              <tbody>
                {tableColumnData.map((obj, key) => (
                  <tr key={key}>

                    <td>
                      {this.state.offset + key + 1}
                    </td>
                    {row.map((obj1) => (
                      <td>
                        {obj1 === "date" ? moment(obj[obj1]).format('DD/MM/YYYY') :
                          obj1 === "entryTime" ? moment.tz(obj[obj1], 'HH:mm:ss').format('HH:mm:ss') :
                            obj1 === "existTime" ? obj[obj1] === "N/A" ? obj[obj1] : moment.tz(obj[obj1], 'HH:mm:ss').format('HH:mm:ss') :
                              // {obj.entryTime !== null ? moment.tz(obj.entryTime ,"Asia/Kolkata").format("hh:mm:ss A") : '-'}
                              obj[obj1]}
                      </td>
                    ))}
                  </tr>
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
        </div>
      </>
    )
  }
}