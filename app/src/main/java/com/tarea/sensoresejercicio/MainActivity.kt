package com.tarea.sensoresejercicio

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tarea.sensoresejercicio.Flow.SensorHandlerFlowCallback
import com.tarea.sensoresejercicio.Flow.SensorViewModelFlow
import com.tarea.sensoresejercicio.ui.theme.SensoresEjercicio1Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text

import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private lateinit var sensorManager: SensorManager // SensorManager para acceder a los sensores del dispositivo
    private var accelerometer: Sensor? = null // Sensor de acelerómetro
    private var temperatureSensor: Sensor? = null // Sensor de temperatura

    private lateinit var accelerometerHandler: SensorHandlerFlowCallback // Manejador del acelerómetro
    private lateinit var temperatureHandler: SensorHandlerFlowCallback // Manejador del sensor de temperatura

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño edge-to-edge para mejor apariencia

        // Inicializamos el SensorManager y obtenemos referencias a los sensores
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        // Creamos manejadores para cada sensor
        accelerometerHandler = SensorHandlerFlowCallback(sensorManager, accelerometer)
        temperatureHandler = SensorHandlerFlowCallback(sensorManager, temperatureSensor)

        // Creamos e iniciamos el ViewModel para manejar los datos de los sensores
        val sensorViewModel = SensorViewModelFlow()
        sensorViewModel.startAccelerometer(accelerometerHandler) // Inicia el manejo del acelerómetro
        sensorViewModel.startTemperature(temperatureHandler) // Inicia el manejo del sensor de temperatura

        // Configuramos la interfaz de usuario usando Jetpack Compose
        setContent {
            SensoresEjercicio1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Muestra la pantalla principal que incluye datos de los sensores
                    SensorScreen(
                        modifier = Modifier.padding(innerPadding),
                        sensorViewModel = sensorViewModel
                    )
                }
            }
        }
    }
}






@Composable
fun SensorScreen(modifier: Modifier = Modifier, sensorViewModel: SensorViewModelFlow) {
    // Utilizamos una LazyColumn para mostrar los datos de ambos sensores
    LazyColumn(modifier = modifier.padding(16.dp)) {
        // Elemento para mostrar los datos del acelerómetro
        item {
            Text(
                text = sensorViewModel.accelerometerData.value,
                modifier = Modifier.padding(8.dp)
            )
        }
        // Elemento para mostrar los datos del sensor de temperatura
        item {
            Text(
                text = sensorViewModel.temperatureData.value,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


