import Axios from "../utils/axiosInstance"

export const deleteAssetTag = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.post('tag/delete-multiple-tag', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
