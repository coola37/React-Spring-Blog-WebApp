import http from "@/lib/http";
import axios from "axios";

export async function  login(credentials){
    const requestBody = JSON.stringify(credentials);
    return await http.post("/api/v1/auth", requestBody)
}
