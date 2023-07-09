import React, { PureComponent } from 'react';
import './Dashboard_GPS.css'
import Highcharts from 'highcharts'
import { Link, NavLink } from 'react-router-dom';
import { listGateway } from "../../Service/listGateway"
import { getGPSDashboardAllCount } from "../../Service/getGPSDashboardAllCount"
import { listAssetTag } from "../../Service/listAssetTag"
import { allGatewayWiseGraphData } from "../../Service/allGatewayWiseGraphData"
import { getSingleGraphData } from "../../Service/getSingleGraphData"
import { getGatewayWiseData } from '../../Service/getGatewayWiseData'
import { Card, CardBody, CardTitle, Container, Row, Col } from "reactstrap";
import Map_GPS from './Map_GPS';

class Gps_DashboardOrganization extends PureComponent {

  state = {
    gateway_count: 0,
    workingcount: 0,
    non_workcount: 0,
    workingGatewaycount: 0,
    non_workGatewaycount: 0,
    admin_count: 0,
    guest_count: 0,
    device_count: 0,
    totalEmpUser: 0,
    gatewayList: [],
    selectDuration: "",
    selectedGateway: "",
    selectedAssetTag: "",
    nonWorkingTag: '',
    nonWorkingGateway: '',
    data: [],
    customDate: false,
    startDate: "",
    endDate: "",
    hours:"",
    newageGraphdata: [],
    workTag: [],
    allData: [],
    amrList: [],
    checkedStatus: [],
    checkedValue: [],
    selectedDevice: ""
  }

  async componentDidMount() {
    let resultAllCount = await getGPSDashboardAllCount();
    console.log('DashboardAllCount', resultAllCount)

    let data = await allGatewayWiseGraphData();
    console.log('gatewayGraphData', data.data)
    this.setState({newageGraphdata: data.data}, () => {
      console.log('newageGraphdata', this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0])
    })

    this.setState({
      gateway_count: resultAllCount && resultAllCount.data.totalGateways,
      admin_count: resultAllCount && resultAllCount.data.totalAdmin,
      guest_count: resultAllCount && resultAllCount.data.totalUser,
      device_count: resultAllCount && resultAllCount.data.totalTags,
      totalEmpUser: resultAllCount && resultAllCount.data.totalEmpUser,
      workingcount: resultAllCount && resultAllCount.data && resultAllCount.data.workingTags,
      non_workcount: resultAllCount && resultAllCount.data && resultAllCount.data.nonWorkingTags,
      workingGatewaycount: resultAllCount && resultAllCount.data && resultAllCount.data.workingGateways,
      non_workGatewaycount: resultAllCount && resultAllCount.data && resultAllCount.data.nonWorkingGateways,
      // assetTagList: tempTagArray,
      // selectedAssetTag: assetTagList && assetTagList.data[0]
    
    // () => {
    //   console.log('Tag', this.state.assetTagList)
    })

