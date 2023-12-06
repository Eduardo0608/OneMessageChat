package br.edu.ifsp.ads.pdm.aulas.onemessagechat.model

interface ChatDao {
    fun createChat(chat: Chat): Int
    fun readChat(id: Int): Chat?
    fun readAllChats(): MutableList<Chat>
    fun updateChat(chat: Chat): Int
}