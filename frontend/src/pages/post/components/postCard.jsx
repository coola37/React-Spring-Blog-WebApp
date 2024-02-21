import { PostList } from "@/pages/Home/components/postList";
import { CommentList } from "./commentList";
import { Routes, Route, useParams } from 'react-router-dom';

export function PostCard({post}){
    


    return(<div className="card text-center">
    <div className="card-header">
      <h3 className="card-title">{
      post.title
      }</h3>
    </div>
    <div className="card-body">
      <p className="card-text text-start">{
      post.body
      }</p>
    </div>
    <div className="card-footer text-body-secondary">
    <CommentList id={post.postId} />
    </div>
  </div>);
}