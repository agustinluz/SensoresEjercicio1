package com.example.pruebasensores.modeloSensoresCallbacks


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pruebasensores.modeloSensoresLaunch.SensorHandlerLaunch


class SensorViewModel : ViewModel() {
    var datosSensor = mutableStateOf("Esperando datos...")

    //fun startSensor(sensorHandler: SensorHandlerCallback){
    fun startSensor(sensorHandler: SensorHandlerLaunch) {
        sensorHandler.startListener { values ->
            val x = values[0]
            val y = values[1]
            val z = values[2]
            datosSensor.value = "X: $x, Y: $y, Z: $z"
        }
    }

    fun stopSensor(sensorHandler: SensorHandlerCallback) {
        sensorHandler.stopListener()
    }
}
