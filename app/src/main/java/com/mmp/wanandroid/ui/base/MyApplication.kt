package com.mmp.wanandroid.ui.base

import android.R
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.mmp.wanandroid.BuildConfig
import com.mmp.wanandroid.ui.base.callback.EmptyCallback
//import com.mmp.wanandroid.room.MyRoomDatabase
import com.mmp.wanandroid.ui.base.callback.ErrorCallback
import com.mmp.wanandroid.ui.base.callback.LoadingCallback
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import timber.log.Timber


class MyApplication : Application(),ViewModelStoreOwner {


    private lateinit var mAppViewModelStore: ViewModelStore

    companion object{

        private lateinit var myApplication: MyApplication

        fun getContext(): Context{
            return myApplication
        }

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator{ context,layout ->
                layout.setPrimaryColorsId(R.color.holo_red_light,R.color.white)
                ClassicsHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator{ context,_ ->
                ClassicsFooter(context)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        myApplication = this
        LoadSir.beginBuilder()
                .addCallback(LoadingCallback())
                .addCallback(ErrorCallback())
                .addCallback(EmptyCallback())
                .setDefaultCallback(SuccessCallback::class.java)
                .commit()
        mAppViewModelStore = ViewModelStore()

        Timber.plant(Timber.DebugTree())
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }


}