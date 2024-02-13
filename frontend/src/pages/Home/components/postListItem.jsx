import { Link } from "react-router-dom";

export function PostListItem({ post }) {
  return (
    <Link
      className="list-group-item list-group-item-action list-group-item-dark"
      to={`/post/${post.postId}`}
      style={{ textDecoration: "none" }}
    >
      <span className="ms-2" />
      {post.title}
    </Link>
  );
}
/*
  <a href="#" class="">A simple light list group item</a>

*/