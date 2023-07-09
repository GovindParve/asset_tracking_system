import React, { Component } from 'react'
import './Device.css'
import { deleteDevice } from "../../Service/deleteDevice"
import { getDevices } from '../../Service/getDevices'


export default class Devices extends Component {
    
    state = {
        allData: [],
        checkedStatus: [],
        checkedValue: [],
        selectedDevice: "",
        Role:"",
        loader:false,
    }
    async componentDidMount() {
        try{
            this.setState({loader:true})
            let result = await getDevices()
            var setRole =await localStorage.getItem('role');
            console.log(result)
            this.setState({ allData: result && result.data,Role:setRole,loader:false },()=>{
                console.log('allData',this.state.allData)
               let temp = []
                this.state.allData.map((obj, key) => {
                    const objTemp = { [key]: false }
                    temp.push(objTemp)
                })
                this.setState({ checkedStatus: temp })
                
            })
        }catch (error){
            console.log(error)
            this.setState({loader:false})
        }
        
    }


    redirectToCreateUser = () => {
        this.props.history.push("/device-create")
    }

    clickCard = async (id) => {
            this.props.history.push({
                pathname: `/update-device/${id}`,
                state: { deviceId: id },
            });
        

    }
    changeStatus = (value, e) => {
        console.log(value, e.target.value)
        let checkedValueTemp = this.state.checkedValue
        
        if (e.target.checked) {
            checkedValueTemp.push(parseInt(e.target.value))
            this.setState({ checkedValue: checkedValueTemp })
            
        } else {
const filteredItems = checkedValueTemp.filter(item => item !== parseInt(e.target.value));
            console.log('filteredItems',filteredItems)
            checkedValueTemp=filteredItems;
            this.setState({ checkedValue: checkedValueTemp });
            
        }

        let temp = this.state.checkedStatus

        temp[value][`${value}`] = !temp[value][`${value}`]

    }

    allCheckUncheked = (e) => {
        let temp = [];
        let checkedArray = []

        if (e.target.checked) {
            console.log('value',e.target.checked);
            this.state.allData.map((obj, key) => {
                const objTemp = { [key]: true }
                temp.push(objTemp)
                checkedArray.push(parseInt(obj.pkDeviceDetails))
                
            })

            this.setState({ checkedStatus: temp, checkedValue: checkedArray })
            
        } else {
            let temp = []
            this.state.allData.map((obj, key) => {
                const objTemp = { [key]: false }
                temp.push(objTemp)
            })
            this.setState({ checkedStatus: temp, checkedValue: [] })
            
        }
        
    }
    
