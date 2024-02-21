import http from "@/lib/http";

export async function getPost(postId){
    return  await http.get(`/api/v1/posts/get/${postId}`);
}