export const setItemWithExpireTime = (key: string, value: string, expire: string) => {
  const obj = {
    value: value,
    expire: new Date(expire),
  };

  const objString = JSON.stringify(obj);

  window.localStorage.setItem(key, objString);
};

export const getItemWithExpireTime = (key: string) => {
  const objString = window.localStorage.getItem(key);

  if (!objString) return null;

  const obj = JSON.parse(objString);

  if (Date.now() > obj.expire) {
    window.localStorage.removeItem(key);
    return null;
  }

  return obj.value;
};
