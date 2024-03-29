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

export async function getLogOut() {
  const response = await APIModule({
    action: "/logout",
    method: "GET",
    data: null,
  });

  return response;
}