"use client";

import { useState } from "react";
import Image from "next/image";
import { ProductCard } from "@/components/common/product-card";
import { Accordion, AccordionActions, AccordionDetails, AccordionSummary } from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { ProductSm } from "@/types/product";
import { NoContents } from "@/components/common/no-contents";

interface Posts {
  posts: Array<ProductSm>;
}

export default function MyProfilePosts({ posts }: Posts) {
  const [expanded, setExpanded] = useState(false); // 아코디언 확장 상태 관리

  const handleAccordionChange =
    (panel: boolean) => (event: React.SyntheticEvent, isExpanded: boolean) => {
      setExpanded(isExpanded ? panel : false);
    };

  const handleCloseClick = () => {
    setExpanded(false); // 아코디언 닫기
  };

  return (
    <div className=" mx-auto my-8">
      <Accordion
        sx={{
          margin: "auto",
          minWidth: "200px",
          borderBottom: "2px solid #d3d3d3",
          "&.MuiPaper-root": { boxShadow: "none" },
        }}
        expanded={expanded}
        onChange={handleAccordionChange(true)}
      >
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1-content"
          id="panel1-header"
          sx={{ margin: "auto" }}
        >
          등록한 거래글 {posts.length}개
        </AccordionSummary>
        {posts.length === 0 && (
          <NoContents>
            <h4 className="c-text3">작성한 글이 없어요</h4>
            <Image src={"/images/Empty-BOX.png"} alt="empty" width={100} height={100} />
          </NoContents>
        )}
        {posts.map((post, index) => (
          <AccordionDetails key={index} sx={{ margin: "auto" }}>
            <ProductCard product={post} next={`/lists/${post.id}`} />
          </AccordionDetails>
        ))}
        <AccordionActions
          sx={{
            margin: "15px auto 10px",
            display: "flex",
            justifyContent: "center",
          }}
        ></AccordionActions>
      </Accordion>
    </div>
  );
}
