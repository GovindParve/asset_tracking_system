import React, { Component } from 'react'
import './Report_GPS.css'
import moment from "moment";
import ReactPaginate from 'react-paginate';
import { Tabs, Tab } from 'react-bootstrap'
import axios from '../../utils/axiosInstance'
import fileSaver from "file-saver"
import Select from "react-select";
import * as Yup from 'yup';
import { listAssetGpsforTrack } from "../../Service/listAssetGpsforTrack"
import { listGateway } from "../../Service/listGateway"
import { getTrackListByTag } from "../../Service/getTrackListByTag"
import { getGpsTrackData } from "../../Service/getGpsTrackData"
import { getDateWiseGPSTag } from "../../Service/getDateWiseGPSTag"
import { getGatewayGraphData } from "../../Service/getGatewayGraphData"
import { getDateWiseGateway } from "../../Service/getDateWiseGateway"
import { getGPSGatewayReports } from "../../Service/getGPSGatewayReports"
import { getGatewayCount } from "../../Service/getGatewayCount"
import { getViewGatewayReport } from "../../Service/getViewGatewayReport"
import { getDuration } from "../../Service/getDuration"
import { getStayTimeGraph } from "../../Service/getStayTimeGraph"
import { getTagStayTimeGraph } from "../../Service/getTagStayTimeGraph"
import { allGatewayWiseGraphData } from "../../Service/allGatewayWiseGraphData"
import { getSingleGraphData } from "../../Service/getSingleGraphData"
import { getPageWiseGPSTagDuration } from '../../Service/getPageWiseGPSTagDuration'
import { getTimeWise } from '../../Service/getTimeWise'
import { getTimeTag } from '../../Service/getTimeTag'
import { getGPSTrackColumnData } from "../../Service/getGPSTrackColumnData"
import { getAdminDrowpdownList } from '../../Service/getAdminDrowpdownList'

import { getParameterList } from '../../Service/getParameterList'
import swal from "sweetalert";
import { Field } from 'formik';
import { getGpsColumnList } from '../../Service/getGpsColumnList'

import Modal from 'react-bootstrap/Modal';
import ModalBody from "react-bootstrap/ModalBody";
import ModalHeader from "react-bootstrap/ModalHeader";
import ModalFooter from "react-bootstrap/ModalFooter";
import ModalTitle from "react-bootstrap/ModalTitle";
import Button from 'react-bootstrap/Button';
//<script src="//code.tidio.co/o8rk9uytps64ic5pp82bbu14pv3o1qi5.js" async></script>

const SignupSchema = Yup.object().shape({
    type_of: Yup.string().required('Required'),
});

export default class Gps_ReportSuperadmin extends Component {
    constructor(props) {
        super(props);
        this.state = {
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
            tableColumnData:[],
            isOpen: false,
            show: false,
            heading: [
                "Sr_No",
                "Asset_Tag_Name",
                "Date",
                "IMEI",
                "Latitude",
                "Longitude",
                "Dispatch_Time",
                "Battery (%)"
            ],
            row: [
                "assetTagName",
                // "assetGatewayName",
                "date",
                "imeiNumber",
                "latitude",
                "longitude",
                // "tagEntryLocation",
                // "tagExistLocation",
                // "existTime",
                "dispatchTime",
                "battryPercentage"
            ]
        };
        this.handlePageClick = this.handlePageClick.bind(this);
    
    }

 

    


