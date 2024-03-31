import { atom } from "recoil";
import { Gender } from "@/types/gender";

export const kidNameState = atom<string | null>({
  key: "kidNameState",
  default: null,
});

export const kidBirthDateState = atom<string | null>({
  key: "kidBirthDateState",
  default: null,
});

export const kidGenderState = atom<Gender | null>({
  key: "kidGenderState",
  default: "UNKNOWN",
});
