package com.example.newsapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.local.dao.BookmarkDao
import com.example.newsapp.data.local.entity.BookmarkEntity

@Database(
    entities = [BookmarkEntity::class],
    version = 1,
    exportSchema = true
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao
}