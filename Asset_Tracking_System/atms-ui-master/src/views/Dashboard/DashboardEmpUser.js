import React, { PureComponent } from 'react';
import './DashboardUser.css'
import Highcharts from 'highcharts'
import { DemoHighChartData } from './DemoHighChartData';
import HighchartsReact from 'highcharts-react-official'
import getoptions from './highChartData'
import getGpsGraph from './getGpsGraph'
import Map from './Map'
import { getGatewayCount } from "../../Service/getGatewayCount"
import { getAdminList } from "../../Service/getAdminList"
import { getGuestCount } from "../../Service/getGuestCount"
import { getDeviceCount } from "../../Service/getDeviceCount"
import { listGateway } from "../../Service/listGateway"
import { listAssetTag } from "../../Service/listAssetTag"
import { getHighChartData } from "../../Service/getHighChartData"
import { getBetweenData } from "../../Service/getBetweenData"
import { allGatewayWiseGraphData } from "../../Service/allGatewayWiseGraphData"
import { getSingleGraphData } from "../../Service/getSingleGraphData"
import { getGatewayWiseData } from '../../Service/getGatewayWiseData'
import { getWorkingOrNonWorkingTag } from '../../Service/getWorkingOrNonWorkingTag'
import { getNonWorkingTag } from '../../Service/getNonWorkingTag'
import { getWorkingNonWorkingGateway } from '../../Service/getWorkingNonWorkingGateway'
import { getDashboardAllCount } from "../../Service/getDashboardAllCount"
import { getNonWorkingGateway } from '../../Service/getNonWorkingGateway'
import { Card, CardBody, CardTitle, Container, Row, Col } from "reactstrap";
import { Link, NavLink } from 'react-router-dom';
import Draggable, { ControlPosition } from 'react-draggable';

//import awsconfig from './aws-exports';
import moment from "moment";
import Select from "react-select";
import assets from "../../assets/img/ble.gif";
import Axios from "../../utils/axiosInstance";
import swal from 'sweetalert';

class DashboardEmpUser extends PureComponent {

  state = {
    gateway_count: 0,
    workingcount: 0,
    non_workcount: 0,
    workingGatewaycount: 0,
    non_workGatewaycount: 0,
    admin_count: 0,
    guest_count: 0,
    device_count: 0,
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
    hours: "",
    newageGraphdata: [],
    workTag: [],
    allData: [],
    amrList: [],
    checkedStatus: [],
    checkedValue: [],
    selectedDevice: ""
  }


  onStop = async (event, data,selectPosition, gatewayName , x ,y) => {
    // Viewport (wrapper)
 

    const documentElement = document.documentElement
    const wrapperHeight = (window.innerHeight || documentElement.clientHeight)
    const wrapperWidth = (window.innerWidth || documentElement.clientWidth)
    

    const center = { 
      x: data.x + (data.node.clientWidth / 2),
      y: data.y + (data.node.clientHeight / 2)
    }
    
    // The margin from the draggable's center,
    // to the viewport sides (top, left, bottom, right)
    const margin = {
      top: center.y - 0,
      left: center.x - 0,
      bottom: wrapperHeight - center.y,
      right: wrapperWidth - center.x
    }
    
    // When we get the nearest viewport side (below), then we can 
    // use these metrics to calculate the new draggable sticky `position`
    const position = {
      top: { y: 0, x: data.x },
      left: { y: data.y, x: 0 },
      // bottom: { y: (wrapperHeight - data.node.clientHeight), x: data.x },
      // right:  { y: data.y, x: (wrapperWidth - data.node.clientWidth)}
    }

 
    // const sorted = Object.keys(margin).sort((a,b) => margin[a]-margin[b])
    // const nearestSide = sorted[0]

    const nearestSide = {x:position.top.x,y:position.left.y}
    
    this.setState({ position: nearestSide }, async ()=>{
      console.log("position",this.state.position)

      let token = localStorage.getItem("token");
      let fkUserId = localStorage.getItem("fkUserId");
      let role = localStorage.getItem("role");
  
      return Axios.post(`/gateway/change_possitions?gatewayName=${gatewayName}&x=${this.state.position.x}&y=${this.state.position.y}&fkUserId=${fkUserId}&role=${role}`, '', { headers: { "Authorization": `Bearer ${token}` } })
          .then(resultResponse => {
              console.log("Update Position", resultResponse);
              //window.location.reload();
              return Promise.resolve();
          }).catch(resultResponse => {
              console.log("Update Position", resultResponse);
          });
    }) 

  }

  async componentDidMount() {
    let resultAllCount = await getDashboardAllCount();
    console.log('DashboardAllCount', resultAllCount)

    let data = await allGatewayWiseGraphData();
    console.log('gatewayGraphData', data.data)
    this.setState({ newageGraphdata: data.data }, () => {
      console.log('newageGraphdata', this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0])
    })

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
    this.setState({
      gateway_count: resultAllCount && resultAllCount.data.totalGateways,
      device_count: resultAllCount && resultAllCount.data.totalTags,
      workingcount: resultAllCount && resultAllCount.data && resultAllCount.data.workingTags,
      non_workcount: resultAllCount && resultAllCount.data && resultAllCount.data.nonWorkingTags,
      workingGatewaycount: resultAllCount && resultAllCount.data && resultAllCount.data.workingGateways,
      non_workGatewaycount: resultAllCount && resultAllCount.data && resultAllCount.data.nonWorkingGateways,
    })

