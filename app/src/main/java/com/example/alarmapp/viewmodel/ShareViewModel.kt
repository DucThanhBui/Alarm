package com.example.alarmapp.viewmodel

import android.content.ClipData
import android.widget.TextClock
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.data.AlarmItem
import com.example.alarmapp.data.AlarmItemDao
import kotlinx.coroutines.launch


/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all items.
 *
 */
class ShareViewModel(private val alarmDao: AlarmItemDao) : ViewModel() {

    /**
     * Inserts the new Item into database.
     */
    fun addNewItem(time: String, sound: String, vibrate: Boolean, repeat: String, delete: Boolean, content: String) {
        val newItem = getNewItemEntry(time, sound, vibrate, repeat, delete, content)
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

//    /**
//     * Returns true if the EditTexts are not empty
//     */
//    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
//        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
//            return false
//        }
//        return true
//    }

    /**
     * Returns an instance of the [Item] entity class with the item info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewItemEntry(time: String, sound: String, vibrate: Boolean, repeat: String, delete: Boolean, content: String): AlarmItem {
        return AlarmItem(
            time = time,
            sound = sound,
            isVibrate = vibrate,
            isRepeat = repeat,
            deleteAfterTrigger = delete,
            content = content
        )
    }



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