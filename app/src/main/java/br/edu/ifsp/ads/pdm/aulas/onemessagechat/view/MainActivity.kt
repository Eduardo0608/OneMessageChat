package br.edu.ifsp.ads.pdm.aulas.onemessagechat.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.adapter.ChatAdapter
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.controller.ChatRoomController
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Chat
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Constants.CHAT_ARRAY
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Constants.EXTRA_CHAT
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Constants.VIEW_CHAT
import java.br.edu.ifsp.ads.pdm.aulas.onemessagechat.R
import java.br.edu.ifsp.ads.pdm.aulas.onemessagechat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // ViewBinding
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data Source
    private val chatList: MutableList<Chat> = mutableListOf()
    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter(this, chatList)
    }
    private val chatController: ChatRoomController by lazy {
        ChatRoomController(this)
    }

    companion object {
        const val GET_CHATS_MSG = 1
        const val GET_CHATS_INTERVAL = 2000L
    }

    val updateChatListHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            msg.data.getParcelableArray(CHAT_ARRAY)?.also { chatArray ->

                chatList.clear()
                chatArray.forEach {
                    chatList.add(it as Chat)
                }

                chatAdapter.notifyDataSetChanged()
            }
        }
    }

    private lateinit var chatResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarIn.toolbar)
        binding.chatsLv.adapter = chatAdapter

        chatResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val chat = result.data?.getParcelableExtra<Chat>(EXTRA_CHAT)
                chat?.let { _chat ->
                    if (chatList.any { it.id == chat.id }) {
                        chatController.editChat(_chat)
                    } else {
                        chatController.insertChat(_chat)
                    }
                }
            }
        }

        binding.chatsLv.setOnItemClickListener { parent, view, position, id ->
            val chat = chatList[position]
            val viewChatIntent = Intent(this, ChatActivity::class.java).apply {
                putExtra(EXTRA_CHAT, chat)
                putExtra(VIEW_CHAT, true)
            }
            startActivity(viewChatIntent)
        }

        registerForContextMenu(binding.chatsLv)
        updateChatListHandler.apply {
            sendMessageDelayed(
                obtainMessage().apply { what = GET_CHATS_MSG }, GET_CHATS_INTERVAL
            )
        }
        chatController.getAllChats()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.createChatMi -> {
                chatResultLauncher.launch(Intent(this, ChatActivity::class.java))
                true
            }

            else -> true
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position

        return when (item.itemId) {
            R.id.editChatMi -> {
                val chatToEdit = chatList[position]
                val editChatIntent = Intent(this, ChatActivity::class.java).apply {
                    putExtra(EXTRA_CHAT, chatToEdit)
                }
                chatResultLauncher.launch(editChatIntent)
                true
            }

            else -> true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(binding.chatsLv)
    }
}