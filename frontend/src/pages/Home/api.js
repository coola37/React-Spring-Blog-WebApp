import http from "@/lib/http";

export function loadPosts(page = 0){
    console.log(http.get("/api/v1/posts", { params: {page, size: 5}}))
    return http.get("/api/v1/posts", { params: {page, size: 15}});
}
