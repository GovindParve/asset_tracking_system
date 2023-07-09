import React, { PureComponent } from 'react';
import './Dashboard_GPS.css'
import Gps_DahboardSuperadmin from "./Gps_DahboardSuperadmin";
import Gps_DashboardAdmin from "./Gps_DashboardAdmin";
import Gps_DashboardUser from "./Gps_DashboardUser";
import Gps_DashboardOrganization from "./Gps_DashboardOrganization";

class Dashboard_GPS extends PureComponent {
  state = {
    Role: "",
  };

  async componentDidMount() {
    let role = localStorage.getItem("role");
    this.setState({ Role: role });
  }

  loading = () => (
    <div className="animated fadeIn pt-1 text-center">Loading...</div>
  );
  render() {
    const { data } = this.state;
    return (
      <>
        {this.state.Role === "admin" ? (
          <Gps_DashboardAdmin />
        ) : this.state.Role === "user" ? (
          <Gps_DashboardUser />
          ) : this.state.Role === "organization" ? (
            <Gps_DashboardOrganization />
        ) : (
          <Gps_DahboardSuperadmin />
        )}
      </>
    );
  }
}
export default Dashboard_GPS;
