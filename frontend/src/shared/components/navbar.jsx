import { Link } from "react-router-dom";
import logo from "@/assets/logo.png";
import { AuthContext, useAuthDispatch, useAuthState } from "../state/context";
import { useContext } from "react";
import { useDispatch, useSelector } from "react-redux";
import { logoutSucces } from "../state/redux";
import { ProfileImg } from "./ProfileImg";

export function NavBar() {
  /*const authState = useAuthState();
  const dispatch = useAuthDispatch();*/

  const authState = useAuthState();
  const dispatch = useDispatch();

  const onClickLogout = () => {
    dispatch(logoutSucces());
  };

  return (
    <nav className="navbar navbar-expand bg-body-tertiary shadow-sm">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">
          <img src={logo} width={60} />
          Whisper
        </Link>
        <ul className="navbar-nav">
          {authState.userId === 0 && (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/login">
                  Login
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/signup">
                  Sign Up
                </Link>
              </li>
            </>
          )}
          {authState.userId > 0 && (
            <>
              <li className="nav-item">
                <Link className="nav-link" to={`/user/${authState.userId}`}>
                  <ProfileImg width={30} />
                  <span>{authState.username}</span>
                </Link>
              </li>
              <li className="nav-item">
                <span
                  className="nav-link"
                  role="button"
                  onClick={onClickLogout}
                >
                  Logout
                </span>
              </li>
            </>
          )}
        </ul>
      </div>
    </nav>
  );
}
