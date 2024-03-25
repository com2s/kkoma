"use client";

import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import InputLabel from "@mui/material/InputLabel";
import OutlinedInput from "@mui/material/OutlinedInput";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import { Dispatch, SetStateAction } from "react";
import { useRecoilState } from "recoil";
import { kidYearState, kidMonthState, kidDateState } from "@/store/kid";

interface selecterProps {
  title: string;
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
}

export function BirthdaySelecter({ title, open, setOpen }: selecterProps) {
  const [year, setYear] = useRecoilState(kidYearState);
  const [month, setMonth] = useRecoilState(kidMonthState);
  const [date, setDate] = useRecoilState(kidDateState);

  const now = new Date();

  let yearList: Array<string> = Array.from({ length: 35 }, (_, i) => `${i + 1990}`);
  let monthList: Array<string> = Array.from({ length: 12 }, (_, i) => `0${i + 1}`.slice(-2));
  let dateList: Array<string> = Array.from({ length: 31 }, (_, i) => `0${i + 1}`.slice(-2));

  const handleChangeYear = (event: SelectChangeEvent<typeof year>) => {
    setYear(event.target.value);
  };

  const handleChangeMonth = (event: SelectChangeEvent<typeof month>) => {
    setMonth(event.target.value);
  };

  const handleChangeDate = (event: SelectChangeEvent<typeof date>) => {
    setDate(event.target.value);
  };

  const handleClose = (event: React.SyntheticEvent<unknown>, reason?: string) => {
    if (reason !== "backdropClick") {
      setOpen(false);
    }
  };

  return (
    <Dialog disableEscapeKeyDown open={open} onClose={handleClose}>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>
        <Box component="form" sx={{ display: "flex", flexWrap: "wrap" }}>
          <FormControl sx={{ m: 1, minWidth: 120 }}>
            <InputLabel htmlFor="demo-dialog-native">년</InputLabel>
            <Select
              value={year}
              onChange={handleChangeYear}
              input={<OutlinedInput label="년" id="demo-dialog-native" />}
            >
              {yearList.map((item, k) => (
                <MenuItem value={item} key={k}>
                  {item}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <FormControl sx={{ m: 1, minWidth: 120 }}>
            <InputLabel id="demo-dialog-select-label">월</InputLabel>
            <Select
              labelId="demo-dialog-select-label"
              id="demo-dialog-select"
              value={month}
              onChange={handleChangeMonth}
              input={<OutlinedInput label="월" />}
            >
              {monthList.map((item, k) => (
                <MenuItem value={item} key={k}>
                  {item}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <FormControl sx={{ m: 1, minWidth: 120 }}>
            <InputLabel id="demo-dialog-select-label">일</InputLabel>
            <Select
              labelId="demo-dialog-select-label"
              id="demo-dialog-select"
              value={date}
              onChange={handleChangeDate}
              input={<OutlinedInput label="일" />}
            >
              {dateList.map((item, k) => (
                <MenuItem value={item} key={k}>
                  {item}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>다음</Button>
      </DialogActions>
    </Dialog>
  );
}
