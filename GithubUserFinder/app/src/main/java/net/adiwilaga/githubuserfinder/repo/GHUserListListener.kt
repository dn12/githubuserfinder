package net.adiwilaga.githubuserfinder.repo

import net.adiwilaga.githubuserfinder.data.dataobject.BaseResponse
import net.adiwilaga.githubuserfinder.data.dataobject.GHUser

interface GHUserListListener {

    fun onSuccess(users:BaseResponse<GHUser>)
    fun onError(err:String)

}