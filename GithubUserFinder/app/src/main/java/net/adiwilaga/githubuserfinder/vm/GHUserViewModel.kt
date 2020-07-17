package net.adiwilaga.githubuserfinder.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.adiwilaga.githubuserfinder.data.dataobject.BaseResponse
import net.adiwilaga.githubuserfinder.repo.GHUserRepository
import net.adiwilaga.githubuserfinder.data.dataobject.GHUser
import net.adiwilaga.githubuserfinder.repo.GHUserListListener

class GHUserViewModel : ViewModel() {

    var isloading:MutableLiveData<Boolean> = MutableLiveData(false)
    var errormessage:MutableLiveData<String> = MutableLiveData("")


    private var _count=0
    private val _query : MutableLiveData<String> = MutableLiveData()
    private val _page : MutableLiveData<Int> = MutableLiveData(1)


    var users:MutableLiveData<List<GHUser>> = MutableLiveData(ArrayList())
    private var _users:MutableLiveData<MutableList<GHUser>> = MutableLiveData(ArrayList())


    private fun getUsers(){
        isloading.value=true
        GHUserRepository.getUsers(_query.value!!,_page.value!!,object :GHUserListListener{
            override fun onSuccess(userss: BaseResponse<GHUser>) {
                isloading.value=false

                _count=userss.totalCount
                if((_page.value!!)==1){
                    _users.value!!.clear()
                }
                    _users.value!!.addAll(userss.items)
                    users.value=_users.value
            }

            override fun onError(err: String) {
                if((_page.value!!)>1){
                    _page.value = _page.value?.minus(1)
                }
                isloading.value=false
                errormessage.value=err
            }
        })


    }

    fun NextPage(){
        if(users.value!!.size<_count) {
            _page.value = _page.value?.plus(1)
            getUsers()
        }
    }

    fun SearchGHUser(q:String){
        if(!q.isNullOrEmpty() && _query.value!=q && q.length>=3) {
            _page.value=1
            _query.value = q
            getUsers()
        }
    }

}