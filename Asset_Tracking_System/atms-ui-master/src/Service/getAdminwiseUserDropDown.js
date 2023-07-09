import Axios from "../utils/axiosInstance";

export const getAdminwiseUserDropDown = (adminname) => {
    let token = localStorage.getItem("token");
    return Axios.get(`/user/get-user_adminwise_list?adminname=${adminname}`, {
        headers: { "Authorization": `Bearer ${token}` }
    })
};