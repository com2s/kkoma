"use client";

import styles from "@/components/lists/lists-create.module.scss";
import TopBar2 from "@/components/lists/lists-create-bar";
import Map from "@/components/common/map";
import { postProduct } from "@/components/lists/lists-ftn";
import { uploadImagesAPI } from "@/services/upload";

import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import Image from "next/image";
import { useRouter } from "next/navigation";

import {
  Button,
  TextField,
  FormControl,
  InputLabel,
  MenuItem,
  Dialog,
} from "@mui/material";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import PhotoCameraOutlinedIcon from "@mui/icons-material/PhotoCameraOutlined";
import LocationOnOutlinedIcon from "@mui/icons-material/LocationOnOutlined";
import NavigateNextOutlinedIcon from "@mui/icons-material/NavigateNextOutlined";
import ClearIcon from "@mui/icons-material/Clear";
import { TransitionProps } from "@mui/material/transitions";
import Slide from "@mui/material/Slide";
import Title from "@/components/common/title";
import { NormalBtn } from "@/components/common/buttons";

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

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
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setLocation("");
    setOpen(false);
  };

  const handleSelect = () => {
    setOpen(false);
  };

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
      <div className="mb-4 max-w-4xl mx-auto">
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <TopBar2 />
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
            <label className="min-w-36 h-36 flex flex-col justify-center items-center bg-slate-50 rounded-md">
              <PhotoCameraOutlinedIcon className="c-text2" />
              <input
                accept="image/*"
                type="file"
                style={{ display: "none" }}
                onChange={handleImageChange}
                multiple
                disabled={images.length >= 10}
              />
              <span className="c-text2">{`${
                images ? images.length : 0
              }/10`}</span>
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
          <div
            onClick={handleClickOpen}
            className="flex items-center justify-between border-b pb-3"
          >
            <div className="flex items-center gap-2">
              <LocationOnOutlinedIcon className="c-text2" />
              {location && location !== "" ? (
                <div className="text-body2">{location}</div>
              ) : (
                <div className="text-body">거래 장소 설정</div>
              )}
            </div>
            <NavigateNextOutlinedIcon className="c-text2 min-w-fit" />
          </div>
          <Dialog
            fullScreen
            open={open}
            onClose={handleClose}
            TransitionComponent={Transition}
          >
            <div className={styles.dialog}>
              <div className="flex justify-end pb-2 w-full ">
                <ClearIcon onClick={handleClose} />
              </div>
              <Title
                title="거래하고 싶은 장소를 선택해주세요"
                subtitle="누구나 찾기 쉬운 공공장소가 좋아요"
              />
              <Image
                src={"/images/Pin.png"}
                alt="pin"
                width={100}
                height={100}
              />
              {location ? (
                <div className="text-caption !text-white bg-gray-500 py-1 px-2 rounded-xl w-fit	text-center">
                  {location}
                </div>
              ) : (
                <></>
              )}
              <Map setLocation={setLocation} />
              <NormalBtn next={handleSelect}>선택</NormalBtn>
            </div>
          </Dialog>
        </form>
      </div>
    </div>
  );
}
