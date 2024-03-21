// import { cookies } from "next/headers";
import { setItemWithExpireTime, getItemWithExpireTime } from "./controlStorage";

const baseURL = process.env.NEXT_PUBLIC_API_URL;

export const getAccessToken = async () => {
  // TODO: 추후 cookie로 받아와서 http secure 옵션 추가해야됨.
  // const cookieStore = cookies();
  // const accessToken = cookieStore.get("accessToken");

  const accessToken = getItemWithExpireTime("accessToken");
  if (accessToken) {
    const grantType = window.localStorage.getItem("grantType");
    return `${grantType} ${accessToken}`;
  }

  const refreshToken = getItemWithExpireTime("refreshToken");
  if (refreshToken) {
    //TODO: refreshToken을 header로 보내줌
    const grantType = window.localStorage.getItem("grantType");
    const res = await fetch(`${baseURL}/access-token/issue`, {
      headers: {
        Authorization: `${grantType} ${refreshToken}`,
      },
    });
    //TODO: 받아온 accesstoken 다시 저장
    console.log(res.json);
  }

  //TODO: 로그아웃. 다시 로그인 필요
  console.log("암것도 없네...");

  window.location.href = "/error";
};
