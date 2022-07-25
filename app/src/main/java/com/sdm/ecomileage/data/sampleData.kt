package com.sdm.ecomileage.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController
import com.sdm.ecomileage.R

data class SampleHomeRow(val image: String, val name: String)
data class SampleHomeColumn(val image: String, val name: String, val data: Int, val content: String)
data class SampleHomeDetailComment(val image: String, val name: String, val text: String)
data class SampleHomeAdd(val imageList: List<String>, val content: String, val tag: String)
data class SampleEducation(val image: String, val name: String, val data: Int, val content: String)
data class SampleCard(
    val currentScreen: String,
    val contentImage: String = "",
    val profileImage: String = "",
    val profileName: String = "세코미",
    val reactionIcon: List<Int>,
    val reactionData: Int,
    val likeYN: Boolean,
    val onClickReaction: (Boolean) -> Unit,
    val reactionTint: Color,
    val commentIcon: Painter,
    val needMoreIcon: Boolean,
    val moreIcon: Painter?,
    val contentText: String = " ",
    val hashtagList: List<String>?,
    val navController: NavController,
    val destinationScreen: String,
    val feedNo: Int
)

val HomeTopScrollRowViewData = listOf<SampleHomeRow>(
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "서마일"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "권민준"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "김채영"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "유채린"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "박주형"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "김제니"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "김다현"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "슬기예뻐요"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "박쥐단"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "둘기야밥묵자"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "99999"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "oIng?"
    ),
    SampleHomeRow(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = ""
    ),
)

val HomeScrollColumnViewData = listOf<SampleHomeColumn>(
    SampleHomeColumn(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "김채영",
        1,
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    ),
    SampleHomeColumn(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "주미현",
        1,
        "코엑스에서 '대한민국 친환경대전' 열렸다고 해서 갔다왔는 데 부스 엄청 많앗네요..."
    ),
    SampleHomeColumn(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "",
        1,
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    ),
    SampleHomeColumn(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "",
        1,
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    ),
    SampleHomeColumn(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "",
        1,
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    ),
    SampleHomeColumn(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "",
        1,
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    ),
    SampleHomeColumn(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "",
        1,
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    ),
    SampleHomeColumn(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "",
        1,
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    ),
)

val HomeDetailCommentData = listOf(
    SampleHomeDetailComment(
        image = "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
        name = "김채영",
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    ),
    SampleHomeDetailComment(
        image = "https://www.codingfactory.net/wp-content/uploads/abc.jpg",
        name = "미정님",
        "오랜만에 올리셨네요! 너무 예뻐요 😀😀"
    ),
    SampleHomeDetailComment(
        image = "http://blog.jinbo.net/attach/615/200937431.jpg",
        name = "하은님",
        "항상 좋은 녹색생활 보고있어요! 텀블러도 써보시는건 어때요? 엄청 편해요! 😀😀"
    ),
    SampleHomeDetailComment(
        image = "https://t1.daumcdn.net/cfile/blog/2455914A56ADB1E315",
        name = "유현님",
        "와 어떻게 만드는 거예요??? 방법도 올려주세요!! 😀😀"
    ),
    SampleHomeDetailComment(
        image = "https://www.urbanbrush.net/web/wp-content/uploads/edd/2020/08/urbanbrush-20200821001006257893.jpg",
        name = "John",
        "WA! GREEN GREEN 😀😀"
    ),
    SampleHomeDetailComment(
        image = "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
        name = "김채영",
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    ),
    SampleHomeDetailComment(
        image = "https://www.codingfactory.net/wp-content/uploads/abc.jpg",
        name = "미정님",
        "오랜만에 올리셨네요! 너무 예뻐요 😀😀"
    ),
    SampleHomeDetailComment(
        image = "http://blog.jinbo.net/attach/615/200937431.jpg",
        name = "하은님",
        "항상 좋은 녹색생활 보고있어요! 텀블러도 써보시는건 어때요? 엄청 편해요! 😀😀"
    ),
    SampleHomeDetailComment(
        image = "https://t1.daumcdn.net/cfile/blog/2455914A56ADB1E315",
        name = "유현님",
        "와 어떻게 만드는 거예요??? 방법도 올려주세요!! 😀😀"
    ),
    SampleHomeDetailComment(
        image = "https://www.urbanbrush.net/web/wp-content/uploads/edd/2020/08/urbanbrush-20200821001006257893.jpg",
        name = "John",
        "WA! GREEN GREEN 😀😀"
    )
)

val HomeAddSampleData =
    SampleHomeAdd(
        imageList = listOf(
            "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
            "https://www.codingfactory.net/wp-content/uploads/abc.jpg",
            "http://blog.jinbo.net/attach/615/200937431.jpg"
        ),
        content = "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤",
        tag = "#컵홀더 #diy #예쁘다 #녹색행활"
    )

val EducationSampleData = listOf(
    SampleEducation(
        image = "https://cdn.pixabay.com/photo/2022/02/09/17/22/cat-7003849_1280.jpg",
        name = "김채영",
        1,
        "직접 컵홀더 만들어서 들고 다니니까 너무 예쁘고 편한 것 같다 헤헤"
    )
)

val EventCurrentSampleData = listOf(
    "https://cdn.pixabay.com/photo/2015/07/28/22/12/autumn-865157_960_720.jpg",
    "https://cdn.pixabay.com/photo/2017/06/07/10/47/elephant-2380009_960_720.jpg",
    "https://cdn.pixabay.com/photo/2015/11/16/16/28/bird-1045954_960_720.jpg",
    "https://cdn.pixabay.com/photo/2015/07/28/22/11/wheat-865152_960_720.jpg",
    "https://cdn.pixabay.com/photo/2020/07/09/03/41/duck-5385741_960_720.jpg"
)

val CardSampleImage = listOf(
    "https://pbs.twimg.com/profile_images/1374979417915547648/vKspl9Et_400x400.jpg",
    "https://us.123rf.com/450wm/yod67/yod671603/yod67160300036/55619539-흰색-배경에-고양이-얼굴-디자인의-벡터-이미지입니다-.jpg?ver=6",
    "https://ww.namu.la/s/4d74824f000826e6e0339e4f8984314ed2bc04a135255dc7b2332e2ca451705c0928eb21fa7724cb072f5c182dfe5bd6bcbce1602aa55656f706b4d5555ac7e619f8a092f5ebbb74d020f6472b6c06be"
)

val ChallengeList = listOf(
    R.drawable.image_daily,
    R.drawable.image_empty_dish,
    R.drawable.image_public_transport,
    R.drawable.image_thermos,
    R.drawable.image_label_detach,
    R.drawable.image_basket,
    R.drawable.image_pull_a_plug,
    R.drawable.image_empty_bottle,
    R.drawable.image_back,
    R.drawable.image_upcycling
)