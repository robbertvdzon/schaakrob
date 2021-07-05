package com.vdzon.java.robotimpl

import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent
import com.pi4j.io.gpio.event.GpioPinListenerDigital
import com.vdzon.java.robitapi.RobotAansturing
import de.pi3g.pi.oled.Font
import de.pi3g.pi.oled.OLEDDisplay

class Display(private val robotAansturing: RobotAansturing) {
    private val display = OLEDDisplay()
    private var pageNr = 0

    fun button1(event: GpioPinDigitalStateChangeEvent){
//        displayText("11:"+event.state)

    }
    fun button2(event: GpioPinDigitalStateChangeEvent){
//        displayText("22:"+event.state)
        if (event.state==PinState.HIGH) {
            nextPage()
            showPage()
        }
    }
    fun button3(event: GpioPinDigitalStateChangeEvent){
//        displayText("33:"+event.state)
        if (event.state==PinState.HIGH) {
            prevPage()
            showPage()
        }
    }
    fun button4(event: GpioPinDigitalStateChangeEvent){
        val maxPages = 10
        when(pageNr % maxPages){
            1-> robotAansturing.runDemoOnce()
            2-> robotAansturing.stopDemo()
            3-> robotAansturing.homeHor()
            4-> robotAansturing.homeVert()
            5-> robotAansturing.clamp1()
            6-> robotAansturing.release1()
            7-> robotAansturing.clamp2()
            8-> robotAansturing.release2()
            9-> robotAansturing.rebuild()
        }

    }

    fun nextPage(){
        pageNr++
    }
    fun prevPage(){
        pageNr--
    }


    fun showPage(){
        val maxPages = 10
        when(pageNr % maxPages){
            0-> displayText("192.168.999.999")
            1-> displayText("start demo")
            2-> displayText("stop demo")
            3-> displayText("home hor")
            4-> displayText("home ver")
            5-> displayText("pak 1")
            6-> displayText("release 1")
            7-> displayText("pak 2")
            8-> displayText("release 2")
            9-> displayText("rebuild")
            else-> displayText("unknown!")
        }
    }


    fun displayText(text: String){
        try{
            display.clear()
            display.drawStringCentered(text, Font.FONT_5X8, 25, true)
            display.update()
        }
        catch (e:Exception){
            e.printStackTrace()
        }

    }

    fun startDisplay(){
        try{
            display.drawStringCentered("Schaakrob!!", Font.FONT_5X8, 25, true)
            display.update()
        }
        catch (e:Exception){
            e.printStackTrace()
        }

        try{
            println("<--Pi4J--> GPIO Listen Example ... started.")
            val gpio = GpioFactory.getInstance()
            val myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN)
            myButton.setShutdownOptions(true)
            myButton.addListener(GpioPinListenerDigital(this::button1))
            val myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN)
            myButton2.setShutdownOptions(true)
            myButton2.addListener(GpioPinListenerDigital(this::button2))
            val myButton3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN)
            myButton3.setShutdownOptions(true)
            myButton3.addListener(GpioPinListenerDigital(this::button3))
            val myButton4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN)
            myButton4.setShutdownOptions(true)
            myButton4.addListener(GpioPinListenerDigital(this::button4))

        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}