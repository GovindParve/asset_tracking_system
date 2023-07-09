import Axios from "../utils/axiosInstance"

export const putTag = async (payload) => {
    let token = await localStorage.getItem("token")
    return Axios.put('/tag/update-tag', payload, { headers: { "Authorization": `Bearer ${token}` } })
}
