package com.vdzon.java

import com.vdzon.java.ui.MainUI
import com.vdzon.java.ui.MainWeb

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val OS = System.getProperty("os.name").toLowerCase()
        val schaakbord = !OS.contains("mac")
//        if (schaakbord) {
//            MainUI.main(args)
//        }
        MainWeb().start(schaakbord)
    }
}
