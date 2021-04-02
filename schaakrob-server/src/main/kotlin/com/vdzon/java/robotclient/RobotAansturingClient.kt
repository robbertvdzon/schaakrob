package com.vdzon.java.robotclient

import com.vdzon.java.BerekenVersnelling
import com.vdzon.java.robitapi.RobotAansturing
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory

class RobotAansturingClient(host: String) : RobotAansturing {
    private val log = LoggerFactory.getLogger(RobotAansturingClient::class.java)
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

    override fun hold() {
        get("$host/api/hold")
    }

    override fun drop() {
        get("$host/api/drop")
    }

    override fun activate() {
        get("$host/api/activate")
    }

    override fun deactivate() {
        get("$host/api/deactivate")
    }

    override fun bootsound() {
        log.info("call bootsound")
        get("$host/api/bootsound")
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

    override fun getA11(): String? {
        return get("$host/api/a11")
    }

    override fun getA21(): String? {
        return get("$host/api/a21")
    }

    override fun setA8(pos: String) {
        post("$host/api/a8", pos)
    }

    override fun setA11(pos: String) {
        post("$host/api/a11", pos)
    }

    override fun setA21(pos: String) {
        post("$host/api/a21", pos)
    }

    override fun getH1(): String? {
        return get("$host/api/h1")
    }

    override fun getH10(): String? {
        return get("$host/api/h10")
    }

    override fun getH20(): String? {
        return get("$host/api/h20")
    }

    override fun setH1(pos: String) {
        post("$host/api/h1", pos)
    }

    override fun setH10(pos: String) {
        post("$host/api/h10", pos)
    }

    override fun setH20(pos: String) {
        post("$host/api/h20", pos)
    }

    override fun getSnelheid(): String? {
        return get("$host/api/snelheid")
    }

    override fun setSnelheid(snelheid: String) {
        post("$host/api/snelheid", snelheid)
    }
    override fun getDelayNaPak(): String? {
        return get("$host/api/delaynapak")
    }

    override fun setDelayNaPak(delay: String) {
        post("$host/api/delaynapak", delay)
    }

    override fun getDelayNaZet(): String? {
        return get("$host/api/delaynazet")
    }

    override fun setDelayNaZet(delay: String) {
        post("$host/api/delaynazet", delay)
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
    override fun startDisplayThread() {
     // disable for remote
    }

    private operator fun get(url: String): String? {
        try {
            val request = HttpGet(url)
            httpClient.execute(request).use { response ->
                log.info(response.statusLine.toString())
                val entity = response.entity
                //        Header headers = entity.getContentType();
//        log.info(headers);
//
                if (entity != null) {
                    // return it as a String
                    val result = EntityUtils.toString(entity)
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
            val request = HttpPost(url)
            request.entity = StringEntity(body)
            httpClient.execute(request).use { response ->
                log.info(response.statusLine.toString())
                val entity = response.entity
                //        Header headers = entity.getContentType();
//        log.info(headers);
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
