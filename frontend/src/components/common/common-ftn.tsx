import APIModule from "@/utils/apiModule";
// import exp from "constants";

// export const updateMemberAPI = async (memberInfo: MemberInfo) => {
//   const res = await APIModule({ action: "/members", method: "PUT", data: memberInfo });
//   const obj = await res.json();

//   return obj;
// };

export async function getMyPoints() {
  const response = await APIModule({
    action: "/points/summary",
    method: "GET",
    data: null,
  });

  return response;
}

// 구매가능한 포인트가 있는가? (부족할 경우 success: false)
export async function getIsBuyable(productId: string) {
  const response = await APIModule({
    action: `/points/products/${productId}`,  
    method: "GET",
    data: null,
  });

  return response;
}

export async function getLogOut() {
  const response = await APIModule({
    action: "/logout",
    method: "GET",
    data: null,
  });

  return response;
}