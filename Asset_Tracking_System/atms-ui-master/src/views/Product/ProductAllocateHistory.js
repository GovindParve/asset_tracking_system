import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import moment from "moment";
import './Product.css'
import { getProductHistory } from '../../Service/getProductHistory'
//import { getDeleteProductHistory } from '../../Service/getDeleteProductHistory'
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';
import "moment-timezone";
import { Link} from 'react-router-dom';

export default class ProductAllocateHistory extends Component {
    state = {
        result: [],
        id: 0,
        loader: false,
        allData: [],
        allDaleteData: [],
        tableData: [],
        location: ''
    }
    componentDidMount = async () => {
        this.setState({ loader: false })
        if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
            const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;

            let result = await getProductHistory(propId)
            console.log('ProductHistory', result)
            if(result && result.data && result.data.length !== 0){
            this.setState({ allData: result && result.data, id: propId, loader: false }, () => {
                console.log('ProductData', this.state.allData)           
            }) 
        }
        else {
            swal("Sorry", "Data is not present", "warning");
        }
        }
        // if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
        //     const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;

        //     let resultDelete = await getDeleteProductHistory(propId)
        //     console.log('ProductDeleteHistory', resultDelete)
        //     if(resultDelete && resultDelete.data && resultDelete.data.length !== 0){
        //     this.setState({ allData: resultDelete && resultDelete.data, id: propId, loader: false }, () => {
        //         console.log('ProductDeleteData', this.state.allDaleteData)           
        //     })
        // }
        // }
    }

    render() {
        const {allData,allDaleteData } = this.state
        return (
            <>
               <div>
                                <button type="submit" className="btn btn-primary"><Link to="/product-list">Back</Link></button>
                            </div>
                    <div>
                        <br />
                        <div style={{ overflowX: "auto" }} className="table-responsive-sm table">
                            <table className="table table__scroll table-hover table-bordered table-striped text-center">
                                <tr className="tablerow">
                                    <th>Sr_No</th>
                                    <th>Product_Name</th>
                                    <th>Asset_Tag_Name</th>
                                    <th>Dispatch_Location</th>
                                    <th>User_Name</th>
                                </tr>
                                <tbody>
                                    {allData.map((obj, key) => (
                                        <>
                                            <tr className="Device__table__col">
                                                <td>
                                                    {key + 1}
                                                </td>
                                                <td>
                                                    {obj.productName}
                                                </td>
                                                <td>
                                                    {obj.allocatedTagName}
                                                </td>
                                                <td>
                                                    {obj.dispatchLocation}
                                                </td>
                                                <td>
                                                    {obj.user}
                                                </td>
                                            </tr>
                                        </>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                  
                    </div>
              
            </>
        )
    }
}
