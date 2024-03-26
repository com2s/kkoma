export interface Deal {
    dealPlace: string;
    elapsedMinutes: number;
    id: number;
    offerCount: number;
    status: 'SALE' | 'PROGRESS' | 'SOLD' | 'CANCELLED' | 'SENT' ;
    thumbnailImage: string;
    price: number;
    title: string;
    type: 'BUY' | 'PROGRESS' | 'SELL';
    viewCount: number;
    wishCount: number;
  }