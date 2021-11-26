package com.vdzon.java.schaakspel

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Piece
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import com.vdzon.java.robitapi.RobotAansturing


class Schaakspel(private val robotAansturing: RobotAansturing) {

    private var board = Board()
    private var player = "w"

    fun reset(): ChessBoard {
        board = Board()
        player = "w"
        println(board.getFen())
        printBoard()
        return toBoard()
    }

    fun computermove(): ChessBoard {
        println("Coputer move")
        println(board.getFen())
        val move = ComputerPlayer.getMove(board.getFen())
        board.doMove(move)
        printBoard()
        changePlayer()
        return toBoard()
    }

    fun ownmove(van: String, naar: String): ChessBoard {
        println("SCHAAK: " + van + " ->" + naar)

        val fromValue = Square.fromValue(van)
        val naarValue = Square.fromValue(naar)
//        board.doMove(Move(Square.E2, Square.E4))
        board.doMove(Move(fromValue, naarValue))
        changePlayer()
        println(board.getFen())
        printBoard()

        toBoard()
        return toBoard()
    }

    private fun toBoard(): ChessBoard {
        val list = mutableListOf<SquareField>()
        for (col in "87654321") {
            for (row in "ABCDEFGH") {
                val sq = "" + row + col
                val s = board.getPiece(Square.fromValue(sq))
                list.add(SquareField(sq,s.toLetter()))
            }
        }
        return ChessBoard(list, player)
    }

    private fun printBoard() {
        println("--------")
        for (col in "87654321") {
            println()
            for (row in "ABCDEFGH") {
                val sq = "" + row + col
                val s = board.getPiece(Square.fromValue(sq))
                print(s.toLetter())
            }
        }
        println("--------")
    }

    fun Piece.toLetter(): String {
        return when (this) {
            Piece.WHITE_PAWN -> "p"
            Piece.WHITE_KNIGHT -> "n"
            Piece.WHITE_BISHOP -> "b"
            Piece.WHITE_ROOK -> "r"
            Piece.WHITE_QUEEN -> "q"
            Piece.WHITE_KING -> "k"
            Piece.BLACK_PAWN -> "P"
            Piece.BLACK_KNIGHT -> "N"
            Piece.BLACK_BISHOP -> "B"
            Piece.BLACK_ROOK -> "R"
            Piece.BLACK_QUEEN -> "Q"
            Piece.BLACK_KING -> "K"
            Piece.NONE -> " "
        }
    }

    fun changePlayer(){
        if (player=="w"){
            player = "b"
        }
        else
            player = "w"
    }


}