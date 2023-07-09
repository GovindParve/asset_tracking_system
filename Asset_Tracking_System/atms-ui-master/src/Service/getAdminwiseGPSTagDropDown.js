import Axios from "../utils/axiosInstance";

export const getAdminwiseGPSTagDropDown = (adminname) => {
    let token = localStorage.getItem("token");
    return Axios.get(`/Tag/get-Tag_list_For_Admin?username=${adminname}&category=GPS`, {
        headers: { "Authorization": `Bearer ${token}` }
    })
};