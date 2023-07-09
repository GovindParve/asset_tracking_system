import Axios from "../utils/axiosInstance"

// export const getProductListByName = async (name) => {
//     let token = await localStorage.getItem("token")
//     let fkUserId = await localStorage.getItem('fkUserId');
//     let role = await localStorage.getItem('role');
//     return Axios.get(`/product/get-product-tag-allocation-list-from-product-name?productName=${name}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    
//     body: JSON.stringify({
//         fkUserId:fkUserId,
//         role: role,
//       }) })
// }
export const getProductListByName = async (pageno,name) => {
  let token = await localStorage.getItem("token")
  let fkUserId = await localStorage.getItem('fkUserId');
  let role = await localStorage.getItem('role');
  return Axios.get(`/product/get-product-tag-allocation-list-from-product-name-with-pagination/${pageno}?productName=${name}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
  
  body: JSON.stringify({
      fkUserId:fkUserId,
      role: role,
    }) })
}
