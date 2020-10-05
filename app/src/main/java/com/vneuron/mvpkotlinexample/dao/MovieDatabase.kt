package com.vneuron.mvpkotlinexample.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vneuron.mvpkotlinexample.model.*

@Database(entities = [MovieTest::class,Movie::class], version = 7, exportSchema = false)
@TypeConverters(IntegerListTypeConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao():MovieDao
    //abstract fun daoMoviesR():MovieRDao
    abstract fun daoTest2(): Test2Dao

    
    companion object{
        private const val DB_NAME = "movieDb1"
        private var INSTANCE:MovieDatabase? = null
        fun getInstance(context:Context):MovieDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MovieDatabase
        }



    }
}