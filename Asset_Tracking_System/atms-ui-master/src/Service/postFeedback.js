import Axios from "../utils/axiosInstance"

export const postFeedback = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/issue/post', payload, { headers: { "Authorization": `Bearer ${token}` } })
}