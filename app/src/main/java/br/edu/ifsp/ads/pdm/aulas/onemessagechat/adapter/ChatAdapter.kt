package br.edu.ifsp.ads.pdm.aulas.onemessagechat.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.br.edu.ifsp.ads.pdm.aulas.onemessagechat.databinding.TileChatBinding
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Chat
import java.br.edu.ifsp.ads.pdm.aulas.onemessagechat.R

class ChatAdapter(
    context: Context,
    private val chatList: MutableList<Chat>
) : ArrayAdapter<Chat>(context, R.layout.tile_chat, chatList) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentChat = chatList[position]
        var chatTileView = convertView
        val holder: TileChatHolder

        if (chatTileView == null) {
            val binding = TileChatBinding.inflate(inflater, parent, false)
            chatTileView = binding.root
            holder = TileChatHolder(binding.idTv, binding.messageTv, binding)
            chatTileView.tag = holder
        } else {
            holder = chatTileView.tag as TileChatHolder
        }

        holder.userIdTv.text = currentChat.id.toString()
        holder.messagePreviewTv.text = currentChat.message

        return chatTileView
    }
}

private class TileChatHolder(
    val userIdTv: TextView,
    val messagePreviewTv: TextView,
    private val binding: TileChatBinding
) {
    init {
        // Set maxLines and ellipsize only once if not dynamic
        messagePreviewTv.maxLines = 1
        messagePreviewTv.ellipsize = TextUtils.TruncateAt.END
    }
}