    let result = await getGatewayWiseData();
    this.setState({ allData: result && result.data }, () => {
      console.log("alldate", this.state.allData)
    })
  }

  changeGateway = async (selectedOptions) => {
    console.log(selectedOptions.value)
    this.setState({ selectedDevice: selectedOptions.value }, async () => {
      let result = await getSingleGraphData(this.state.selectedDevice)
      console.log('result', result.data)
      this.setState({ newageGraphdata: result.data }, () => {
        console.log('newageGraphdata', this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0])
      })

    })
  }
  
  clickCard = async () => {
    //console.log("selectedTag", tag)
    this.props.history.push({
      pathname: `/working-tag`,
    });
  }

  onClickGateway = async () =>{
    let gatewayList = await listGateway();
    console.log('gatewayList', gatewayList)
    let tempArray = []
    if (gatewayList && gatewayList.data && gatewayList.data.length != 0) {
      gatewayList.data.map((obj) => {
        let tempObj = {
         // id: obj.gatewayId,
          value: obj,
          label: obj
        }
        tempArray.push(tempObj)
      })
    }
    this.setState({  gatewayList: tempArray,
      selectedGateway: gatewayList && gatewayList.data[0], }, () => {
      console.log('Gateway List', this.state.gatewayList)
    })
  }
  //loading = () => <div className="animated fadeIn pt-1 text-center">Loading...</div>
  render() {
    const { allData} = this.state
    const { data } = this.state
    var Role = localStorage.getItem('role');
    return (
      <div className="animated fadeIn">
        <div className="header bg-gradient-info pb-8 pt-5 pt-md-8">
          <Container fluid>
            <div className="header-body">
              {/* Card stats */}
              <Row>
                <Col lg="6" xl="4">
                  <Card className="cards mb-4 mb-xl-0 ">
                    <CardBody>
                      <Link to={{ pathname: "/dashboard_Gps_adminlist" }}>
                        <Row>
                          <div className="col">
                            <CardTitle
                              tag="h5"
                              className="text-uppercase text-muted mb-0">
                              Total Admin
                            </CardTitle>
                            <span className="h2 font-weight-bold mb-0">{this.state.admin_count}</span>
                          </div>
                          <Col className="col-auto">
                            <div className="icon icon-shape bg-warning text-white rounded-circle shadow">
                              <i className="icon-people icons" />
                            </div>
                          </Col>
                        </Row>
                      </Link>                   
                    </CardBody>                   
                  </Card>
                </Col>
                <Col lg="6" xl="4" >
                  <Card className="cards mb-4 mb-xl-0">
                    <CardBody >
                      <Link to={{ pathname: "/dashboard_Gps_userlist" }}>
                        <Row>
                          <div className="col">
                            <CardTitle
                              tag="h5"
                              className="text-uppercase text-muted mb-0">
                              Total Specific Users
                            </CardTitle>
                            <span className="h2 font-weight-bold mb-0">{this.state.guest_count}</span>
                          </div>
                          <Col className="col-auto">
                            <div className="icon icon-shape bg-yellow text-white rounded-circle shadow">
                              <i className="icon-people icons" />
                            </div>
                          </Col>
                        </Row>
                      </Link>
                    </CardBody>
                  </Card>
                </Col>
                <Col lg="6" xl="4" >
                  <Card className="card-stats mb-4 mb-xl-0">
                    <CardBody >
                    <Link to={{ pathname: "/dashboard_Gps_empuserlist" }}>
                        <Row>
                          <div className="col">
                            <CardTitle
                              tag="h5"
                              className="text-uppercase text-muted mb-0">
                              Total Emp Users
                            </CardTitle>
                            <span className="h2 font-weight-bold mb-0">
                            {this.state.totalEmpUser} 
                              </span>
                          </div>
                          <Col className="col-auto">
                            <div className="icon icon-shape bg-yellow text-white rounded-circle shadow">
                              <i className="icon-people icons" />
                            </div>
                          </Col>
                        </Row>
                      </Link>
                    </CardBody>
                  </Card>
                </Col>
                <Col lg="6" xl="4" className="mt-3">
                  <Card className="cards mb-4 mb-xl-0">
                    <CardBody>
                      <Link to={{ pathname: "/asset-gps" }}>
                        <Row>
                          <div className="col">
                            <CardTitle
                              tag="h5"
                              className="text-uppercase text-muted mb-0">
                              Total Tags
                            </CardTitle>
                            <span className="h2 font-weight-bold mb-0">
                              {this.state.device_count}
                            </span>
                          </div>
                          <Col className="col-auto">
                            <div className="icon icon-shape bg-danger text-white rounded-circle shadow">
                              <i className="icon-tag icons" />
                            </div>
                          </Col>
                        </Row>
                      </Link>
                    </CardBody>
                  </Card>
                </Col>
                <Col lg="6" xl="4" className="mt-3">
                  <Card className="cards mb-4 mb-xl-0">
                    <CardBody >
                    <Link to={{pathname: "/working-gps-tag"}}>
                      <Row>
                        <div className="col">
                          <CardTitle tag="h5" className="text-uppercase text-muted mb-0">
                            Working Tags
                          </CardTitle>
                          {/* <br /> */}
                          <span className="h3 font-weight-bold mb-0">
                            {this.state.workingcount}
                          </span>
                        </div>
                        <Col className="col-auto">
                          <div className="icon icon-shape bg-red text-white rounded-circle shadow">
                            <i className="icon-tag icons" />
                          </div>
                        </Col>
                      </Row>
                      </Link>
                    </CardBody>
                  </Card>
                </Col>
                <Col lg="6" xl="4" className="mt-3">
                  <Card className="cards mb-4 mb-xl-0">
                    <CardBody>
                    <Link to={{pathname: "/Nonworking-gps-tag"}}>
                      <Row>
                        <div className="col">
                          <CardTitle
                            tag="h5"
                            className="text-uppercase text-muted mb-0"
                          >
                            Non-Working Tags
                          </CardTitle>
                          <span className="h3 font-weight-bold mb-0">
                            {this.state.non_workcount}
                          </span>
                        </div>
                        <Col className="col-auto">
                          <div className="icon icon-shape bg-pink text-white rounded-circle shadow">
                            <i className="icon-tag icons" />
                          </div>
                        </Col>
                      </Row>
                      </Link>
                    </CardBody>
                  </Card>
                </Col>
              </Row>
            </div>
          </Container>
        </div>
        <br />
        <br />
        <br />
        <br />
        <div className='mapadd'>
         <Map_GPS />
        </div>
        <br />
      </div>
    );
  }
}
export default Gps_DashboardOrganization;
