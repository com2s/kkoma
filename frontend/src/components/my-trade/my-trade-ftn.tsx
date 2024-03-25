import APIModule from "@/utils/apiModule";
// import exp from "constants";

// export const updateMemberAPI = async (memberInfo: MemberInfo) => {
//   const res = await APIModule({ action: "/members", method: "PUT", data: memberInfo });
//   const obj = await res.json();

//   return obj;
// };
interface TypeData {
  type: "sell" | "buy" | "progress";
}

export async function getMyProducts(type: TypeData["type"]) {
  const queryObject = {
    type: type,
  };

  const query = new URLSearchParams(queryObject).toString();

  const response = await APIModule({
    action: `/members/products?${query}`,
    method: "GET",
    data: null,
  });

  return response.data;
}

export async function getRequesters(productId: string) {
  const response = await APIModule({
    action: `/offers/products/${productId}`,
    method: "GET",
    data: null,
  });

  return response.data;
}
