package com.vdzon.java.robotclient

import com.vdzon.java.robitapi.RobotAansturing
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

class RobotAansturingClient(host: String) : RobotAansturing {
    private val httpClient = HttpClients.createDefault()
    private var host = ""
    override fun movetoVlak(vlak: String) {
        post("$host/api/movevlak", vlak)
    }

    override fun moveto(x: Int, y: Int) {
        post("$host/api/move", "$x,$y")
    }

    override fun homeVert() {
        get("$host/api/home_vert")
    }

    override fun homeHor() {
        get("$host/api/home_hor")
    }

    override fun sleep() {
        get("$host/api/sleep")
    }

    override fun clamp() {
        get("$host/api/clamp")
    }

    override fun release() {
        get("$host/api/release")
    }

    override fun rebuild() {
        get("$host/api/rebuild")
    }

    override fun restart() {
        get("$host/api/restart")
    }

    override fun getA8(): String? {
        return get("$host/api/a8")
    }

    override fun setA8(pos: String) {
        post("$host/api/a8", pos)
    }

    override fun getH1(): String? {
        return get("$host/api/h1")
    }

    override fun setH1(pos: String) {
        post("$host/api/h1", pos)
    }

    override fun getSnelheid(): String? {
        return get("$host/api/snelheid")
    }

    override fun setSnelheid(snelheid: String) {
        post("$host/api/snelheid", snelheid)
    }

    override fun getDemoString(): String? {
        return get("$host/api/demo")
    }

    override fun setDemoString(demoString: String) {
        post("$host/api/demo", demoString)
    }

    override fun runDemoOnce() {
        get("$host/api/startdemoonce")
    }

    override fun runDemoLoop() {
        get("$host/api/startdemoloop")
    }

    override fun stopDemo() {
        get("$host/api/stopdemo")
    }

    private operator fun get(url: String): String? {
        try {
            println("call:$url")
            val request = HttpGet(url)
            httpClient.execute(request).use { response ->
                println(response.statusLine.toString())
                val entity = response.entity
                //        Header headers = entity.getContentType();
//        System.out.println(headers);
//
                if (entity != null) {
                    // return it as a String
                    val result = EntityUtils.toString(entity)
                    println("Body:$result")
                    return result
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null // Dit mooi oplossen!
    }

    private fun post(url: String, body: String): String? {
        try {
            println("call post:$url")
            val request = HttpPost(url)
            request.entity = StringEntity(body)
            httpClient.execute(request).use { response ->
                println(response.statusLine.toString())
                val entity = response.entity
                //        Header headers = entity.getContentType();
//        System.out.println(headers);
//
                if (entity != null) {
                    // return it as a String
                    return EntityUtils.toString(entity)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null // Dit mooi oplossen!
    }

    init {
        this.host = host
    }
}
