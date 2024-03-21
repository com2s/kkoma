"use client";

import styles from "@/components/lists/lists-create.module.scss";
import TopBar2 from "@/components/lists/lists-create-bar";
import Map from "@/components/common/map";

import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import {
  Button,
  TextField,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Typography,
  FormControl,
  InputLabel,
  MenuItem,
  //   ImageList,
  //   ImageListItem,
} from "@mui/material";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import Image from "next/image";
import { useRouter } from "next/navigation";

import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import LocationOnOutlinedIcon from "@mui/icons-material/LocationOnOutlined";
import PhotoCameraOutlinedIcon from '@mui/icons-material/PhotoCameraOutlined';
import CalendarMonthOutlinedIcon from "@mui/icons-material/CalendarMonthOutlined";

export default function CreatePost() {
  const formButtonRef = useRef<HTMLButtonElement>(null); // form 참조 생성
  const scrollRef = useRef(null);
  const [category, setCategory] = useState(""); // TODO: 카테고리
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [images, setImages] = useState<{ url: string; file: File }[]>([]);
  const [location, setLocation] = useState(""); // TODO: 위치 정보 입력 받기
  const router = useRouter();

  // 카테고리 변경 처리
  const handleCategoryChange = (event: SelectChangeEvent) => {
    setCategory(event.target.value);
  };

  // 이미지 파일 변경 처리
  const handleImageChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      const uploadedImages = Array.from(event.target.files).map((file) => ({
        url: URL.createObjectURL(file),
        file: file, // 파일 자체를 저장
      }));
      setImages([...images, ...uploadedImages]);
    }
  };

  // images 배열이 변경될 때마다 실행
  useEffect(() => {
    if (scrollRef.current) {
      (scrollRef.current as HTMLElement).scrollLeft = (
        scrollRef.current as HTMLElement
      ).scrollWidth;
    }
  }, [images]);

  // 이미지 삭제 처리
  const handleRemoveImage = (index: number) => {
    setImages(images.filter((_, i) => i !== index));
  };

  // 폼 제출 처리
  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    if (!title.trim() || !content.trim()) {
      alert("제목과 내용은 필수 입력 사항입니다.");
      return;
    }
    // 확인 대화 상자 표시
    if (!window.confirm("게시글을 제출하시겠습니까?")) {
      return;
    }

    event.preventDefault();
    const formData = new FormData();
    formData.append("title", title);
    formData.append("content", content);
    images.forEach(({ file }) => formData.append("images", file));

    // 백엔드로 데이터 전송 로직 여기에 추가...
    console.log({ title, content }); // 실제 로직에서는 제거해야 합니다.

    // 성공적으로 데이터 전송 후 페이지 이동
    router.push("/lists");
  };

  return (
    <div className={styles.container}>
      {/* form 내부에 있는 제출 버튼을 끌어온다. */}
      <TopBar2 onFormSubmit={() => formButtonRef.current?.click()} />
      <div className="my-4  max-w-4xl mx-auto">
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          {/*  이미지를 렌더링할 때 파일 이름도 표시하고 삭제 버튼을 제공 */}
          <div
            ref={scrollRef}
            className="flex grid-cols-3 gap-3 overflow-x-auto mb-8 "
          >
            {images.map((image, index) => (
              <div
                key={index}
                className="flex flex-col justify-between p-2 h-64 w-1/4 min-w-32"
              >
                <div className="relative w-full h-52 flex justify-center">
                  {/* 이미지의 컨테이너 높이를 조정 */}
                  <Image
                    src={image.url}
                    alt={`Uploaded Image ${index + 1}`}
                    // width={100}
                    // height={100}
                    layout="fill"
                    objectFit="contain"
                    className="h-40"
                  />
                </div>
                <Typography className="text-center line-clamp-1 my-2 pb-2">
                  {image.file.name}
                </Typography>
                {/* 파일 이름 표시 */}
                <Button
                  variant="outlined"
                  color="error"
                  onClick={() => handleRemoveImage(index)}
                  className="self-center" // 버튼을 중앙으로 정렬
                >
                  삭제
                </Button>
                {/* 삭제 버튼 */}
              </div>
            ))}
          </div>
          <div className="flex gap-4">
          <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
            <InputLabel id="demo-select-small-label">Age</InputLabel>
            <Select
              labelId="카테고리-선택-라벨"
              id="카테고리-선택"
              value={category}
              label="카테고리"
              onChange={handleCategoryChange}
            >
              <MenuItem value="">
                <em>없음</em>
              </MenuItem>
              <MenuItem value={1}>카테고리 : {category}</MenuItem>
              <MenuItem value={2}>카테고리 : {category}</MenuItem>
              <MenuItem value={3}>카테고리 : {category}</MenuItem>
            </Select>
          </FormControl>
          {/* 버튼으로 인풋 작동하기 */}
          <Button variant="outlined" component="label" className="px-1 h-10 my-auto">
            <PhotoCameraOutlinedIcon />
            <input
              accept="image/*"
              type="file"
              style={{ display: "none" }}
              onChange={handleImageChange}
              multiple
            />
          </Button>
          </div>
          <TextField
            label="제목 입력"
            variant="outlined"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            fullWidth
          />
          <TextField
            label="판매 제품 소개"
            variant="outlined"
            multiline
            rows={6}
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
            fullWidth
          />
          {/* 선택 옵션 */}
          <Accordion>
            {/* 거래 장소 선택 */}
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel1-content"
              id="panel1-header"
            >
              <LocationOnOutlinedIcon className="mr-4" />
              <span>거래 장소 설정</span>
            </AccordionSummary>
            <AccordionDetails>
              <span>여기서 주소 받아오기</span>
              <Map />
            </AccordionDetails>
          </Accordion>
          {/* 거래 일시 선택 */}
          {/* <Accordion>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel2-content"
              id="panel2-header"
            >
              <CalendarMonthOutlinedIcon className="mr-4" />
              <span>거래 날짜 설정</span>
            </AccordionSummary>
            <AccordionDetails>
              <span>임시로 만든 컴포넌트</span>
            </AccordionDetails>
          </Accordion> */}

          {/* TopBar2 내의 작성 버튼과 연결하고 보이지 않게 함 */}
          <Button ref={formButtonRef} type="submit" color="primary" hidden>
            게시글 올리기
          </Button>
        </form>
      </div>
    </div>
  );
}
