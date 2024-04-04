![logo](backend/src/main/resources/static/logo.png)

#### 목차

1. [프로젝트 소개](#-프로젝트 소개)   
2. [서비스 소개](#-주요-기능)  
5. [팀 소개](#-팀-소개)  

<br>
<br>
<br>
<br>
<br>
<br>

# 🐔 프로젝트 소개

빅데이터 기반 육아 용품 중고 거래 추천 플랫폼 개발 프로젝트

<br>

### 🐤 프로젝트 기간
2024.02.19 - 2024.04.04 (7주)

> **기획 및 설계** : 2024.02.19 - 2023.03.08 (3주)
>
> **기능 구현** : 2024.03.11 - 2024.04.04 (4주)

<br>
<br>
<br>
<br>
<br>
<br>

# 🐓 서비스 소개 

**서비스 명** : 꼬꼬마켓

<br>

### 🐣 특징
    ✔ 육아용품에 특화된 중고 거래 플랫폼
    ✔ 사용자 성향을 기반으로 한 거래글 추천 기능 


<br>

### 🐥 기술 스택
|구분|기술|
|:---|:---|
|Infra|<img src="https://img.shields.io/badge/AWS EC2-FF9900?style=flat-square&logo=amazonec2&logoColor=white"><img src="https://img.shields.io/badge/NGINX-009639?style=flat-square&logo=nginx&logoColor=white"><img src="https://img.shields.io/badge/DOCKER-2496ED?style=flat-square&logo=docker&logoColor=white"><img src="https://img.shields.io/badge/UBUNTU-E95420?style=flat-square&logo=ubuntu&logoColor=white"><img src="https://img.shields.io/badge/JENKINS-D24939?style=flat-square&logo=jenkins&logoColor=white">|
|Frontend|<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white"><img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white"><img src="https://img.shields.io/badge/TYPESCRIPT-3178C6?style=flat-square&logo=typescript&logoColor=white"><img src="https://img.shields.io/badge/NEXTJS-000000?style=flat-square&logo=nextdotjs&logoColor=white">|
|Backend|<img src="https://img.shields.io/badge/JAVA-FF4000?style=flat-square&logo=openjdk&logoColor=white"><img src="https://img.shields.io/badge/SPRING-6DB33F?style=flat-square&logo=spring&logoColor=white"><img src="https://img.shields.io/badge/HIBERNATE-59666C?style=flat-square&logo=hibernate&logoColor=white"><img src="https://img.shields.io/badge/MYSQL-4479A1?style=flat-square&logo=mysql&logoColor=white"><img src="https://img.shields.io/badge/REDIS-DC382D?style=flat-square&logo=redis&logoColor=white"><img src="https://img.shields.io/badge/KAKAO API-FFCD00?style=flat-square&logo=kakao&logoColor=white"><img src="https://img.shields.io/badge/HADOOP-66CCFF?style=flat-square&logo=apachehadoop&logoColor=white">|
|Management Tool|<img src="https://img.shields.io/badge/Jira-0052CC?style=flat-square&logo=Jira&logoColor=white"><img src="https://img.shields.io/badge/GitLab-FC6D26?style=flat-square&logo=GitLab&logoColor=white"><img src="https://img.shields.io/badge/MATTERMOST-0058CC?style=flat-square&logo=mattermost&logoColor=white"><img src="https://img.shields.io/badge/NOTION-000000?style=flat-square&logo=notion&logoColor=white"><img src="https://img.shields.io/badge/FIGMA-F24E1E?style=flat-square&logo=figma&logoColor=white">
|IDE|<img src="https://img.shields.io/badge/vscode-007ACC?style=flat-square&logo=visualstudiocode&logoColor=white"><img src="https://img.shields.io/badge/IntellJ IDEA-000000?style=flat-square&logo=intellijidea&logoColor=white">|

<br>

### 🦜 설계

**인프라 아키텍쳐**
![erd](backend/src/main/resources/static/infraarch.png)

**백엔드 아키텍쳐**
![erd](backend/src/main/resources/static/bearch.png)

**ERD 다이어그램**
![erd](backend/src/main/resources/static/erd.png)

**외부 문서**
- [와이어프레임](https://www.figma.com/file/rtugKRau06UXVcB2W1WwzH/KKOMA?type=design&node-id=144%3A5737&mode=design&t=KrA32jhI4t51iAsZ-1)


<br>

### 🦆 주요 기능

|구분|설명| BE 담당 | FE 담당 |
|:---|:---|:---|:---|
|거래|- 거래 요청 시 거래를 위한 금액을 미리 출금하고 서버에서 관리하여 사기 방지<br>- 거래 완료를 위해 QR 코드를 태그하는 방식을 사용|🐰김이현<br>🦝이수현<br>🐷조형준<br>🐹유소연|🐼최지우<br>🐻황인규|
|추천|- 협업 필터링을 활용하여 사용자가 원할 것 같은 거래글을 보여줌|🐷조형준|🐼최지우|
|검색|- ...|🐹유소연|🐼최지우|
|채팅|- WebSocket을 이용한 실시간 채팅 |🐰김이현|🐼최지우|
|알림|- ...|🦝이수현|🐻황인규|
|인증|- 카카오 소셜 로그인을 이용한 사용자 인증|🐷조형준|🐼최지우|

<br>

### 🕊 실행방법

exec 폴더 내 포팅 메뉴얼 참조

<br>
<br>
<br>
<br>
<br>
<br>

# 👨‍👩‍👧‍👦 팀 소개 

**삼성 청년 SW 아카데미** 10기 서울 3반 8조 깻잎마을방범대

<table>
  <tbody>
    <tr>
        <td align="center">
            <a href="#">
            <img src="https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/rs_quotation_api/tqp1v6ni/f76dc5ed5ada4f968ca7f9d0e96d9e17.jpg" width="100px" alt="김이현"/>
                <br/>
                <b>김이현</b>
            </a>
            <br/>
            <sub>BE</sub>
        </td>
        <td align="center">
            <a href="#">
            <img src="https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/rs_quotation_api/ed3y3bml/b85629c20207415da6d71a6c80133246.jpg" width="100px" alt="조형준"/>
                <br/>
                <b>조형준</b>
            </a>
            <br/>
            <sub>BE</sub>
        </td>
        <td align="center">
            <a href="#">
            <img src="https://thumbnail15.coupangcdn.com/thumbnails/remote/292x292q65ex/image/rs_quotation_api/sfljdb3g/a0514217b99140b69bde6cb66d2ee914.jpg" width="100px"/>
                <br/>
                <b>유소연</b>
            </a>
            <br/>
            <sub>BE / Infra</sub>
        </td>
        <td align="center">
            <a href="#">
            <img src="https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/rs_quotation_api/qzykqm6l/436d92c7ede34878a8cceb0253d5e019.jpg" width="100px"/>
                <br/>
                <b>이수현</b>
            </a>
            <br/>
            <sub>BE / Infra</sub>
        </td>
        <td align="center">
            <a href="#">
            <img src="https://thumbnail13.coupangcdn.com/thumbnails/remote/292x292q65ex/image/retail/images/99212757171500-b264f16c-f06d-4156-aee1-6f6dffc3d11e.jpg" width="100px"/>
                <br/>
                <b>최지우</b>
            </a>
            <br/>
            <sub>FE</sub>
        </td>
        <td align="center">
            <a href="#">
            <img src="https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/rs_quotation_api/zc09dr10/5263a0ec068e427b8827e6e2ebfeaa6d.jpg" width="100px"/>
                <br/>
                <b>황인규</b>
            </a>
            <br/>
            <sub>FE</sub>
        </td>
    </tr>
  </tbody>
</table>
