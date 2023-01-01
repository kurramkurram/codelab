package io.github.kurramkurram.roomword

import android.app.Application
import io.github.kurramkurram.roomword.data.WordRoomDatabase
import io.github.kurramkurram.roomword.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}