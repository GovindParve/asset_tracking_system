import React, { Component } from "react";
import { getPayloadData } from "../../Service/getPayloadData";
import { listAmrID } from "../../Service/listAmrID";
import { deleteBatch } from "../../Service/deleteBatch";
import { getPayloadAmrId } from "../../Service/getPayloadAmrId";
import Select from "react-select";
import axios from "../../utils/axiosInstance";
import fileSaver from "file-saver";
import "./Payloads.css";
// import {data as dummyData} from "./payloadData"

export default class Payloads extends Component {
  state = {
    allData: [],

    amrList: [],
    checkedStatus: [],
    checkedValue: [],
    selectedDevice: "",
    Role: "",
    loader: false,
  };

  async componentDidMount() {
    try {
      this.setState({ loader: true });
      let result = await getPayloadData();
      console.log("result", result);
      let resultArmId = await listAmrID();
      console.log("amrid", resultArmId.data);
      var setRole = await localStorage.getItem("role");
      let tempArray = [];
      if (resultArmId && resultArmId.data && resultArmId.data.length != 0) {
        resultArmId.data.map((obj) => {
          let tempObj = {
            value: obj,
            label: obj,
          };
          tempArray.push(tempObj);
        });
      }
      // let temp=[{}]
      this.setState(
        {
          allData: result && result.data,
          amrList: tempArray,
          Role: setRole,
          loader: false,
        },
        () => {
          console.log("allData", this.state.allData);
          let temp = [];
          this.state.allData.map((obj, key) => {
            const objTemp = { [key]: false };
            temp.push(objTemp);
          });
          this.setState({ checkedStatus: temp });
        }
      );
    } catch (error) {
      console.log(error);
      this.setState({ loader: false });
    }
  }

