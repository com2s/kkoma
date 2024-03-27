export function MoneyFormat(money: number) {
  return money.toLocaleString() + "원";
}

export function KidBirthFormat(birthDate: string) {
  const today = new Date();
  const birth = new Date(birthDate);

  const diff = today.getTime() - birth.getTime();
  const dday = Math.floor(Math.abs(diff) / (1000 * 60 * 60 * 24));
  if (diff < 0) {
    //아직 태어나지 않았을 때

    return `0살 (D-${dday})`;
  } else if (dday < 781) {
    //25개월 이하일 때는 개월수로 표시

    const month = dday / 30;
    return `${month}개월 (D+${dday})`;
  } else {
    //만나이, 디데이 표시
    let age = today.getFullYear() - birth.getFullYear();
    let m = today.getMonth() - birth.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birth.getDate())) {
      age--;
    }
    return `만 ${age}살 (D+${dday})`;
  }
}
