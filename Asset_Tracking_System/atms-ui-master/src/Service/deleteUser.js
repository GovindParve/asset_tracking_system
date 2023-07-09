import Axios from "../utils/axiosInstance"

export const deleteUser = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('/user/bulkdelete', payload, { headers: { "Authorization": `Bearer ${token}` } })
}