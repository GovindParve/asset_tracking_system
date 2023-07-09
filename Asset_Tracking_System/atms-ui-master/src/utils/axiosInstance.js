import axios from "axios"

const instance = axios.create({
    baseURL: 'http://34.231.193.123:8082',
    //baseURL: 'http://localhost:8082,
    timeout: 1800000,   
    headers: {"Access-Control-Allow-Origin": "*"}
})

instance.interceptors.response.use(function(response) {
    return response;
},async function(error){
    console.log("error :" + JSON.stringify(error));
    if (error.message === "Request failed with status code 403" || error.message === "Request failed with status code 404" ) {
        let refreshToken = localStorage.getItem("refreshToken")
        return await axios.post(`http://34.231.193.123:8082/users/refreshToken?refreshToken=${refreshToken}`)
            .then(tokenRefreshResponse => {
                console.log("Access Token:", tokenRefreshResponse.data.accessToken);
                localStorage.setItem('token', tokenRefreshResponse.data.accessToken);
                return Promise.resolve();
            });
    }
    console.log("Rest promise error");
    return Promise.reject(error);
});
export default instance
