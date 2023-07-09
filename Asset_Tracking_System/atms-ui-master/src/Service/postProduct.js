import Axios from "../utils/axiosInstance"

export const postProduct = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/product/add-product-details', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
