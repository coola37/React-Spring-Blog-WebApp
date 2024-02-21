import http from "@/lib/http";

export function getComments(id, page = 0){
    return http.get(`/api/v1/comments/${id}`);

}

export function likeComment(id){
    return http.put(`/api/v1/comment/like/${id}`)
}