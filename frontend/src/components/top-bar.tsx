import * as React from 'react';
import AppBar from '@mui/material/AppBar';
// AppBar 컴포넌트는 상단에 고정된 헤더를 만들 때 사용합니다.
// 그래서 이 컴포넌트에 쓰면 헤더가 중복이 되어 에러가 발생합니다.
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

export default function TopBar() {
  return (
    <div className="bg-gray-800 text-white h-12 flex justify-between items-center px-4 mb-2 top-0 left-0 right-0">
    <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
        왼쪽 버튼
    </button>
    <div className="flex">
        <button className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded mr-2">
            오른쪽 버튼 1
        </button>
        <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
            오른쪽 버튼 2
        </button>
    </div>
</div>
  );
}
