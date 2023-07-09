import React, { PureComponent } from 'react';
import './DashboardAdmin.css'
import { Link, NavLink } from 'react-router-dom';
import { Formik, Form, Field } from 'formik';
import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'
import getoptions from './highChartData'
import Map from './Map'
import { listGateway } from "../../Service/listGateway"
import { allGatewayWiseGraphData } from "../../Service/allGatewayWiseGraphData"
import { getSingleGraphData } from "../../Service/getSingleGraphData"
import { getGatewayWiseData } from '../../Service/getGatewayWiseData'
import { getDashboardAllCount } from "../../Service/getDashboardAllCount"
import { getUpdateLimits } from '../../Service/getUpdateLimits'
import { postUpdateLimits } from '../../Service/postUpdateLimits'
import { Card, CardBody, CardTitle, Container, Row, Col } from "reactstrap";
import Draggable, { ControlPosition } from 'react-draggable';
import Modal from 'react-bootstrap/Modal';
import { postUploadDoc } from "../../Service/postUploadDoc";
import { getImage } from '../../Service/getImage'

//import awsconfig from './aws-exports';
import moment from "moment";
import Select from "react-select";
import assets from "../../assets/img/ble.gif";
import swal from 'sweetalert';
import Axios from "../../utils/axiosInstance";
import AWS from "aws-sdk";

