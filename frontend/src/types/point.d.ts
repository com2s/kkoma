export type PointHistory = {
  content: Array<History>;
  size: number;
  page: number;
  numberOfElements: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
  empty: boolean;
};

export type PointChangeType = "CHARGE" | "PAY" | "PROFIT" | "REFUND" | "WITHDRAW";
