import { useCallback, useEffect, useState } from "react";
import { Spinner } from "@/shared/components/spinner";
import { PostListItem } from "./postListItem";
import { loadPosts } from "../api";
import { Button } from "@/shared/components/button";


export function PostList() {
  const [postPage, setPostPage] = useState({
    content: [],
    last: false,
    first: false,
    number: 0,
  });

  const [apiProgress, setApiProgress] = useState(false);

  const fetchPosts = useCallback(async (page) => {
    setApiProgress(true);
    try {
      const response = await loadPosts(page);
      setPostPage(response.data);
    } catch {
    } finally {
      setApiProgress(false);
    }
  }, []);

  useEffect(() => {
    fetchPosts();
  }, []);

  return (
    <>
      <div className="card text-bg mb-3">
        <div className="card-header text-center fs-4">
          <label className="text-start">Question</label>  
          <button className="btn btn-outline-primary btn-sm float-end">Ask Question</button>
        </div>
        
        <ul className="list-group list-group-flush">
          {postPage.content.map((post) => {
            return <PostListItem key={post.postId} post={post}/>;
          })}
        </ul>

        <div className="card-footer text-center">
          {apiProgress && <Spinner />}
          {!apiProgress && !postPage.first && (
            <button
              className="btn btn-outline-secondary btn-sm float-start"
              onClick={() => fetchPosts(postPage.number - 1)}
            >
              Previous
            </button>
          )}
          {!apiProgress && !postPage.last && (
            <button
              className="btn btn-outline-secondary btn-sm float-end"
              onClick={() => fetchPosts(postPage.number + 1)}
            >
              Next
            </button>
          )}
        </div>
      </div>
    </>
  );
}