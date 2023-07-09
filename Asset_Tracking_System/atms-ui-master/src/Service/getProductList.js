import Axios from "../utils/axiosInstance"
export const getProductList = async (pageno) => {
  let token = await localStorage.getItem("token")
  let fkUserId = await localStorage.getItem('fkUserId');
  let role = await localStorage.getItem('role');
  let category = ["BLE"];
  return Axios.get(`/product/get-product-tag-allocation-list/${pageno}?fkUserId=${fkUserId}&role=${role}&category=${category}`, { headers: { "Authorization": `Bearer ${token}` },
  body: JSON.stringify({
      fkUserId:fkUserId,
      role: role,
    }) })
}
