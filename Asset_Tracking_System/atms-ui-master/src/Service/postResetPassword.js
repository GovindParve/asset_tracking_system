import Axios from "../utils/axiosInstance";

export const postResetPassword = async (payload) => {
  return Axios.post(`/users/reset-password?token=${payload.token}&password=${payload.password}`
  );
};