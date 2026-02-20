package dev.pranals.coffeeshop.utils.helper

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.text.TextUtils
import com.google.gson.Gson
import dev.pranals.coffeeshop.domain.model.ItemModel
import java.io.File
import java.io.FileOutputStream

class TempDB(appContext: Context) {

    private lateinit var sharedPreferences: SharedPreferences

    private val CART_LIST = "CartList"
    private var lastImagePath: String = ""
    private var DEFAULT_APP_IMAGE_DATA_DIRECTORY: String? = null

    init {
        sharedPreferences = appContext.getSharedPreferences(CART_LIST, Context.MODE_PRIVATE)
    }

    companion object {
        private var instance: TempDB? = null
        fun getInstance(appContext: Context): TempDB {
            if (instance == null) {
                instance = TempDB(appContext)
            }
            return instance!!
        }

        fun isExternalStorageWritable(): Boolean {
            return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
        }

        fun isExternalStorageReadable(): Boolean {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state ||
                    Environment.MEDIA_MOUNTED_READ_ONLY == state
        }

    }

    fun getImage(path: String): Bitmap? {
        var bitmapFromPath: Bitmap? = null
        try {
            bitmapFromPath = BitmapFactory.decodeFile(path)
        } catch (e: Exception) {
            e.printStackTrace();
        }
        return bitmapFromPath
    }

    fun getSavedImagePath() = lastImagePath

    fun putImage(theFolder: String, theImageName: String, theBitmap: Bitmap): String {
        DEFAULT_APP_IMAGE_DATA_DIRECTORY = theFolder
        val mFullPath = setupFullPath(theImageName)

        if (mFullPath.isNotEmpty()) {
            lastImagePath = mFullPath
            saveBitmap(mFullPath, theBitmap)
        }
        return mFullPath
    }

    fun putImageWithFullPath(fullPath: String, theBitmap: Bitmap?): Boolean {
        return !(fullPath.isEmpty() || theBitmap == null) && saveBitmap(fullPath, theBitmap)
    }

    fun setupFullPath(imageName: String): String {
        val mFolder =
            File(Environment.getExternalStorageDirectory(), DEFAULT_APP_IMAGE_DATA_DIRECTORY)
        if (isExternalStorageReadable() && isExternalStorageWritable() && !mFolder.exists()) {
            if (!mFolder.mkdirs()) {
                return ""
            }
        }
        return mFolder.path + '/' + imageName
    }

    fun saveBitmap(fullPath: String, bitmap: Bitmap?): Boolean {
        if (fullPath.isEmpty() || bitmap == null) {
            return false
        }
        var fileCreated = false
        var bitmapCompressed = false
        var streamClosed = false

        try {
            val imageFile = File(fullPath)
            if (imageFile.exists()) {
                if (!imageFile.delete()) {
                    return false
                }
            }

            fileCreated = imageFile.createNewFile()
            val out = FileOutputStream(imageFile)
            bitmapCompressed = bitmap.compress(Bitmap.CompressFormat.PNG, 80, out)
            out.flush()
            out.close()
            streamClosed = true

        } catch (e: Exception) {
            e.printStackTrace()
            streamClosed = false
            bitmapCompressed = false
        }
        return fileCreated && bitmapCompressed && streamClosed
    }

    /**
     * Get int value from SharedPreferences at 'key'. If key not found, return 0
     *
     * @param key SharedPreferences key
     * @return int value at 'key' or 0 if key not found
     */
    fun getInt(key: String) = sharedPreferences.getInt(key, 0)

    /**
     * Get parsed ArrayList of Integers from SharedPreferences at 'key'
     *
     * @param key SharedPreferences key
     * @return ArrayList of Integers
     */
    fun getListInt(key: String): ArrayList<Int> {
        val myList = sharedPreferences.getString(key, "")?.split("‚‗‚")?.toList()
        val newList = ArrayList<Int>()
        myList?.forEach { item ->
            newList.add(item.toInt())
        }
        return newList
    }

