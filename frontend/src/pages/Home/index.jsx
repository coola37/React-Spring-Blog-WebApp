import { Button } from "@/shared/components/button";
import { PostList } from "./components/postList";
import { Input } from "@/shared/components/Input";


export function Home(){
    return (
        <div>
            <div>
            <PostList/>
            </div>
            
        </div>
    );
}