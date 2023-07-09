import Axios from "../utils/axiosInstance"

export const putProductAllocation = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.put('/product/update-product-allocation', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
