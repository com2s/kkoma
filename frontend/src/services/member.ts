import APIModule from "@/utils/apiModule";

interface MemberInfo {
  profileImage: string;
  nickname: string;
}

export const updateMemberAPI = async (memberInfo: MemberInfo) => {
  try {
    const res = await APIModule({
      action: "/members",
      method: "PUT",
      data: memberInfo,
    });
    if (res.success) {
      return res.data;
    } else {
      //TODO: error 페이지로 이동
      throw new Error(res.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};

export const updatePreferredPlaceAPI = async (regionCode: string) => {
  try {
    const res = await APIModule({
      action: "/members/place",
      method: "PUT",
      data: { preferredPlaceRegionCode: regionCode },
    });
    if (res.success) {
      return res.data;
    } else {
      //TODO: error 페이지로 이동
      throw new Error(res.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};

export const getProfileAPI = async (memberId: number) => {
  try {
    const res = await APIModule({
      action: `/members/${memberId}/profile`,
      method: "GET",
    });
    if (res.success) {
      return res.data;
    } else {
      //TODO: error 페이지로 이동
      throw new Error(res.error.errorMessage);
    }
  } catch (e: any) {
    //TODO: error 페이지로 이동
  }
};
