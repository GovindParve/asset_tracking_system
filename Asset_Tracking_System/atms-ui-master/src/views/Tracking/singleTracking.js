import React, { Component } from 'react'
import { getSingleTagTracking } from '../../Service/getSingleTagTracking'
import { getTrackData } from "../../Service/getTrackData"
import { getTrackListByTag } from "../../Service/getTrackListByTag"
import Select from "react-select";
import moment from "moment";
import axios from '../../utils/axiosInstance'
import fileSaver from "file-saver"
import "./mainTracking.css"
//import { Timeline } from 'rsuite';
import GearIcon from '@rsuite/icons/Gear';
//import { Timeline, Grid, Row, Col } from 'rsuite';
import { Timeline, TimelineItem } from 'vertical-timeline-component-for-react';
import { getColumnList } from '../../Service/getColumnList'
import { getAdminColumnList } from '../../Service/getAdminColumnList'


export default class SingleTracking extends Component {
    state = {
        result: [],
        id: 0,
        loader: false,
        uniqueDate: [],
        newlist: [],
        tableData: [],
        allshow: null,
        selectedDevice: "",
        validateDate: '',
        location: '',
        allColumn: [],
        heading: [
            "Entry_Time",
            "Entry_Loc",
            "Batt (%)"
        ],
        //   row: [
        //     "entryTime",
        //     "tagEntryLocation",
        //     "battryPercentage"
        //   ]

    }
    componentDidMount = async () => {
        try {
            this.setState({ loader: true })

            // if (this.state.Role === "super-admin") {
            let resultColumn = await getColumnList();
            console.log("All Column Data", resultColumn.data)
            resultColumn.data.map((obj) => {
                console.log('Column', obj);
                let label = obj.allocatedColumn_db + " (" + obj.unit + ")";
                console.log("label", label)
                this.state.heading.push(label)
                this.state.allColumn.push(obj.columnName)
                // this.state.heading.push(obj.allocatedColumn_db)
                // this.state.allColumn.push(obj.columnName)
            })
            console.log('Column', this.state.allColumn)
            console.log('Column Heading', this.state.heading)
            // }
            //   else if (this.state.Role === "admin") {
            //     let result = await getAdminColumnList();
            //     console.log("All Column Data", result.data)
            //     result.data.map((obj) => {
            //       console.log('Column', obj);
            //       this.state.heading.push(obj.columns)
            //     })
            //     console.log('Column', this.state.heading)
            //   }
            if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
                const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;

                let result = await getSingleTagTracking(propId)

                console.log('Single tracking result', result.data)

                this.setState({ result: result && result.data, id: propId, loader: false },
                    () => {
                        console.log('data', this.state.result[0].assetTagName)
                    })

                let dateTimeLoc = new Map();
                result.data.map(item => {
                    let dynamic = this.state.allColumn.map(obj => {
                        //     let tempTagObj = {
                        //         value: item[obj.value],
                        //         label: obj.label
                        //       }
                        //   return tempTagObj;
                        return item[obj]
                    })
                   // console.log('Dynamic Column', dynamic)

                    if (dateTimeLoc.has(moment(item.entryTime).format('DD/MM/YYYY'))) {
                        let pre = dateTimeLoc.get(moment(item.entryTime).format('DD/MM/YYYY')); 
                        dateTimeLoc.set(moment(item.entryTime).format('DD/MM/YYYY'), [...pre, [moment.tz(item.entryTime, 'HH:mm:ss').format('HH:mm:ss'), item.tagEntryLocation, item.battryPercentage, dynamic]]);
                    }
                    else {
                        dateTimeLoc.set(moment(item.entryTime).format('DD/MM/YYYY'), [[moment.tz(item.entryTime, 'HH:mm:ss').format('HH:mm:ss'), item.tagEntryLocation, item.battryPercentage, dynamic]]);
                    }
                });
                let arrayUniqueByKey = [...dateTimeLoc];
                const DateArray = arrayUniqueByKey.map(obj => {
                    return { date: obj[0], value: obj[1] }
                })
                this.setState({ newlist: DateArray });
                console.log('Date wise Data', DateArray)

            } else
                if (this.props.match && this.props.match.params && this.props.match.params.id) {
                    const propParamId = this.props.match && this.props.match.params && this.props.match.params.id;
                    let result = await getSingleTagTracking(propParamId)
                    console.log('result', result)
                    this.setState({ result: result && result.data, id: propParamId, loader: false })

                } else {
                    this.props.history.push("/main-tracking");
                }

        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }

    }
    // changeToggle = e => {
    //     this.setState(e => ({ allshow: !e.allshow }))

    // }
    changeToggle = (id) => () => {
        this.setState(prevState => ({
            allshow: prevState.allshow === id ? null : id,
        }));
    }

    render() {
        const { loader, newlist, heading } = this.state
        return (
            <>
                <Timeline className="userAdd__wrapper" >
                    <h3 className="tagname">{this.state && this.state.result[0] && this.state.result[0].assetTagName} TAG HISTORY </h3>
                    {newlist.map((obj, key) => (
                        <div className="maindate" key={key}>
                            <TimelineItem className="date" id={key} dateText={obj.date}>
                                <div className='location1'><h5>{obj.value[0][1]}</h5></div>
                                <div>
                                    <button id={"toggle-button1"} onClick={this.changeToggle(key)} ><i className="fa fa-folder-open fa-lg" ></i></button> <p class="tooltip-text">Click on File</p>
                                </div>

                                {this.state.allshow === key ?
                                    <div className='container-fuild' >
                                        <div className="target" key={key} >
                                            <table className="table table-hover table-bordered table-striped text-center">
                                                <tr className="tablerowsingle">
                                                    {heading.map((obj) => (
                                                        <th>
                                                            {obj}
                                                        </th>
                                                    ))}
                                                </tr>
                                                {obj.value.map((val) => (
                                                    <tbody>
                                                        <td>
                                                            <div className="time">{val[0]}</div>
                                                        </td>
                                                        <td>
                                                            <div className="location">{val[1]}</div>
                                                        </td>
                                                        <td>
                                                            <div className="battery">{val[2]}</div>
                                                        </td>
                                                        {val[3].map((obj1) => (
                                                                <td>
                                                                    <div className="column">
                                                                        {obj1}
                                                                    </div>
                                                                </td>
                                                            )
                                                            )
                                                            // ))
                                                        }
                                                    </tbody>
                                                )
                                                )
                                                }
                                            </table>
                                        </div>
                                    </div>
                                    : ""
                                }
                                {this.state.selectedDevice}
                            </TimelineItem>
                        </div>
                    ))}
                    {/* </div> */}
                </Timeline>
            </>
        )
    }


}
//ReactDOM.render(<SingleTracking />, document.getElementById("react-div"));