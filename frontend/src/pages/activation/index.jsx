import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { activateUser } from "./api";
import { Alert } from "@/shared/components/alert";
import { Spinner } from "@/shared/components/spinner";
import { useRouteParamApiRequest } from "@/shared/hooks/useRouteParamApiReques";

export function Activation() {
  const {apiProgress, data, error
  } = useRouteParamApiRequest('token', activateUser)



  return (
    <>
      {apiProgress && (
        <Alert styleType="secondary" center>
          <Spinner/>
        </Alert>
      )}
      {data?.message && (
        <Alert>{data.message}</Alert>
      )}
      {
      error && 
      <Alert styleType="danger">{error}</Alert>
      }
    </>
  );
}
