package com.cmt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cmt.services.model.PackagesModel
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemPackageBinding

class BuyPackagesAdapter(val context: Context,var dataResponse: MutableList<PackagesModel>,var click: onPackage) : RecyclerView.Adapter<BuyPackagesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): BuyPackagesAdapter.ViewHolder {
        return ViewHolder(ItemPackageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BuyPackagesAdapter.ViewHolder, position: Int) {
        val model=dataResponse[position]
        with(holder.binding){
           tvPackageName.text= model.packageDes
           tvDesc.text= model.description
           tvRupess.text = "₹ "+model.price
           tvRupess.text= "${model.days} days access"
        }

        holder.itemView.setOnClickListener {
            dataResponse.onEach { it.isClicked=false }
            dataResponse[position].isClicked=true
            click.onPackageClick(dataResponse[position])
            notifyDataSetChanged()
        }

        if(dataResponse[position].isClicked){
            holder.binding.llbg.background = ContextCompat.getDrawable(holder.binding.llbg.context,R.drawable.bg_selected)
        }else{
            holder.binding.llbg.background = ContextCompat.getDrawable(holder.binding.llbg.context,R.drawable.bg_unselected)

        }
    }

    override fun getItemCount(): Int {
        return dataResponse.size
    }

    class ViewHolder(var binding: ItemPackageBinding): RecyclerView.ViewHolder(binding.root)
}

interface onPackage{
    fun onPackageClick(model: PackagesModel)
}