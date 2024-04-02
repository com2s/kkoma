"use client";

import styles from "@/components/my-page/my-detail.module.scss";
import React, { ChangeEvent, useEffect, useState, useRef } from "react";
import { getMyInfo, putMyInfo } from "@/components/my-page/my-page-ftn";
import { MyInfo } from "@/types/member";

import { uploadImagesAPI } from "@/services/upload";

import Avatar from "@mui/material/Avatar";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import {
  Accordion,
  AccordionActions,
  AccordionDetails,
  AccordionSummary,
  Button,
  TextField,
} from "@mui/material";

export default function MyProfileEdit() {
  const [success, setSuccess] = useState(true);
  const [myDetail, setMyDetail] = useState<MyInfo | null>(null);
  const [nickname, setNickname] = useState<string | null>("");
  const [name, setName] = useState<string | null>("");
  const [phone, setPhone] = useState<string | null>("");
  const [image, setImage] = useState<{ url: string; file?: File }>();
  const [expanded, setExpanded] = useState(false); // 아코디언 확장 상태 관리

  useEffect(() => {
    const fetchData = async () => {
      const res = await getMyInfo();
      setSuccess(res.success);
      setMyDetail(res);
      if (res.success) {
        updateData(res.data);
      }
    };
    fetchData();
  }, []);

  const updateData = (data: MyInfo["data"]) => {
    setName(data.name ?? "");
    setNickname(data.nickname ?? "");
    setPhone(data.phone ?? "");
    setImage({ url: data.profileImage ?? "" });
  };

  // 이미지 파일 변경 처리
  const handleImageChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      const uploadedImage = event.target.files[0];
      const formData = new FormData();
      formData.append("images", uploadedImage);
      setImage({
        url: URL.createObjectURL(uploadedImage),
        file: uploadedImage,
      });
    }
  };

  const handleAccordionChange =
    (panel: boolean) => (event: React.SyntheticEvent, isExpanded: boolean) => {
      setExpanded(isExpanded ? panel : false);
    };

  // 데이터초기화
  const resetData = () => {
    setNickname(myDetail?.data.nickname ?? "");
    setName(myDetail?.data.name ?? "");
    setPhone(myDetail?.data.phone ?? "");
    setImage({ url: myDetail?.data.profileImage ?? "" });
  };

  const handleCloseClick = () => {
    resetData();
    setExpanded(false); // 아코디언 닫기
  };

  const handleEditClick = async () => {
    const formData = new FormData();
    let profileImage = null;
    let isUpload = false;
    if (image?.file) {
      isUpload = true;
      formData.append("images", image.file);
      // console.log("image: ", image);
      profileImage = await uploadImagesAPI(formData);
      // console.log("profileImage: ", profileImage);
    }

    const data = {
      // profileImage: profileImage ? profileImage[0] : null,
      profileImage: isUpload ? profileImage[0] : myDetail?.data.profileImage,
      nickname: nickname === "" ? null : nickname,
      name: name === "" ? null : name,
      phone: phone === "" ? null : phone,
    };
    console.log("data: ", data);
    const res = await putMyInfo(data);
    await alert("수정되었습니다.");
    setExpanded(false); // 아코디언 닫기
    window.location.reload();
  };
  return (
    <div className="mx-auto my-8">
      {success === false && (
        <div className="border-t-yellow-300 border-t-2">
          <h1>내 정보를 불러오는데 실패했습니다.</h1>
        </div>
      )}
      {success === true && myDetail && (
        <>
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
            >
              프로필 수정
            </AccordionSummary>
            <AccordionDetails sx={{ margin: "auto", paddingX: "2rem" }}>
              <label className="min-w-36 h-36 flex flex-col justify-center items-center bg-slate-50 rounded-md">
                <Avatar
                  src={image?.url ?? ""}
                  alt="Profile Image"
                  // sx={{ width: 56, height: 56 }}
                  className={`${styles.responsiveImg} mx-4`}
                />
                <input
                  accept="image/*"
                  type="file"
                  style={{ display: "none" }}
                  onChange={handleImageChange}
                />
              </label>
              {/* <Button color="warning" sx={{display:'block'}}>사진 지우기</Button> */}
              <TextField
                fullWidth
                id="standard-basic"
                label="닉네임"
                variant="standard"
                sx={{ marginY: 2, marginX: "auto" }}
                value={nickname}
                onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                  setNickname(event.target.value);
                }}
              />
            </AccordionDetails>
            <AccordionActions
              sx={{
                margin: "12px auto",
                display: "flex",
                justifyContent: "center",
                width: "70%",
              }}
            >
              <Button onClick={handleEditClick} variant="outlined">
                수정
              </Button>
            </AccordionActions>
          </Accordion>
        </>
      )}
    </div>
  );
}
