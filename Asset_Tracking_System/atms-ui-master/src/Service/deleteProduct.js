import Axios from "../utils/axiosInstance"

export const deleteProduct = async (id) => {
    let token = await localStorage.getItem("token")
    return Axios.delete(`/product/delete-product/${id}`, { headers: { "Authorization": `Bearer ${token}` } })
}
