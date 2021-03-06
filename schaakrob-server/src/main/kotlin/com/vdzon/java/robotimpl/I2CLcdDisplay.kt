package com.vdzon.java.robotimpl

import com.pi4j.io.i2c.I2CDevice
import java.util.*

class I2CLcdDisplay {
    var rsFlag = false
    var eFlag = false
    private val LCD_LINE_ADDRESS = intArrayOf(0x40, 0x80) //Address for LCD Lines 0 and 1
    private val LCD_CHR = true //To decide sent data is data or command
    var RS_PIN = 0 //Pin of MCP23017 PORTB/A connected LCD RS pin
    var EN_PIN = 1 //Pin of MCP23017 PORTB/A connected LCD E pin
    var D7_PIN = 5 //Pin of MCP23017 PORTB/A connected LCD D7 pin
    var D6_PIN = 4 //Pin of MCP23017  PORTB/A connected LCD D6 pin
    var D5_PIN = 3 //Pin of MCP23017  PORTB/A connected LCD D5 pin
    var D4_PIN = 2 //Pin of MCP23017 PORTB/A connected LCD D4 pin
    fun write(data: Byte) { //Writes 1 Byte data to LCD
        try {
            lcd_byte(data.toInt(), LCD_CHR)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun write(data: String) { //Writes a string to LCD
        for (i in 0 until data.length) {
            try {
                lcd_byte(data[i].toInt(), LCD_CHR)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Throws(Exception::class)
    fun lcd_byte(`val`: Int, type: Boolean) { //Sets RS flag and send value to ports depending on DATA or COMMAND
        rsFlag = type
        write(`val` shr 4)
        pulse_en(type, `val` shr 4) // cmd or display data
        write(`val` and 0x0f)
        pulse_en(type, `val` and 0x0f)
    }

    @Throws(Exception::class)
    fun initDisplay() { //Initialization routine of LCD
        lcd_byte(0x33, LCD_CMD) // 4 bit
        lcd_byte(0x32, LCD_CMD) // 4 bit
        lcd_byte(0x28, LCD_CMD) // 4bit - 2 line
        lcd_byte(0x08, LCD_CMD) // don't shift, hide cursor
        lcd_byte(0x01, LCD_CMD) // clear and home display
        lcd_byte(0x06, LCD_CMD) // move cursor right
        lcd_byte(0x0c, LCD_CMD) // turn on
    }

    @Throws(Exception::class)
    private fun pulse_en(type: Boolean, `val`: Int) { // Make the enable pin high and low to provide a pulse.
        eFlag = true
        write(`val`)
        eFlag = false
        write(`val`)
        if (type == LCD_CMD) {
            Thread.sleep(1)
        }
    }

    @Throws(Exception::class)
    private fun write(incomingData: Int) { // Arrange the respective bit of value to be send depending upon the pins the LCD is connected to.
        val bits = fromByte(incomingData.toByte())
        val out = ((if (bits[3]) 1 shl D7_PIN else 0 shl D7_PIN) or (if (bits[2]) 1 shl D6_PIN else 0 shl D6_PIN) or (if (bits[1]) 1 shl D5_PIN else 0 shl D5_PIN) or (if (bits[0]) 1 shl D4_PIN else 0 shl D4_PIN) or (if (rsFlag) 1 shl RS_PIN else 0 shl RS_PIN) or if (eFlag) 1 shl EN_PIN else 0 shl EN_PIN).toByte()
        dev!!.write(0x13, out) //Set the value to PORT B register.
    }

    fun setCursorPosition(row: Int, column: Int) {
        try {
            lcd_byte(LCD_LINE_ADDRESS[row] + column, LCD_CMD)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    companion object {
        var dev: I2CDevice? = null
        const val LCD_CMD = false
        fun fromByte(b: Byte): BitSet { //Convert a byte into Bitset
            var b = b
            val bits = BitSet(8)
            for (i in 0..7) {
                bits[i] = b.toInt() and 1 == 1
                b = (b.toInt() shr 1).toByte()
            }
            return bits
        }
        /*
    public static BitSet fromByte(byte b) { //Convert a byte into Bitset
        BitSet bits = new BitSet(8);

        for (int i = 0; i < 8; i++) {
            bits.set(i, (b & 1) == 1);
            b >>= 1;
        }

        return bits;
    }
         */

    }
}
