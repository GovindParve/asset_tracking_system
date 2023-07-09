import React, { Component } from 'react'
import "./IssueList.css"
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import { postFeedback } from '../../Service/postFeedback'
import { postNew } from '../../Service/postNew'
import { getSingleLoginUserDetails } from "../../Service/getSingleLoginUserDetails";
import { getUser } from "../../Service/getUser";
import swal from 'sweetalert';

const SignupSchema = Yup.object().shape({
    userName: Yup.string()
        .min(2, "Too Short!")
        .max(50, "Too Long!")
        .required("Required"),
    // contact: Yup.string()
    //     .min(10, "Too Short!")
    //     .max(10, "Too Long!")
    //     .required("Required"),
    mailId: Yup.string().email("Invalid email").required("Required"),
    // subject: Yup.string()
    //     .min(2, "Too Short!")
    //     .max(50, "Too Long!")
    //     .required("Required"),
    issue: Yup.string()
        .min(2, "Too Short!")
        .max(50, "Too Long!")
        .required("Required"),
});


export default class IssueCreate extends Component {
    state = {
        result: [],
        id: 0,
        loader: false,
    };

    componentDidMount = async () => {
        try {
            this.setState({ loader: true });
            if (this.props.location && this.props.location.state && this.props.location.state.userId) 
            {const propId = this.props.location && this.props.location.state && this.props.location.state.userId;
                let result = await getSingleLoginUserDetails(propId);
                console.log("result", result);
                this.setState({
                    result: result && result.data,
                    id: propId,
                    loader: false,
                });
            } else if (
                this.props.match &&
                this.props.match.params &&
                this.props.match.params.id
            ) {
                const propParamId =
                    this.props.match &&
                    this.props.match.params &&
                    this.props.match.params.id;
                let result = await getSingleLoginUserDetails(propParamId);
                console.log("result", result);
                this.setState({
                    result: result && result.data,
                    id: propParamId,
                    loader: false,
                });
            } else {
                this.props.history.push("/issuelist");
            }
        } catch (error) {
            console.log(error);
            this.setState({ loader: false });
        }
    };

    render() {
        return (
            <div className="userAdd__wrapper">
                <h3>Issue Information</h3>
                <br />
                <Formik
                    enableReinitialize={true}
                    initialValues={{
                        userId: this.state.id,
                        userName:
                            this.state &&
                            this.state.result &&
                            this.state.result.firstname + " " + this.state.result.lastname,
                        contact:
                            this.state && this.state.result && this.state.result.contactNo,
                        mailId: this.state && this.state.result && this.state.result.email,
                        // subject: "",
                        issue: "",
                    }}
                    validationSchema={SignupSchema}
                    onSubmit={async values => {
                        let category = await localStorage.getItem("categoryname")
                        const data = {
                            userName: values.userName,
                            fkUserId: parseInt(this.state.id),
                            contact: values.contact,
                            mailId: values.mailId,
                            issue: values.issue,
                            category: category,
                            role: values.type_of === "user" ? 'user' : values.type_of === "admin" ? 'admin' : values.type_of === "organization" ? 'organization' : 'super-admin'
                        }
                        console.log('data', data);
                        let result = await postFeedback(data);
                        if (result.status === 200) {
                            swal("Great", "Data added successfully", "success");
                            this.props.history.push("/Issue_list")
                        }
                        else {
                            swal("Failed", "Something went wrong please check your internet", "error");
                        }
                    }}
                >
                    {({ errors, touched }) => (
                        <Form>
                            <div className="user__form__wrapper">
                                <div>
                                    <label className='label'>Name</label>
                                    <Field className="form-control" name="userName" placeholder="First Name" />
                                    {errors.userName && touched.userName ? (
                                        <div className="error__msg">
                                            {errors.userName}
                                        </div>
                                    ) : null}
                                </div>
                                <div>
                                    <label className='label'>Phone No</label>
                                    <Field className="form-control" name="contact" placeholder="Phone" maxlength="10" />
                                    {/* {errors.phone && touched.phone ? <div className="error__msg">{errors.phone}</div> : null} */}
                                </div>
                                <div>
                                    <label className='label'>Email Id</label>
                                    <Field className="form-control" name="mailId" type="email" placeholder="Email_Id" validate={this.validateEmail} />
                                    {errors.mailId && touched.mailId ? <div className="error__msg">{errors.mailId}</div> : null}
                                </div>
                                <div>
                                    <label className='label1'>Issue</label>
                                    <Field className="form-control" as="textarea" name="issue" placeholder="Enter Your Issue" />
                                    {errors.issue && touched.issue ? (
                                        <div className="error__msg">
                                            {errors.issue}
                                        </div>
                                    ) : null}
                                </div>
                            </div>
                            <br />
                            <div className='btn-user' align="center">
                                <button type="submit" className="btn btn-primary">SUBMIT</button>
                            </div>
                            <br />
                            <br />
                        </Form>
                    )}
                </Formik>
            </div>
        )
    }
}
