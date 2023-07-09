import React, { Component } from 'react'
import './reports.css'
import { Tabs, Tab } from 'react-bootstrap'
import ParameterReport from './ParameterReport';
import ParameterAdminReport from './ParameterAdminReport';
import TagWiseReport from './TagWiseReport';
import GatewiseReport from './GatewiseReport';
import DateWiseTagReport from './DateWiseTagReport';
import DatewiseGatewayReport from './DatewiseGatewayReport';
import DateTimeWiseReport from './DateTimeWiseReport';



export default class Reports extends Component {
  constructor(props) {
    super(props);
    this.state = {
      Role: "",
      loader: false,
    };
  }

  async componentDidMount() {
    try {
      this.setState({ loader: true, Role: localStorage.getItem('role') })
    } catch (error) {
      console.log(error)
      this.setState({ loader: false })
    }
  }

  render() {

    return (
      <>
     
        <div className="tab-wrapper">
          <div className='' >
            <div className="row">
              <div className="col-sm-12">
                {(this.state.Role === 'admin') ?(
                    <Tabs defaultActiveKey="Superadmin">
                      <Tab eventKey="Superadmin" title="Tag_Wise">
                        <TagWiseReport />
                      </Tab>
                      <Tab eventKey="Gateway_Wise" title="Gateway_Wise">
                        <GatewiseReport />
                      </Tab>
                      <Tab eventKey="Date" title="Date Wise Tag Report">
                        <DateWiseTagReport />
                      </Tab>
                      <Tab eventKey="GatewayDate" title="Date Wise Gateway Report">
                        <DatewiseGatewayReport />
                      </Tab>
                      <Tab eventKey="TimeWise" title="Date & Time Wise Report">
                        <DateTimeWiseReport />
                      </Tab>
                      <Tab eventKey="ColumnWise" title="Parameters Wise Report">
                        <ParameterAdminReport />
                      </Tab>
                    </Tabs>
                  ) : this.state.Role === "organization" ?(
                      <Tabs defaultActiveKey="Superadmin">
                        <Tab eventKey="Superadmin" title="Tag_Wise">
                          <TagWiseReport />
                        </Tab>
                        <Tab eventKey="Gateway_Wise" title="Gateway_Wise">
                          <GatewiseReport />
                        </Tab>

                        <Tab eventKey="Date" title="Date Wise Tag Report">
                          <DateWiseTagReport />
                        </Tab>
                        <Tab eventKey="GatewayDate" title="Date Wise Gateway Report">
                          <DatewiseGatewayReport />
                        </Tab>
                        <Tab eventKey="TimeWise" title="Date & Time Wise Report">
                          <DateTimeWiseReport />
                        </Tab>
                        <Tab eventKey="ColumnWise" title="Parameters Wise Report">
                          <ParameterReport />
                        </Tab>
                      </Tabs>
                    ) : (this.state.Role === 'user' || this.state.Role === 'empuser') ? (
                      <Tabs defaultActiveKey="Superadmin">
                        <Tab eventKey="Superadmin" title="Tag_Wise">
                          <TagWiseReport />
                        </Tab>
                        <Tab eventKey="Gateway_Wise" title="Gateway_Wise">
                          <GatewiseReport />
                        </Tab>
                        <Tab eventKey="Date" title="Date Wise Tag Report">
                          <DateWiseTagReport />
                        </Tab>
                        <Tab eventKey="GatewayDate" title="Date Wise Gateway Report">
                          <DatewiseGatewayReport />
                        </Tab>
                        <Tab eventKey="TimeWise" title="Date & Time Wise Report">
                          <DateTimeWiseReport />
                        </Tab>
                        <Tab eventKey="ColumnWise" title="Parameters Wise Report">
                          <ParameterAdminReport />
                        </Tab>
                      </Tabs>
                    ) : (
                        <Tabs defaultActiveKey="Superadmin">
                          <Tab eventKey="Superadmin" title="Tag_Wise">
                            <TagWiseReport />
                          </Tab>
                          <Tab eventKey="Gateway_Wise" title="Gateway_Wise">
                            <GatewiseReport />
                          </Tab>
                          <Tab eventKey="Date" title="Date Wise Tag Report">
                            <DateWiseTagReport />
                          </Tab>
                          <Tab eventKey="GatewayDate" title="Date Wise Gateway Report">
                            <DatewiseGatewayReport />
                          </Tab>
                          <Tab eventKey="TimeWise" title="Date & Time Wise Report">
                            <DateTimeWiseReport />
                          </Tab>
                          <Tab eventKey="ColumnWise" title="Parameters Wise Report">
                            <ParameterReport />
                          </Tab>
                        </Tabs>
                      )}
              </div>
            </div>
          </div>
          <br />
        </div>
      </>
    )
  }
}
