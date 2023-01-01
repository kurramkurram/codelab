package io.github.kurramkurram.roomword.repository

import androidx.annotation.WorkerThread
import io.github.kurramkurram.roomword.data.Word
import io.github.kurramkurram.roomword.data.WordDao
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}