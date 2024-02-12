import axios from "axios";
 

const http = axios.create();


http.interceptors.request.use((config) => {
    
    config.headers["Accept-Language"] = "en";
    config.headers["Content-Type"] = "application/json";
   
     return config;
} )

export default http;