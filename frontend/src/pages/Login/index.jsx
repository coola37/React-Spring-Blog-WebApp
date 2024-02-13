import { useContext, useEffect, useState } from "react";
import { Input } from "@/shared/components/Input";
import { Alert } from "@/shared/components/alert";
import { Button } from "@/shared/components/button";
import { login as test } from "./api";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { loginSucces } from "@/shared/state/redux";
import { useAuthDispatch } from "@/shared/state/context";

export function Login() {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [apiProgress, setApiProgress] = useState();
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();
  const navigate = useNavigate();
  const dispatch = useAuthDispatch();

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        email: undefined,
      };
    });
  }, [email]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        password: undefined,
      };
    });
  }, [password]);

  const onSubmit = async (event) => {
    event.preventDefault();
    setGeneralError();
    setApiProgress(true);

    try {
      const response = await test({ email: email, password: password });      
      dispatch({type: 'login-success', data: response.data});
      navigate("/")
    }catch (axiosError) {
      if (axiosError.response?.data) {
        if (axiosError.response.data.status == 400) {
          setErrors(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError("genericError");
      }
    }finally {
      setApiProgress(false);
    }
  };

  return (
    <div className="container">
      <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
        <form className="card" onSubmit={onSubmit}>
          <div className="text-center card-header">
            <h1>Login</h1>
          </div>
          <div className="card-body">
            <Input
              id="email"
              label="E-Mail"
              error={errors.email}
              onChange={(event) => setEmail(event.target.value)}
            />
            <Input
              id="password"
              label="Password"
              error={errors.password}
              onChange={(event) => setPassword(event.target.value)}
              type="password"
            />

            {
              //{successMessage && <Alert>{successMessage}</Alert>}
            }
            {generalError && <Alert styleType="danger">{generalError}</Alert>}
            <div className="text-center">
              <Button disabled={!password || !email} apiProgress={apiProgress}>
                Login
              </Button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
