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

  return response;
}

export async function getBuyProducts() {
  const response = await APIModule({
    action: `/members/products/buy`,
    method: "GET",
    data: null,
  });

  return response;
}

export async function getRequesters(productId: string) {
  const response = await APIModule({
    action: `/offers/products/${productId}`,
    method: "GET",
    data: null,
  });

  return response;
}

export async function patchOfferAccept(offerId: string, date: string, time: string) {
  const koreaTime = date + "T" + time + ":00";
  const isoString = new Date(koreaTime).toISOString();
  const queryObject = {
    type: "accept",
  };
  const query = new URLSearchParams(queryObject).toString();
  const response = await APIModule({
    action: `/offers/${offerId}?${query}`,
    method: "PUT",
    data: {
      selectedTime: isoString,
    },
  });

  return response;
}

export async function patchOfferReject(offerId: number) {
  const queryObject = {
    type: "reject",
  };
  const query = new URLSearchParams(queryObject).toString();
  const response = await APIModule({
    action: `/offers/${offerId}?${query}`,
    method: "PUT",
    data: null,
  });

  return response;
}
