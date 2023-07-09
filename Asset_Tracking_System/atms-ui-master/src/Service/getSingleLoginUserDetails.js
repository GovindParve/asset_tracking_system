import Axios from "../utils/axiosInstance";

export const getSingleLoginUserDetails = async (id) => {
  let token = await localStorage.getItem("token");
  return Axios.get(
    `/user/get-user-details-for-issue-and-contactus/${id}`,
    {
      headers: { Authorization: `Bearer ${token}` },
      body: JSON.stringify({
        fkUserId: localStorage.getItem("fkUserId"),
        role: localStorage.getItem("role"),
      }),
    }
  );
};