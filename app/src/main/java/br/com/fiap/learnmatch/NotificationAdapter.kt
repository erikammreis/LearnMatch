package br.com.fiap.learnmatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(notificationList: List<NotificationData>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private var notifications: List<NotificationData> = notificationList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notification_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var notification = notifications[position]
        holder.notificationText.text = notification.text
        if (notification.type == "match") {
            holder.notificationIcon.setImageResource(R.drawable.match_icon)
        } else {
            holder.notificationIcon.setImageResource(R.drawable.notifimenugreen)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var notificationText: TextView
        var notificationIcon: ImageView

        init {
            notificationText = itemView.findViewById(R.id.notification_title)
            notificationIcon = itemView.findViewById(R.id.notification_icon)
        }
    }
}
