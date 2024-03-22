import APIModule from "@/utils/apiModule";

interface KidInfo {
  name: string | null;
  birthDate: string | null;
  gender: string | null;
}

export const updateKidAPI = async (kidInfo: KidInfo) => {
  const res = await APIModule({ action: "/kids", method: "PUT", data: kidInfo });
  const obj = await res.json();

  return obj;
};
