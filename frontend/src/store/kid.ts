import { atom } from "recoil";
import { Gender } from "@/types/gender";

export const kidNameState = atom<string | null>({
  key: "kidNameState",
  default: null,
});

export const kidYearState = atom<string | null>({
  key: "kidYearState",
  default: "2024",
});

export const kidMonthState = atom<string | null>({
  key: "kidMonthState",
  default: "01",
});

export const kidDateState = atom<string | null>({
  key: "kidDaState",
  default: "01",
});

export const kidGenderState = atom<Gender | null>({
  key: "kidGenderState",
  default: "UNKNOWN",
});
