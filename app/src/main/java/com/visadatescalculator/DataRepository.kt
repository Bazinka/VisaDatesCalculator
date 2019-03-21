package com.visadatescalculator

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.visadatescalculator.database.MainDatabase
import com.visadatescalculator.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository internal constructor(private val context: Context) {

    @WorkerThread
    suspend fun insertPerson(person: Person) {
        withContext(Dispatchers.IO) {
            MainDatabase.getDatabase(context).personDao().insertPerson(person)
        }
    }

    fun getAllPersons(): LiveData<List<Person>> {
        return MainDatabase.getDatabase(context).personDao().getAll()
    }
//
//
//    fun loadComments(productId: Int): LiveData<List<CommentEntity>> {
//        return mDatabase.commentDao().loadComments(productId)
//    }
//
//    fun searchProducts(query: String): LiveData<List<ProductEntity>> {
//        return mDatabase.productDao().searchAllProducts(query)
//    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getDataRepository(context: Context): DataRepository {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val tempInstance = DataRepository(context)
                instance = tempInstance
                return tempInstance
            }
        }
    }
}