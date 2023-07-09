import React, { Component } from 'react'
import './AssetTags.css'
import { deleteAssetTag } from "../../Service/deleteAssetTag"
import { getAssetCategory } from '../../Service/getAssetCategory'
import { getTagList } from '../../Service/getTagList'
import { getAdmin } from '../../Service/getAdmin'
import { getDeviceUser } from '../../Service/getDeviceUser'
import moment from "moment";
import Select from "react-select";
import { getPageWiseTagList } from '../../Service/getPageWiseTagList'
import { getTagListByCategory } from '../../Service/getTagListByCategory'
import ReactPaginate from 'react-paginate';
import swal from "sweetalert";
import Swal from 'sweetalert2'
import Axios from "../../utils/axiosInstance";

export default class AssetTags extends Component {
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
            }, () => {
                let temp = [];
                this.state.tableData.map((obj, key) => {
                    const objTemp = { [key]: false };
                    temp.push(objTemp);
                });
                this.setState({ checkedStatus: temp, checkedValue: [] });
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

        // else if (this.state.selectedCategory !== "") {
        //     let result = await getTagListByCategory(selectedPage, this.state.selectedCategory);
        //     console.log("Tag wise Pagination", result);
        //     if (result && result.data && result.data.length !== 0) {
        //         const data = result.data.content;
        //         this.setState({
        //             currentPage: selectedPage,
        //             pageCount: result.data.totalPages,
        //             tableData: data,
        //             offset: result.data.pageable.offset,
        //             loader: false,
        //         });
        //     }
        //     else {
        //         this.setState(
        //             {
        //                 pageCount: {},
        //                 tableData: [],
        //             }
        //         );
        //         swal("Sorry", "Data is not present", "warning");
        //     }
        // }

    };

    async componentDidMount() {
        try {
            this.setState({ Role: localStorage.getItem('role'), selectedCategory: localStorage.getItem('categoryname') }, () => {
                console.log("categoryname", this.state.selectedCategory)
            })

            this.getTagData();

            // let resultcategory = await getAssetCategory();
            // console.log('resultcategory', resultcategory);
            // let tempCategoryArray = []
            // resultcategory && resultcategory.data && resultcategory.data.map((obj) => {
            //     console.log('objresultcategory', obj);
            //     let tempCategoryObj = {
            //         id: `${obj.pkCatId}`,
            //         value: `${obj.categoryName}`,
            //         label: `${obj.categoryName}`
            //     }
            //     console.log('tempCategoryObj', tempCategoryObj);

            //     tempCategoryArray.push(tempCategoryObj);
            // })

            // this.setState({ allCategory: tempCategoryArray, }, () => {
            //     console.log('allCategory List', this.state.allCategory)
            // })

        } catch (error) {
            console.log(error)

        }


    }
    async getTagData() {
        let result = await getPageWiseTagList(this.state.currentPage);
        console.log("Tag List", result);
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
                });
            swal("Sorry", "Data is not present", "warning");
        }
    }


    redirectToCreateUser = () => {
        this.props.history.push("/asset-tag-create")
    }
    redirectToCreateTagOrg = () => {
        this.props.history.push("/asset-tag-Org-create")
    }
    redirectToCreateTagAdmin = () => {
        this.props.history.push("/asset-tag-Admin-create")
    }
    redirectToUploadExcel = () => {
        this.props.history.push("/upload-excel")
    }
    redirectToOrgUploadExcel = () => {
        this.props.history.push("/upload-excel-for-org");
    }
    redirectToTagList = () => {
        this.props.history.push("/org-asset-tag");
    }


    clickCard = async (id, macId) => {

        this.props.history.push({
            pathname: `/update-asset-tag/${id}`,
            state: { deviceId: id, macId },
        });
    }
    clickOrgCard = async (id) => {
        this.props.history.push({
            pathname: `/update-org-asset-tag/${id}`,
            state: { deviceId: id },
        });
    }
    clickAdminCard = async (id) => {

        this.props.history.push({
            pathname: `/update-admin-asset-tag/${id}`,
            state: { deviceId: id },
        });
    }
    clickHitory = async (tag) => {
        console.log("ProductName", tag)
        this.props.history.push({
            pathname: `/product-allocate-history`,
            state: { deviceId: tag },
        });
    }

    clickDeleteHitory = async (tag) => {
        console.log("ProductName", tag)
        this.props.history.push({
            pathname: `/product-delete-history`,
            state: { deviceId: tag },
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
                checkedArray.push(parseInt(obj.assetTagId))
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

    deleteDevice = async () => {
        // console.log('delete', this.state.checkedValue)
        // try {
        //     let result = await deleteAssetTag(this.state.checkedValue)

        //     if (result.status === 200) {
        //         swal("Great", "Tag Deleted Successfully", "success")
        //         window.location.reload();
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
                return Axios.post('/tag/delete-multiple-tag', this.state.checkedValue, { headers: { "Authorization": `Bearer ${token}` } })
                    .then(resultResponse => {
                        console.log("Delete User", resultResponse);
                        Swal.fire({
                            position: 'top-center',
                            icon: 'success',
                            title: 'Tag Is Deleted...!',
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

    // changeCategory = (selectedCategory) => {
    //     this.setState({ selectedCategory: selectedCategory.value }, async () => {
    //         console.log('Selected Category--->', this.state.selectedCategory)
    //         let resultAllData = await getTagListByCategory(this.state.currentPage, this.state.selectedCategory);
    //         console.log("selected category data", resultAllData)
    //         if (resultAllData && resultAllData.data && resultAllData.data.length !== 0) {
    //             const data = resultAllData.data.content;
    //             this.setState(
    //                 {
    //                     pageCount: resultAllData.data.totalPages,
    //                     tableData: data,
    //                     loader: false,
    //                 }
    //             );
    //         }
    //         else {
    //             swal("Sorry", "Data is not present", "warning");
    //         }
    //     }
    //     );
    // }

    render() {
        const { allData, tableData, loader } = this.state
        return (
            <>
                <div>
                    {
                        (this.state.Role === 'admin') ? (<>
                            <div className="device__btn__wrapper" >
                                <button className="btn btn-primary" onClick={this.redirectToCreateTagAdmin}>Create Tag</button>&nbsp;&nbsp;
                                <a href="Asset_tag_generation.xlsx" download="Asset_tag_generation.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                                <button className="btn btn-primary" onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                            </div>

                        </>)
                            : (this.state.Role === 'user' || this.state.Role === 'empuser') ? (<><div className="device__btn__wrapper" style={{ display: 'none' }}>
                                <button className="btn btn-primary" onClick={this.redirectToCreateTagOrg}>Create Tag</button>&nbsp;&nbsp;
                                <a href="Asset_tag_generation.xlsx" download="Asset_tag_generation.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                                <button className="btn btn-primary" onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                            </div>

                            </>)
                                : (this.state.Role === 'organization') ? (<><div className="device__btn__wrapper">
                                    <button className="btn btn-primary" onClick={this.redirectToCreateTagOrg}>Create Tag</button>&nbsp;&nbsp;
                                    <a href="Asset_tag_generation.xlsx" download="Asset_tag_generation.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                                    <button className="btn btn-primary" onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                                    <button className="btn btn-primary" onClick={this.redirectToTagList}>Unallocated Tag List</button>&nbsp;&nbsp;
                                </div>

                                </>)
                                    : (<>
                                        <div className="device__btn__wrapper">
                                            <button className="btn btn-primary" onClick={this.redirectToCreateUser}>Create Tag</button>&nbsp;&nbsp;
                                            <a href="Asset_tag_generation.xlsx" download="Asset_tag_generation.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template</span></a>
                                            <button className="btn btn-primary" onClick={this.redirectToUploadExcel}>Upload Excel</button>&nbsp;&nbsp;
                                            <a href="AssetTagFromSuperAdmin.xlsx" download="AssetTagFromSuperAdmin.xlsx"><i className="fa fa-download fa-lg "></i><span class="tooltip-d-text">Download Template For Organization</span></a>
                                            <button className="btn btn-primary" onClick={this.redirectToOrgUploadExcel}>Upload Excel For Org</button>&nbsp;&nbsp;
                                        </div>
                                        {/* <div className="Device__form__wrapper1">
                                        <label><strong>SELECT ASSET TAG CATEGORY</strong></label>
                                        <Select options={this.state.allCategory} onChange={this.changeCategory} className="payload__select" placeholder="Select Asset Tag Category Name" />
                                    </div> */}
                                    </>
                                    )}
                    <br />

                    <div className="payload__btn__wrapper">
                        <div>
                            &nbsp;&nbsp;&nbsp;
                            {this.state.checkedValue.length === 0 ?
                                "" :
                                <button className="btn btn-danger" onClick={this.deleteDevice}>Delete</button>}
                        </div>
                    </div>
                    <br />
                    <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                        <table className="table table__scroll table-hover table-bordered table-striped text-center">

                            <tr className="tablerow">

                                {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <>
                                    <th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                    <th style={{ display: 'none' }}>EDIT</th>
                                </> :
                                    (this.state.Role === 'user' || this.state.Role === 'empuser') ? <> <th style={{ display: 'none' }}><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                        <th style={{ display: 'none' }}>EDIT</th> </> : <>
                                        <th><input type="checkbox" onChange={this.allCheckUncheked} /></th>
                                    </>}
                                <th>Sr_No</th>
                                <th>Barcode_No.</th>
                                <th>Tag_Name</th>
                                <th>Tag_Mac_Id</th>
                                {/* {(this.state.selectedCategory === 'BLE') ? <></> : <>
                                    <th>SIM</th>
                                    <th>IMSI</th>
                                    <th>IMEI</th>
                                </>} */}
                                <th>Wakeup_Time</th>
                                <th>TimeZone</th>
                                <th>Date&Time</th>
                                <th>Technology_Type</th>
                                {(this.state.Role === 'admin' || this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                    <th style={{ display: 'none' }}>Created By</th>
                                </> : <>
                                    <th>Created By</th>
                                </>}
                                {(this.state.Role === 'super-admin' || this.state.Role === 'organization' || this.state.Role === 'admin') ? <>
                                    <th>User</th>
                                </> : ""}
                                {(this.state.Role === 'super-admin' || this.state.Role === 'organization' || this.state.Role === 'user') ? <>
                                    <th>Admin</th>
                                </> : ""}
                                {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <>
                                    <th style={{ display: 'none' }}>Organization</th>
                                </> : <>
                                    <th>Organization</th>
                                </>}
                                <th>Status</th>
                                {/* <th>Tag_Allocate_History</th>
                                <th>Tag_Delete_History</th> */}
                                {(this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                    <th style={{ display: 'none' }}>Tag_Allocate_History</th></>
                                    : <>
                                        <th>Tag_Allocate_History</th>
                                    </>}
                                {(this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                    <th style={{ display: 'none' }}>Tag_Delete_History</th></>
                                    : <>
                                        <th>Tag_Delete_History</th>
                                    </>}
                                {(this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                    <th style={{ display: 'none' }}>Edit</th></>
                                    : <>
                                        <th>Edit</th>
                                    </>}
                            </tr>
                            <tbody>
                                {tableData.map((obj, key) => (
                                    <>
                                        <tr className="Device__table__col">
                                            {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <> <th style={{ display: 'none' }}><div key={key}>
                                                <input type="checkbox" value={obj.assetTagId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                            </div></th>
                                                <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.assetTagId)}><button className="btn btn-info">EDIT</button></td></>
                                                : (this.state.Role === 'user' || this.state.Role === 'empuser') ? <><th style={{ display: 'none' }}><div key={key}>
                                                    <input type="checkbox" value={obj.assetTagId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                                </div></th>
                                                    <td style={{ display: 'none' }} onClick={() => this.clickCard(obj.assetTagId)}><button className="btn btn-info">EDIT</button></td></>
                                                    : <>
                                                        <th><div key={key}>
                                                            <input type="checkbox" value={obj.assetTagId} onChange={(e) => this.changeStatus(key, e)} checked={this.state && this.state.checkedStatus[key] && this.state.checkedStatus[key][`${key}`]} />
                                                        </div></th>
                                                    </>}
                                            <td>
                                                {this.state.offset + key + 1}
                                            </td>
                                            <td>
                                                {obj.assetBarcodeSerialNumber}
                                            </td>
                                            <td>{obj.assetTagName}</td>
                                            <td> {obj.assetUniqueCodeMacId}</td>
                                            <td>{obj.wakeupTime === "" ? 'N/A' : obj.wakeupTime === "0" ? 'N/A' : obj.wakeupTime}</td>
                                            <td>
                                                {obj.timeZone}
                                            </td>
                                            <td>
                                                {moment(obj.dateTime).format("DD/MM/YYYYTHH:mm:ss")}
                                            </td>
                                            <td>{obj.assetTagCategory}</td>
                                            {(this.state.Role === 'admin' || this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                                <td style={{ display: 'none' }}>
                                                    {obj.createdBy}
                                                </td>
                                            </> : <>
                                                <td>
                                                    {obj.createdBy}
                                                </td>
                                            </>}
                                            {(this.state.Role === 'super-admin' || this.state.Role === 'organization' || this.state.Role === 'admin') ? <>
                                                <td>{obj.user === "" ? 'N/A' : obj.user === "0" ? 'N/A' : obj.user}</td>
                                            </> : ""}
                                            {(this.state.Role === 'super-admin' || this.state.Role === 'organization' || this.state.Role === 'user') ? <>
                                                <td>
                                                    {obj.admin === "" ? 'N/A' : obj.admin === "0" ? 'N/A' : obj.admin}
                                                </td>
                                            </> : ""}
                                            {(this.state.Role === 'admin' || this.state.Role === 'organization') ? <>
                                                <td style={{ display: 'none' }}>
                                                    {obj.organization === "" ? 'N/A' : obj.organization === "0" ? 'N/A' : obj.organization}
                                                </td>
                                            </> : <>
                                                <td>
                                                    {obj.organization === "" ? 'N/A' : obj.organization === "0" ? 'N/A' : obj.organization}
                                                </td>
                                            </>}
                                            <td className={obj.status === 'Not-allocated' ? "colorRed" : "colorBlue"}>{obj.status}</td>
                                            {(this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                                <td onClick={() => this.clickHitory(obj.assetTagName)} style={{ display: 'none' }}>
                                                    <i className="fa fa-history fa-lg"></i>
                                                </td>
                                                <td onClick={() => this.clickDeleteHitory(obj.assetTagName)} style={{ display: 'none' }}>
                                                    <i className="fa fa-history fa-lg "></i>
                                                </td>
                                            </>
                                                : <> <td onClick={() => this.clickHitory(obj.assetTagName)}>
                                                    <i className="fa fa-history fa-lg"></i>
                                                </td>
                                                    <td onClick={() => this.clickDeleteHitory(obj.assetTagName)}>
                                                        <i className="fa fa-history fa-lg "></i>
                                                    </td>
                                                </>}

                                            {(this.state.Role === 'user' || this.state.Role === 'empuser') ? <>
                                                <td onClick={() => this.clickCard(obj.assetTagId)} style={{ display: 'none' }}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                    <i className="fa fa-edit fa-lg "></i>
                                                </td>
                                            </> :
                                                (this.state.Role === 'organization') ? <>
                                                    <td onClick={() => this.clickOrgCard(obj.assetTagId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                        <i className="fa fa-edit fa-lg "></i>
                                                    </td>
                                                </> :
                                                    (this.state.Role === 'admin') ? <>
                                                        <td onClick={() => this.clickAdminCard(obj.assetTagId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                            <i className="fa fa-edit fa-lg "></i>
                                                        </td>

                                                    </> : <>
                                                        <td onClick={() => this.clickCard(obj.assetTagId, obj.assetUniqueCodeMacId)}><button className="btn btn-info" style={{ display: 'none' }}>EDIT</button>
                                                            <i className="fa fa-edit fa-lg "></i>
                                                        </td>
                                                    </>}
                                        </tr>
                                    </>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <ReactPaginate
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
                        activeClassName={"active"} />
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
