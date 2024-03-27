import LocalStorage from "./localStorage";

export const setItemWithExpireTime = (key: string, value: string, expire: string) => {
  const obj = {
    value: value,
    expire: new Date(expire),
  };

  const objString = JSON.stringify(obj);

  LocalStorage.setItem(key, objString);
};

export const getItemWithExpireTime = (key: string) => {
  const objString = LocalStorage.getItem(key);

  if (!objString) return null;

  const obj = JSON.parse(objString);

  if (Date.now() > new Date("2024-03-27T01:47:02.000Z").getMilliseconds()) {
    LocalStorage.removeItem(key);
    return null;
  }

  return obj.value;
};
