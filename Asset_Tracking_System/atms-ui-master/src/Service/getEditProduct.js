import Axios from "../utils/axiosInstance"

export const getEditProductAlloc = async (productAllocationid) => {
    let token = await localStorage.getItem("token");
    let fkUserId =  await localStorage.getItem('fkUserId');
    let role =await localStorage.getItem('role');
    return Axios.get(`/product/get-product-allocation-for-edit/${productAllocationid}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}