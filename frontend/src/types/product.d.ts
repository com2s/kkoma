export interface ProductSm {
  id: number;
  thumbnail_image: string;
  title: string;
  price: number;
}

export interface Product {
  success: boolean;
  data: [
    {
      id: number;
      thumbnailImage: string;
      title: string;
      dealPlace: string;
      price: number;
      status: "SALE" | "PROGRESS" | "SOLD";
      elapsedMinutes: number;
    }
  ];
  error: {
    errorCode: string;
    errorMessage: string;
  };
}

interface DetailParams {
  success: boolean;
  data: {
    id: number;
    productImages: string[];
    title: string;
    description: string;
    categoryName: string;
    price: number;
    status: "SALE";
    dealPlace: string;
    elapsedMinutes: number;
    memberSummary: {
      memberId: number;
      profileImage: string;
      nickname: string;
      preferredPlace: string;
    };
    wishCount: number;
    offerCount: number;
    viewCount: number;
  };
  error: {
    errorCode: string;
    errorMessage: string;
  };
}
