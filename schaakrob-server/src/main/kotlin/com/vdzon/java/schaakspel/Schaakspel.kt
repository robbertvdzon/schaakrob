package com.vdzon.java.schaakspel

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Piece
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import com.vdzon.java.robitapi.RobotAansturing
import java.lang.RuntimeException


class Schaakspel(private val robotAansturing: RobotAansturing) {

    private var board = Board()
    private var targetBoard = Board()
    private var player = "w"


    val blackStoreSquares = listOf<StoreSquare>(
        StoreSquare("A21"),
        StoreSquare("B21"),
        StoreSquare("C21"),
        StoreSquare("D21"),
        StoreSquare("E21"),
        StoreSquare("F21"),
        StoreSquare("G21"),
        StoreSquare("H21"),
        StoreSquare("A20"),
        StoreSquare("B20"),
        StoreSquare("C20"),
        StoreSquare("D20"),
        StoreSquare("E20"),
        StoreSquare("F20"),
        StoreSquare("G20"),
        StoreSquare("H20")
    )
    val whiteStoreSquares = listOf<StoreSquare>(
        StoreSquare("A11"),
        StoreSquare("B11"),
        StoreSquare("C11"),
        StoreSquare("D11"),
        StoreSquare("E11"),
        StoreSquare("F11"),
        StoreSquare("G11"),
        StoreSquare("H11"),
        StoreSquare("A10"),
        StoreSquare("B10"),
        StoreSquare("C10"),
        StoreSquare("D10"),
        StoreSquare("E10"),
        StoreSquare("F10"),
        StoreSquare("G10"),
        StoreSquare("H10")
    )

    fun reset(): ChessBoard {
        board = Board()
        player = "w"
        blackStoreSquares.forEach{it.piece = Piece.NONE}
        whiteStoreSquares.forEach{it.piece = Piece.NONE}
        println(board.getFen())
        printBoard()
        return toBoard()
    }

    fun sleep(): ChessBoard {
        robotAansturing.sleep()
        return toBoard()
    }

    fun home(): ChessBoard {
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

//    fun setTargetBoard(board: Board){
//
//    }
//    fun newMoveToGetToTargetBoard(): ChessBoard{
//        // 1 stap voor terug naar tarhet pos
//        val (van, naar) = bepaalMoveForTargetBoard()
//        return ownmove(van, naar)
//
//
//    }

    fun ownmove(van: String, naar: String): ChessBoard {
        println("SCHAAK: " + van + " ->" + naar)

//        if (positieBuitenBord(van)){
//            if (getPiece(naar)!=Piece.NONE) throw RuntimeException("Zet van buitenbord kan alleen naar lege plek")
//            robotMove(van, naar)
//            val naarValue = Square.fromValue(naar)
//            val pieceToMove:Piece = getPiece(van)
//            board.setPiece(pieceToMove, naarValue)
//        }
//        else {
            val fromValue: Square = Square.fromValue(van)
            val naarValue = Square.fromValue(naar)
            robotMove(van, naar)
            board.doMove(Move(fromValue, naarValue))
            changePlayer()
//        }

        println(board.getFen())
        printBoard()
        toBoard()
        return toBoard()
    }

    private fun robotMove(van: String, to:String){
        println("robot from:$van to:$to")

        val toPiece = board.getPiece(Square.fromValue(to))

        if (toPiece!=Piece.NONE){
            // slaan
            if (toPiece.pieceSide==Side.BLACK){
                val storeSquare: StoreSquare = blackStoreSquares.filter { it.piece==Piece.NONE }.first()
                storeSquare.piece = toPiece
                robotAansturing.movetoVlak(van, 0)
                robotAansturing.clamp1()

                robotAansturing.movetoVlak(to,1)
                robotAansturing.clamp2()

                robotAansturing.movetoVlak(to, 0)
                robotAansturing.release1()

                robotAansturing.movetoVlak(storeSquare.pos,1)
                robotAansturing.release2()
            }
            else{
                val storeSquare: StoreSquare = whiteStoreSquares.filter { it.piece==Piece.NONE }.first()
                storeSquare.piece = toPiece
                robotAansturing.movetoVlak(van, 1)
                robotAansturing.clamp2()

                robotAansturing.movetoVlak(to,0)
                robotAansturing.clamp1()

                robotAansturing.movetoVlak(to, 1)
                robotAansturing.release2()

                robotAansturing.movetoVlak(storeSquare.pos,0)
                robotAansturing.release1()
            }
        }
        else {
            robotAansturing.movetoVlak(van, 0)
            robotAansturing.clamp1()
            robotAansturing.movetoVlak(to, 0)
            robotAansturing.release1()
        }
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

data class StoreSquare(val pos: String, var piece: Piece = Piece.NONE)
