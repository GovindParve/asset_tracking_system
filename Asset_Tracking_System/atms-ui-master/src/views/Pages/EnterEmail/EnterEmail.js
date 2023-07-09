import React, { Component } from "react";
import Axios from "../../../utils/axiosInstance";
import { postEmail } from "../../../Service/postEmail";
import {
  Button,
  Card,
  CardBody,
  CardFooter,
  Col,
  Container,
  Input,
  InputGroup,
  InputGroupAddon,
  InputGroupText,
  Row,
} from "reactstrap";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import swal from "sweetalert";
import "../Login/Login.css"

const SignupSchema = Yup.object().shape({
  email: Yup.string().email("Invalid email").required("Required"),
});

class EnterEmail extends Component {
  render() {
    return (
      <div className="app LoginBack flex-row align-items-center">
        <Container>
          <Row className="justify-content-center">
            <Col md="9" lg="7" xl="6">
              <Card className="mx-4">
                <CardBody className="register-form p-4">
                  <Formik
                    initialValues={{
                      email: "",
                    }}
                    validationSchema={SignupSchema}
                    onSubmit={async (values) => {
                      return await Axios.post(`/users/forgot-password?email=${values.email}`)
                        .then(resultResponse => {
                          swal(
                            "Success",
                            "Reset Pasword Link sending on your Email",
                            "success"
                          );
                          return Promise.resolve();
                        }).catch(resultResponse => {
                          swal(
                            "Error",
                            "Please Enter Register Email Id",
                            "error"
                          );
                        });
                    }
                  }
                    // const data = {
                    //     email: values.email,
                    //   };
                    //   console.log("data", data);
                    //   let result = await postEmail(data);
                    //   console.log("result", result);
                    //   if (result.status === 200) {
                    //     swal(
                    //       "Great",
                    //       "Email sent successfully on registered email",
                    //       "success"
                    //     );
                    //     this.props.history.push("/login");
                    //   } else {
                    //     swal(
                    //       "Failed",
                    //       "Something went wrong please check your internet",
                    //       "error"
                    //     );
                    //   }
                    // }}
                  >
                    {({ errors, touched }) => (
                      <Form>
                        <div className="registerHead">
                          <h1>Enter Registered Email</h1>
                        </div>
                        <br />
                        <InputGroup className="mb-2">
                          <InputGroupAddon addonType="prepend">
                            <InputGroupText>
                              <i className="icon-lock"></i>
                            </InputGroupText>
                          </InputGroupAddon>
                          <Field
                            className="form-control"
                            type="email"
                            name="email"
                            placeholder="Email"
                          />
                        </InputGroup>
                        {errors.email && touched.email ? (
                          <div className="error__msg">{errors.email}</div>
                        ) : null}
                        <br />
                        <div className='' align="center">
                          <button type="submit" className="px-4 btn-registerbtn">
                            SUBMIT
                          </button>
                        </div>
                      </Form>
                    )}
                  </Formik>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

export default EnterEmail;
