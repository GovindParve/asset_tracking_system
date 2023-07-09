import Axios from "../utils/axiosInstance";

export const getOnlyGpsAdminDrowpdownList = async () => {
  let token = localStorage.getItem("token");
  let fkUserId = localStorage.getItem("fkUserId");
  let role = localStorage.getItem("role");
  let category = ["GPS"];
  return Axios.get(
    `/users/get-all-admin-list-created-by-super-admin?fkUserId=${fkUserId}&role=${role}&category=${category}`,
    { headers: { Authorization: `Bearer ${token}` } }
  );
};