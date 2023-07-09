import React, { Component } from 'react'

import './BatteryPercentage.css'
import { getTagWiseBatteryPercentFilter } from '../../Service/getTagWiseBatteryPercentFilter'
import { getTrackData } from "../../Service/getTrackData"
import { getTagWiseWithoutPagination } from "../../Service/getTagWiseWithoutPagination"
import Select from "react-select";
import moment from "moment";

export default class BatteryPercentage extends Component {
    state = {
        result: [],
        id: 0,
        loader: false,
        uniqueDate: [],
        newlist: [],
        tableData: [],
        TagData: [],
        allshow: false,
        selectedDevice: "",
        validateDate: '',
        location: ''
    }
   
    componentDidMount = async () => {
    try {
        this.setState({ loader: true })
        if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
            const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;
            // let result = await getTagWiseWithoutPagination(propId)
            // console.log('TagWisedata',result)
            let result = await getTagWiseBatteryPercentFilter(propId)
            console.log('TagWise data',result.data)    
            this.setState({TagData: result && result.data, id: propId, loader: false },() => {
                console.log('Tag data', this.state.TagData)
            })            
        }
           
    } catch (error) {
        console.log(error)
        this.setState({ loader: false })
    }
}

    render() {
        const {TagData} = this.state
        return (
            <>
                <div>                 
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm">
                        <table className="table table-hover table-bordered table-striped text-center">
                            <tr className="tablerow">
                                <th>Sr_No</th>
                                <th>Asset_Tag_Name</th>
                                <th>Asset_Gatway_Name</th>
                                <th>Date</th>
                                <th>Asset_Tag_Entry_Location</th>
                                <th>Entry_Time</th>
                                <th>Asset_Tag_Last_Location</th>
                                <th>Exit_Time</th>                            
                                <th>Battery %</th>
                            </tr>
                            <tbody>
                                {TagData.map((obj, key) => (
                                    <>
                                        <tr key={key} className={obj.prob_tamp || obj.mag_tamp || obj.axis_tamp === 1 ? "" : ""}>
                                            <td>
                                                {key + 1}
                                            </td>
                                            <td>
                                                {obj.assetTagName}
                                            </td>
                                            <td>
                                                {obj.assetGatewayName}
                                            </td>
                                            <td>
                                                {moment(obj.date).format('DD/MM/YYYY')}
                                            </td>
                                            <td>
                                                {obj.tagEntryLocation}
                                            </td>
                                            <td>
                                                {obj.entryTime !== null ? moment(obj.entryTime, 'hh:mm A').format('hh:mm A') : '-'}
                                            </td>
                                            <td>
                                                {obj.tagExistLocation !== null ? obj.tagExistLocation : '-'}
                                            </td>
                                            <td>
                                                {obj.existTime !== null ? moment(obj.entryTime, 'hh:mm A').format('hh:mm A') : '-'}
                                            </td>                                       
                                            <td className={obj.battryPercentage <= '20' ? "colorRed" : "colorBlue"}>
                                                {obj.battryPercentage}
                                            </td>
                                        </tr>
                                    </>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </>
        )
    }
}
