import Axios from "../utils/axiosInstance";

export const postUploadDoc = (payload) => {
  //let fkUserId = localStorage.getItem("fkUserId");
  let token = localStorage.getItem("token");
  return Axios.post(`/asset/tracking/add-image`, payload ,{headers: { "Authorization": `Bearer ${token}` },
  });
};