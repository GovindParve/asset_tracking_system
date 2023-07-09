import Axios from "../utils/axiosInstance"

export const putProduct = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.put('/product/update-product-allocation', payload, { headers: { "Authorization": `Bearer ${token}` } })
}