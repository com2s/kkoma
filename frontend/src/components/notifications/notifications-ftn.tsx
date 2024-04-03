import APIModule from "@/utils/apiModule";

export async function getMyNotifications(page?: number, size?: number) {
  const queryObject = {
    page: page?.toString() ?? "0",
    size: size?.toString() ?? "20",
  };

  const query = new URLSearchParams(queryObject).toString();

  const response = await APIModule({
    action: `/notifications?${query}`,
    method: "GET",
    data: null,
  });

  return response;
}

export async function postReadNotification(notificationId: number) {
  const response = await APIModule({
    action: `/notifications/read/${notificationId}`,
    method: "POST",
    data: null,
  });
  console.log("READ NOTIFICATION");
  return response;
}
