import Axios from "../utils/axiosInstance"

export const postUser = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/users/signup', payload, { headers: { "Authorization": `Bearer ${token}` } })
}

