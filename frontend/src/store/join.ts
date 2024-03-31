import { atom } from "recoil";

export const userProfileState = atom<FormData | null>({
  key: "userProfileState",
  default: null,
});

export const userNicknameState = atom<string | null>({
  key: "userNicknameState",
  default: null,
});
