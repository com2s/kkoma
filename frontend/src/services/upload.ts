import { getAccessToken } from "@/utils/getAccessToken";

const baseURL = process.env.NEXT_PUBLIC_API_URL;

export const uploadImagesAPI = async (images: FormData) => {
  const accessToken = await getAccessToken();
  const res = await fetch(`${baseURL}/images/upload`, {
    method: "POST",
    headers: {
      Authorization: accessToken ?? "",
    },
    body: images,
  });
  const obj = res.json();
  return obj;
};
