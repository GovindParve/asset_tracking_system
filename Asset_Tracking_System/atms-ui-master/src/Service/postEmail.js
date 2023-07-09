import Axios from "../utils/axiosInstance";

export const postEmail = async (payload) => {
 // return Axios.post(`/users/forgot-password?email=${payload.email}`);
  return Axios.post(`/users/forgot-password?email=${payload.email}`);
};

