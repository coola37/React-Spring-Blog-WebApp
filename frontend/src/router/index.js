import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { Home } from '@/pages/Home/index.jsx';
import { SignUp } from '@/pages/SignUp/index.jsx';
import App from '@/App.jsx';
import { Activation } from '@/pages/activation/index.jsx';
import { User } from '@/pages/user';
import { Login } from '@/pages/Login';

export default createBrowserRouter([
   {
    path: "/",
    Component: App,
    children:[
      {
        path: "/",
        index: true,
        Component: Home
      },
      {
        path: "/signup",
        Component: SignUp
      },
      {
        path: "/activation/:token",
        Component: Activation
      },
      {
        path: "/user/:id",
        Component: User
      },
      {
        path: '/login',
        Component: Login
      }
    ]
   }
  ])