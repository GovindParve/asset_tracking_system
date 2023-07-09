import Axios from "../utils/axiosInstance";

export const getOnlyAdminDrowpdownList = async () => {
  let token = localStorage.getItem("token");
  let fkUserId = localStorage.getItem("fkUserId");
  let role = localStorage.getItem("role");
  let category = ["BLE"];
  return Axios.get(
    `/users/get-all-admin-list-created-by-super-admin?fkUserId=${fkUserId}&role=${role}&category=${category}`,
    { headers: { Authorization: `Bearer ${token}` } }
  );
};
