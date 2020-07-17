package net.adiwilaga.githubuserfinder.ui.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.adiwilaga.githubuserfinder.R
import net.adiwilaga.githubuserfinder.data.dataobject.GHUser

class GHUserListAdapter(var items: List<GHUser>, val context: Context, val lst:OnBottomReachListener) : RecyclerView.Adapter<GHUserViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GHUserViewHolder {
        return GHUserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: GHUserViewHolder, position: Int) {

        if(position==itemCount-1){
            lst.OnBottomReached(position)
        }

        val u=items.get(position)
        holder.tname?.text=u.login
        Glide.with(context).load(u.avatarUrl).into(holder.imgava)

    }


}
class GHUserViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val imgava = view.findViewById<ImageView>(R.id.imgava)
    val tname=view.findViewById<TextView>(R.id.tname)

}

interface OnBottomReachListener{
     fun OnBottomReached(pos:Int)
}
