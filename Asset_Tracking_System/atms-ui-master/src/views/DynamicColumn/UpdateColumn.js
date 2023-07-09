import React, { Component } from 'react'
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import moment from "moment";
import './DynamicColumn.css'
import { Link, NavLink } from 'react-router-dom';
import { putColumn } from '../../Service/putColumn'
import { listColumn } from '../../Service/listColumn'
import { getEditColumn } from '../../Service/getEditColumn'
import Select from "react-select";
import { timezoneNames, ZonedDate } from "@progress/kendo-date-math";
import "@progress/kendo-date-math/tz/all";
import swal from 'sweetalert';
import "moment-timezone";

export default class UpdateColumn extends Component {
    state = {
        result: {},
        allData: [],
        selectedColumn: "",
        loader: false,
        id: 0,
        category: "",

    }
    componentDidMount = async () => {
        try {
            setTimeout(async () => {
            this.setState({loader:true})
            if (this.props.location && this.props.location.state && this.props.location.state.deviceId) {
                const propId = this.props.location && this.props.location.state && this.props.location.state.deviceId;
                let result = await getEditColumn(propId)
                console.log('All Edit Column',result.data)
                let resultColumn = await listColumn()
                console.log('All Column List',resultColumn.data)
                let tempArray = []
                if (resultColumn && resultColumn.data && resultColumn.data.length != 0) {
                  resultColumn.data.map((obj) => {
                    let tempObj = {
                     // id: obj.pkId,
                     value: `${obj}`,
                     label: `${obj}`
                    }
                    tempArray.push(tempObj)
                  })
                }
                this.setState({ allData: tempArray, selectedColumn: result.data.columnName,result:result.data, id: propId,loader:false})
            } 
        }, 200)
        }      
        catch (error) {
            console.log(error)
            this.setState({loader:false})
        }
    }

    render() {

        const { loader } = this.state
        return (
            <>
                {loader ? <div class="loader"></div> :
                    <div className="userAdd__wrapper">
                        <h3>Edit Parameter</h3>
                        <br />
                        <br />
                        <Formik
                        enableReinitialize={true}
                            initialValues={{
                                columnName: this.state && this.state.result && this.state.result.columnName,
                                allocatedColumn_db: this.state && this.state.result && this.state.result.allocatedColumn_db,
                                allocatedColumn_ui: this.state && this.state.result && this.state.result.allocatedColumn_ui,
                                unit: this.state && this.state.result && this.state.result.unit,
                            }}
                            // validationSchema={SignupSchema}
                            onSubmit={async values => {
                                let fkUserId = await localStorage.getItem("fkUserId")
                                if(localStorage.getItem("categoryname") !== "GPS" && localStorage.getItem("categoryname") !== "BLE"){
                                    this.setState({category: localStorage.getItem("SelectedCategory")}) 
                                  }
                                  else {
                                    this.setState({category: localStorage.getItem("categoryname")})
                                  }
                                const payload = {
                                    //productId: this.state.selectedProductId,
                                    columnName: values.columnName,
                                    allocatedColumn_db: values.allocatedColumn_db,
                                    allocatedColumn_ui: values.allocatedColumn_ui,
                                    unit: values.unit,
                                    userid:fkUserId,
                                    pkId: parseInt(this.state.id),
                                    category:this.state.category,
                                }

                                console.log('Column payload', payload);
                                let result = await putColumn(payload)
                                if (result.status === 200) {
                                    swal("Great", "Data added successfully", "success");

                                    this.props.history.push("/column-list")
                                } else {

                                    swal("Failed", "Something went wrong please check your internet", "error");
                                }

                            }}
                        >
                            {({ errors, touched,setFieldValue,values }) => (
                                <Form>
                                    <div className="Product_form">
                                        <div align="left">
                                            <label className='addlabel'>Parameter Name</label>
                                            <Select options={this.state.allData}
                                            onChange={(value) =>
                                        setFieldValue(
                                          "columnName",
                                          value.value
                                        )
                                      } 
                                      value={
                                        this.state.allData
                                          ? this.state.allData.find(
                                              (option) =>
                                                option.value ===
                                                values.columnName
                                            )
                                          : ""
                                      }
                                            placeholder="Select Column Name" />
                                        </div>
                                        <div>
                                            <label className='addlabel'>Allocated Parameter</label>
                                            <Field className="form-control" name="allocatedColumn_db" placeholder="Enter Allocated Parameter" />
                                        </div>
                                        <div>
                                            <label className='addlabel'>Parameter Display UI</label>
                                            <Field className="form-control" name="allocatedColumn_ui" placeholder="Enter Parameter Display UI" />
                                        </div>
                                        <div>
                                            <label className='addlabel'>Unit</label>
                                            <Field className="form-control" name="unit" placeholder="Enter Unit" />
                                        </div>
                                    </div>
                                    <br />
                                    <div className='btn-product' align="center">
                                        <button type="submit" className="btn btn-primary">Submit</button>
                                        <button type="cancel" className="btn btn-primary"> <Link to={{ pathname: "/column-list" }}>Cancel</Link></button>
                                    </div>
                                </Form>
                            )}
                        </Formik>
                    </div>
                }
            </>
        )
    }
}
