package com.vdzon.java.schaakspel

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Piece
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import com.vdzon.java.robitapi.RobotAansturing


class Schaakspel(private val robotAansturing: RobotAansturing) {

    private var board = Board()
    private var player = "w"
    private var possibleRestPlaces = mutableListOf("A20","B20","C20", "D20")

    fun reset(): ChessBoard {
        board = Board()
        player = "w"
        possibleRestPlaces = mutableListOf("A20","B20","C20", "D20")
        println(board.getFen())
        printBoard()
        robotAansturing.homeHor()
        robotAansturing.homeVert()
        return toBoard()
    }

    fun load(): ChessBoard {
        return toBoard()
    }

    fun computermove(): ChessBoard {
        println("Coputer move")
        println(board.getFen())
        val move = ComputerPlayer.getMove(board.getFen())
        robotMove(move.from.value(), move.to.value())
        board.doMove(move)
        printBoard()
        changePlayer()
        return toBoard()
    }

    fun ownmove(van: String, naar: String): ChessBoard {
        println("SCHAAK: " + van + " ->" + naar)

        val fromValue = Square.fromValue(van)
        val naarValue = Square.fromValue(naar)
        robotMove(van, naar)
        board.doMove(Move(fromValue, naarValue))
        changePlayer()
        println(board.getFen())
        printBoard()

        toBoard()
        return toBoard()
    }

    private fun robotMove(van: String, to:String){
        println("robot from:$van to:$to")

        val toPiece = board.getPiece(Square.fromValue(to))
        if (toPiece!=Piece.NONE){
            robotAansturing.movetoVlak(to,0)
            robotAansturing.clamp1()
            val restPlace = possibleRestPlaces.first()
            possibleRestPlaces.remove(restPlace)
            robotAansturing.movetoVlak(restPlace,0)
            robotAansturing.release1()
        }
        // A20

        robotAansturing.movetoVlak(van,0)
        robotAansturing.clamp1()
        robotAansturing.movetoVlak(to,0)
        robotAansturing.release1()
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