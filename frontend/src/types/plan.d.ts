export interface PlanCard {
  id: number;
  thumbnailImage: string;
  chatRoomId: number;
  price: number;
  title: string;
  dealPlace: string;
  dealId: number;
  selectedTime: string;
  wishCount?: number | null;
  offerCount?: number | null;
  viewCount?: number | null;
}
