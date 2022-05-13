package com.sdm.ecomileage.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.sdm.ecomileage.SdmEcoMileageApplication
import com.sdm.ecomileage.data.AppSettings
import com.sdm.ecomileage.data.AppSettingsSerializer
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.math.roundToInt

var accessTokenUtil: String = ""
var loginedUserIdUtil: String = ""
var lastLoginedUserIdUtil: String = ""
var currentUUIDUtil = ""
var isSaveIdUtil = false
var isAutoLoginUtil = false
var isThisFirstTime = true
var refreshTokenUtil: String = ""
var backWaitTime = 0L

val kakaoNativeAppKey = "fb7903a5b79c6fb3d7172024332e682d"
val naverClientID = "yNnPfOiZhyJxp49H7LKN"
val naverClientSecret = "aZha1lkIGS"

val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)
suspend fun setIsAutoLogin(isAutoLogin: Boolean) {
    SdmEcoMileageApplication.ApplicationContext().dataStore.updateData {
        it.copy(isAutoLogin = isAutoLogin)
    }
}

suspend fun setIsSaveId(isSaveId: Boolean, saveId: String) {
    SdmEcoMileageApplication.ApplicationContext().dataStore.updateData {
        it.copy(lastLoginId = saveId, isSaveId = isSaveId)
    }
}

suspend fun setRefreshToken(refreshToken: String) {
    SdmEcoMileageApplication.ApplicationContext().dataStore.updateData {
        it.copy(refreshToken = refreshToken)
    }.let {
        refreshTokenUtil = it.refreshToken
    }

}

suspend fun setUUID() {
    SdmEcoMileageApplication.ApplicationContext().dataStore.updateData {
        it.copy(uuid = UUID.randomUUID().toString())
    }
}

suspend fun setIsThisFirstTime() {
    SdmEcoMileageApplication.ApplicationContext().dataStore.updateData {
        it.copy(isThisFirstInit = false)
    }
}


@Composable
fun AppSettings() {
    val appSettings =
        SdmEcoMileageApplication.ApplicationContext().dataStore.data.collectAsState(initial = AppSettings()).value

    currentUUIDUtil = appSettings.uuid
    isSaveIdUtil = appSettings.isSaveId
    isAutoLoginUtil = appSettings.isAutoLogin
    lastLoginedUserIdUtil = appSettings.lastLoginId
    isThisFirstTime = appSettings.isThisFirstInit
    refreshTokenUtil = appSettings.refreshToken
}


@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


fun bitmapToString(bitmap: Bitmap): String {
    val currentBitmap = bitmap.copy(Bitmap.Config.RGB_565, false)

    val byteArrayOutputStream = ByteArrayOutputStream()
    currentBitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)

    val byteArray = byteArrayOutputStream.toByteArray()

    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}

fun stringToBitmap(encodedString: String): Bitmap {

    val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)

    return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
}

fun pxToDp(px: Float): Int {
    val displayMetrics = SdmEcoMileageApplication.ApplicationContext().resources.displayMetrics
    return (px / displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT).roundToInt()
}

