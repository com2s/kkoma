import { getAccessToken } from "@/utils/getAccessToken";

const baseURL = process.env.NEXT_PUBLIC_API_URL;

export const uploadImagesAPI = async (images: FormData) => {
  const res = await fetch(`${baseURL}/images/upload`, {
    method: "POST",
    headers: {
      Authorization:
        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE3MTA5MjI4MjAsImV4cCI6MTcxMjEzMjQyMCwibWVtYmVySWQiOjY3LCJyb2xlIjoiVVNFUiJ9.VOKOQ51mVSeLYDa9pOoQC8j70V5m7sL-kbh-uYFwWRYYAweMwI2WYytx4PVaFo46lUbDdP3hoUYblyt68KOWMw",
    },
    body: images,
  });
  // const res = await APIModule({
  //   action: "/images/upload",
  //   method: "POST",
  //   data: images,
  // });
  const obj = res.json();
  return obj;
};
