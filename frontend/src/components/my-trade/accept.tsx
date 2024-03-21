import Image from "next/image";
import React from "react";

export default function Accept() {
  return (
    <div className="p-8">
      <h2>거래 수락이 완료 되었어요</h2>
      <p className="my-2 text-gray-400">이제 1:1 채팅이 가능해요!</p>
      <Image
        src={"/images/message1.svg"}
        alt="거래 수락"
        width={300}
        height={300}
      />
    </div>
  );
}