val termsFirst = "이용약관\n" +
        "\n" +
        "제1조 (목적)\n" +
        "본 약관은 서울특별시 서대문구의 서대문구 기후환경 마일리지 서비스을 통해 제공하는 환경 정보 서비스(이하 \"서비스)와 관련하여, 이를 이용하는 가입자 (이하 \"회원\" 또는 \"개인회원\")의 이용조건 및 제반 절차, 기타 필요한 사항을 규정함을 목적으로 한다.\n" +
        " \n" +
        "\n" +
        "제2조 (용어의 정의)\n" +
        "이 약관에서 사용하는 용어의 정의는 아래와 같다.\n" +
        "① \"서비스\"라 함은 앱 서비스를 통해 개인이 등록하는 자료를 DB화하여 각각의 목적에 맞게 분류 가공, 집계하여 정보를 제공하는 서비스와 해당 사이트에서 제공하는 모든 부대 서비스를 말한다.\n" +
        "② \"개인회원\"이라 함은 서비스를 이용하기 위하여 동 약관에 동의하고 서비스 이용계약을 체결하여 이용자 ID를 부여 받은 개인을 말한다.\n" +
        "③ \"이용자ID\"라 함은 회원의 식별과 서비스 이용을 위해 회원이 직접 입력, 설정하여 인큐베이터 플랫폼에 등록한 이메일, 휴대전화번호, 또는 영문과 숫자의 조합을 말한다.\n" +
        "⑤ \"비밀번호\"라 함은 서비스를 이용하려는 사람이 이용자ID를 부여받은 자와 동일인임을 확인하고 회원의 권익을 보호하기 위하여 회원이 선정한 문자와 숫자의 조합을 말한다.\n" +
        " \n" +
        "\n" +
        "제3조 (약관의 효력과 변경)\n" +
        "① 이 약관은 서비스 화면에 게시하거나 기타의 방법으로 공지함으로써 이용자에게 공지하고 이에 동의한 이용자가 회원으로 가입함으로서 효력이 발생한다.\n" +
        "② 약관의 규제 등에 관한 법률, 전기통신기본법, 전기통신사업법, 정보통신망 이용 촉진 등에 관한 법률 등 관련법을 위배하지 않는 범위에서 이 약관을 개정할 수 있다.\n" +
        "③ 약관을 개정할 경우에는 적용일자 및 개정사유를 상당한 기간 동안 해당 사이트에 공시하여야 한다.\n" +
        "④ 회원은 변경된 약관에 대해 거부할 권리가 있다. 회원은 변경된 약관이 공지된 지 20일 이내에 거부의사를 표명할 수 있다. 회원이 변경된 약관을 거부하는 경우 본 서비스 제공자인 서대문구는 15일의 기간을 정하여 회원에게 사전 통지 후 당해 계약을 해지할 수 있다. 만약, 회원이 거부 의사를 표시하지 않고 서비스를 계속 이용하는 경우에는 변경된 약관에 동의한 것으로 간주한다.\n" +
        "⑤ 회원은 정기적으로 사이트를 방문하여 약관의 변경사항을 확인하여야 한다. 변경된 약관에 대한 정보를 알지 못해 발생하는 회원의 피해는 책임지지 않는다.\n" +
        " \n" +
        "\n" +
        "제4조 (약관 이외의 준칙)\n" +
        "이 약관에 명시되지 않은 사항에 대해서는 약관의 규제 등에 관한 법률, 전기통신기본법, 전기통신사업법, 정보통신망이용촉진 등에 관한 법률 등의 관계법령에 따른다.\n" +
        " \n" +
        "\n" +
        "제5조 (이용계약의 성립)\n" +
        "① 이용계약은 서비스를 이용하고자 하는 자의 본 약관과 개인정보보호정책의 내용에 대한 동의 및 이용신청(회원가입신청)에 대하여  기후환경 마일리지 서비스가 승낙함으로써 성립한다.\n" +
        "② 이용계약에 대한 동의는 이용신청 당시 신청서에 ‘동의함’을 누름으로써 의사표시를 한다.\n" +
        "③ 네이버, 카카오, 페이스북, 구글 등 외부 서비스와의 연동을 통해 이용계약을 신청할 경우, 본 약관과  기후환경 마일리지 서비스의 개인정보처리방침에 동의를 하고, 서비스 제공을 위해  기후환경 마일리지 서비스가 \"회원\"의 외부 서비스 계정 정보에 접근하고 \"회원\"이 활용에 \"동의\" 또는 \"확인\" 버튼을 누르면  기후환경 마일리지 서비스가 사이트상의 안내 및 전자메일로 \"회원\"에게 통지함으로써 이용계약이 성립된다.\n" +
        " \n" +
        "\n" +
        "제6조 (서비스 이용신청)\n" +
        "① 회원으로 가입하여 본 서비스를 이용하고자 하는 자는  기후환경 마일리지 서비스에서 요청하는 제반정보(이름, 생년월일, 연락처 등)를 제공하여야 한다.\n" +
        "② 모든 회원은 반드시 회원 본인의 이름과 생년월일을 제공하여야만 서비스를 이용할 수 있으며, 실명으로 등록하지 않은 사용자는 일체의 권리를 주장할 수 없다.\n" +
        "③ 타인의 명의를 도용하여 이용신청을 한 회원의 모든 ID는 삭제되며, 관계법령에 따라 처벌을 받을 수 있다.\n" +
        "④ 기업/기관회원 가입하기 위해서는 개인회원 가입 이후,  기후환경 마일리지 서비스에서 요청하는 제반정보(기업/기관명,사업자등록번호, 주소, 정보수신 이메일대표자 등)를 별도 제공하여야 한다.\n" +
        "⑤ 개인회원 가입후 기업/기관회원 가입신청한 경우, 관리자가 제반요건을 검토하여 승인할 경우 기업/기관회원으로서 서비스를 이용할 수 있다.\n" +
        "⑤ 기업/기관회원은 반드시 기업/기관명과 사업자등록번호를 제공하여야만 서비스를 이용할 수 있으며, 기업명과 사업자등록번호가 허위로 등록된 경우에 사용자는 일체의 권리를 주장할 수 없다.\n" +
        " \n" +
        "제7조 (이용신청의 승낙과 제한)\n" +
        "①  기후환경 마일리지 서비스는 전조의 규정에 의한 이용신청 자에 대하여 업무수행상 또는 기술상 지장이 없는 경우에는 원칙적으로 접수순서에 따라 서비스 이용을 승낙한다.\n" +
        "②  기후환경 마일리지 서비스는 아래사항에 해당하는 경우에 대해서는 이용신청을 승낙하지 아니한다.\n" +
        "1. 실제 기업/기관명과 사업자등록번호가 아니거나 타사의 정보를 이용하여 신청한 경우\n" +
        "2. 이용계약 신청서의 내용을 허위로 기재한 경우\n" +
        "3. 사회의 안녕질서 또는 미풍양속을 저해할 목적으로 신청한 경우\n" +
        "4. 기타  기후환경 마일리지 서비스 소정의 이용신청 요건을 충족하지 못하는 경우\n" +
        "③  기후환경 마일리지 서비스는 아래사항에 해당하는 경우에는 그 신청에 대하여 승낙제한 사유가 해소될 때까지 승낙을 유보할 수 있다.\n" +
        "1.  기후환경 마일리지 서비스가 설비의 여유가 없는 경우\n" +
        "2.  기후환경 마일리지 서비스의 기술상 지장이 있는 경우\n" +
        "3. 기타  기후환경 마일리지 서비스의 귀책사유로 이용승낙이 곤란한 경우\n" +
        " \n" +
        "제8조 (서비스 내용)\n" +
        "①  기후환경 마일리지 서비스는 제2조 1항의 서비스를 제공할 수 있으며 그 내용은 다음 각 호와 같다.\n" +
        "1. 환경 동영상 교육\n" +
        "2. 포인트 마일리지 서비스\n" +
        "3. 환경 관련 이벤트 서비스\n" +
        "②  기후환경 마일리지 서비스는 필요한 경우 서비스의 내용을 추가 또는 변경할 수 있다. 단, 이 경우  기후환경 마일리지 서비스는 추가 또는 변경 내용을 회원에게 공지해야 한다.\n" +
        " \n" +
        "제9조 (회원정보, 포트폴리오, 창업계획서 노출)\n" +
        "① 개인회원의 포스팅 데이터(이하 ‘사진’)는 개인이 회원가입 또는 포스팅 글쓰기 작성 및 수정 시 희망한 형태로 노출한다.\n" +
        "②  기후환경 마일리지 서비스는 개인회원이 서비스 내용을 공개/비공개 지정, 연락처 공개/비공개를 자유롭게 선택할 수 있도록 하여야 한다.\n" +
        "③  기후환경 마일리지 서비스는 개인회원이 서비스 내용 공개를 희망했을 경우 타 회원이 개인회원의 포스팅 사진 및 태깅 정보를 열람하게 할 수 있다. 다만, 연락처 각 항목이 비공개로 지정된 경우 해당 항목별 연락처를 노출할 수 없다.\n" +
        " \n" +
        "제10조 (서비스 개시)\n" +
        "① 서비스는  기후환경 마일리지 서비스가 제7조에 따라서 이용신청을 승낙한 때로부터 즉시 개시된다. 다만,  기후환경 마일리지 서비스의 업무상 또는 기술상의 장애로 인하여 서비스를 즉시 개시하지 못하는 경우  기후환경 마일리지 서비스는 회원에게 이를 지체 없이 통지한다.\n" +
        "②  기후환경 마일리지 서비스는 제휴 관계를 체결한 여타 인터넷 웹 사이트 또는 신문, 잡지 등의 오프라인 매체를 통해 사이트에 등록한 회원의 정보가 열람될 수 있도록 서비스를 제공할 수 있다.\n" +
        "③  기후환경 마일리지 서비스는 제휴를 통해 타 사이트 및 매체에 등록될 수 있음을 고지해야 하며, 제휴 사이트 전체 목록을 사이트 내에서 상시 열람할 수 있도록 해야 한다.\n" +
        " \n" +
        "제11조 (서비스 이용시간)\n" +
        "①  기후환경 마일리지 서비스는 특별한 사유가 없는 한 연중무휴, 1일 24시간 서비스를 제공한다. 다만,  기후환경 마일리지 서비스는 서비스의 종류나 성질에 따라 제공하는 서비스 중 일부에 대해서는 별도로 이용시간을 정할 수 있으며, 이 경우  기후환경 마일리지 서비스는 그 이용시간을 사전에 회원에게 공지 또는 통지하여야 한다.\n" +
        "②  기후환경 마일리지 서비스는 자료의 가공과 갱신을 위한 시스템 작업시간, 장애해결을 위한 보수작업 시간, 정기 PM작업,시스템 교체작업, 회선 장애 등이 발생한 경우 일시적으로 서비스를 중단할 수 있으며 계획된 작업의 경우 공지란에 서비스 중단 시간과 작업 내용을 알려야 한다.\n" +
        " \n" +
        "제12조 (서비스제공의 중지)\n" +
        "①  기후환경 마일리지 서비스는 아래에 해당하는 경우 서비스의 제공을 중지할 수 있다.\n" +
        "1. 설비의 보수 등  기후환경 마일리지 서비스의 필요에 의해 사전에 회원들에게 통지한 경우\n" +
        "2. 기관통신사업자가 전기통신서비스 제공을 중지하는 경우\n" +
        "3. 기타 불가항력적인 사유에 의해 서비스 제공이 객관적으로 불가능한 경우\n" +
        " \n" +
        "제13조 (이용계약의 해지 및 서비스 이용제한)\n" +
        "① 회원이 서비스 이용계약을 해지하고자 하는 때에는 사이트에 본인이 직접 해지신청을 하여야 한다.\n" +
        "②  기후환경 마일리지 서비스 관리자는 이용자가 다음 각 호에 해당하는 행위를 한 경우 사전 통지 없이 이용계약을 해지하거나 서비스의 전부 또는 일부의 제공하지 않을 수 있다.\n" +
        "1. 타인의 아이디(ID) 및 비밀번호(Password)를 도용한 경우\n" +
        "2. 다른 사람의 서비스 이용을 방해하거나 그 정보를 도용하는 등의 행위를 하였을 때\n" +
        "3. 수신자의 의사에 반하는 광고성 정보, 전자우편을 전송하는 경우\n" +
        "4. 사이트의 승인을 받지 아니한 광고, 판촉물, 정크메일, 스팸, 행운의 편지, 피라미드 조직 기타 다른 형태의 권유를 게시, 게재, 전자메일 또는 기타의 방법으로 전송하는 경우\n" +
        "5. 서비스를 이용하여 얻은 정보를 사이트의 동의 없이 상업적으로 이용하는 경우\n" +
        "6. 정보통신윤리위원회로부터의 이용제한 요구 대상인 경우\n" +
        "7. 다른 이용자의 개인정보를 수집 또는 저장하는 행위\n" +
        "8. 기타 사이트를 이용하여 법령과 본 약관이 금지하는 행위를 하는 경우\n" +
        "\n" +
        "제14조 (정보의 제공 및 광고의 게재)\n" +
        "①  기후환경 마일리지 서비스는 회원에게 서비스 이용에 필요가 있다고 인정되거나 서비스 개선 및 회원대상의 서비스 소개 등의 목적으로 하는 각종 정보에 대해서 전자우편이나 서신우편, SMS를 이용한 방법으로 제공할 수 있다.\n" +
        "②  기후환경 마일리지 서비스는 제공하는 서비스와 관련되는 정보 또는 광고를 서비스 화면, 홈페이지 등에 게재할 수 있으며, 회원들에게 메일이나 SMS를 통해 알릴 수 있다.\n" +
        "③  기후환경 마일리지 서비스는 서비스상에 게재되어 있거나 본 서비스를 통한 광고주의 판촉활동에 회원이 참여하거나 교신 또는 거래를 함으로써 발생하는 모든 손실과 손해에 대해 책임을 지지 않는다.\n" +
        "④ 본 서비스의 회원은 서비스 이용 시 노출되는 광고게재에 대해 동의 하는 것으로 간주한다.\n" +
        "\n" +
        "제15조 (자료내용의 책임과  기후환경 마일리지 서비스의 정보수정 / 삭제 권한))\n" +
        "① 자료내용은 회원이 등록한 개인정보 및 포트폴리오와 사이트에 게시한 게시물을 말한다.\n" +
        "② 회원은 자료 내용 및 게시물을 사실에 근거하여 성실하게 작성해야 하며, 만일 자료의 내용이 사실이 아니거나 부정확하게 작성되어 발생하는 모든 책임은 회원에게 있다.\n" +
        "③ 모든 자료내용의 관리와 작성은 회원 본인이 하는 것이 원칙이나 사정상 위탁 또는 대행관리를 하더라도 자료내용의 책임은 회원에게 있으며 회원은 주기적으로 자신의 자료를 확인하여 항상 정확하게 관리가 되도록 노력해야 한다.\n" +
        "④  기후환경 마일리지 서비스는 회원이 등록한 자료 내용에 오자, 탈자 또는 사회적 통념에 어긋나는 문구가 있을 경우 사전 통지나 동의 없이 이를 언제든지 수정 또는 삭제 할 수 있다.\n" +
        "\n" +
        "제16조 (게시물 또는 내용물의 삭제)\n" +
        " 기후환경 마일리지 서비스는 서비스의 게시물 또는 내용물이 제20조의 규정에 위반되거나  기후환경 마일리지 서비스 소정의 게시기간을 초과하는 경우 사전 통지나 동의 없이 이를 삭제할 수 있다.\n" +
        "\n" +
        "제17조 (게시물의 저작권 등)\n" +
        "① 게시물에 대한 저작권을 포함한 모든 권리 및 책임은 이를 게시한 회원에게 있다. 회원이 서비스내에 게시한 게시물에 대한 권리와 책임은 각 회원에게 있으며, 회원간 게시물을 공유할 수 있도록 전송하는 경우에도 동일하다.\n" +
        "② 회원은 서비스 이용과정에서 취득한 게시물을 개설자 또는  기후환경 마일리지 서비스의 사전 승낙없이 복제, 배포, 전시,전송, 출판, 방송, 수정, 개조 또는 개변 등의 방법으로 이용하거나 제3자가 이용할 수 있도록 복제, 배포,전송 등을 하여서는 아니 된다.\n" +
        "③ 게시물은 개설자의 책임 하에 게시된 것으로서  기후환경 마일리지 서비스는 게시물의 신뢰도, 정확성, 신빙성 등에 관한 보증, 담보 등 일체의 책임을 부담하지 않는다.\n" +
        "④ 기후환경 마일리지 서비스는 관련법에서 특별히 정한 경우를 제외하고는 게시물에 대한 정기적인 확인, 검토 등 모니터링 의무를 부담하지 않는다.\n" +
        "⑤ 기후환경 마일리지 서비스는 회원이 서비스 내에 게시한 게시물이 타인의 저작권 등을 침해하는 경우 이에 대한 민·형사상의 어떠한 책임도 부담하지 않는다. 만일, 회원이 타인의 저작권 등을 침해하였음을 이유로  기후환경 마일리지 서비스가 제3자로부터 손해배상청구, 고소 또는 고발 등의 당사자가 된 경우 회원은  기후환경 마일리지 서비스를 면책 시켜야 하며,  기후환경 마일리지 서비스가 면책되지 못한 경우 회원은 그로 인해  기후환경 마일리지 서비스가 입은 손해를 배상하여야 한다.\n" +
        "⑥ 기후환경 마일리지 서비스는 게시물이 저작권 등을 침해하는 게시물이라는 유효한 주장을 접수 받은 경우에는  기후환경 마일리지 서비스가 별도로 마련하여 운영하고 있는 「저작권정책」에 따라 처리한다.\n" +
        "⑦ 기후환경 마일리지 서비스가 작성한 저작물에 대한 저작권은  기후환경 마일리지 서비스에 귀속한다.\n" +
        "\n" +
        "제18조 (자료내용의 활용 및 포트폴리오, 창업계획서 정보)\n" +
        "① 개인회원이 입력한 정보는 환경관련 동향의 통계 자료로 활용될 수 있으며 그 자료는 매체를 통해 언론에 배포될 수 있다.\n" +
        "② 기후환경 마일리지 서비스의 등록된 자료는 시스템을 통해 해당 기업에게 제공된 개인회원의 정보는 해당 기관의 환경 및 투자자료 등으로 활용될 수 있으며, 이에 대한 관리 권한은 해당 기업의 정책에 의한다.\n" +
        "\n" +
        "제19조 ( 기후환경 마일리지 서비스의 의무)\n" +
        "① 기후환경 마일리지 서비스는 본 약관에서 정한 바에 따라 계속적, 안정적으로 서비스를 제공할 수 있도록 최선의 노력을 다해야 한다.\n" +
        "② 기후환경 마일리지 서비스는 서비스와 관련한 회원의 불만사항이 접수되는 경우 이를 즉시 처리하여야 하며, 즉시 처리가 곤란한 경우에는 그 사유와 처리일정을 서비스 화면 또는 기타 방법을 통해 동 회원에게 통지하여야 한다.\n" +
        "③ 천재지변 등 예측하지 못한 일이 발생하거나 시스템의 장애가 발생하여 서비스가 중단될 경우 이에 대한 손해에 대해서는  기후환경 마일리지 서비스가 책임을 지지 않는다. 다만 자료의 복구나 정상적인 서비스 지원이 되도록 최선 을 다할 의무를 진다.\n" +
        "④ 회원의 자료를 본 서비스 이외의 목적으로 제3자에게 제공하거나 열람시킬 경우 반드시 회원의 동의를 얻어야 한다.\n" +
        "\n" +
        "제20조 (회원의 의무)\n" +
        "① 회원은 관계법령과 본 약관의 규정 및 기타  기후환경 마일리지 서비스가 통지하는 사항을 준수하여야 하며, 기타  기후환경 마일리지 서비스의 업무에 방해되는 행위를 해서는 안 된다.\n" +
        "② 회원은 서비스를 이용하여 얻은 정보를  기후환경 마일리지 서비스의 사전 동의 없이 복사, 복제, 번역, 출판, 방송 기타의 방법으로 사용하거나 이를 타인에게 제공할 수 없다.\n" +
        "③ 회원은 본 서비스를 건전한 교육, 취업, 창업 이외의 목적으로 사용해서는 안 되며 이용 중 다음 각 호의 행위를 해서는 안 된다.\n" +
        "1. 다른 회원의 아이디를 부정 사용하는 행위\n" +
        "2. 범죄행위를 목적으로 하거나 기타 범죄행위와 관련된 행위\n" +
        "3. 타인의 명예를 훼손하거나 모욕하는 행위\n" +
        "4. 타인의 지적재산권 등의 권리를 침해하는 행위\n" +
        "5. 해킹행위 또는 바이러스의 유포 행위\n" +
        "6. 타인의 의사에 반하여 광고성 정보 등 일정한 내용을 계속적으로 전송하는 행위\n" +
        "7. 서비스의 안정적인 운영에 지장을 주거나 줄 우려가 있다고 판단되는 행위\n" +
        "8. 사이트의 정보 및 서비스를 이용한 영리 행위\n" +
        "9. 그밖에 선량한 풍속, 기타 사회질서를 해하거나 관계법령에 위반하는 행위\n" +
        "\n" +
        "제21조 (회원의 가입해지 / 서비스 중지/제한 / 자료삭제)\n" +
        "① 개인회원이 가입 해지를 하고자 할 때는  기후환경 마일리지 서비스 또는 \"회원 탈퇴\" 메뉴를 이용해 해지 신청을 해야하고,  기후환경 마일리지 서비스는 즉시 가입해지(회원탈퇴)에 필요한 조치를 취한다.\n" +
        "② 다음의 사항에 해당하는 경우  기후환경 마일리지 서비스는 사전 동의 없이 가입해지나 서비스 중지, 자료 삭제 조치를 취할 수 있다.\n" +
        "1. 회원의 의무를 성실하게 이행하지 않았을 때\n" +
        "2. 본 서비스 목적에 맞지 않는 분야에 정보를 활용하여 사회적 물의가 발생한 때\n" +
        "3. 회원이 등록한 정보의 내용이 사실과 다르거나 조작되었을 때\n" +
        "4. 동일 회원ID로 마감일이 지나지 않은 사실상의 동일 내용의 게시물을 중복 등록했을 때\n" +
        "5. 지사, 지점, 영업소 등의 경우 구체적인 지사, 지점, 영업소 명칭을 사용하지 않고 기관명을 등록 했거나 지사, 지점, 영업소의 사업자등록번호가 별도 있음에도 불구하고 본사의 사업자등록번호로 기업/기관회원 가입을 했을 때\n" +
        "6. 사업자등록증에 나타난 정보와 기업/기관회원 가입 시의 정보가 일치하지 않을 때\n" +
        "7. 재택 쇼핑몰 운영(홍보)에 관한 내용을 등록했을 때\n" +
        "8. 기타 본 서비스의 명예를 훼손하였을 때\n" +
        "③  기후환경 마일리지 서비스는 회원 가입이 해제된 경우에 해당 회원의 정보를 가입이 해제된 시점에서 1년 이내에 모두 삭제하여야 한다. 다만, 다른 법령에서 그 개인정보가 수집대상으로 명시되어있는 경우 그 정보를 삭제할 수 없다.\n" +
        "\n" +
        "제22조 (양도금지)\n" +
        "회원이 서비스의 이용권한, 기타 이용 계약상 지위를 타인에게 양도, 증여할 수 없으며, 이를 담보로 제공할 수 없다.\n" +
        "\n" +
        "제23조 (회원 개인정보보호)\n" +
        " 기후환경 마일리지 서비스는 이용자의 개인정보보호를 위하여 노력해야 한다. 이용자의 개인정보보호에 관해서는 정보통신망이용촉진 및 정보보호 등에 관한 법률에 따르고, 사이트에 \"개인정보보호정책\"을 고지한다\n" +
        "\n" +
        "제24조 (신용정보의 제공활용 동의)\n" +
        "①  기후환경 마일리지 서비스가 회원가입과 관련하여 취득한 회원의 개인신용정보를 타인에게 제공하거나 활용하고자 할 때에는 신용정보 이용 및 보호에 관한 법률 제23조의 규정에 따라 사전에 그 사유 및 해당기관 또는 업체명 등을 밝히고 해당 회원의 동의를 얻어야 한다.\n" +
        "② 본 서비스와 관련하여  기후환경 마일리지 서비스가 회원으로부터 신용정보 이용 및 보호에 관한 법률에 따라 타인에게 제공 활용에 동의를 얻은 경우 회원은  기후환경 마일리지 서비스가 신용정보 사업자 또는 신용정보 집중기관에 정보를 제공하여 회원의 신용을 판단하기 위한 자료로 활용하거나, 공공기관에서 정책자료로 활용되도록 정보를 제공하는데 동의한 것으로 간주한다.\n" +
        "③  기후환경 마일리지 서비스는 신용정보의 이용 및 보호에 관한 법률 또는 본 약관에서 규정하지 아니한 방식으로는 어떠한 회원의 신용정보, 개인정보에 대해서라도 타 기관 또는 개인에게 회원의 동의 없이 정보를 제공할 수 없다.\n" +
        "\n" +
        "제25조 (면책·손해배상)\n" +
        "① 기후환경 마일리지 서비스는 회원이 서비스에 게재한 정보, 자료, 사실의 정확성, 신뢰성 등 그 내용에 관하여는 어떠한 책임을 부담하지 아니하고, 회원은 자기의 책임아래 서비스를 이용하며, 서비스를 이용하여 게시 또는 전송한 자료 등에 관하여 손해가 발생하거나 자료의 취사 선택, 기타서비스 이용과 관련하여 어떠한 불이익이 발생 하더라도 이에 대한 모든 책임은 회원에게 있다.\n" +
        "② 기후환경 마일리지 서비스는 회원 간 또는 회원과 제3자간에 서비스를 매개로 하여 물품거래 등과 관련하여 어떠한 책임도 부담하지 아니하고, 회원이 서비스의 이용과 관련하여 기대하는 이익에 관하여 책임을 부담하지 않는다.\n" +
        "③ 회원 아이디(ID)와 비밀번호의 관리 및 이용상의 부주의로 인하여 발생 되는 손해 또는 제3자에 의한 부정사용 등에 대한 책임은 모두 회원에게 있다.\n" +
        "④ 회원이 이 약관의 제17조, 제20조, 제21조, 제22조 등의 규정을 위반함으로 인하여  기후환경 마일리지 서비스가 회원 또는 제3자에 대하여 책임을 부담한다.\n" +
        "⑤ 제④항으로 인해  기후환경 마일리지 서비스에게 손해가 발생하는 경우, 회원은  기후환경 마일리지 서비스에게 발생하는 모든 손해를 배상하여야 하며, 동 손해로부터  기후환경 마일리지 서비스를 면책시켜야 한다. 만일,  기후환경 마일리지 서비스가 면책되지 못한 경우 회원은 그로 인해  기후환경 마일리지 서비스가 입은 손해를 배상하여야 한다.\n" +
        "\n" +
        "제26조 (분쟁의 해결)\n" +
        "① 기후환경 마일리지 서비스와 회원은 서비스와 관련하여 발생한 분쟁을 원만하게 해결하기 위하여 필요한 모든 노력을 하여야 한다.\n" +
        "② 전항의 노력에도 불구하고, 동 분쟁에 관한 소송은  기후환경 마일리지 서비스 주소지인 서울특별시 서대문구 관할법원으로 한다.\n" +
        "\n" +
        "부칙\n" +
        "제1조(시행일) 이 약관은 2022년 3월 22일부터 시행한다."

