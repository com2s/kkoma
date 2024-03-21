import { getAccessToken } from "./getAccessToken";
import { APIProps } from "@/types/api";

const baseURL = process.env.NEXT_PUBLIC_API_URL;

/**
 * Fetch API를 쉽게 부를 수 있도록 도와줍니다.
 * header에 Authorization이 기본으로 추가되어있습니다.
 *
 * @param APIProps
 * @returns Response
 */

const APIModule = async ({ action, method, data }: APIProps) => {
  if (data !== null) {
    data = JSON.stringify(data);
  }

  // const accessToken = await getAccessToken();

  const res = await fetch(`${baseURL}${action}`, {
    method: method,
    headers: {
      "Content-Type": "application/json",
      Authorization:
        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE3MTEwMTI4NjIsImV4cCI6MTcyMzEwODg2MiwibWVtYmVySWQiOjU4LCJyb2xlIjoiVVNFUiJ9.wqWhTgpIVOoP1ZovYRxMOyHmY6vRDSz34nE-LdrkqxeSVrYqtuJ_waW3KN9jjJDLnRZRfKSUDp0w51oj2sI2EQ",
    },
    body: data,
  });
  return res;
};

export default APIModule;
