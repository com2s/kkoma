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
    await acceptDealAPI({ dealId: params.id, code: code });
    router.replace(`/plan/${params.id}/complate`);
  };

  useEffect(() => {
    acceptDeal();
  }, []);

  return <Loading />;
}
