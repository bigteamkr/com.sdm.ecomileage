# ecomileage_android

### 핵심 기술
Kotlin, Jetpack compose (100%), AAC - MVVM 패턴 • Navigation, Dagger-Hilt, Retrofit2, ExoPlayer, proto dataStroe  

### Dependency
- Jetpack compose v.1.2.0-alpha05  
- Kotlin v.1.6.10  
- Dagger-Hilt v.2.4.1  
- Coroutine v.1.6.0  
- Coil v.1.4.0  
- Retrofit2 v.2.9.0  
- OkHttp v.5.0.0-alpha2    
- ExoPlayer v.2.17.1  
- AppCompanist(systemUiController, viewPager, Swipe Refresh) v.0.24.4-alhpa  
- Image Cropper v.4.0.0  
- Kakao Login v.2.9.0  
- Naver Login v.5.1.0  
- Facebook Login v.\[8,9  
- GoogleLogin v.30.0.0  
- protobuf v.1.0.0  
- paging v.3.1.1  
- *자세한 내용 build.gradle(Module) 참조  

### 구조
![스크린샷 2022-06-02 오전 10 57 07](https://user-images.githubusercontent.com/80164141/171531917-e655ad3a-ef9f-4072-82ff-19020c33ac5c.png)

1. Components  
: UI 에서 자주 사용되는 Custom widgets 들을 관리하는 폴더, Profile Image, Profile Text, Card Structure, AppBar 등이 있음.  
- SecomiTopAppBar : UI 내 대표 상단 탑바, 전체 크기 사용을 위해 TopAppBar.content 에 모든 영역을 잡고 자체적으로 왼쪽, 가운데, 오른쪽을 나눔.  
  
- MainFeedCardStructure : Home 화면에서 보이는 카드 피드의 골격, 해당 Composable 함수로 Home, Education, Search 의 카드들을 모두 관리함  
MainFeedCardStructure 의 기능은 해당 카드가 **신고** 되었는지 확인하고, 그에 맞는 UI 를 제공함.  
  
- MainCardFeed : MainFeedCardStructure 에서 신고가 되지 않은 카드라면 보여주는 내용.  
education, like, follow, 등이 카드에 따라 유무가 달리는 내용은 기본값이 null 로 되어있으니 확인필요.  
education 과 다른 카드들의 차이가 커서 `isOnEducation: Boolean` 의 값으로 education 과 그 외 카드들을 나누는 파라미터가 있음.  
내용은 3단 구조로 이미지, 카드 정보(사용자, 좋아요, 마일리지 등), 카드 본문함수로 나뉘어져 있음.  
   
- CardWriter : 로직 특이사항 - 좋아요 기능은 웹소켓이 아닌, UI 상 눈속임으로 작성되었음.   
즉 좋아요를 누르더라도 페이지는 리로드되지 않으며 followInfo 가 서버로부터 최초의 좋아요 상태를 받은 후에 유저의 행동에 따라 local 값을 가지며 UI 에서 반응함.    
이후 리로드 시 마지막 좋아요 서버 결과값이 반영됨.  
해당 로직은 댓글창 (homeDetail) 및 신고, 팔로우 기능에서도 동일하게 적용됨.  
   
- MileageSwipeButton : 기존의 UI 가 아닌 canvas 로 자체적으로 그린 UI  
  
<br/>
<br/>
  
2. data 
: proto dataStore 설정 파일, Retrofit 결과값을 받는 DataClass (DataOrException), 샘플 데이터가 있음.  
- AppSettings : proto datastore 에서 저장하고 싶은 내용  
  
- AppSettingsSerializer : proto datastore 에서 serializer 화 하여 쓰기 및 읽기 적용  
실제 적용 함수들은 util/utils.kt 에 set... 으로 사용 중  
  
- DataOrException : ```data class DataOrException <T, Boolean, E: Exception>()```  
를 통해서 데이터/로딩/에러 를 받음. 모든 Retrofit2 에서 받는 결과값으 DataOrException 을 사용하고 있음.  
  
<br/>
<br/>  
  
3. di  
: Dagger-hilt DI 파일, AppModule 을 설정하고 각 API 에 따른 Retrofit 을 실제 빌드 및 injection 하는 곳.  
새로운 API 를 적용한다면 이곳에 등록해서 사용하고 있음.  
  
<br/>  
<br/>  
  
4. model  
: 각 API 결과값을 받기 위한 Data Class 들  
model 은 (대분류) - API 이름 - request/response 로 패키지를 나누어서 관리하고 있음.
  
<br/>  
<br/>  
  
5. navigation  
: AAC - Jetpack Navigation 적용 내용  
모든 페이지는 이곳에서 이동 및 파리미터를 관리함.  
SdmNavigation.kt 에 처음 선언한 navController 는 모든 페이지에 전달하여, 해당 navController 로 페이지간 이동을 하고 있음.  
systemUIController 역시 이곳에 선언되어 있으며, 모든 페이지에 전달하여 사용 중임.  
  
<br/>  
<br/>  
  
6. network
: 페이지 혹은 목적별 API, model 에서 선언한 data class 를 통해 이곳에서 body 와 리턴값을 설정함.  
실제 서버에서의 method 는 모두 `POST` 로 설정되어 있지만 가독성을 위해 자체적으로 GET, POST 등을 붙인 함수명을 사용하고 있으니 주의  
API 이름 역시 최대한 서버의 이름과 따라가지만, 가독성을 위해 다른 부분들이 존재  
  
<br/>  
<br/>  
  
7. repository  
: MVVM 구조에 따른 repository 를 구조 및 paging 과 관련된 class 를 이곳에서 관리하고 있음.  
  
<br/>  
<br/>  
  
8. screen  
: 실제 UI 페이지  
페이지 별로 관리되고 있으며 viewModel 역시 screen/(페이지명) 에서 함께 관리되고 있음.  
코드의 재사용성과 가독성을 위해 자주 사용되는 UI 는 components 에서 관리하고 있음.  
  
  screen 의 구조는  
  - systemUIController 를 통해 status 색을 바꿈.
  - 필요한 데이터를 호출하고, 상태에 따라 필요한 UI / 로직을 설정함
  - 데이터가 정상적으로 호출되며 Scaffold 로 데이터를 넣어주며 이동함.
 
  
<br/>  
<br/>  
  
9. ui.theme  
: Color 에서 모든 color 를 설정하고 있음.  
Type 에서 Pretendard font 를 적용하고 있음 (전체 Text font 는 pretendard 로 적용 중)  
  
<br/>  
<br/>  
  
10. util
: UI 와 관련없지만 자주 사용되는 비즈니스 로직들 및 몇몇 데이터들이 있음.  
`backWaitTime` 변수는 BackHandler 에서 두번 눌러서 나가기 할 때 사용되는 변수 (Home 화면 참고)  

11. MainActivity  
: 앱 호출 및 소셜로그인에 필요한 sdk 호출  
  
<br/>  
<br/>  
  
12. SdmEcoMileageApplication  
: DI 에서 가장 상단, Application 을 상속하며 context 가 필요하면 이를 제공하는 class
  
<br/>  
  
### UUID 와 proto dataStore
UUID 는 앱 내 최초 실행 시 (proto dataStore 에 UUID 가 저장되어있지 않을 때) Splash 페이지에서 util/setUUID 를 통해 설정함.  
이후에는 proto dataStore 에서 호출한 값을 이용함.  
모든 proto dataStore 의 값들은 util/current... 변수로 관리되고 있음.
  
<br/>  
<br/>
  
### 대부분의 이미지 파일들은 svg 로 적용하였음.  
  
<br/>  
<br/>
  
### 소셜로그인과 자동로그인의 로직
**소셜로그인**  
: 소셜로그인 로직이 서버에 토큰을 전달하는 방식이 아닌, 소셜로그인 정보를 바탕으로 자체 API 를 사용하도록 설정되어 있음.  
즉 소셜로그인 시도 -> 성공 시 소셜로그인으로부터 자체 ID값, 이메일, 전화번호등을 수집  
-> getSocialLogin API 로 소셜로그인 시도 -> 성공 시 Home 화면, 실패 시 Register 페이지로 이동  
  
<br/>  
  
**자동로그인**  
: 로그인 시 AccessToken 과 RefreshToken 을 전달받는데, AccessToken 은 만료되고 RefreshToken 이 유효할 때 사용

AccessToken 만료 시 AppInit 에 RefreshToken 넣고 호출 ->    
- RefreshToken 이 유효하면 
   새로운 AccessToken 과 RefreshToken 받기, 

- RefreshToken 만료 시 로그인 창으로 이동

해당 로직은 LoginScreen.kt 의 AutoLoginLogic 임.  
  
<br/>  
<br/>
  
### 이미지 업로드 및 이미지 렌더링
이미지 업로드는 Base64 인코딩을 적용하고 있으며 JPEG (투명X), 원본에서 30% 화질을 BitMap 에서 String 으로 바꿔서 전송 중이며  
이미지를 불러올 때는 서버에서 URL 형태로 전달 중임.  
기본아바타는 drawble/ic_default_profile 로 있음 (SVG)  
  
<br/>  
<br/>
  
### 파악한 버그 목록
1. 로그인 간혈적으로 튕김 현상 (조건이 불분명하며, 1달동안 보이지 않긴 함.)  
2. 댓글 작성 시 화면 밀림  
3. 삼성 키보드 이슈 - 한글을 치다가 중간에 가서 작성하면 자모음 분리현상이 일어남  
4. myPage 좋아요 이슈  
5. myPage 다이얼로그에서 좋아요 누르면 반영 안됨  
6. 업로드 할 때, 사진을 올렸다 지워도 인디케이터 숫자는 사진의 최대치로  
  
<br/>  
<br/>
  
  
  
