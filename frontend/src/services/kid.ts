import APIModule from "@/utils/apiModule";

interface KidInfo {
  name: string | null;
  birthDate: string | null;
  gender: string | null;
}

export const updateKidAPI = async (kidInfo: KidInfo) => {
  try {
    const res = await APIModule({ action: "/kids", method: "PUT", data: kidInfo });
    if (res.success) {
      return res.data;
    } else {
      //TODO: error 페이지로 이동
      throw new Error(res.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};