val termsSecond = "개인정보처리방침\n" +
        "\n" +
        "서울특별시 서대문구 기후환경 마일리지 서비스는 개인정보 보호법 제30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리지침을 수립․공개합니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "제1조 (개인정보의 처리목적)\n" +
        "① 기후환경 마일리지 서비스는 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는 개인정보 보호법 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "1. 홈페이지 회원 가입 및 관리\n" +
        "\n" +
        "회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별ㆍ인증, 회원자격 유지ㆍ관리, 제한적 본인확인제 시행에 따른 본인확인, 서비스 부정이용 방지, 만 14세 미만 아동의 개인정보 처리시 법정대리인의 동의여부 확인, 각종 고지ㆍ통지, 고충처리 등을 목적으로 개인정보를 처리합니다.\n" +
        "\n" +
        "2. 민원사무 처리\n" +
        "\n" +
        "민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락ㆍ통지, 처리결과 통보 등의 목적으로 개인정보를 처리합니다.\n" +
        "\n" +
        "3. 서비스 제공\n" +
        "\n" +
        "본인인증, 채용정보 매칭, 면접 연습 및 컨텐츠 제공을 위한 개인식별, 회원간의 상호 연락, 물품 및 증빙발송, 부정 이용방지와 비인가 사용방지 등의 목적으로 개인정보를 처리합니다.\n" +
        "\n" +
        "4. 서비스 개발 및 마케팅 활용\n" +
        "\n" +
        "맞춤 서비스 제공, 서비스 안내 및 이용권유, 서비스 개선 및 신규 서비스 개발을 위한 통계 및 접속빈도 파악, 통계학적 특성에 따른 이벤트 정보 및 참여기회 제공 등의 목적으로 개인정보를 처리합니다.\n" +
        "\n" +
        "5. 고용 및 취업동향 파악\n" +
        "\n" +
        "고용 및 취업동향 파악을 위한 통계학적 분석, 서비스 고도화를 위한 데이터 분석 등의 목적으로 개인정보를 처리합니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "② 기후환경 마일리지 서비스가 개인정보 보호법 제32조에 따라 등록ㆍ공개하는 개인정보파일의 처리목적은 다음과 같습니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "개인정보파일명\n" +
        "\n" +
        "보유근거\n" +
        "\n" +
        "보유목적\n" +
        "\n" +
        "개인정보파일에 기록되는 개인정보의 항목\n" +
        "\n" +
        "보유기간\n" +
        "\n" +
        "개인회원\n" +
        "\n" +
        "개인회원 가입 및 플랫폼 서비스 운영 등\n" +
        "\n" +
        "(필수) 이름, 아이디, 비밀번호, 휴대폰번호 및 이메일, 성별, 카카오 계정 등 외부 서비스와의 연동을 통해 이용자가 설정한 계정 정보\n" +
        "(선택) 이용목적, 관심분야, 관심직종, 관심키워드\n" +
        "\n" +
        "회원 탈퇴 후 지체없이 삭제\n" +
        "\n" +
        "기관회원\n" +
        "\n" +
        "서울특별시 서대문구 운영에 관한 조례\n" +
        "\n" +
        "기업/기관회원 가입 및 플랫폼 서비스 운영 등\n" +
        "\n" +
        "(필수) 기업/기관명, 사업자등록번호, 인사담당자 이름, 인사담당자 생년월일, 인사담당자 휴대폰 번호, 아이디, 비밀번호, 대표자명, 본사/지사, 법인사업자/개인사업자, 업종\n" +
        "(선택) 홈페이지 주소, 이용목적, 관심분야, 관심직종, 관심키워드, 추천인 아이디, 주요 사업내용\n" +
        "\n" +
        "회원 탈퇴 후 지체없이 삭제\n" +
        "\n" +
        " \n" +
        "\n" +
        "※ 기타  기후환경 마일리지 서비스의 개인정보파일 등록사항 공개는 행정안전부 개인정보보호 종합지원 포털(www.privacy.go.kr) → 개인정보민원 → 개인정보열람등 요구 → 개인정보파일 목록검색 메뉴를 활용해주시기 바랍니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        " \n" +
        "\n" +
        "제2조 (개인정보의 처리 및 보유기간)\n" +
        "①  기후환경 마일리지 서비스는 법령에 따른 개인정보 보유ㆍ이용기간 또는 정보주체로부터 개인정보를 수집시에 동의받은 개인정보 보유ㆍ이용기간 내에서 개인정보를 처리ㆍ보유합니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "② 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.\n" +
        "\n" +
        "1. 홈페이지 회원 가입 및 관리 :  기후환경 마일리지 서비스 탈퇴시까지\n" +
        "\n" +
        "다만, 다음의 사유에 해당하는 경우에는 해당 사유 종료시까지\n" +
        "\n" +
        "  1) 관계 법령 위반에 따른 수사ㆍ조사 등이 진행중인 경우에는 해당 수사ㆍ조사 종료시까지\n" +
        "\n" +
        "  2) 홈페이지 이용에 따른 채권ㆍ채무관계 잔존시에는 해당 채권ㆍ채무관계 정산시까지\n" +
        "\n" +
        "  3) 보유기간을 미리 공지하고 그 보유기간이 경과하지 아니한 경우와 개별적으로 동의를 받은 경우에는 약정한 기간 동안 보유\n" +
        "\n" +
        "2. 민원사무 처리 : 민원처리 종료 후 3년\n" +
        "\n" +
        "3. 부정이용 등에 관한 기록 : 5년\n" +
        "\n" +
        "4. 웹사이트 방문기록(로그인 기록, 접속기록) : 1년\n" +
        "\n" +
        "5. 이용자가 1년 동안  기후환경 마일리지 서비스를 이용하지 않은 경우, 이메일(또는 카카오계정 등 외부 서비스와의 연동을 통해 이용자가 설정한 계정 정보)를 \"휴면계정\"로 분리하여 해당 계정의 이용을 중지할 수 있습니다. 이 경우  기후환경 마일리지 서비스는 \"휴면계정 처리 예정일\"로부터 30일 이전에 해당사실을 전자메일, 서면, SMS 중 하나의 방법으로 사전통지하며 이용자가 직접 본인확인을 거쳐, 다시 \"플랫폼\" 이용 의사표시를 한 경우에는 \"플랫폼\" 이용이 가능합니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        " \n" +
        "\n" +
        "제3조 (개인정보의 제3자 제공)\n" +
        "①  기후환경 마일리지 서비스는 정보주체의 개인정보를 제1조(개인정보의 처리 목적)에서 명시한 범위 내에서만 처리하며, 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.\n" +
        "\n" +
        "② 다음의 경우에는 합당한 절차를 통하여 개인정보를 제공할 수 있습니다.\n" +
        "\n" +
        "1. 마일리지 서비스에 연계하는 경우\n" +
        "\n" +
        "이용자가 기후환경 포인트 마일리지 서비스를 연계하는 경우, 이용자의 연락처 등 개인정보가 마일리지 연계 절차 진행을 위해 제공됩니다.\n" +
        "\n" +
        "2. 기업/기관회원에게 제공되는 경우\n" +
        "\n" +
        "포스팅 정보 노출을 허용한 이용자에 한하여 조회 및 제공이 가능하며, 이용자가 수락한 경우에 한하여 개인정보 열람이 가능합니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "개인정보를 제공받는 자\n" +
        "\n" +
        "제공받는 자의 개인정보 이용목적\n" +
        "\n" +
        "제공하는 개인정보 항목\n" +
        "\n" +
        "위탁기간\n" +
        "\n" +
        "기업/기관회원\n" +
        "\n" +
        "포인트 연계 의사가 있는 개인회원 정보 열람\n" +
        "\n" +
        "회원 탈퇴시\n" +
        "\n" +
        " \n" +
        "\n" +
        " \n" +
        "\n" +
        " \n" +
        "\n" +
        "제4조 (개인정보처리의 위탁)\n" +
        "①  기후환경 마일리지 서비스는 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "위탁하는 업무의 내용\n" +
        "\n" +
        "수탁업체현황\n" +
        "\n" +
        "업체명\n" +
        "\n" +
        "주소\n" +
        "\n" +
        " 기후환경 마일리지 서비스 시스템 운영관리 및 회원정보 관리를 위한 기술적 처리\n" +
        "\n" +
        "주식회사 빅팀아이앤씨\n" +
        "\n" +
        "서울시 도봉구 마들로731 로앤로즈빌딩 2F\n" +
        "\n" +
        "\n" +
        " \n" +
        "\n" +
        "②  기후환경 마일리지 서비스는 위탁계약 체결시 개인정보 보호법 제26조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적ㆍ관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리ㆍ감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.\n" +
        "\n" +
        "③ 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "제5조 (정보주체와 법정대리인의 권리ㆍ 의무 및 행사방법)\n" +
        "① 정보주체는  기후환경 마일리지 서비스에 대해 언제든지 개인정보 열람ㆍ정정ㆍ삭제ㆍ처리정지 요구 등의 권리를 행사할 수 있습니다.\n" +
        "\n" +
        "② 제1항에 따른 권리 행사는  기후환경 마일리지 서비스에 대해 개인정보 보호법 시행령 제41조제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며,  기후환경 마일리지 서비스는 이에 대해 지체없이 조치하겠습니다.\n" +
        "\n" +
        "③ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.\n" +
        "\n" +
        "④ 개인정보 열람 및 처리정지 요구는 개인정보보호법 제35조 제4항, 제37조 제2항에 의하여 정보주체의 권리가 제한 될 수 있습니다.\n" +
        "\n" +
        "⑤ 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.\n" +
        "\n" +
        "⑥  기후환경 마일리지 서비스는 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "제6조 (처리하는 개인정보 항목)\n" +
        " 기후환경 마일리지 서비스는 다음의 개인정보 항목을 처리하고 있습니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "1. 기후환경 마일리지 서비스 개인 회원가입 및 관리\n" +
        "\n" +
        "ㆍ필수항목 : 성명, 생년월일, 아이디, 비밀번호, 주소, 휴대폰번호, 성별, 이메일주소, 이메일 인증번호\n" +
        "\n" +
        "ㆍ선택항목 : 프로필사진, 닉네임, 포인트 정보, 포스팅 정보\n" +
        "\n" +
        " \n" +
        "\n" +
        "2. 앱서비스 가입 기업/기관 회원가입 및 관리\n" +
        "\n" +
        "ㆍ필수항목 : 회사이름, 주소, 사업자등록번호, 산업군, 정보수신 이메일, 담당자 연락처\n" +
        "\n" +
        " \n" +
        "\n" +
        " \n" +
        "\n" +
        "3. 인터넷 서비스 이용과정에서 아래 개인정보 항목이 자동으로 생성되어 수집될 수 있습니다.\n" +
        "\n" +
        "ㆍIP주소, 쿠키, MAC주소, 서비스 이용기록, 방문기록, 불량 이용기록 등\n" +
        "\n" +
        " \n" +
        "\n" +
        "제7조 (개인정보의 파기)\n" +
        "①  기후환경 마일리지 서비스는 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다.\n" +
        "\n" +
        "② 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당 개인정보(또는 개인정보파일)을 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다.\n" +
        "\n" +
        "③ 개인정보 파기의 절차 및 방법은 다음과 같습니다.\n" +
        "\n" +
        "1. 파기절차\n" +
        "\n" +
        " 기후환경 마일리지 서비스는 파기하여야 하는 개인정보(또는 개인정보파일)에 대해 개인정보 파기계획을 수립하여 파기합니다.  기후환경 마일리지 서비스는 파기 사유가 발생한 개인정보(또는 개인정보파일)을 선정하고,  기후환경 마일리지 서비스는 개인정보 보호책임자의 승인을 받아 개인정보(또는 개인정보파일)을 파기합니다.\n" +
        "\n" +
        "2. 파기방법\n" +
        "\n" +
        " 기후환경 마일리지 서비스는 전자적 파일 형태로 기록ㆍ저장된 개인정보는 기록을 재생할 수 없도록 로우레밸포멧(Low Level Format) 등의 방법을 이용하여 파기하며, 종이 문서에 기록ㆍ저장된 개인정보는 분쇄기로 분쇄하거나 소각하여 파기합니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "제8조 (개인정보의 안전성 확보조치)\n" +
        "①  기후환경 마일리지 서비스는 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다.\n" +
        "\n" +
        "1. 관리적 조치 : 내부관리계획 수립ㆍ시행, 정기적 직원 교육 등\n" +
        "\n" +
        "2. 기술적 조치 : 개인정보처리시스템 등의 접근권한 관리, 접근통제시스템 설치, 고유식별정보 등의 암호화, 보안프로그램 설치\n" +
        "\n" +
        "3. 물리적 조치 : 전산실, 자료보관실 등의 접근통제\n" +
        "\n" +
        " \n" +
        "\n" +
        "제9조 (개인정보 자동 수집 장치의 설치∙운영 및 거부에 관한 사항)\n" +
        "①  기후환경 마일리지 서비스는 이용자에게 개별적인 맞춤서비스를 제공하기 위해 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용합니다.\n" +
        "\n" +
        "② 쿠키는 웹사이트를 운영하는데 이용되는 서버(http)가 이용자의 컴퓨터 브라우저에게 보내는 소량의 정보이며 이용자의 PC 컴퓨터내의 하드디스크에 저장되기도 합니다.\n" +
        "\n" +
        "  가. 쿠키의 사용목적: 이용자가 방문한 각 서비스와 웹 사이트들에 대한 방문 및 이용형태, 인기 검색어, 보안접속 여부, 등을 파악하여 이용자에게 최적화된 정보 제공을 위해 사용됩니다.\n" +
        "\n" +
        "  나. 쿠키의 설치∙운영 및 거부 : 웹브라우저 상단의 도구>인터넷 옵션>개인정보 메뉴의 옵션 설정을 통해 쿠키 저장을 거부 할 수 있습니다.\n" +
        "\n" +
        "  다. 쿠키 저장을 거부할 경우 맞춤형 서비스 이용에 어려움이 발생할 수 있습니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "제10조(개인정보 보호책임자)\n" +
        "①  기후환경 마일리지 서비스는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.\n" +
        "\n" +
        "▶ 개인정보 보호책임자(담당부서)\n" +
        "\n" +
        "성명 : 지성환\n" +
        "\n" +
        "직책 : 주무관\n" +
        "\n" +
        "연락처 :  step@sdm.go.kr, Tel) 02-3140-8097\n" +
        "\n" +
        " \n" +
        "\n" +
        "② 정보주체께서는  기후환경 마일리지 서비스의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호채임자 및 담당부서로 문의하실 수 있습니다.  기후환경 마일리지 서비스는 정보주체의 문의에 대해 지체없이 답변 및 처리해드릴 것입니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "제11조(개인정보 열람청구)\n" +
        "① 정보주체는 개인정보 보호법 제35조에 따른 개인정보의 열람 청구를 아래의 부서에 할 수 있습니다.  기후환경 마일리지 서비스는 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.\n" +
        "\n" +
        "▶ 개인정보 열람청구 접수ㆍ처리 부서\n" +
        "\n" +
        "성명 : 지성환\n" +
        "\n" +
        "직책 : 주무관\n" +
        "\n" +
        "연락처 : step@sdm.go.kr, Tel) 02-3140-8097\n" +
        "\n" +
        "② 정보주체께서는 제1항의 열람청구 접수․처리부서 이외에, 행정안전부의 ‘개인정보보호 종합지원 포털’ 웹사이트(www.privacy.go.kr)를 통하여서도 개인정보 열람청구를 하실 수 있습니다.\n" +
        "\n" +
        "제12조(권익침해 구제방법)\n" +
        "정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.\n" +
        "\n" +
        " \n" +
        "\n" +
        "<아래의 기관은  기후환경 마일리지 서비스와는 별개의 기관으로서,  기후환경 마일리지 서비스의 자체적인 개인정보 불만처리, 피해구제 결과에 만족하지 못하시거나 보다 자세한 도움이 필요하시면 문의하여 주시기 바랍니다>\n" +
        "\n" +
        " \n" +
        "\n" +
        "▶ 개인정보 침해신고센터 (한국인터넷진흥원 운영)\n" +
        "\n" +
        "- 소관업무 : 개인정보 침해사실 신고, 상담 신청\n" +
        "\n" +
        "- 홈페이지 : privacy.kisa.or.kr\n" +
        "\n" +
        "- 전화 : (국번없이) 118\n" +
        "\n" +
        " \n" +
        "\n" +
        "제13조(부칙)\n" +
        "이 개인정보 처리방침은 2022년 3월 22일부터 적용됩니다."

