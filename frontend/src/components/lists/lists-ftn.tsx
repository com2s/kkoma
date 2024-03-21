const baseURL = process.env.NEXT_PUBLIC_API_URL;
const accessToken = 'Bearer ' + process.env.NEXT_PUBLIC_TEMP_TOKEN_1;
console.log("baseURL: ", baseURL);
// console.log("accessToken: ", accessToken);
// console.log("accessToken: ", process.env.NEXT_PUBLIC_TEMP_TOKEN_1);
// const accessToken = process.env.TEMP_ACCESS_TOKEN_2;


// export async function getProducts() {
//   // new Promise((resolve) => setTimeout(resolve, 500));
//   const products = [
//     {
//       id: "1",
//       url: "/temp-img.svg",
//       price: "37000",
//       productName: "Product 1",
//       town: "동네 2",
//       time: "2024-02-28 05:20",
//       views: 20,
//       likes: 11,
//       status: "거래 중",
//     },
//     {
//       id: "2",
//       url: "/temp-img.svg",
//       price: "19000",
//       productName: "Product 2",
//       town: "동네 2",
//       time: "2024-02-25 13:33",
//       views: 71,
//       likes: 16,
//       status: "거래 완료",
//     },
//     {
//       id: "3",
//       url: "/temp-img.svg",
//       price: "31000",
//       productName: "Product 3",
//       town: "동네 8",
//       time: "2024-03-01 06:08",
//       views: 40,
//       likes: 24,
//       status: "판매 중",
//     },
//     {
//       id: "4",
//       url: "/temp-img.svg",
//       price: "44000",
//       productName: "Product 4",
//       town: "동네 3",
//       time: "2024-02-15 11:47",
//       views: 77,
//       likes: 33,
//       status: "거래 중",
//     },
//     {
//       id: "5",
//       url: "/temp-img.svg",
//       price: "17000",
//       productName: "Product 5",
//       town: "동네 6",
//       time: "2024-02-27 05:48",
//       views: 44,
//       likes: 12,
//       status: "거래 완료",
//     },
//     {
//       id: "6",
//       url: "/temp-img.svg",
//       price: "24000",
//       productName: "Product 6",
//       town: "동네 3",
//       time: "2024-02-12 11:17",
//       views: 50,
//       likes: 15,
//       status: "거래 완료",
//     },
//     {
//       id: "7",
//       url: "/temp-img.svg",
//       price: "25000",
//       productName: "Product 7",
//       town: "동네 9",
//       time: "2024-02-18 09:35",
//       views: 82,
//       likes: 44,
//       status: "거래 완료",
//     },
//     {
//       id: "8",
//       url: "/temp-img.svg",
//       price: "24000",
//       productName: "Product 8",
//       town: "동네 3",
//       time: "2024-02-16 04:58",
//       views: 93,
//       likes: 47,
//       status: "거래 완료",
//     },
//     {
//       id: "9",
//       url: "/temp-img.svg",
//       price: "37000",
//       productName: "Product 9",
//       town: "동네 7",
//       time: "2024-02-14 23:03",
//       views: 97,
//       likes: 23,
//       status: "판매 중",
//     },
//     {
//       id: "10",
//       url: "/temp-img.svg",
//       price: "19000",
//       productName: "Product 10",
//       town: "동네 8",
//       time: "2024-02-27 14:14",
//       views: 46,
//       likes: 28,
//       status: "거래 중",
//     },
//     {
//       id: "11",
//       url: "/temp-img.svg",
//       price: "37000",
//       productName: "Product 11",
//       town: "동네 5",
//       time: "2024-03-04 01:08",
//       views: 84,
//       likes: 37,
//       status: "거래 중",
//     },
//     {
//       id: "12",
//       url: "/temp-img.svg",
//       price: "47000",
//       productName: "Product 12",
//       town: "동네 4",
//       time: "2024-02-13 14:21",
//       views: 62,
//       likes: 43,
//       status: "판매 중",
//     },
//     {
//       id: "13",
//       url: "/temp-img.svg",
//       price: "16000",
//       productName: "Product 13",
//       town: "동네 4",
//       time: "2024-03-08 14:47",
//       views: 57,
//       likes: 34,
//       status: "거래 완료",
//     },
//     {
//       id: "14",
//       url: "/temp-img.svg",
//       price: "45000",
//       productName: "Product 14",
//       town: "동네 4",
//       time: "2024-03-02 06:25",
//       views: 43,
//       likes: 17,
//       status: "거래 완료",
//     },
//     {
//       id: "15",
//       url: "/temp-img.svg",
//       price: "28000",
//       productName: "Product 15",
//       town: "동네 6",
//       time: "2024-02-11 12:03",
//       views: 55,
//       likes: 28,
//       status: "판매 중",
//     },
//     {
//       id: "16",
//       url: "/temp-img.svg",
//       price: "44000",
//       productName: "Product 16",
//       town: "동네 9",
//       time: "2024-03-08 13:59",
//       views: 30,
//       likes: 11,
//       status: "거래 완료",
//     },
//     {
//       id: "17",
//       url: "/temp-img.svg",
//       price: "47000",
//       productName: "Product 17",
//       town: "동네 3",
//       time: "2024-02-18 17:28",
//       views: 160,
//       likes: 36,
//       status: "판매 중",
//     },
//     {
//       id: "18",
//       url: "/temp-img.svg",
//       price: "31000",
//       productName: "Product 18",
//       town: "동네 3",
//       time: "2024-02-14 02:40",
//       views: 98,
//       likes: 11,
//       status: "거래 중",
//     },
//     {
//       id: "19",
//       url: "/temp-img.svg",
//       price: "11000",
//       productName: "Product 19",
//       town: "동네 10",
//       time: "2024-02-20 19:22",
//       views: 109,
//       likes: 39,
//       status: "판매 중",
//     },
//     {
//       id: "20",
//       url: "/temp-img.svg",
//       price: "43000",
//       productName: "Product 20",
//       town: "동네 6",
//       time: "2024-03-09 20:35",
//       views: 150,
//       likes: 46,
//       status: "거래 완료",
//     },
//   ];

//   return products;
// }

export async function getProducts() {
  const response = await fetch(`${baseURL}/products`, {
    method: "GET",
    headers: {
      Authorization: accessToken ?? "",
    },
  });

  return response.json();
}

export async function getProductDetail(id: string) {
  const response = await fetch(`${baseURL}/products/${id}`, {
    method: "GET",
    headers: {
      Authorization: accessToken ?? "",
    },
  });

  if (!response.ok) {
    throw new Error("Something is wrong...");
  }

  return response.json();
}

export async function getImages() {
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
