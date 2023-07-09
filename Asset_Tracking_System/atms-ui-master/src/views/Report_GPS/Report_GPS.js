import React, { Component } from 'react'
import './Report_GPS.css'
import moment from "moment";
import ReactPaginate from 'react-paginate';
import { Tabs, Tab } from 'react-bootstrap'
import axios from '../../utils/axiosInstance'

import * as Yup from 'yup';
import { listAssetTagforTrack } from "../../Service/listAssetTagforTrack"
import { listGateway } from "../../Service/listGateway"


import { allGatewayWiseGraphData } from "../../Service/allGatewayWiseGraphData"

import swal from "sweetalert";
import { Field } from 'formik';
import Gps_ReportSuperadmin from './Gps_ReportSuperadmin';
import Gps_ReportAdmin from './Gps_ReportAdmin';
import Gps_ReportOrganization from './Gps_ReportOrganization';
import Gps_ReportUser from './Gps_ReportUser';


import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
//<script src="//code.tidio.co/o8rk9uytps64ic5pp82bbu14pv3o1qi5.js" async></script>

const SignupSchema = Yup.object().shape({
    type_of: Yup.string().required('Required'),
});

export default class Report_GPS extends Component {
    state = {
        offset: 0,
        data: [],
        perPage: 10,
        currentPage: 0,
        show: true,
        tableData: [],
        tableTagData: [],
        tableGatewayData: [],
        tableDateData: [],
        orgtableData: [],
        Role: "",
        loader: false,
        startDate: "",
        endDate: "",
        startDateReport: "",
        endDateReport: "",
        startTime: "",
        endTime: "",
        fileType: "",
        durationList: [],
        tagList: [],
        gatewayList: [],
        allData: [],
        allTime: [],
        allColumnList: [],
        allParameter: [],
        tagName: [],
        gatewayName: [],
        newCount: [],
        agedCount: [],
        graph: [],
        Taggraph: [],
        allColumn: [],
        selectedDevice: "",
        selectedColumn: "",
        selectedTagDevice: "",
        selectedGateDevice: "",
        selectedGateway: "",
        selectedAssetTag: "",
        selectDuration: "",
        selectGatewayDuration: "",
        AssetTag: "",
        AssetGateway: "",
        selectedGatewayDevice: "",
        AssetGatway: "",
        selectedTag: "",
        gatewayTagList: [],
        newageGraphdata: [],
        newageGatewayGraphdata: [],
        isOpen: false
    }

    async componentDidMount() {
        try {
            this.setState({ loader: true })


            let [resultAssetTagList, resultAssetGatewayList, setRole] = await Promise.all([listAssetTagforTrack(), listGateway(), localStorage.getItem('role')]);

            let tempTagArray = []
            if (resultAssetTagList && resultAssetTagList.data && resultAssetTagList.data.length != 0) {
                resultAssetTagList.data.map((obj) => {
                    let tempTagObj = {
                        value: obj,
                        label: obj
                    }
                    tempTagArray.push(tempTagObj)
                })
            }
            let tempGatewayArray = []
            if (resultAssetGatewayList && resultAssetGatewayList.data && resultAssetGatewayList.data.length != 0) {
                resultAssetGatewayList.data.map((obj) => {
                    let tempGatewayObj = {
                        value: obj,
                        label: obj
                    }
                    tempGatewayArray.push(tempGatewayObj)
                })
            }

            this.setState({ tagList: tempTagArray, Role: setRole, loader: false }, () => {
                console.log('Tag List', this.state.tagList)
            })
            this.setState({ gatewayList: tempGatewayArray, Role: setRole, loader: false }, () => {
                console.log('Gateway List', this.state.gatewayList)
            })

            let data = await allGatewayWiseGraphData();
            console.log('gatewayGraphData', data.data)
            this.setState({ newageGraphdata: data.data }, () => {
                //  console.log('newageGraphdata', this.state && this.state.newageGraphdata[0] && this.state.newageGraphdata[0])
            })
            this.setState({ gatewayName: this.state.newageGraphdata[0].gatewayNames, newCount: this.state.newageGraphdata[0].newTagCount, agedCount: this.state.newageGraphdata[0].agedTagCount })
            console.log('Gateway Graph Data', this.state.newageGraphdata, this.state.gatewayName, this.state.newCount, this.state.agedCount)

        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }


    }



    render() {
        // const { allTime, allData, tableData, tableGatewayData, tableTagData, tableDateData, allColumn, allParameter } = this.state
        // const { data } = this.state
        return (
            <>
                <div className="tab-wrapper">
                    <div className='' >
                        <div className="row">
                            <div className="col-sm-12">
                                {(this.state.Role === 'admin') ?
                                    (

                                        <Gps_ReportAdmin />

                                    ) : this.state.Role === "organization" ?
                                        (

                                            <Gps_ReportOrganization />

                                        )
                                        : this.state.Role === 'user' ? (

                                            <Gps_ReportUser />

                                        )
                                            //Working
                                            : (

                                                <Gps_ReportSuperadmin />

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
