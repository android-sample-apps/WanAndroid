package com.mmp.wanandroid.ui.mine

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.data.*
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object MineRepository : BaseRepository(){

    private val wanAndroidService = WanAndroidService.create()

    private var coinPage = 0

    private var collectPage = 0

    suspend fun getLogin(loginLiveData: StateLiveData<User>,username: String,password: String) = executeResp(loginLiveData){
        wanAndroidService.getLogin(username, password)
    }

    suspend fun getRegister(registerLiveData: StateLiveData<Any>,username: String,password: String,repassword: String) = executeResp(StateLiveData()){
        wanAndroidService.getRegister(username, password, repassword)
    }

    suspend fun getUserRank(integralLiveData: StateLiveData<Rank>) = executeResp(integralLiveData){
        wanAndroidService.getUserRank()
    }

    suspend fun getCoinList(coinLiveData: StateLiveData<IntegralData>) = executeResp(coinLiveData){
        coinPage = 0
        wanAndroidService.getCoinList(coinPage)
    }

    suspend fun getCoinMore(coinLiveData: StateLiveData<IntegralData>) = executeResp(coinLiveData){
        coinPage++
        wanAndroidService.getCoinList(coinPage)
    }

    suspend fun getCollectArticle(articlesLiveData: StateLiveData<ArticleData>) = executeResp(articlesLiveData){
        collectPage = 0
        wanAndroidService.getCollectArticle(collectPage)
    }

    suspend fun getArticleMore(articlesLiveData: StateLiveData<ArticleData>) = executeResp(articlesLiveData){
        collectPage++
        wanAndroidService.getCollectArticle(collectPage)
    }



    suspend fun getCollectTools(websLiveData: StateLiveData<List<Web>>) = executeResp(websLiveData){
        wanAndroidService.getCollectTools()
    }

    suspend fun logout(logoutLiveData: StateLiveData<Any>) = executeResp(logoutLiveData){
        wanAndroidService.getLogout()
    }

}