    deleteDevice = async () => {
        console.log('delete',this.state.checkedValue)
        try {
            let result = await deleteDevice(this.state.checkedValue)
            
            if (result.status === 200) {
                alert("paylaod deleted")
                window.location.reload();
            } else {
                alert("something went wrong please check your connection")
            }
        } catch (error) {
            console.log(error)
        }
    }
    render() {
        const { allData,loader} = this.state
        
        return (
            <>
            {loader?<div class="loader"></div>:
            <div>
            {(this.state.Role==='admin') ? (<div className="device__btn__wrapper" style={{display:'none'}}>
                    <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create</button>&nbsp;&nbsp;
                </div>)
            : this.state.Role==='user'?(<div className="device__btn__wrapper" style={{display:'none'}}>
                    <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create</button>&nbsp;&nbsp;
                </div>)
            :(<div className="device__btn__wrapper">
                    <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create</button>&nbsp;&nbsp;
                </div>)}
                


                <div className="payload__btn__wrapper">
                    <div>
                        {/* <select onChange={this.changeDevice}>
                            <option>Select device</option>
                            {this.state && this.state.amrList && this.state.amrList.map((obj, key) => (
                                <option key={key} >{obj}</option>
                            ))}
                        </select> */}
                        &nbsp;&nbsp;&nbsp;
                        {this.state.checkedValue.length === 0 ?
                            "" :
                            <button className="btn btn-danger" onClick={this.deleteDevice}>Delete</button>}

                    </div>
                </div>
                <br />
                <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                    <table className="table table__scroll table-hover table-bordered table-striped text-center">
                    
                    <tr>
                    {(this.state.Role==='admin') ? <><th style={{display:'none'}}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                        <th style={{display:'none'}}>EDIT</th> </>:(this.state.Role==='user') ?<> <th style={{display:'none'}}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                        <th style={{display:'none'}}>EDIT</th> </>:<><th><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                        <th>EDIT</th></>}
                   
                            <th>Sr no</th>
                            {(this.state.Role==='admin') ? <><th style={{display:'none'}}>IMEI</th>
                        <th style={{display:'none'}}>SIM</th> <th>AMR</th> <th style={{display:'none'}}>IMSI</th> </>:(this.state.Role==='user') ?<> <th style={{display:'none'}}>IMEI</th>
                        <th style={{display:'none'}}>SIM</th> <th>AMR</th> <th style={{display:'none'}}>IMSI</th></>:<><th>IMEI</th>
                        <th>SIM</th> <th>AMR</th> <th>IMSI</th></>}
                            
                            <th>device Amr</th>
                            <th>wake up time</th>
                            <th>Timezone</th>
                            <th>Sample count</th>
                            <th>Liter per pulse</th>
                            <th>Date time</th>
                            <th>Application Of Amr</th>
                            <th>Type</th>
                            <th>Diameter</th>
                            <th>State</th>
                            <th>City</th>
                            <th>Area</th>
                            <th>Zone</th>
                            <th>Meter Start Reading</th>
                            <th>Device Type</th>
                            <th>User</th>
                            <th>Admin</th>
                           
                        </tr>
                        <tbody>
                        
                            {allData.map((obj, key) => (
                               <>
                               
                                <tr className="Device__table__col">
                               
                                {(this.state.Role==='admin') ? <> <th style={{display:'none'}}><div key={key}>
                                    <input type="checkbox" value={obj.pkDeviceDetails} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                    </div></th>
                                    <td  style={{display:'none'}} onClick={() => this.clickCard(obj.pkDeviceDetails)}><button className="btn btn-info">EDIT</button></td></>
                                :(this.state.Role==='user') ?<><th style={{display:'none'}}><div key={key}>
                                    <input type="checkbox" value={obj.pkDeviceDetails} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                    </div></th>
                                    <td  style={{display:'none'}} onClick={() => this.clickCard(obj.pkDeviceDetails)}><button className="btn btn-info">EDIT</button></td></>
                                :<>
                                <th><div key={key}>
                                    <input type="checkbox" value={obj.pkDeviceDetails} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                    </div></th>
                                    <td  onClick={() => this.clickCard(obj.pkDeviceDetails)}><button className="btn btn-info">EDIT</button></td>
                                </>}
                               
                                    <td>
                                        {key + 1}
                                    </td>
                                    {(this.state.Role==='admin') ? <><td style={{display:'none'}}>{obj.deviceImei}</td>
                        <td style={{display:'none'}}> {obj.deviceSim}</td> <td>{obj.deviceAmrId}</td> <td style={{display:'none'}}> {obj.deviceImsi}</td> </>:(this.state.Role==='user') ?<> <td style={{display:'none'}}>{obj.deviceImei}</td>
                        <td style={{display:'none'}}> {obj.deviceSim}</td> <td>{obj.deviceAmrId}</td> <td style={{display:'none'}}> {obj.deviceImsi}</td></>:<><td>{obj.deviceImei}</td>
                        <td> {obj.deviceSim}</td> <td>{obj.deviceAmrId}</td> <td> {obj.deviceImsi}</td></>}
                                   
                                    <td>
                                        {obj.deviceAmrEnable}
                                    </td>
                                    <td>
                                        {obj.deviceWakeupTime}
                                    </td>
                                    <td>
                                        {obj.deviceTimeZone}
                                    </td>
                                    <td>
                                        {obj.deviceDataSampleCount}
                                    </td>
                                    <td>
                                        {obj.deviceLiterPerPulse}
                                    </td>
                                    <td>
                                        {obj.deviceDateTime}
                                    </td>
                                    <td>
                                        {obj.deviceApplicationOfAmr}
                                    </td>
                                    <td>
                                        {obj.deviceType}
                                    </td>
                                    <td>
                                        {obj.deviceDiameterSize}
                                    </td>
                                    <td>
                                        {obj.deviceState}
                                    </td>
                                    <td>
                                        {obj.deviceCity}
                                    </td>
                                    <td>
                                        {obj.deviceArea}
                                    </td>
                                    <td>
                                        {obj.deviceZone}
                                    </td>
                                    <td>
                                        {obj.deviceMeterStartReading}
                                    </td>
                                    <td>
                                        {obj.deviceTypeAmrOrBle}
                                    </td>
                                    <td>
                                        {obj.deviceUser}
                                    </td>
                                    <td>
                                        {obj.deviceAdmin}
                                    </td>
                                </tr>
                                </>
                            ))}


                        </tbody>
                    </table >
                </div>

            </div>
        }
            </>
        )
    }
}
