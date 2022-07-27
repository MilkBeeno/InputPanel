package com.milk.inputpanel

import android.app.Activity
import android.content.Context
import android.view.View
import com.milk.inputpanel.keyboardpanel.KeyBoardPanel
import com.milk.inputpanel.keyboardpanel.KeyBoardSize

/** 输入面板管理器 */
object InputPanelManager {
    private val keyBoardPanel by lazy { KeyBoardPanel() }
    private val keyBoardSize by lazy { KeyBoardSize() }

    fun showKeyBoardPanel(view: View) {
        keyBoardPanel.showKeyboard(view)
    }

    fun hideKeyBoardPanel(view: View) {
        keyBoardPanel.hideKeyboard(view)
    }

    fun hideKeyBoardPanel(activity: Activity) {
        keyBoardPanel.hideKeyboard(activity)
    }

    fun saveKeyBoardHeight(context: Context, height: Int) {
        keyBoardSize.saveKeyBoardHeight(context, height)
    }

    fun getKeyBoardHeight(context: Context, defaultHeight: Int = 0): Int {
        return keyBoardSize.getKeyBoardHeight(context, defaultHeight)
    }
}