export const kakaoLogin = async (code: string) => {
  const baseURL = process.env.NEXT_PUBLIC_API_URL;

  const res = await fetch(`${baseURL}/oauth/kakao?code=${code}`);

  return res;
};
