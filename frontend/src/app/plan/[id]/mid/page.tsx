"use client";

import Loading from "@/components/common/loading";
import { useEffect } from "react";
import { useRouter, useParams } from "next/navigation";

export default function Mid() {
  //TODO: axios 연결해서 거래 완료 처리 필요
  const router = useRouter();
  const params = useParams<{ id: string }>();

  useEffect(() => {
    router.replace(`/plan/${params.id}/complate`);
  }, [router, params.id]);

  return <Loading />;
}
