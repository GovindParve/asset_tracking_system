import Axios from "../utils/axiosInstance"

// export const getTrackListByTag = async (tagname) => {
//     let token = await localStorage.getItem("token")
//     let fkUserId = await localStorage.getItem('fkUserId');
//     let role = await localStorage.getItem('role');
//     return Axios.get(`/asset/tag/get-tracking-list-for-filter-tagnamewise?tagName=${tagname}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
//     body: JSON.stringify({
//         fkUserId: localStorage.getItem('fkUserId'),
//         role: localStorage.getItem('role'),
//       }) })
// }
export const getTrackListByTag = async (pageno,tagName) => {
  let token = await localStorage.getItem("token")
  let fkUserId = await localStorage.getItem('fkUserId');
  let role = await localStorage.getItem('role');
  let category = ["BLE"];
  return Axios.get(`/asset/tag/get-tracking-list-for-filter-tagnamewise-with-pagination/${pageno}?tagName=${tagName}&fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
      fkUserId: localStorage.getItem('fkUserId'),
      role: localStorage.getItem('role'),
    }) })
}
