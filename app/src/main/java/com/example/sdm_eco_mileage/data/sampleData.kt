package com.example.sdm_eco_mileage.data

data class SampleHomeRow(val image: String, val name: String)
data class SampleHomeColumn(val image: String, val name: String, val data: Int, val content: String)
data class SampleHomeDetailComment(val image: String, val name: String, val text: String)
data class SampleHomeAdd(val imageList: List<String>, val content: String, val tag: String)

val HomeTopScrollRowViewData = listOf<SampleHomeRow>(
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "서마일"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "권민준"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "김채영"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "유채린"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "박주형"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "김제니"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "김다현"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "슬기예뻐요"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "박쥐단"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "둘기야밥묵자"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "99999"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "oIng?"),
    SampleHomeRow(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = ""),
)

val HomeScrollColumnViewData = listOf<SampleHomeColumn>(
    SampleHomeColumn(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "김채영", 1,"직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"),
    SampleHomeColumn(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "주미현", 1,"코엑스에서 '대한민국 친환경대전' 열렸다고 해서 갔다왔는 데 부스 엄청 많앗네요..."),
    SampleHomeColumn(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "", 1,"직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"),
    SampleHomeColumn(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "", 1,"직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"),
    SampleHomeColumn(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "", 1,"직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"),
    SampleHomeColumn(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "", 1,"직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"),
    SampleHomeColumn(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "", 1,"직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"),
    SampleHomeColumn(image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg", name = "", 1,"직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"),
)

val HomeDetailCommentData = listOf(
    SampleHomeDetailComment(image = "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E", name = "김채영", "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"),
    SampleHomeDetailComment(image = "https://www.codingfactory.net/wp-content/uploads/abc.jpg", name = "미정님", "오랜만에 올리셨네요! 너무 예뻐요 😀😀"),
    SampleHomeDetailComment(image = "http://blog.jinbo.net/attach/615/200937431.jpg", name = "하온님", "항상 좋은 녹색생활 보고있어요! 텀블러도 써보시는건 어때요? 엄청 편해요! 😀😀"),
    SampleHomeDetailComment(image = "https://t1.daumcdn.net/cfile/blog/2455914A56ADB1E315", name = "유현님", "와 어떻게 만드는 거예요??? 방법도 올려주세요!! 😀😀"),
    SampleHomeDetailComment(image = "https://www.urbanbrush.net/web/wp-content/uploads/edd/2020/08/urbanbrush-20200821001006257893.jpg", name = "Jhon", "WA! GREEN GREEN 😀😀")
)

val HomeAddSampleData =
    SampleHomeAdd(
        imageList = listOf("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E", "https://www.codingfactory.net/wp-content/uploads/abc.jpg", "http://blog.jinbo.net/attach/615/200937431.jpg"),
        content = "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤",
        tag = "#컵홀더 #diy #예쁘다 #녹색행활"
    )
