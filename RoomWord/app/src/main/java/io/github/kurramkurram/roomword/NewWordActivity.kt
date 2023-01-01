package io.github.kurramkurram.roomword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewWordActivity : AppCompatActivity() {

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editText = findViewById(R.id.edit_word)

        val button = findViewById<Button>(R.id.button_save).apply {
            setOnClickListener {
                val replyIntent = Intent().apply {
                    if (TextUtils.isEmpty(editText.text)) {
                        setResult(RESULT_CANCELED, this)
                    } else {
                        val word = editText.text.toString()
                        putExtra(EXTRA_REPLY, word)
                        setResult(RESULT_OK, this)
                    }
                }
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}