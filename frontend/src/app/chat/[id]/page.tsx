"use client";

import SockJS from "sockjs-client";
import { CompatClient, Stomp } from "@stomp/stompjs";
import { ChangeEvent, useEffect, useState } from "react";
import { useParams } from "next/navigation";
import Image from "next/image";
import TopBar3 from "@/components/common/top-bar3";
import styles from "./chat.module.scss";
import { getChatListAPI, getProductAPI } from "@/services/chat";
import { Chat } from "@/types/chat";
import LocalStorage from "@/utils/localStorage";

import SendIcon from "@mui/icons-material/Send";
import { ProductCard } from "@/components/common/product-card";
import { ChatProduct } from "@/types/product";

let ws: CompatClient;

export default function Chatting() {
  const params = useParams<{ id: string }>();
  const [chatList, setChatList] = useState<Array<Chat>>([]);
  const [product, setProduct] = useState<ChatProduct>();
  const [canChat, setCanChat] = useState<boolean>(true);
  const [msg, setMsg] = useState<string>("");

  const connectChat = () => {
    const serverURL = process.env.NEXT_PUBLIC_API_URL + "/chat";
    const socket = new SockJS(serverURL);
    ws = Stomp.over(socket);
    process.env.NODE_ENV === "production" ? (ws.debug = () => {}) : "";
    ws.connect(
      {},
      () => {
        ws.subscribe(`/topic/chatRooms/${params.id}`, (res) => {
          setChatList((list) => [...list, JSON.parse(res.body)]);
        });
      },
      (e: any) => {
        console.log("소켓 연결 실패", e);
      }
    );
  };

  const getChatList = async () => {
    const res = await getChatListAPI(params.id);
    setChatList(res);
  };

  const getProduct = async () => {
    const res = await getProductAPI(params.id);
    setProduct(res);
  };

  const handleChatInput = (e: ChangeEvent<HTMLInputElement>) => {
    setMsg(e.target.value);
  };

  const sendChat = () => {
    console.log(ws);
    if (ws && ws.connected && msg.length > 0) {
      const send = {
        memberId: LocalStorage.getItem("memberId"),
        content: msg,
      };
      ws.send(`/app/chatRooms/${params.id}`, {}, JSON.stringify(send));
      setMsg("");
    }
  };

  useEffect(() => {
    getProduct();
    connectChat();
    getChatList();
  }, []);

  useEffect(() => {
    if (
      product &&
      (product.status === "SOLD" || product.status === "PROGRESS")
    ) {
      const myId = Number(LocalStorage.getItem("memberId"));
      if (product.buyerId !== myId && product.sellerId !== myId) {
        setCanChat(false);
      }
    }
  }, [product]);

  return (
    <div className={styles.chat}>
      <TopBar3 />
      {product ? (
        <div className={styles.product}>
          <ProductCard
            product={{
              id: product.id,
              thumbnail_image: product.thumbnailImage,
              title: product.title,
              price: product.price,
            }}
          />
        </div>
      ) : (
        <></>
      )}
      <section className={styles.chatBody}>
        {chatList && chatList.length > 0 ? (
          chatList.map((item, k) => (
            <div
              key={k}
              className={
                item.memberProfile.id === 2 ? styles.chatRight : styles.chatLeft
              }
            >
              <Image
                className={styles.profile}
                src={item.memberProfile.profileImage}
                alt="profile"
                width={30}
                height={30}
              />
              <div className={styles.chatBox}>
                <span className={styles.nickname}>
                  {item.memberProfile.nickname}
                </span>
                <span className={styles.content}>{item.content}</span>
                <span className={styles.time}>{item.sentTime}</span>
              </div>
            </div>
          ))
        ) : (
          <></>
        )}
      </section>
      <div className={styles.inputLine}>
        <input
          type="text"
          onChange={handleChatInput}
          value={msg}
          placeholder={canChat ? "채팅 입력" : "이미 진행 중인 거래입니다."}
          disabled={!canChat}
        />
        {canChat ? (
          <button onClick={sendChat}>
            <SendIcon className="c-text1" />
          </button>
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}
