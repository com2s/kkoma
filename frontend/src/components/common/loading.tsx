"use client";
import Lottie from "lottie-react";
import lottieJson from "../../../public/images/lottie.json";

export default function Loading() {
  return (
    <div className="flex flex-col justify-center items-center py-12">
      <Lottie autoplay loop animationData={lottieJson} style={{ width: "100%" }} />
      <h2 className="text-center">조금만</h2>
      <h2 className="text-center">기다려주세요</h2>
    </div>
  );
}
