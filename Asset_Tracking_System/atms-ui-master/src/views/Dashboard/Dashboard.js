import React, { PureComponent } from 'react';
import './Dashboard.css'
import DashboardSuperAdmin from "./DashboardSuperAdmin";
import DashboardAdmin from "./DashboardAdmin";
import DashboardUser from "./DashboardUser";
import DashboardEmpUser from "./DashboardEmpUser";
import DashboardOrganization from "./DashboardOrganization";

class Dashboard extends PureComponent {

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
          <DashboardAdmin />
        ) : this.state.Role === "user" ? (
          <DashboardUser />
          ) : this.state.Role === "empuser" ? (
            <DashboardEmpUser />
          ) : this.state.Role === "organization" ? (
            <DashboardOrganization />
        ) : (
          <DashboardSuperAdmin />
        )}
      </>
    );
  }
}
export default Dashboard;
