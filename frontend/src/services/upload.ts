import { getAccessToken } from "@/utils/getAccessToken";

const baseURL = process.env.NEXT_PUBLIC_API_URL;

export const uploadImagesAPI = async (images: FormData) => {
  try {
    const accessToken = await getAccessToken();
    const res = await fetch(`${baseURL}/images/upload`, {
      method: "POST",
      headers: {
        Authorization: accessToken ?? "",
      },
      body: images,
    });
    const obj = await res.json();
    if (obj.success) {
      return obj.data;
    } else {
      //TODO: error 페이지로 이동
      throw new Error(obj.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};
