import React, { Component } from 'react'
import "./UserList.css"
import { getUser } from '../../Service/getUser'
import { getOrgWiseGPSList } from '../../Service/getOrgWiseGPSList'
import { getAdminWiseGPSUserList } from '../../Service/getAdminWiseGPSUserList'
import { getGpsWiseUserList } from '../../Service/getGpsWiseUserList'
import ReactPaginate from 'react-paginate';
import { deleteUser } from "../../Service/deleteUser"
import axios from '../../utils/axiosInstance'
import fileSaver from "file-saver"
import swal from "sweetalert";
import Swal from 'sweetalert2'
import Select from "react-select";
import Axios from "../../utils/axiosInstance";
export default class UserGpsList extends Component {

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
            noOfElements: 0,
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
        if (this.state.selectedOrg === "" && this.state.selectedAdmin === "" && this.state.selectedRole === "") {
            let result = await getGpsWiseUserList(selectedPage, this.state.selectedOrg, this.state.selectedAdmin);
            console.log("Pagination", result);
            if (result && result.data && result.data.length !== 0) {
                const data = result.data.content;
                this.setState(
                    {
                        currentPage: selectedPage,
                        pageCount: result.data.totalPages,
                        tableData: data,
                        offset: result.data.pageable.offset,
                        loader: false,
                    });
            }
            else {
                this.setState(
                    {
                        pageCount: {},
                        tableData: [],
                    }
                );
            }
        }
        else if (this.state.selectedOrg !== "") {
            let token = await localStorage.getItem("token");
            let category = ["GPS"];
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
                    return Promise.resolve();
                })
                .catch((result) => {
                    swal("Failed", "Somthing went wrong", "error");
                });
        }
        else if (this.state.selectedAdmin !== "") {
            let token = await localStorage.getItem("token");
            let category = ["GPS"];
            axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedAdmin}&role=admin&searchrole=&category=${category}`, {
                headers: { "Authorization": `Bearer ${token}` },
            })
                .then((result) => {
                    console.log("admin Name", result);
                    if (result && result.data && result.data.length !== 0) {
                        const data = result.data.content;
                        this.setState(
                            {
                                currentPage: selectedPage,
                                pageCount: result.data.totalPages,
                                tableData: data,
                                offset: result.data.pageable.offset,
                                noOfElements: result.data.numberOfElements,
                                loader: false,
                            },
                            () => {
                                let temp = [];
                                this.state.tableData.map((obj, key) => {
                                    const objTemp = { [key]: false };
                                    temp.push(objTemp);
                                });
                                this.setState({ checkedStatus: temp, checkedValue: [] });
                            }
                        );
                    }

                    return Promise.resolve();
                })
                .catch((result) => {
                    swal("Failed", "Somthing went wrong", "error");
                });

        } else if (this.state.selectedRole !== "") {
            let token = await localStorage.getItem("token");
            let category = ["GPS"];
            if (this.state.selectedAdmin === "") {
                axios
                    .get(
                        `/user/get-all-userNameWise-list/${this.state.currentPage
                        }?userName=${localStorage.getItem("username")}&role=${this.state.Role
                        }&searchrole=${this.state.selectedRole}&category=${category}`,
                        {
                            headers: { Authorization: `Bearer ${token}` },
                        }
                    )
                    .then((result) => {
                        console.log("User Name", result);
                        if (result && result.data && result.data.length !== 0) {
                            const data = result.data.content;
                            this.setState(
                                {
                                    currentPage: selectedPage,
                                    pageCount: result.data.totalPages,
                                    tableData: data,
                                    offset: result.data.pageable.offset,
                                    noOfElements: result.data.numberOfElements,
                                    loader: false,
                                },
                                () => {
                                    let temp = [];
                                    this.state.tableData.map((obj, key) => {
                                        const objTemp = { [key]: false };
                                        temp.push(objTemp);
                                    });
                                    this.setState({ checkedStatus: temp, checkedValue: [] });
                                }
                            );
                        }
                        return Promise.resolve();
                    })

                    .catch((result) => {
                        swal("Failed", "Somthing went wrong", "error");
                    });
            } else {
                axios
                    .get(
                        `/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedAdmin}&role=admin&searchrole=${this.state.selectedRole}&category=${category}`,
                        {
                            headers: { Authorization: `Bearer ${token}` },
                        }
                    )
                    .then((result) => {
                        console.log("User Name", result);
                        if (result && result.data && result.data.length !== 0) {
                            const data = result.data.content;
                            this.setState(
                                {
                                    currentPage: selectedPage,
                                    pageCount: result.data.totalPages,
                                    tableData: data,
                                    offset: result.data.pageable.offset,
                                    noOfElements: result.data.numberOfElements,
                                    loader: false,
                                },
                                () => {
                                    let temp = [];
                                    this.state.tableData.map((obj, key) => {
                                        const objTemp = { [key]: false };
                                        temp.push(objTemp);
                                    });
                                    this.setState({ checkedStatus: temp, checkedValue: [] });
                                }
                            );
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
                this.setState({ loader: true, Role: localStorage.getItem("role") });
                this.getData();
                let resultOrg = await getOrgWiseGPSList();
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

                this.setState({ allOrgList: tempOrgArray }, () => {
                    console.log("allOrgList List", this.state.allOrgList);
                });

                let resultAdmin = await getAdminWiseGPSUserList();
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

                this.setState({ allAdminList: tempAdminArray }, () => {
                    console.log("allAdmin List", this.state.allAdminList);
                });
            }, 200);
        } catch (error) {
            console.log(error)
            this.setState({ loader: false })
        }
    }

    async getData() {
        let result = await getGpsWiseUserList(
            this.state.currentPage,
            this.state.selectedOrg,
            this.state.selectedAdmin
        );
        console.log("User List", result);

        if (
            result &&
            result.data &&
            result.data.content &&
            result.data.content.length !== 0
        ) {
            const data = result.data.content;
            this.setState(
                {
                    pageCount: result.data.totalPages,
                    tableData: data,
                    noOfElements: result.data.numberOfElements,
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
        this.props.history.push("/gps-emp-users")
    }
    redirectToUploadExcel = () => {
        this.props.history.push("/upload-user-excel")
    }
    redirectToUpdate = (id) => {
        this.props.history.push({
            pathname: `/update-Gps-user/${id}`,
            state: { userId: id },
        });
    }
    redirectToUploadAdminExcel = () => {
        this.props.history.push("/upload-onlyadmin-excel");
      };
    changeStatus = (value, e) => {
        console.log("ChangeStatus", e.target.value)
        let checkedValueTemp = this.state.checkedValue

        if (e.target.checked) {
            checkedValueTemp.push(parseInt(e.target.value));
            this.setState({ checkedValue: checkedValueTemp }, () => {
                console.log("checkedValue", this.state.checkedValue);
            });
        } else {
            const filteredItems = checkedValueTemp.filter(
                (item) => item !== parseInt(e.target.value)
            );
            console.log("filteredItems", filteredItems);
            checkedValueTemp = filteredItems;
            this.setState({ checkedValue: checkedValueTemp }, () => {
                console.log("checkedValue", this.state.checkedValue);
            });
        }

        let temp = this.state.checkedStatus;
        temp[value][`${value}`] = !temp[value][`${value}`];
    };
    changeStatusEmpUser = (value, e) => {
        console.log("changeStatusEmpUser", e.target.value);
        let checkedValueTemp = this.state.checkedValue;

        if (e.target.checked) {
            checkedValueTemp.push(parseInt(e.target.value));
            this.setState({ checkedValue: checkedValueTemp }, () => {
                console.log("checkedValue", this.state.checkedValue);
            });
        } else {
            const filteredItems = checkedValueTemp.filter(
                (item) => item !== parseInt(e.target.value)
            );
            console.log("filteredItems", filteredItems);
            checkedValueTemp = filteredItems;
            this.setState({ checkedValue: checkedValueTemp });

        }
        let temp = this.state.checkedStatus
        temp[value][`${value}`] = !temp[value][`${value}`]

    }
    allCheckUncheked = (e) => {
        let temp = [];
        let checkedArray = [];
        if (e.target.checked) {
            console.log("value", e.target.checked);
            this.state.tableData.map((obj, key) => {
                const objTemp = { [key]: true };
                temp.push(objTemp);
                checkedArray.push(parseInt(obj.pkuserId));
            });

            this.setState({ checkedStatus: temp, checkedValue: checkedArray }, () => {
                console.log("checkedValue", this.state.checkedValue);
            });
        } else {
            let temp = [];
            this.state.tableData.map((obj, key) => {
                const objTemp = { [key]: false };
                temp.push(objTemp);
            });
            this.setState({ checkedStatus: temp, checkedValue: [] }, () => {
                console.log("checkedValue", this.state.checkedValue);
            });
        }
    };

    changeAllUserStatus = (value, e, checkRole) => {
        console.log("ChangeStatus", e.target.value);
        let checkedValueTemp = this.state.checkedValue;
        if (e.target.checked) {
            checkedValueTemp.push({ id: parseInt(e.target.value), role: checkRole });
            this.setState({ checkedValue: checkedValueTemp }, () => {
                console.log("checkedValue", this.state.checkedValue);
            });
        } else {
            const filteredItems = checkedValueTemp.filter(
                (item) => item.id !== parseInt(e.target.value)
            );
            console.log("filteredItems", filteredItems);
            checkedValueTemp = filteredItems;
            this.setState({ checkedValue: checkedValueTemp }, () => {
                console.log("checkedValue", this.state.checkedValue);
            });
        }

        let temp = this.state.checkedStatus;
        temp[value][`${value}`] = !temp[value][`${value}`];
    };
    allUserTypeCheckUncheked = (e) => {
        let temp = [];
        let checkedArray = [];

        if (e.target.checked) {
            console.log("value", e.target.checked);
            this.state.tableData.map((obj, key) => {
                const objTemp = { [key]: true };
                temp.push(objTemp);
                checkedArray.push({ id: parseInt(obj.pkuserId), role: obj.role });
            });
            this.setState({ checkedStatus: temp, checkedValue: checkedArray }, () => {
                console.log("checkedValue", this.state.checkedValue);
            });
        } else {
            let temp = [];
            this.state.tableData.map((obj, key) => {
                const objTemp = { [key]: false };
                temp.push(objTemp);
            });
            this.setState({ checkedStatus: temp, checkedValue: [] }, () => {
                console.log("checkedValue", this.state.checkedValue);
            });
        }
    };
    deleteUser = async () => {
        console.log("delete", this.state.checkedValue);
        Swal.fire({
            title: "Are you sure want to Delete?",
            // text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#c01664",
            confirmButtonText: "Yes, Delete it!",
        })
            .then(async (resultResponse) => {
                if (resultResponse.isConfirmed) {
                    let token = localStorage.getItem("token");
                    return Axios.post("/user/bulkdelete", this.state.checkedValue, {
                        headers: { Authorization: `Bearer ${token}` },
                    }).then((resultResponse) => {
                        console.log("Delete User", resultResponse);
                        Swal.fire({
                            position: "top-center",
                            icon: "success",
                            title: "User Is Deleted...!",
                            showConfirmButton: false,
                            timer: 3000,
                        });
                        window.location.reload();
                        return Promise.resolve();
                    });
                }
            })
            .catch((resultResponse) => {
                swal("Failed", "Somthing went wrong", "error");
                console.log("Delete User ", resultResponse);
            });
    };
    deleteEmp = async () => {
        console.log("delete", this.state.checkedValue);
        Swal.fire({
            title: "Are you sure want to Delete?",
            // text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#c01664",
            confirmButtonText: "Yes, Delete it!",
        })
            .then(async (resultResponse) => {
                if (resultResponse.isConfirmed) {
                    let token = localStorage.getItem("token");
                    return Axios.post(
                        "/asset/tracking/BulkDeleteEmployee",
                        this.state.checkedValue,
                        { headers: { Authorization: `Bearer ${token}` } }
                    ).then((resultResponse) => {
                        console.log("Delete User", resultResponse);
                        Swal.fire({
                            position: "top-center",
                            icon: "success",
                            title: "User Is Deleted...!",
                            showConfirmButton: false,
                            timer: 3000,
                        });
                        window.location.reload();
                        return Promise.resolve();
                    });
                }
            })
            .catch((resultResponse) => {
                swal("Failed", "Somthing went wrong", "error");
                console.log("Delete User ", resultResponse);
            });
    };
    deleteAllTypeUser = async () => {
        console.log("delete", this.state.checkedValue);
        const filteredEmpUser = this.state.checkedValue.filter(
            (item) => item.role === "empuser"
        );
        const empUserId = filteredEmpUser.map((obj) => {
            return obj.id;
        });
        console.log("empUserId", empUserId);
        const filteredUser = this.state.checkedValue.filter(
            (item) => item.role !== "empuser"
        );
        const userId = filteredUser.map((obj) => {
            return obj.id;
        });
        console.log("userId", userId);
        Swal.fire({
            title: "Are you sure want to Delete?",
            // text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#c01664",
            confirmButtonText: "Yes, Delete it!",
        })
            .then(async (resultResponse) => {
                if (resultResponse.isConfirmed) {
                    if (userId.length !== 0 && empUserId.length !== 0) {
                        let token = localStorage.getItem("token");
                        return Axios.post("/user/bulkdelete", userId, {
                            headers: { Authorization: `Bearer ${token}` },
                        }).then(async (resultResponse) => {
                            console.log("Delete User", resultResponse);
                            // Swal.fire({
                            //     position: 'top-center',
                            //     icon: 'success',
                            //     title: 'User Is Deleted...!',
                            //     showConfirmButton: false,
                            //     timer: 3000
                            // })
                            const resultResponse_1 = await Axios.post(
                                "/asset/tracking/BulkDeleteEmployee",
                                empUserId,
                                { headers: { Authorization: `Bearer ${token}` } }
                            );
                            console.log("Delete User", resultResponse_1);
                            Swal.fire({
                                position: "top-center",
                                icon: "success",
                                title: "User Is Deleted...!",
                                showConfirmButton: false,
                                timer: 3000,
                            });
                            window.location.reload();
                            return await Promise.resolve();
                        });
                    }
                    if (userId.length !== 0) {
                        let token = localStorage.getItem("token");
                        return Axios.post("/user/bulkdelete", userId, {
                            headers: { Authorization: `Bearer ${token}` },
                        }).then((resultResponse) => {
                            console.log("Delete User", resultResponse);
                            Swal.fire({
                                position: "top-center",
                                icon: "success",
                                title: "User Is Deleted...!",
                                showConfirmButton: false,
                                timer: 3000,
                            });
                            window.location.reload();
                            return Promise.resolve();
                        });
                    }
                    if (empUserId.length !== 0) {
                        let token = localStorage.getItem("token");
                        return Axios.post("/asset/tracking/BulkDeleteEmployee", empUserId, {
                            headers: { Authorization: `Bearer ${token}` },
                        }).then((resultResponse) => {
                            console.log("Delete User", resultResponse);
                            Swal.fire({
                                position: "top-center",
                                icon: "success",
                                title: "User Is Deleted...!",
                                showConfirmButton: false,
                                timer: 3000,
                            });
                            window.location.reload();
                            return Promise.resolve();
                        });
                    }
                }
            })
            .catch((resultResponse) => {
                swal("Failed", "Somthing went wrong", "error");
                console.log("Delete User ", resultResponse);
            });
    };

    downloadUserExcel = async () => {
        let token = await localStorage.getItem("token");
        let fkUserId = await localStorage.getItem('fkUserId');
        let role = await localStorage.getItem('role');
        let fileType = ["excel"];
        let category = ["GPS"]
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
        let category = ["GPS"]
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
        let category = ["GPS"];
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
        axios.get(`user/get-adminlist_for_dropdown?fkUserId=${selectOrg.id}&role=organization&category=GPS`, {
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

        let category = ["GPS"];
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

    // changeRole = async (selectUser) => {
    //     console.log('Selected Role wise--->', selectUser.value)
    //     this.setState({ selectedRole: selectUser.value });
    //     let token = await localStorage.getItem("token");
    //     let category = ["GPS"];
    //     axios.get(`/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedAdmin}&role=admin&searchrole=${this.state.selectedRole}&category=${category}`, {
    //         headers: { "Authorization": `Bearer ${token}` },
    //     })
    //         .then((result) => {
    //             console.log("User Name", result);
    //             if (result && result.data && result.data.length !== 0) {
    //                 const data = result.data.content;
    //                 this.setState(
    //                     {
    //                         pageCount: result.data.totalPages,
    //                         tableData: data,
    //                         loader: false,
    //                     }
    //                 );
    //             }
    //             else {
    //                 this.setState(
    //                     {
    //                         pageCount: {},
    //                         tableData: [],
    //                     }
    //                 );
    //                 swal("Sorry", "Data is not present", "warning");
    //             }
    //             return Promise.resolve();
    //         })
    //         .catch((result) => {
    //             swal("Failed", "Somthing went wrong", "error");
    //         });
    // }

    changeRole = async (selectUser) => {
        console.log('Selected Role wise--->', selectUser.value)
        console.log('Selected Admin--->', this.state.selectedAdmin)
        this.setState({ selectedRole: selectUser.value });
        let token = await localStorage.getItem("token");
        let category = ["GPS"];
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
        } else {
            axios
                .get(
                    `/user/get-all-userNameWise-list/${this.state.currentPage}?userName=${this.state.selectedAdmin}&role=admin&searchrole=${this.state.selectedRole}&category=${category}`,
                    {
                        headers: { Authorization: `Bearer ${token}` },
                    }
                )
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
            title: 'Are you sure?',
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
                //window.location.reload();
                return Promise.resolve();
            }
        }).catch(resultResponse => {
            swal("Failed", "Somthing went wrong", "error");
            console.log("User Status", resultResponse);
        });




    }
    render() {
        const { allData, tableData, loader } = this.state
        return (
            <>

                <div className="userList__wrapper">
                    {/* {(this.state.Role === 'admin') ? (
                        <>
                            <div className="userList__btn__wrapper" >
                                <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create User</button>&nbsp;&nbsp;
                                <button className="btn btn-primary " onClick={this.redirectToCreateEmpUser}>Emp User List <i className="fa fa-arrow-right"></i></button>&nbsp;&nbsp;
                                <a href="User_Upload_Template.xlsx" download="User_Upload_Template.xlsx"><i className="fa fa-download fa-lg "></i></a>
                                <button className="btn btn-primary " onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                                <button className="btn btn-primary" onClick={this.downloadUserExcel}>Excel</button>&nbsp;&nbsp;
                                <button className="btn btn-primary" onClick={this.downloadUserPdf}>Pdf</button>&nbsp;&nbsp;
                            </div>                            <br />
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
                                        <a href="User_Upload_Template.xlsx" download="User_Upload_Template.xlsx"><i className="fa fa-download fa-lg "></i></a>
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
                                    <div className="alluser">
                                        <div className="userName">
                                            <Select options={this.state.allOrgList} onChange={this.changeOrgName} className="payload__select" placeholder="Select Organization" />
                                        </div>&nbsp;&nbsp;
                                        <div className="userName">
                                            <Select options={this.state.allAdminList} onChange={this.changeAdminName} className="payload__select" placeholder="Select Admin" />
                                        </div>&nbsp;&nbsp;
                                        <div className="userName">
                                            <Select
                                                className="payload__select"
                                                options={this.state.allRole}
                                                value={
                                                    this.state.allRole
                                                        ? this.state.allRole.find(
                                                            (option) =>
                                                                option.value === this.state.optionUser
                                                        )
                                                        : ""
                                                }
                                                onChange={this.changeRole}
                                                placeholder="Select User"
                                            />
                                        </div>
                                        &nbsp;&nbsp;
                                        <button
                                            className="btn btn-primary"
                                            onClick={this.redirectToCreateUser}
                                        >
                                            Create User
                                        </button>
                                        &nbsp;&nbsp;
                                        <a
                                            href="User_Upload_Template.xlsx"
                                            download="User_Upload_Template.xlsx"
                                        >
                                            <i className="fa fa-download fa-lg " ></i>
                                        </a>
                                        <button
                                            className="btn btn-primary"
                                            onClick={this.redirectToUploadExcel}
                                        >
                                            Upload Excel
                                        </button>
                                        &nbsp;&nbsp;
                                        <button
                                            className="btn btn-primary"
                                            onClick={this.downloadUserExcel}
                                        >
                                            Excel
                                        </button>
                                        &nbsp;&nbsp;
                                        <button
                                            className="btn btn-primary"
                                            onClick={this.downloadUserPdf}
                                        >
                                            Pdf
                                        </button>
                                        &nbsp;&nbsp;
                                    </div>
                                </div>
                            </div>
                        </>
                    )} */}

                    {this.state.Role === "admin" ? (
                        <>
                            <div className="userList__btn__wrapper">
                                <div className="userName">
                                    <Select
                                        className="payload__select"
                                        options={this.state.allRole}
                                        value={
                                            this.state.allRole
                                                ? this.state.allRole.find(
                                                    (option) =>
                                                        option.value === this.state.optionUser
                                                )
                                                : ""
                                        } onChange={this.changeRole} placeholder="Select User" />
                                </div>&nbsp;&nbsp;
                                <button className="btn btn-primary org" onClick={this.redirectToCreateUser} >Create User</button>&nbsp;&nbsp;
                                <button className="btn btn-primary org" onClick={this.redirectToCreateEmpUser}>Emp User List <i className="fa fa-arrow-right"></i></button>&nbsp;&nbsp;
                                {this.state.organization === "Yes" ? <>
                                    <a href="User_Upload_TemplateforAdminCreateUser.xlsx" download="User_Upload_TemplateforAdminCreateUser.xlsx">
                                        <i className="fa fa-download fa-lg " style={{ marginTop: "14%" }}></i></a>
                                </> : <>
                                    <a href="User_Upload_TemplateforsuperadminCreateDirectAdminUnderUser.xlsx" download="User_Upload_TemplateforsuperadminCreateDirectAdminUnderUser.xlsx">
                                        <i className="fa fa-download fa-lg " style={{ marginTop: "14%" }}></i></a>
                                </>}
                                <button className="btn btn-primary org" onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                                <button className="btn btn-primary org" onClick={this.downloadUserExcel}>Excel</button>&nbsp;&nbsp;
                                <button className="btn btn-primary org" onClick={this.downloadUserPdf}> Pdf </button> &nbsp;&nbsp;
                            </div>
                            <br />
                        </>
                    ) : this.state.Role === "organization" ? (
                        <>
                            <div className="row">
                                <div className="col-md-12">
                                    <div className="">
                                        <div className="userName">
                                            <Select options={this.state.allAdminList} onChange={this.changeAdminName} className="payload__select" placeholder="Select Admin" />
                                        </div>&nbsp;&nbsp;
                                        <div className="userName">
                                            <Select className="payload__select" options={this.state.allRole}
                                                value={
                                                    this.state.allRole
                                                        ? this.state.allRole.find(
                                                            (option) =>
                                                                option.value === this.state.optionUser
                                                        )
                                                        : ""} onChange={this.changeRole} placeholder="Select User" />
                                        </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <button className="btn btn-primary org" onClick={this.redirectToCreateUser}>Create User</button>&nbsp;&nbsp;
                                        <button className="btn btn-primary " onClick={this.redirectToCreateEmpUser}>Emp User List <i className="fa fa-arrow-right"></i></button>&nbsp;&nbsp;
                                        <a href="User_Upload_TemplateforOrganizationtoCreateUser - Copy.xlsx" download="User_Upload_TemplateforOrganizationtoCreateUser - Copy.xlsx">
                                            <i className="fa fa-download fa-lg "></i></a>
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
                                <div className="col-md-12">
                                    <div className="alluser" >
                                        <div className="userName">
                                            <Select options={this.state.allOrgList} onChange={this.changeOrgName} className="payl_select" placeholder="Select Organization" />
                                        </div>
                                        &nbsp;&nbsp;
                                        <div className="userName">
                                            <Select options={this.state.allAdminList} onChange={this.changeAdminName} className="payl_select" placeholder="Select Admin" />
                                        </div>
                                        &nbsp;&nbsp;
                                        <div className="userName">
                                            <Select className="payl_select" options={this.state.allRole} onChange={this.changeRole} placeholder="Select User"
                                                value={this.state.allRole
                                                    ? this.state.allRole.find(
                                                        (option) =>
                                                            option.value === this.state.optionUser
                                                    )
                                                    : ""
                                                } />
                                        </div>&nbsp;&nbsp;
                                        <button className="btn btn-primary" onClick={this.redirectToCreateUser}> Create User </button>&nbsp;&nbsp;
                                        <a href="User_Upload_Template.xlsx" download="User_Upload_Template.xlsx"> <i className="fa fa-download fa-lg " ></i>
                                            <span class="tooltip-org-text">For Organization</span>
                                        </a>
                                        <button className="btn btn-primary" onClick={this.redirectToUploadExcel}> Upload Excel</button>&nbsp;&nbsp;
                                        <a href="User_Upload_TemplateforsuperadminCreateDirectAdminUnderUser.xlsx" download="User_Upload_TemplateforsuperadminCreateDirectAdminUnderUser.xlsx">
                                            <i className="fa fa-download fa-lg "></i><span class="tooltip-ad-text">Only Admin</span></a>
                                        <button className="btn btn-primary" onClick={this.redirectToUploadAdminExcel}>Upload Only admin</button>&nbsp;&nbsp;
                                        <button className="btn btn-primary" onClick={this.downloadUserExcel}><i className="fa fa-file-excel-o fa-lg"></i></button>&nbsp;&nbsp;
                                        <button className="btn btn-primary" onClick={this.downloadUserPdf}><i className="fa fa-file-pdf-o fa-lg"></i></button>&nbsp;&nbsp;
                                    </div>
                                </div>
                            </div>
                        </>
                    )}

                    <div className="payload__btn__wrapper">
                        {(this.state.selectedOrg !== "" ||
                            this.state.selectedAdmin !== "") &&
                            this.state.selectedRole === "" ? (
                            <>
                                <div>
                                    &nbsp;&nbsp;&nbsp;
                                    {this.state.checkedValue.length === 0 ? (
                                        ""
                                    ) : (
                                        <button
                                            className="btn btn-danger"
                                            onClick={this.deleteAllTypeUser}
                                        >
                                            Delete
                                        </button>
                                    )}
                                </div>
                            </>
                        ) : this.state.selectedRole === "empuser" ? (
                            <>
                                <div>
                                    &nbsp;&nbsp;&nbsp;
                                    {this.state.checkedValue.length === 0 ? (
                                        ""
                                    ) : (
                                        <button className="btn btn-danger" onClick={this.deleteEmp}>
                                            Delete
                                        </button>
                                    )}
                                </div>
                            </>
                        ) : (
                            <>
                                <div>
                                    &nbsp;&nbsp;&nbsp;
                                    {this.state.checkedValue.length === 0 ? (
                                        ""
                                    ) : (
                                        <button
                                            className="btn btn-danger"
                                            onClick={this.deleteUser}
                                        >
                                            Delete
                                        </button>
                                    )}
                                </div>
                            </>
                        )}
                    </div>
                    <br />
                    <div className=" table-responsive">
                        <table className="table table-hover table-bordered table-striped text-center">
                            <tr className="tablerow">
                                {this.state.Role === "admin" ||
                                    this.state.Role === "organization" ? (
                                    <>
                                        <th style={{ display: "none" }}>
                                            <input type="checkbox" onChange={this.allCheckUncheked} />
                                        </th>
                                        <th style={{ display: "none" }}>EDIT</th>{" "}
                                    </>
                                ) : this.state.Role === "user" ? (
                                    <>
                                        {" "}
                                        <th style={{ display: "none" }}>
                                            <input type="checkbox" onChange={this.allCheckUncheked} />
                                        </th>
                                        <th style={{ display: "none" }}>EDIT</th>{" "}
                                    </>
                                ) : (this.state.selectedOrg !== "" ||
                                    this.state.selectedAdmin !== "") &&
                                    this.state.selectedRole === "" ? (
                                    <>
                                        <th>
                                            <input
                                                type="checkbox"
                                                onChange={this.allUserTypeCheckUncheked}
                                                checked={
                                                    this.state.checkedValue.length ===
                                                    this.state.noOfElements
                                                }
                                            />
                                        </th>
                                    </>
                                ) : (
                                    <>
                                        <th>
                                            <input
                                                type="checkbox"
                                                onChange={this.allCheckUncheked}
                                                checked={
                                                    this.state.checkedValue.length ===
                                                    this.state.noOfElements
                                                }
                                            />
                                        </th>
                                    </>
                                )}
                                <th>ID</th>
                                <th>First_Name</th>
                                <th>Last_Name</th>
                                <th>Email_Id</th>
                                <th>Company_Name</th>
                                <th>Address</th>
                                <th>Phone</th>
                                <th>User_Role</th>
                                {this.state.Role === "super-admin" || this.state.Role === "admin" || this.state.Role === "user" ? <>
                                    <th>Organization</th>
                                </> : ""}
                                {this.state.Role === "super-admin" || this.state.Role === "organization" ? <>
                                    <th>Admin</th>
                                </> : ""}
                                <th>Assign Tag</th>
                                <th>Assign Gateway</th>
                                <th>Category</th>
                                <th>Created By</th>
                                <th>Status</th>
                                <th>Edit</th>

                            </tr>
                            <tbody>
                                {tableData.map((obj, key) => (
                                    <tr key={key} className="Device__table__col">
                                        {this.state.Role === "admin" ||
                                            this.state.Role === "organization" ? (
                                            <>
                                                <th style={{ display: "none" }}>
                                                    <div key={key}>
                                                        <input
                                                            type="checkbox"
                                                            value={obj.pkuserId}
                                                            onChange={(e) => this.changeStatus(key, e)}
                                                            checked={
                                                                this.state &&
                                                                this.state.checkedStatus[key] &&
                                                                this.state.checkedStatus[key][`${key}`]
                                                            }
                                                        />
                                                    </div>
                                                </th>
                                            </>
                                        ) : (this.state.selectedOrg !== "" ||
                                            this.state.selectedAdmin !== "") &&
                                            this.state.selectedRole === "" ? (
                                            <>
                                                <th>
                                                    <div key={key}>
                                                        <input
                                                            type="checkbox"
                                                            value={obj.pkuserId}
                                                            onChange={(e) =>
                                                                this.changeAllUserStatus(key, e, obj.role)
                                                            }
                                                            checked={
                                                                this.state &&
                                                                this.state.checkedStatus[key] &&
                                                                this.state.checkedStatus[key][`${key}`]
                                                            }
                                                        />
                                                    </div>
                                                </th>
                                            </>
                                        ) : this.state.selectedRole === "empuser" ? (
                                            <>
                                                <th>
                                                    <div key={key}>
                                                        <input
                                                            type="checkbox"
                                                            value={obj.pkuserId}
                                                            onChange={(e) => this.changeStatusEmpUser(key, e)}
                                                            checked={
                                                                this.state &&
                                                                this.state.checkedStatus[key] &&
                                                                this.state.checkedStatus[key][`${key}`]
                                                            }
                                                        />
                                                    </div>
                                                </th>
                                            </>
                                        ) : (
                                            <>
                                                <th>
                                                    <div key={key}>
                                                        <input
                                                            type="checkbox"
                                                            value={obj.pkuserId}
                                                            onChange={(e) => this.changeStatus(key, e)}
                                                            checked={
                                                                this.state &&
                                                                this.state.checkedStatus[key] &&
                                                                this.state.checkedStatus[key][`${key}`]
                                                            }
                                                        />
                                                    </div>
                                                </th>
                                            </>
                                        )}
                                        <td>{this.state.offset + key + 1}</td>
                                        <td>{obj.firstName}</td>
                                        <td>{obj.lastName}</td>
                                        <td>{obj.emailId}</td>
                                        <td>{obj.companyName}</td>
                                        <td>{obj.address}</td>
                                        <td>{obj.phoneNumber}</td>
                                        {/* <td></td> */}
                                        <td>{obj.role}</td>
                                        {this.state.Role === "super-admin" || this.state.Role === "admin" || this.state.Role === "user" ?
                                            <td>{obj.organization === "" ? 'N/A' : obj.organization === "0" ? 'N/A' : obj.organization === null ? 'N/A' : obj.organization}</td>
                                            : ""}
                                        {this.state.Role === "super-admin" || this.state.Role === "organization" ?
                                            <td>{obj.admin === "" ? 'N/A' : obj.admin === "0" ? 'N/A' : obj.admin === null ? 'N/A' : obj.admin}</td>
                                            : ""}
                                        <td>{obj.tagCount}</td>
                                        <td>{obj.gatewayCount}</td>
                                        <td>{obj.category}</td>
                                        <td>{obj.createdby === "" ? 'N/A' : obj.createdby === "0" ? 'N/A' : obj.createdby === null ? 'N/A' : obj.createdby}</td>
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
                                            />
                                        </td>
                                        <td
                                            onClick={() =>
                                                this.redirectToUpdate(obj.pkuserId, obj.role)
                                            }>
                                            <button
                                                className="btn btn-info"
                                                style={{ display: "none" }}>
                                                {" "}
                                            </button>
                                            <i className="fa fa-edit fa-lg "></i>
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
                    <br />
                    <br />
                    <br />
                </div>

            </>
        )
    }
}
