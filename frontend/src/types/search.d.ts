import { ProductSm } from "./product";
import { ProductStatus } from "./status";

type SearchParms = {
  [index: string]: number | string | ProductStatus | 10 | null;
  regionCode: number | null;
  categoryId: number | null;
  memberId: number | null;
  keyword: string | null;
  status: ProductStatus | null;
  page: number | null;
  size: number | null;
  sort: string | null;
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
