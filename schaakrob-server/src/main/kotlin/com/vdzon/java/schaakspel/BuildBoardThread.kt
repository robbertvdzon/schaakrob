package com.vdzon.java.schaakspel

import com.github.bhlangonijr.chesslib.Board


class BuildBoardThread(private val schaakspel: Schaakspel) {

    private var enabled: Boolean = false

    fun isBuzy() = enabled

    fun restoreBoard() {
        schaakspel.setTargetBoard(Board())
        enabled = true
    }

    fun loadFen(fen: String) {
        val board = Board()
        board.loadFromFen(fen)
        schaakspel.setTargetBoard(board)
        enabled = true
    }

    fun cancel() {
        enabled = false
    }

    fun startThread() {
        val thread = Thread {
            while (true) {
                if (enabled) {
                    println("Find move to restore")
                        val finished = schaakspel.newMoveToGetToTargetBoard()
                        if (finished) {
                            schaakspel.resetTo(schaakspel.getTargetBoard())
                            println("Board is restored, home")
                            schaakspel.home()
                            enabled = false
                        }
                }
                try {
                    Thread.sleep(500)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()

    }


}
