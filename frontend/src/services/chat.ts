import { ChatProduct } from "@/types/product";
import APIModule from "@/utils/apiModule";

export const getChatListAPI = async (id: string) => {
  try {
    const res = await APIModule({
      action: `/chatRooms/${id}`,
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

export const getProductAPI = async (id: string) => {
  try {
    const res = await APIModule({
      action: `/chatRooms/${id}/product`,
      method: "GET",
    });
    if (res.success) {
      return res.data as ChatProduct;
    } else {
      new Error(res.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};
