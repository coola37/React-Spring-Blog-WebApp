import { useCallback, useEffect, useState } from "react";
import { getComments, postComment } from "./api";
import { CommentListItem } from "./commentListItem";
import { Input } from "@/shared/components/Input";
import { Button } from "@/shared/components/button";

export function CommentList({ id }) {
  const [commentPage, setCommentPage] = useState({
    content: [],
    last: false,
    first: false,
    number: 0,
  });
  const [apiProgress, setApiProgress] = useState(false);
  const [body, setBody] = useState();
  const [postId, setPostId] = useState("");

  const getData = useCallback(async () => {
    setApiProgress(true);
    try {
      let response = await getComments(id);
      setCommentPage(response.data);
      console.log(response.data);
    } catch (error) {
      console.log(error);
    } finally {
      setApiProgress(false);
    }
  }, [id]);

  useEffect(() => {
    getData();
  }, [getData]);

  const onSubmit = async (event) => {
    event.preventDefault();
    setApiProgress(true);
    setPostId(id);
    console.log(postId);
    try {
      const response = postComment({
        postId: id,
        body
      });
    } catch (error) {
      
    }finally{
      setApiProgress(false);
      setBody("");
    }
  }

  return (
    <div className="">
      <div className="card-header text-center fs-4"></div>
      <ul className="list-group list-group-flush">
        {commentPage.content.map((comment) => {
          return <CommentListItem key={comment.commentId} comment={comment} />;
        })}
      </ul>
      <form>
        <Input id="commentBody"
              label="Feedback"
              type="text"
              onChange={(event) => setBody(event.target.value)}
        />
        <Button onClick={onSubmit}>Send</Button>
      </form>
    </div>
  );
}
