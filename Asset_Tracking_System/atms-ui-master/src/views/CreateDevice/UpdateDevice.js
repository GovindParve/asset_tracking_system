import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import moment from "moment";
import { getDeviceUser } from '../../Service/getDeviceUser'
import { getAdmin } from '../../Service/getAdmin'
import './CreateDevice.css'
import { getEditDevice } from "../../Service/geteditDevice"
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';

import "moment-timezone";

import { getSingleDevice } from "../../Service/getSingleDevice"
import { putDevice } from "../../Service/putDevice"
const SignupSchema = Yup.object().shape({
    imei: Yup.string()
        .required('Required'),
    amr_id: Yup.string()
        .required('Required'),
    imsi: Yup.string()
        .required('Required'),
    liter_per_pulse: Yup.string()
        .required('Required'),
    deviceTypeAmrOrBle: Yup.string()
         .required('Required'),
        // timezone: Yup.string()
        // .required('Required'),

});
const timezones = timezoneNames()
    .filter((l) => l.indexOf("/") > -1)
    .sort((a, b) => a.localeCompare(b));
export default class UpdateDevice extends Component {
    state = {
        timezone: "Asia/Kolkata",
        result: [],
        allDataUser: [],
        allDataAdmin: [],
        id: 0,
        selectedUserId:'',
        selectedAdminId:'',
        loader:false,
    }
    componentDidMount = async () => {

        try {
            this.setState({loader:true})
            if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
                const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;

                let result = await getEditDevice(propId)
                console.log('result',result)
                let resultUser = await getDeviceUser();
                let resultAdmin = await getAdmin();
                this.setState({timezone:result.data[0].deviceTimeZone})
                console.log('ress1',this.state.timezone)
                console.log('ress2',result)
                let tempArray = []
                resultUser && resultUser.data && resultUser.data.map((obj) => {
                    let tempObj = {
                        UserId :`${obj.fkUserId}`,
                        value: `${obj.firstname + " " + obj.lastname}`,
                        label: `${obj.firstname + " " + obj.lastname}`
                    }
                    tempArray.push(tempObj)
                })
                
                let tempAdminArray = []
                resultAdmin && resultAdmin.data && resultAdmin.data.map((obj) => {
                    let tempAdminObj = {
                        AdminId:`${obj.fkAdminId}`,
                        value: `${obj.firstname + " " + obj.lastname}`,
                        label: `${obj.firstname + " " + obj.lastname}`
                    }
                    tempAdminArray.push(tempAdminObj)
                })
                this.setState({ allDataUser: tempArray, allDataAdmin: tempAdminArray, result: result && result.data, id: propId,loader:false },()=>{

                     console.log("user",this.state.allDataUser);
                     console.log("admin...",this.state.allDataAdmin);
                });
                
            } else if (this.props.match && this.props.match.params && this.props.match.params.id) {
                const propParamId = this.props.match && this.props.match.params && this.props.match.params.id;
                let result = await getSingleDevice(propParamId)
                let resultUser = await getDeviceUser();
                let resultAdmin = await getAdmin();
                console.log('ress2',result)
                this.setState({timezone:result.data[0].deviceTimeZone})
                console.log('ress1',result.data[0].deviceTimeZone)
                
                let tempArray = []
                resultUser && resultUser.data && resultUser.data.map((obj) => {
                    let tempObj = {
                        UserId :`${obj.fkUserId}`,
                        value: `${obj.firstname + " " + obj.lastname}`,
                        label: `${obj.firstname + " " + obj.lastname}`
                    }
                    tempArray.push(tempObj)
                })
                let tempAdminArray = []
                resultAdmin && resultAdmin.data && resultAdmin.data.map((obj) => {
                    let tempAdminObj = {
                        AdminId:`${obj.fkAdminId}`,
                        value: `${obj.firstname + " " + obj.lastname}`,
                        label: `${obj.firstname + " " + obj.lastname}`
                    }
                    tempAdminArray.push(tempAdminObj)
                })
                
                this.setState({ allDataUser: tempArray, allDataAdmin: tempAdminArray, result: result && result.data, id: propParamId,loader:false })
                
            } else {
                this.props.history.push("/device");
            }
            
        } catch (error) {
            console.log(error)
            this.setState({loader:false})
        }
    }
    changeUser = (selectedUser) => {
        console.log('selectedUser--------.',selectedUser.UserId);
        this.setState({selectedUserId:selectedUser.UserId},()=>{
            console.log('UserId',this.state.selectedUserId);
        })    
    }
    changeAdmin = (selectedAdmin) => {
        console.log('selectedAdmin',selectedAdmin.AdminId);
        this.setState({ selectedAdminId:selectedAdmin.AdminId},()=>{
            console.log('AdminId',this.state.selectedAdminId);
        })
    }
    handleTimezoneChange = (event) => {
        this.setState({ timezone: event.target.value });
    };
    render() {

console.log('initialValues',this.state && this.state.result[0] && this.state.result[0].deviceBuildingNameOrWingName);
        console.log(this.state.allDataUser)
        

        const { loader} = this.state
        return (
            <>
            {loader?<div class="loader"></div>:
            <div>
                <Formik
                    enableReinitialize={true}
                    initialValues={{
                        
                        imei: this.state && this.state.result[0] && this.state.result[0].deviceImei,
                        amr_id: this.state && this.state.result[0] && this.state.result[0].deviceAmrId,
                        sim: this.state && this.state.result[0] && this.state.result[0].deviceSim,
                        imsi: this.state && this.state.result[0] && this.state.result[0].deviceImsi,
                        amr_enable: this.state && this.state.result[0] && this.state.result[0].deviceAmrEnable === 0 ? false : true,
                        // wakeupTime: moment(this.state && this.state.result[0] && this.state.result[0].deviceWakeupTime).format("YYYY-MM-DDTHH:mm"),
                        wakeupTime: this.state && this.state.result[0] && this.state.result[0].deviceWakeupTime,
                        timezone: this.state && this.state.result[0] && this.state.result[0].deviceTimeZone,
                        data_sample_count: this.state && this.state.result[0] && this.state.result[0].deviceDataSampleCount,
                        liter_per_pulse: this.state && this.state.result[0] && this.state.result[0].deviceLiterPerPulse,
                        datetime: moment(this.state && this.state.result[0] && this.state.result[0].deviceDateTime).format(),
                        application_of_amr: this.state && this.state.result[0] && this.state.result[0].deviceApplicationOfAmr,
                        type: this.state && this.state.result[0] && this.state.result[0].deviceType,
                        dimeter_size: this.state && this.state.result[0] && this.state.result[0].deviceDiameterSize,
                        state: this.state && this.state.result[0] && this.state.result[0].deviceState,
                        city: this.state && this.state.result[0] && this.state.result[0].deviceCity,
                        area: this.state && this.state.result[0] && this.state.result[0].deviceArea,
                        deviceCustomerName: this.state && this.state.result[0] && this.state.result[0].deviceCustomerName,
                        deviceCustomerAddress: this.state && this.state.result[0] && this.state.result[0].deviceCustomerAddress,
                        deviceMeterLocation: this.state && this.state.result[0] && this.state.result[0].deviceMeterLocation,
                        deviceLitPerPrice: this.state && this.state.result[0] && this.state.result[0].deviceLitPerPrice,
                        Zone: this.state && this.state.result[0] && this.state.result[0].deviceZone,
                        User: this.state && this.state.result[0] && this.state.result[0].deviceUser,
                        deviceAxisEnable: this.state && this.state.result[0] && this.state.result[0].deviceAxisEnable === 0 ? false : true,
                        deviceFkUserId:this.state && this.state.result[0] && this.state.result[0].fkUserId,
                        Admin:this.state && this.state.result[0] && this.state.result[0].deviceAdmin,
                        deviceFkAdminId:this.state && this.state.result[0] && this.state.result[0].fkAdminId,
                        deviceBuildingNameOrWingName:this.state && this.state.result[0] && this.state.result[0].deviceBuildingNameOrWingName,
                        deviceTypeAmrOrBle:this.state && this.state.result[0] && this.state.result[0].deviceTypeAmrOrBle,
                        deviceMeterStartReading:this.state && this.state.result[0] && this.state.result[0].deviceMeterStartReading
                    }}
                    
                    validationSchema={SignupSchema}
                    onSubmit={async values => {
                        // const resultDate = moment(values.datetime).format("YYYY-MM-DDTHH:mm:ss.SSS[Z]");
                        const resultDate = moment()
                            .tz(this.state.timezone)
                            .format("YYYY-MM-DDTHH:mm:ss.SSS[Z]")
                        const wakeTime = values.wakeupTime + ":00";
                        let UserData=this.state.allDataUser;
                        // console.log('UserData',UserData);
                        // console.log(values.User)
                         var selectUserId = UserData.filter(UserId => UserId.value == values.User);
                         var UpdateUserId = selectUserId[0].UserId;
                        //  console.log('UpdateUserId',UpdateUserId);

                         let AdminData=this.state.allDataAdmin;
                        // console.log('AdminData',AdminData);
                        // console.log(values.Admin)
                         var selectAdminId = AdminData.filter(AdminId => AdminId.value == values.Admin);
                         var UpdateAdminId = selectAdminId[0].AdminId;
                        //  console.log('UpdateUserId',UpdateAdminId);

                        const payload = {
                            deviceAmrEnable: values.amr_enable === true ? 1 : 0,
                            pkDeviceDetails: this.state.id,
                            deviceAmrId: values.amr_id,
                            deviceApplicationOfAmr: values.application_of_amr,
                            deviceArea: values.area,
                            deviceAxisEnable: values.deviceAxisEnable === true ? 1 : 0,
                            deviceCity: values.city,
                            deviceDataSampleCount: values.data_sample_count,
                            deviceDateTime: resultDate,
                            deviceDiameterSize: values.dimeter_size,
                            deviceImei: values.imei,
                            deviceImsi: values.imsi,
                            deviceLiterPerPulse: values.liter_per_pulse,
                            deviceSim: values.sim,
                            deviceState: values.state,
                            deviceTimeZone: this.state.timezone,
                            deviceType: values.type,
                            deviceCustomerAddress: values.deviceCustomerAddress,
                            deviceCustomerName: values.deviceCustomerName,
                            deviceBuildingNameOrWingName:values.deviceBuildingNameOrWingName,
                            deviceMeterLocation: values.deviceMeterLocation,
                            deviceLitPerPrice: values.deviceLitPerPrice,
                            deviceUser: values.User,
                            deviceWakeupTime: wakeTime,
                            deviceZone: values.Zone,
                            deviceFkUserId:parseInt(UpdateUserId),
                            deviceAdmin:values.Admin,
                            deviceFkAdminId:parseInt(UpdateAdminId),
                            deviceTypeAmrOrBle:values.deviceTypeAmrOrBle,
                            deviceMeterStartReading:values.deviceMeterStartReading
                        }

                        console.log("Updated data------>", payload)
                        let result = await putDevice(payload)
                        console.log('payload',payload)
                        if (result.status === 200) {
                            swal("Great", "Data added successfully", "success");
                            
                            this.props.history.push("/device")
                        } else {
                            swal("Failed", "Something went wrong please check your internet", "error");
                        }

                    }}
                >
                    {({ errors, touched }) => (
                        <Form>
                        <div className="Devicestyle">
                            <div className="Device__form__wrapper">
                                <div>
                                    <label>Enter IMEI</label>
                                    <Field className="form-control" name="imei" placeholder="imei" />
                                    {errors.imei && touched.imei ? (
                                        <div className="error__msg">{errors.imei}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label>Enter Amr Id</label>
                                    <Field className="form-control" name="amr_id" placeholder="amr id" />
                                    {errors.amr_id && touched.amr_id ? (
                                        <div className="error__msg">{errors.amr_id}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label>Enter sim</label>
                                    <Field className="form-control" name="sim" placeholder="SIM" />
                                    {errors.sim && touched.sim ? (
                                        <div className="error__msg">{errors.sim}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label>Enter Imsi</label>
                                    <Field className="form-control" name="imsi" placeholder="imsi" />
                                    {errors.imsi && touched.imsi ? (
                                        <div className="error__msg">{errors.imsi}</div>
                                    ) : null}
                                </div>
                                </div>
                                </div>
                                <div className="Devicestyle">
                                <label className="DeviceLabel">Device Enable</label>
                                <div className="Device__form__wrapper">
                                <div>
                                    <Field name="amr_enable" type="checkbox" />AMR Enable
                                </div>
                                <div>
                                    <Field name="deviceAxisEnable" type="checkbox" />Tamper Enable
                                </div>
                                </div>
                                </div>
                                <div className="Devicestyle">
                                <label className="DeviceLabel">Device Configuration</label>
                                <div className="Device__form__wrapper">
                                <div>
                                    <label>Wakeup time</label>
                                    <Field className="form-control" type="time" name="wakeupTime" />
                                </div>
                                <div>
                                    <label>Timezone</label> 
                                    {/* <Field className="form-control" name="timezone" readonly="true" /> */}
                                    <select
                                        className="form-control"
                                        onChange={this.handleTimezoneChange}
                                        value={this.state.timezone}
                                       > 
                                     
                                        {timezones.map((zone, i) => (
                                            <option key={i}>{zone}</option>
                                        ))}
                                    </select>
                                   
                                     {moment()
                                        .tz(this.state.timezone)
                                        .format("YYYY-MM-DDTHH:mm:ss.SSS[Z]")}
                                </div>
                                <div>
                                    <label>Data sample count up to 48 (Only Even No) </label>
                                    <Field className="form-control" name="data_sample_count" />
                                </div>
                                <div>
                                    <label>Liter Per Pulse</label>
                                    <Field className="form-control" name="liter_per_pulse" />
                                    {errors.liter_per_pulse && touched.liter_per_pulse ? (
                                        <div className="error__msg">{errors.liter_per_pulse}</div>
                                    ) : null}
                                </div>
                                <div/>
                                </div>
                                </div>
                                <div className="Devicestyle">
                                <label className="DeviceLabel">Device Information</label>
                                <div className="Device__form__wrapper">
                                
                                {/* <div>
                                    <label>Date And Time</label>
                                    <Field className="form-control" name="datetime" readonly="true" />
                                </div> */}
                                
                                <div>
                                    <label>Application of Amr</label>
                                    <Field as="select" className="form-control" name="application_of_amr" >
                                        <option value="">Select option</option>
                                        <option value="Domestic">Domestic</option>
                                        <option value="Commercial">Commercial</option>
                                        <option value="STP">STP</option>
                                        <option value="ETP">ETP</option>
                                    </Field>
                                </div>

                                <div>
                                    <label>Type</label>
                                    <Field as="select" className="form-control" name="type" >
                                        <option value="">Select option</option>
                                        <option value="Volumetric">Volumetric</option>
                                        <option value="Multijet">Multijet</option>
                                        <option value="Singlejet">Singlejet</option>
                                        <option value="Bulk">Bulk</option>
                                        <option value="Electromagnetic">Electromagnetic</option>
                                        <option value="Ultrasonic">Ultrasonic</option>
                                    </Field>
                                </div>
                                <div>
                                    <label>Diameter size</label>
                                    <Field as="select" className="form-control" name="dimeter_size" >
                                        <option value="">Select option</option>
                                        <option value="15">15</option>
                                        <option value="20">20</option>
                                        <option value="25">25</option>
                                        <option value="50">50</option>
                                        <option value="65">65</option>
                                        <option value="80">80</option>
                                        <option value="100">100</option>
                                        <option value="150">150</option>
                                        <option value="250">250</option>
                                        <option value="300">300</option>

                                    </Field>
                                </div>
                                <div>
                                    <label>Building Name Or Wing Name</label>
                                    <Field className="form-control" name="deviceBuildingNameOrWingName" />
                                </div>
                                <div>
                                    <label>State</label>
                                    <Field className="form-control" name="state" />
                                </div>

                                <div>
                                    <label>City</label>
                                    <Field className="form-control" name="city" />
                                </div>

                                <div>
                                    <label>Device Customer address</label>
                                    <Field className="form-control" component="textarea" name="deviceCustomerAddress" />
                                </div>
                                <div>
                                    <label>Device Customer Name</label>
                                    <Field className="form-control" name="deviceCustomerName" />
                                </div>
                                <div>
                                    <label>Device Meter location</label>
                                    <Field className="form-control" name="deviceMeterLocation" />
                                </div>
                                <div>
                                    <label>Price/Ltr</label>
                                    <Field className="form-control" name="deviceLitPerPrice" />
                                </div>
                                <div>
                                    <label>Area</label>
                                    <Field className="form-control" name="area" />
                                </div>
                                <div>
                                    <label>Zone</label>
                                    <Field className="form-control" name="Zone" />
                                </div>
                                <div>
                                    <label>Meter Start Reading</label>
                                    <Field className="form-control" name="deviceMeterStartReading" />
                                </div>
                                <div>
                                <label className="userLabel">Device Technology Type</label>
                                    <span className="mustRequired">*</span>
                                    <Field as="select" className="form-control" name="deviceTypeAmrOrBle">
                                        <option>Select Device Technology</option>
                                        <option value="amr">AMR</option>
                                        <option value="ble">BLE</option>
                                        <option value="lorawan">LoRaWan</option>
                                        
                                    </Field>
                                    {errors.deviceTypeAmrOrBle && touched.deviceTypeAmrOrBle ? <div className="error__msg">{errors.deviceTypeAmrOrBle}</div> : null}
                                </div>
                                <div></div>
                             </div>
                                </div>
                                <div className="Devicestyle">
                                <label className="DeviceLabel">Device Allocation</label>
                                <div className="Device__form__wrapper">
                                
                                <div>
                                    <label>User</label>
                                    <Field as="select" className="form-control" name="User" >
                                        {this.state && this.state.allDataUser && this.state.allDataUser.map((obj) => (

                                            <option  value={`${obj.value}`}>{obj.value}</option>
                                        ))}
                                    </Field>
                                </div>
                                <div>
                                    <label>Admin</label>
                                    <Field as="select" className="form-control" name="Admin" >
                                        {this.state && this.state.allDataAdmin && this.state.allDataAdmin.map((obj) => (

                                            <option value={`${obj.value}`}>{obj.value}</option>
                                        ))}
                                    </Field>
                                </div>
                            </div>
                            </div>
                            <br />
                            <button type="submit" className="btn btn-primary">Submit</button>
                        </Form>
                    )}
                </Formik>
            </div>
            }
            </>
        )
    }
}
