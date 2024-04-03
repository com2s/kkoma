import { Category } from "@/types/product";
import { SearchParms, SearchResults } from "@/types/search";
import APIModule from "@/utils/apiModule";

export const getSearchProductAPI = async (params: SearchParms) => {
  const queryString = Object.keys(params)
    .filter((i) => params[i] !== null && params[i] !== 0)
    .map((k) => {
      const value = params[k];
      if (value !== null) {
        return `${encodeURIComponent(k)}=${encodeURIComponent(value)}`;
      } else {
        return null;
      }
    })
    .join("&");

  try {
    const res = await APIModule({
      action: `/products/search?${queryString}`,
      method: "GET",
    });
    if (res.success) {
      return res.data as SearchResults;
    } else {
      new Error(res.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};

export const getCategoryAPI = async () => {
  try {
    const res = await APIModule({
      action: `/categories`,
      method: "GET",
    });
    if (res.success) {
      return res.data;
    } else {
      new Error(res.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};

export const getRecommendAPI = async () => {
  try {
    const res = await APIModule({
      action: `/products/recommend?num=4`,
      method: "GET",
    });
    if (res.success) {
      return res.data;
    } else {
      new Error(res.error.errorMessage);
    }
  } catch (e: any) {}
};

export const getWishProductAPI = async () => {
  try {
    const res = await APIModule({
      action: `/products/hourly/wish`,
      method: "GET",
    });
    if (res.success) {
      return res.data;
    } else {
      new Error(res.error.errorMessage);
    }
  } catch (e: any) {}
};

export const getViewProductAPI = async () => {
  try {
    const res = await APIModule({
      action: `/products/hourly/view`,
      method: "GET",
    });
    if (res.success) {
      return res.data;
    } else {
      new Error(res.error.errorMessage);
    }
  } catch (e: any) {}
};
