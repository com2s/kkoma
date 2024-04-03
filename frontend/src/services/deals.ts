import APIModule from "@/utils/apiModule";

interface DealInfo {
  dealId: string;
  code?: string | null;
}

export const getDealListAPI = async () => {
  try {
    const res = await APIModule({
      action: `/members/products?type=progress`,
      method: "GET",
    });
    if (res.success) {
      return res.data;
    } else {
      new Error(res.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};

export const getDealCodeAPI = async ({ dealId }: DealInfo) => {
  try {
    const res = await APIModule({
      action: `/deals/${dealId}/code`,
      method: "GET",
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

export const acceptDealAPI = async ({ dealId, code }: DealInfo) => {
  try {
    const res = await APIModule({
      action: `/deals/${dealId}/accept?code=${code}`,
      method: "POST",
    });
    if (res.success) {
      console.log("success deal accept", res.success);
      return res.data;
    } else {
      if (res.error.errorCode === "DEAL-004") {
        alert(res.error.errorMessage);
      }
      return null;
    }
  } catch (e: any) {}
};
