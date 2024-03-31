import { atom } from "recoil";
import { Gender } from "@/types/gender";
import dayjs from "dayjs";

export const kidNameState = atom<string | null>({
  key: "kidNameState",
  default: null,
});

export const kidBirthDateState = atom<string | null>({
  key: "kidBirthDateState",
  default: dayjs().format("YYYY-MM-DD"),
});

export const kidGenderState = atom<Gender | null>({
  key: "kidGenderState",
  default: "UNKNOWN",
});
