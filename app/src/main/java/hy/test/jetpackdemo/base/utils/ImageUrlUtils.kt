package hy.test.jetpackdemo.base.utils

import android.content.Context
import hy.test.jetpackdemo.base.constant.Constant
import java.io.File

object ImageUrlUtils {
    private var domain = ""
    fun getCompleteUrl(url: String, mContext: Context): String {

        try {
            val file = File(url)
            if (file.exists()) {
                return url
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return if (url.startsWith("http") || url.startsWith("https")) {
            url
        } else {
            domain = SharedPreferencesUtils.getParam(mContext, Constant.IMAGE_DOMAIN, "") as String
            if (domain.isNullOrBlank()) {
                "http://logistics.haoqipei.com/$url"
            } else {
                domain + url
            }

        }
    }

}