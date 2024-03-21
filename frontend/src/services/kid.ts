import APIModule from "@/utils/apiModule";

interface KidInfo {
  name: string;
  birthDate: string;
  gender: string;
}

export const updateKidAPI = async (kidInfo: KidInfo) => {
  const res = await APIModule({ action: "/kids", method: "PUT", data: kidInfo });
  const obj = await res.json();

  return obj;
};
