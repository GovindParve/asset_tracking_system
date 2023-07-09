import React, { Component } from "react";
import "./UserList.css";
import { getEmpUser } from "../../Service/getEmpUser";

import ReactPaginate from "react-paginate";
import { deleteEmp } from "../../Service/deleteEmp";
import axios from "../../utils/axiosInstance";
import fileSaver from "file-saver";
import swal from "sweetalert";
import Select from "react-select";
import PropTypes from "prop-types";
import Swal from "sweetalert2";
import Axios from "../../utils/axiosInstance";

export default class EmpUserList extends Component {
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
      Role: "",
      loader: false,
      checkedStatus: [],
      checkedValue: [],
      selectedUser: "",
      allUser: [
        {
          label: "User List",
          value: "User List",
        },
        {
          label: "Emp User List",
          value: "Emp User List",
        },
      ],
      allStatus: [
        { value: "Active", label: "Active" },
        { value: "Deactive", label: "Deactive" },
      ],
    };

    this.handlePageClick = this.handlePageClick.bind(this);
  }

  handlePageClick = async (e) => {
    const selectedPage = e.selected;
    let result = await getEmpUser(selectedPage);
    console.log("Employee", result);
    if (result && result.data && result.data.length !== 0) {
      const data = result.data.content;
      this.setState(
        {
          currentPage: selectedPage,
          pageCount: result.data.totalPages,
          tableData: data,
          offset: result.data.pageable.offset,
          loader: false,
        },
        () => {
          let temp = [];
          this.state.tableData.map((obj, key) => {
            const objTemp = { [key]: false };
            temp.push(objTemp);
          });
          this.setState({ checkedStatus: temp, checkedValue: [] });
        }
      );
    }
  };

  async componentDidMount() {
    try {
      this.setState({ loader: true, Role: localStorage.getItem("role") });
      this.getData();
    } catch (error) {
      console.log(error);
      this.setState({ loader: false });
    }
  }

  async getData() {
    let result = await getEmpUser(this.state.currentPage);
    console.log("Emp User List", result);
    if (result && result.data && result.data.length !== 0) {
      const data = result.data.content;
      this.setState(
        {
          pageCount: result.data.totalPages,
          tableData: data,
          loader: false,
        },
        () => {
          let temp = [];
          this.state.tableData.map((obj, key) => {
            const objTemp = { [key]: false };
            temp.push(objTemp);
          });
          this.setState({ checkedStatus: temp });
        }
      );
    } else {
      this.setState({
        pageCount: {},
        tableData: [],
      });
      swal("Sorry", "Data is not present", "warning");
    }
  }
  redirectToCreateUser = () => {
    this.props.history.push("/users");
  };
  redirectToCreateEmpUser = () => {
    this.props.history.push("/empuser-create");
  };
  redirectToCreateAdminEmpUser = () => {
    this.props.history.push("/admin-empuser-create");
  };
  redirectToUploadExcel = () => {
    this.props.history.push("/upload-empuser-excel");
  };
  redirectToUpdate = (id) => {
    this.props.history.push({
      pathname: `/update-emp/${id}`,
      state: { empId: id },
    });
  };
  changeStatus = (value, e) => {
    console.log("ChangeStatus", e.target.value);
    let checkedValueTemp = this.state.checkedValue;

    if (e.target.checked) {
      checkedValueTemp.push(parseInt(e.target.value));
      this.setState({ checkedValue: checkedValueTemp });
    } else {
      const filteredItems = checkedValueTemp.filter(
        (item) => item !== parseInt(e.target.value)
      );
      console.log("filteredItems", filteredItems);
      checkedValueTemp = filteredItems;
      this.setState({ checkedValue: checkedValueTemp });
    }

    let temp = this.state.checkedStatus;

    temp[value][`${value}`] = !temp[value][`${value}`];
  };
  allCheckUncheked = (e) => {
    let temp = [];
    let checkedArray = [];

    if (e.target.checked) {
      console.log("value", e.target.checked);
      this.state.tableData.map((obj, key) => {
        const objTemp = { [key]: true };
        temp.push(objTemp);
        checkedArray.push(parseInt(obj.pkDeviceDetails));
      });

      this.setState({ checkedStatus: temp, checkedValue: checkedArray });
    } else {
      let temp = [];
      this.state.tableData.map((obj, key) => {
        const objTemp = { [key]: false };
        temp.push(objTemp);
      });
      this.setState({ checkedStatus: temp, checkedValue: [] });
    }
  };
  deleteEmp = async () => {
    // console.log("delete", this.state.checkedValue);
    // try {
    //   let result = await deleteEmp(this.state.checkedValue);
    //   if (result.status === 200) {
    //     alert("User deleted");
    //     window.location.reload();
    //   } else {
    //     alert("something went wrong please check your connection");
    //   }
    // } catch (error) {
    //   console.log(error);
    // }
    console.log("delete", this.state.checkedValue);
    Swal.fire({
      title: "Are you sure want to Delete?",
      // text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#c01664",
      confirmButtonText: "Yes, Delete it!",
    })
      .then(async (resultResponse) => {
        if (resultResponse.isConfirmed) {
          let token = localStorage.getItem("token");
          return Axios.post(
            "/asset/tracking/BulkDeleteEmployee",
            this.state.checkedValue,
            { headers: { Authorization: `Bearer ${token}` } }
          ).then((resultResponse) => {
            console.log("Delete User", resultResponse);
            Swal.fire({
              position: "top-center",
              icon: "success",
              title: "User Is Deleted...!",
              showConfirmButton: false,
              timer: 3000,
            });
            window.location.reload();
            return Promise.resolve();
          });
        }
      })
      .catch((resultResponse) => {
        swal("Failed", "Somthing went wrong", "error");
        console.log("Delete User ", resultResponse);
      });
  };
  downloadUserExcel = async () => {
    let token = await localStorage.getItem("token");
    let fkUserId = await localStorage.getItem("fkUserId");
    let role = await localStorage.getItem("role");
    let fileType = ["excel"];
    let category = ["BLE"];
    axios
      .get(
        `/user/report/get-Employee-User-report-export-excel-or-pdf-download?fileType=${fileType}&fkUserId=${fkUserId}&role=${role}&category=${category}`,
        {
          headers: { Authorization: `Bearer ${token}` },
          responseType: "arraybuffer",
        }
      )
      .then((response) => {
        var blob = new Blob([response.data], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });
        fileSaver.saveAs(blob, `EmployeeUserDetailsReportExcel.xls`);
      });
  };
  downloadUserPdf = async () => {
    let token = await localStorage.getItem("token");
    let fkUserId = await localStorage.getItem("fkUserId");
    let role = await localStorage.getItem("role");
    let fileType = ["pdf"];
    let category = ["BLE"];
    axios
      .get(
        `/user/report/get-Employee-User-report-export-excel-or-pdf-download?fileType=${fileType}&fkUserId=${fkUserId}&role=${role}&category=${category}`,
        {
          headers: { Authorization: `Bearer ${token}` },
          responseType: "arraybuffer",
        }
      )
      .then((response) => {
        var blob = new Blob([response.data], { type: "application/pdf" });
        fileSaver.saveAs(blob, `EmployeeUserDetailsReportPDF.pdf`);
      });
  };

  changeUser = (e) => {
    this.setState({ selectedUser: e.value });
    var user = e.value;
    if (user === "Emp User List") {
      this.props.history.push("/emp-users");
    } else if (user === "User List") {
      this.props.history.push("/users");
    }
  };
  changeActDeactive = async (selectedStatus, pkuserid, userrole) => {
    console.log(selectedStatus.value, pkuserid, userrole);
    Swal.fire({
      title: "Are you sure want to deactivate or activate?",
      // text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#c01664",
      confirmButtonText: "Yes, Status Update it!",
    })
      .then((resultResponse) => {
        if (resultResponse.isConfirmed) {
          this.setState({ selectStatus: selectedStatus.value }, async () => {
            console.log("this.state.selectStatus", this.state.selectStatus);
            let token = localStorage.getItem("token");
            return await Axios.post(
              `/users/enable-status?status=${this.state.selectStatus}&pkuserid=${pkuserid}&userrole=${userrole}`,
              "",
              { headers: { Authorization: `Bearer ${token}` } }
            ).then((resultResponse) => {
              console.log("User Status", resultResponse);
              Swal.fire({
                position: "top-center",
                icon: "success",
                title: "Status Updated...!",
                showConfirmButton: false,
                timer: 3000,
              });
              window.location.reload()
            });
          });
          return Promise.resolve();
        }
      })
      .catch((resultResponse) => {
        swal("Failed", "Somthing went wrong", "error");
        console.log("User Status", resultResponse);
      });
  };

  render() {
    const { tableData } = this.state;
    return (
      <>
        <div className="userList__wrapper">
          {this.state.Role === "admin" ? (
            <div className="userList__btn__wrapper">
              {/* <div className="allUser">
                <Select
                  options={this.state.allUser}
                  value={
                    this.state.allUser
                      ? this.state.allUser.find(
                          (option) => option.value === this.state.selectedUser
                        )
                      : ""
                  }
                  onChange={this.changeUser}
                  placeholder="Emp User List"
                />
              </div>
              &nbsp;&nbsp; */}
              <button className="btn btn-primary" onClick={this.redirectToCreateUser}>
                <i className="fa fa-arrow-left"></i> User List</button>
              &nbsp;&nbsp;

              <button
                className="btn btn-primary"
                onClick={this.redirectToCreateAdminEmpUser}
              >
                Create Emp User
              </button>
              &nbsp;&nbsp;
              <a
                href="EmpUser_Upload_Template.xlsx"
                download="EmpUser_Upload_Template.xlsx"
              >
                <i className="fa fa-download fa-lg "></i>
                <span class="tooltip-d-text">Download Template</span>
              </a>
              <button
                className="btn btn-primary"
                onClick={this.redirectToUploadExcel}
              >
                Upload Excel
              </button>
              &nbsp;&nbsp;
              <button
                className="btn btn-primary"
                onClick={this.downloadUserExcel}
              >
                Excel
              </button>
              &nbsp;&nbsp;
              <button
                className="btn btn-primary"
                onClick={this.downloadUserPdf}
              >
                Pdf
              </button>
              &nbsp;&nbsp;
            </div>
          ) : this.state.Role === "organization" ? (
            <div className="userList__btn__wrapper">
              {/* <div className="allUser">
                <Select
                  options={this.state.allUser}
                  value={
                    this.state.allUser
                      ? this.state.allUser.find(
                          (option) => option.value === this.state.optionUser
                        )
                      : ""
                  }
                  onChange={this.changeUser}
                  placeholder="Emp User List"
                />
              </div>
              &nbsp;&nbsp; */}
              <button
                className="btn btn-primary"
                onClick={this.redirectToCreateUser}
              >
                <i className="fa fa-arrow-left"></i> User List
              </button>
              &nbsp;&nbsp;
              <button
                className="btn btn-primary"
                onClick={this.redirectToCreateEmpUser}
              >
                Create Emp User
              </button>
              &nbsp;&nbsp;
              <a
                href="EmpUser_Upload_Template.xlsx"
                download="EmpUser_Upload_Template.xlsx"
              >
                <i className="fa fa-download fa-lg "></i>
                <span class="tooltip-d-text">Download Template</span>
              </a>
              <button
                className="btn btn-primary"
                onClick={this.redirectToUploadExcel}
              >
                Upload Excel
              </button>
              &nbsp;&nbsp;
              <button
                className="btn btn-primary"
                onClick={this.downloadUserExcel}
              >
                Excel
              </button>
              &nbsp;&nbsp;
              <button
                className="btn btn-primary"
                onClick={this.downloadUserPdf}
              >
                Pdf
              </button>
              &nbsp;&nbsp;
            </div>
          ) : (
            <div className="userList__btn__wrapper">
              <button
                className="btn btn-primary"
                onClick={this.redirectToCreateUser}
              >
                Create User
              </button>
              &nbsp;&nbsp;
              <a
                href="EmpUser_Upload_Template.xlsx"
                download="EmpUser_Upload_Template.xlsx"
              >
                <i className="fa fa-download fa-lg "></i>
                <span class="tooltip-d-text">Download Template</span>
              </a>
              <button
                className="btn btn-primary"
                onClick={this.redirectToUploadExcel}
              >
                Upload Excel
              </button>
              &nbsp;&nbsp;
              <button
                className="btn btn-primary"
                onClick={this.downloadUserExcel}
              >
                Excel
              </button>
              &nbsp;&nbsp;
              <button
                className="btn btn-primary"
                onClick={this.downloadUserPdf}
              >
                Pdf
              </button>
              &nbsp;&nbsp;
            </div>
          )}

          <div className="payload__btn__wrapper">
            <div>
              &nbsp;&nbsp;&nbsp;
              {this.state.checkedValue.length === 0 ? (
                ""
              ) : (
                <button className="btn btn-danger" onClick={this.deleteEmp}>
                  Delete
                </button>
              )}
            </div>
          </div>
          <br />
          <div className=" table-responsive">
            <table className="table table-hover table-bordered table-striped text-center">
              <tr className="tablerow">
                {this.state.Role === "admin" ||
                  this.state.Role === "organization" ? (
                  <>
                    <th style={{ display: "none" }}>
                      <input type="checkbox" onChange={this.allCheckUncheked} />
                    </th>
                    <th style={{ display: "none" }}>EDIT</th>{" "}
                  </>
                ) : this.state.Role === "user" ? (
                  <>
                    {" "}
                    <th style={{ display: "none" }}>
                      <input type="checkbox" onChange={this.allCheckUncheked} />
                    </th>
                    <th style={{ display: "none" }}>EDIT</th>{" "}
                  </>
                ) : (
                  <>
                    <th>
                      <input type="checkbox" onChange={this.allCheckUncheked} />
                    </th>
                  </>
                )}
                {/* <th>
                  <input type="checkbox" onChange={this.allCheckUncheked} />
                </th> */}
                <th>ID</th>
                <th>First_Name</th>
                <th>Last_Name</th>
                <th>Email_Id</th>
                <th>Company_Name</th>
                <th>Address</th>
                <th>Phone</th>
                <th>User_Role</th>
                <th>Category</th>
                <th>Created By</th>
                <th>Status</th>
                <th>Edit</th>
              </tr>
              <tbody>
                {tableData.map((obj, key) => (
                  <tr key={key} className="Device__table__col">
                    {/* <th>
                      <div key={key}>
                        <input
                          type="checkbox"
                          value={obj.pkuserId}
                          onChange={(e) => this.changeStatus(key, e)}
                          checked={
                            this.state &&
                            this.state.checkedStatus[key] &&
                            this.state.checkedStatus[key][`${key}`]
                          }
                        />
                      </div>
                    </th> */}
                    {this.state.Role === "admin" ||
                      this.state.Role === "organization" ? (
                      <>
                        <th style={{ display: "none" }}>
                          <div key={key}>
                            <input
                              type="checkbox"
                              value={obj.pkuserId}
                              onChange={(e) => this.changeStatus(key, e)}
                              checked={
                                this.state &&
                                this.state.checkedStatus[key] &&
                                this.state.checkedStatus[key][`${key}`]
                              }
                            />
                          </div>
                        </th>
                      </>
                    ) : (
                      <>
                        <th>
                          <div key={key}>
                            <input
                              type="checkbox"
                              value={obj.pkuserId}
                              onChange={(e) => this.changeStatus(key, e)}
                              checked={
                                this.state &&
                                this.state.checkedStatus[key] &&
                                this.state.checkedStatus[key][`${key}`]
                              }
                            />
                          </div>
                        </th>
                      </>
                    )}
                    <td>{this.state.offset + key + 1}</td>
                    <td>{obj.firstName}</td>
                    <td>{obj.lastName}</td>
                    <td>{obj.emailId}</td>
                    <td>{obj.companyName}</td>
                    <td>{obj.address}</td>
                    <td>{obj.phoneNumber}</td>
                    <td>{obj.role}</td>
                    <td>{obj.category}</td>
                    <td>{obj.createdby
                      === "" ? 'N/A' : obj.createdby
                        === "0" ? 'N/A' : obj.createdby
                    }</td>
                    <td>
                      <Select
                        className="status_select"
                        onChange={async (e) => {
                          this.changeActDeactive(e, obj.pkuserId, obj.role);
                        }}
                        options={this.state.allStatus}
                        value={
                          this.state.allStatus
                            ? this.state.allStatus.find(
                              (option) => option.value === obj.status
                            )
                            : ""
                        }
                        placeholder="Active"
                      />
                    </td>
                    <td onClick={() => this.redirectToUpdate(obj.pkuserId)}>
                      <button
                        className="btn btn-info"
                        style={{ display: "none" }}
                      >
                        {" "}
                      </button>
                      <i className="fa fa-edit fa-lg "></i>
                      <span class="tooltip-text">Edit</span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <br />
          <br />
          <ReactPaginate
            // previousLabel={"Prev"}
            // nextLabel={"Next"}
            previousLabel={<i class="fa fa-angle-left"></i>}
            nextLabel={<i class="fa fa-angle-right"></i>}
            breakLabel={"..."}
            breakClassName={"break-me"}
            pageCount={this.state.pageCount}
            marginPagesDisplayed={2}
            pageRangeDisplayed={3}
            onPageChange={this.handlePageClick}
            containerClassName={"pagination"}
            subContainerClassName={"pages pagination"}
            activeClassName={"active"}
          />
          <br />
          <br />
          <br />
          <br />
        </div>
      </>
    );
  }
}
