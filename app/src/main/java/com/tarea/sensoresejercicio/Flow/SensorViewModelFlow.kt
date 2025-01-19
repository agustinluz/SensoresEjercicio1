package com.tarea.sensoresejercicio.Flow

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SensorViewModelFlow : ViewModel() {

    // Estados mutables para almacenar datos de cada sensor
    var accelerometerData = mutableStateOf("Esperando datos del acelerómetro...")
    var temperatureData = mutableStateOf("Esperando datos de temperatura...")

    // Función para iniciar la recolección de datos del acelerómetro
    fun startAccelerometer(sensorHandler: SensorHandlerFlowCallback) {
        viewModelScope.launch {
            sensorHandler.getDatosFlow().collect { datos ->
                // Descomponemos los datos del acelerómetro (X, Y, Z)
                val x = datos[0]
                val y = datos[1]
                val z = datos[2]
                accelerometerData.value = "Acelerómetro - X: $x, Y: $y, Z: $z"
            }
        }
    }

    // Función para iniciar la recolección de datos del sensor de temperatura
    fun startTemperature(sensorHandler: SensorHandlerFlowCallback) {
        viewModelScope.launch {
            sensorHandler.getDatosFlow().collect { datos ->
                // Solo se utiliza el primer valor del sensor de temperatura
                val temperature = datos[0]
                temperatureData.value = "Temperatura: $temperature°C"
            }
        }
    }
}
