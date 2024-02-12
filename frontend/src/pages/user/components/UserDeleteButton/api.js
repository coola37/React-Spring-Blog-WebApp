import http from "@/lib/http";


export function deleteUser(userId){
    return http.delete(`/api/v1/users/${userId}`)
}