import React, { Component, Suspense } from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';
import * as router from 'react-router-dom';
import { Container } from 'reactstrap';
import { connect } from 'react-redux'
import { activateGeod, closeGeod } from '../../Store';
import {
  AppAside,
  AppFooter,
  AppHeader,
  AppSidebar,
  AppSidebarFooter,
  AppSidebarForm,
  AppSidebarHeader,
  AppSidebarMinimizer,
  AppBreadcrumb2 as AppBreadcrumb,
  AppSidebarNav2 as AppSidebarNav,
} from '@coreui/react';
// sidebar nav config
import navigation from '../../_nav';
import navigationu from '../../_navu';
import navigationa from '../../_nava';
import navigationo from '../../_navao';
import navigationgs from '../../_navgs';
import navigationga from '../../_navga';
import navigationgo from '../../_navgo';
import navigationgu from '../../_navgu';
// routes config
import routes from '../../routes';
import DefaultFooter from './DefaultFooter';
import DefaultHeader from './DefaultHeader';

// const DefaultFooter = React.lazy(() => import('./DefaultFooter'));
// const DefaultHeader = React.lazy(() => import('./DefaultHeader'));

class DefaultLayout extends Component {
  state = {
    Role: "",
    categoryName: "",
    deviceCat: "",
    runOnce: true,
    gasAdmin: navigationa,
    gasOrg: navigationo,
    gasUser: navigationu,
    gasSuperAdmin: navigation,
    gpsAdmin: navigationga,
    gps: false

  }
  loading = () => <div className="animated fadeIn pt-1 text-center">Loading...</div>

  signOut(e) {
    e.preventDefault()
    this.props.activateGeod({ title: false })
    localStorage.clear();
      
  }
  componentDidMount() {
    setTimeout(() => {
      var setRole = localStorage.getItem('role');
      var setCategory = localStorage.getItem('categoryname');

      console.log("set Category", setCategory)
      this.setState({ Role: setRole, deviceCat: setCategory }, () => {

        if (this.state.deviceCat === "GPS") {
          let updatecat = navigationgs
          this.setState({ gasSuperAdmin: updatecat, gps: true }, () => {

          })
        }

        if (this.state.deviceCat === "GPS") {
          let updatecat = navigationgo
          this.setState({ gasOrg: updatecat, gps: true }, () => {
          })
        }


        if (this.state.deviceCat === "GPS") {
          let updatecat = navigationga
          this.setState({ gasAdmin: updatecat, gps: true }, () => {
          })
        }

        if (this.state.deviceCat === "GPS") {
          let updatecat = navigationgu
          this.setState({ gasUser: updatecat, gps: true }, () => {
          })
        }
        console.log("Role", this.state.Role)
      })

    }, 200)

  }


  handleCategory = (catValue) => {
    this.setState({ categoryName: catValue }, () => {
      console.log("category Name", this.state.categoryName)
      if (this.state.categoryName === "BLE") {
        let updatecat = navigation
        this.setState({ gasSuperAdmin: updatecat }, () => {
        })
      }
      else if (this.state.categoryName === "GPS") {
        let updatecat = navigationgs
        this.setState({ gasSuperAdmin: updatecat }, () => {
        })
      }
      if (this.state.categoryName === "BLE") {
        let updatecat = navigationa
        this.setState({ gasAdmin: updatecat }, () => {
        })
      }
      else if (this.state.categoryName === "GPS") {
        let updatecat = navigationga
        this.setState({ gasAdmin: updatecat }, () => {
        })
      }
      if (this.state.categoryName === "BLE") {
        let updatecat = navigationo
        this.setState({ gasOrg: updatecat }, () => {
        })
      }
      else if (this.state.categoryName === "GPS") {
        let updatecat = navigationgo
        this.setState({ gasOrg: updatecat }, () => {
        })
      }
      if (this.state.categoryName === "BLE") {
        let updatecat = navigationu
        this.setState({ gasUser: updatecat }, () => {
        })
      }
      else if (this.state.categoryName === "GPS") {
        let updatecat = navigationgu
        this.setState({ gasUser: updatecat }, () => {
        })
      }
      // if (this.state.categoryName === "GPS") {
      //   let updatecat = navigationga
      //   this.setState({ gpsAdmin: updatecat}, () => {
      //     console.log("GPS Login",this.state.gpsAdmin)
      //   })

      // }
    });
  }
  render() {
    return (
      <div className="app">
        <AppHeader fixed>
          <Suspense fallback={this.loading()}>
            <DefaultHeader onLogout={e => this.signOut(e)} onSelectCategory={this.handleCategory} />
          </Suspense>
        </AppHeader>
        <div className="app-body">
          <AppSidebar fixed display="lg">
            <AppSidebarHeader />
            <AppSidebarForm />
            <Suspense>
              {
                // (this.state.Role === 'admin') ?
                //   (
                //     <AppSidebarNav navConfig={this.state.gasAdmin} {...this.props} router={router} />
                //   )
                //   : (this.state.Role) === 'user' ?
                //   (
                //     <AppSidebarNav navConfig={this.state.gasUser} {...this.props} router={router} />
                //   ) 
                //   : (this.state.Role) === 'organization' ?
                //   (
                //     <AppSidebarNav navConfig={this.state.gasOrg} {...this.props} router={router} />
                //   )
                //   //For Gps
                //   : 
                (this.state.Role === 'user' || this.state.Role === 'empuser') ?
                  (
                    <AppSidebarNav navConfig={this.state.gasUser} {...this.props} router={router} />
                  )
                  : (this.state.Role === 'organization') ?
                    (
                      <AppSidebarNav navConfig={this.state.gasOrg} {...this.props} router={router} />
                    )
                    : (this.state.Role === 'admin') ?
                      (
                        <AppSidebarNav navConfig={this.state.gasAdmin} {...this.props} router={router} />

                      )
                      : (this.state.Role === 'super-admin') ?
                        (
                          <AppSidebarNav navConfig={this.state.gasSuperAdmin} {...this.props} router={router} />
                        )
                        :
                        (
                          ""
                        )
              }
            </Suspense>
            <AppSidebarFooter />
            <AppSidebarMinimizer />
          </AppSidebar>
          <main className="main">
            {/* <div className='routeHead'> */}
              <AppBreadcrumb appRoutes={routes} router={router} />
            {/* </div> */}
            <Container fluid>
              <Suspense fallback={this.loading()}>
                <Switch>
                  {routes.map((route, idx) => {
                    return route.component ? (
                      <Route
                        key={idx}
                        path={route.path}
                        exact={route.exact}
                        name={route.name}
                        render={(props) => <route.component {...props} />}
                      />
                    ) : (null);
                  })}
                  <Redirect from="/" to="/dashboard" />
                </Switch>
              </Suspense>
            </Container>
          </main>
        </div>
        <AppFooter>
          <Suspense fallback={this.loading()}>
            <DefaultFooter />
          </Suspense>
        </AppFooter>
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
)(DefaultLayout);
export default AppContainer;

