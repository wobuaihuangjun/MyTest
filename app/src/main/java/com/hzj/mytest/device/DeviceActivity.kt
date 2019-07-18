package com.hzj.mytest.device

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import android.widget.TextView
import com.hzj.mytest.R
import com.hzj.mytest.barlibrary.BaseActivity


class DeviceActivity : BaseActivity() {

    private val TAG = "DeviceActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)
        if (checkPermission()) {
            findViewById<TextView>(R.id.device_info).setText(init())
        }
    }

    private fun init(): String {
        val sb = StringBuffer()
        sb.append("手机品牌:" + DeviceUtil.getPhoneBrand()
                + " ,型号:" + DeviceUtil.getPhoneModel()
                + " ,系统版本:" + DeviceUtil.getAndroidOsVersion() + "\n\n")

        val deviceUUID = DeviceUUIDFactory.buildDeviceUUID(this)
        sb.append("deviceUUID: " + deviceUUID + ", \n\n")

        val android_id = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID)
        sb.append("android_id: " + android_id + ", \n\n")

        val SerialNumber1 = Build.SERIAL
        sb.append("SerialNumber1: " + SerialNumber1 + ", \n\n")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= 26) {
                    val SerialNumber2 = Build.getSerial()
                    sb.append("SerialNumber2: " + SerialNumber2 + ", \n\n")
                }

                val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val device_id = tm.getDeviceId()
                sb.append("device_id: " + device_id + ", \n\n")
            }
        } else {
            val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val device_id = tm.getDeviceId();
            sb.append("device_id: " + device_id + ", \n\n")
        }

        sb.append("getMacAddress: " + MacAddressUtil.getMacAddress(this) + ", \n\n")
        sb.append("getMacByJavaAPI: " + MacAddressUtil.getMacByJavaAPI() + ", \n\n")
        sb.append("getMachineHardwareAddress: " + MacAddressUtil.getMachineHardwareAddress() + ", \n\n")
        sb.append("getMacAddressForOpen: " + MacAddressUtil.getMacAddressForOpen() + ", \n\n")

        return sb.toString()
    }

    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 0)
                Log.d(TAG, "获取电话权限")
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                findViewById<TextView>(R.id.device_info).setText(init())
            }
        }
    }

}