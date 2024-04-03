export type ProductStatus = "SALE" | "PROGRESS" | "SOLD";
export type OfferStatus = "CANCELLED" | "SENT" | "ACCEPTED" | "REJECTED";
export type DealStatus = "BUY" | "PROGRESS" | "SELL";

export interface Deal {
  dealPlace: string;
  elapsedMinutes: number;
  id: number;
  offerCount: number;
  status: ProductType;
  offerStatus: OfferStatus;
  thumbnailImage: string;
  price: number;
  title: string;
  type: DealStatus;
  viewCount: number;
  wishCount: number;
}
