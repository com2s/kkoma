import { ProductSm } from "./product";
import { ProductStatus } from "./status";

type SearchParms = {
  [regionCode: string]: number | null;
  [categoryId: string]: number | null;
  [memberId: string]: number | null;
  [status: string]: ProductStatus | null;
  [page: string]: number | null;
  [size: string]: 10 | null;
  [keyword: string]: string | null;
  [sort: string]: string | null;
};

type SearchResults = {
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

export type { SearchParms, SearchResults };
