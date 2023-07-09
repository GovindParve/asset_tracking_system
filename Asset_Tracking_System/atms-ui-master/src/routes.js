import React from 'react';

import Breadcrumbs from './views/Base/Breadcrumbs';
import Dashboard from './views/Dashboard';
import Dashboard_GPS from './views/Dashboard_GPS/Dashboard_GPS';
import Dashboard_Gps_UserList from './views/Dashboard_GPS/Dashboard_Gps_UserList';
import Dashboard_Gps_AdminList from './views/Dashboard_GPS/Dashboard_Gps_AdminList';
import Dashboard_Gps_OrgList from './views/Dashboard_GPS/Dashboard_Gps_OrgList';
import Dashboard_Gps_EmpuserList from './views/Dashboard_GPS/Dashboard_Gps_EmpuserList';
import WorkingGPSTagList from './views/Dashboard_GPS/WorkingGPSTagList';
import NonWorkingGPSTagList from './views/Dashboard_GPS/NonWorkingGPSTagList';
import WorkingTag from './views/Dashboard/WorkingTag';
import NonWorkingTag from './views/Dashboard/NonWorkingTag';
import WorkingGateway from './views/Dashboard/WorkingGateway';
import NonWorkingGateway from './views/Dashboard/NonWorkingGateway';
import DashboarduserList from './views/Dashboard/DashboarduserList';
import DashboardAdminList from './views/Dashboard/DashboardAdminList';
import DashboardOrgList from './views/Dashboard/DashboardOrgList';
import EmpUserListDashboard from './views/Dashboard/EmpUserListDashboard';
import BatteryPercentage from './containers/DefaultLayout/BatteryPercentage';
import IssueList from './views/Issue/IssueList';
import Gps_IssueList from './views/Issue/Gps_IssueList';
import ContactList from './views/ContactList/ContactList';
import Gps_ContactList from './views/ContactList/Gps_ContactList';
import UserList from './views/UserList/UserList';
import EmpUserList from './views/UserList/EmpUserList';
import UserGpsList from './views/UserList/UserGpsList';
import EmpGpsUserList from './views/UserList/EmpGpsUserList';
import UserAdd from './views/UserAdd/UserAdd';
import EmpUserAdd from './views/UserAdd/EmpUserAdd';
import AdminEmpUserAdd from './views/UserAdd/AdminEmpUserAdd';
import IssueCreate from './views/Issue/IssueCreate';
import CreateContact from './views/ContactList/CreateContact';
import GatwayList from './views/Gatway/GatwayList';
import OrgGatway from './views/Gatway/OrgGatway';
import ProductList from './views/Product/ProductList';
import AddProductList from './views/Product/AddProductList';
import ProductGpsList from './views/Product/ProductGpsList';
import AddGPSProductList from './views/Product/AddGPSProductList';
import AddGatway from './views/Gatway/AddGatway';
import AddOrgGateway from './views/Gatway/AddOrgGateway';
import AddAdminGateway from './views/Gatway/AddAdminGateway';
import Devices from './views/Devices/Devices';
import AddCategory from './views/AddCategory/AddCategory';
import UploadFile from './views/AssetTags/uploadExcel';
import UploadFileForOrg from './views/AssetTags/UploadExcelForOrg';
import AllocateProduct from './views/Product/ProductAllocation';
import ProductGpsAllocation from './views/Product/ProductGpsAllocation';
import CreateDevice from './views/CreateDevice/CreateDevice';
import AssetTags from './views/AssetTags/AssetTags';
import OrgTags from './views/AssetTags/OrgTags';
import AssetGps from './views/AssetTags/AssetGps';
import CreateAssetTag from './views/AssetTags/CreateAssetTag';
import TagCreateOrg from './views/AssetTags/TagCreateOrg';
import TagCreateAdmin from './views/AssetTags/TagCreateAdmin';
import CreateProduct from './views/Product/CreateProduct';
import CreateGpsProduct from './views/Product/CreateGpsProduct';
import ProductAllocateHistory from './views/Product/ProductAllocateHistory';
import DeleteHistory from './views/Product/DeleteHistory';
import UpdateAssetTag from './views/AssetTags/UpdateAssetTag';
import UpdateOrgAssetTag from './views/AssetTags/UpdateOrgAssetTag';
import UpdateAdminAssetTag from './views/AssetTags/UpdateAdminAssetTag';
import MainTracking from './views/Tracking/mainTracking';
import Gps_MainTracking from './views/Tracking_GPS/Gps_MainTracking';
import SingleTracking from './views/Tracking/singleTracking';
import Payloads from './views/Payloads/Payloads';
import UpdateDevice from './views/CreateDevice/UpdateDevice';
import UpdateUsers from './views/UserAdd/UpdateUsers';
import UpdateGpsUsers from './views/UserAdd/UpdateGpsUser';
import UpdateEmp from './views/UserAdd/UpdateEmp';
import UpdateGatway from './views/Gatway/UpdateGatway';
import UpdateOrgGateway from './views/Gatway/UpdateOrgGateway';
import UpdateAdminGateway from './views/Gatway/UpdateAdminGateway';
import UpdateProduct from './views/Product/UpdateProduct';
import UpdateAllProduct from './views/Product/UpdateAllProduct';
import GatewaywiseTab from './views/Alert/GatewaywiseTab';
import Reports from './views/Reports/Reports';
import Report_GPS from './views/Report_GPS/Report_GPS';
import Billing from './views/Billing/Billing';
import UploadGatewayExcel from './views/Gatway/uploadGatewayExcel';
import UploadGatewayExcelForOrg from './views/Gatway/UploadGatewayExcelForOrg';
import uploadUserExcel from './views/UserList/uploadUserExcel';
import UploadOnlyAdmin from './views/UserList/UploadOnlyAdmin';
import uploadEmpUserExcel from './views/UserList/uploadEmpUserExcel';
import UploadProductExcel from './views/Product/UploadProductExcel';
import UploadProductAllocExcel from './views/Product/UploadProductAllocExcel';
import DynamicColumn from './views/DynamicColumn/DynamicColumn';
import DynamicColumnGpsList from './views/DynamicColumn/DynamicColumnGpsList';
import CreateColunm from './views/DynamicColumn/CreateColunm';
import UpdateColumn from './views/DynamicColumn/UpdateColumn';
import AdminColumnList from './views/DynamicColumn/AdminColumnList';
import GpsAdminColumnList from './views/DynamicColumn/GpsAdminColumnList';
import AdminCreateColunm from './views/DynamicColumn/AdminCreateColunm';



