package com.example.pruebasensores.modeloSensoresCallbacks

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.pruebasensores.modeloSensoresFlowCallback.SensorHandlerFlowCallback
import com.example.pruebasensores.modeloSensoresFlowCallback.SensorViewModelFlow
import com.example.pruebasensores.modeloSensoresLaunch.SensorHandlerLaunch

import com.example.pruebasensores.ui.theme.PruebaSensoresTheme

class MainActivity : ComponentActivity() {

    //DECLARAMOS LOS COMPONENTES PRINCIPALES PARA MANEJO DE SENSORES.
    private lateinit var sensorManager: SensorManager
    private  var sensor: Sensor? = null
    //private lateinit var sensorHandlerCallback: SensorHandlerCallback // CLASE PROPIA

    //private lateinit var sensorHandlerLaunch: SensorHandlerLaunch // CLASE PROPIA
    private lateinit var sensorHandlerFlowCallback: SensorHandlerFlowCallback // CLASE PROPIA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //INICIALIZAMOS LOS OBJETOS
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) //o cualquier otro

        //INICIALIZAMOS sensorHandler
        //1 con callback
        //sensorHandlerCallback = SensorHandlerCallback(sensorManager, sensor)
        //2 con corrutinas
        //sensorHandlerLaunch = SensorHandlerLaunch(sensorManager, sensor, lifecycleScope)
        //3 con FlowCallBack
        sensorHandlerFlowCallback=SensorHandlerFlowCallback(sensorManager, sensor)


        //DECLARAMOS E INICIALIZAMOS EL VIEWMODEL
        //val sensorViewModel = SensorViewModel() // para los ejemplos 1 y 2
        //sensorViewModel.startSensor(sensorHandlerCallback) // ejemplo 1
       //sensorViewModel.startSensor(sensorHandlerLaunch) // ejemplo 2
       val sensorViewModel = SensorViewModelFlow() // ejemplo 3
        sensorViewModel.startSensor(sensorHandlerFlowCallback)

        setContent {
            PruebaSensoresTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        sensorViewModel //Pasamos el sensorViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, sensorVM: SensorViewModelFlow) {
    SalidaDatosSensor(sensorVM)
}


@Composable
fun SalidaDatosSensor(sensorViewModel: SensorViewModelFlow) {

    val sensorText = sensorViewModel.datosSensor.value

    Text(
        text = sensorText,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PruebaSensoresTheme {

    }
}