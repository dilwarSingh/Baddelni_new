package com.baddelni.baddelni.loginRegister

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.Countries.Countries
import com.baddelni.baddelni.Response.Countries.CountriesItem
import com.baddelni.baddelni.Response.SocialLoginResponses.SocialRegisterResponse
import com.baddelni.baddelni.Response.loginResponse.LoginResponse
import com.baddelni.baddelni.intro.AdActivity
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.*
import com.baddelni.baddelni.util.Api.Api
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    val co: CommonObjects by lazy { CommonObjects(context!!) }
    private lateinit var countryList: List<CountriesItem>

    companion object {
        private const val TAG = "LoginFragment"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCountyData()
        /*if (co.getAppLanguage().isEnglish()) {
            changeLanguage.text = getString(R.string.english)
        } else {
            changeLanguage.text = getString(R.string.arabic)
        }*/

        changeLanguage.setOnClickListener {

            co.showLanguageDialog(object : LangListener {
                override fun onClickEnglish() {
                    val appLanguage = AppLanguage.ENGLISH
                    co.setAppLanguage(appLanguage)

                    LocaleHelper.setLocale(context, appLanguage.langCode())

                    val intent = Intent(context, LoginRegisterActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

                override fun onClickHindi() {
                    val appLanguage = AppLanguage.HINDI
                    co.setAppLanguage(appLanguage)

                    LocaleHelper.setLocale(context, appLanguage.langCode())

                    val intent = Intent(context, LoginRegisterActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

                override fun onClickArabic() {
                    val appLanguage = AppLanguage.ARABIC
                    co.setAppLanguage(appLanguage)

                    LocaleHelper.setLocale(context, appLanguage.langCode())

                    val intent = Intent(context, LoginRegisterActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

            })


        }

        signUpBt.setOnClickListener { (context as LoginRegisterActivity).changeContainerFragment(RegisterFragment()) }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this.activity!!, gso)
        auth = FirebaseAuth.getInstance()

        callbackManager = CallbackManager.Factory.create()


        val token = FirebaseInstanceId.getInstance().id
        //   val buttonFacebookLogin = LoginButton(context)

        /*  buttonFacebookLogin.setReadPermissions("email", "public_profile")
          buttonFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
              override fun onSuccess(loginResult: LoginResult) {
                  Log.d(TAG, "facebook:onSuccess:$loginResult")
                  handleFacebookAccessToken(loginResult.accessToken)
              }

              override fun onCancel() {
                  Log.d(TAG, "facebook:onCancel")
              }

              override fun onError(error: FacebookException) {
                  Log.d(TAG, "facebook:onError", error)
                  Toast.makeText(context, "Error Occurred due to: ${error.message}", Toast.LENGTH_LONG).show()

              }
          })
  */

        loginBt.setOnClickListener {
            hitLoginApi()
        }

        forgotBt.setOnClickListener {
            context!!.startActivity(Intent(context!!, ForgotPassword::class.java))
        }
        googleCard.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        //     fbCard.visibility = GONE
        fbCard.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))

            //   buttonFacebookLogin.performClick()

        }

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                co.showToastDialog(detail = getString(R.string.errorOccurred) + " " + error.message, yesNo = null)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        } else
            callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")
//user.providerData[1].email
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this.activity!!) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        if (user != null) {
                            if (user.providerData[1].email != null && !user.providerData[1].email!!.isEmpty()) {
                                registerUser(user, "f", user.providerData[1].email)
                            } else {
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle(getString(R.string.fbError))
                                builder.setMessage(getString(R.string.facebookAccountNotFullyVerified))
                                builder.setCancelable(false)

                                builder.setPositiveButton(
                                        getString(R.string.ok)
                                ) { dialog, id -> dialog.cancel() }

                                val alert11 = builder.create()
                                alert11.show()
                            }
                        } else
                            registerUser(null, null)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        co.showToastDialog(detail = getString(R.string.authenticationFailed), yesNo = null)

                        registerUser(null, null)
                    }

                }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this.activity!!) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        if (user != null) {
                            registerUser(user, "g", userEmail = acct.email)
                        } else {
                            co.showToastDialog(detail = getString(R.string.couldntVeryfyAccount), yesNo = null)
                        }
                    } else {
                        co.showToastDialog(detail = getString(R.string.installPlayServices), yesNo = object : YesNoInterface {
                            override fun onClickYes() {
                                Log.w(TAG, "signInWithCredential:failure", task.exception)
                                registerUser(null, null)

                            }
                        })
                    }

                }
    }

    private fun registerUser(user: FirebaseUser?, type: String?, userEmail: String? = user?.email) {
        if (user != null) {
            co.showLoading()

            if (co.getStringPrams(AppConstants.TOKEN).isBlank()) {
                val taskToken = FirebaseInstanceId.getInstance().instanceId
                taskToken.addOnCompleteListener(context as LoginRegisterActivity) { result ->
                    val myToken = result.result?.token ?: ""
                    co.putStringPrams(AppConstants.TOKEN, myToken)
                    registerUser(user, type)
                }
                return
            }//SocialRegisterResponse
            Api.getApi().socialRegister(type ?: "g", user.uid, user.displayName ?: "", userEmail
                    ?: "", co.getStringPrams(AppConstants.TOKEN), "a").enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val jResponse = response.body()?.string()
                    val body = Gson().fromJson(jResponse, SocialRegisterResponse::class.java)

                    val jsonObject = JSONObject(jResponse)

                    /*     // val body = response.body()    */
                    body?.user?.apply {
                        if (body.code!!.isSuccess()) {
                            co.putStringPrams(AppConstants.AVALIABLE_POSTS, (availableProduct
                                    ?: 0).toString())

                            co.putStringPrams(AppConstants.IMG_URL, (img
                                    ?: 0).toString())

                            co.putStringPrams(AppConstants.PERSON_NAME, (name
                                    ?: 0).toString())

                            co.putStringPrams(AppConstants.USER_ID, id.toString())
                            co.putBool(AppConstants.LOGGED_IN, true)

                            showLocationDialog(jsonObject)
                        } else {
                            co.showToastDialog(detail = getString(R.string.errorOccurred) + " " + body.code, yesNo = null)
                        }
                    }
                    co.hideLoading()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    co.myToast(t.message)
                    Log.e("ResponseFailure: ", t.message)
                    t.printStackTrace()
                    co.hideLoading()
                }

            })
        }
    }

    private fun showLocationDialog(jsonObject: JSONObject) {

        val user = jsonObject.getJSONObject("user")

        MaterialDialog
                .Builder(context!!)
                .itemsCallbackSingleChoice(0, object : MaterialDialog.ListCallbackSingleChoice {
                    override fun onSelection(dialog: MaterialDialog?, itemView: View?, postion: Int, text: CharSequence?): Boolean {
                        val countryId = countryList[postion].id!!

                        saveCountry(countryId, user)

                        return true
                    }
                })
                .items(countryList.map { it.country })
                .build()
                .show()


    }

    private fun saveCountry(countryId: Int, user: JSONObject) {
        co.showLoading()
        Api.getApi().changeCountry(co.getStringPrams(), countryId.toString(), co.getAppLanguage().langCode())
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                        val jo = JSONObject(response.body()?.string())

                        if (jo.getString("code") == "0") {
                            if (!user.has("phone")) {
                                val intent = Intent(context, AdActivity::class.java)
                                intent.putExtra("toScreen", "home")
                                intent.putExtra("to", "Interest")
                                context?.startActivity(intent)
                            } else {
                                val intent = Intent(context, AdActivity::class.java)
                                intent.putExtra("to", "main")
                                context?.startActivity(intent)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }


                })


    }

    private fun getCountyData() {


        co.showLoading()

        Api.getApi().getCountries(co.getAppLanguage().langCode()).enqueue(object : Callback<Countries> {

            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {
                        this@LoginFragment.countryList = countryList!!
                    }
                }
                co.hideLoading()

            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })

    }

    private fun hitLoginApi() {

        if (isDataCorrect().not())
            return

        co.showLoading()

        if (co.getStringPrams(AppConstants.TOKEN).isBlank()) {
            val taskToken = FirebaseInstanceId.getInstance().instanceId
            taskToken.addOnCompleteListener(context as LoginRegisterActivity, object : OnCompleteListener<InstanceIdResult> {
                override fun onComplete(result: Task<InstanceIdResult>) {
                    val myToken = result.result?.token ?: ""
                    co.putStringPrams(AppConstants.TOKEN, myToken)
                    hitLoginApi()
                }
            })
            return
        }

        Api.getApi().login(emailET.text.toString().trim(), passwordET.text.toString().trim(), co.getAppLanguage().langCode()
                , co.getStringPrams(AppConstants.TOKEN), "a")
                .enqueue(object : Callback<LoginResponse> {

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val body = response.body()

                        body?.apply {

                            if (code!!.isSuccess()) {
                                co.putStringPrams(AppConstants.USER_ID, body.data?.id.toString())
                                co.putStringPrams(AppConstants.PERSON_NAME, body.data?.name.toString())
                                co.putStringPrams(AppConstants.IMG_URL, body.data?.img.toString())
                                co.putBool(AppConstants.LOGGED_IN, true)
                                co.putStringPrams(AppConstants.AVALIABLE_POSTS, body.data?.availableProduct.toString())
                                val intent = Intent(context, AdActivity::class.java)
                                intent.putExtra("to", "Main")
                                context?.startActivity(intent)
                                (context as AppCompatActivity).finish()
                            } else {
                                co.myToast(msg)

                            }

                        }
                        co.hideLoading()
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }

                })

    }

    private fun isDataCorrect(): Boolean {

        return true
    }


}
