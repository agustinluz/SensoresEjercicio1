package com.example.pruebasensores.modeloSensoresFlowCallback


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebasensores.modeloSensoresLaunch.SensorHandlerLaunch
import kotlinx.coroutines.launch


class SensorViewModelFlow : ViewModel() {

    var datosSensor = mutableStateOf("Esperando datos...")

    fun startSensor(sensorHandler: SensorHandlerFlowCallback) {

        viewModelScope.launch {
            // Collectables los datos del Flow del SensorHandler
            sensorHandler.getDatosFlow().collect { datos ->
                val x = datos[0]
                val y = datos[1]
                val z = datos[2]
                datosSensor.value = "X: $x, Y: $y, Z: $z"

            }
        }
    }

}
