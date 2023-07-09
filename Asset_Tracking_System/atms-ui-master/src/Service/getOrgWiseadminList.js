import Axios from "../utils/axiosInstance";

export const getOrgWiseadminList = (pageno,role,searchrole) => {
  let token = localStorage.getItem("token");
  let fkUserId = localStorage.getItem("fkUserId");
  let userName = localStorage.getItem("username");
  //let role = localStorage.getItem("role");
  let category = ["BLE"];
  return Axios.get(`/user/get-all-userNameWise-list/${pageno}?userName=${userName}&role=${role}&searchrole=${searchrole}&fkUserId=${fkUserId}&category=${category}`,{ headers: { "Authorization": `Bearer ${token}` },
   //return Axios.get(`/user/get-all-user-details/${pageno}?fkUserId=${fkUserId}&loginrole=${role}&userName=${userName}&searchrole=${searchrole}&category=${category}`,{ headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
    fkUserId: localStorage.getItem('fkUserId'),
    role: localStorage.getItem('role'),
  }) 
})
};