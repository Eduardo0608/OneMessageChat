package br.edu.ifsp.ads.pdm.aulas.onemessagechat.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.br.edu.ifsp.ads.pdm.aulas.onemessagechat.databinding.ChatActivityBinding
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Chat
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Constants.EXTRA_CHAT
import br.edu.ifsp.ads.pdm.aulas.onemessagechat.model.Constants.VIEW_CHAT
import kotlin.random.Random

class ChatActivity : AppCompatActivity() {
    private val binding: ChatActivityBinding by lazy {
        ChatActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Chat details"

        val receivedChat = intent.getParcelableExtra<Chat>(EXTRA_CHAT)
        receivedChat?.let { _receivedChat ->
            val viewChat: Boolean = intent.getBooleanExtra(VIEW_CHAT, false)
            with(binding) {
                if (viewChat) {
                    messageEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
                val generatedId = _receivedChat.id ?: generateId()
                idTv.text = generatedId.toString()
                messageEt.setText(_receivedChat.message)
            }
        }

        with(binding) {
            val chatId = receivedChat?.id ?: generateId()
            Log.d("chatId", chatId.toString())
            idTv.text = chatId.toString()

            saveBt.setOnClickListener {

                val chat = Chat(
                    id = chatId,
                    message = messageEt.text.toString()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_CHAT, chat)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun generateId() = Random(System.currentTimeMillis()).nextInt()
}