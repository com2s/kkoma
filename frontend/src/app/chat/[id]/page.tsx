"use client";

import SockJS from "sockjs-client";
import { CompatClient, Stomp } from "@stomp/stompjs";
import { ChangeEvent, useEffect, useState } from "react";
import { useParams } from "next/navigation";
import TopBar3 from "@/components/common/top-bar3";
import styles from "./chat.module.scss";
import { getChatListAPI } from "@/services/chat";
import { Chat } from "@/types/chat";
import LocalStorage from "@/utils/localStorage";

let ws: CompatClient;

export default function Chatting() {
  const params = useParams<{ id: string }>();
  const [chatList, setChatList] = useState<Array<Chat>>([]);
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
    connectChat();
    getChatList();
  }, []);

  return (
    <div className={styles.chat}>
      <TopBar3 />
      {chatList && chatList.length > 0 ? (
        chatList.map((item, k) => (
          <div key={k}>
            <span>{item.memberProfile.nickname}</span>:<span>{item.content}</span>
          </div>
        ))
      ) : (
        <></>
      )}
      <input type="text" onChange={handleChatInput} value={msg} />
      <button onClick={sendChat}>전송</button>
    </div>
  );
}
