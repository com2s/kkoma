// 'use client';

import React from 'react';

// fetch 함수를 사용할 때 method, headers, body 등을 명시적으로 지정하지 않으면 기본값이 적용됩니다:

// method: 기본값은 "GET"입니다. 
// 따라서 fetch 함수에 특별한 메소드를 지정하지 않으면, 
// 그 요청은 "GET" 요청으로 간주됩니다.

// headers: 기본적으로는 빈 객체({})로 설정됩니다. 
// 특정 Content-Type이나 인증 헤더(Authorization) 등을 설정하지 않으면, 
// 서버로 전송되는 요청 헤더는 기본값만 포함하게 됩니다.

// body: GET과 HEAD 요청을 제외한 다른 모든 요청 타입에 사용될 수 있습니다. 
// 기본값은 null입니다. body는 POST, PUT, PATCH 등의 요청에서 데이터를 전송할 때 사용됩니다.


// GET 요청을 수행하는 함수 // Promise<any> 생략 가능
// async function fetchData(): Promise<any> {
async function fetchData() {
  const response = await fetch('https://api.example.com/data', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    },
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  return response.json();
}

// POST 요청을 수행하는 함수
async function postData(url: string, data: object): Promise<any> {
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  return response.json();
}

// postData 함수 사용
postData('https://api.example.com/data', { key: 'value' })
  .then(data => {
    console.log(data); // 여기서 data는 API로부터 받은 응답 데이터입니다.
  })
  .catch(error => {
    console.error('Error posting data: ', error);
  });


// fetchData 함수 사용
fetchData()
  .then(data => {
    console.log(data); // 여기서 data는 API로부터 받은 데이터입니다.
  })
  .catch(error => {
    console.error('Error fetching data: ', error);
  });

// PUT 요청을 수행하는 함수
async function updateData(url: string, data: object): Promise<any> {
  const response = await fetch(url, {
    method: 'PUT', // 요청 메소드를 PUT으로 설정
    headers: {
      'Content-Type': 'application/json' // 요청의 Content-Type을 application/json으로 설정
    },
    body: JSON.stringify(data) // JavaScript 객체를 JSON 문자열로 변환하여 전송
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  return response.json(); // 응답 본문을 JSON으로 변환
}

// updateData 함수 사용 예
updateData('https://api.example.com/data/1', { key: 'updatedValue' })
  .then(data => {
    console.log(data); // 응답 데이터 로깅
  })
  .catch(error => {
    console.error('Error updating data: ', error);
  });

// DELETE 요청을 수행하는 함수
async function deleteData(url: string): Promise<any> {
  const response = await fetch(url, {
    method: 'DELETE', // 요청 메소드를 DELETE로 설정
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  return response.json(); // 응답 본문을 JSON으로 변환
}

// deleteData 함수 사용 예
deleteData('https://api.example.com/data/1')
  .then(data => {
    console.log(data); // 응답 데이터 로깅
  })
  .catch(error => {
    console.error('Error deleting data: ', error);
  });


export default function Test() {
  return (
    <div>
      <h1>Test</h1>
      <p>다른 페이지에 적용하기 전에 테스트를 하기 위해 만든 페이지입니다.</p>
    </div>
  );
}