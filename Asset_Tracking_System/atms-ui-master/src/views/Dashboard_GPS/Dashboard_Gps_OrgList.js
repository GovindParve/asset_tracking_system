import React, { Component } from 'react'
import '../Dashboard/WorkingOrNonTag.css'
import { getDashboardGPSOrgList } from '../../Service/getDashboardGPSOrgList'
import moment from "moment";
import { getPageWiseGatewayList } from '../../Service/getPageWiseGatewayList'
import Select from "react-select";
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";

export default class Dashboard_Gps_OrgList extends Component {
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
        let result = await getPageWiseGatewayList(selectedPage);
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
    };

    async componentDidMount() {
        try {
            this.setState({ loader: true })
            var setRole = await localStorage.getItem('role');
            let result = await getDashboardGPSOrgList()
            console.log('Organization List', result)
            this.setState({ allData: result && result.data, Role: setRole, loader: false }, () => {
                console.log('Organization data', this.state.allData)
                let temp = []
                this.state.allData.map((obj, key) => {
                    const objTemp = { [key]: false }
                    temp.push(objTemp)
                })
                this.setState({ checkedStatus: temp })
            })
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
        const { allData } = this.state
        return (
            <>
                {/* {loader ? <div class="loader"></div> : */}
                <div>
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table-hover table-bordered table-striped text-center">

                            <tr className="tablerow">
                                {/* {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <><th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                    <th style={{ display: 'none' }}>EDIT</th> </> : (this.state.Role === 'user') ? <> <th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                        <th style={{ display: 'none' }}>EDIT</th> </> : <><th><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                </>} */}
                                <th>ID</th>
                                <th>First_Name</th>
                                <th>Last_Name</th>
                                <th>Email_Id</th>
                                <th>Company_Name</th>
                                <th>Address</th>
                                <th>Phone</th>
                                {/* <th>Create By</th> */}
                                <th>User_Role</th>
                                {/* <th>Assign Tag</th>
                                <th>Assign Gateway</th> */}
                                <th>Status</th>
                              
                            </tr>
                            <tbody>
                                {allData.map((obj, key) => (
                                    <tr key={key} className="Device__table__col">
                                        {/* {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <> <th style={{ display: 'none' }}><div key={key}>
                                            <input type="checkbox" value={obj.pkuserId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                        </div>
                                        </th>
                                            <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.pkuserId)}><button className="btn btn-info">EDIT</button></td></>
                                            : (this.state.Role === 'user') ? <><th style={{ display: 'none' }}><div key={key}>
                                                <input type="checkbox" value={obj.pkuserId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                            </div></th>
                                                <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.pkuserId)}><button className="btn btn-info">EDIT</button></td></>
                                                : <>
                                                    <th><div key={key}>
                                                        <input type="checkbox" value={obj.pkuserId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                                    </div></th>
                                                </>} */}
                                        <td>
                                            {this.state.offset + key + 1}
                                        </td>
                                        <td>
                                            {obj.firstName}
                                        </td>
                                        <td>
                                            {obj.lastName}
                                        </td>
                                        <td>
                                            {obj.emailId}
                                        </td>
                                        <td>
                                            {obj.companyName}
                                        </td>
                                        <td>
                                            {obj.address}
                                        </td>
                                        <td>
                                            {obj.phoneNumber}
                                        </td>
                                        {/* <td></td> */}
                                        <td>
                                            {obj.role}
                                        </td>
                                        {/* <td>{obj.tagCount}</td>
                                        <td>{obj.gatewayCount}</td> */}
                                        <td>{obj.status}</td>
                                        {/* <td>
                                            <Select className="status_select" onChange={async (e) => {
                                                this.changeActDeactive(e, obj.pkuserId, obj.role)
                                            }
                                            }
                                                options={this.state.allStatus}
                                                value={
                                                    this.state.allStatus
                                                        ? this.state.allStatus.find(
                                                            (option) =>
                                                                option.value ===
                                                                obj.status
                                                        )
                                                        : ""
                                                }
                                                placeholder="Active" />
                                        </td> */}
                                      
                                    </tr>
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
                {/* } */}
            </>
        )
    }
}
