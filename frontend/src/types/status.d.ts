export interface Deal {
    dealPlace: string;
    elapsedMinutes: number;
    id: number;
    offerCount: number;
    status: 'SALE' | 'PROGRESS' | 'SOLD' | 'CANCELED' | 'SENT' ;
    thumbnailImage: string;
    title: string;
    type: 'BUY' | 'PROGRESS' | 'SELL';
    viewCount: number;
    wishCount: number;
  }