// const Breadcrumbs = React.lazy(() => import('./views/Base/Breadcrumbs'));
// const Dashboard = React.lazy(() => import('./views/Dashboard'));
// const BatteryPercentage = React.lazy(() => import('./containers/DefaultLayout/BatteryPercentage'));
// const UserList = React.lazy(() => import('./views/UserList/UserList'));
// const UserAdd = React.lazy(() => import('./views/UserAdd/UserAdd'));
// const GatwayList = React.lazy(() => import('./views/Gatway/GatwayList'));
// const ProductList = React.lazy(() => import('./views/Product/ProductList'));
// const AddGatway = React.lazy(() => import('./views/Gatway/AddGatway'));
// const Devices = React.lazy(() => import('./views/Devices/Devices'));
// const AddCategory = React.lazy(() => import('./views/AddCategory/AddCategory'));
// const UploadFile = React.lazy(() => import('./views/AssetTags/uploadExcel'));
// const AllocateProduct = React.lazy(() => import('./views/Product/ProductAllocation'));
// const CreateDevice = React.lazy(() => import('./views/CreateDevice/CreateDevice'));
// const AssetTags = React.lazy(() => import('./views/AssetTags/AssetTags'));
// const CreateAssetTag = React.lazy(() => import('./views/AssetTags/CreateAssetTag'));
// const CreateProduct = React.lazy(() => import('./views/Product/CreateProduct'));
// const ProductAllocateHistory = React.lazy(() => import('./views/Product/ProductAllocateHistory'));
// const UpdateAssetTag = React.lazy(() => import('./views/AssetTags/UpdateAssetTag'));
// const MainTracking = React.lazy(() => import('./views/Tracking/mainTracking'));
// const SingleTracking = React.lazy(() => import('./views/Tracking/singleTracking'));
// const Payloads = React.lazy(() => import('./views/Payloads/Payloads'));
// const UpdateDevice = React.lazy(() => import('./views/CreateDevice/UpdateDevice'));
// const UpdateUsers = React.lazy(() => import('./views/UserAdd/UpdateUsers'));
// const UpdateGatway = React.lazy(() => import('./views/Gatway/UpdateGatway'));
// const UpdateProduct = React.lazy(() => import('./views/Product/UpdateProduct'));
// const GatewaywiseTab = React.lazy(() => import('./views/Alert/GatewaywiseTab'));
// const Reports = React.lazy(() => import('./views/Reports/Reports'));
// const ViewTagWise = React.lazy(() => import('./views/Reports/ViewTagWise'));
// const Billing = React.lazy(() => import('./views/Billing/Billing'));
// const UploadGatewayExcel = React.lazy(() => import('./views/Gatway/uploadGatewayExcel'));
// const uploadUserExcel = React.lazy(() => import('./views/UserList/uploadUserExcel'));



