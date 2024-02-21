import { Link } from "react-router-dom";

export function PostListItem({ post }) {
  return (
    <Link
      className="list-group-item list-group-item-action list-group-item"
      to={`/post/${post.postId}`}
      style={{ textDecoration: "none" }}
    >
      <span className="ms-2" />
      {post.title}
    </Link>
  );
}
