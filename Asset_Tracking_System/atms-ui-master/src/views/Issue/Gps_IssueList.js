import React, { Component } from 'react'
import "./IssueList.css"
import { getGPSFeedbackList } from '../../Service/getGPSFeedbackList'
import { deleteIssue } from "../../Service/deleteIssue";
import { postStatus } from '../../Service/postStatus'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";
import Select from "react-select";
import Axios from "../../utils/axiosInstance";

export default class Gps_IssueList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            offset: 0,
            data: [],
            tableData: [],
            tableGatewayData: [],
            statusData: [],
            orgtableData: [],
            perPage: 10,
            currentPage: 0,
            allData: [],
            selectStatus: "",
            Role: "",
            loader: false,
            checkedStatus: [],
            checkedValue: [],
            fkUserId: "",
            allStatus: [
                { value: "fixed", label: "Fixed" },
                { value: "pending", label: "Pending" },
                { value: "rejected", label: "Rejected" },
            ],
        };


        this.handlePageClick = this
            .handlePageClick
            .bind(this);
    }

    handlePageClick = async (e) => {
        const selectedPage = e.selected;
        let result = await getGPSFeedbackList(selectedPage);
        console.log("Issue List", result);
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
            this.getData();
            // let result = await getGPSFeedbackList()
            // console.log("Feedback List",result)
            var setRole = await localStorage.getItem('role');
            var setfkUserId = await localStorage.getItem("fkUserId");
            this.setState({ Role: setRole, fkUserId: setfkUserId, loader: false }, () => {
            })
        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }
    }

    async getData() {
        let result = await getGPSFeedbackList(this.state.currentPage);
        console.log("Issue Pagination List", result);
        if (result && result.data && result.data.length !== 0) {
            const data = result.data.content;
            this.setState(
                {
                    pageCount: result.data.totalPages,
                    tableData: data,
                    loader: false,
                },
                () => {
                    let temp = [];
                    this.state.tableData.map((obj, key) => {
                        const objTemp = { [key]: false };
                        temp.push(objTemp);
                    });
                    this.setState({ checkedStatus: temp });
                }
            );
        } else {
            swal("Sorry", "Data is not present", "warning");
        }
    }

    redirectToCreateUser = (id) => {
        this.props.history.push({
            pathname: `/create_Issue/${id}`,
            state: { userId: id },
        });
    }

    deleteIssue = async (id) => {
        try {
            let result = await deleteIssue(id);
            if (result.status === 200) {
                swal("Deleted", "Issue deleted successfully", "success");
                window.location.reload();
            } else {
                swal(
                    "Failed",
                    "Something went wrong please check your internet",
                    "error"
                );
            }
        } catch (error) {
            // console.log(error)
        }
    };

    // changeStatus = async (selectedStatus,issueid) => {
    //     console.log(selectedStatus.target.value)

    //       this.setState({ selectStatus: selectedStatus.target.value }, async () => {
    //         console.log('this.state.selectStatus', this.state.selectStatus);
    //         let result = await getStatus(this.state.selectStatus,issueid);
    //         console.log('this.state.selectedStatus', result);
    //     });

    //   }
    
    changeIssue = async (selectedStatus, issueid) => {
        console.log(selectedStatus.value, issueid)
        this.setState({ selectStatus: selectedStatus.value }, async () => {
            console.log('this.state.selectStatus', this.state.selectStatus);
            let token = localStorage.getItem("token");
            let fkUserId = localStorage.getItem("fkUserId");
            let role = localStorage.getItem("role");
            //let payload = {status:this.state.selectStatus,issueid:issueid,fkUserId:fkUserId,role:role}
            return await Axios.post(`/issue/update-status?status=${this.state.selectStatus}&issueid=${issueid}&fkUserId=${fkUserId}&role=${role}`, '', { headers: { "Authorization": `Bearer ${token}` }, })
                // return await Axios.post(`/issue/update-status`,payload, { headers: { "Authorization": `Bearer ${token}` },})

                .then(resultResponse => {
                    console.log("Issue Status", resultResponse);
                    window.location.reload();
                    return Promise.resolve();
                }).catch(resultResponse => {
                    console.log("Issue Status", resultResponse);
                });

        });
    }

    render() {
        const { tableData } = this.state
        return (
            <>
                <div className="userList__wrapper">
                    {(this.state.Role === 'admin' || this.state.Role === "organization") ? (<div className="userList__btn__wrapper" >
                        <button className="btn btn-primary" onClick={() => this.redirectToCreateUser(this.state.fkUserId)}>Create Issue</button>&nbsp;&nbsp;
                    </div>)
                        : this.state.Role === 'user' ? (<div className="userList__btn__wrapper">
                            <button className="btn btn-primary" onClick={() => this.redirectToCreateUser(this.state.fkUserId)}>Create Issue</button>&nbsp;&nbsp;
                        </div>)
                            : (<div className="userList__btn__wrapper">
                                {/* <button className="btn btn-primary" onClick={() => this.redirectToCreateUser(this.state.fkUserId)}>Create User</button>&nbsp;&nbsp; */}
                            </div>)}
                    <br />
                    <br />
                    <br />
                    <div className=" table-responsive">
                        <table className="table table-hover table-bordered table-striped text-center">
                            <tr className="tablerow">
                                {/* {(this.state.Role === 'admin') ? <><th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                    <th style={{ display: 'none' }}>EDIT</th> </> : (this.state.Role === 'user') ? <> <th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                        <th style={{ display: 'none' }}>EDIT</th> </> : <><th><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                </>} */}
                                <th>ID</th>
                                <th>Name</th>
                                <th>Contact</th>
                                <th>Email_Id</th>
                                <th>Issue</th>
                                <th>Status</th>
                                {this.state.Role === "user" ? (
                                    <>
                                        <th style={{ display: "none" }}>Delete</th>
                                    </>
                                ) : (
                                    <>
                                        <th>Delete</th>
                                    </>
                                )}
                            </tr>
                            <tbody>
                                {tableData.map((obj, key) => (
                                    <tr key={key} className="Device__table__col">
                                        <td>
                                            {this.state.offset + key + 1}
                                        </td>
                                        <td>
                                            {obj.userName}
                                        </td>
                                        <td>
                                            {obj.contact}
                                        </td>
                                        <td>
                                            {obj.mailId}
                                        </td>
                                        <td>
                                            {obj.issue}
                                        </td>
                                        {this.state.Role === "user" ? (
                                            <>
                                                <td>
                                                    {obj.status}
                                                </td>
                                            </>
                                        ) : (
                                            <>
                                                <td>
                                                    <Select onChange={(e) => this.changeIssue(e, obj.issueid)}
                                                        options={this.state.allStatus}
                                                        value={
                                                            this.state.allStatus || obj.status !== null
                                                                ? this.state.allStatus.find(
                                                                    (option) =>
                                                                        option.value ===
                                                                        obj.status
                                                                )
                                                                : ""
                                                        }
                                                    />
                                                </td>
                                            </>
                                        )}
                                        {this.state.Role === "user" ? (
                                            <>
                                                <td style={{ display: "none" }} onClick={() => this.deleteIssue(obj.issueid)}>
                                                    <button className="btn btn-info" style={{ display: "none" }}> {" "} </button>
                                                    <i className="fa fa-trash fa-lg "></i>
                                                    <span class="tooltip-text">Delete</span>
                                                </td>
                                            </>
                                        ) : (
                                            <>
                                                <td onClick={() => this.deleteIssue(obj.issueid)}>
                                                    <button className="btn btn-info" style={{ display: "none" }}> {" "}</button>
                                                    <i className="fa fa-trash fa-lg "></i><span class="tooltip-text">Delete</span>
                                                </td>
                                            </>
                                        )}
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <br />
                    <br />
                    <ReactPaginate
                        previousLabel={<i class="fa fa-angle-left"></i>}
                        nextLabel={<i class="fa fa-angle-right"></i>}
                        breakLabel={"..."}
                        breakClassName={"break-me"}
                        pageCount={this.state.pageCount}
                        marginPagesDisplayed={2}
                        pageRangeDisplayed={3}
                        onPageChange={this.handlePageClick}
                        containerClassName={"pagination"}
                        subContainerClassName={"pages pagination"}
                        activeClassName={"active"} />
                    <br />
                    <br />
                    <br />
                    <br />
                </div>
            </>
        )
    }
}
