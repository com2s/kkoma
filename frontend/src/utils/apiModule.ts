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

  const accessToken = await getAccessToken();

  const res = await fetch(`${baseURL}${action}`, {
    method: method,
    headers: {
      "Content-Type": "application/json",
      Authorization: accessToken ?? "",
    },
    body: data,
  });
  return res;
};

export default APIModule;