    handlePageClick = async (e) => {
        const selectedPage = e.selected;
        if (this.state.selectedDevice === "" && this.state.selectDuration === "") {
            let result = await getGpsTrackData(selectedPage);
            console.log("Tag List result", result);
            if (result && result.data && result.data.length !== 0) {
                const data = result.data.content;
                this.setState({
                    currentPage: selectedPage,
                    pageCount: result.data.totalPages,
                    tableData: data,
                    offset: result.data.pageable.offset,
                    loader: false,
                });
            }


        } else if (this.state.selectedDevice === "" && this.state.selectDuration !== "") {
            let resultDuration = await getPageWiseGPSTagDuration(selectedPage, this.state.selectedDevice, this.state.selectDuration);
            console.log("TagDuration Pagination List", resultDuration.data);
            if (resultDuration && resultDuration.data && resultDuration.data.length !== 0) {
                const data = resultDuration.data.content;
                this.setState({
                    currentPage: selectedPage,
                    pageCount: resultDuration.data.totalPages,
                    tableData: data,
                    offset: resultDuration.data.pageable.offset,
                    loader: false,
                });
            }
        }

        if (this.state.startDate !== "" && this.state.endDate !== "" && this.state.AssetTag !== "") {
            let result = await getDateWiseGPSTag(selectedPage, this.state.startDate, this.state.endDate, this.state.AssetTag);
            console.log("Date wise GPS List", result);
            if (result && result.data && result.data.length !== 0) {
                const data = result.data.content;
                this.setState({
                    currentPage: selectedPage,
                    pageCount: result.data.totalPages,
                    tableTagData: data,
                    offset: result.data.pageable.offset,
                    loader: false,
                });
            }
        }

       
        let result = await getGPSTrackColumnData(selectedPage);
        console.log("All Column List result", result);
        if (result && result.data && result.data.length !== 0) {
          const data = result.data.content;
          this.setState({
            currentPage: selectedPage,
            pageCount: result.data.totalPages,
            tableColumnData: data,
            offset: result.data.pageable.offset,
            loader: false,
          });
        }

    };

    async componentDidMount() {
        try {
            this.setState({ loader: true })

            let resultColumn = await getGpsColumnList();
            console.log("All Gps Column Data", resultColumn.data)
            resultColumn.data.map((obj) => {
              console.log('Column', obj);
              let label = obj.allocatedColumn_ui + " (" + obj.unit + ")";
              console.log("label",label)
              this.state.heading.push(label)
              this.state.row.push(obj.columnName)
            })
            console.log('admin Row', this.state.row)
            this.receivedData()
            this.receivedColumnData()
            this.receivedTagData()
            this.ColumnData()

            let [resultAssetTagList, resultAssetGatewayList, setRole] = await Promise.all([listAssetGpsforTrack(), listGateway(), localStorage.getItem('role')]);

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

           
            let columnList = await getAdminDrowpdownList();
            console.log('Column Drowpdown List', columnList)
            let tempArray = []
            if (columnList && columnList.data && columnList.data.length != 0) {
              columnList.data.map((obj) => {
                let tempObj = {
                  // id: obj.pkId,
                  value: `${obj}`,
                  label: `${obj}`
                }
                console.log('Column', tempObj);
                tempArray.push(tempObj)
              })
            }
            this.setState({
              columnList: tempArray, selectedColumn: columnList && columnList.data[0]
            }, () => {
              console.log('Select Column', this.state.columnList)
            })

       

        //*********Tag wise Graph Madal***********
        var modal = document.getElementById("myModal");
        var btn = document.getElementById("myBtn");
        var span = document.getElementsByClassName("close")[0];
        btn.onclick = () => {
            modal.style.display = "block";
        }
        span.onclick = () => {
            modal.style.display = "none";
        }
        window.onclick = function (event) {
            if (event.target == modal) {
                modal.style.display = "block";
            }
        }

  

        //*********Datewise tag Graph Madal***********
        var modalDateTag = document.getElementById("myModal-DateWiseTag");
        var buuton = document.getElementById("myBtn-DateWiseTag");
        var span = document.getElementsByClassName("close-Tagwise")[0];
        buuton.onclick = function () {
            modalDateTag.style.display = "block";
        }
        span.onclick = function () {
            modalDateTag.style.display = "none";
        }
        window.onclick = function (event) {
            if (event.target == modalDateTag) {
                modalDateTag.style.display = "block";
            }
        }

        //*********End Graph Madal***********

    } catch (error) {
        console.log(error)
        this.setState({ loader: false })
    }

    }

