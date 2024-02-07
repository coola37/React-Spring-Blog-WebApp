import { createContext, useContext, useEffect, useReducer, useState } from "react";
import { loadAuthState, storeAuthState } from "./storage";
import { setToken } from "@/lib/http";

export const AuthContext = createContext();

export const AuthDispatchContext = createContext();

export function useAuthState(){
    return useContext(AuthContext);
}

export function useAuthDispatch(){
    return useContext(AuthDispatchContext);
}
 
const authReducer = (authState, action) => {
  
  console.log(authState)
  switch (action.type) {
    case "login-succes": 
      setToken(action.data.token);
      return action.data.user;
    case "logout-succes":
      setToken();
      return { userId: 0 };
    case "user-update-success": 
      return {  
        ...authState,
        username: action.data.username
        /*email: action.data.email, 
        image: action.data.image,
        userId: action.data.userId*/
      };

    default:
      throw new Error(`unknown action: ${action.type}`);
  }
};

export function AuthenticationContext({ children }) {
  const [authState, dispatch] = useReducer(authReducer, loadAuthState());

  useEffect(() => {
    storeAuthState(authState);
  }, [authState]);

  return (
    <AuthContext.Provider value={authState}>
      <AuthDispatchContext.Provider value={dispatch}>
        {children}
      </AuthDispatchContext.Provider>
    </AuthContext.Provider>
  );
}
