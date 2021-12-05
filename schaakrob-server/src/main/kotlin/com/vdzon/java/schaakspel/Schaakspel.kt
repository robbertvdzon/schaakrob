package com.vdzon.java.schaakspel

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Piece
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import com.vdzon.java.robitapi.RobotAansturing
import java.io.File
import java.lang.RuntimeException

class Schaakspel(private val robotAansturing: RobotAansturing) {

    private var board = Board()
    private var targetBoard = Board()
    private var buildBoardThread = BuildBoardThread(this)
    private val mapper = jacksonObjectMapper()

    var initialBlackStoreSquares: List<StoreSquare>
    var initialWhiteStoreSquares: List<StoreSquare>
    lateinit var  blackStoreSquares : List<StoreSquare>
    lateinit var whiteStoreSquares: List<StoreSquare>

    init{

        initialBlackStoreSquares = listOf<StoreSquare>(
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
        initialWhiteStoreSquares = listOf<StoreSquare>(
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

        buildBoardThread.startThread()
        loadBoard(initialBlackStoreSquares, initialWhiteStoreSquares)
    }






    fun reset(): ChessBoard {
        board = Board()
        blackStoreSquares.forEach{it.piece = Piece.NONE.name}
        whiteStoreSquares.forEach{it.piece = Piece.NONE.name}
        saveBoard()
        return toBoard()
    }

    fun resetTo(newBoard: Board): ChessBoard {
        board = newBoard
        saveBoard()
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

    fun moveExtraWhenCastle(move: Move){
        val side= board.getPiece(move.from).pieceSide
        val isKingSideCastle = board.context.isKingSideCastle(move)
        val isQueenSideCastle = board.context.isQueenSideCastle(move)

        val extraMove: Pair<String, String> ?= when {
            side==Side.WHITE && isKingSideCastle -> Pair("H1","F1")
            side==Side.WHITE && isQueenSideCastle -> Pair("A1","D1")
            side==Side.BLACK && isKingSideCastle -> Pair("H8","F8")
            side==Side.BLACK && isQueenSideCastle ->  Pair("A8","D8")
            else -> null
        }
        if (extraMove!=null) {
            println("EXTRA MOVE:"+extraMove)
            robotMove(extraMove.first, extraMove.second)
        }

    }

    fun computermove(): ChessBoard {
        println("Coputer move")
//        println(board.getFen())
        val move = ComputerPlayer.getMove(board.getFen())

        robotMove(move.from.value(), move.to.value())
        moveExtraWhenCastle(move)

        board.doMove(move)
        saveBoard()
//        printBoard()
        return toBoard()
    }

    fun restoreBoard(){
        buildBoardThread.restoreBoard()
    }

    fun loadFen(fen: String){
        buildBoardThread.loadFen(fen)
    }

    fun setTargetBoard(board: Board){
        targetBoard = board
        printBoard(targetBoard)
    }

    fun getTargetBoard(): Board{
        return targetBoard
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
                saveBoard()


                if (pieceNow==Piece.NONE && pieceWanted!=Piece.NONE){
                    val from= findPieceToRestore(pieceWanted)
                    if (from==null) return null// zou niet mogen gebeuren
                    return Pair(from,pos)
                }
            }
        }

        // maak lege plekken
        for (row in "ABCDEFGH"){
            for (col in "12345678"){
                val pos = "$row$col"
                val square = Square.valueOf(pos)
                val pieceNow = board.getPiece(square)
                val pieceWanted = targetBoard.getPiece(square)

                if (pieceNow!=Piece.NONE && pieceWanted==Piece.NONE){
                    if (pieceNow.pieceSide==Side.WHITE){
                        val storeSquare: StoreSquare = whiteStoreSquares.filter { it.piece==Piece.NONE.name }.first()
                        return Pair(pos,storeSquare.pos)
                    }
                    else{
                        val storeSquare: StoreSquare = blackStoreSquares.filter { it.piece==Piece.NONE.name }.first()

                        return Pair(pos,storeSquare.pos)
                    }
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
            blackStoreSquare.piece = Piece.NONE.name
        }
        if (whiteStoreSquare!=null){
            whiteStoreSquare.piece = Piece.NONE.name
        }
    }


    private fun getPiece(pos: String): Piece {
        val blackStoreSquare = blackStoreSquares.firstOrNull { it.pos == pos}
        val whiteStoreSquare = whiteStoreSquares.firstOrNull { it.pos == pos}
        if (blackStoreSquare!=null){
            return Piece.fromValue(blackStoreSquare.piece)
        }
        if (whiteStoreSquare!=null){
            return Piece.fromValue(whiteStoreSquare.piece)
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
            saveBoard()

        }
        else {
            if (positieBuitenBord(naar)){
                val fromValue: Square = Square.fromValue(van)
                robotMove(van, naar)
                println("board.setPiece: " + fromValue + " ->" + Piece.NONE)
                board.unsetPiece(board.getPiece(fromValue), fromValue)
                saveBoard()
            }
            else{
                val fromValue: Square = Square.fromValue(van)
                val naarValue = Square.fromValue(naar)
                robotMove(van, naar)
                moveExtraWhenCastle(Move(fromValue, naarValue))
                if (!board.doMove(Move(fromValue, naarValue))){
                    // not valid
                    // do it anyway
                    val piece = board.getPiece(fromValue)
                    board.setPiece(piece, naarValue)
                    board.unsetPiece(piece, fromValue)
                }
                saveBoard()
            }
        }

        println(board.getFen())
//        printBoard()
        toBoard()
        return toBoard()
    }


    private fun robotMove(van: String, to:String){
        println("robot from:$van to:$to")

        val toPiece = if (positieBuitenBord(to)) Piece.NONE else board.getPiece(Square.fromValue(to))

        if (toPiece!=Piece.NONE){
            // slaan
            if (toPiece.pieceSide==Side.BLACK){
                val storeSquare: StoreSquare = blackStoreSquares.filter { it.piece==Piece.NONE.name }.first()
                storeSquare.piece = toPiece.name
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
                val storeSquare: StoreSquare = whiteStoreSquares.filter { it.piece==Piece.NONE.name }.first()
                storeSquare.piece = toPiece.name
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
            if (positieBuitenBord(to)){
                val storeSquareBlack = blackStoreSquares.filter { it.pos== to }.firstOrNull()
                val storeSquareWhite = whiteStoreSquares.filter { it.pos== to }.firstOrNull()
                storeSquareBlack?.piece = board.getPiece(Square.fromValue(van)).name
                storeSquareWhite?.piece = board.getPiece(Square.fromValue(van)).name
            }

            if (van.endsWith("21") || to.endsWith("21")) {// bovenste rij
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
        println("het logal moves 1")
        val moves: List<Move> = board.legalMoves()
        println("het logal moves 2")
        val mate = board.isMated
        val draw = board.isDraw
        val side = board.sideToMove
        println("het logal moves 3")
        return ChessBoard(list, side.value(), moves.toChessMoves(), draw, mate)
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

    private fun loadBoard(initialBlackStoreSquares: List<StoreSquare>, initialWhiteStoreSquares: List<StoreSquare>) {
        try {
            blackStoreSquares= emptyList()
            whiteStoreSquares=emptyList()

            val fen = File("chessboard.dat").readText()
            board.loadFromFen(fen)

            val blackJson = File("chessboard-blackstore.dat").readText()
            blackStoreSquares = mapper.readValue(blackJson)
            val whiteJson = File("chessboard-whitekstore.dat").readText()
            whiteStoreSquares = mapper.readValue(whiteJson)

        }
        catch (e:Exception){
            println("Error loading fen: "+e.message)
        }
        if (blackStoreSquares==null || blackStoreSquares.size==0) blackStoreSquares = initialBlackStoreSquares
        if (whiteStoreSquares==null || whiteStoreSquares.size==0) whiteStoreSquares = initialWhiteStoreSquares
        println(blackStoreSquares)

    }
    private fun saveBoard(){
        File("chessboard.dat").writeText(board.getFen())
        File("chessboard-blackstore.dat").writeText(mapper.writeValueAsString(blackStoreSquares))
        File("chessboard-whitekstore.dat").writeText(mapper.writeValueAsString(whiteStoreSquares))

    }


}
data class StoreSquare(val pos: String, var piece: String = Piece.NONE.name): java.io.Serializable
