import { Button } from "@/shared/components/button";
import { useAuthState } from "@/shared/state/context";
import { useState } from "react";
import { ProfileImg } from "@/shared/components/ProfileImg";
import { UserEditForm } from "./UserEditForm";
import { UserDeleteButton } from "./UserDeleteButton";

export function ProfileCard({ user }) {
  const authState = useAuthState();
  const [editMode, setEditMode] = useState(false);
  const [tempImg, setTempImg] = useState();

  const isLogged = !editMode && authState.userId == user.userId;
  const visibleUsername = authState.userId == user.userId ? authState.username : user.username;
  return (
    <div className="card">
      <div className="card-header text-center">
        <ProfileImg width={200} tempImage={tempImg} image={user.image} />
      </div>
      <div className="card-body text-center">
        {!editMode && <span className="fs-3 d-block">{visibleUsername}</span>}
        {isLogged && (
          <>
            <Button onClick={() => setEditMode(true)}>Edit</Button>
            <div className="d-inline m-1"></div>
            <UserDeleteButton />
          </>
        )}
        {editMode && <UserEditForm setEditMode={setEditMode} setTempImg={setTempImg}/>}
      </div>
    </div>
  );
}
