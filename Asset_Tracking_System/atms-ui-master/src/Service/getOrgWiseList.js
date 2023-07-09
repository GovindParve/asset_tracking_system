import Axios from "../utils/axiosInstance";

export const getOrgWiseList = () => {
  let token = localStorage.getItem("token");
  let fkUserId = localStorage.getItem("fkUserId");
  //let role = localStorage.getItem("role");
  let category = ["BLE"];
  return Axios.get(`/user/get-organization_list?fkUserId=${fkUserId}&category=${category}`,{ headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
    fkUserId: localStorage.getItem('fkUserId'),
   // role: localStorage.getItem('role'),
  }) 
})
};