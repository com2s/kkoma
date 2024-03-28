export type ChatMember = {
  id: number;
  nickname: string;
  profileImage: string;
};

export type Chat = {
  memberProfile: ChatMember;
  content: string;
  sentTime: string;
};
