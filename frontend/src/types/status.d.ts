export type ProductStatus = "SALE" | "PROGRESS" | "SOLD" | "CANCELLED" | "SENT";
export type DealStatus = "BUY" | "PROGRESS" | "SELL";

export interface Deal {
  dealPlace: string;
  elapsedMinutes: number;
  id: number;
  offerCount: number;
  status: ProductType;
  thumbnailImage: string;
  price: number;
  title: string;
  type: DealStatus;
  viewCount: number;
  wishCount: number;
}
