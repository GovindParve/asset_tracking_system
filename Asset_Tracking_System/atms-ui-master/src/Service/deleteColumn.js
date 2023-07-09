import Axios from "../utils/axiosInstance"

export const deleteColumn = async (id) => {
    let token = await localStorage.getItem("token")
    return Axios.delete(`/asset/tracking/delete-mapping-with-column/${id}`, { headers: { "Authorization": `Bearer ${token}` } })
}