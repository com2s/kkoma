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