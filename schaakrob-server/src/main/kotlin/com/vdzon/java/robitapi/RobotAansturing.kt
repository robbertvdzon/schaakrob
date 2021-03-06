package com.vdzon.java.robitapi

interface RobotAansturing {
    fun movetoVlak(vlak: String)
    fun moveto(x: Int, y: Int)
    fun homeVert()
    fun homeHor()
    fun sleep()
    fun clamp()
    fun release()
    fun rebuild()
    fun restart()
    fun getA8(): String?
    fun setA8(pos: String)
    fun getH1(): String?
    fun setH1(pos: String)
    fun getSnelheid(): String?
    fun setSnelheid(snelheid: String)
    fun getDemoString(): String?
    fun setDemoString(demoString: String)
    fun runDemoLoop()
    fun runDemoOnce()
    fun stopDemo()
    fun startDisplayThread()
}
