import Axios from "../utils/axiosInstance"

export const putUser = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.put('/users/update-user', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
