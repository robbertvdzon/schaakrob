package com.vdzon.java.schaakspel

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Piece
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import com.vdzon.java.robitapi.RobotAansturing
import java.lang.RuntimeException


class BuildBoardThread(private val schaakspel: Schaakspel) {

    private var enabled: Boolean = false

    fun restoreBoard(){
        schaakspel.setTargetBoard(Board())
        enabled = true
    }

    fun loadFen(){
        val board = Board()
//        board.loadFromFen("r5rk/5p1p/5R2/4B3/8/8/7P/7K w")
//        board.loadFromFen("r1bqkb1r/pp1ppp1p/5np1/8/8/2P5/PP2PPPP/RNnQKBNR b KQkq - 0 5")
        board.loadFromFen("r1bq2r1/b4pk1/p1pp1p2/1p2pP2/1P2P1PB/3P4/1PPQ2P1/R3K2R w")
        schaakspel.setTargetBoard(board)
        enabled = true
    }

    fun cancel(){
        enabled = false
    }

    fun startThread(){
        val thread = Thread {
            while (true) {
                if (enabled) {
                    println("Find move to restore")
                    val finished = schaakspel.newMoveToGetToTargetBoard()
                    if (finished) {
                        println("Board is restored")
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
