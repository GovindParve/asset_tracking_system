import './WorkingOrNonTag.css'
import React, { Component } from 'react'

import { getWorkingOrNonTag } from '../../Service/getWorkingOrNonTag'
import { getAdmin } from '../../Service/getAdmin'
import { getDeviceUser } from '../../Service/getDeviceUser'
import moment from "moment";
import Select from "react-select";
import { getPageWiseTagList } from '../../Service/getPageWiseTagList'
import { getTagListByCategory } from '../../Service/getTagListByCategory'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";

export default class NonWorkingTag extends Component {
    constructor(props) {
        super(props);
        this.state = {
            offset: 0,
            data: [],
            tableData: [],
            tableGatewayData: [],
            orgtableData: [],
            perPage: 10,
            currentPage: 0,
            allData: [],
            checkedStatus: [],
            checkedValue: [],
            allCategory: [],
            selectedDevice: "",
            selectedCategory: "",
            Role: "",
            loader: false,
        };

        this.handlePageClick = this
            .handlePageClick
            .bind(this);
    }



    handlePageClick = async (e) => {
        const selectedPage = e.selected;
        if (this.state.selectedCategory === "") {
            let result = await getPageWiseTagList(selectedPage);
            // console.log("TrackList", result);
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
        } else if (this.state.selectedCategory !== "") {
            let result = await getTagListByCategory(selectedPage, this.state.selectedCategory);
            console.log("Tag wise Pagination", result);
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
        }

    };
    async componentDidMount() {
        try {
            //this.getTagData();
            let result = await getWorkingOrNonTag()
            var setRole = await localStorage.getItem('role');
            console.log('Working or Nonworking Tag', result)
            // this.setState({ allData: result && result.data, Role: setRole, loader: false }, () => {
            //     console.log('allData', this.state.allData)

            // })
            console.log('Working Tag List', result.data)
            if (result && result.data && result.data && result.data.length !== 0) {
            this.setState({ allData: result && result.data, Role: setRole}, () => {
                console.log('all Tag Data', this.state.allData)

            })
        }
            else {
                this.setState(
                  {
                 
                    allData: [],
                  }
                );
                swal("Sorry", "Data is not present", "warning");
              }
    

        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }


    }



    changeStatus = (value, e) => {
        console.log(value, e.target.value)
        let checkedValueTemp = this.state.checkedValue

        if (e.target.checked) {
            checkedValueTemp.push(parseInt(e.target.value))
            this.setState({ checkedValue: checkedValueTemp })

        } else {
            const filteredItems = checkedValueTemp.filter(item => item !== parseInt(e.target.value));
            console.log('filteredItems', filteredItems)
            checkedValueTemp = filteredItems;
            this.setState({ checkedValue: checkedValueTemp });

        }

        let temp = this.state.checkedStatus

        temp[value][`${value}`] = !temp[value][`${value}`]

    }





    render() {
        const { allData} = this.state

        return (
            <>
                <div>
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table__scroll table-hover table-bordered table-striped text-center">
                            <tr className="tablerow">
                                <th>Sr_No</th>
                                <th>Asset_Barcode_No.</th>
                                <th>Asset_Tag_Name</th>
                                {(this.state.Role === 'admin') ? <><th style={{ display: 'none' }}>Asset Unique Code</th>
                                    <th style={{ display: 'none' }}>SIM</th>  <th style={{ display: 'none' }}>IMSI</th> </> :
                                    (this.state.Role === 'user') ? <> <th style={{ display: 'none' }}>Asset Unique Code</th>
                                        <th style={{ display: 'none' }}>SIM</th>  <th style={{ display: 'none' }}>IMSI</th></> : <>
                                        <th>Asset_Mac_Id</th>
                                    </>}
                                <th>TimeZone</th>
                                <th>Date&Time</th>
                                <th>Asset_Technology_Type</th>
                                <th>Status</th>
                                <th>Non-Working</th>
                            </tr>
                            <tbody>
                                {allData.map((obj, key) => (
                                    <>
                                        <tr className="Device__table__col">
                                            <td>
                                                {this.state.offset + key + 1}
                                            </td>
                                            <td>
                                                {obj.assetBarcodeSerialNumber}
                                            </td>
                                            <td>{obj.assetTagName}</td>
                                            {(this.state.Role === 'admin') ? <>
                                                <td style={{ display: 'none' }}>{obj.assetUniqueCodeMacId}</td>  <td style={{ display: 'none' }}> {obj.assetSimNumber === "" ? 'N/A' : obj.assetSimNumber}</td>
                                                <td style={{ display: 'none' }}>{obj.assetIMSINumber === "" ? 'N/A' : obj.assetIMSINumber}</td>

                                            </> : (this.state.Role === 'user') ? <>
                                                <td style={{ display: 'none' }}> {obj.assetUniqueCodeMacId}</td>  <td style={{ display: 'none' }}> {obj.assetSimNumber === "" ? 'N/A' : obj.assetSimNumber}</td>
                                                <td style={{ display: 'none' }}>{obj.assetIMSINumber === "" ? 'N/A' : obj.assetIMSINumber}</td>
                                            </> : <>
                                                <td> {obj.assetUniqueCodeMacId}</td>
                                            </>}
                                            <td>
                                                {obj.timeZone}
                                            </td>
                                            <td>
                                                {moment(obj.dateTime).format("DD/MM/YYYYTHH:mm:ss")}
                                            </td>
                                            <td>{obj.assetTagCategory}</td>
                                            <td className={obj.status === 'Not-allocated' ? "colorRed" : "colorBlue"}>{obj.status}</td>
                                            <td>{obj.workingORNonWorking}</td>
                                        </tr>
                                    </>
                                ))}
                            </tbody>
                        </table>

                    </div>
                    {/* <ReactPaginate
                        previousLabel={<i class="fa fa-angle-left"></i>}
                        nextLabel={<i class="fa fa-angle-right"></i>}
                        breakLabel={"..."}
                        breakClassName={"break-me"}
                        pageCount={this.state.pageCount}
                        marginPagesDisplayed={2}
                        pageRangeDisplayed={5}
                        onPageChange={this.handlePageClick}
                        containerClassName={"pagination"}
                        subContainerClassName={"pages pagination"}
                        activeClassName={"active"} /> */}
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />

                </div>

            </>
        )
    }
}
