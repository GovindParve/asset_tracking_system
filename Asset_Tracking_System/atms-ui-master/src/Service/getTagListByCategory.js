import Axios from "../utils/axiosInstance"
export const getTagListByCategory = async (pageno,assetTagCategory) => {
    let token = await localStorage.getItem("token")
    let fkUserId = await localStorage.getItem('fkUserId');
    let role = await localStorage.getItem('role');
    return Axios.get(`/Tag/get-Tag_list-categorywise-with-pagination/${pageno}?assetTagCategory=${assetTagCategory}&fkUserId=${fkUserId}&role=${role}`, { headers: { "Authorization": `Bearer ${token}` },
    body: JSON.stringify({
        fkUserId: localStorage.getItem('fkUserId'),
        role: localStorage.getItem('role'),
      }) })
  }
  