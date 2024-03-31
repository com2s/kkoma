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