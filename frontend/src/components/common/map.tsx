"use client"

import { useEffect, useState } from "react";

declare global {
  interface Window {
    kakao: any;
  }
}

// TODO: 사용자 위치 받아오기, 마커 찍기, 마커 주소 받아오기
export default function Home() {
    // .env.local 파일에 API 키를 저장하고 불러온다.
    // const MAP_API_KEY 에 저장하지 않고 바로 쓸 수도 있다.
    const MAP_API_KEY = process.env.NEXT_PUBLIC_KAKAO_JS_KEY
    
    useEffect(() => {
        const kakaoMapScript = document.createElement('script')
        kakaoMapScript.async = false
        // 카카오 API 키를 쓸 때 먼저 우리의 사이트 도메인을 등록해야한다.
        kakaoMapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${MAP_API_KEY}&autoload=false`
        console.log(kakaoMapScript.src)
        document.head.appendChild(kakaoMapScript)
      
        const onLoadKakaoAPI = () => {
          window.kakao.maps.load(() => {
            var container = document.getElementById('map')
            var options = {
              center: new window.kakao.maps.LatLng(33.450701, 126.570667),
              level: 3,
            }
      
            var map = new window.kakao.maps.Map(container, options)
          })
        }
      
        kakaoMapScript.addEventListener('load', onLoadKakaoAPI)
      }, [])

  return (
    <main className="w-full flex flex-col items-center justify-center pt-4">
      <div className="w-full h-auto aspect-square">
        <div id="map" style={{ width: "100%", height: "100%" }}></div>
      </div>
    </main>
  );
}
