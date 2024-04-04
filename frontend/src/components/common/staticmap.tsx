"use client";

import { useEffect } from "react";

declare global {
  interface Window {
    kakao: any;
  }
}

interface Props {
  lat: number;
  lng: number;
}

export function StaticMap({ lat, lng }: Props) {
  let map: any;
  const MAP_API_KEY = process.env.NEXT_PUBLIC_KAKAO_JS_KEY;

  useEffect(() => {
    const kakaoMapScript = document.createElement("script");
    kakaoMapScript.async = false;
    kakaoMapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${MAP_API_KEY}&autoload=false&libraries=services`;
    document.head.appendChild(kakaoMapScript);

    const onLoadKakaoAPI = () => {
      window.kakao.maps.load(() => {
        let markerPosition = new window.kakao.maps.LatLng(lng, lat);

        let marker = {
          position: markerPosition,
        };

        const container = document.getElementById("map");

        // map.relayout();

        let options = {
          center: new window.kakao.maps.LatLng(lng, lat),
          level: 4,
          marker: marker,
        };

        map = new window.kakao.maps.StaticMap(container, options);
      });
    };

    kakaoMapScript.addEventListener("load", onLoadKakaoAPI);
  }, []);

  return (
    <div className="w-full mt-2">
      <div id="map" style={{ width: "100%", height: "160px", borderRadius: "10px" }}></div>
    </div>
  );
}
