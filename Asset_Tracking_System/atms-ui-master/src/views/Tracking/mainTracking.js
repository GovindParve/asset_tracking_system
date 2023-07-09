import React, { Component } from 'react'
import "./mainTracking.css";
import SuperAdminTrackingList from "./SuperAdminTrackingList";
import AdminTrackingList from "./AdminTrackingList";
import UserTrackingList from "./UserTrackingList";
import OrganizationTrackList from "./OrganizationTrackList";
import swal from "sweetalert";

export default class mainTracking extends Component {
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
      selectGateway: "",
      checkedValue: [],
      selectDuration:"",
      selectTime:"",
      selectedDevice: "",
      Role: "",
      loader: false,
      battryPercentage: ""
    };
  }
  async componentDidMount() {
    let role = localStorage.getItem("role");
    this.setState({ Role: role });
  }

  render() {
    return (
      <>
         {(this.state.Role === "user" || this.state.Role === 'empuser') ? (
          <UserTrackingList />
        ) : this.state.Role === "admin" ? (
          <AdminTrackingList />
        ) : this.state.Role === "organization" ? (
            <OrganizationTrackList />
        ) : (
          <SuperAdminTrackingList />
        )}
      </>
    )
  }
}


