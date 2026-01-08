package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.services.model.SampleVideosModel
import com.cmt.view.activity.PlainActivity
import com.cmt.view.activity.YoutubeVideoPlayActivity
import com.the_pride_ias.databinding.ItemsSampleVideosBinding

class SampleVideoListAdapter(val context: Context, val dataset: MutableList<SampleVideosModel>) :
    RecyclerView.Adapter<SampleVideoListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsSampleVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: SampleVideosModel) {
            binding.model = datamodel
            binding.playBtn.setOnClickListener {
                val intent = Intent(context, YoutubeVideoPlayActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.youtubeId, datamodel.you_tube_video_code)
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsSampleVideosBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
        /*if(position % 2 ==0){
            binder1.layout.setBackgroundColor(context.getColor(R.color.colorEditTextHint))
        }*/
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}