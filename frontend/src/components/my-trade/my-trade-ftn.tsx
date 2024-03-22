export async function getRequesters() {
  const requesters = [
    { 
      userId : "1",
      url : "/temp-img.svg",
      times : [
        { start : "2024-03-22 11:00", end : "2024-03-22 18:00" },
        { start : "2024-03-28 11:00", end : "2024-03-28 18:00" },
        { start : "2024-04-04 11:00", end : "2024-04-04 18:00" },
      ],
    },
    { 
      userId : "2",
      url : "/temp-img.svg",
      times : [
        { start : "2024-03-22 11:00", end : "2024-03-22 18:00" },
        { start : "2024-03-28 11:00", end : "2024-03-28 18:00" },
        { start : "2024-04-04 11:00", end : "2024-04-04 18:00" },
      ],
    },
    { 
      userId : "3",
      url : "/temp-img.svg",
      times : [
        { start : "2024-03-22 11:00", end : "2024-03-22 18:00" },
        { start : "2024-03-28 11:00", end : "2024-03-28 18:00" },
        { start : "2024-04-04 11:00", end : "2024-04-04 18:00" },
      ],
    },
  ];
  return requesters;
}

import APIModule from "@/utils/apiModule";
// import exp from "constants";

// export const updateMemberAPI = async (memberInfo: MemberInfo) => {
//   const res = await APIModule({ action: "/members", method: "PUT", data: memberInfo });
//   const obj = await res.json();

//   return obj;
// };
interface TypeData {
    type: 'sell' | 'buy' | 'progress';
}

export async function getMyProducts(type: TypeData['type']) {
  const queryObject = {
    type: type,
  };

  const query = new URLSearchParams(queryObject).toString();

  const response = await APIModule({
    action: `/members/products?${query}`,
    method: "GET",
    data: null,
  });

  return response.json();
}
