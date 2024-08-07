package com.awesome.devtest.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.awesome.devtest.R
import com.awesome.devtest.datamodels.RecentLink
import com.bumptech.glide.Glide

class RecentLinksAdapter(private var recentLinks: List<RecentLink>) :
    RecyclerView.Adapter<RecentLinksAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.icon)
        val title: TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)
        val clicks: TextView = itemView.findViewById(R.id.clicks)
        val url: TextView = itemView.findViewById(R.id.url)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recentLink = recentLinks[position]

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(recentLink.original_image)

            .into(holder.icon)

        holder.title.text = recentLink.title
        holder.date.text = recentLink.times_ago
        holder.clicks.text = recentLink.total_clicks.toString()+"\nclicks"
        holder.url.text = recentLink.smart_link
    }

    override fun getItemCount(): Int {
        return recentLinks.size
    }

    fun updateData(newLinks: List<RecentLink>) {
        this.recentLinks = newLinks
        notifyDataSetChanged()
    }
}
