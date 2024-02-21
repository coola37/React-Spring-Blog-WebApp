import { useCallback, useEffect, useState } from "react";
import { getComments } from "./api";
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
  }, [getData]); // Only re-run the effect if getData changes

  return (
    <div className="">
      <div className="card-header text-center fs-4"></div>
      <ul className="list-group list-group-flush">
        {commentPage.content.map((comment) => {
          return <CommentListItem key={comment.commentId} comment={comment} />;
        })}
      </ul>
      <form>
        <label>Feedback</label>
        <Input id="commentBody"
              label=""
              type="text"
        />
        <Button>Send</Button>
      </form>
    </div>
  );
}
