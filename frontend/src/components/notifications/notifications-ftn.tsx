import APIModule from "@/utils/apiModule";

export async function getMyNotifications() {
  const response = await APIModule({
    action: "/notifications",
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
  console.log("READ NOTIFICATION")
  return response;
}