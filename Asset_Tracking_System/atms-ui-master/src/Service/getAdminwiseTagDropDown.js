import Axios from "../utils/axiosInstance";

export const getAdminwiseTagDropDown = (adminname) => {
    let token = localStorage.getItem("token");
    return Axios.get(`/Tag/get-Tag_list_For_Admin?username=${adminname}&category=BLE`, {
        headers: { "Authorization": `Bearer ${token}` }
    })
};