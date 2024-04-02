"use client";

import { Suspense, useEffect, useState } from "react";
import { useParams } from "next/navigation";
import styles from "@/components/my-page/my-detail.module.scss";
import TopBar2 from "@/components/my-page/my-page-profile";
import MyInfoEditor from "@/components/my-page/my-profile/my-profile-edit";
import MyInfoSummary from "@/components/my-page/my-profile/my-profile-summary";
import MyPosts from "@/components/my-page/my-profile/my-profile-posts";
import { MyInfo } from "@/types/member";
import { ProductSm } from "@/types/product";
import { getProfileAPI } from "@/services/member";
import LocalStorage from "@/utils/localStorage";

interface Profile {
  memberProfileResponse: MyInfo;
  myProductList: Array<ProductSm>;
}

export default function ProfilePage() {
  const params = useParams<{ memberId: string }>();
  const [profile, setProfile] = useState<Profile>();

  const myId = LocalStorage.getItem("memberId");

  const fetchProfile = async () => {
    const res = await getProfileAPI(Number(params.memberId));
    setProfile(res);
  };

  useEffect(() => {
    fetchProfile();
  }, []);

  return (
    <div className={styles.container}>
      <TopBar2 />
      <Suspense fallback={<div>Loading...</div>}>
        {profile?.memberProfileResponse && (
          <MyInfoSummary myDetail={profile?.memberProfileResponse} />
        )}
      </Suspense>
      <Suspense fallback={<div>Loading...</div>}>
        {myId === params.memberId ? <MyInfoEditor /> : <></>}
      </Suspense>
      <Suspense fallback={<div>Loading...</div>}>
        {profile?.myProductList && <MyPosts posts={profile?.myProductList} />}
      </Suspense>
    </div>
  );
}
