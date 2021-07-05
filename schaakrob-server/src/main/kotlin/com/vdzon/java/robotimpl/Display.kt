package com.vdzon.java.robotimpl

import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.RaspiPin
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent
import com.pi4j.io.gpio.event.GpioPinListenerDigital
import de.pi3g.pi.oled.Font
import de.pi3g.pi.oled.OLEDDisplay

object Display {
    private val display = OLEDDisplay()

    fun button1(event: GpioPinDigitalStateChangeEvent){
        displayText("11:"+event.state)

    }
    fun button2(event: GpioPinDigitalStateChangeEvent){
        displayText("22:"+event.state)
    }
    fun button3(event: GpioPinDigitalStateChangeEvent){
        displayText("33:"+event.state)
    }
    fun button4(event: GpioPinDigitalStateChangeEvent){
        displayText("44:"+event.state)
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