import Axios from "../utils/axiosInstance"

export const postContact = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/contact/post', payload, { headers: { "Authorization": `Bearer ${token}` } })
}