export interface OfferTime {
  offerDate: string;
  startTime: string;
  endTime: string;
}

export interface Requester {
  id: number;
  memberProfile: {
    id: number;
    nickname: string;
    profileImage: string;
  };
  offerTimes: [
    {
      offerDate: string;
      startTime: string;
      endTime: string;
    }
  ];
}
