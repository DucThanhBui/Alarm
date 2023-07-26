package com.example.alarmapp.viewmodel

import android.content.ClipData
import android.provider.SyncStateContract.Helpers.update
import android.widget.TextClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.data.AlarmItem
import com.example.alarmapp.data.AlarmItemDao
import com.example.alarmapp.fragment.AddAlarmFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all items.
 *
 */
class ShareViewModel(private val alarmDao: AlarmItemDao) : ViewModel() {

    /**
     * Inserts the new Item into database.
     */
    fun addNewAlarm(newItem: AlarmItem) {
        insertItem(newItem)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(item: AlarmItem) {
        viewModelScope.launch {
            alarmDao.insert(item)
        }
    }

    /**
     * Updates an existing Item in the database.
     */
    fun updateAlarm(item: AlarmItem, newItem: AlarmItem) {
        updateItem(newItem)
    }

    private fun updateItem(item: AlarmItem) {
        viewModelScope.launch {
            alarmDao.update(item)
        }
    }

    /**
     * Launching a new coroutine to delete an item in a non-blocking way
     */
    fun deleteItem(item: AlarmItem) {
        viewModelScope.launch {
            alarmDao.delete(item)
        }
    }

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveItem(id: Int): Flow<AlarmItem> {
        return alarmDao.getItem(id)
    }

    fun getAllItems(): Flow<List<AlarmItem>> = alarmDao.getAllItems()

}
/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class ShareViewModelFactory(private val alarmDao: AlarmItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShareViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShareViewModel(alarmDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}