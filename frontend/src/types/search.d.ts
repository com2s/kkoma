import { ProductSm } from "./product";
import { ProductStatus } from "./status";

export type SearchParms = {
  regionCode: number | null;
  categoryId: number | null;
  memberId: number | null;
  keyword: string | null;
  status: ProductStatus | null;
  page: number | null;
  size: 10 | null;
  sort: string | null;
};

export type SearchResults = {
  content: Array<ProductSm>;
  size: number;
  page: number;
  numberOfElements: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
  empty: boolean;
};
