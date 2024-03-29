import styles from "@/components/my-page/my-page-list.module.scss";
import "@/app/globals.scss";
import Link from "next/link";
import LogOutItem from "@/components/my-page/my-page-logout";

import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import PlaylistAddIcon from "@mui/icons-material/PlaylistAdd";
import RoomOutlinedIcon from "@mui/icons-material/RoomOutlined";
import QueryBuilderIcon from "@mui/icons-material/QueryBuilder";

import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';

export default function MyPageList() {
  return (
    <div className={styles.myList}>
      <List>
        <ListItem button component={Link} href="/my-page/wish-list">
          <ListItemIcon>
            <FavoriteBorderIcon />
          </ListItemIcon>
          <ListItemText primary="찜 목록" />
        </ListItem>
        <ListItem button component={Link} href="/my-page/recent">
          <ListItemIcon>
            <PlaylistAddIcon />
          </ListItemIcon>
          <ListItemText primary="최근 열람한 글" />
        </ListItem>
        <ListItem button component={Link} href="/my-page/wish-locate">
          <ListItemIcon>
            <RoomOutlinedIcon />
          </ListItemIcon>
          <ListItemText primary="거래 희망 장소 설정" />
        </ListItem>
        <ListItem button component={Link} href="/my-page/children">
          <ListItemIcon>
            <QueryBuilderIcon />
          </ListItemIcon>
          <ListItemText primary="아이 정보 변경" />
        </ListItem>
        <LogOutItem />
      </List>
    </div>
  );
}

