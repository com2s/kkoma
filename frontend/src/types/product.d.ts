import { ProductStatus } from "./status";

interface ProductSm {
  id: number;
  thumbnailImage: string;
  title: string;
  dealPlace: string;
  price: number;
  status: ProductStatus;
  elapsedMinutes: number | string;
  wishCount?: number | null;
  offerCount?: number | null;
  viewCount?: number | null;
}

type ProductSmCard = {
  id: number;
  thumbnailImage: string;
  title: string;
  price: number;
};

type ChatProduct = {
  id: number;
  thumbnailImage: string;
  title: string;
  price: number;
  status: ProductStatus;
  sellerId: number;
  buyerId: number;
};

interface Category {
  id: number | null;
  name: string;
}

interface Product {
  success: boolean;
  data: Array<ProductSm>;
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
    status: ProductStatus;
    dealPlace: string;
    elapsedMinutes: number;
    memberSummary: {
      memberId: number;
      profileImage: string;
      nickname: string;
      preferredPlace: string;
    };
    chatRoomId: number;
    wish: boolean;
    wishCount: number;
    offerCount: number;
    viewCount: number;
  };
  error: {
    errorCode: string;
    errorMessage: string;
  };
}

export type {
  ProductSm,
  ProductSmCard,
  ChatProduct,
  Category,
  Product,
  DetailParams,
};
