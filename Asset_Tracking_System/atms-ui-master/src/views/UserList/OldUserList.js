import React, { Component } from 'react'
import "./UserList.css"
import { getOrgWiseList } from '../../Service/getOrgWiseList'
import { getAdminWiseUserList } from '../../Service/getAdminWiseUserList'
import { getOrgWiseadminList } from '../../Service/getOrgWiseadminList'
import { getPageWiseUser } from '../../Service/getPageWiseUser'
import ReactPaginate from 'react-paginate';
import { deleteUser } from "../../Service/deleteUser"
import axios from '../../utils/axiosInstance'
import fileSaver from "file-saver"
import swal from "sweetalert";
import Select from "react-select";
import PropTypes from 'prop-types';
import { Link, NavLink, withRouter } from 'react-router-dom';
import { Formik, Form, Field } from 'formik';
import Axios from "../../utils/axiosInstance";
import Swal from 'sweetalert2'

const propTypes = {
    children: PropTypes.node,
};
const defaultProps = {};
export default class UserList extends Component {

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
            userName: [],
            allUserName: [],
            allOrgList: [],
            allAdminList: [],
            allRole: [],
            Role: "",
            loader: false,
            checkedStatus: [],
            checkedValue: [],
            optionUser: "",
            selectedUser: "",
            checkValue: "",
            selectedOrg: "",
            selectedAdmin: "",
            emprole: "",
            allUser: [
                {
                    label: "User List",
                    value: "User List"
                },
                {
                    label: "Emp User List",
                    value: "Emp User List"
                }
            ],
            allRole: [
                {
                    label: "Users",
                    value: "user"
                },
                {
                    label: "Emp Users",
                    value: "empuser"
                }
            ],
            selectedRole: "",
            allStatus: [
                { value: "Active", label: "Active" },
                { value: "Deactive", label: "Deactive" },
            ],
        };

        this.handlePageClick = this
            .handlePageClick
            .bind(this);
    }

    handlePageClick = async (e) => {
        const selectedPage = e.selected;
        // if (this.state.selectedOrg === "" && this.state.selectedAdmin === "" && this.state.selectedRole === "") {
        let result = await getPageWiseUser(selectedPage, this.state.selectedOrg, this.state.selectedAdmin);
        console.log("Pagination", result);
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

        // }
        if (this.state.selectedOrg !== "") {
            let token = await localStorage.getItem("token");
            let category = ["BLE"];
            axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedOrg}&role=organization&searchrole=&category=${category}`, {
                headers: { "Authorization": `Bearer ${token}` },
            })
                .then((result) => {
                    console.log("Organization Name", result);
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
                    return Promise.resolve();
                })
                .catch((result) => {
                    swal("Failed", "Somthing went wrong", "error");
                });
        }
        else if (this.state.selectedAdmin !== "") {
            let token = await localStorage.getItem("token");
            let category = ["BLE"];
            axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedAdmin}&role=admin&searchrole=&category=${category}`, {
                headers: { "Authorization": `Bearer ${token}` },
            })
                .then((result) => {
                    console.log("admin Name", result);
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

                    return Promise.resolve();
                })
                .catch((result) => {
                    swal("Failed", "Somthing went wrong", "error");
                });

        } else if (this.state.selectedRole !== "") {
            let token = await localStorage.getItem("token");
            let category = ["BLE"];
            if (this.state.selectedAdmin === "") {
                axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${localStorage.getItem("username")}&role=${this.state.Role}&searchrole=${this.state.selectedRole}&category=${category}`, {
                    headers: { "Authorization": `Bearer ${token}` },
                })
                    .then((result) => {
                        console.log("User Name", result);
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
                        return Promise.resolve();
                    })

                    .catch((result) => {
                        swal("Failed", "Somthing went wrong", "error");
                    });
            }
            else {
                axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedAdmin}&role=admin&searchrole=${this.state.selectedRole}&category=${category}`, {
                    headers: { "Authorization": `Bearer ${token}` },
                })
                    .then((result) => {
                        console.log("User Name", result);
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
                        return Promise.resolve();
                    })

                    .catch((result) => {
                        swal("Failed", "Somthing went wrong", "error");
                    });
            }
        }
    };
    async componentDidMount() {
        try {
            setTimeout(async () => {
                this.setState({ loader: true, Role: localStorage.getItem('role') })
                this.getData();

                let resultOrg = await getOrgWiseList();
                console.log('result', resultOrg);
                let tempOrgArray = []
                if (resultOrg && resultOrg.data && resultOrg.data.length != 0) {
                    resultOrg.data.map((obj) => {
                        let tempObj = {
                            id: `${obj.pkuserId}`,
                            value: `${obj.username}`,
                            label: `${obj.username}`
                        }
                        tempOrgArray.push(tempObj)
                    })
                }

                this.setState({ allOrgList: tempOrgArray, }, () => {
                    console.log('allOrgList List', this.state.allOrgList)
                })

                let resultAdmin = await getAdminWiseUserList();
                console.log('Admin result', resultAdmin);
                let tempAdminArray = []
                if (resultAdmin && resultAdmin.data && resultAdmin.data.length != 0) {
                    resultAdmin.data.map((obj) => {
                        let tempAdminObj = {
                            id: `${obj.pkuserId}`,
                            value: `${obj}`,
                            label: `${obj}`
                        }
                        tempAdminArray.push(tempAdminObj)
                    })
                }

                this.setState({ allAdminList: tempAdminArray, }, () => {
                    console.log('allAdmin List', this.state.allAdminList)
                })


            }, 200)

        } catch (error) {
            console.log(error)
        }
    }

    async getData() {
        let result = await getPageWiseUser(this.state.currentPage, this.state.selectedOrg, this.state.selectedAdmin);
        console.log("User List", result);

        if (result && result.data && result.data.content && result.data.content.length !== 0) {
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
        }
        else {
            this.setState(
                {
                    pageCount: {},
                    tableData: [],
                }
            );
            swal("Sorry", "Data is not present", "warning");
        }
    }
    redirectToCreateUser = () => {
        this.props.history.push("/create")
    }
    redirectToCreateEmpUser = () => {
        this.props.history.push("/emp-users")
    }
    redirectToUpdateEmpuser = (id) => {
        this.props.history.push({
            pathname: `/update-emp/${id}`,
            state: { empId: id },
        });
    }
    redirectToUploadExcel = () => {
        this.props.history.push("/upload-user-excel")
    }
    redirectToUpdate = (id, role) => {
        if (role === "empuser") {
            this.props.history.push({
                pathname: `/update-emp/${id}`,
                state: { empId: id },
            });
        } else {
            this.props.history.push({
                pathname: `/update-user/${id}`,
                state: { userId: id },
            });
        }
    }

    changeStatus = (value, e) => {
        console.log("ChangeStatus", e.target.value)
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

    changeStatusEmpUser = (value, e) => {
        console.log("changeStatusEmpUser", e.target.value)
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
    allCheckUncheked = (e) => {
        let temp = [];
        let checkedArray = []

        if (e.target.checked) {
            console.log('value', e.target.checked);
            this.state.tableData.map((obj, key) => {
                const objTemp = { [key]: true }
                temp.push(objTemp)
                checkedArray.push(parseInt(obj.pkDeviceDetails))

            })

            this.setState({ checkedStatus: temp, checkedValue: checkedArray })
        } else {
            let temp = []
            this.state.tableData.map((obj, key) => {
                const objTemp = { [key]: false }
                temp.push(objTemp)
            })
            this.setState({ checkedStatus: temp, checkedValue: [] })
        }
    }

    deleteUser = async () => {
        // console.log('delete', this.state.checkedValue)
        // try {
        //     let result = await deleteUser(this.state.checkedValue)
        //     if (result.status === 200) {
        //         swal("Great", "User Deleted Successfully", "success")
        //         //window.location.reload();
        //     } else {
        //         swal("Failed", "Something went wrong please check your internet", "error");
        //     }
        // } catch (error) {
        //     console.log(error)
        // }
        console.log('delete', this.state.checkedValue)
        Swal.fire({
            title: 'Are you sure want to Delete?',
            // text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#c01664',
            confirmButtonText: 'Yes, Delete it!'
        }).then(async (resultResponse) => {
            if (resultResponse.isConfirmed) {

                let token = localStorage.getItem("token");
                return Axios.post('/user/bulkdelete', this.state.checkedValue, { headers: { "Authorization": `Bearer ${token}` } })
                    .then(resultResponse => {
                        console.log("Delete User", resultResponse);
                        Swal.fire({
                            position: 'top-center',
                            icon: 'success',
                            title: 'User Is Deleted...!',
                            showConfirmButton: false,
                            timer: 3000
                        })
                        window.location.reload()
                        return Promise.resolve();
                    })
            }
        }).catch(resultResponse => {
            swal("Failed", "Somthing went wrong", "error");
            console.log("Delete User ", resultResponse);
        });
    }
    deleteEmp = async () => {
        // console.log('delete', this.state.checkedValue)
        // try {
        //     let result = await deleteEmp(this.state.checkedValue)

        //     if (result.status === 200) {
        //         alert("User deleted")
        //          window.location.reload();
        //     } else {
        //         alert("something went wrong please check your connection")
        //     }
        // } catch (error) {
        //     console.log(error)
        // }
        console.log('delete', this.state.checkedValue)
        Swal.fire({
            title: 'Are you sure want to Delete?',
            // text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#c01664',
            confirmButtonText: 'Yes, Delete it!'
        }).then(async (resultResponse) => {
            if (resultResponse.isConfirmed) {

                let token = localStorage.getItem("token");
                return Axios.post('/asset/tracking/BulkDeleteEmployee', this.state.checkedValue, { headers: { "Authorization": `Bearer ${token}` } })
                    .then(resultResponse => {
                        console.log("Delete User", resultResponse);
                        Swal.fire({
                            position: 'top-center',
                            icon: 'success',
                            title: 'User Is Deleted...!',
                            showConfirmButton: false,
                            timer: 3000
                        })
                        window.location.reload()
                        return Promise.resolve();
                    })
            }
        }).catch(resultResponse => {
            swal("Failed", "Somthing went wrong", "error");
            console.log("Delete User ", resultResponse);
        });
    }

    downloadUserExcel = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["excel"];
        let category = ["BLE"]
        axios.get(`/user/report/get-User-report-export-excel-or-pdf-download?fileType=${fileType}&fkUserId=${fkUserId}&role=${role}&category=${category}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                fileSaver.saveAs(blob, `UserDetailsReportExcel.xls`);
            });
    }

    downloadUserPdf = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["pdf"];
        let category = ["BLE"]
        axios.get(`/user/report/get-User-report-export-excel-or-pdf-download?fileType=${fileType}&fkUserId=${fkUserId}&role=${role}&category=${category}`, {
            headers: { "Authorization": `Bearer ${token}` },
            responseType: 'arraybuffer'
        })
            .then((response) => {
                var blob = new Blob([response.data], { type: 'application/pdf' });
                fileSaver.saveAs(blob, `UserDetailsReportPDF.pdf`);
            });
    }

    changeUser = (e) => {
        this.setState({ optionUser: e.value })
        var user = e.value;
        if (user === "Emp User List") {
            this.props.history.push("/emp-users")
        } else if (user === "User List") {
            this.props.history.push("/users")
        }
    }

    changeOrgName = async (selectOrg) => {
        console.log('Selected Organization Name--->', selectOrg.value)
        this.setState({ selectedOrg: selectOrg.value });
        let token = await localStorage.getItem("token");
        let category = ["BLE"];
        axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedOrg}&role=organization&searchrole=&category=${category}`, {
            headers: { "Authorization": `Bearer ${token}` },
        })
            .then((result) => {
                console.log("Organization Name", result);
                if (result && result.data && result.data.length !== 0) {
                    const data = result.data.content;
                    this.setState(
                        {
                            pageCount: result.data.totalPages,
                            tableData: data,
                            loader: false,
                        }
                    );
                }
                else {
                    this.setState(
                        {
                            pageCount: {},
                            tableData: [],
                        }
                    );
                    swal("Sorry", "Data is not present", "warning");
                }
                return Promise.resolve();
            })
            .catch((result) => {
                swal("Failed", "Somthing went wrong", "error");
            });
        axios.get(`user/get-adminlist_for_dropdown?fkUserId=${selectOrg.id}&role=organization&category=BLE`, {
            headers: { "Authorization": `Bearer ${token}` },
        })
            .then((result) => {
                console.log("Admin Name", result);
                if (result && result.data && result.data.length !== 0) {
                    let tempAdminArray = []

                    result.data.map((obj) => {
                        let tempAdminObj = {
                            id: `${obj.pkuserId}`,
                            value: `${obj}`,
                            label: `${obj}`
                        }
                        tempAdminArray.push(tempAdminObj)
                    })
                    this.setState({ allAdminList: tempAdminArray, }, () => {
                        console.log('allAdmin List', this.state.allAdminList)
                    })
                }
                else {
                    this.setState(
                        {
                            pageCount: {},
                            tableData: [],
                        }
                    );
                    swal("Sorry", "Data is not present", "warning");
                }
                return Promise.resolve();
            })
            .catch((result) => {
                swal("Failed", "Somthing went wrong", "error");
            });
    }

    changeAdminName = async (selectAdmin) => {
        console.log('Selected Admin Name--->', selectAdmin.value)
        this.setState({ selectedAdmin: selectAdmin.value });
        let token = await localStorage.getItem("token");
        let category = ["BLE"];
        axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedAdmin}&role=admin&searchrole=&category=${category}`, {
            headers: { "Authorization": `Bearer ${token}` },
        })
            .then((result) => {
                console.log("admin Name", result);
                if (result && result.data && result.data.length !== 0) {
                    const data = result.data.content;
                    this.setState(
                        {
                            pageCount: result.data.totalPages,
                            tableData: data,
                            loader: false,
                        }
                    );
                }
                else {
                    this.setState(
                        {
                            pageCount: {},
                            tableData: [],
                        }
                    );
                    swal("Sorry", "Data is not present", "warning");
                }
                return Promise.resolve();
            })
            .catch((result) => {
                swal("Failed", "Somthing went wrong", "error");
            });
    }

    changeRole = async (selectUser) => {
        console.log('Selected Role wise--->', selectUser.value)
        this.setState({ selectedRole: selectUser.value });
        let token = await localStorage.getItem("token");
        let category = ["BLE"];
        if (this.state.selectedAdmin === "") {
            axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${localStorage.getItem("username")}&role=${this.state.Role}&searchrole=${this.state.selectedRole}&category=${category}`, {
                headers: { "Authorization": `Bearer ${token}` },
            })
                .then((result) => {
                    console.log("User Name", result);
                    if (result && result.data && result.data.length !== 0) {
                        const data = result.data.content;
                        this.setState(
                            {
                                pageCount: result.data.totalPages,
                                tableData: data,
                                loader: false,
                            }
                        );
                    }
                    else {
                        this.setState(
                            {
                                pageCount: {},
                                tableData: [],
                            }
                        );
                        swal("Sorry", "Data is not present", "warning");
                    }
                    return Promise.resolve();
                })

                .catch((result) => {
                    swal("Failed", "Somthing went wrong", "error");
                });
        }
        else {
            axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedAdmin}&role=admin&searchrole=${this.state.selectedRole}&category=${category}`, {
                headers: { "Authorization": `Bearer ${token}` },
            })
                .then((result) => {
                    console.log("User Name", result);
                    if (result && result.data && result.data.length !== 0) {
                        const data = result.data.content;
                        this.setState(
                            {
                                pageCount: result.data.totalPages,
                                tableData: data,
                                loader: false,
                            }
                        );
                    }
                    else {
                        this.setState(
                            {
                                pageCount: {},
                                tableData: [],
                            }
                        );
                        swal("Sorry", "Data is not present", "warning");
                    }
                    return Promise.resolve();
                })

                .catch((result) => {
                    swal("Failed", "Somthing went wrong", "error");
                });
        }
    }
    changeActDeactive = async (selectedStatus, pkuserid, userrole) => {
        console.log(selectedStatus.value, pkuserid, userrole)
        Swal.fire({
            title: 'Are you sure want to deactivate or activate?',
            // text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#c01664',
            confirmButtonText: 'Yes, Status Update it!'
        }).then((resultResponse) => {
            if (resultResponse.isConfirmed) {
                this.setState({ selectStatus: selectedStatus.value }, async () => {
                    console.log('this.state.selectStatus', this.state.selectStatus);
                    let token = localStorage.getItem("token");
                    return await Axios.post(`/users/enable-status?status=${this.state.selectStatus}&pkuserid=${pkuserid}&userrole=${userrole}`, '', { headers: { "Authorization": `Bearer ${token}` }, })
                        .then(resultResponse => {
                            console.log("User Status", resultResponse);
                            Swal.fire({
                                position: 'top-center',
                                icon: 'success',
                                title: 'Status Updated...!',
                                showConfirmButton: false,
                                timer: 3000
                            })
                            window.location.reload()
                        })
                })
                return Promise.resolve();
            }
        }).catch(resultResponse => {
            swal("Failed", "Somthing went wrong", "error");
            console.log("User Status", resultResponse);
        });
    }

    render() {
        const { allData, tableData } = this.state

        return (
            <>
                <div className="userList__wrapper">
                    {(this.state.Role === 'admin') ? (
                        <>
                            <div className="userList__btn__wrapper" >

                                <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create User</button>&nbsp;&nbsp;
                                <button className="btn btn-primary " onClick={this.redirectToCreateEmpUser}>Emp User List <i className="fa fa-arrow-right"></i></button>&nbsp;&nbsp;
                                <a href="User_Upload_Excel.xlsx" download="User_Upload_Excel.xlsx"><i className="fa fa-download fa-lg "></i></a>
                                <button className="btn btn-primary " onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                                <button className="btn btn-primary " onClick={this.redirectToCreateUser}>Excel</button>&nbsp;&nbsp;
                                <button className="btn btn-primary " onClick={this.redirectToCreateUser}>Pdf</button>&nbsp;&nbsp;
                            </div>
                            <br />
                        </>
                    ) : this.state.Role === 'organization' ? (
                        <>
                            <div className="row" >
                                <div className='col-md-12'>
                                    <div className="" >
                                        <div className="userName">
                                            <Select options={this.state.allAdminList} onChange={this.changeAdminName} className="payload__select" placeholder="Select Admin" />
                                        </div>&nbsp;&nbsp;
                                        <div className="userName">
                                            <Select className="payload__select" options={this.state.allRole} value={
                                                this.state.allRole
                                                    ? this.state.allRole.find(
                                                        (option) => option.value === this.state.optionUser
                                                    )
                                                    : ""
                                            } onChange={this.changeRole} placeholder="Select User" />
                                        </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                        <button className="btn btn-primary org" onClick={this.redirectToCreateUser}>Create User</button>&nbsp;&nbsp;
                                        <button className="btn btn-primary " onClick={this.redirectToCreateEmpUser}>Emp User List <i className="fa fa-arrow-right"></i></button>&nbsp;&nbsp;
                                        <a href="User_Upload_Excel.xlsx" download="User_Upload_Excel.xlsx"><i className="fa fa-download fa-lg "></i></a>
                                        <button className="btn btn-primary org" onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                                        <button className="btn btn-primary org" onClick={this.downloadUserExcel}>Excel</button>&nbsp;&nbsp;
                                        <button className="btn btn-primary org" onClick={this.downloadUserPdf}>Pdf</button>&nbsp;&nbsp;
                                    </div>
                                </div>
                            </div>
                        </>
                    ) : (
                        <>
                            <div className="row" >
                                <div className='col-md-12'>
                                    <div className="alluser" >
                                        <div className="userName">
                                            <Select options={this.state.allOrgList} onChange={this.changeOrgName} className="payload__select" placeholder="Select Organization" />
                                        </div>&nbsp;&nbsp;
                                        <div className="userName">
                                            <Select options={this.state.allAdminList} onChange={this.changeAdminName} className="payload__select" placeholder="Select Admin" />
                                        </div>&nbsp;&nbsp;
                                        <div className="userName">
                                            <Select className="payload__select" options={this.state.allRole} value={
                                                this.state.allRole
                                                    ? this.state.allRole.find(
                                                        (option) => option.value === this.state.optionUser
                                                    )
                                                    : ""
                                            } onChange={this.changeRole} placeholder="Select User" />
                                        </div>&nbsp;&nbsp;
                                        <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create User</button>&nbsp;&nbsp;
                                        <a href="User_Upload_Excel.xlsx" download="User_Upload_Excel.xlsx"><i className="fa fa-download fa-lg "></i></a>
                                        <button className="btn btn-primary" onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                                        <button className="btn btn-primary" onClick={this.downloadUserExcel}>Excel</button>&nbsp;&nbsp;
                                        <button className="btn btn-primary" onClick={this.downloadUserPdf}>Pdf</button>&nbsp;&nbsp;
                                    </div>
                                </div>
                            </div>
                        </>
                    )}

                    <div className="payload__btn__wrapper">

                        {(this.state.selectedRole === 'empuser') ? <>
                            <div>
                                &nbsp;&nbsp;&nbsp;
                                {this.state.checkedValue.length === 0 ?
                                    "" :
                                    <button className="btn btn-danger" onClick={this.deleteEmp}>Delete</button>}
                            </div>
                        </> : <>
                            <div>
                                &nbsp;&nbsp;&nbsp;
                                {this.state.checkedValue.length === 0 ?
                                    "" :
                                    <button className="btn btn-danger" onClick={this.deleteUser}>Delete</button>}
                            </div>
                        </>}
                    </div>
                    <br />
                    <div className=" table-responsive">
                        <table className="table table-hover table-bordered table-striped text-center">

                            <tr className="tablerow">
                                {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <><th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                    <th style={{ display: 'none' }}>EDIT</th> </> : (this.state.Role === 'user') ? <> <th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                        <th style={{ display: 'none' }}>EDIT</th> </> : <><th><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                </>}
                                <th>ID</th>
                                <th>First_Name</th>
                                <th>Last_Name</th>
                                <th>Email_Id</th>
                                <th>Company_Name</th>
                                <th>Address</th>
                                <th>Phone</th>
                                {/* <th>Create By</th> */}
                                <th>User_Role</th>
                                <th>Assign Tag</th>
                                <th>Assign Gateway</th>
                                <th>Category</th>
                                {/* <th>CreateBy</th> */}
                                <th>Status</th>
                                <th>Edit</th>
                            </tr>
                            <tbody>
                                {tableData.map((obj, key) => (
                                    <tr key={key} className="Device__table__col">
                                        {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <>
                                            <th style={{ display: 'none' }}><div key={key}>
                                                <input type="checkbox" value={obj.pkuserId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                            </div>
                                            </th>
                                        </> : (this.state.selectedRole === 'empuser') ? <>
                                            <th><div key={key}>
                                                <input type="checkbox" value={obj.pkuserId} onChange={(e) => this.changeStatusEmpUser(key, e, obj.role)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                            </div>
                                            </th>
                                        </> : <>
                                            <th><div key={key}>
                                                <input type="checkbox" value={obj.pkuserId} onChange={(e) => this.changeStatus(key, e, obj.role)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                            </div></th>
                                        </>}
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
                                        <td>{obj.tagCount}</td>
                                        <td>{obj.gatewayCount}</td>
                                        <td>{obj.category}</td>
                                        {/* <td>{localStorage.getItem("username") + '/' + localStorage.getItem('role')}</td> */}
                                        <td>
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
                                        </td>
                                        {/* {(this.state.selectedRole === 'empuser' ) ? <>
                                        <td onClick={() => this.redirectToUpdateEmpuser(obj.pkuserId)}><button className="btn btn-info" style={{ display: 'none' }}> </button><i className="fa fa-edit fa-lg "></i>
                                            <span class="tooltip-text">Edit</span>
                                        </td>
                                        </>:<>
                                        <td onClick={() => this.redirectToUpdate(obj.pkuserId)}><button className="btn btn-info" style={{ display: 'none' }}> </button><i className="fa fa-edit fa-lg "></i>
                                            <span class="tooltip-text">Edit</span>
                                        </td>
                                        </>} */}
                                        <td onClick={() => this.redirectToUpdate(obj.pkuserId, obj.role)}><button className="btn btn-info" style={{ display: 'none' }}> </button><i className="fa fa-edit fa-lg "></i>
                                            <span class="tooltip-text">Edit</span>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>

                        </table>
                    </div>
                    <br />
                    <br />
                    <ReactPaginate
                        // previousLabel={"Prev"}
                        // nextLabel={"Next"}
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

                </div>

            </>
        )
    }
}
UserList.propTypes = propTypes;
UserList.defaultProps = defaultProps;