    /**
     * Get long value from SharedPreferences at 'key'. If key not found, return 0
     *
     * @param key SharedPreferences key
     * @return long value at 'key' or 0 if key not found
     */
    fun getLong(key: String) = sharedPreferences.getLong(key, 0)

    /**
     * Get float value from SharedPreferences at 'key'. If key not found, return 0
     *
     * @param key SharedPreferences key
     * @return float value at 'key' or 0 if key not found
     */
    fun getFloat(key: String) = sharedPreferences.getFloat(key, 0f)

    /**
     * Get String value from SharedPreferences at 'key'. If key not found, return ""
     *
     * @param key SharedPreferences key
     * @return String value at 'key' or "" (empty String) if key not found
     */
    fun getString(key: String) = sharedPreferences.getString(key, "")

    /**
     * Get double value from SharedPreferences at 'key'. If exception thrown, return 0
     *
     * @param key SharedPreferences key
     * @return double value at 'key' or 0 if exception is thrown
     */
    fun getDouble(key: String) = getString(key)?.toDouble()

    /**
     * Get parsed ArrayList of Double from SharedPreferences at 'key'
     *
     * @param key SharedPreferences key
     * @return ArrayList of Double
     */
    fun getListDouble(key: String): ArrayList<Double> {
        val myList = getString(key)?.split("‚‗‚")?.toList()
        val newList = ArrayList<Double>()
        myList?.forEach {
            newList.add(it.toDouble())
        }
        return newList
    }

    /**
     * Get parsed ArrayList of Integers from SharedPreferences at 'key'
     *
     * @param key SharedPreferences key
     * @return ArrayList of Longs
     */
    fun getListLong(key: String): ArrayList<Long> {
        val myList = getString(key)?.split("‚‗‚")?.toList()
        val newList = ArrayList<Long>()
        myList?.forEach {
            newList.add(it.toLong())
        }
        return newList
    }

    /**
     * Get parsed ArrayList of String from SharedPreferences at 'key'
     *
     * @param key SharedPreferences key
     * @return ArrayList of String
     */
    fun getListString(key: String): List<String>? {
        return sharedPreferences.getString(key, "")?.split("‚‗‚")?.toList()
    }

    /**
     * Get boolean value from SharedPreferences at 'key'. If key not found, return false
     *
     * @param key SharedPreferences key
     * @return boolean value at 'key' or false if key not found
     */
    fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    /**
     * Get parsed ArrayList of Boolean from SharedPreferences at 'key'
     *
     * @param key SharedPreferences key
     * @return ArrayList of Boolean
     */
    fun getListBoolean(key: String): ArrayList<Boolean>? {
        val myList = getListString(key)
        val newList = ArrayList<Boolean>()
        myList?.forEach {
            if (it == "true") {
                newList.add(true)
            } else {
                newList.add(false)
            }
        }
        return newList
    }


    //put methods

    fun getListObject(key: String): ArrayList<ItemModel> {
        val gson = Gson()
        val playerList = ArrayList<ItemModel>()
        getListString(key)?.forEach {
            val player = gson.fromJson(it, ItemModel::class.java)
            playerList.add(player)
        }
        return playerList
    }

    fun <T> getObject(key: String?, classOfT: Class<T?>): T? {
        val json: String = getString(key!!)!!
        val value: T & Any = Gson().fromJson<T?>(json, classOfT)!!
        return value as T
    }

    fun checkForNullKey(key: String) {
        if (key == null) throw NullPointerException()
    }

