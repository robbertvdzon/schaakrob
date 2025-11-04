package com.vdzon.java.state

object GlobalState {
    fun reset() {
        errorDetected = false
        failOnResetDetected = false
    }

    fun startDetectError(){
        startTime = System.currentTimeMillis()
        errorDetected = false
        failOnResetDetected = true
    }

    var startTime: Long = 0
    var stopTime: Long = 0
    var errorDetected = false
    var lastPakkerCount = 0
    var failOnResetDetected = false
}