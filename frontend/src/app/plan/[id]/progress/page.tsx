"use client";

import Loading from "@/components/common/loading";
import { useEffect } from "react";
import { useRouter, useParams, useSearchParams } from "next/navigation";
import { acceptDealAPI } from "@/services/deals";

export default function DealProgress() {
  const router = useRouter();
  const params = useParams<{ id: string }>();
  const query = useSearchParams();

  const code = query.get("code");

  const acceptDeal = async () => {
    const res = await acceptDealAPI({ dealId: params.id, code: code });
    console.log("res==", res);
    if (res) {
      router.replace(`/plan/${params.id}/complate`);
    } else {
      router.replace(`/plan`);
    }
  };

  useEffect(() => {
    acceptDeal();
  }, []);

  return <Loading />;
}
