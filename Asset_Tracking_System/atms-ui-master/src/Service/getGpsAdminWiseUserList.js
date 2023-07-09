import Axios from "../utils/axiosInstance";

export const getGpsAdminWiseUserList = () => {
  let token = localStorage.getItem("token");
  let fkUserId = localStorage.getItem("fkUserId");
  let role = localStorage.getItem("role");
  let category = ["GPS"];
  return Axios.get(`/user/get-adminlist_for_dropdown?fkUserId=${fkUserId}&role=${role}&category=${category}`,{ headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
    fkUserId: localStorage.getItem('fkUserId'),
   // role: localStorage.getItem('role'),
  }) 
})
};