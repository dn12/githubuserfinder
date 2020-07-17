package net.adiwilaga.githubuserfinder.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.adiwilaga.githubuserfinder.R
import net.adiwilaga.githubuserfinder.ui.adapter.GHUserListAdapter
import net.adiwilaga.githubuserfinder.ui.adapter.OnBottomReachListener
import net.adiwilaga.githubuserfinder.vm.GHUserViewModel

class MainActivity : AppCompatActivity() {
    lateinit var ctx:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ctx=this
        initializeUi()
    }


    private fun initializeUi() {

        val viewModel = ViewModelProvider(this).get(GHUserViewModel::class.java)

        rvuser.layoutManager=LinearLayoutManager(this)

        val adp = GHUserListAdapter(viewModel.users.value!!, ctx, object :OnBottomReachListener{
            override fun OnBottomReached(pos: Int) {
                viewModel.NextPage()
            }
        })
        rvuser.adapter = adp
         viewModel.users.observe(this, Observer {
                adp.items=it
                adp.notifyDataSetChanged()
         })




        btsearch.setOnClickListener{
            viewModel.SearchGHUser(etsearch.text.toString())
        }


        viewModel.isloading.observe(this, Observer {
            if(it)
                progressbar.visibility= View.VISIBLE
            else
                progressbar.visibility= View.INVISIBLE
        })

        viewModel.errormessage.observe(this, Observer {
            if(it.length>0){
                Toast.makeText(ctx,it,Toast.LENGTH_LONG).show()
            }
        })



    }
}