val termsThird = "- 만 14세 미만의 어린이는 법률에 의거하여 보호자(법적대리인)의 동의가 필요합니다.\n" +
        "\n" +
        "- 정보통신망이용촉진 및 정보보호등에 관한 법률 제31조 제1항에서 14세미만의 아동의 개인정보수집 시 부모의 동의를 얻도록 규정되어 있습니다. 동의 체크 후 부모님 동의 확인 인증을 진행해 주시기 바랍니다. \n" +
        "\n" +
        "-  휴대폰 인증 시 신용평가 기관을 통하여 실명확인을 진행하며, 실명 확인 용도 외 별도 저장되지 않습니다.\n" +
        "\n" +
        "-  만 14세 미만 아동의 개인정보 수집·이용에 대한 보호자(법정대리인) 동의"

val uploadAlarm = "본인이 직접 촬영하거나\n제작한 게시물이 아닐 경우,\n마일리지 환수 등 불이익을 받을 수 있습니다."

val MainFeedReportOptions =
    listOf("음란성 게시물", "폭력적 또는 불쾌한 게시물", "스팸 게시물", "사생활 침해/개인정보 유출 게시물", "불법적인 게시물")
val CommentReportOptions =
    listOf("음란성 댓글", "욕설, 비방, 명예훼손 댓글", "스팸 댓글", "사생활 침해, 개인정보 유출 게시물", "불법적인 댓글")
val UserReportOptions =
    listOf(
        "스팸 및 사기",
        "계정 해킹 의심됨",
        "개인정보 유출, 사생활 침해",
        "신고자 본인 혹은 타인을 사칭",
        "가학적이거나 혐오 콘텐츠 포함된 계정",
        "기타 이유"
    )
