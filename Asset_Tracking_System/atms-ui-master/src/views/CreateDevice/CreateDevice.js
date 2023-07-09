import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import moment from "moment";
import './CreateDevice.css'
import { getDeviceUser } from '../../Service/getDeviceUser'
import { getAdmin } from '../../Service/getAdmin'
import Select from "react-select";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';


import "moment-timezone";

import { postDevice } from '../../Service/postDevice'

const SignupSchema = Yup.object().shape({
    imei: Yup.string()
        .required('Required'),
    amr_id: Yup.string()
        .required('Required'),
    imsi: Yup.string()
        .required('Required'),
    liter_per_pulse: Yup.string()
         .required('Required'),
    devicetype: Yup.string()
         .required('Required'),

});

const timezones = timezoneNames()
    .filter((l) => l.indexOf("/") > -1)
    .sort((a, b) => a.localeCompare(b));
export default class CreateDevice extends Component {

    // interval;
    state = {
        timezone: "Asia/Kolkata",
        date: null,
        result: null,
        allData: [],
        allAdminData: [],
        selectedUser: "",
        selectedUserId: "",
        selectedAdmin: "",
        selectedAdminId: "",
    }
    async componentDidMount() {
        let result = await getDeviceUser();
        console.log('result', result);
        // this.tick();
        // this.interval = setInterval(() => this.tick(), 1000);
        let tempArray = []
        result && result.data && result.data.map((obj) => {
            console.log('objresult', obj);
            let tempObj = {
                id: `${obj.fkUserId}`,
                value: `${obj.firstname + " " + obj.lastname}`,
                label: `${obj.firstname + " " + obj.lastname}`
            }
            console.log('tempObj', tempObj);

            tempArray.push(tempObj);
        })

        this.setState({ allData: tempArray });

        let resultAdmin = await getAdmin();

        let tempAdminArray = []
        resultAdmin && resultAdmin.data && resultAdmin.data.map((obj) => {
            console.log('resultAdmin', obj);
            let tempadminObj = {
                id: `${obj.fkAdminId}`,
                value: `${obj.firstname + " " + obj.lastname}`,
                label: `${obj.firstname + " " + obj.lastname}`
            }

            tempAdminArray.push(tempadminObj)
        })

        this.setState({ allAdminData: tempAdminArray })
    }


    // componentWillUnmount() {
    //     clearInterval(this.interval);
    // }

    // tick = () => {
    //     const date = new Date();
    //     const result = ZonedDate.fromLocalDate(date, this.state.timezone);
    //     this.setState({ date, result });
    // };

    handleTimezoneChange = (event) => {
        this.setState({ timezone: event.target.value });
    };

    changeUser = (selectedUser) => {

        this.setState({ selectedUser: selectedUser.value, selectedUserId: selectedUser.id },()=>{
            console.log('fkuserid',this.state.selectedUser)
            console.log('selectedUserId',this.state.selectedUserId)
        })
    }
    changeAdmin = (selectedAdmin) => {
        this.setState({ selectedAdmin: selectedAdmin.value, selectedAdminId: selectedAdmin.id })
    }

