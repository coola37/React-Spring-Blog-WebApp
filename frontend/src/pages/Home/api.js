import http from "@/lib/http";

export function loadUsers(page = 0){
    console.log(http.get("/api/v1/users/get", { params: {page, size: 5}}))
    return http.get("/api/v1/users/get", { params: {page, size: 5}});
}

/*

import http from "@/lib/http";
import axios from "axios";


export async function loadUsers(page = 0){
    return await axios.get("/api/v1/users/get", {
        params: { page, size: 5}
    });

}



export async function  login(credentials){
    const requestBody = JSON.stringify(credentials);
 console.log(requestBody)
    return await http.post("/api/v1/auth", requestBody/*,{
        headers: {
            "Accept-Language": "en",
            "Content-Type": "application/json"
        }
    } */
