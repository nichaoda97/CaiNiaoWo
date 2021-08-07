package com.cainiaowo.common.webview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.BuildConfig
import com.cainiaowo.common.R
import com.cainiaowo.common.ktx.context
import com.cainiaowo.common.network.config.SP_KEY_USER_TOKEN
import com.cainiaowo.common.utils.MMKVUtils
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebView
import com.just.agentweb.DefaultWebClient

class WebViewActivity : AppCompatActivity() {

    private lateinit var mAgentWeb: AgentWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val viewGroup = findViewById<ViewGroup>(R.id.webView)
        mAgentWeb = AgentWeb.with(this@WebViewActivity)
            .setAgentWebParent(
                viewGroup, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator(ContextCompat.getColor(context, R.color.colorAccent))
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)   // 打开其他应用时,弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl()       // 拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .get()

        val url = intent.getStringExtra("url")
        mAgentWeb.urlLoader.loadUrl(url)

        // 添加JS调用native函数
        mAgentWeb.jsInterfaceHolder.addJavaObject(JsAndroidApi.JS_CALL_APP_KEY, JsAndroidApi)

        // 开启WebView的调试
        AgentWebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    companion object {
        fun openUrl(context: Context, url: String) {
            context.startActivity(Intent(context, WebViewActivity::class.java).also {
                it.putExtra("url", url)
            })
        }
    }
}

object JsAndroidApi {

    const val JS_CALL_APP_KEY = "cniaoApp"

    @JavascriptInterface
    fun getAppToken(): String {
        LogUtils.w("JsAndroidApi 中 js调用了getToken")
        return MMKVUtils.getString(SP_KEY_USER_TOKEN) ?: ""
    }
}