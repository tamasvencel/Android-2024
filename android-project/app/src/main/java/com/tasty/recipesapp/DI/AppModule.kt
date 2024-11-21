package com.tasty.recipesapp.DI

import android.app.Application
import androidx.room.Room
import com.tasty.recipesapp.RoomDatabase.RecipeDatabase
import com.tasty.recipesapp.dao.RecipeDao
import com.tasty.recipesapp.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application):
            RecipeDatabase {
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            "recipe_database"
        ).build()
    }

    @Provides
    fun provideRecipeDao(database: RecipeDatabase):
            RecipeDao {
        return database.recipeDao()
    }

    @Provides
    fun provideRecipeRepository(recipeDao: RecipeDao):
            RecipeRepository {
        return RecipeRepository(recipeDao)
    }
}