package com.vdzon.java.schaakspel

import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import java.io.File
import java.util.concurrent.TimeUnit

object ComputerPlayer {
    fun getMove(fen: String): Move{

        val cmd: List<String> = listOf("/usr/bin/sh","-c","/home/robbert/Downloads/stockfish_14.1_linux_x64/calc.sh $fen")
        val res = runCommand(cmd=cmd)
        println(res)
        val bestMove = res?.substringAfterLast("bestmove")?.trim()?.toUpperCase()?:""
        println("BEST MOVE="+bestMove)
        val from = bestMove.substring(0,2)
        val to = bestMove.substring(2,4)
        println("from="+from)
        println("to="+to)
        println("from="+Square.fromValue(from))
        println("to="+Square.fromValue(to))
        return Move(Square.fromValue(from), Square.fromValue(to))
    }

    fun runCommand(
        workingDir: File = File("."),
        timeoutAmount: Long = 60,
        timeoutUnit: TimeUnit = TimeUnit.SECONDS,
        cmd: List<String>

    ): String? = runCatching {
        ProcessBuilder(cmd)
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start().also { it.waitFor(timeoutAmount, timeoutUnit) }
            .inputStream.bufferedReader().readText()
    }.onFailure { it.printStackTrace() }.getOrNull()
}