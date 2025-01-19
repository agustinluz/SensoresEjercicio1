package com.example.pruebasensores.modeloSensoresFlowCallback

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class SensorHandlerFlowCallback(private val sensorManager: SensorManager,
                                private val sensor: Sensor?){

    fun getDatosFlow(): Flow<FloatArray> = callbackFlow {
        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    trySend(it.values) // Emitimos el valor de temperatura
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensor?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Cerramos el flujo cuando ya no se necesitan datos
        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }





}
