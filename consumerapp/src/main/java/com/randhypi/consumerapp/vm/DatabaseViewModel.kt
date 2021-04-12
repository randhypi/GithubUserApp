package com.randhypi.consumerapp.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.randhypi.consumerapp.data.UserSourceData
import com.randhypi.consumerapp.model.User
import com.randhypi.consumerapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers


class DatabaseViewModel(private val context: Application, private val userRepository: UserRepository) :
    ViewModel() {

        val userFavorite: LiveData<ArrayList<User>> = liveData{
          emit(ArrayList(userRepository.getUserFavorite().toList()))
        }


}

class DatabaseViewModelFactory(private val application: Application): ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DatabaseViewModel::class.java)) {
            val source =
                UserSourceData(application.contentResolver,application.applicationContext)
            DatabaseViewModel(application, UserRepository(source, Dispatchers.IO,application.contentResolver,application.applicationContext)) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}