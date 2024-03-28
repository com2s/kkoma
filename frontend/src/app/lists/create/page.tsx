"use client";

import styles from "@/components/lists/lists-create.module.scss";
import TopBar2 from "@/components/lists/lists-create-bar";
import Map from "@/components/common/map";
import { postProduct } from "@/components/lists/lists-ftn";
import { uploadImagesAPI } from "@/services/upload";

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
import PhotoCameraOutlinedIcon from "@mui/icons-material/PhotoCameraOutlined";
import CalendarMonthOutlinedIcon from "@mui/icons-material/CalendarMonthOutlined";
import ClearIcon from "@mui/icons-material/Clear";

export default function CreatePost() {
  const formButtonRef = useRef<HTMLButtonElement>(null); // form 참조 생성
  const scrollRef = useRef(null);
  const [category, setCategory] = useState<number>(1); // TODO: 카테고리
  const [title, setTitle] = useState("");
  const [price, setPrice] = useState<number | null>();
  const [content, setContent] = useState("");
  const [images, setImages] = useState<{ url: string; file: File }[]>([]);
  const [location, setLocation] = useState(""); // TODO: 위치 정보 입력 받기
  const router = useRouter();

  const handleCategoryChange = (event: SelectChangeEvent) => {
    //TODO: 변경처리 해야됨
    // setCategory(parseInt(event.target.value));
    setCategory(1);
  };

  // 가격 변경 처리
  const handleChangePrice = (value: string) => {
    const numberValue = parseInt(value);
    if (isNaN(numberValue)) {
      setPrice(null);
      return;
    } else if (numberValue >= 0) {
      setPrice(numberValue);
    } else {
      setPrice(null);
    }
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
    if (!title.trim() || !content.trim() || !price) {
      alert("제목, 내용, 가격은 필수 입력 사항입니다.");
      return;
    }
    // 확인 대화 상자 표시
    if (!window.confirm("게시글을 제출하시겠습니까?")) {
      return;
    }

    event.preventDefault();

    // 이미지 업로드
    const formData = new FormData();
    images.forEach((image) => {
      formData.append("images", image.file);
    });
    const productImages = await uploadImagesAPI(formData);

    const categoryId = category === 0 ? null : category;
    const description = content;
    const data = {
      productImages,
      categoryId,
      title,
      description,
      price,
    };

    const response = await postProduct(data);

    router.push("/lists");
  };

  return (
    <div className={styles.container}>
      {/* form 내부에 있는 제출 버튼을 끌어온다. */}
      <TopBar2 onFormSubmit={() => formButtonRef.current?.click()} />
      <div className="mb-4  max-w-4xl mx-auto">
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          {/*  이미지를 렌더링할 때 파일 이름도 표시하고 삭제 버튼을 제공 */}
          <div
            ref={scrollRef}
            className="flex items-end grid-cols-3 gap-3 overflow-x-auto scroll-smooth mb-8 "
          >
            {images.map((image, index) => (
              <div key={index} className="flex flex-col items-end h-auto">
                <div className="relative w-36 h-36 flex justify-center rounded">
                  <button
                    onClick={() => handleRemoveImage(index)}
                    className="absolute z-10 right-1"
                  >
                    <ClearIcon className="c-text2" />
                  </button>
                  {/* 이미지의 컨테이너 높이를 조정 */}
                  <Image
                    src={image.url}
                    alt={`Uploaded Image ${index + 1}`}
                    layout="fill"
                    objectFit="contain"
                    className="rounded-md"
                  />
                </div>
              </div>
            ))}
            <label className="min-w-36 h-36 block flex flex-col justify-center items-center bg-slate-50 rounded-md">
              <PhotoCameraOutlinedIcon className="c-text2" />
              <input
                accept="image/*"
                type="file"
                style={{ display: "none" }}
                onChange={handleImageChange}
                multiple
                disabled={images.length >= 10}
              />
              <span className="c-text2">{`${images ? images.length : 0}/10`}</span>
            </label>
          </div>
          <div className="flex gap-4">
            <FormControl size="medium" fullWidth>
              <InputLabel id="demo-select-small-label">카테고리</InputLabel>
              <Select
                labelId="카테고리-선택-라벨"
                id="카테고리-선택"
                value={category.toString()}
                label="카테고리"
                onChange={handleCategoryChange}
              >
                <MenuItem value="">
                  <em>없음</em>
                </MenuItem>
                <MenuItem value={0}>카테고리 없음 {category}</MenuItem>
                <MenuItem value={1}>카테고리 1 : {category}</MenuItem>
                <MenuItem value={2}>카테고리 2 : {category}</MenuItem>
              </Select>
            </FormControl>
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
            label="가격 입력"
            variant="outlined"
            value={price}
            onChange={(e) => handleChangePrice(e.target.value)}
            type="number"
            helperText="숫자만 입력해주세요."
            required
            sx={{ my: 1, width: "50%", minWidth: "200px" }}
            InputProps={{
              inputProps: {
                min: 0, // 최소값 설정
                step: 100, // 여기에서 step 값을 정의합니다.
              },
            }}
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

          <Button ref={formButtonRef} type="submit" color="primary" hidden>
            게시글 올리기
          </Button>
        </form>
      </div>
    </div>
  );
}
