import APIModule from "@/utils/apiModule";

export const sendWishAPI = async (id: string) => {
  try {
    const res = await APIModule({
      action: `/products/${id}/wish`,
      method: "POST",
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

export const sendUnWishAPI = async (id: string) => {
  try {
    const res = await APIModule({
      action: `/products/${id}/unwish`,
      method: "PUT",
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
