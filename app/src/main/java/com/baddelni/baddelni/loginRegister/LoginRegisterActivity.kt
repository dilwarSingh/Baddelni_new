package com.baddelni.baddelni.loginRegister

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.AppLanguage
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.YesNoInterface
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_login_register.*


class LoginRegisterActivity : AppCompatActivity() {

    val co: CommonObjects by lazy { CommonObjects(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (co.getDefaultAppLanguage() == null) {
            val appLanguage = AppLanguage.ARABIC
            co.setAppLanguage(appLanguage)

            LocaleHelper.setLocale(this, appLanguage.langCode())

            /*  val intent = Intent(this, LoginRegisterActivity::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
              startActivity(intent)*/
        }

        setContentView(R.layout.activity_login_register)



        if (checkPlayServices()) {
            requestStoragePermission()
        }
    }

    fun changeContainerFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(frameContainer.id, fragment).commitAllowingStateLoss()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, AppLanguage.ARABIC.langCode()))

        // super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    private fun requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (co.getBool(AppConstants.LOGGED_IN)) {
                                startActivity(Intent(this@LoginRegisterActivity, MainActivity::class.java))
                                finish()
                            } else {
                                changeContainerFragment(LoginFragment())
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).withErrorListener { error ->
                    co.showToastDialog(detail = getString(R.string.errorOccurred) + error?.name, yesNo = null)
                }.onSameThread()
                .check()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this@LoginRegisterActivity)
        builder.setTitle(getString(R.string.needPermission))
        builder.setMessage(getString(R.string.appNeedPermission))
        builder.setPositiveButton(getString(R.string.goToSetting), DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
            openSettings()
        })
        builder.setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()

    }

    var errorDialog: android.app.Dialog? = null

    private fun checkPlayServices(): Boolean {

        val googleApiAvailability = GoogleApiAvailability.getInstance()

        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {

                if (errorDialog == null) {
                    errorDialog = googleApiAvailability.getErrorDialog(this, resultCode, 2404)
                    errorDialog?.setCancelable(false)
                }

                if (!errorDialog?.isShowing!!)
                    errorDialog?.show()

            } else {
                co.showToastDialog(detail = getString(R.string.installPlayServices), yesNo = object : YesNoInterface {
                    override fun onClickYes() {
                        finish()
                    }
                })
            }
        }

        return resultCode == ConnectionResult.SUCCESS
    }
}
