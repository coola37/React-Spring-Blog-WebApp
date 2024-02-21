import { Input } from "@/shared/components/Input";
import { Button } from "@/shared/components/button";
import { useState } from "react";
import { sendPost } from "./api";
import { useNavigate } from "react-router-dom";


export function SendPost() {
  
    const [title, setTitle] = useState();
    const [body, setBody] = useState();
    const [apiProgress, setApiProgress] = useState();
    const navigate = useNavigate();
  
  
    const onSubmit = async (event) => {
        event.preventDefault();
        setApiProgress(true);
        try {
            const response =  sendPost({
              title,
              body
            });
            setSuccessMessage(response.data.message);
            
          } catch (axiosError) {
            if (axiosError.response?.data) {
              if (axiosError.response.data.status === 400) {
                setErrors(axiosError.response.data.validationErrors);
              } else {
                setGeneralError(axiosError.response.data.message);
              }
            } else {
              setGeneralError(t("genericError"));
            }
          } finally {
            setApiProgress(false);
            navigate("/")
          }
    }
  
  
    return (
    <div className="container">
      <div className="">
        <form className="card" onSubmit={onSubmit}>
          <div className="text-center card-header">
            <h1>Ask Question</h1>
          </div>
          <div className="card-body">
            <Input id="title" label="Title" onChange={(event) => setTitle(event.target.value)}/>
            <div class="mb-3">
              <label for="body" className="form-label">
                What are the details of your problem?
              </label>
              <textarea
                className="form-control"
                id="body"
                rows="10"
                onChange = {(event) => setBody(event.target.value)}

              ></textarea>
            </div>
            <div className="d-grid gap-2 d-md-flex justify-content-md-end">
            <button type="submit" className="btn btn-primary mb-3 text-center" 
            onClick={onSubmit}> Send </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
