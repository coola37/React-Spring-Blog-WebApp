import http from "@/lib/http";


export async function sendPost(post){
    return await http.post('/api/v1/posts', post);
}