    let result = await getGatewayWiseData();
    if (result && result.data && result.data.length !== 0) {
    this.setState({ allData: result && result.data }, () => {
      console.log("GatewayWiseData", this.state.allData)
    })
  }else {
    swal("Failed", "Indoor Tracking Gateway Wise Tag Count Data is not present", "error");
  
  }

    
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
  changehours = async (e) => {
    console.log(e.target.value)
    this.setState({ nonWorkingTag: e.target.value }, async () => {
      let result = await getNonWorkingTag(this.state.nonWorkingTag)
      console.log('nonWorkingTag', result.data)

    })
  }
  changeGatewayhours = async (e) => {
    console.log(e.target.value)
    this.setState({ nonWorkingGateway: e.target.value }, async () => {
      let result = await getNonWorkingGateway(this.state.nonWorkingGateway)
      console.log('nonWorkingGateway', result.data)

    })
  }

  loading = () => <div className="animated fadeIn pt-1 text-center">Loading...</div>
  render() {
    const { allData, checkedStatus } = this.state
    const { data } = this.state
    var Role = localStorage.getItem('role');
    return (
      <div className="animated fadeIn">
        <div className="header bg-gradient-info pb-8 pt-5 pt-md-8">
          <Container fluid>
            <div className="header-body">
              {/* Card stats */}
              <Row>
                <Col lg="6" xl="4" >
                  <Card className="card-stats mb-4 mb-xl-0">
                    <CardBody>
                      <Link to={{ pathname: "/asset-tag" }}>
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
                <Col lg="6" xl="4" >
                  <Card className="card-stats mb-4 mb-xl-0">
                    <CardBody >
                      <Link to={{ pathname: "/working-tag" }}>
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
                <Col lg="6" xl="4">
                  <Card className="card-stats mb-4 mb-xl-0">
                    <CardBody>
                    <Link to={{pathname: "/Nonworking-tag"}}>
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
                <Col lg="6" xl="4" className="mt-3">
                  <Card className="card-stats mb-4 mb-xl-0">
                    <CardBody>
                      <Link to={{ pathname: "/GatewaywiseTab" }}>
                        <Row>
                          <div className="col">
                            <CardTitle tag="h5"
                              className="text-uppercase text-muted mb-0">
                              Total Gateways
                            </CardTitle>
                            <span className="h3 font-weight-bold mb-0">{this.state.gateway_count}</span>
                          </div>
                          <Col className="col-auto">
                            <div className="icon icon-shape bg-info text-white rounded-circle shadow">
                              {/* <i className="nav-icon fa fa-map-pin" /> */}
                              <i className="fa fa-map-marker fa-lg"></i>
                            </div>
                          </Col>
                        </Row>
                      </Link>
                    </CardBody>
                  </Card>
                </Col>
                <Col lg="6" xl="4" className="mt-3">
                  <Card className="card-stats mb-4 mb-xl-0">
                    <CardBody>
                    <Link to={{pathname: "/working-gateway"}}>
                      <Row>
                        <div className="col">
                          <CardTitle
                            tag="h5"
                            className="text-uppercase text-muted mb-0">
                            Working Gateways
                          </CardTitle>
                          <span className="h3 font-weight-bold mb-0">
                            {this.state.workingGatewaycount}
                          </span>
                        </div>
                        <Col className="col-auto">
                          <div className="icon icon-shape bg-green text-white rounded-circle shadow">
                            <i className="fa fa-map-marker fa-lg"></i>
                          </div>
                        </Col>
                      </Row>
                      </Link>
                    </CardBody>
                  </Card>
                </Col>
                <Col lg="6" xl="4" className="mt-3">
                  <Card className="card-stats mb-4 mb-xl-0">
                    <CardBody>
                    <Link to={{pathname: "/Nonworking-gateway"}}>
                      <Row>
                        <div className="col">
                          <CardTitle
                            tag="h5"
                            className="text-uppercase text-muted mb-0">
                            Non-Working Gateways
                          </CardTitle>
                          <span className="h3 font-weight-bold mb-0">
                            {this.state.non_workGatewaycount}
                          </span>
                        </div>
                        <Col className="col-auto">
                          <div className="icon icon-shape bg-orange text-white rounded-circle shadow">
                            <i className="fa fa-map-marker fa-lg"></i>
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
        <div className="container-fluid">
          <div className='ble_gsp'>
            <div>
              <div className="trackingHead">
                <h1>Indoor Asset Tracking</h1>
              </div>
              <div className="card__wrap">
                {allData.map((obj, key) => (
                    <Draggable onStop={(event, data,e) => this.onStop(event, data,e, obj.gatewayName, obj.x , obj.y)}
                      defaultPosition={{x: obj.positions.x, y: obj.positions.y}}>
                  <div className="handle1" key={key} >
                  <div className="dashbord_tag3">
                    <h4 className='gatewayHead'> {obj.gatewayLocation} </h4>
                    <br />
                    <h5>Total Tag:<span className="tag_value" title="Gateway Wise Total Tag">{obj.tagCount}</span></h5>
                    </div>
                  </div>
                  </Draggable>
                ))}
              </div>
            </div>
            <img src={assets} />
          </div>
        </div>
        <br />
        <br />
        <div className="dashboard__select__wrapper">
          <div className="select__block">
            <div ><b>SELECT GATEWAY</b></div>
            <Select options={this.state.gatewayList} className="payload__select" onChange={this.changeGateway} />
          </div>
        </div>
        <br />
        <br />
        <br />
        <HighchartsReact
          className="mscalculator-chart"
          highcharts={Highcharts}
          options={getoptions(this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0].gatewayNames, this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0].newTagCount, this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0].agedTagCount)}
          style={{ with: "100%" }}
        />
        <br />
        <br />
        {/* <div className='mapadd'>
         <Map />
        </div> */}
        <br />
        <br />
      </div>
    );
  }
}
export default DashboardEmpUser;