    async receivedData() {
        let result = await getGpsTrackData(this.state.currentPage);
        console.log("All Tag Data", result)
        if (result && result.data && result.data.content && result.data.content.length !== 0) {
            const data = result.data.content;
            this.setState(
                {
                    pageCount: result.data.totalPages,
                    tableData: data,
                    loader: false,
                },

            );
        }
        else {    
            this.setState(
                {
                    pageCount:{},
                    tableData: [],
                }
            );
            swal("Sorry", "Data is not present", "warning");
        }
    }
    async receivedColumnData() {
        let result = await getGPSTrackColumnData(this.state.currentPage);
        console.log("All Column GPS List", result.data);
        if (result && result.data && result.data.content && result.data.content.length !== 0) {
          const data = result.data.content;
          this.setState({
            pageCount: result.data.totalPages,
            tableColumnData: data,
            loader: false,
          });
        } else {    
            this.setState(
                {
                    pageCount:{},
                    tableData: [],
                }
            );
            swal("Sorry", "Data is not present", "warning");
        }
      }
    changeDeviceView = async (selectedOptions) => {
        console.log(selectedOptions.value)
        if (selectedOptions.value == "Select device") {
            this.receivedData()
        } else {
            this.setState({ selectedDevice: selectedOptions.value }
            );
        }
    }

    changeDuration = async (selectedDuration) => {
        console.log(selectedDuration.target.value)
        if (selectedDuration.value == "Select Duration") {
            this.receivedData()
        } else {
            this.setState({ selectDuration: selectedDuration.target.value }, async () => {
                console.log('this.state.selectDuration', this.state.selectDuration);
                let resultStay = await getStayTimeGraph(this.state.selectDuration, this.state.selectedDevice);
                let staytime = Object.entries(resultStay.data);
                console.log('Stay Time', staytime);
                this.setState({ graph: staytime, loader: false },
                    () => {
                        console.log('Stay Time Tag Data', this.state.graph)
                    })

                let result = await getPageWiseGPSTagDuration(this.state.currentPage, this.state.selectedDevice, this.state.selectDuration);
                console.log('this.state.selectedDevice', this.state.selectedDevice);
                if (result && result.data && result.data.content && result.data.content.length !== 0) {
                    const data = result.data.content;
                    console.log("Tag Duration List", data);
                    this.setState(
                        {
                            pageCount: result.data.totalPages,
                            tableData: data,
                            loader: false,
                        },
                    );
                } else {    
                    this.setState(
                        {
                            pageCount:{},
                            tableData: [],
                        }
                    );
                    swal("Sorry", "Data is not present", "warning");
                }

                this.setState({ result: result && result.data, loader: false },
                    () => {
                        console.log('data', this.state.result)
                    })
                this.setState({ resultStay: resultStay && resultStay.data, loader: false },
                    () => {
                        console.log('Stay Time Tag Data', this.state.resultStay)
                    })
            });
        }
    }

   


  

   

    async receivedTagData() {
        let result = await getDateWiseGPSTag(this.state.currentPage, this.state.startDate, this.state.endDate, this.state.selectedDevice);
        console.log("Date Wise Tag Data", result)
        if (result && result.data && result.data.content && result.data.content.length !== 0) {
            const data = result.data.content;
            this.setState(
                {
                    pageCount: result.data.totalPages,
                    tableTagData: data,
                    loader: false,
                },
            );
        }
        else {    
            this.setState(
                {
                    pageCount:{},
                    tableData: [],
                }
            );
            swal("Sorry", "Data is not present", "warning");
        }
    }

    changeTagDevice = async (selectedOptions) => {
        console.log(selectedOptions.value)

        if (selectedOptions.value == "Select Tag device") {
            this.receivedData()
        } else {
            this.setState({ selectedDevice: selectedOptions.value }

            );
        }
    }

