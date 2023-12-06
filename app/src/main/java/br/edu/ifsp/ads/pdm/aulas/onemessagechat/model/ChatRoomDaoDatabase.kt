package br.edu.ifsp.ads.pdm.aulas.onemessagechat.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Chat::class], version = 1)
abstract class ChatRoomDaoDatabase : RoomDatabase() {
    abstract fun getChatRoomDao(): ChatRoomDao
}