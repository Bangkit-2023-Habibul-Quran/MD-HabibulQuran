package com.everybodv.habibulquran.utils.audiorecorder

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}