    changeDevice = (selectedTagDevice) => {
        this.setState({ AssetTag: selectedTagDevice.value }, async () => {
            console.log('AssetTag', this.state.AssetTag)
            let result = await getDateWiseGPSTag(this.state.currentPage, this.state.startDate, this.state.endDate, this.state.AssetTag);
            console.log("Date Wise Tag Data", result)
            if (result && result.data && result.data.content && result.data.content.length !== 0) {
                const data = result.data.content;
                this.setState(
                    {
                        pageCount: result.data.totalPages,
                        tableTagData: data,
                        loader: false,
                    },
                );
            }
            else {
                swal("Sorry", "Data is not present", "warning");
            }
            let resultStay = await getTagStayTimeGraph(this.state.startDate, this.state.endDate, this.state.AssetTag);
            let staytime = Object.entries(resultStay.data);
            console.log('Stay Time', staytime);
            this.setState({ Taggraph: staytime, loader: false },
                () => {
                    console.log('Stay Time Date Wise Tag Data', this.state.Taggraph)
                })
        });
    }

   

   

   

    async ColumnData() {
        let resultPara = await getParameterList();
        console.log("All Tracking List", resultPara.data)
        this.setState({ allParameter: resultPara.data }, () => {
            console.log('startdate', this.state.allParameter)
        })
    }

    startDateValue = (e) => {
        this.setState({ startDate: moment(e.target.value).format("yyyy-MM-DD") }, () => {
            console.log('startdate', this.state.startDate)
        })
    }

    endDateValue = (e) => {
        this.setState({ endDate: moment(e.target.value).format("yyyy-MM-DD") }, () => {
            console.log('enddate', this.state.endDate)
        })
    }

    startDate = (e) => {
        this.setState({ startDateReport: moment(e.target.value).format("yyyy-MM-DD") }, () => {
            console.log('startDateReport', this.state.startDateReport)
        })
    }

    endDate = (e) => {
        this.setState({ endDateReport: moment(e.target.value).format("yyyy-MM-DD") }, () => {
            console.log('endDateReport', this.state.endDateReport)
        })
    }
    startTimeValue = (e) => {
        this.setState({ startTime: moment(e.target.value, 'hh:mm:ss').format('hh:mm:ss') }, () => {
            console.log('startTime', this.state.startTime)
        })
    }

    endTimeValue = (e) => {
        this.setState({ endTime: moment(e.target.value, 'hh:mm:ss').format('hh:mm:ss') }, async () => {
            console.log('endTime', this.state.endTime)
            let resultTime = await getTimeWise(this.state.startDateReport, this.state.endDateReport, this.state.startTime, this.state.endTime);
            console.log("All Time Data", resultTime.data)
            this.setState({ allData: resultTime.data }, async () => {
                console.log('endTime', this.state.allData)
            })
        })


    }

