package br.edu.ifsp.ads.pdm.aulas.onemessagechat.controller

import android.os.Message
import androidx.room.Room
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Chat
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.ChatRoomDao
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.ChatRoomDao.Companion.CHAT_DATABASE_FILE
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.ChatRoomDaoDatabase
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Constants.CHAT_ARRAY
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.view.MainActivity

class ChatRoomController(private val mainActivity: MainActivity) {
    private val chatDao: ChatRoomDao by lazy {
        Room.databaseBuilder(
            mainActivity,
            ChatRoomDaoDatabase::class.java,
            CHAT_DATABASE_FILE
        ).build().getChatRoomDao()
    }

    fun insertChat(chat: Chat) {
        Thread {
            chatDao.createChat(chat)
            getAllChats()
        }.start()
    }

    fun getChat(id: Int) = chatDao.readChat(id)

    fun getAllChats() {
        Thread {
            val returnList = chatDao.readAllChats()

            mainActivity.updateChatListHandler.apply {
                sendMessage(Message().apply {
                    data.putParcelableArray(
                        CHAT_ARRAY,
                        returnList.toTypedArray()
                    )
                })
            }

        }.start()
    }

    fun editChat(chat: Chat) {
        Thread {
            chatDao.updateChat(chat)
            getAllChats()
        }.start()
    }
}
