import defaultProfileImg from "@/assets/profile.png";
import { ProfileImg } from "@/shared/components/ProfileImg";
import { Link } from "react-router-dom";

export function UserListItem({ user }) {
  return (
    <Link
      className="list-group-item list-group-item-action"
      to={`/user/${user.userId}`}
      style={{ textDecoration: "none" }}
    >
      <ProfileImg width={30} />
      <span className="ms-2" />
      {user.username}
    </Link>
  );
}
