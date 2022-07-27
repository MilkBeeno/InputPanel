package com.milk.inputpanel.keyboardpanel

import android.content.Context
import android.content.SharedPreferences

/** 保存键盘的高度、获取键盘的高度并保存到内存中 */
class KeyBoardSize {
    companion object {
        private const val FILE_NAME = "KEYBOARD.HEIGHT"
        private const val KEY_KEYBOARD_HEIGHT = "SP.KEY.KEYBOARD.HEIGHT"
    }

    private var keyBoardSp: SharedPreferences? = null

    internal fun saveKeyBoardHeight(context: Context, height: Int) {
        getKeyBoardSp(context).edit()
            .putInt(KEY_KEYBOARD_HEIGHT, height)
            .apply()
    }

    internal fun getKeyBoardHeight(context: Context, defaultHeight: Int): Int {
        return getKeyBoardSp(context).getInt(KEY_KEYBOARD_HEIGHT, defaultHeight)
    }

    private fun getKeyBoardSp(context: Context): SharedPreferences {
        if (keyBoardSp == null) {
            synchronized(KeyBoardSize::class.java) {
                if (keyBoardSp == null) {
                    keyBoardSp = context
                        .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                }
            }
        }
        return checkNotNull(keyBoardSp)
    }
}