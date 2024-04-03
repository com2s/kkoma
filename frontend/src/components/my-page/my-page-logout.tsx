"use client";

import { ListItem, ListItemIcon, ListItemText } from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";
import { postLogOut } from "@/components/common/common-ftn";

export default function MyPageLogout() {
  const clickLogOut = async () => {
    if (window.confirm("로그아웃 하시겠습니까?")) {
      // const logOut = await postLogOut();
      // console.log(logOut);
      // if (logOut.success) {
      //   console.log("로그아웃 성공");
      //   window.location.href = "/welcome";
      // } else {
      //   console.error("로그아웃 실패");
      // }
      try {
        const logOut = await postLogOut();
        console.log(logOut);
        if (logOut.success) {
          localStorage.clear();
          console.log("로그아웃 성공");
          window.location.href = "/welcome";
        } else {
          console.error("로그아웃 실패");
        }
      } catch (err) {
        console.error("로그아웃 실패");
      }

    } else {
      console.log("로그아웃 취소");
    }
  };

  return (
    <ListItem
      button
      onClick={clickLogOut}
      sx={{ marginTop: "1rem", width: "160px" }}
    >
      <ListItemIcon>
        <LogoutIcon color="warning" />
      </ListItemIcon>
      <ListItemText primary="로그아웃" sx={{ color: "crimson" }} />
    </ListItem>
  );
}
