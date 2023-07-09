import Axios from "../utils/axiosInstance"

export const getSingleProduct = async (id) => {
    let token = await localStorage.getItem("token")
    return Axios.get(`/product/get-product/${id}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}