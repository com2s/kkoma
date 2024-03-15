// src/theme.ts
"use client";

import { Noto_Sans_KR } from "next/font/google";
import { createTheme } from "@mui/material/styles";

const notoSansKr = Noto_Sans_KR({ subsets: ["cyrillic"] });

const theme = createTheme({
  typography: {
    fontFamily: notoSansKr.style.fontFamily,
  },
});

export default theme;
