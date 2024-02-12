import { Input } from "@/shared/components/Input";
import { Button } from "@/shared/components/button";
import { useAuthDispatch, useAuthState } from "@/shared/state/context";
import { useState } from "react";
import { updateUser } from "./api";
import { Alert } from "@/shared/components/alert";

export function UserEditForm({ setEditMode, setTempImg }) {
  const authState = useAuthState();
  const [newUsername, setNewUsername] = useState(authState.username);
  const [apiProgress, setApiProgress] = useState(false);
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();
  const dispatch = useAuthDispatch();
  const [newImg, setNewImg] = useState();

  const onChangeUsername = (event) => {
    setNewUsername(event.target.value);
    setErrors(function (lastErrors){
      return {
        ...lastErrors,
        username: undefined,
      };
    });
  };
  const onClickCancel = () => {
    setEditMode(false);
    setNewUsername(authState.username);
    setNewImg();
    setTempImg();
  };

  const onSelectImg = (event) => {
    setErrors(function (lastErrors){
      return {
        ...lastErrors,
        image: undefined,
      };
    });
    if(event.target.files.length < 1) return;
    const file = event.target.files[0];
    const fileReader = new FileReader();

    fileReader.onloadend = () => {
        const data = fileReader.result
        setNewImg(data);
        setTempImg(data);
    }
    fileReader.readAsDataURL(file);
  }

  const onClickSave = async () => {
    setApiProgress(true);
    setErrors({});
    setGeneralError();
    try {
      const {data} = await updateUser(authState.userId, { username: newUsername, image: newImg });
      dispatch({
        type: "user-update-success",
        data: {
          username: data.username, image: data.image
        },
      });
      setEditMode(false);
      setNewUsername(newUsername);
    } catch (axiosError) {
      if (axiosError.response?.data) {
        if (axiosError.response.data.status == 400) {
          setErrors(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError("genericError");
      }
    } finally {
      setApiProgress(false);
    }
  };
  return (
    <form>
      <Input
        label="Username"
        defaultValue={authState.username}
        onChange={onChangeUsername}
        error={errors.username}
      />
      
      <Input
         label="Profile Image"
         type="file"
         onChange={onSelectImg}
         error={errors.image}
      />
    

      {generalError && <Alert styleType="danger">{generalError}</Alert>}
      <Button
        apiProgress={apiProgress}
        type="submit"
        onClick={() => onClickSave()}
      >
        Save
      </Button>
      <div className="d-inline m-1"></div>
      <Button styleType="outline-secondary" onClick={() => onClickCancel()} type="button">
        Cancel
      </Button>
    </form>
  );
}
