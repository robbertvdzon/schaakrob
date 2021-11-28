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
    private var buildBoardThread = BuildBoardThread(this)

    init{
        buildBoardThread.startThread()
    }


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
//        println(board.getFen())
//        printBoard()
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
//        println(board.getFen())
        val move = ComputerPlayer.getMove(board.getFen())
        robotMove(move.from.value(), move.to.value())
        board.doMove(move)
//        printBoard()
        changePlayer()
        return toBoard()
    }

    fun restoreBoard(){
        buildBoardThread.restoreBoard()
    }

    fun loadFen(){
        buildBoardThread.loadFen()
    }

    fun setTargetBoard(board: Board){
        targetBoard = board
        printBoard(targetBoard)
    }


    fun newMoveToGetToTargetBoard(): Boolean{
        // 1 stap voor terug naar target pos
        val move = bepaalMoveForTargetBoard()
        if (move!=null) {
            val (van, naar) = move
            ownmove(van, naar)
            return false
        }
        return true


    }

    private fun bepaalMoveForTargetBoard(): Pair<String, String>? {
        for (row in "ABCDEFGH"){
            for (col in "12345678"){
                val pos = "$row$col"
                val square = Square.valueOf(pos)
                val pieceNow = board.getPiece(square)
                val pieceWanted = targetBoard.getPiece(square)

                if (pieceNow==Piece.NONE && pieceWanted!=Piece.NONE){
                    val from= findPieceToRestore(pieceWanted)
                    if (from==null) return null// zou niet mogen gebeuren
                    return Pair(from,pos)
                }
            }
        }

        return null
    }

    private fun findPieceToRestore(piece: Piece): String? {
        // find on board
        for (row in "ABCDEFGH"){
            for (col in "12345678"){
                val pos = "$row$col"
                val square = Square.valueOf(pos)
                val pieceNow = board.getPiece(square)
                val pieceWanted = targetBoard.getPiece(square)

                if (pieceNow!=pieceWanted && pieceNow==piece){
                    return pos
                }
            }
        }

        // find outside board
        for (row in "ABCDEFGH"){
            for (col in listOf("20","21","10","11")){
                val pos = "$row$col"
                if (getPiece(pos)==piece) return pos
            }
        }

        return null
    }

    private fun clearPos(pos: String){
        val blackStoreSquare = blackStoreSquares.firstOrNull { it.pos == pos}
        val whiteStoreSquare = whiteStoreSquares.firstOrNull { it.pos == pos}
        if (blackStoreSquare!=null){
            blackStoreSquare.piece = Piece.NONE
        }
        if (whiteStoreSquare!=null){
            whiteStoreSquare.piece = Piece.NONE
        }
    }


    private fun getPiece(pos: String): Piece {
        val blackStoreSquare = blackStoreSquares.firstOrNull { it.pos == pos}
        val whiteStoreSquare = whiteStoreSquares.firstOrNull { it.pos == pos}
        if (blackStoreSquare!=null){
            return blackStoreSquare.piece
        }
        if (whiteStoreSquare!=null){
            return whiteStoreSquare.piece
        }
        return board.getPiece(Square.fromValue(pos))
    }

    private fun positieBuitenBord(pos: String): Boolean {
        val blackStoreSquare = blackStoreSquares.firstOrNull { it.pos == pos}
        val whiteStoreSquare = whiteStoreSquares.firstOrNull { it.pos == pos}
        return blackStoreSquare!=null || whiteStoreSquare!=null
    }


    fun ownmove(van: String, naar: String): ChessBoard {
        println("SCHAAK: " + van + " ->" + naar)

        if (positieBuitenBord(van)){
            if (getPiece(naar)!=Piece.NONE) throw RuntimeException("Zet van buitenbord kan alleen naar lege plek")
            val naarValue = Square.fromValue(naar)
            val pieceToMove:Piece = getPiece(van)
            robotMove(van, naar)
            println("board.setPiece: " + pieceToMove + " ->" + naarValue)
            board.setPiece(pieceToMove, naarValue)
        }
        else {
            val fromValue: Square = Square.fromValue(van)
            val naarValue = Square.fromValue(naar)
            robotMove(van, naar)
            board.doMove(Move(fromValue, naarValue))
            changePlayer()
        }

//        println(board.getFen())
//        printBoard()
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
            if (van.endsWith("21") ) {// bovenste rij
                robotAansturing.movetoVlak(van, 1)
                robotAansturing.clamp2()
                robotAansturing.movetoVlak(to, 1)
                robotAansturing.release2()
            }
            else{
                robotAansturing.movetoVlak(van, 0)
                robotAansturing.clamp1()
                robotAansturing.movetoVlak(to, 0)
                robotAansturing.release1()
            }
            // als hij van buiten bord komt, dan vlak leegmaken
            if (positieBuitenBord(van)){
                clearPos(van)

            }
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

    private fun printBoard(boardToPrint: Board) {
        println("--------")
        for (col in "87654321") {
            println()
            for (row in "ABCDEFGH") {
                val sq = "" + row + col
                val s = boardToPrint.getPiece(Square.fromValue(sq))
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
