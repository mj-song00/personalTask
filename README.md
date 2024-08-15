

## API 
<img width="1285" alt="스크린샷 2024-08-14 오전 9 55 15" src="https://github.com/user-attachments/assets/faa3da89-885f-43ff-bbfe-54c26b85d8cf">


## ERD

<img width="716" alt="스크린샷 2024-08-15 오후 7 50 46" src="https://github.com/user-attachments/assets/e63ace4f-e65d-43ed-a575-d9cea9722662">

1-1. 수정의 경우 PathVariable로 수정할 post의 id값을 받았습니다. @RequestDto, @RequestBody로 변경할 manager, contents값을 받았습니다.<br>
1-2. 삭제의 경우 PathVariable로 삭제할 post의 id값을 받았습니다. 수정시 필요한 password는 @RequestBody, @RequestDto로 받았습니다. <br>
2. rest의 원칙을 지키려 노력했으나 각 API 응답값에 statusCode가 포함되지 않아 Restful하지 않습니다. <br>
3. 프론트 요청을 받으면 각 api로 분리해주는 controller와 핵심 로직들이 있는 service, 프론트의 Request값과 Response값을 Dto로 만들어 관심사를 분리하였습니다.
   