    render() {

        const { result, date } = this.state;
        return (
            <div className="userAdd__wrapper">

                <h3>Create Devices</h3>
                <div >
                    <b>Device metadata</b>
                </div>
                <br />
                <Formik
                    initialValues={{
                        imei: '',
                        amr_id: '',
                        sim: '',
                        imsi: '',
                        amr_enable: '',
                        wakeupTime: '',
                        timezone: '',
                        data_sample_count: '',
                        liter_per_pulse: '',
                        datetime: '',
                        application_of_amr: '',
                        type: '',
                        dimeter_size: '',
                        state: '',
                        city: '',
                        area: '',
                        deviceCustomerName: '',
                        deviceCustomerAddress: '',
                        deviceMeterLocation: '',
                        deviceLitPerPrice: '',
                        Zone: '',
                        deviceBuildingNameOrWingName: '',
                        User: '',
                        deviceAxisEnable: false,
                        deviceFkUserId: '',
                        deviceAdmin: '',
                        deviceFkAdminId: '',
                        deviceTypeAmrOrBle:'',
                        deviceMeterStartReading:''

                    }}
                    validationSchema={SignupSchema}
                    onSubmit={async values => {
                        const resultDate = moment()
                            .tz(this.state.timezone)
                            .format()
                        // const tempVal = resultDate.tz(this.state.timezone)
                        // const wakeTime = moment(values.wakeupTime).format("HH:mm")

                        const payload = {
                            deviceAmrEnable: values.amr_enable === true ? 1 : 0,
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
                            deviceMeterLocation: values.deviceMeterLocation,
                            deviceLitPerPrice: values.deviceLitPerPrice,
                            deviceUser: this.state.selectedUser,
                            deviceWakeupTime: values.wakeupTime + ":00",
                            deviceZone: values.Zone,
                            deviceBuildingNameOrWingName: values.deviceBuildingNameOrWingName,
                            deviceFkUserId:parseInt(this.state.selectedUserId),
                            deviceAdmin:this.state.selectedAdmin,
                            deviceFkAdminId:parseInt(this.state.selectedAdminId),
                            deviceTypeAmrOrBle:values.devicetype,
                            deviceMeterStartReading:parseFloat(values.MeterStartReading)
                        }
                        console.log('payload', payload);


                        let result = await postDevice(payload)
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
                                    <Field className="form-control" name="imei" placeholder="IMEI" />
                                    {errors.imei && touched.imei ? (
                                        <div className="error__msg">{errors.imei}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label>Enter Amr Id</label>
                                    <Field className="form-control" name="amr_id" placeholder="Amr Id" />
                                    {errors.amr_id && touched.amr_id ? (
                                        <div className="error__msg">{errors.amr_id}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label>Enter SIM</label>
                                    <Field className="form-control" name="sim" placeholder="SIM" />
                                    {errors.sim && touched.sim ? (
                                        <div className="error__msg">{errors.sim}</div>
                                    ) : null}
                                </div>
                                <div>
                                    <label>Enter IMSI</label>
                                    <Field className="form-control" name="imsi" placeholder="IMSI" />
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
                                    <Field name="amr_enable" type="checkbox" /><label> &nbsp;&nbsp;AMR Enable</label>
                                </div>
                                <div>
                                    <Field name="deviceAxisEnable" type="checkbox" /><label> &nbsp;&nbsp;Tamper Enable</label>
                                </div>
                                </div>
                                </div>
                                <div className="Devicestyle">
                                <label className="DeviceLabel">Device Configuration</label>
                                <div className="Device__form__wrapper">
                                <div>
                                    <label>Wakeup Time</label>
                                    <Field className="form-control" type="time" name="wakeupTime" />

                                </div>
                                <div>
                                    <label>Timezone</label>
                                    {/* <Field className="form-control" name="timezone" placeholder="TimeZone" /> */}
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
                                        .format()}
                                </div>
                                <div>
                                    <label>Data Sample Count</label>
                                    <Field className="form-control" name="data_sample_count" placeholder="Data Sample Count" />
                                </div>
                                <div>
                                    <label>Liter Per Pulse</label>
                                    <Field className="form-control" name="liter_per_pulse" placeholder="Liter Per Pulse" />
                                    {errors.liter_per_pulse && touched.liter_per_pulse ? (
                                        <div className="error__msg">{errors.liter_per_pulse}</div>
                                    ) : null}
                                </div>
                                </div>
                                </div>
                                <div className="Devicestyle">
                                <label className="DeviceLabel">Device Information</label>
                                <div className="Device__form__wrapper">
                                
                                {/* <div>
                                    <label>Datetime</label>
                                    <Field className="form-control" type="datetime-local" name="datetime" placeholder="Datetime" />
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
                                    <label>State</label>
                                    <Field className="form-control" name="state" placeholder="State" />
                                </div>

                                <div>
                                    <label>City</label>
                                    <Field className="form-control" name="city" placeholder="City" />
                                </div>

                                <div>
                                    <label>Device Customer Address</label>
                                    <Field className="form-control" component="textarea" name="deviceCustomerAddress" placeholder="Device Customer Address" />
                                </div>
                                <div>
                                    <label>Device Customer Name</label>
                                    <Field className="form-control" name="deviceCustomerName" placeholder="Device Customer Name" />
                                </div>
                                <div>
                                    <label>Device Meter Location</label>
                                    <Field className="form-control" name="deviceMeterLocation" placeholder="Device Meter Location" />
                                </div>
                                <div>
                                    <label>Building Or Wing</label>
                                    <Field className="form-control" name="deviceBuildingNameOrWingName" placeholder="Building or Wing" />
                                </div>
                                <div>
                                    <label>Price/Ltr</label>
                                    <Field className="form-control" name="deviceLitPerPrice" placeholder="Price/Ltr" />
                                </div>
                                <div>
                                    <label>Area</label>
                                    <Field className="form-control" name="area" placeholder="Area" />
                                </div>
                                <div>
                                    <label>Zone</label>
                                    <Field className="form-control" name="Zone" placeholder="Zone" />
                                </div>
                                <div>
                                    <label>Meter Start Reading</label>
                                    <Field className="form-control" name="MeterStartReading" placeholder="Meter Start Reading" />
                                </div>
                                <div>
                                <label className="userLabel">Device Technology Type</label>
                                    <span className="mustRequired">*</span>
                                    <Field as="select" className="form-control" name="devicetype">
                                        <option>Select Device Technology</option>
                                        <option value="amr">AMR</option>
                                        <option value="ble">BLE</option>
                                        <option value="lorawan">LoRaWan</option>
                                        
                                    </Field>
                                    {errors.devicetype && touched.devicetype ? <div className="error__msg">{errors.devicetype}</div> : null}
                                </div>
                                </div>
                                </div>
                                <div className="Devicestyle">
                                <label className="DeviceLabel">Device Allocation</label>
                                <div className="Device__form__wrapper">
                               
                                <div>
                                    <label>User</label>
                                    <Select options={this.state.allData} onChange={this.changeUser} />
                                </div>
                                <div>
                                    <label>Admin</label>
                                    <Select options={this.state.allAdminData} onChange={this.changeAdmin} />
                                </div>
                            </div>
                            </div>
                            <br />
                            <button type="submit" className="btn btn-primary">Submit</button>
                        </Form>
                    )}
                </Formik>
            </div>
        )
    }
}


