import { getAccessToken } from "./getAccessToken";

const baseURL = process.env.NEXT_PUBLIC_API_URL;

interface APIProps {
  action: string;
  method: string;
  data: object | string | null;
}

const APIModule = async ({ action, method, data }: APIProps) => {
  if (data !== null) {
    data = JSON.stringify(data);
  }

  const accessToken = await getAccessToken();

  const res = await fetch(`${baseURL}${action}`, {
    method: method,
    headers: {
      Authorization: accessToken ?? "",
    },
    body: data,
  });

  console.log("res in api module=", res);
  return res;
};
