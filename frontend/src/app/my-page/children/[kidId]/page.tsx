"use client";

import { editKidDetail, getKidDetail, KidInfo } from "@/services/kid";
import React, { useEffect, useState } from "react";
import TextField from "@mui/material/TextField";
import { InputLabel, MenuItem, Select } from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";
import { ButtonContainer, NormalBtn } from "@/components/common/buttons";

interface IParams {
  params: { kidId: string };
}

export default function ChildDetailPage({ params: { kidId } }: IParams) {
  const [kidDetail, setKidDetail] = useState<KidInfo | null>(null);
  const [success, setSuccess] = useState(true);
  const [name, setName] = useState("");
  const [gender, setGender] = useState("UNKNOWN");
  const [birthDate, setBirthDate] = useState("");

  const fetchData = async () => {
    const res = await getKidDetail(parseInt(kidId));
    console.log(res);
    setKidDetail(res.data);
    setSuccess(res.success);
    if (!res.success) {
      console.error(res.error);
    }
    if (res !== null) {
      setName(res.data.name);
      setGender(res.data.gender);
      setBirthDate(res.data.birthDate);
    }
  };

  const updateData = (data: KidInfo) => {
    setName(data.name ?? "");
    setGender(data.gender ?? "UNKNOWN");
    setBirthDate(data.birthDate ?? "");
  }

  useEffect(() => {
    fetchData();
  }, []);

  const handleGenderChange = (event: SelectChangeEvent) => {
    setGender(event.target.value);
  };

  if (success === false) {
    return (
      <div className="border-t-yellow-300 border-t-2">
        <h1>아이 정보를 불러오는데 실패했습니다.</h1>
      </div>
    );
  }

  const handleSubmitButton = async () => {
    const data = {
      id: parseInt(kidId),
      name: name,
      birthDate: birthDate,
      gender: gender === "" ? null : gender,
    };
    const res = await editKidDetail(data);
    if (res.success) {
      console.log("success");
      updateData(res.data);
      alert("아이 정보가 수정되었습니다.");
    } else {
      console.error(res.error);
      alert("아이 정보 수정에 실패했습니다.");
    }
  };

  return (
    <div className="border-t-yellow-300 border-t-4 pt-4">
      <div className="flex-row">
        <TextField
          id="standard-basic"
          label="이름"
          variant="standard"
          placeholder="ex) 이싸피"
          value={name}
          onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
            setName(event.target.value);
          }}
          sx={{
            margin: "1rem 0.5rem",
            width: "70%",
          }}
        />
        <Select
          labelId="gender-select-standard-label"
          id="gender-select-standard"
          value={gender}
          onChange={handleGenderChange}
          // label="성별"
          sx={{
            margin: "2rem 0.5rem",
            width: "50%",
          }}
        >
          <MenuItem value="MALE">남자</MenuItem>
          <MenuItem value="FEMALE">여자</MenuItem>
          <MenuItem value="UNKNOWN">
            <em>미정</em>
          </MenuItem>
        </Select>
        <TextField
          id="standard-basic"
          label="생년월일"
          variant="standard"
          // placeholder="ex) 20240325"
          type="date"
          InputLabelProps={{
            shrink: true,
          }}
          value={birthDate}
          onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
            setBirthDate(event.target.value);
          }}
          sx={{
            margin: "1rem 0.5rem",
            width: "70%",
            minWidth: "240px",
          }}
        />
      </div>
      <ButtonContainer>
        <button
          className="bg-yellow-300 h-14 w-5/6 max-w-96 rounded-lg"
          onClick={handleSubmitButton}
        >
          <strong className="text-gray-600">수정하기</strong>
        </button>
      </ButtonContainer>
    </div>
  );
}
