// import { cookies } from "next/headers";
import { setItemWithExpireTime, getItemWithExpireTime } from "./controlStorage";
import LocalStorage from "./localStorage";

const baseURL = process.env.NEXT_PUBLIC_API_URL;

export const getAccessToken = async () => {
  // TODO: 추후 cookie로 받아와서 http secure 옵션 추가해야됨.
  // const cookieStore = cookies();
  // const accessToken = cookieStore.get("accessToken");

  const accessToken = getItemWithExpireTime("accessToken");
  if (accessToken) {
    const grantType = LocalStorage.getItem("grantType");
    return `${grantType} ${accessToken}`;
  }

  const refreshToken = getItemWithExpireTime("refreshToken");
  if (refreshToken) {
    const grantType = LocalStorage.getItem("grantType");
    const res = await fetch(`${baseURL}/access-token/issue`, {
      headers: {
        Authorization: `${grantType} ${refreshToken}`,
      },
    });
    const obj = await res.json();
    setItemWithExpireTime("accessToken", obj.accessToken, obj.accessTokenExpireTime);
  }

  if (typeof window !== "undefined") {
    alert("로그인이 필요합니다.");
    window.location.href = "/welcome";
  }
};
