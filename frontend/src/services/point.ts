import APIModule from "@/utils/apiModule";

export const getPointHistoryAPI = async () => {
  try {
    const res = await APIModule({
      action: `/points/history`,
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

/**
 * 포인트 충전
 *
 * @param amount
 * @returns
 */
export const chargePointAPI = async (amount: number) => {
  try {
    const res = await APIModule({
      action: `/points`,
      method: "POST",
      data: { type: "CHARGE", amount: amount },
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

/**
 * 계좌 인출
 *
 * @param amount
 * @returns
 */
export const withDrawPointAPI = async (amount: number) => {
  try {
    const res = await APIModule({
      action: `/points`,
      method: "POST",
      data: { type: "WITHDRAW", amount: amount },
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
