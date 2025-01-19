package com.example.pruebasensores.modeloSensoresLaunch

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SensorHandlerLaunch(private val sensorManager: SensorManager,
                          private val sensor: Sensor?,
                          private val coroutineScope: CoroutineScope) {

    private var listener: SensorEventListener? = null



    fun startListener(onSensorDatos: (FloatArray) -> Unit) {
        listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    coroutineScope.launch(Dispatchers.Default) {
                        onSensorDatos(it.values)
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }


        sensor?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopListener() {
        listener?.let {
            sensorManager.unregisterListener(it)
        }
    }
}
