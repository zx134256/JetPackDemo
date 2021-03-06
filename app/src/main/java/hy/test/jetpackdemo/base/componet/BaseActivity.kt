package hy.test.jetpackdemo.base.componet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import hy.test.jetpackdemo.R
import com.balysv.materialmenu.MaterialMenuDrawable
import com.jakewharton.rxbinding2.widget.RxTextView.color
import hy.test.jetpackdemo.base.utils.ActivityControllerUtil
import hy.test.jetpackdemo.base.utils.UIUtil
import hy.test.jetpackdemo.base.helper.PermissionsHelper

import com.leshu.gamebox.base.MyApplication
import com.leshu.gamebox.base.componet.IBaseView
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import hy.test.jetpackdemo.base.utils.DisplayUtil
import hy.test.jetpackdemo.base.utils.RxViewUtils
import kotlinx.android.synthetic.main.include_title_bar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : RxAppCompatActivity(), IBaseView, LifecycleOwner {
    private var mIsFullScreen = false
    private var dataBindingView: ViewDataBinding? = null
    private lateinit var mLifecycleRegistry: LifecycleRegistry
    val mPermissionsHelper by lazy { PermissionsHelper(this) }
    private var mIsSwipeEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingView = DataBindingUtil.inflate(LayoutInflater.from(this), resLayoutId, null, false)

        if (dataBindingView == null) {
            setContentView(resLayoutId)
        } else {
            setContentView(dataBindingView?.root)
            dataBindingView?.lifecycleOwner = this
        }

        mLifecycleRegistry = LifecycleRegistry(this)

        mLifecycleRegistry.currentState = Lifecycle.State.CREATED
        mLifecycleRegistry.addObserver(object : LifecycleObserver {

        })

        ActivityControllerUtil.add(this)
        EventBus.getDefault().register(this)

        if (!mIsFullScreen) {
            /*todo fix ??????????????????*/

        }

        hideTitleBarRight()

    }

    /**
     *
     * org.greenrobot.eventbus.EventBusException: Subscriber class com.jiutong.haofahuo.fragment.WaitDueFragment
     * and its super classes have no public methods with the @Subscribe annotation
     *
     * ???????????????EventBus??????????????????????????????????????? @Subscribe ????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSubscribe(str: String) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        super.onStop()
    }

    fun getBindingView(): ViewDataBinding {
        return dataBindingView!!
    }

    private fun hideTitleBarRight() {

    }

    /**
     * Android6.0????????????????????????????????????????????????????????????????????????
     */
    private fun setStatusAndNavBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //???????????????????????????????????????,??? ContentView ???????????????????????????????????????
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            //?????????????????? flag ???????????? setStatusBarColor ????????????????????????
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //?????????????????????
            window.statusBarColor = UIUtil.getColor(R.color.white)
            //?????????????????????
            window.navigationBarColor = UIUtil.getColor(R.color.white)
            //???????????????????????????????????????
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    /**
     * ??????activity????????????????????????setContentView??????????????????
     */
    protected fun setFullScreen() {
        mIsFullScreen = true
        // ????????????
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // ????????????
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //???????????????
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        super.onPause()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        ActivityControllerUtil.remove(this)
        MyApplication.getRefWatcher().watch(this)
        dismissLoading()
        RxViewUtils.mCompositeDisposable.clear()
        EventBus.getDefault().unregister(this)
    }

    /**
     * ?????????title
     */
    protected fun initTitle(
        resId: Int,
        isBack: Boolean = true,
        iconState: MaterialMenuDrawable.IconState = MaterialMenuDrawable.IconState.ARROW
    ) {
        mTvTitle?.setText(resId)
        initTitle(isBack, iconState)
    }


    protected fun initTitle(
        title: String,
        isBack: Boolean = true,
        iconState: MaterialMenuDrawable.IconState = MaterialMenuDrawable.IconState.ARROW
    ) {
        mTvTitle?.text = title
        initTitle(isBack, iconState)
    }

    private fun initTitle(
        isBack: Boolean = true,
        iconState: MaterialMenuDrawable.IconState = MaterialMenuDrawable.IconState.ARROW
    ) {
        setSupportActionBar(mToolbar)
        supportActionBar?.title = ""
        if (isBack) {
            val materialMenu = MaterialMenuDrawable(
                this,
                UIUtil.getColor(R.color.grey_979797),
                MaterialMenuDrawable.Stroke.THIN
            )
            materialMenu.iconState = iconState
            //???????????????????????????
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //?????????????????????
            supportActionBar?.setHomeAsUpIndicator(materialMenu)
            //????????????
            mToolbar.setNavigationOnClickListener {
                finish()
            }
            //??????????????????????????????
            mIsSwipeEnabled = true
        } else {
            //?????????????????????
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            mIsSwipeEnabled = false
        }
    }


    /**
     * ??????????????????
     */
    protected fun setTvRight(resId: Int, click: (() -> Unit)?) {
        hideTitleBarRight()
        mTvTitleRight?.setText(resId)
        mTvTitleRight?.visibility = View.VISIBLE
        RxViewUtils.clicks(mTvTitleRight, this)
            .subscribe { click?.invoke() }
    }

    /**
     * ????????????????????????????????????
     */
    protected fun setTvIcRight(resId: Int, icRes: Int, click: (() -> Unit)?) {
        hideTitleBarRight()
        mLlTitleRight?.visibility = View.VISIBLE
        val drawable = resources.getDrawable(icRes)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        mTvIcRight.setCompoundDrawables(null, drawable, null, null)
        mTvIcRight.compoundDrawablePadding = DisplayUtil.dp2px(5)
        mTvIcRight.setText(resId)
        RxViewUtils.clicks(mTvIcRight, this)
            .subscribe { click?.invoke() }
    }

    protected abstract val resLayoutId: Int

    companion object {
        val TAG = this.javaClass.simpleName
    }
}