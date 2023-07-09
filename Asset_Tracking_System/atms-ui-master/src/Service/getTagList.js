import Axios from "../utils/axiosInstance"

export const getTagList = async () => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/Tag/get-Tag_list?fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId:fkUserId,
        role: role,
      }) })
}