    downloadTagFile = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["excel"];
        let category = ["BLE"];
        axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download?tagName=${this.state.selectedDevice}&fileType=${fileType}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                fileSaver.saveAs(blob, `TagDetailsReportExcel.xls`);
            });
    }

    downloadTagPDF = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["pdf"];
        let category = ["BLE"];
        axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download?fileType=${fileType}&tagName=${this.state.selectedDevice}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/pdf' });
                fileSaver.saveAs(blob, `TagDetailsReportPDF.pdf`);
            });
    }

   

    downloadDateWiseTagFile = async () => {
        if (this.state.startDate !== '' && this.state.endDate !== '') {
            let token = await localStorage.getItem("token")
            let fkUserId = await localStorage.getItem('fkUserId');
            let role = await localStorage.getItem('role');
            let fileType = ["excel"];
            let category = ["BLE"];
            axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-between-date?fileType=${fileType}&fromDate=${this.state.startDate}&toDate=${this.state.endDate}&tagName=${this.state.AssetTag}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
                headers: { "Authorization": `Bearer ${token}` },
                responseType: 'arraybuffer'
            })
                .then((response) => {
                    var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    fileSaver.saveAs(blob, 'TagDetailsReportExcelBetweenDate.xls');
                });
        }
        else {
            alert('Please Select Date');
        }
    }

    downloadDateWiseTagPDF = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["pdf"];
        let category = ["BLE"];
        axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-between-date?fileType=${fileType}&fromDate=${this.state.startDate}&toDate=${this.state.endDate}&tagName=${this.state.AssetTag}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/pdf' });
                fileSaver.saveAs(blob, `TagDetailsReportPDFBetweenDate.pdf`);
            });
    }
    downloadGatewayFile = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["excel"];
        axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-gateway-wise?fileType=${fileType}&gatewayName=${this.state.selectedGateway}&fkUserId=${fkUserId}&role=${role}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                fileSaver.saveAs(blob, `GatewayWiseTagDetailsReportExcel.xls`);
            });
    }

    downloadGatewayPDF = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["pdf"];
        axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-gateway-wise?fileType=${fileType}&gatewayName=${this.state.selectedGateway}&fkUserId=${fkUserId}&role=${role}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/pdf' });
                fileSaver.saveAs(blob, `GatewayWiseTagDetailsReportPDF.pdf`);
            });
    }

    downloadDateWiseGatewayFile = async () => {
        if (this.state.startDate !== '' && this.state.endDate !== '') {
            let token = await localStorage.getItem("token")
            let fkUserId = await localStorage.getItem('fkUserId');
            let role = await localStorage.getItem('role');
            let fileType = ["excel"];
            axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-gateway-wise?fileType=${fileType}&fromDate=${this.state.startDate}&toDate=${this.state.endDate}&gatewayName=${this.state.AssetGateway}&fkUserId=${fkUserId}&role=${role}`, {
                headers: { "Authorization": `Bearer ${token}` },
                responseType: 'arraybuffer'
            })
                .then((response) => {
                    var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    fileSaver.saveAs(blob, 'DateWiseGatewayReportExcel.xls');
                });
        }
        else {
            alert('Please Select Date');
        }
    }

    downloadDateWiseGatewayPDF = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["pdf"];
        axios.get(`/asset/report/get-tag-report-export-excel-or-pdf-download-gateway-wise?fileType=${fileType}&fromDate=${this.state.startDate}&toDate=${this.state.endDate}&gatewayName=${this.state.AssetGateway}&fkUserId=${fkUserId}&role=${role}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/pdf' });
                fileSaver.saveAs(blob, `DateWiseGatewayReportPDF.pdf`);
            });

    }
    downloadTime = async () => {
        // if (this.state.startDateReport !== '' && this.state.endDateReport !== '' && this.state.startTime !=='' && this.state.endTime !== '') {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["excel"];
        axios.get(`/tag/get-tag-Wise-gateway-View-between-date-and-time-Report?fromdate=${this.state.startDateReport}&todate=${this.state.endDateReport}&fromtime=${this.state.startTime}&totime=${this.state.endTime}&fileType=${fileType}&fkUserId=${fkUserId}&role=${role}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                fileSaver.saveAs(blob, `TagDetailsGetewaywiseReportExcel.xls`);
            });
        // }
        // else {
        //   alert('Please Select Date');
        // }
    }

    viewTag() {
        if (document.getElementById("displaytable").style.display === "none")
            document.getElementById("displaytable").style.display = "block";
        else
            document.getElementById("displaytable").style.display = "none";
    }

    viewGateway() {
        if (document.getElementById("displaytable1").style.display === "none")
            document.getElementById("displaytable1").style.display = "block";
        else
            document.getElementById("displaytable1").style.display = "none";
    }

    changeTagWiseDateview() {
        if (document.getElementById("displaytable3").style.display === "none")
            document.getElementById("displaytable3").style.display = "block";
        else
            document.getElementById("displaytable3").style.display = "none";
    }

    changeGatewayWiseDateview() {
        if (document.getElementById("displaytable4").style.display === "none")
            document.getElementById("displaytable4").style.display = "block";
        else
            document.getElementById("displaytable4").style.display = "none";
    }

    changeTimeview() {
        if (document.getElementById("displaytable5").style.display === "none")
            document.getElementById("displaytable5").style.display = "block";
        else
            document.getElementById("displaytable5").style.display = "none";
    }
    changeColumn = (selectedOptions) => {
        console.log(selectedOptions.value)
        this.setState({ selectedColumn: selectedOptions.value }, async () => {
            console.log('selected Column', this.state.selectedColumn)
            let resultAllData = await getGpsColumnList();
            let tempTagArray = [
                "Sr_No",
                "Asset_Tag_Name",
                "Date",
                "Asset_Tag_Entry_Location",
                "Entry_Time",
                "Asset_Tag_Last_Location",
                "Exit_Time"
            ]
            let tempRow = [
                "assetTagName",
                "date",
                "tagEntryLocation",
                "entryTime",
                "tagExistLocation",
                "existTime",
            ]
            //const tempTag = new Set();
            if (resultAllData && resultAllData.data && resultAllData.data.length != 0) {
                let tempTagArray2 = resultAllData.data.filter(obj => {
                    return obj.allocatedColumn_ui === this.state.selectedColumn
                });
                let label = tempTagArray2[0].allocatedColumn_ui + " (" + tempTagArray2[0].unit + ")";
                console.log("label", label)
                // tempTagArray.push(tempTagArray2[0].allocatedColumn_ui);
                tempTagArray.push(label);
                tempRow.push(tempTagArray2[0].columnName);
            }
            this.setState({ row: tempRow, loader: false, heading: tempTagArray }, () => {
                // console.log("selected Column data", this.state.allColumnData);
                console.log('rowNew', this.state.row)
            })
        });

    }

    changeColumnView() {
        if (document.getElementById("displaytable6").style.display === "none")
          document.getElementById("displaytable6").style.display = "block";
        else
          document.getElementById("displaytable6").style.display = "none";
      }
      downloadFile = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["excel"];
        let category = ["BLE"];
        axios.get(`/asset/report/get-tracking-report-for-dynamic-columns?columnname=${this.state.selectedColumn}&fileType=${fileType}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
          headers: {"Authorization": `Bearer ${token}` },
          responseType: 'arraybuffer'
        })
          .then((response) => {
            var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            fileSaver.saveAs(blob, `ColumnNameWiseReport.xls`);
          });
      }
    
      downloadPDF = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["pdf"];
        let category = ["BLE"];
        axios.get(`/asset/report/get-tracking-report-for-dynamic-columns?columnname=${this.state.selectedColumn}&fileType=${fileType}&category=${category}&fkUserId=${fkUserId}&role=${role}`, {
          headers: {"Authorization": `Bearer ${token}` },
          responseType: 'arraybuffer'
        })
          .then((response) => {
            var blob = new Blob([response.data], { type: 'application/pdf' });
            fileSaver.saveAs(blob, `ColumnNameWiseReportPDF.pdf`);
          });
      }
    render() {
        const { allTime, allData, tableData, tableGatewayData, tableTagData, tableDateData, tableColumnData, heading, allColumn, row } = this.state
        const { data } = this.state
        return (
            <>
                <div className="tab-wrapper">
                    <div className='' >
                        <div className="row">
                            <div className="col-sm-12">

                                <Tabs defaultActiveKey="Superadmin">
                                    <Tab eventKey="Superadmin" title="Tag_Wise">
                                        <div className="tab-item-wrapper">
                                            <h5>Tag Wise Report</h5>

                                            <br />
                                            <div className="report_form">
                                                <div>
                                                    <label>SELECT TAG</label>
                                                    <Select options={this.state.tagList} className="payload__select" onChange={this.changeDeviceView} />
                                                </div>
                                            </div>
                                            <br />
                                            {/* <p>For Tag_Wise Export Click on Download Button</p> */}
                                            <button onClick={this.downloadTagFile}>EXCEL</button>
                                            <button onClick={this.downloadTagPDF}>PDF</button>
                                            <button onClick={this.viewTag}>VIEW</button>
                                        </div>
                                        <br />
                                        <div id="displaytable" style={{ display: 'none' }} >
                                            <hr />
                                            <div className="">
                                                <div className="report_form">
                                                    <div className="select__block">
                                                        <label >Duration</label>
                                                        <select className="form-control" onChange={this.changeDuration} >
                                                            <option>Select Duration</option>
                                                            <option value="today">Today's</option>
                                                            <option value="lastWeek">Last 7 Days</option>
                                                            <option value="lastMonth">Last 30 Days</option>
                                                        </select>
                                                    </div>
                                                    <div>
                                                    </div>
                                                </div>
                                                <br />
                                                <br />
                                                <div style={{ overflowX: "auto" }} className="table-responsive-sm">
                                                    <table className=" table table-hover table-bordered table-striped text-center">
                                                        <tr className="tablerow">
                                                            <th>Sr_No</th>
                                                            <th>Asset_Tag_Name</th>
                                                            <th>Asset_Gatway_Name</th>
                                                            <th>Date</th>
                                                            <th>Asset_Tag_Entry_Location</th>
                                                            <th>Entry_Time</th>
                                                            <th>Asset_Tag_Last_Location</th>
                                                            <th>Exit_Time</th>
                                                            {allColumn.map((obj) => (
                                                                <th>{obj.allocatedColumn_ui}
                                                                    <br />
                                                                    ({obj.unit})
                                                                </th>
                                                            ))}
                                                        </tr>
                                                        <tbody>
                                                            {tableData.map((obj, key) => (
                                                                <>
                                                                    <tr key={key} >

                                                                        <td>
                                                                            {this.state.offset + key + 1}
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
                                                                        {(this.state.Role === 'super-admin') ? <>
                                                                            {allColumn.map((obj) => (
                                                                                <td>
                                                                                    {/* {obj.allocatedColumn_ui} */}
                                                                                </td>
                                                                            ))}
                                                                        </>
                                                                            : <>
                                                                            </>}
                                                                    </tr>
                                                                </>
                                                            ))}
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <br />
                                            <ReactPaginate
                                                previousLabel={<i class="fa fa-angle-left report"></i>}
                                                nextLabel={<i class="fa fa-angle-right report"></i>}
                                                breakLabel={"..."}
                                                breakClassName={"break-me"}
                                                pageCount={this.state.pageCount}
                                                marginPagesDisplayed={2}
                                                pageRangeDisplayed={5}
                                                onPageChange={this.handlePageClick}
                                                containerClassName={"pagination"}
                                                subContainerClassName={"pages pagination"}
                                                activeClassName={"active"} />
                                            <br />
                                            <br />

                                        </div>
                                    </Tab>


                                    <Tab eventKey="Date" title="Date Wise Tag Report">
                                        <div className="tab-item-wrapper">
                                            <h5>Date Wise Tag Report</h5>
                                            <br />
                                            <div className="report_form">
                                                <div>
                                                    <label>START DATE</label>
                                                    <input className="form-control" type="date" name="sDate" onChange={this.startDateValue} />
                                                </div>
                                                <div>
                                                    <label>END DATE</label>
                                                    <input className="form-control" type="date" name="eDate" onChange={this.endDateValue} />
                                                </div>
                                                <div>
                                                    <label>SELECT TAG</label>
                                                    <Select options={this.state.tagList} name="selectDevice" className="payload__select" onChange={this.changeDevice} />
                                                </div>
                                            </div>
                                            <br />
                                            {/* <p>For Date Wise Tag Export Click on Download Button</p> */}
                                            <button onClick={this.downloadDateWiseTagFile}>EXCEL</button>
                                            <button onClick={this.downloadDateWiseTagPDF}>PDF</button>
                                            <button onClick={this.changeTagWiseDateview}> VIEW</button>
                                           
                                            
                                        </div>
                                       
                                        <div id="displaytable3" style={{ display: 'none' }} >
                                            <br />
                                            <div style={{ overflowX: "auto" }} className="table-responsive-sm">
                                                <table className=" table table-hover table-bordered table-striped text-center">
                                                    <tr className="tablerow">
                                                        <th>Sr_No</th>
                                                        <th>Asset_Tag_Name</th>
                                                        <th>Asset_Gatway_Name</th>
                                                        <th>Date</th>
                                                        <th>Asset_Tag_Entry_Location</th>
                                                        <th>Entry_Time</th>
                                                        <th>Asset_Tag_Last_Location</th>
                                                        <th>Exit_Time</th>
                                                        {allColumn.map((obj) => (
                                                            <th>{obj.allocatedColumn_ui}
                                                                <br />
                                                                ({obj.unit})
                                                            </th>
                                                        ))}
                                                    </tr>
                                                    <tbody>
                                                        {tableTagData.map((obj, key) => (
                                                            <>
                                                                <tr key={key}>
                                                                    <td>
                                                                        {this.state.offset + key + 1}
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
                                                                    {(this.state.Role === 'super-admin') ? <>
                                                                        {allColumn.map((obj) => (
                                                                            <td>
                                                                                {/* {obj.allocatedColumn_ui} */}
                                                                            </td>
                                                                        ))}
                                                                    </>
                                                                        : <>
                                                                        </>}
                                                                </tr>
                                                            </>
                                                        ))}
                                                    </tbody>
                                                </table>
                                            </div>
                                            <br />
                                            <br />
                                            <ReactPaginate
                                                previousLabel={<i class="fa fa-angle-left report"></i>}
                                                nextLabel={<i class="fa fa-angle-right report"></i>}
                                                breakLabel={"..."}
                                                breakClassName={"break-me"}
                                                pageCount={this.state.pageCount}
                                                marginPagesDisplayed={2}
                                                pageRangeDisplayed={5}
                                                onPageChange={this.handlePageClick}
                                                containerClassName={"pagination"}
                                                subContainerClassName={"pages pagination"}
                                                activeClassName={"active"} />
                                            <br />
                                            <br />
                                            <br />
                                        </div>
                                        
                                    </Tab>
                                    <Tab eventKey="ColumnWise" title="Parameters Wise Report">
                                        <div className="tab-item-wrapper">
                                            <h5>Parameters Wise Report</h5>
                                            <br />
                                            <div className="report_form">
                                                <div>
                                                    <label>SELECT PARAMETER</label>
                                                    <Select options={this.state.columnList} name="columnName" onChange={this.changeColumn} placeholder="Select Parameter Name" />
                                                </div>
                                            </div>
                                            <br />
                                            <button onClick={this.downloadFile}>EXCEL</button>
                                            <button onClick={this.downloadPDF}>PDF</button>
                                            <button onClick={this.changeColumnView}>VIEW</button>
                                        </div>
                                        <div id="displaytable6" style={{ display: 'none' }} >
                                            <br />
                                            <div style={{ overflowX: "auto" }} className="table-responsive-sm">
                                                <table className=" table table-hover table-bordered table-striped text-center">
                                                    <tr className="tablerow">
                                                        {heading.map((obj) => (
                                                            <th>
                                                                {obj}
                                                            </th>
                                                        ))}
                                                    </tr>
                                                    <tbody>
                                                        {tableColumnData.map((obj, key) => (
                                                            <tr key={key}>
                                                                <td>
                                                                    {this.state.offset + key + 1}
                                                                </td>
                                                                {row.map((obj1) => (
                                                                    <td>
                                                                        {obj1 === "date" ? moment(obj[obj1]).format('DD/MM/YYYY') :
                                                                            obj1 === "entryTime" || obj1 === "existTime" ? moment(obj[obj1], 'hh:mm:ss A').format('hh:mm:ss A') :
                                                                                obj[obj1]}
                                                                    </td>
                                                                ))}
                                                            </tr>
                                                        ))}
                                                    </tbody>
                                                </table>
                                            </div>
                                            <br />
                                            <br />
                                            <ReactPaginate
                                                previousLabel={<i class="fa fa-angle-left report"></i>}
                                                nextLabel={<i class="fa fa-angle-right report"></i>}
                                                breakLabel={"..."}
                                                breakClassName={"break-me"}
                                                pageCount={this.state.pageCount}
                                                marginPagesDisplayed={2}
                                                pageRangeDisplayed={5}
                                                onPageChange={this.handlePageClick}
                                                containerClassName={"pagination"}
                                                subContainerClassName={"pages pagination"}
                                                activeClassName={"active"} />
                                            <br />
                                            <br />
                                            <br />
                                        </div>
                                    </Tab>
                                </Tabs>
                            </div>
                        </div>
                    </div>
                    <br />
                </div>
            </>
        )
    }
}
