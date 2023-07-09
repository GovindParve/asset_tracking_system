import Axios from "../utils/axiosInstance"

export const putProductById = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.put('/product/update-product', payload, { headers: { "Authorization": `Bearer ${token}` } })
}