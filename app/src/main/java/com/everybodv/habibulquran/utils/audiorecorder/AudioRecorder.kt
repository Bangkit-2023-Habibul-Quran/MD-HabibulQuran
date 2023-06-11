package com.everybodv.habibulquran.utils.audiorecorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}