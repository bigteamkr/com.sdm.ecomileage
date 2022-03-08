# ecomileage_android

진행사항

1. 구조
- 기본 패키지 설정 (di, data, model, repository, network, navigation, screens, components, utils) (2022.03.02 / Newon)
- DI 골격 완성 (Application - Manifest, AppModule, AndroidEntryPoint) (2022.03.02 / Newon)
- Navigation 골격 완성 (Navigation.kt && Enum Class SdmScreens) (2022.03.02 / Newon)

2. Splash, Home, HomeDetail, HomeAdd
- Splash 구현 완료 (2022.03.04 / Newon)
- Home Main_Content Card 구현 완료 - 피드백 대기 (2022.03.04 / Newon)
- Bottom Bar, TopBar 구현 완료 - 피드백 대기 (2022.03.04 / Newon)
- HomeDetail UI 80% 완료 (Search Bar 미세 조정 및 Top Bar 조정 남음) (2022.03.05 / Newon)
  
<br/>

Components List
1. TopBar, TopAppBar (2022.03.02 / Newon)  
상단 바 구조와 내용, currentScreen 값을 기준으로 TopAppBar 내용이 바뀐다. (수정 중)


2. BottomBar-FloatingActionButton (2022.03.04 / Newon)
하단 바 CutOut Shape-Floating 구현  

```Kotlin
        Surface(){
            ...
            bottomBar = {
                SecomiBottomBar(
                    navController = navController,
                    currentScreen = SecomiScreens.HomeScreen.name
                )
            },
            floatingActionButton = { SecomiMainFloatingActionButton(navController) },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center
        }

```
<img width="351" alt="스크린샷 2022-03-04 오후 11 42 51" src="https://user-images.githubusercontent.com/80164141/156783672-e0a686bb-3a88-4b9e-ae28-667e3196cfa8.png">

  
<br/>
  
2. Profile Image, Profile Name (2022.03.02 / Newon)  
프로필 이미지와 이름, 기본 사이즈 = 55.dp  
* Profile Image : image 필수
* Profile Text  : name, fontStyle 필수
* Experimental API -> Dagger - OptIn 처리
```Kotlin
ProfileImage(
    image = data.image,
    modifier = Modifier
                .size(30.dp)
)

ProfileName(
    name = data.name,
    modifier = Modifier.padding(top = 2.dp, bottom = 5.dp),
    fontStyle = MaterialTheme.typography.subtitle2
)
```
<img width="59" alt="스크린샷 2022-03-04 오후 11 44 15" src="https://user-images.githubusercontent.com/80164141/156783961-dbb4af12-37a8-4d68-b257-93b731da7c87.png"><img width="48" alt="스크린샷 2022-03-04 오후 11 44 23" src="https://user-images.githubusercontent.com/80164141/156783970-6f3755cd-bca5-40d5-af6f-26fa95ea4ab7.png">

  
필요사항  
1. 로그인 후 데이터 API (2022.03.02 / Newon)

<br/>
  
이슈사항

  
다음 목표

1. Main UI 완성 (2022.03.02 / Newon)  
2. 바텀바 및 네비게이션 (2022.03.02 / Newon || 2022.03.04 / Newon)  
3. HomeDetail, HomeAdd (2022.03.04 / Newon)
4. Card Component 화
5. API 연결
