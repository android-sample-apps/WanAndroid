package com.mmp.wanandroid.ui.base

//import com.mmp.wanandroid.room.MyRoomDatabase

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.mmp.wanandroid.R
import com.mmp.wanandroid.ui.base.callback.EmptyCallback
import com.mmp.wanandroid.ui.base.callback.ErrorCallback
import com.mmp.wanandroid.ui.base.callback.LoadingCallback
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class MyApplication : Application(),ViewModelStoreOwner {


    private lateinit var mAppViewModelStore: ViewModelStore

    companion object{

        private lateinit var myApplication: MyApplication

        fun getContext(): Context{
            return myApplication
        }

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator{ context,layout ->
                layout.setPrimaryColorsId(R.color.red,R.color.white)
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