import { getPost } from "./api"
import { PostCard } from "./components/postCard";
import { useParams } from 'react-router-dom';
import { useEffect, useState } from "react";

export function Post(){
    let params  = useParams();
    const [post, setPost] = useState({});
    
    const postId = params.id;
    //console.log(postId); 
    
    async function fetchPost() {
        try {
            const response = await getPost(postId)
            setPost(response.data.payload)
            //console.log(response.data.payload)
        } catch{
            
        }finally{
           
        }
    }



     useEffect( () => {
        fetchPost();
     }, []);
     
     //console.log(post);
    return <PostCard post={post} />;
}