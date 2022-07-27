package com.milk.inputpanel.keyboardpanel

import android.app.Activity
import android.content.Context
import android.os.*
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class KeyBoardPanel {
    /** 显示键盘 */
    fun showKeyboard(view: View) {
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        val imm = getInputMethodManager(view.context)
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED,
            object : ResultReceiver(Handler(Looper.getMainLooper())) {
                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    if (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN
                        || resultCode == InputMethodManager.RESULT_HIDDEN
                    ) {
                        toggleSoftInput(view, imm, true)
                    }
                }
            })
    }

    /** 隐藏键盘键盘 */
    fun hideKeyboard(activity: Activity) {
        var view = activity.currentFocus
        if (view == null) {
            val decorView = activity.window.decorView
            val focusView = decorView.findViewWithTag<View>("keyboardTagView")
            if (focusView == null) {
                view = EditText(activity)
                view.tag = "keyboardTagView"
                (decorView as ViewGroup).addView(view, 0, 0)
            } else view = focusView
            view.requestFocus()
        }
        hideKeyboard(view)
    }

    /** 隐藏键盘键盘 */
    fun hideKeyboard(view: View) {
        view.isFocusable = false
        view.isFocusableInTouchMode = false
        view.clearFocus()
        val imm = getInputMethodManager(view.context)
        imm.hideSoftInputFromWindow(view.windowToken, 0, object : ResultReceiver(Handler()) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN
                    || resultCode == InputMethodManager.RESULT_SHOWN
                ) {
                    toggleSoftInput(view, imm, false)
                }
            }
        })
    }

    /** 使用 view.getWindowToken()、此view需要被添加到布局里才行、否则不能关闭软键盘 */
    private fun toggleSoftInput(view: View, imm: InputMethodManager, showKeyBoard: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (showKeyBoard)
                imm.showSoftInput(view, 0)
            else
                imm.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            if (showKeyBoard)
                imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY)
            else
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

    /** 获取 InputMethodManager 对象 */
    private fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
    }
}