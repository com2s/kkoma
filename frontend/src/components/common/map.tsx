"use client";

import { useEffect, useState, Dispatch, SetStateAction } from "react";
import FmdGoodIcon from "@mui/icons-material/FmdGood";

declare global {
  interface Window {
    kakao: any;
  }
}

interface Props {
  setLocation?: Dispatch<SetStateAction<string>>;
  setRegionCode?: Dispatch<SetStateAction<string>>;
  setRegion?: Dispatch<SetStateAction<string>>;
  setLng?: Dispatch<SetStateAction<number>>;
  setLat?: Dispatch<SetStateAction<number>>;
}

export default function Map({ setLocation, setRegionCode, setRegion, setLng, setLat }: Props) {
  let map: any, geocoder: any;
  const MAP_API_KEY = process.env.NEXT_PUBLIC_KAKAO_JS_KEY;

  const displayCenterInfo = (result: any, status: any) => {
    if (status === window.kakao.maps.services.Status.OK) {
      for (let i = 0; i < result.length; i++) {
        if (result[i].region_type === "B") {
          setRegion && setRegion(result[i].region_1depth_name + " " + result[i].region_2depth_name);
          setRegionCode && setRegionCode(result[i].code);
          break;
        }
      }
    }
  };

  const displayAddressInfo = (result: any, status: any) => {
    if (status === window.kakao.maps.services.Status.OK) {
      if (result[0]?.road_address?.address_name) {
        setLocation && setLocation(result[0].road_address.address_name);
      } else {
        setLocation && setLocation(result[0].address.address_name);
      }
    }
  };

  const searchAddrFromCoords = (coords: any) => {
    if (setLng && setLat) {
      setLng(coords.getLng());
      setLat(coords.getLat());
    }
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), displayCenterInfo);
    geocoder.coord2Address(coords.getLng(), coords.getLat(), displayAddressInfo);
  };

  useEffect(() => {
    const kakaoMapScript = document.createElement("script");
    kakaoMapScript.async = false;
    kakaoMapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${MAP_API_KEY}&autoload=false&libraries=services`;
    console.log(kakaoMapScript.src);
    document.head.appendChild(kakaoMapScript);

    const onLoadKakaoAPI = () => {
      window.kakao.maps.load(() => {
        let container = document.getElementById("map");
        let options = {
          center: new window.kakao.maps.LatLng(33.450701, 126.570667),
          level: 3,
        };

        map = new window.kakao.maps.Map(container, options);
        geocoder = new window.kakao.maps.services.Geocoder();

        window.navigator.geolocation.getCurrentPosition((p) => {
          let lat = p.coords.latitude;
          let lng = p.coords.longitude;
          map.setCenter(new window.kakao.maps.LatLng(lat, lng));
        });

        window.kakao.maps.event.addListener(map, "idle", () =>
          searchAddrFromCoords(map.getCenter())
        );
      });
    };

    kakaoMapScript.addEventListener("load", onLoadKakaoAPI);
  }, []);

  return (
    <main className="w-full flex flex-col items-center justify-center pt-1">
      <div className="w-full aspect-square relative">
        <div id="map" style={{ width: "100%", height: "100%", borderRadius: "10px" }}></div>
        <div className="pointer-events-none absolute z-10 top-0 left-0 w-full h-full flex items-center justify-center">
          <FmdGoodIcon fontSize="large" className="c-text2" />
        </div>
      </div>
    </main>
  );
}
