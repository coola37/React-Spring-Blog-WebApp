import { loadToken, storeToken } from "@/shared/state/storage";
import axios from "axios";
 

const http = axios.create();

let authToken = loadToken();

export function setToken(token){
    authToken = token;
    storeToken(token)
}

http.interceptors.request.use((config) => {
    
    config.headers["Accept-Language"] = "en";
    config.headers["Content-Type"] = "application/json";
    
    if(authToken){
        config.headers["Authorization"] = `${authToken.prefix} ${authToken.token}`;
        //console.log(`${authToken.prefix} ${authToken.token}`)
        //return config;
     }else {
        console.log("auth token null")}
     return config;
} )

export default http;