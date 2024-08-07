package com.awesome.devtest.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.awesome.devtest.R
import com.awesome.devtest.datamodels.TopLink
import com.bumptech.glide.Glide

class DashboardAdapter(private var topLinks: List<TopLink>) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topLink = topLinks[position]
        holder.bind(topLink)
    }

    override fun getItemCount(): Int {
        return topLinks.size
    }

    fun updateTopLinks(newTopLinks: List<TopLink>) {
        topLinks = newTopLinks
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemIcon: ImageView = itemView.findViewById(R.id.item_icon)
        private val itemText: TextView = itemView.findViewById(R.id.item_text)
        private val itemLabel: TextView = itemView.findViewById(R.id.item_label)

        fun bind(topLink: TopLink) {
            // Use Glide to load the image
            Glide.with(itemView.context)
                .load(topLink.thumbnail)  // assuming `thumbnail` is the URL or resource
                .placeholder(R.drawable.ic_launcher_background)  // placeholder while loading
                .error(R.drawable.ic_launcher_background)  // error image if fail to load
                .into(itemIcon)

            itemText.text = topLink.title
            itemLabel.text = topLink.app
        }
    }
}
