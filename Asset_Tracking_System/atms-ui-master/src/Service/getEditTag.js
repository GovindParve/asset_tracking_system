import Axios from "../utils/axiosInstance"

export const getEditTag = async (id) => {
    let token = await localStorage.getItem("token");
    let fkUserId =  await localStorage.getItem('fkUserId');
    let role =await localStorage.getItem('role');
    return Axios.get(`/tag/get-tag-for-edit/${id}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
}

