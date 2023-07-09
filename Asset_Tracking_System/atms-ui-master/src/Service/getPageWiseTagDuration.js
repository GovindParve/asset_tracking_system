import { Category } from "@material-ui/icons";
import Axios from "../utils/axiosInstance";

export const getPageWiseTagDuration = (pageno,tagName,duration) => {
  let token = localStorage.getItem("token");
  let fkUserId = localStorage.getItem("fkUserId");
  let role = localStorage.getItem("role");
  let category = ["BLE"];
  return Axios.get(`/asset/tracking/view-all-tracking-data-with-time-duration-with-pagination/${pageno}?tagName=${tagName}&duration=${duration}&fkUserId=${fkUserId}&role=${role}&category=${category}`,{ headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
    fkUserId: localStorage.getItem('fkUserId'),
    role: localStorage.getItem('role'),
  }) 
})
};

// import Axios from "../utils/axiosInstance";

// export const getPageWiseTagDuration = (pageno,tagName,duration) => {
//   let token = localStorage.getItem("token");
//   let fkUserId = localStorage.getItem("fkUserId");
//   let role = localStorage.getItem("role");
//   return Axios.get(`/asset/tracking/get_trackining-details-time-wise-tagnamewise/${pageno}?tagName=${tagName}&duration=${duration}&fkUserId=${fkUserId}&role=${role}`,{ headers: { "Authorization": `Bearer ${token}` },
//   body: JSON.stringify({
//     fkUserId: localStorage.getItem('fkUserId'),
//     role: localStorage.getItem('role'),
//   }) 
// })


// };