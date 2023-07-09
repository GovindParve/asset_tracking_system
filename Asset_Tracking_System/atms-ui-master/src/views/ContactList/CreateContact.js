import React, { Component } from 'react'
import "./ContactList.css"
import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import { postContact } from '../../Service/postContact'
import { getSingleLoginUserDetails } from "../../Service/getSingleLoginUserDetails";
import { getUser } from "../../Service/getUser";
import swal from 'sweetalert';

const SignupSchema = Yup.object().shape({
    contactname: Yup.string()
      .min(2, "Too Short!")
      .max(50, "Too Long!")
      .required("Required"),
      contactnumber: Yup.string()
      .min(10, "Too Short!")
      .max(10, "Too Long!")
      .required("Required"),
    email: Yup.string().email("Invalid email").required("Required"),
      description: Yup.string()
      .min(2, "Too Short!")
      .max(50, "Too Long!")
      .required("Required"),
  });

export default class CreateContact extends Component {
    state = {
        result: [],
        id: 0,
        loader: false,
      };


    componentDidMount = async () => {
        try {
            this.setState({ loader: true });
            if (
                this.props.location &&
                this.props.location.state &&
                this.props.location.state.userId
            ) {
                const propId =
                    this.props.location &&
                    this.props.location.state &&
                    this.props.location.state.userId;

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
        let show = (<i className="icon-eye"></i>);
        let hide = (<i className="fa fa-eye-slash"></i>);
        return (
            <div className="userAdd__wrapper">
                <h3>User Information</h3>
                <br />
                <Formik
                    enableReinitialize={true}
                    initialValues={{
                        contactname: this.state && this.state.result &&
                        this.state.result.firstname + " " + this.state.result.lastname,
                        contactnumber:
                        this.state && this.state.result && this.state.result.phoneNumber,
                        email: this.state && this.state.result && this.state.result.email,
                        userId: this.state.id,
                        description: "",
                    }}
                    validationSchema={SignupSchema}
                    onSubmit={async values => {
                        let category = await localStorage.getItem("categoryname")
                        const data = {
                            // phoneNumber: values.phone,
                            contactname: values.contactname,
                            contactnumber: values.contactnumber,
                            fkUserId: this.state.id,
                            email: values.email,
                            description: values.description,
                            category: category,
                            role: values.type_of === "user" ? 'user' : values.type_of === "admin" ? 'admin' : values.type_of === "organization" ? 'organization' : 'super-admin'
                        }
                        console.log('data', data);
                        let result = await postContact(data);
                        if (result.status === 200) {
                            swal("Great", "Data added successfully", "success");
                            this.props.history.push("/Contact_List")
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
                                    <Field className="form-control" name="contactname" placeholder="First Name" />
                                    {errors.contactname && touched.contactname ? (
                                        <div className="error__msg">
                                            {errors.contactname}
                                        </div>
                                    ) : null}
                                </div>

                                <div>
                                    <label className='label'>Phone No</label>
                                    <Field className="form-control" name="contactnumber" placeholder="Phone" maxlength="10" />
                                    {/* {errors.phone && touched.phone ? <div className="error__msg">{errors.phone}</div> : null} */}
                                </div>
                                <div>
                                    <label className='label'>Email Id</label>
                                    <Field className="form-control" name="email" type="email" placeholder="Email_Id" validate={this.validateEmail} />
                                    {errors.email && touched.email ? (
                                        <div className="error__msg">
                                            {errors.email}
                                        </div>
                                    ) : null}
                                </div>
                                <div>
                                    <label className='label1'>Description</label>
                                    <Field className="form-control" as="textarea" name="description" placeholder="Enter Description" />
                                    {errors.description && touched.description ? (
                                  <div className="error__msg">
                                    {errors.description}
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
