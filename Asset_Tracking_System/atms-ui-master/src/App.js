import React, { Component } from 'react';
import { BrowserRouter, Route, Redirect, Switch } from 'react-router-dom';
import { connect } from 'react-redux'
// import { renderRoutes } from 'react-router-config';
import { activateGeod, closeGeod } from './Store';
import EnterEmail from "./views/Pages/EnterEmail";
//import BatteryPercentage from "./containers/DefaultLayout/BatteryPercentage";
import './App.scss';

import DefaultLayout from './containers/DefaultLayout';
import Login from './views/Pages/Login';
import Register from './views/Pages/Register';
import Page404 from './views/Pages/Page404';
import Page500 from './views/Pages/Page500';

const loading = () => <div className="animated fadeIn pt-3 text-center">Loading...</div>;

// Containers
// const DefaultLayout = React.lazy(() => import('./containers/DefaultLayout'));
// const Login = React.lazy(() => import('./views/Pages/Login'));
// const Register = React.lazy(() => import('./views/Pages/Register'));
// const Page404 = React.lazy(() => import('./views/Pages/Page404'));
// const Page500 = React.lazy(() => import('./views/Pages/Page500'));

const isAuthenticated = (reduxprops) => {
  //write your condition here
  console.log("initial value", reduxprops && reduxprops.geod && reduxprops.geod.title && reduxprops.geod.title && reduxprops.geod.title != undefined)
  if (reduxprops && reduxprops.geod && reduxprops.geod.title && reduxprops.geod.title) {
    return true;
  } else {

    return false;

  }
}

const UnauthenticatedRoute = ({ component: Component, reduxprops, ...rest }) => (
  <Route {...rest} render={(props) => (
    !isAuthenticated(reduxprops)
      ? <Component {...props} />
      : <Redirect to='/' />
  )} />

);

const AuthenticatedRoute = ({ component: Component, reduxprops, ...rest }) => (
  <Route {...rest} render={(props) => (
    isAuthenticated(reduxprops)
      ? <Component {...props} />
      : <Redirect to='/login' />
  )} />
);
class App extends Component {

  render() {
    return (
      <BrowserRouter basename="/">
        <React.Suspense fallback={loading()}>
          <Switch>
            <UnauthenticatedRoute exact path="/login" reduxprops={this.props} name="Login Page" component={Login} />
            <Route exact path="/register/:token" name="Register Page" component={Register} />
            <Route exact path="/404" name="Page 404" component={Page404} />
            <Route exact path="/500" name="Page 500" component={Page500} />
            <Route exact path="/enter-email" name="Enter Email" component={EnterEmail}/>
            {/* <Route exact path="/battery-percentage" name="Battery-Percentage" component={BatteryPercentage}/> */}
            <AuthenticatedRoute path="/" name="Home" reduxprops={this.props} component={DefaultLayout} />
          </Switch>
        </React.Suspense>
      </BrowserRouter>
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
)(App);
export default AppContainer;