class DashboardAdmin extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      gateway_count: 0,
      workingcount: 0,
      non_workcount: 0,
      workingGatewaycount: 0,
      non_workGatewaycount: 0,
      admin_count: 0,
      guest_count: 0,
      device_count: 0,
      totalEmpUser: 0,
      tagCountLimit: 0,
      gatewayCountLimit: 0,
      agedDayCountLimit: 0,
      gatewayList: [],
      selectDuration: "",
      selectedGateway: "",
      selectedAssetTag: "",
      nonWorkingTag: '',
      nonWorkingGateway: '',
      agedTagInput: '',
      data: [],
      customDate: false,
      startDate: "",
      endDate: "",
      hours: "",
      fkUserId: "",
      fkadminId: "",
      newageGraphdata: [],
      workTag: [],
      allData: [],
      amrList: [],
      limitData: [],
      checkedStatus: [],
      checkedValue: [],
      selectedDevice: "",
      isRevealPwd: false,
      isRevealPwd1: false,
      isRevealPwd2: false,
      isImage: false,
      tag: false,
      gateway: false,
      aged: false,
      isOpen: false,
      fileLastm0: "",
      fileLastm0name: "",
      fileLastm0numUrl: "",
      fkUserId: localStorage.getItem("fkUserId"),
      S3_BUCKET: "asset-tracking-image-upload",
      REGION: "us-east-2",
      filesLogo: [],
      selectedType: "",
      allImage: {}

    };
    this.onSubmit = this.onSubmit.bind(this);
  }

  openModal = () => this.setState({ isOpen: true });
  closeModal = () => this.setState({ isOpen: false });



  onStop = async (event, data,selectPosition, gatewayName, x, y) => {

    const documentElement = document.documentElement
    const wrapperHeight = (window.innerHeight || documentElement.clientHeight)
    const wrapperWidth = (window.innerWidth || documentElement.clientWidth)


    const center = {
      x: data.x + (data.node.clientWidth / 2),
      y: data.y + (data.node.clientHeight / 2)
    }

    const margin = {
      top: center.y - 0,
      left: center.x - 0,
      bottom: wrapperHeight - center.y,
      right: wrapperWidth - center.x
    }

    const position = {
      top: { y: 0, x: data.x },
      left: { y: data.y, x: 0 },
    }
    const nearestSide = { x: position.top.x, y: position.left.y }

    this.setState({ position: nearestSide }, async () => {
      console.log("position", this.state.position)

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
    try{
    let resultAllCount = await getDashboardAllCount();
    console.log('DashboardAllCount', resultAllCount)

    let data = await allGatewayWiseGraphData();
    console.log('gatewayGraphData', data.data)
    this.setState({ newageGraphdata: data.data }, () => {
      console.log('newageGraphdata', this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0])
    })

    this.setState({
      gateway_count: resultAllCount && resultAllCount.data.totalGateways,
      guest_count: resultAllCount && resultAllCount.data.totalUser,
      device_count: resultAllCount && resultAllCount.data.totalTags,
      totalEmpUser: resultAllCount && resultAllCount.data.totalEmpUser,
      workingcount: resultAllCount && resultAllCount.data && resultAllCount.data.workingTags,
      non_workcount: resultAllCount && resultAllCount.data && resultAllCount.data.nonWorkingTags,
      workingGatewaycount: resultAllCount && resultAllCount.data && resultAllCount.data.workingGateways,
      non_workGatewaycount: resultAllCount && resultAllCount.data && resultAllCount.data.nonWorkingGateways,
    }, () => {
      console.log('Tag', this.state.non_workGatewaycount)
    })

    //indoor tracking data

    let result = await getGatewayWiseData();
    if (result && result.data && result.data.length !== 0) {
      this.setState({ allData: result && result.data }, () => {
        console.log("GatewayWiseData", this.state.allData)
      })
    } 
    // else {
    //   swal("Failed", "Indoor Tracking Gateway Wise Tag Count Data is not present", "error");

    // }

    let resultImg = await getImage(this.state.fkadminId);
    console.log("All Image", resultImg.data)
    if (resultImg && resultImg.data && resultImg.data.length !== 0) {
      this.setState({ allImage: resultImg && resultImg.data }, () => {
        console.log("Indoor Image Data", this.state.allImage)
      }
      )

    } 
    // else {
    //   swal("Failed", "Indoor Image is not present", "error");
    // }



    //Hours and Day Limit data
    let resultOfLimit = await getUpdateLimits(this.state.fkUserId);
    if (resultOfLimit && resultOfLimit.data && resultOfLimit.data.length !== 0) {
      this.setState({
        nonWorkingTag: resultOfLimit.data.hLimitForNonWorkingTag,
        nonWorkingGateway: resultOfLimit.data.hLimitForNonWorkingGateway,
        agedTagInput: resultOfLimit.data.daysLimitAgedAndRecentTag
      }, () => {
        console.log("Time Limit alldate", resultOfLimit.data)

        if (resultOfLimit.data.hLimitForNonWorkingTag !== null) {
          this.setState({ isRevealPwd: !this.state.isRevealPwd });
        }
        if (resultOfLimit.data.hLimitForNonWorkingGateway !== null) {
          this.setState({ isRevealPwd1: !this.state.isRevealPwd });
        }
        if (resultOfLimit.data.daysLimitAgedAndRecentTag !== null) {
          this.setState({ isRevealPwd2: !this.state.isRevealPwd });
        }
      })

    } 
    // else {
    //   swal("Failed", "Time Limit Data is not present", "error");
    // }
  } catch (error) {
    console.log(error);
    //swal("Sorry", "Data is not present", "warning");
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
      console.log('nonWorkingTag', this.state.nonWorkingTag)
    })

  }
  onClickGateway = async () => {
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
    this.setState({
      gatewayList: tempArray,
      selectedGateway: gatewayList && gatewayList.data[0],
    }, () => {
      console.log('Gateway List', this.state.gatewayList)
    })
  }

  changeGatewayhours = async (e) => {
    console.log(e.target.value)
    this.setState({ nonWorkingGateway: e.target.value }, async () => {
      console.log('nonWorkingGateway', this.state.nonWorkingGateway)
    })
  }

  changeAged = async (e) => {
    console.log(e.target.value)
    this.setState({ agedTagInput: e.target.value }, async () => {
      console.log('AgedTagInput', this.state.agedTagInput)
    })
  }



  toggleShow = async () => {
    this.setState({ isRevealPwd: !this.state.isRevealPwd });
    if
      (document.getElementById("displaytable2").style.display === "none")
      document.getElementById("displaytable2").style.display = "block";
    else
      (document.getElementById("displaytable3").style.display = "none")
    document.getElementById("displaytable4").style.display = "none";
  }

  toggleShow1 = () => {
    this.setState({ isRevealPwd1: !this.state.isRevealPwd1 });
    if (document.getElementById("displaytable3").style.display === "none")
      document.getElementById("displaytable3").style.display = "block";
    else
      (document.getElementById("displaytable4").style.display = "none")
    document.getElementById("displaytable2").style.display = "none";
  }


  toggleShow2 = () => {
    this.setState({ isRevealPwd2: !this.state.isRevealPwd2 });
    if (document.getElementById("displaytable4").style.display === "none")
      document.getElementById("displaytable4").style.display = "block";
    else
      (document.getElementById("displaytable2").style.display = "none");
    document.getElementById("displaytable3").style.display = "none";
  }

  clickTag = () => {
    this.setState({ isRevealPwd: !this.state.isRevealPwd });
    if (document.getElementById("displaytable2").style.display === "none")
      document.getElementById("displaytable2").style.display = "block";
    else
      document.getElementById("displaytable2").style.display = "none";
  }

  clickGateway = () => {
    this.setState({ isRevealPwd1: !this.state.isRevealPwd1 });
    if (document.getElementById("displaytable3").style.display === "none")
      document.getElementById("displaytable3").style.display = "block";
    else
      document.getElementById("displaytable3").style.display = "none";
  }

  clickAged = () => {
    this.setState({ isRevealPwd2: !this.state.isRevealPwd2 });
    if (document.getElementById("displaytable4").style.display === "none")
      document.getElementById("displaytable4").style.display = "block";
    else
      document.getElementById("displaytable4").style.display = "none";
  }

  async onSubmit(e) {
    e.preventDefault();
    // try {


    AWS.config.update({
      region: this.state.REGION,
      accessKeyId: 'AKIAUPTDWS7563EXCMBM',
      secretAccessKey: '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'
    });

    const myBucket = new AWS.S3({
      params: { Bucket: this.state.S3_BUCKET },
      region: this.state.REGION,
    });

    let key = `${this.state.fkUserId}/${this.state.fkUserId}_${this.state.fileLastm0name}`;

    const params = {
      ACL: "public-read",
      Body: this.state.fileLastm0,
      Bucket: this.state.S3_BUCKET,
      Key: key,
    };

    myBucket
      .putObject(params)
      .on("httpUploadProgress", function (progress) {
        console.log(
          Math.round((progress.loaded / progress.total) * 100) + "% done"
        );
      })
      .send((err) => {
        if (err) console.log(err);
      });

    const data = {
      fkadminId: this.state.fkUserId,
      image: this.state.fileLastm0numUrl
    };


    await postUploadDoc(data)
      .then(async (resultResponse) => {
        console.log("file add", resultResponse);
        swal("Great", "Image uploaded successfully", "success");

        let resultImg = await getImage(this.state.fkadminId);
        console.log("All Image", resultImg.data)
        if (resultImg && resultImg.data && resultImg.data.length !== 0) {
          this.setState({ allImage: resultImg && resultImg.data }, () => {
            console.log("Indoor Image Data", this.state.allImage)
          }
          )
        }
        //window.location.reload();
        return Promise.resolve();
      }).catch(resultResponse => {
        swal(
          "Error",
          "Something went wrong please check your internet ",
          "error"
        );
      });
  }

  onChangeImg = (e) => {
    let fileLastName = e.target.files[0].name;
    console.log("file", e.target.files[0]);
    let extension = fileLastName.split(".").pop();

    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);
    this.setState({ filesLogo: [...filesArr] });

    this.setState(
      {
        //fileLast: URL.createObjectURL(e.target.files[0]),
        fileLastm0: e.target.files[0]
      },
      () => {
        var fname = "indoorImage." + extension;
        let key = `${this.state.fkUserId}/${this.state.fkUserId}_${fname}`;
        let path =
          "https://" +
          this.state.S3_BUCKET +
          ".s3." +
          this.state.REGION +
          ".amazonaws.com/" +
          key;
        this.setState({
          fileLastm0numUrl: path,
          fileLastm0name: "indoorImage." + extension,
        });
      }
    );

  };

  onImage = () => {

    this.setState({ isImage: !this.state.isImage });
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
                <Col lg="6" xl="4">
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
                <Col lg="6" xl="4">
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
                      <Link to={{ pathname: "/Nonworking-tag" }}>
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
                      <Link to={{ pathname: "/working-gateway" }}>
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
                      <Link to={{ pathname: "/Nonworking-gateway" }}>
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
                <Col lg="6" xl="4" className="mt-3">
                  <Card className="card-stats mb-4 mb-xl-0">
                    <CardBody >
                      <Link to={{ pathname: "/dashboard-user-list" }}>
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
                <Col lg="6" xl="4" className="mt-3">
                  <Card className="card-stats mb-4 mb-xl-0">
                    <CardBody >
                    <Link to={{ pathname: "/dashboard-emp-list" }}>
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
              </Row>
            </div>
          </Container>
        </div>
        <br />
        <br />
        <br />

        <Formik
          enableReinitialize={true}
          initialValues={{
            nontag: this.state.nonWorkingTag,
            nongateway: this.state.nonWorkingGateway,
            agedtag: this.state.agedTagInput,
          }}
          // validationSchema={SignupSchema}
          onSubmit={async values => {
            let fkUserId = await localStorage.getItem("fkUserId")
            const payload = {
              fkadminId: fkUserId,
              hLimitForNonWorkingTag: this.state.nonWorkingTag,
              hLimitForNonWorkingGateway: this.state.nonWorkingGateway,
              daysLimitAgedAndRecentTag: this.state.agedTagInput,
            }
            console.log('Limit Data', payload);
            let result = await postUpdateLimits(payload)
            console.log("Limit Result", result)
            if (result.status === 200) {
              this.setState({ nonWorkingTag: result.data.hLimitForNonWorkingTag, nonWorkingGateway: result.data.hLimitForNonWorkingGateway, agedTagInput: result.data.daysLimitAgedAndRecentTag })
              swal("Great", "Data added successfully", "success");
            } else {
              swal("Failed", "Something went wrong please check your internet", "error");
            }
          }}
        >
          {({ errors, touched }) => (
            <Form>
              <div className='non_working'>
                <div className="container">
                  <div className="row">
                    <div className="col-md-4">
                      <div className="row">
                        <div className="non_tag">
                          <div ><b>ADD HRS LIMIT FOR NON WORKING TAG</b></div>
                          <Field className="form-control" name="nontag" placeholder="HRS" onChange={this.changehours} readOnly={this.state.isRevealPwd} />
                        </div>
                        <div className="edit_tag" align="center" onClick={this.toggleShow} >
                          <i className="fa fa-edit fa-lg new"></i>
                          {/* <input type="button" value="Edit" /> */}
                        </div>
                      </div>

                    </div>
                    <div className="col-md-4">
                      <div className="row">
                        <div className="non_gateway">
                          <div ><b>ADD HRS LIMIT FOR NON WORKING GATEWAY</b></div>
                          <Field className="form-control" name="nongateway" placeholder="HRS" onChange={this.changeGatewayhours} readOnly={this.state.isRevealPwd1} />
                          <div className="edit_gateway" align="center" onClick={this.toggleShow1}>
                            <i className="fa fa-edit fa-lg new"></i>
                            {/* <input type="button" value="Edit"  /> */}
                          </div>
                        </div>
                      </div>

                    </div>
                    <div className="col-md-4">
                      <div className="row">
                        <div className="non_time">
                          <div ><b>ADD DAYS LIMIT FOR AGED TAG</b></div>
                          <Field className="form-control" name="agedtag" placeholder="DAYS" onChange={this.changeAged} readOnly={this.state.isRevealPwd2} />
                          <div className="edit_aged" align="center" onClick={this.toggleShow2}>
                            <i className="fa fa-edit fa-lg new"></i>
                            {/* <input type="button" value="Edit"  /> */}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="updateTag" id="displaytable2" style={{ display: 'none' }} align="center">
                    <input type="submit" value="Submit" onClick={this.clickTag} />
                  </div>
                  <div className="updateGateway" id="displaytable3" style={{ display: 'none' }} align="center">
                    <input type="submit" value="Submit" onClick={this.clickGateway} />
                  </div>
                  <div className="update" id="displaytable4" style={{ display: 'none' }} align="center">
                    <input type="submit" value="Submit" onClick={this.clickAged} />
                  </div>
                  <br />
                </div>
                <br />
                <br />
              </div>
              {/* </div> */}
            </Form>
          )}
        </Formik>
        <br />
        <br />

        {/* <button> */}
        {/* <i className="fa fa-file-excel-o fa-lg mt-4"></i> */}
        <div>
          {/* <marquee behavior="scroll" direction="left" scrollamount="5"><p>Add image for indoor tracking</p></marquee> */}
          <form onSubmit={this.onSubmit}>
            <div className='fileadd'>
            <label className="postone-custom-file-upload">
              <input type="file" name="logo" id="file" onChange={this.onChangeImg} />
              <div className='image-icon' >
                <i className="fa fa-image fa-lg mt-4" onClick={this.onImage}></i>
                {this.state.isImage ?
                  <button type="submit" className="btn btn-primary" id="image" ><i className="fa fa-upload fa-lg"></i></button>
                  : null
                }
              </div>
            </label>
            </div>
            {this.state.filesLogo.map(x =>
              <div className="postone-file-preview">{x.name}</div>
            )}
          </form>
        </div>
        <br />
        <div className="container-fluid">
          <div className='ble_gsp'>
            <div>
              <div className="trackingHead">
                <h1>Indoor Asset Tracking</h1>
              </div>
              <div className="card__wrap">
                {allData.map((obj, key) => (
                  <Draggable onStop={(event, data, e) => this.onStop(event, data, e, obj.gatewayName, obj.x, obj.y)}
                    defaultPosition={{ x: obj.positions.x, y: obj.positions.y }}>
                    <div className="handle1" key={key} >
                      <div className="dashbord_tag2">
                        <h4 className='gatewayHead'> {obj.gatewayLocation} </h4>
                        <br />
                        <h5>Total Tag:<span className="tag_value" title="Gateway Wise Total Tag">{obj.tagCount}</span></h5>
                      </div>
                    </div>
                  </Draggable>
                ))}
              </div>
            </div>

            <div className='indoorImage'>
              <img src={this.state.allImage.image} />
            </div>

          </div>
        </div>
        <br />
        <br />
        <div className="dashboard__select__wrapper">
          <div className="select__block">
            <div><b>SELECT GATEWAY</b></div>
            <div onClick={this.onClickGateway}>
              <Select options={this.state.gatewayList} className="payload__select" onChange={this.changeGateway} />
            </div>
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
        {/* <HighchartsReact
          className="mscalculator-chart"
          highcharts={Highcharts}
          options={getGpsGraph(data && data.graphdata)}
          style={{ with: "100%" }} id="fetchURL"
        />
        <br /> */}
      </div>
    );
  }
}
export default DashboardAdmin;
