import React, { Component } from 'react';
import { Button, Card, CardBody, CardFooter, Col, Container, Form, Input, InputGroup, InputGroupAddon, InputGroupText, Row } from 'reactstrap';
import { postResetPassword } from "../../../Service/postResetPassword";
import { Formik, Field } from "formik";
import * as Yup from "yup";
import swal from "sweetalert";
import "../Login/Login.css"

  const SignupSchema = Yup.object().shape({
    password: Yup.string().min(6).required("Required"),
    repeatpassword: Yup.string()
      .oneOf([Yup.ref("password")], "Passwords does not match")
      .required("Required"),
  });
  class Register extends Component {
 
  render() {
    // let show = (<i className="icon-eye"></i>);
    // let hide = (<i className="fa fa-eye-slash"></i>);
    return (
      <div className="app LoginBack flex-row align-items-center">
        <Container>
          <Row className="justify-content-center">
            <Col md="9" lg="7" xl="6">
              <Card className="mx-4">
                <CardBody className="register-form p-4">
                <Formik
                    initialValues={{
                      password: "",
                      repeatpassword: "",
                    }}
                    validationSchema={SignupSchema}
                    onSubmit={async (values) => {
                      const data = {
                        password: values.password,
                        token: this.state.currentToken,
                      };
                      console.log("data", data);
                      let result = await postResetPassword(data);
                      console.log("result", result);
                      if (result.status === 200) {
                        swal("Great", "Password reset successfully", "success");
                        this.props.history.push("/login");
                      } else {
                        swal(
                          "Failed",
                          "Something went wrong please check your internet",
                          "error"
                        );
                      }
                    }}
                    
                  >
                    {({ errors, touched }) => (
                      <Form >
                      <div className="registerHead">
                        <h1>Reset Password</h1>
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
                            //type={this.state.isRevealPwd ? "text" : "password"}
                            name="password"
                            placeholder="Password"
                          />
                          {/* <InputGroupText>
                          <span onClick={this.toggleShow}>{this.state.isRevealPwd ? hide : show}</span>
                        </InputGroupText> */}
                        </InputGroup>
                        {errors.password && touched.password ? (
                          <div className="error__msg">{errors.password}</div>
                        ) : null}
                        <InputGroup className="mb-2 mt-3">
                          <InputGroupAddon addonType="prepend">
                            <InputGroupText>
                              <i className="icon-lock"></i>
                            </InputGroupText>
                          </InputGroupAddon>
                          <Field className="form-control" 
                          // type={this.state.isRevealPwdR ? "text" : "password"} 
                          name="repeatpassword" placeholder="Repeat password"/>
                          {/* <InputGroupText>
                          <span onClick={this.toggleShowR}>{this.state.isRevealPwdR ? hide : show}</span>
                        </InputGroupText> */}
                        </InputGroup>
                        {errors.repeatpassword && touched.repeatpassword ? (
                          <div className="error__msg">
                            {errors.repeatpassword}
                          </div>
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
                {/* <CardFooter className="p-4">
                  <Row>
                    <Col xs="12" sm="6">
                      <Button className="btn-facebook mb-1" block><span>facebook</span></Button>
                    </Col>
                    <Col xs="12" sm="6">
                      <Button className="btn-twitter mb-1" block><span>twitter</span></Button>
                    </Col>
                  </Row>
                </CardFooter> */}
              </Card>
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

export default Register;
