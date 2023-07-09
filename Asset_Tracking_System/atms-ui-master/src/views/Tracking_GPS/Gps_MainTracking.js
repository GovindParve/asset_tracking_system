import React, { Component } from 'react'
import "./Gps_mainTracking.css";
import Gps_SuperAdminTracking from "./Gps_SuperAdminTracking";
import Gps_AdminTracking from "./Gps_AdminTracking";
import Gps_UserTracking from "./Gps_UserTracking";
import Gps_OrganizationTrack from "./Gps_OrganizationTrack";
import swal from "sweetalert";

export default class Gps_MainTracking extends Component {
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
         {this.state.Role === "user" ? (
          <Gps_UserTracking />
        ) : this.state.Role === "admin" ? (
          <Gps_AdminTracking />
        ) : this.state.Role === "organization" ? (
            <Gps_OrganizationTrack/>
        ) : (
          <Gps_SuperAdminTracking />
        )}
      </>
    )
  }
}


