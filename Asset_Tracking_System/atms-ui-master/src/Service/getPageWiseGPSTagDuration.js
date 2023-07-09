import Axios from "../utils/axiosInstance";

export const getPageWiseGPSTagDuration = (pageno,tagName,duration) => {
  let token = localStorage.getItem("token");
  let fkUserId = localStorage.getItem("fkUserId");
  let role = localStorage.getItem("role");
  let category = ["GPS"];
  return Axios.get(`/asset/tracking/view-all-tracking-data-with-time-duration-with-pagination/${pageno}?tagName=${tagName}&duration=${duration}&fkUserId=${fkUserId}&role=${role}&category=${category}`,{ headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
    fkUserId: localStorage.getItem('fkUserId'),
    role: localStorage.getItem('role'),
  }) 
})
};