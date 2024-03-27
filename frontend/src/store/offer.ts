import { atom } from "recoil";

export const offerTimeState = atom({
  key: "offerTimesState",
  default: [
    {
      offerDate: "",
      startTime: "",
      endTime: "",
    },
  ]
});  
