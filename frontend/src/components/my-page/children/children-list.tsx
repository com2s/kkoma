import { Card, CardContent, Typography, IconButton } from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";

export default function ChildrenList() {
  const children = [
    {
      id: "1",
      name: "재율이",
      birth: true,
      birthday: "2022-01-02",
      gender: "남자",
    },
    {
      id: "2",
      name: "태명",
      birth: false,
      birthday: "2024-04-02",
      gender: "-",
    },
  ];

  return (
    <div>
      <h1>아이 정보</h1>
      {children.map((child) => (
        <Card
          key={child.id}
          className="my-6 flex justify-between border-yellow-300 rounded-xl
          border-2 p-4"
        >
          <CardContent className="w-48">
            <Typography variant="h5">{child.name}</Typography>
            <Typography variant="h6">{child.birthday} {child.birth? '생':'예정'}</Typography>
          </CardContent>
          <CardContent className="bg-blue-200 rounded-xl h-24 w-24 aspect-square flex justify-center">
            <Typography variant="h6" className="flex justify-center">
              <p>{child.gender}</p>
            </Typography>
          </CardContent>
          <IconButton className="w-16">
            <ArrowForwardIosIcon />
          </IconButton>
        </Card>
      ))}
      <Card className="my-6 flex justify-center border-gray-300 rounded-xl h-32
          border-2 hover:bg-gray-100 transition duration-300 ease-in-out p-4">
        <CardContent>
            <Typography variant="h5" align="justify">아이 추가</Typography>
        </CardContent>
      </Card>
    </div>
  );
}
