export interface MemberSummary {
  memberId: number;
  profileImage: string;
  nickname: string;
  preferredPlace: string;
}

export interface MyInfo {
  id: number | null;
  profileImage: string | null;
  email: string | null;
  nickname: string | null;
  name: string | null;
  phone: string | null;
  role: string | null;
}

export interface MemberInfo {
  id: number | null;
  profileImage: string | null;
  email: string | null;
  nickname: string | null;
  role: string | null;
}

export interface WishContent {
  id: number;
  thumbnailImage: string;
  title: string;
  dealPlace: string;
  price: number;
  status: "SALE" | "PROGRESS" | "SOLD";
  elapsedMinutes: number;
}

export interface WishData {
  content: WishContent[];
  size: number;
  page: number;
  numberOfElements: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

export interface MyInfo {
  success: boolean;
  data: {
    id: number | null;
    profileImage: string | null;
    email: string | null;
    nickname: string | null;
    name: string | null;
    phone: string | null;
    role: string | null;
  };
  error: {
    errorCode: string;
    errorMessage: string;
  };
}

export interface mySummary {
  success: boolean;
  data: {
    profileImage: string;
    nickname: string;
    preferredPlace: string;
  };
  error: {
    errorCode: string;
    errorMessage: string;
  };
}

export interface RecentPost {
    id: number;
    thumbnailImage: string;
    title: string;
    dealPlace: string;
    price: number;
    status: "SALE" | "SOLD" | "PROGRESS";
    elapsedMinutes: string;
}

export interface RecentList {
    success: boolean;
    data: {
        content: RecentPost[];
        size: number;
        page: number;
        numberOfElements: number;
        totalElements: number;
        totalPages: number;
        first: boolean;
        last: boolean;
        empty: boolean;
    };
    error: {
        errorCode: string;
        errorMessage: string;
    };
}

interface Notification {
    id: number;
    message: string;
    destination: string;
    sentAt: string;
    readAt: string|null;
}

export interface NotificationList {
    success: boolean;
    data: {
        content: Notification[];
        size: number;
        page: number;
        numberOfElements: number;
        totalElements: number;
        totalPages: number;
        first: boolean;
        last: boolean;
        empty: boolean;
    };
    error: {
        errorCode: string;
        errorMessage: string;
    };
}
