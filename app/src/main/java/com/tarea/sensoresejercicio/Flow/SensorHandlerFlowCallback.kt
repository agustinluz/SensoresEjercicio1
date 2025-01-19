package com.tarea.sensoresejercicio.Flow

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SensorHandlerFlowCallback(
    private val sensorManager: SensorManager, // SensorManager para registrar los sensores
    private val sensor: Sensor? // El sensor que queremos gestionar
) {

    // Función para obtener los datos del sensor como un Flow
    fun getDatosFlow(): Flow<FloatArray> = callbackFlow {
        // Listener que escucha eventos del sensor
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    // Emitimos los valores del sensor a través del Flow
                    trySend(it.values)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // No es necesario implementar cambios de precisión
            }
        }

        // Registramos el listener para el sensor
        sensor?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Cerramos el Flow y liberamos el listener cuando se cancela
        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }
}
