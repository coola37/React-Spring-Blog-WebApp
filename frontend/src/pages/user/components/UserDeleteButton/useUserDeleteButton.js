import { useAuthDispatch, useAuthState } from "@/shared/state/context"
import { deleteUser } from "./api"
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

export function useUserDeleteButton(){
    const {userId} = useAuthState();
    const [apiProgress, setApiProgress] = useState(false); 
    const dispatch = useAuthDispatch();
    const navigate = useNavigate();

    const onClick = useCallback (async () => {
        const result = confirm("Are you sure?");
        if(result){
            setApiProgress(true);
            try{
                await deleteUser(userId)
                dispatch({type: 'logout-success'});
            }catch{
    
            }finally{
                setApiProgress(false);
            }
        }
       
    }, [userId])

    return { apiProgress, onClick}
}