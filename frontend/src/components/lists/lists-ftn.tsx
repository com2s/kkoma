import APIModule from "@/utils/apiModule";
// import exp from "constants";

// export const updateMemberAPI = async (memberInfo: MemberInfo) => {
//   const res = await APIModule({ action: "/members", method: "PUT", data: memberInfo });
//   const obj = await res.json();

//   return obj;
// };

export async function getProducts() {
  const response = await APIModule({ action: "/products", method: "GET", data: null });
  
  return response;
}

export async function getProductDetail(id: string) {
  const response = await APIModule({ action: `/products/${id}`, method: "GET", data: null });

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
  const response = await APIModule({ action: "/products", method: "POST", data: data });

  return response.data;
}

export async function getImages() {
  // 이미지 기본값을 넣기 위한 임시 함수
  const images = [
    "/chicken-home.svg",
    "/next.svg",
    "/temp-img.svg",
    "/vercel.svg",
    "/images/baby-img.png",
    "/images/logo-icon.svg",
    "/images/sample1.webp",
  ];
  return images;
}
