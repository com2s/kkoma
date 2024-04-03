import APIModule from "@/utils/apiModule";

export const getUnreadNotificationAPI = async () => {
  try {
    const res = await APIModule({
      action: `/notifications/hasUnread`,
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
