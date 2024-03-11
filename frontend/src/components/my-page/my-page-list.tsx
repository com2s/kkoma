import styles from "@/components/my-page/my-page-list.module.scss";
import "@/app/globals.scss";
import Link from "next/link";
import { Icon } from "@mui/material";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import PlaylistAddIcon from "@mui/icons-material/PlaylistAdd";
import RoomOutlinedIcon from "@mui/icons-material/RoomOutlined";
import QueryBuilderIcon from "@mui/icons-material/QueryBuilder";
import PersonOutlineIcon from "@mui/icons-material/PersonOutline";

export default function MyPageList() {
  return (
    <div className={styles.myList}>
      <ul>
        <li>
          <Link href="/my-page/wish-list">
            <div>
              <FavoriteBorderIcon>icon</FavoriteBorderIcon>
              <span>찜 목록</span>
            </div>
          </Link>
        </li>
        <li>
          <Link href="/my-page/recent">
            <div>
              <PlaylistAddIcon>icon</PlaylistAddIcon>
              <span>최근 열람한 글</span>
            </div>
          </Link>
        </li>
        <li>
          <Link href="/my-page/wish-locate">
            <div>
              <RoomOutlinedIcon>icon</RoomOutlinedIcon>
              <span>거래 희망 장소 설정</span>
            </div>
          </Link>
        </li>
        <li>
          <Link href="/my-page/children">
            <div>
              <QueryBuilderIcon>icon</QueryBuilderIcon>
              <span>아이 정보 변경</span>
            </div>
          </Link>
        </li>
        <li>
          <Link href="/my-page/edit-account">
            <div>
              <PersonOutlineIcon>icon</PersonOutlineIcon>
              <span>계정 정보 변경</span>
            </div>
          </Link>
        </li>
      </ul>
    </div>
  );
}