  changeDevice = async (selectedOptions) => {
    console.log(selectedOptions.value);
    if (selectedOptions.value == "Select device") {
      let result = await getPayloadData();
      this.setState({ allData: result && result.data }, () => {
        let temp = [];
        this.state.allData.map((obj, key) => {
          const objTemp = { [key]: false };
          temp.push(objTemp);
        });
        this.setState({ checkedStatus: temp });
      });
    } else {
      this.setState({ selectedDevice: selectedOptions.value }, async () => {
        let result = await getPayloadAmrId(this.state.selectedDevice);
        this.setState({ allData: result && result.data }, () => {
          console.log("alldata", this.state.allData);
          let temp = [];
          this.state.allData.map((obj, key) => {
            const objTemp = { [key]: false };
            temp.push(objTemp);
          });
          this.setState({ checkedStatus: temp });
        });
      });
    }
  };
  downloadFile = async () => {
    let token = await localStorage.getItem("token");
    axios
      .get("/iotmeter/payload/get-payload_report_for_admin_export_download", {
        headers: { Authorization: `Bearer ${token}` },
        responseType: "arraybuffer",
      })
      .then((response) => {
        var blob = new Blob([response.data], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });
        fileSaver.saveAs(blob, "ReportFile.xls");
      });
  };
  changeStatus = (value, e) => {
    console.log(value, e.target.value);
    let checkedValueTemp = this.state.checkedValue;

    if (e.target.checked) {
      checkedValueTemp.push(parseInt(e.target.value));
      this.setState({ checkedValue: checkedValueTemp });
      // console.log("checkedValue1",this.state.checkedValue);
    } else {
      const filteredItems = checkedValueTemp.filter(
        (item) => item !== parseInt(e.target.value)
      );
      console.log("filteredItems", filteredItems);
      checkedValueTemp = filteredItems;
      this.setState({ checkedValue: checkedValueTemp }, () => {
        // console.log("checkedValue",this.state.checkedValue);
      });
    }

    let temp = this.state.checkedStatus;

    temp[value][`${value}`] = !temp[value][`${value}`];
  };

  allCheckUncheked = (e) => {
    let temp = [];
    let checkedArray = [];

    if (e.target.checked) {
      this.state.allData.map((obj, key) => {
        const objTemp = { [key]: true };
        temp.push(objTemp);
        checkedArray.push(parseInt(obj.pkPayloadDetails));
      });

      this.setState({ checkedStatus: temp, checkedValue: checkedArray });
    } else {
      let temp = [];
      this.state.allData.map((obj, key) => {
        const objTemp = { [key]: false };
        temp.push(objTemp);
      });
      this.setState({ checkedStatus: temp, checkedValue: [] });
    }
  };

  deleteBatch = async () => {
    try {
      let result = await deleteBatch(this.state.checkedValue);

      if (result.status === 200) {
        alert("paylaod deleted");
        window.location.reload();
      } else {
        alert("something went wrong please check your connection");
      }
    } catch (error) {
      console.log(error);
    }
  };

  render() {
    const { allData, checkedStatus, loader } = this.state;

    return (
      <>
        {loader ? (
          <div class="loader"></div>
        ) : (
          <div>
            <label>
              <strong>SELECT AMR_ID</strong>
            </label>
            <Select
              options={this.state.amrList}
              className="payload__select"
              onChange={this.changeDevice}
            />
            <div className="payload__btn__wrapper">
              <div>
                {/* <select onChange={this.changeDevice}>
                            <option>Select device</option>
                            {this.state && this.state.amrList && this.state.amrList.map((obj, key) => (
                                <option key={key} >{obj}</option>
                            ))}
                        </select> */}
                &nbsp;&nbsp;&nbsp;
                {this.state.checkedValue.length === 0 ? (
                  ""
                ) : (
                  <button className="btn btn-danger" onClick={this.deleteBatch}>
                    Delete
                  </button>
                )}
              </div>
              <div>
                {/* <button className="btn btn-primary" >Filter</button>&nbsp;&nbsp; */}
                {/* <button className="btn btn-success" onClick={this.downloadFile}>Export</button> */}
              </div>
            </div>
            <br />
            <div style={{ overflowX: "auto" }} className="table-responsive-sm">
              <table className=" table table-hover table-bordered table-striped text-center">
                <tr>
                  {this.state.Role === "admin" ? (
                    <>
                      <th style={{ display: "none" }}>
                        <input
                          type="checkbox"
                          onChange={this.allCheckUncheked}
                        />
                      </th>{" "}
                    </>
                  ) : this.state.Role === "user" ? (
                    <>
                      <th style={{ display: "none" }}>
                        <input
                          type="checkbox"
                          onChange={this.allCheckUncheked}
                        />
                      </th>{" "}
                    </>
                  ) : (
                    <>
                      <th>
                        <input
                          type="checkbox"
                          onChange={this.allCheckUncheked}
                        />
                      </th>
                    </>
                  )}

                  <th>S NO</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>AMR Id</th>
                  <th>Volume</th>
                  <th>VBat</th>
                  <th>Sig Stg</th>

                  <th>Prob Tamp</th>
                  <th>Mag Tamp</th>
                  <th>Axis Tamp</th>
                  <th>log t</th>
                  <th>Lat t</th>
                </tr>
                <tbody>
                  {allData.map((obj, key) => (
                    <>
                      <tr
                        key={key}
                        className={
                          obj.prob_tamp || obj.mag_tamp || obj.axis_tamp === 1
                            ? "colorRed"
                            : "colorBlue"
                        }
                      >
                        {this.state.Role === "admin" ? (
                          <>
                            {" "}
                            <td style={{ display: "none" }}>
                              <input
                                type="checkbox"
                                value={obj.pkPayloadDetails}
                                onChange={(e) => this.changeStatus(key, e)}
                                checked={
                                  this.state &&
                                  this.state.checkedStatus[key] &&
                                  this.state.checkedStatus[key][`${key}`]
                                }
                              />
                            </td>
                          </>
                        ) : this.state.Role === "user" ? (
                          <>
                            {" "}
                            <td style={{ display: "none" }}>
                              <input
                                type="checkbox"
                                value={obj.pkPayloadDetails}
                                onChange={(e) => this.changeStatus(key, e)}
                                checked={
                                  this.state &&
                                  this.state.checkedStatus[key] &&
                                  this.state.checkedStatus[key][`${key}`]
                                }
                              />
                            </td>
                          </>
                        ) : (
                          <>
                            {" "}
                            <td>
                              <input
                                type="checkbox"
                                value={obj.pkPayloadDetails}
                                onChange={(e) => this.changeStatus(key, e)}
                                checked={
                                  this.state &&
                                  this.state.checkedStatus[key] &&
                                  this.state.checkedStatus[key][`${key}`]
                                }
                              />
                            </td>
                          </>
                        )}

                        <td>{key + 1}</td>
                        <td>{obj.date}</td>
                        <td>{obj.time}</td>
                        <td>{obj.amrid}</td>
                        <td>{obj.vol}</td>
                        <td>{obj.vbat}</td>
                        <td>{obj.sig_stg}</td>

                        <td>{obj.prob_tamp}</td>
                        <td>{obj.mag_tamp}</td>
                        <td>{obj.axis_tamp}</td>
                        <td>{obj.lat_t}</td>
                        <td>{obj.log_t}</td>
                      </tr>
                    </>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}
      </>
    );
  }
}
