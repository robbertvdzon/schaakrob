package com.vdzon.java

import com.vdzon.java.ui.MainWeb

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val remote = System.getenv()["SCHAAKROBOT_REMOTE"]?:"false"
        println("remote=$remote")
        val schaakbord = !remote.contains("true")
//        if (schaakbord) {
//            MainUI.main(args)
//        }
        MainWeb().start(schaakbord)
    }
}