    fun putInt(key: String, value: Int) {
        checkForNullKey(key)
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun PutListInt(key: String, intList: ArrayList<Int>) {
        checkForNullKey(key)
        val mIntList = intList.toArray()
        sharedPreferences.edit().putString(key, mIntList.joinToString("‚‗‚")).apply()
    }

    fun putLong(key: String, value: Long) {
        checkForNullKey(key)
        sharedPreferences.edit().putLong(key, value)
            .apply()
    }

    fun putListLong(key: String, longList: ArrayList<Long>) {
        checkForNullKey(key)
        val myList = longList.toTypedArray<Long>()
        sharedPreferences.edit().putString(key, myList.joinToString("‚‗‚")).apply()
    }

    fun putFloat(key: String, value: Float) {
        checkForNullKey(key)
        sharedPreferences.edit().putFloat(key, value)
            .apply()
    }

    fun putDouble(key: String, value: Double) {
        checkForNullKey(key)
        putString(key, value.toString())
    }

    fun putListDouble(key: String, doubleList: ArrayList<Double>) {
        checkForNullKey(key)
        val myStringList = doubleList.toTypedArray<Double?>()
        sharedPreferences.edit().putString(key, myStringList.joinToString("‚‗‚")).apply()
    }

    /**
     * Put String value into SharedPreferences with 'key' and save
     *
     * @param key   SharedPreferences key
     * @param value String value to be added
     */
    fun putString(key: String, value: String) {
        checkForNullKey(key)
        checkNotNull(value)
        sharedPreferences.edit().putString(key, value)
            .apply()
    }

    /**
     * Put ArrayList of String into SharedPreferences with 'key' and save
     *
     * @param key        SharedPreferences key
     * @param stringList ArrayList of String to be added
     */
    fun putListString(key: String, stringList: java.util.ArrayList<String?>) {
        checkForNullKey(key)
        val myStringList = stringList.toTypedArray<String?>()
        sharedPreferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply()
    }

    /**
     * Put boolean value into SharedPreferences with 'key' and save
     *
     * @param key   SharedPreferences key
     * @param value boolean value to be added
     */
    fun putBoolean(key: String, value: Boolean) {
        checkForNullKey(key)
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    /**
     * Put ArrayList of Boolean into SharedPreferences with 'key' and save
     *
     * @param key      SharedPreferences key
     * @param boolList ArrayList of Boolean to be added
     */
    fun putListBoolean(key: String, boolList: java.util.ArrayList<Boolean>) {
        checkForNullKey(key)
        val newList = java.util.ArrayList<String?>()

        for (item in boolList) {
            if (item) {
                newList.add("true")
            } else {
                newList.add("false")
            }
        }

        putListString(key, newList)
    }

    /**
     * Put ObJect any type into SharedPrefrences with 'key' and save
     *
     * @param key SharedPreferences key
     * @param obj is the Object you want to put
     */
    fun putObject(key: String, obj: Any?) {
        checkForNullKey(key)
        val gson = Gson()
        putString(key, gson.toJson(obj))
    }

    fun putListObject(key: String, playerList: ArrayList<ItemModel>) {
        checkForNullKey(key)
        val gson = Gson()
        val objStrings = java.util.ArrayList<String?>()
        for (player in playerList) {
            objStrings.add(gson.toJson(player))
        }
        putListString(key, objStrings)
    }

    /**
     * Remove SharedPreferences item with 'key'
     *
     * @param key SharedPreferences key
     */
    fun remove(key: String?) {
        sharedPreferences.edit().remove(key).apply()
    }

    /**
     * Delete image file at 'path'
     *
     * @param path path of image file
     * @return true if it successfully deleted, false otherwise
     */
    fun deleteImage(path: String): Boolean {
        return File(path).delete()
    }

    /**
     * Clear SharedPreferences (remove everything)
     */
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    /**
     * Retrieve all values from SharedPreferences. Do not modify collection return by method
     *
     * @return a Map representing a list of key/value pairs from SharedPreferences
     */
    fun getAll(): MutableMap<String?, *>? {
        return sharedPreferences.getAll()
    }

    /**
     * Register SharedPreferences change listener
     *
     * @param listener listener object of OnSharedPreferenceChangeListener
     */
    fun registerOnSharedPreferenceChangeListener(
        listener: OnSharedPreferenceChangeListener?,
    ) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    /**
     * Unregister SharedPreferences change listener
     *
     * @param listener listener object of OnSharedPreferenceChangeListener to be unregistered
     */
    fun unregisterOnSharedPreferenceChangeListener(
        listener: OnSharedPreferenceChangeListener?,
    ) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     *
     * @param value the pref value to check
     */
    private fun checkForNullValue(value: String) {
        if (value == null) {
            throw java.lang.NullPointerException()
        }
    }

}
