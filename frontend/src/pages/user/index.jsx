import { useEffect, useState } from "react";
import { getUser } from "./api";
import { Alert } from "@/shared/components/alert";
import { Spinner } from "@/shared/components/spinner";
import { useRouteParamApiRequest } from "@/shared/hooks/useRouteParamApiReques";
import { ProfileCard } from "./components/ProfileCard";

export function User() {
  const { apiProgress, data, error 
  } = useRouteParamApiRequest("id", getUser);
  const user = data?.payload
  console.log(user);

  return (
    <>
      {apiProgress && (
        <Alert styleType="secondary" center>
          <Spinner />
        </Alert>
      )}
      {user && <ProfileCard user={user}/>}
      {error && <Alert styleType="danger">{error}</Alert>}
    </>
  );
}
