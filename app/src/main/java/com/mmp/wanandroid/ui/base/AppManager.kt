package com.mmp.wanandroid.ui.base

import android.app.Activity
import java.lang.Exception
import java.util.*


/**
 * activity堆栈管理模式
 */


class AppManager private constructor(){

    fun addActivity(activity: Activity){
        activityStack.add(activity)
    }

    fun removeActivity(activity: Activity){
        activityStack.remove(activity)
    }

    /**
     * 获取当前Activity
     */
    fun currentActivity(): Activity{
        return activityStack.lastElement()
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity){
        if (activity != null){
            if (!activity.isFinishing){
                activity.finish()
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    fun finishAllActivity(){
        for (activity in activityStack){
            finishActivity(activity)
        }
    }


    /**
     * 退出应用
     */
    fun appExit(){
        try {
            finishAllActivity()
//            杀死进程
            android.os.Process.killProcess(android.os.Process.myPid())
        }catch (e: Exception){
            activityStack.clear()
            e.printStackTrace()
        }
    }

    companion object{

        var activityStack: Stack<Activity> = Stack()

        val instance: AppManager by lazy { AppManager() }
    }
}