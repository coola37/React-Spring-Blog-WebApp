import { useCallback, useEffect, useState } from 'react';
import { Button } from "@/shared/components/button";
import { likeComment } from "./api";
import { getUser } from '@/pages/user/api';
import { useAuthState } from '@/shared/state/context';

export function CommentListItem({ comment }) {
    const [liked, setLiked] = useState(false);
    const [user, setUser] = useState();
    const authState = useAuthState(); 

    function likeControl(){
        const likedById = comment.likedById;
        const control = likedById.includes(authState.userId)
        setLiked(control)
    }

    useEffect(() => {
        likeControl();
    }, [comment]);

    async function fetchUser(id){
        const response =await getUser(id);
        //console.log(response)
        setUser(response.data.payload)
    }

    useEffect(() => {
        fetchUser(authState.userId);
      }, [comment]);

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title text-start">{comment.senderUsername}</h5>
                <p className="card-text text-start">{comment.body}</p>
                <p className="">Like:{comment.likeCount}</p>
                <Button onClick={() => likeComment(comment.commentId)} > {liked ? 'Dislike' : 'Like'}</Button>
            </div>
        </div>
    );
}