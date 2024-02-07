import { configureStore, createSlice } from "@reduxjs/toolkit";
import { loadAuthState, storeAuthState } from "./storage";
import { setToken } from "@/lib/http";

const authSlice = createSlice({
    name: 'auth',
    initialState: loadAuthState(),
    reducers: {
        loginSucces: (state, action) => {
            state.userId = action.payload.user.userId;
            state.username = action.payload.user.username;
            state.email = action.payload.user.email; 
            state.image = action.payload.user.image;
            //console.log(action.payload.token);
            setToken(action.payload.token)
        },
        logoutSucces: (state, action) => {
            state.userId = 0;
            delete  state.username;
            delete  state.email;
            delete  state.image;
            setToken();
        }
    }
})

export const {loginSucces, logoutSucces} = authSlice.actions;

export const store = configureStore({
    reducer: {
        auth: authSlice.reducer
    }
})

store.subscribe(() => {
    storeAuthState(store.getState().auth)
})

