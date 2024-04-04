import APIModule from "@/utils/apiModule";
// import exp from "constants";

// export const updateMemberAPI = async (memberInfo: MemberInfo) => {
//   const res = await APIModule({ action: "/members", method: "PUT", data: memberInfo });
//   const obj = await res.json();

//   return obj;
// };

export async function getProducts() {
  const response = await APIModule({
    action: "/products",
    method: "GET",
    data: null,
  });

  return response;
}

export async function getProductDetail(id: string) {
  const response = await APIModule({
    action: `/products/${id}`,
    method: "GET",
    data: null,
  });

  return response;
}

interface PostData {
  productImages: string[];
  categoryId: number | null;
  title: string;
  description: string;
  price: number;
}

export async function postProduct(data: PostData) {
  const response = await APIModule({
    action: "/products",
    method: "POST",
    data: data,
  });

  return response;
}

interface PostOffer {
  offerDate: string;
  startTime: string;
  endTime: string;
}

export async function postOffer(productId: string, selectedTimes: string[][]) {
  const data: PostOffer[] = [];
  selectedTimes.map((time) => {
    if (time[0] === "") return;

    const [offerDate, times] = time;
    const [start, end] = times.split(" ~ ");

    data.push({
      offerDate,
      startTime: start === "24:00" ? "23:59" : start,
      endTime: end === "24:00" ? "23:59" : end,
    });
  });

  const response = await APIModule({
    action: `/offers/products/${productId}`,
    method: "POST",
    data: data,
  });

  return response;
}

export async function getIsBuyable(productId: string) {
  const response = await APIModule({
    action: `/points/products/${productId}`,
    method: "GET",
  });

  return response;
}
