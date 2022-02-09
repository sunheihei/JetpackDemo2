package com.example.jetpackdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jetpackdemo.DATABASE_NAME
import com.example.jetpackdemo.bean.GitRepoItem
import com.example.jetpackdemo.bean.Owner

@Database(
    entities = [GitRepoItem::class, Owner::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(OwnerConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gitRepoDao(): GitRepoDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                        }
                    }
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
