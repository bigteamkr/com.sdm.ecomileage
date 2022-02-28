package com.sdm.ecomileage.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InlineSuggestionsResponse
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.sdm.ecomileage.activities.HomeActivity
import com.sdm.ecomileage.activities.RegisterActivity
import com.sdm.ecomileage.databinding.FragmentRegisterBinding
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback


class RegisterFragment : Fragment() {

    // This for control the Fragment-Layout views:
    lateinit var binding: FragmentRegisterBinding
    lateinit var registerActivity: RegisterActivity
    val api = APIS.create();
    var chk = false

//    val pref = requireActivity().getPreferences(0)
//    val editor = pref.edit()







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        네이버 로그인 콜백
//        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//            if (error != null) {
//                Toast.makeText(getActivity(), "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
//            }
//            else if (tokenInfo != null) {
//                Toast.makeText(getActivity(), "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
//                val intent = Intent(getActivity(), HomeActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                getActivity()?.finish()
//            }
//        }
//
//
//        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//            if (error != null) {
//                when {
//                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
//                        Toast.makeText(getActivity(), "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
//                        Toast.makeText(getActivity(), "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
//                        Toast.makeText(getActivity(), "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
//                        Toast.makeText(getActivity(), "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
//                        Toast.makeText(getActivity(), "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
//                        Toast.makeText(getActivity(), "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.ServerError.toString() -> {
//                        Toast.makeText(getActivity(), "서버 내부 에러", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
//                        Toast.makeText(getActivity(), "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
//                    }
//                    else -> { // Unknown
//                        Toast.makeText(getActivity(), "기타 에러", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//            else if (token != null) {
//                Toast.makeText(getActivity(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
//                val intent = Intent(getActivity(), HomeActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                getActivity()?.finish()
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the fragment layout:

        binding = FragmentRegisterBinding.inflate(inflater, container, false)



        binding.cardGoogle.setOnClickListener { goToHome() }
//        binding.cardApple.setOnClickListener { goToHome() }
        binding.cardFacebook.setOnClickListener { goToHome() }
        binding.cardLogin.setOnClickListener { goToHome() }
        binding.cardKokaoTalk.setOnClickListener {
            goToHome()

//            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
//                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
//
//
//            }else{
//                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
//            }
        }
        binding.cardNaver.setOnClickListener { goToHome() }



        return binding.root // Get the fragment layout root.


    }

    private fun goToHome()
    {
      startActivity(Intent(context,HomeActivity::class.java ))
    }

//    fun Login() {
//        var random_uuid = UUID.randomUUID().toString()
//        MyApplication.uuid.setUid("uuid",random_uuid)
//        binding.cardLogin.setOnClickListener {
//            if(chk == true) {
//                val data = PostModel(binding.edEmail.text.toString(), binding.edPassword.text.toString(),random_uuid)
//                api.post_users(data).enqueue(object : Callback<PostResult> {
//                    override fun onResponse(
//                        call: Call<PostResult>,
//                        response: Response<PostResult>
//                    ) {
//                        Log.d("log", response.toString())
//                        Log.d("log", response.body().toString())
//                        if(!response.body().toString().isEmpty()) {
//                            if(response.body()?.data?.message == "정상발급") {
//                                val intent = Intent(this@RegisterFragment, HomeActivity::class.java);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                                MyApplication.prefs.setString("token", response.body()!!.data.token)
//                                MyApplication.id.setId("id", "noid"))
//                                startActivity(intent);
//                            }
//                            else if(response.body()?.message.toString() == "아이디가 없습니다."){
//                                Toast.makeText(this@RegisterFragment, "아이디를 조회하지 못했습니다",Toast.LENGTH_SHORT.show())
//                            }
//                            else if(response.body()?.message.toString() == "비밀번호가 유효하지 않습니다."){
//                                Toast.makeText(this@RegisterFragment, "비밀번호를 조회하지 못했습니다.",Toast.LENGTH_SHORT.show())
//                            }
//                        }
//                    }
//
//                    override fun onFailure(call: Call<PostResult>, t: Throwable) {
//                        Log.d("log", t.message.toString())
//                        Log.d("log", "fail")
//                    }
//                })
//            }
//            else{
//                Toast.makeText(this@RegisterFragment, "아이디 또는 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}