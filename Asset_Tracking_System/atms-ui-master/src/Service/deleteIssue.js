import Axios from "../utils/axiosInstance";

export const deleteIssue = async (issueId) => {
  let token = await localStorage.getItem("token");
  return Axios.delete(`/issue/delete/${issueId}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};