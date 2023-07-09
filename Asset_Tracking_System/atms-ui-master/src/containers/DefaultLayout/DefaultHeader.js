import React, { Component } from 'react';
import { Link, NavLink, withRouter } from 'react-router-dom';
import * as ReactDOM from "react-dom";
import Select from "react-select";
import {
  Badge, UncontrolledDropdown, DropdownItem, DropdownMenu, DropdownToggle, Nav, NavItem, ListGroup,
  ListGroupItem,
} from 'reactstrap';
import PropTypes from 'prop-types';
import contactImg from "../../assets/img/contact.jpg";
import './DefaultHeader.css'
import { AppAsideToggler, AppNavbarBrand, AppSidebarToggler } from '@coreui/react';
import { getBatterypercentage } from "../../Service/getBatterypercentage"
import { getDownloadCount } from "../../Service/getDownloadCount"
import { getNotificationCount } from "../../Service/getNotificationCount"
import { getAgedTagCount } from "../../Service/getAgedTagCount"
// import { ToastContainer, toast } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css';
import addNotification from 'react-push-notification';
import { Notifications } from 'react-push-notification';
import swal from "sweetalert";

const propTypes = {
  children: PropTypes.node,
};
const defaultProps = {};

class DefaultHeader extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isHidden: false,
      battery: [],
      batteryUpdate: false,
      agedTagUpdate: false,
      allBattery: [],
      agedTagCount: [],
      dotDounloadCount: 0,
      dotBatteryCount: 0,
      dotAgedCount: 0,
      Role: "",
      categoryname: "",
      category: false,
      downloadFile: [],
      allNotification: [],
      tagName: "",
      loader: false,
      battryPercentage: "100",
      allBattery: [],
      allCategory: [
        {
          label: "BLE",
          value: "BLE"
        },
        {
          label: "GPS",
          value: "GPS"
        }
      ]
    }
  }

  async componentDidMount() {
    try {
      setTimeout(async () => {
        this.setState({ loader: true })
        var setRole = localStorage.getItem('role');

        var setcategory = localStorage.getItem('categoryname');
        this.setState({ Role: setRole, categoryname: setcategory, loader: false }, () => {
          console.log('categoryname', this.state.categoryname);
          let tempArray = this.state.categoryname.split(","
          )
          console.log('tempArray', tempArray)
          let tempCategoryBLE = tempArray.find(obj => {
            return obj === "BLE"
          })
          let tempCategoryGPS = tempArray.find(obj => {
            return obj === "GPS"
          })
          if (tempCategoryBLE === "BLE" && tempCategoryGPS === "GPS") {
            this.setState({ category: true }, () => {
              console.log('temp categoryArray', this.state.category)
            })
          }
        })
        let result = await getBatterypercentage()
        if (result && result.data && result.data.length !== 0) {
          this.setState({ allBattery: result.data }, () => {
            console.log('Battery Percentage data', this.state.allBattery)
          })
        } else {
          this.setState({ allBattery: [] })
          //swal("Sorry", "Battery % Data is not present", "warning");

        }


        let resultDownload = await getDownloadCount()
        console.log('Total Download Count', resultDownload)
        if (resultDownload && resultDownload.data && resultDownload.data.length !== 0) {
          this.setState({ downloadFile: resultDownload.data, loader: false }, () => {
            console.log('Notification Download Count', this.state.downloadFile)
          })
        }
        // else {
        //   swal("Failed", "Notification-Download Count Data is not present", "error");}

        let resultTagCount = await getAgedTagCount()
        console.log('agedTag Total Count', resultTagCount)
        // if (this.state.battryPercentage <= "20" ) {
        //  swal("Battery Warning", "Device Battery Is Low ! Please Check Notification !", "warning");
        // }
      }, 200)

    } catch (error) {
      console.log(error)
      this.setState({ loader: false })
    }
  }

  clickCard = async (tag) => {
    console.log("selectedTag", tag)
    this.props.history.push({
      pathname: `/battery-percentage`,
      state: { deviceId: tag },
    });
  }
  changeCategory = (e) => {
    var cat = e.value;
    localStorage.setItem("SelectedCategory", cat)
    this.props.onSelectCategory(cat);
    if (cat === "BLE") {
      this.props.history.push(
        "/dashboard"
      );
    }
    else if (cat === "GPS") {
      this.props.history.push("/dashboard_GPS")
    }
  }

  render() {
    const { children, ...attributes } = this.props;
    const { allBattery, agedTagCount, downloadFile } = this.state
    return (
      <React.Fragment>
        <AppSidebarToggler className="d-lg-none" display="md" mobile />
        <div className="main_heading">
          <h3>Asset Tracking System</h3>
        </div>
        <Nav className="ml-auto" navbar>

          <div className='Newcategory'>
            {(this.state.category === true) ?
              (
                <div className='category'>
                  <Select options={this.state.allCategory} onChange={this.changeCategory} placeholder="BLE" />
                </div>
              ) : (
                ""
              )}
          </div>

          <UncontrolledDropdown nav direction="down">
            <DropdownToggle nav>
              <div className='icon-with-badge'>
                <i className="fa fa-bell-o fa-fw" aria-hidden="true"> </i>
                <div className="tag_notification">
                  <span className="position-absolute top-0 start-100 translate-middle p-2 bg-danger border border-light rounded-circle"></span>
                </div>
              </div>
            </DropdownToggle>
            <DropdownMenu className="notification" right>
              <DropdownItem className="d-flex justify-content-between align-items-center">
                <h6 className="mb-0">Activity Feed</h6>
              </DropdownItem>
              <div className='extended-dropdown__section extended-dropdown__section--list'>
                {(this.state.Role === 'admin' || this.state.Role === 'organization') ?
                  (
                    <div>
                      <ListGroup>
                        <div className="baterry_item">
                          <div className="tag_notification">
                            <div className='battery_field'><h3>{this.state.downloadFile.username}</h3>&nbsp;&nbsp;
                            <h4>has downloaded the file </h4><h3>&nbsp;&nbsp;{this.state.downloadFile.downlodeCount}&nbsp;&nbsp;</h3>
                            <h4> times</h4>&nbsp;&nbsp;
                            </div>
                            <hr />
                          </div>
                        </div>
                      </ListGroup>
                      <ListGroup>
                        <div className="baterry_item">
                          <div className="tag_notification">
                            {allBattery.map((obj, key) => (
                              <div className="baterry_percent" key={key}>
                                <div className='battery_field'><h3>{obj.assetTagName} </h3>
                                  &nbsp;&nbsp;<h4>this Device Battery is Low</h4>&nbsp;&nbsp;&nbsp;&nbsp;
                                  <button className='btn-success'><Link to={{ pathname: "/battery-percentage", state: { deviceId: obj.assetTagName } }}>Show</Link></button>
                                </div>
                                <hr />
                              </div>
                            ))}
                          </div>
                        </div>
                      </ListGroup>
                      <ListGroup>
                        <div className="baterry_item">
                          <div className="tag_notification">
                            {agedTagCount.map((obj, key) => (
                              <div className="baterry_percent" key={key}>
                                <div className='battery_field'><h3>{obj}</h3>&nbsp;&nbsp;<h4>is Aged Tag</h4>&nbsp;&nbsp;&nbsp;&nbsp;
                                </div>
                                <hr />
                              </div>
                            ))}
                          </div>
                        </div>
                      </ListGroup>
                    </div>
                  )
                  : (this.state.Role) === 'user' ? (
                    <div>
                      <ListGroup>
                        <div className="baterry_item">
                          <div className="tag_notification">
                            {allBattery.map((obj, key) => (
                              <div className="baterry_percent" key={key}>
                                <div className='battery_field'><h3>{obj.assetTagName} </h3>
                                  &nbsp;&nbsp;<h4>this Device Battery is Low</h4>&nbsp;&nbsp;&nbsp;&nbsp;
                                  <button className='btn-success'><Link to={{ pathname: "/battery-percentage", state: { deviceId: obj.assetTagName } }}>Show</Link></button>
                                </div>
                                <hr />
                              </div>
                            ))}
                          </div>
                        </div>
                      </ListGroup>
                    </div>
                  ) : (
                    <div>
                      <ListGroup>
                        <div className="baterry_item">
                          <br />
                          <div className="tag_notification">
                            <div className='battery_field'><h3>{this.state.downloadFile.username}</h3>&nbsp;&nbsp;<h4>has downloaded the file </h4><h3>&nbsp;&nbsp;{this.state.downloadFile.downlodeCount}&nbsp;&nbsp;</h3><h4> times</h4>&nbsp;&nbsp;
                            </div>
                            <hr />
                          </div>
                        </div>
                      </ListGroup>
                      <ListGroup>
                        <div className="baterry_item">
                          <div className="tag_notification">
                            {allBattery.map((obj, key) => (
                              <div className="baterry_percent" key={key}>
                                <div className='battery_field'><h3>{obj.assetTagName} </h3>
                                  &nbsp;&nbsp;<h4>this Device Battery is Low</h4>&nbsp;&nbsp;&nbsp;&nbsp;
                                  <button className='btn-success'><Link to={{ pathname: "/battery-percentage", state: { deviceId: obj.assetTagName } }}>Show</Link></button>
                                </div>
                                <hr />
                              </div>
                            ))}
                          </div>
                        </div>
                      </ListGroup>
                      <ListGroup>
                        <div className="baterry_item">
                          <div className="tag_notification">
                            {agedTagCount.map((obj, key) => (
                              <div className="baterry_percent" key={key}>
                                <div className='battery_field'><h3>{obj}</h3>&nbsp;&nbsp;<h4>is Aged Tag</h4>&nbsp;&nbsp;&nbsp;&nbsp;
                                </div>
                                <hr />
                              </div>
                            ))}
                          </div>
                        </div>
                      </ListGroup>
                    </div>)}
              </div>
            </DropdownMenu>
          </UncontrolledDropdown>
          <UncontrolledDropdown nav direction="down">
            <DropdownToggle nav>
              <img src={contactImg} className="img-contact" />
            </DropdownToggle>
            <DropdownMenu right>
              <DropdownItem><i className="fa fa-phone" aria-hidden="true"></i> <a href="tel:8888741044">8888741044</a></DropdownItem>
              <DropdownItem><i className="fa fa-envelope" aria-hidden="true"></i><a href="mailto:sales@embel.co.in">sales@embel.co.in</a></DropdownItem>
            </DropdownMenu>
          </UncontrolledDropdown>
          <UncontrolledDropdown nav direction="down">
            <DropdownToggle nav>
              <img src={'../../assets/img/avatars/track.jfif'} className="img-avatar" alt="admin@bootstrapmaster.com" />
            </DropdownToggle>
            <DropdownMenu right>
              <DropdownItem><i className="fa fa-user"></i> {localStorage.getItem('firstname') + ' ' + localStorage.getItem('lastname')}/{localStorage.getItem('role')}</DropdownItem>
              <DropdownItem onClick={e => this.props.onLogout(e)}><i className="fa fa-lock"></i> Logout</DropdownItem>
            </DropdownMenu>
          </UncontrolledDropdown>
        </Nav>
      </React.Fragment>
    );
  }
}
DefaultHeader.propTypes = propTypes;
DefaultHeader.defaultProps = defaultProps;
export default withRouter(DefaultHeader)


