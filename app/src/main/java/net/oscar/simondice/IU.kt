package net.oscar.simondice

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun IU() {

}

@Composable
fun BotonesNormales(modeloVista: ModeloVista,color: Colores) {
    val activo = modeloVista.estadoActual.collectAsState().value.boton_activo
    Button(onClick = {modeloVista.incrementandoLista(color)}, enabled = activo, colors = ButtonDefaults.buttonColors(color.color)) {
        Text(color.txt)
    }
}