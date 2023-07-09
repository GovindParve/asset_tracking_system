import Axios from "../utils/axiosInstance"

export const postProductAllocation = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/product/add-product-tag-allocation', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
