import Axios from "../utils/axiosInstance";

export const getPageWiseTagList = (pageno) => {
  let token = localStorage.getItem("token");
  let fkUserId = localStorage.getItem("fkUserId");
  let role = localStorage.getItem("role");
  let category = ["BLE"];
  return Axios.get(`/Tag/get-Tag_list-with-pagination/${pageno}?category=${category}&fkUserId=${fkUserId}&role=${role}`,{ headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
    fkUserId: localStorage.getItem('fkUserId'),
    role: localStorage.getItem('role'),
  }) 
})
};