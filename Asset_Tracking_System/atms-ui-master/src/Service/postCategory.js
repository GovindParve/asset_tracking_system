import Axios from "../utils/axiosInstance"

export const postCategory = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/category/add', payload, { headers: { "Authorization": `Bearer ${token}` } })
}

