import React, { Component } from 'react'
import "./AddGatway.css"
import axios from 'axios';
import swal from "sweetalert";


import { excelUpload } from '../../Service/excelUpload'

export default class UploadGatewayExcel extends Component {
    UPLOAD_ENDPOINT = 'http://13.126.159.159:8082/gateway/import-excel-file-with-gateway-details-to-database';
    constructor(props) {
        super(props);
        this.state = {
            file: null,
            username: "",
            loader: false,
        }
        this.onSubmit = this.onSubmit.bind(this)
        this.onChange = this.onChange.bind(this)
        this.uploadGatewayExcel = this.uploadGatewayExcel.bind(this)
    }

    async onSubmit(e) {
        e.preventDefault()
        try {
            this.setState({ loader: true, username: localStorage.getItem('firstname') + ' ' + localStorage.getItem('lastname') + '/' + localStorage.getItem("username") })
            let res = await this.uploadGatewayExcel(this.state.file);
            if (res.status === 200) {
                this.setState({ loader: false })
                swal("Great", "File Upload Successfully", "success")
                this.props.history.push("/gatway_list");
                //window.location.reload(false);
            } else {
                swal("Failed", "Something went wrong please check your internet", "error");
                this.setState({ loader: false })
            }

        } catch (error) {
            console.log("error", error)
            alert(error)
            this.setState({ loader: false })
        }
    }
    onChange(e) {
        this.setState({ file: e.target.files[0] })
    }
    async uploadGatewayExcel(file) {

        const formData = new FormData();

        await formData.append('file', file)
        await formData.append('username', this.state.username)
        return await excelUpload(formData)


        // await axios.post(this.UPLOAD_ENDPOINT, formData, {
        //     headers: {
        //         'content-type': 'multipart/form-data',
        //         "Access-Control-Allow-Origin": "*",
        //     },

        // },
        //     {
        //         "type": "formData"
        //     });
    }
    render() {
        return (
            <div className="gateway_upload" align="center">
                {this.state.loader ? <div>Please wait file is uploading....</div> :
                    <form onSubmit={this.onSubmit}>
                        <h3> Upload Gateway Excel</h3>
                        <br />
                        <div className="category__form__wrapper">
                                <div>
                                <input type="file"  className="form-control" onChange={this.onChange} />
                            </div></div>
                            
                        
                        <button type="submit" className="btn btn-primary">Upload File</button>
                    </form>
                }
            </div>
        )
    }
}