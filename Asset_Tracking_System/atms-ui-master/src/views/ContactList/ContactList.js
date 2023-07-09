import React, { Component } from 'react'
import "./ContactList.css"
import { getContactList } from '../../Service/getContactList'
import ReactPaginate from 'react-paginate';
import { deleteContact } from "../../Service/deleteContact";
import swal from "sweetalert";
import Swal from 'sweetalert2'
import Axios from "../../utils/axiosInstance";

export default class ContactList extends Component {

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
            Role: "",
            loader: false,
            checkedStatus: [],
            checkedValue: [],
            fkUserId:""
        };

        this.handlePageClick = this
            .handlePageClick
            .bind(this);
    }

    handlePageClick = async (e) => {
        const selectedPage = e.selected;
        let result = await getContactList(selectedPage);
         console.log("Contact List", result);
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
            var setfkUserId = await localStorage.getItem("fkUserId");
            this.getData();
            this.setState({Role: setRole,fkUserId: setfkUserId, loader: false }, () => {
            //console.log("Contact List")
           

})
        } catch (error) {
            console.log(error)
            swal("Sorry", "Data is not present", "warning");
            this.setState({ loader: false })
        }
    }

    async getData() {
      
        let result = await getContactList(this.state.currentPage);
        console.log("Pagination Contact List", result.data);
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

    redirectToCreateUser = (id) => {
       //this.props.history.push("/Contact_Create")
        this.props.history.push({
            pathname: `/Contact_Create/${id}`,
            state: { userId: id },
          });
    }

    deleteContact = async (id) => {
        // try {
        //   let result = await deleteContact(id);
        //   if (result.status === 200) {
        //     swal("Deleted", "Contact deleted successfully", "success");
        //     window.location.reload();
        //   } else {
        //     swal(
        //       "Failed",
        //       "Something went wrong please check your internet",
        //       "error"
        //     );
        //   }
        // } catch (error) {
        //   // console.log(error)
        // }
        console.log('delete', id)
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
            return Axios.delete(`/contact/${id}`, { headers: { "Authorization": `Bearer ${token}` } })
                .then(resultResponse => {
                    console.log("Delete User", resultResponse);
                          Swal.fire({
                           position: 'top-center',
                            icon: 'success',
                            title: 'Contact Is Deleted...!',
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
      };

   

    render() {
        const { tableData, loader } = this.state
        return (
            <>
                <div className="userList__wrapper">
                    {(this.state.Role === 'admin' || this.state.Role === "organization") ? (<div className="userList__btn__wrapper" >
                        <button className="btn btn-primary" onClick={() => this.redirectToCreateUser(this.state.fkUserId)}>Create Contact</button>&nbsp;&nbsp;                       
                    </div>)
                        : (this.state.Role === 'user' || this.state.Role === 'empuser') ? (<div className="userList__btn__wrapper">
                            <button className="btn btn-primary" onClick={() => this.redirectToCreateUser(this.state.fkUserId)}>Create Contact</button>&nbsp;&nbsp;
                        </div>)
                            : (<div className="userList__btn__wrapper">
                                {/* <button className="btn btn-primary" onClick={() => this.redirectToCreateUser(this.state.fkUserId)}>Create Contact</button>&nbsp;&nbsp; */}
                         </div>)}
                    <br />
                    <br />
                    <div className=" table-responsive">
                        <table className="table table-hover table-bordered table-striped text-center">
                            <tr className="tablerow">
                                <th>ID</th>
                                <th>Name</th>
                                <th>Contact</th>
                                <th>Email_Id</th>
                                <th>Discription</th>
                                {(this.state.Role === "user" || this.state.Role === 'empuser') ? (
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
                                            {obj.contactname}
                                        </td>
                                        <td>
                                            {obj.contactnumber}
                                        </td>
                                        <td>
                                            {obj.email}
                                        </td>
                                        <td>
                                            {obj.description}
                                        </td>
                                    {(this.state.Role === "user" || this.state.Role === 'empuser') ? (
                                      <>
                                        <td
                                          style={{ display: "none" }}
                                          onClick={() =>
                                            this.deleteContact(obj.contactid)
                                          }
                                        >
                                          <button
                                            className="btn btn-info"
                                            style={{ display: "none" }}
                                          >
                                            {" "}
                                          </button>
                                          <i className="fa fa-trash fa-lg "></i>
                                          <span class="tooltip-text">Delete</span>
                                        </td>
                                      </>
                                    ) : (
                                      <>
                                        <td
                                          onClick={() =>
                                            this.deleteContact(obj.contactid)
                                          }
                                        >
                                          <button
                                            className="btn btn-info"
                                            style={{ display: "none" }}
                                          >
                                            {" "}
                                          </button>
                                          <i className="fa fa-trash fa-lg "></i>
                                          <span class="tooltip-text">Delete</span>
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
