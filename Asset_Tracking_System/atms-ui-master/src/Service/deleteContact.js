import Axios from "../utils/axiosInstance";

export const deleteContact = async (contactId) => {
  let token = await localStorage.getItem("token");
  return Axios.delete(`/contact/${contactId}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};