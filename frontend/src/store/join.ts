import { atom } from "recoil";

export const userProfileState = atom<FormData | null>({
  key: "userProfileState",
  default: null,
});

export const userNicknameState = atom<string | null>({
  key: "userNicknameState",
  default: null,
});

export const userNameState = atom<string | null>({
  key: "userNameState",
  default: null,
});

export const userPhoneState = atom<string | null>({
  key: "userPhoneState",
  default: null,
});
