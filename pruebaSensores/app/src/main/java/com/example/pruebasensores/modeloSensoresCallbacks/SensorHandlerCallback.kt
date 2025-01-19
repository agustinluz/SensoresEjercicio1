package com.example.pruebasensores.modeloSensoresCallbacks

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorHandlerCallback(private val sensorManager: SensorManager, private val sensor: Sensor?) {

    private var listener: SensorEventListener? = null


    //Aquí se declara e implementa  listener, un objeto que implementa SensorEventListener
    //Pero hace uso de una función lambda (onSensorDatos) será la que defina el
    //tratamiento de nuestros datos. Esta función es llamada cada vez que se ejecuta
    //onSensorChanged que ocurrirá cada vez que recibimos un dato del sensor

    fun startListener(onSensorDatos: (FloatArray) -> Unit) {
        listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    onSensorDatos(it.values)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        //Una vez definido nuestro listener, y con un sensor no Null, podremos registrarlo
        //como oyente de sensor M
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
