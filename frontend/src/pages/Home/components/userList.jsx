import { useCallback, useEffect, useState } from "react";
import { Spinner } from "@/shared/components/spinner";
import { UserListItem } from "./userListItem";
import { loadUsers } from "../api";


export function UserList() {
  const [userPage, setUserPage] = useState({
    content: [],
    last: false,
    first: false,
    number: 0,
  });

  const [apiProgress, setApiProgress] = useState(false);

  const fetchUsers = useCallback(async (page) => {
    setApiProgress(true);
    try {
      const response = await loadUsers(page);
      setUserPage(response.data);
    } catch {
    } finally {
      setApiProgress(false);
    }
  }, []);

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <>
      <div className="card">
        <div className="card-header text-center fs-4">User List</div>
        <ul className="list-group list-group-flush">
          {userPage.content.map((user) => {
            return <UserListItem key={user.userId} user={user}/>;
          })}
        </ul>

        <div className="card-footer text-center">
          {apiProgress && <Spinner />}
          {!apiProgress && !userPage.first && (
            <button
              className="btn btn-outline-secondary btn-sm float-start"
              onClick={() => fetchUsers(userPage.number - 1)}
            >
              Previous
            </button>
          )}
          {!apiProgress && !userPage.last && (
            <button
              className="btn btn-outline-secondary btn-sm float-end"
              onClick={() => fetchUsers(userPage.number + 1)}
            >
              Next
            </button>
          )}
        </div>
      </div>
    </>
  );
}
