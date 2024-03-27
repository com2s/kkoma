import APIModule from "@/utils/apiModule";

interface KidInfo {
  name: string | null;
  birthDate: string | null;
  gender: string | null;
}

export const updateKidAPI = async (kidInfo: KidInfo) => {
  try {
    const res = await APIModule({
      action: "/kids",
      method: "PUT",
      data: kidInfo,
    });
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

export interface KidsSummary {
  success: boolean;
  data: KidInfo[];
  error: {
    errorCode: string;
    errorMessage: string;
  };
}

export async function getKidsSummary() {
  const response = await APIModule({
    action: "/kids/summary",
    method: "GET",
    data: null,
  });

  return response;
}

export async function getKidDetail(kidId: number) {
  const response = await APIModule({
    action: `/kids/summary/${kidId}`,
    method: "GET",
    data: null,
  });

  return response;
}

export async function editKidDetail(kidId: number, kidInfo: KidInfo) {
  const response = await APIModule({
    action: `/kids/${kidId}`,
    method: "PUT",
    data: kidInfo,
  });

  return response;
}
