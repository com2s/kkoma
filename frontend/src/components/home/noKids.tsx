import { NoContents } from "../common/no-contents";
import RecommendOutlinedIcon from "@mui/icons-material/RecommendOutlined";

export function NoKids() {
  return (
    <NoContents>
      <h4 className="flex items-center">
        <RecommendOutlinedIcon className="c-text3" />
        상품 추천
      </h4>
      <div className="text-caption c-text3 whitespace-pre-wrap text-center">
        {`아이 정보를 입력해보세요.\n우리 아이에게 딱 맞는 상품을\n추천해드릴게요`}
      </div>
    </NoContents>
  );
}
