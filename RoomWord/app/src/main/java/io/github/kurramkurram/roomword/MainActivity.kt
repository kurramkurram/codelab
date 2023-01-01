package io.github.kurramkurram.roomword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.kurramkurram.roomword.data.Word
import io.github.kurramkurram.roomword.viewmodel.WordViewModel
import io.github.kurramkurram.roomword.viewmodel.WordViewModelFactory

class MainActivity : AppCompatActivity() {

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    private val callback = ActivityResultCallback<ActivityResult> { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_SHORT).show()
        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), callback
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = WordListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        wordViewModel.allWords.observe(this, Observer { word ->
            word?.let { adapter.submitList(it) }
        })

        findViewById<FloatingActionButton>(R.id.fab).apply {
            setOnClickListener {
                Intent(this@MainActivity, NewWordActivity::class.java).apply {
                    launcher.launch(this)
                }
            }
        }
    }
}
