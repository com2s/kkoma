export const kakaoLoginAPI = async (code: string) => {
  const baseURL = process.env.NEXT_PUBLIC_API_URL;
  const appURL = process.env.NEXT_PUBLIC_APP_URL;

  const res = await fetch(`${baseURL}/oauth/kakao?code=${code}&clientHost=${appURL}`);

  return res;
};
