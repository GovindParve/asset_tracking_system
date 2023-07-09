import React, { Component } from 'react'
import "./Product.css"
import axios from 'axios';
import swal from "sweetalert";
import { uploadProductExcel } from '../../Service/uploadProductExcel'


export default class UploadProductExcel extends Component {
    constructor(props) {
        super(props);
        this.state = {
            file: "",
            username: ""
        }
        this.onSubmit = this.onSubmit.bind(this)
        this.onChange = this.onChange.bind(this)
        this.uploadProductExcel = this.uploadProductExcel.bind(this)
    }

    state = {
        loader: false
    }
    async onSubmit(e) {
        e.preventDefault()
        try {
            this.setState({ loader: true, username: localStorage.getItem('firstname') + ' ' + localStorage.getItem('lastname') + '/' + localStorage.getItem("role") })
            let result = await this.uploadProductExcel(this.state.file);
            console.log("File Upload", result);
            if (result.status === 200) {
                this.setState({ loader: false })
                swal("Great", "File Upload Successfully", "success")
                this.props.history.push("/list-of-product");
                //window.location.reload(false);
            } else {
                swal("Failed", "Something went wrong please check your internet", "error");
                this.setState({ loader: false })
            }

        }
        catch (error) {
            console.log("error", error)
            this.setState({ loader: false })
        }
    }
    onChange(e) {
        this.setState({ file: e.target.files[0] })
    }
    async uploadProductExcel(file) {

        const formData = new FormData();

        await formData.append('file', file)
        await formData.append('username', this.state.username)
        return await uploadProductExcel(formData)


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
            <div className="user_uploadform" align="center">
                {this.state.loader ? <div>Please wait file is uploading....</div> :
                    <form onSubmit={this.onSubmit}>
                        <h3> Upload Product Excel</h3>
                        <br />
                        <div className="category__form__wrapper">
                            <div>
                                <input type="file" className="form-control" onChange={this.onChange} />
                            </div>
                        </div>
                        <button type="submit" className="btn btn-primary">Upload File</button>
                    </form>
                }
            </div>
        )
    }
}