import http from "@/lib/http";

export async function updateUser(id, body){
    return http.put(`/api/v1/users/${id}`, body)
}