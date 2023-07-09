import Axios from "../utils/axiosInstance"

export const getProductListForFilter = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    let category = ["BLE"];
    return Axios.get(`/product/get-product-list-for-dropdown?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId:fkUserId,
        role: role,
      }) })
}

// export const getProductListForFilter = async (pageno) => {
//   let token = await localStorage.getItem("token")
//   let fkUserId = await localStorage.getItem('fkUserId');
//   let role = await localStorage.getItem('role');
//   return Axios.get(`/get-product-list-for-dropdown-pagination/${pageno}?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
//   body: JSON.stringify({
//       fkUserId:fkUserId,
//       role: role,
//     }) })
// }
