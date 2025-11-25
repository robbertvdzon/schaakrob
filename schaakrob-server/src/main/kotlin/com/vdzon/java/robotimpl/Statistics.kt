package com.vdzon.java.robotimpl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Statistics {
    private var statisticsData = StatisticsData()
    private val mapper = jacksonObjectMapper()
    private val file = File("statistics.json")
    private var lastHomeErrorTimestamp: Long = 0
    
    init{
        load()
    }
    

    fun load() {
        try {
            if (file.exists()) {
                val content = file.readText()
                if (content.isNotBlank()) {
                    statisticsData = mapper.readValue<StatisticsData>(content)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun save(){
        try {
            file.parentFile?.mkdirs()
            val json = mapper.writeValueAsString(statisticsData)
            file.writeText(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getLastPickCount() : Long = statisticsData.lastPickCount

    fun setLastPickCount(count: Long){
        statisticsData.lastPickCount = count
        save()
    }

    fun addPick(){
        statisticsData.picksSinceLastPickerRestartDetected++
        statisticsData.picksSinceLastHomeNeededDetected++
        save()
    }

    fun pickerRestartDetected(){
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        statisticsData.errors.add("$timestamp : Picker restarted after ${statisticsData.picksSinceLastPickerRestartDetected} picks")
        statisticsData.picksSinceLastPickerRestartDetected = 0L
        save()
    }

    fun homeNeededDetected(){
        val diffBetweenLastHomeErrorAndNow = System.currentTimeMillis() - lastHomeErrorTimestamp
        if (diffBetweenLastHomeErrorAndNow<6000) return // when home needed is detected multiple times in a row within 6 seconds, ignore this.
        lastHomeErrorTimestamp = System.currentTimeMillis()
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        statisticsData.errors.add("$timestamp : Home needed after ${statisticsData.picksSinceLastPickerRestartDetected} picks")
        statisticsData.picksSinceLastHomeNeededDetected = 0L
        save()
    }

    fun getCurrentStats() : StatisticsData = statisticsData



}

data class StatisticsData(
    var lastPickCount: Long = 0,
    var picksSinceLastPickerRestartDetected: Long = 0,
    var picksSinceLastHomeNeededDetected: Long = 0,
    var errors: MutableList<String> = mutableListOf()
)
