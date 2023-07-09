import Axios from "../utils/axiosInstance"

export const getEditColumn = async (userid) => {
    let token = await localStorage.getItem("token");
    return Axios.get(`/asset/tracking/get-columnmapping-By-id/${userid}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}