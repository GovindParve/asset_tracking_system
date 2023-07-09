import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { login } from '../../../Service/login'
import { connect } from 'react-redux'
import $ from "jquery"
import { Button, Card, CardBody, CardGroup, Col, Container, Form, InputGroup, InputGroupAddon, InputGroupText, Row } from 'reactstrap';
import { activateGeod, closeGeod } from '../../../Store';
import "./Login.css"
import swal from 'sweetalert';
import Swal from 'sweetalert2'
class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      emailId: "",
      password: "",
      isRevealPwd: false
    }
    this.toggleShow = this.toggleShow.bind(this);
  }

  onChangeRole = (e) => {
    //this.setState({ emailId: e.target.value })
  }
  onChangeEmail = (e) => {
    this.setState({ emailId: e.target.value })
  }

  onChangePass = (e) => {
    this.setState({ password: e.target.value })
  }
  toggleShow() {
    this.setState({ isRevealPwd: !this.state.isRevealPwd });
  }
  submitLogin = async () => {
    try {
      setTimeout(async () => {
      const data = {
        emailid: this.state.emailId,
        passWord: this.state.password
      }

      let result = await login(data)
      console.log('result', result);
      let tempOrgArray = result && result.data && result.data.category.map((obj) => {

        return obj.categoryName
      })
      console.log('result', tempOrgArray);

      var d = new Date();

      await localStorage.setItem("loginStatus", true)
      await localStorage.setItem("token", result && result.data.token)
      await localStorage.setItem("refreshToken", result && result.data.refreshToken)
      await localStorage.setItem("loginTime", d)
      await localStorage.setItem("username", result && result.data.perList[0].username)
      await localStorage.setItem("role", result && result.data.perList[0].role)
      await localStorage.setItem("fkUserId", result && result.data.perList[0].pkuserId)
      await localStorage.setItem("firstname", result && result.data.perList[0].firstName)
      await localStorage.setItem("lastname", result && result.data.perList[0].lastName)
      await localStorage.setItem("organization", result && result.data.perList[0].organization)
      await localStorage.setItem("categoryname", tempOrgArray)

      if (localStorage.getItem("categoryname") !== "GPS" && localStorage.getItem("categoryname") !== "BLE") {
        await localStorage.setItem("SelectedCategory", "BLE")

      }
      else if (localStorage.getItem("categoryname") === "GPS"){
        await localStorage.setItem("SelectedCategory", "GPS")
      }
      else if (localStorage.getItem("categoryname") === "BLE"){
        await localStorage.setItem("SelectedCategory", "BLE")
      }

      if (localStorage.getItem("token") !== "User is Not Active") {
        this.props.activateGeod({ title: true })
        if (localStorage.getItem("categoryname") === "GPS") {
          this.props.history.push("/dashboard_GPS")
        } else {
          this.props.history.push("/")
        }

        if (result.status === 200) {
          Swal.fire({
            position: 'top-center',
            icon: 'success',
            title: 'Login Successfully',
            showConfirmButton: false,
            timer: 1200
          })
        }
      }
      else {
        if (result.status === 200) {
          swal("Login Failed!", "User is Not Active Please contact your admin!", "error");
          //this.props.history.push("/login")
        }
      }

      if (localStorage.getItem("categoryname") === "GPS") {
        this.props.history.push("/dashboard_GPS")
      } else {
        this.props.history.push("/")
      }

      if (result.status === 200) {
        Swal.fire({
          position: 'top-center',
          icon: 'success',
          title: 'Login Successfully',
          showConfirmButton: false,
          timer: 1200
        })
      }
    }, 200);
    }
 
    catch (error) {
      console.log(error)
      swal("Login Failed!", "Check Username And Password!", "error");

    }
  }
  render() {
    let show = (<i className="icon-eye"></i>);
    let hide = (<i className="fa fa-eye-slash"></i>);
    return (
      <div className="app LoginBack flex-row align-items-center">
        <Container>
          <Row className="justify-content-center">
            <Col md="8">
              <CardGroup className="cardmain">
                <Card className="text-white bg-card py-5 d-md-down-none" style={{ width: '44%' }}>
                  <CardBody className="mainbody text-center">
                    <div className="welcomelogin">
                      {/* <h3>Welcome<br/>To<br/>Asset<br/>Management</h3> */}
                      <h3>Asset<br />Tracking<br />Management</h3>
                    </div>
                  </CardBody>
                </Card>

                <Card className="mainlogin p-4">
                  <CardBody className="mainbody">
                    <Form className="loginform">
                      <div className='login-heading'>
                        <h1>LOGIN</h1>
                      </div>
                      <p className="text-mut">Sign in with your account</p>
                      <InputGroup className="mb-3">
                        <InputGroupAddon addonType="prepend">
                          <InputGroupText>
                            <i className="icon-user"></i>
                          </InputGroupText>
                        </InputGroupAddon>
                        <input type="email" className="form-control" placeholder="Username" onChange={this.onChangeEmail} autoComplete="username" />
                      </InputGroup>
                      <InputGroup className="mb-4">
                        <InputGroupAddon addonType="prepend">
                          <InputGroupText>
                            <i className="icon-lock"></i>
                          </InputGroupText>
                        </InputGroupAddon>
                        <input type={this.state.isRevealPwd ? "text" : "password"} className="form-control" placeholder="Password" onChange={this.onChangePass} autoComplete="current-password" />
                        <InputGroupText>
                          <span onClick={this.toggleShow}>{this.state.isRevealPwd ? hide : show}</span>
                        </InputGroupText>
                      </InputGroup>
                      <InputGroup className="forgotPass mb-2">
                        <Link to="/enter-email" className="ancher">Forgot Password?</Link>
                      </InputGroup>
                      <Row>
                        <Col xs="12">
                          <Button className="px-4 btn-loginbtn" onClick={this.submitLogin}>Login</Button>
                        </Col>
                      </Row>
                    </Form>
                  </CardBody>
                </Card>
              </CardGroup>
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  geod: state.geod,
});

const mapDispatchToProps = {
  activateGeod,
  closeGeod,
};

const AppContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(Login);
export default AppContainer;
