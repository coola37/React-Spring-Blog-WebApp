import { Input } from "@/shared/components/Input";
import { Button } from "@/shared/components/button";


export function SendPost(){
    return (
        <form className="card">
               <div className="card-header text-center fs-4">
                <Input label="Title"  id="postTitle" type="text" />
                <label>Body</label>
                <div className="input-group">
                    <textarea className="form-control" aria-label="With textarea"></textarea>
                </div>
                <div></div>
                <Button>Send</Button>
               </div>
            </form>
    );
}