// https://github.com/ReactTraining/react-router/tree/master/packages/react-router-config

const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/dashboard_GPS', name: 'Dashboard_GPS', component: Dashboard_GPS },
  { path: '/dashboard_Gps_userlist', name: 'Dashboard_Gps_UserList', component: Dashboard_Gps_UserList },
  { path: '/dashboard_Gps_adminlist', name: 'Dashboard_Gps_AdminList', component: Dashboard_Gps_AdminList },
  { path: '/dashboard_Gps_orglist', name: 'Dashboard_Gps_OrgList', component: Dashboard_Gps_OrgList },
  { path: '/dashboard_Gps_empuserlist', name: 'Dashboard_Gps_EmpUserList', component: Dashboard_Gps_EmpuserList },
  { path: '/dashboard-user-list', name: 'Dashboard / DashboarduserList', component: DashboarduserList },
  { path: '/dashboard-admin-list', name: 'Dashboard / DashboardAdminList', component: DashboardAdminList },
  { path: '/dashboard-org-list', name: 'Dashboard / DashboardOrgList', component: DashboardOrgList },
  { path: '/dashboard-emp-list', name: 'Dashboard / DashboardEmpUserList', component: EmpUserListDashboard},
  { path: '/working-gps-tag', name: 'Dashboard_GPS / WorkingGPSTagList', component: WorkingGPSTagList },
  { path: '/Nonworking-gps-tag', name: 'Dashboard_GPS / NonWorkingGPSTagList', component: NonWorkingGPSTagList },
  { path: '/working-tag', name: 'Dashboard / WorkingTag', component: WorkingTag },
  { path: '/Nonworking-tag', name: 'Dashboard / NonWorkingTag', component: NonWorkingTag },
  { path: '/working-gateway', name: 'Dashboard / WorkingGateway', component: WorkingGateway },
  { path: '/Nonworking-gateway', name: 'Dashboard / NonWorkingGateway', component: NonWorkingGateway },
  { path: '/battery-percentage', name: 'Asset_Tag_Wise_Battery_Percentage', component: BatteryPercentage },
  { path: '/base/breadcrumbs', name: 'Breadcrumbs', component: Breadcrumbs },
  { path: '/Issue_list', name: 'Issue List', component: IssueList },
  { path: '/Gps_IssueList', name: 'GPS Issue List', component: Gps_IssueList },
  { path: '/Contact_List', name: 'Contact List', component: ContactList },
  { path: '/Gps_Contact_List', name: 'Gps Contact List', component: Gps_ContactList },
  { path: '/Contact_Create/:id', name: 'Create Contact', component: CreateContact },
  { path: '/users', name: 'User List', component: UserList },
  { path: '/emp-users', name: 'Employee User List', component: EmpUserList },
  { path: '/gps-users', name: 'User Gps List', component: UserGpsList },
  { path: '/gps-emp-users', name: 'User Gps List', component: EmpGpsUserList },
  { path: '/product-list', name: 'Product Allocation List', component: ProductList },
  { path: '/list-of-product', name: 'Product List', component: AddProductList },
  { path: '/gps-product-list', name: 'Product Gps List', component: ProductGpsList },
  { path: '/gps-list-of-product', name: 'Product Gps List', component: AddGPSProductList },
  { path: '/create', name: 'User List / User Add', component: UserAdd },
  { path: '/empuser-create', name: 'Employee User List / Emp User Add', component: EmpUserAdd },
  { path: '/admin-empuser-create', name: 'Employee User List / Emp User Add', component: AdminEmpUserAdd },
  { path: '/create_Issue/:id', name: 'Issue Add', component: IssueCreate },
  { path: '/gatway_list', name: 'Gateway List', component: GatwayList },
  { path: '/org_gatway_list', name: 'Organization Gateway List', component: OrgGatway },
  { path: '/add-gatway', name: 'Gateway List / Add Gateway', component: AddGatway },
  { path: '/add-Org-gatway', name: 'Gateway List / Add Gateway', component: AddOrgGateway },
  { path: '/add-Admin-gatway', name: 'Gateway List / Add Gateway', component: AddAdminGateway },
  { path: '/device', name: 'Devices', component: Devices },
  { path: '/add-category', name: 'Add Category', component: AddCategory },
  { path: '/upload-excel', name: 'Upload Excel', component: UploadFile },
  { path: '/upload-excel-for-org', name: 'Upload Excel For Organization', component: UploadFileForOrg },
  { path: '/product-allocation', name: 'Product Allocation List / Product Allocation', component: AllocateProduct },
  { path: '/product-gps-allocation', name: 'Product GPS Allocation', component: ProductGpsAllocation },
  { path: '/device-create', name: 'Create Device', component: CreateDevice },
  { path: '/product-create', name: 'Product List / Create Product', component: CreateProduct },
  { path: '/product-gps-create', name: 'Product Gps List / Create Gps Product', component: CreateGpsProduct },
  { path: '/asset-tag', name: 'Asset Tag', component: AssetTags },
  { path: '/org-asset-tag', name: 'Organization Asset Tag', component: OrgTags },
  { path: '/asset-gps', name: 'Asset GPS Tag', component: AssetGps },
  { path: '/asset-tag-create', name: 'Asset Tag / Create Asset Tag', component: CreateAssetTag },
  { path: '/asset-tag-Org-create', name: 'Asset Tag / Create Asset Tag', component: TagCreateOrg },
  { path: '/asset-tag-Admin-create', name: 'Asset Tag / Create Asset Tag', component: TagCreateAdmin },
  { path: '/payloads', name: 'Payload', component: Payloads },
  { path: '/main-tracking', name: 'Asset_Tracking', component: MainTracking },
  { path: '/gps-maintracking', name: 'GPS_Tracking', component: Gps_MainTracking },
  { path: '/singleTag-tracking', name: 'Asset_Tag_Wise_Tracking', component: SingleTracking },
  { path: '/GatewaywiseTab', name: 'GatewaywiseTab', component: GatewaywiseTab },
  { path: '/update-device/:id', name: 'Update Device', component: UpdateDevice },
  { path: '/update-product/:id', name: 'Update Product Allocation', component: UpdateProduct },
  { path: '/edit-product/:id', name: 'Product List / Update Product', component: UpdateAllProduct },
  { path: '/product-allocate-history', name: 'Tag Allocate History', component: ProductAllocateHistory },
  { path: '/product-delete-history', name: 'Delete History', component: DeleteHistory },
  { path: '/update-user/:id', name: 'Update User', component: UpdateUsers },
  { path: '/update-Gps-user/:id', name: 'Update Gps User', component: UpdateGpsUsers },
  { path: '/update-emp/:id', name: 'Update Employee', component: UpdateEmp },
  { path: '/update-gatway/:id', name: 'Gateway List / Update Gateway', component: UpdateGatway },
  { path: '/update-org-gatway/:id', name: 'Gateway List / Update Gateway', component: UpdateOrgGateway },
  { path: '/update-admin-gatway/:id', name: 'Gateway List / Update Gateway', component: UpdateAdminGateway },
  { path: '/update-asset-tag/:id', name: 'Asset Tag / Update Asset Tag', component: UpdateAssetTag },
  { path: '/update-org-asset-tag/:id', name: 'Asset Tag / Update Asset Tag', component: UpdateOrgAssetTag },
  { path: '/update-admin-asset-tag/:id', name: 'Asset Tag / Update Asset Tag', component: UpdateAdminAssetTag },
  { path: '/report', name: 'Reports', component: Reports },
  { path: '/report_gps', name: 'Report_GPS', component: Report_GPS },
  { path: '/billing', name: 'Billing', component: Billing },
  { path: '/upload-gateway-excel', name: 'Upload Gateway Excel', component: UploadGatewayExcel },
  { path: '/upload-gateway-excel-for-org', name: 'Upload Gateway Excel For Organization', component: UploadGatewayExcelForOrg },
  { path: '/upload-user-excel', name: 'Upload User Excel', component: uploadUserExcel },
  { path: '/upload-onlyadmin-excel', name: 'Upload only admin Excel', component: UploadOnlyAdmin },
  { path: '/upload-empuser-excel', name: 'Upload Employee User Excel', component: uploadEmpUserExcel },
  { path: '/upload-product-excel', name: 'Upload Product Excel ', component: UploadProductExcel },
  { path: '/upload-product-alloc-excel', name: 'Upload Product Excel', component: UploadProductAllocExcel },
  { path: '/column-list', name: 'Column List', component: DynamicColumn },
  { path: '/gps-column-list', name: 'Gps Column List', component: DynamicColumnGpsList },
  { path: '/column-create', name: 'Column List / Create Column', component: CreateColunm },
  { path: '/update-column/:id', name: 'Column List/Update Column', component: UpdateColumn },
  { path: '/admin-column-list', name: 'Admin Column List', component: AdminColumnList },
  { path: '/gps-admin-column-list', name: 'Admin GPS Column List', component: GpsAdminColumnList },
  { path: '/admin-column-create', name: 'Admin Column List / Admin Create Column', component: AdminCreateColunm },
  
];

export default routes;
