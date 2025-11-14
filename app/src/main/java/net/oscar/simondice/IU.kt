package net.oscar.simondice

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import net.oscar.simondice.ui.theme.SimonDiceTheme

/**
Funcion principal de interfaz recibe el [modeloVista]
**/
@Composable
fun IU(modeloVista: ModeloVista) {
    Surface(modifier = Modifier.padding(start = 10.dp, top = 30.dp)) {
    Column {
        Row {
            BotonesNormales(modeloVista,Colores.ROJO)
            BotonesNormales(modeloVista,Colores.VERDE)
        }
        Row {
            BotonesNormales(modeloVista,Colores.AMARILLO)
            BotonesNormales(modeloVista,Colores.AZUL)
        }
        CrearBotonStart(modeloVista,Colores.START)
        MostrarEstado(modeloVista)
        MostrarPuntuacion(modeloVista)
        MostrarTextoFinal(modeloVista)
        MostrarRonda(modeloVista)

        }
    }
}
@Composable
fun MostrarPuntuacion(modeloVista: ModeloVista) {
    val puntuacion = modeloVista.puntuacion.collectAsState().value
    val estado = modeloVista.estadoActual.collectAsState().value
    if (estado!=Estados.FINALIZANDO) {
        Text(text = "Puntuacion: $puntuacion")
    }
}

@Composable
fun MostrarRonda(modeloVista: ModeloVista) {
    val fase = modeloVista.fase.collectAsState().value
    val estado = modeloVista.estadoActual.collectAsState().value
    if (estado!=Estados.FINALIZANDO) {
        Text(text = "Ronda: $fase")
    }
}
@Composable
fun MostrarTextoFinal(modeloVista: ModeloVista) {
    val estado = modeloVista.estadoActual.collectAsState().value
    val fase_anterior = modeloVista.fase.collectAsState().value-1
    if (estado==Estados.FINALIZANDO) {
        Text(text = "Juego Terminado Nivel alcanzado $fase_anterior")
    }
}
@Composable
fun MostrarEstado(modeloVista: ModeloVista) {
    val estado = modeloVista.estadoActual.collectAsState().value
    when (estado) {
        Estados.GENERANDO -> {
            Text(text = "Simon Muestra")
        }
        Estados.FINALIZANDO -> {
            Text(text = "Has perdido")
        }
        Estados.JUGANDO -> {
            Text("Tu turno")
        }
        Estados.INICIO -> {}
    }
}
@Composable
fun CrearBotonStart(modeloVista: ModeloVista,color: Colores) {
    val start_activo = modeloVista.estadoActual.collectAsState().value.start_activo
    Button(onClick = {modeloVista.iniciarJuego()}, enabled = start_activo
    ) { Text(color.txt) }
}

@Composable
fun BotonesNormales(modeloVista: ModeloVista,color: Colores) {
    val context = LocalContext.current
    val mediaPlayer = MediaPlayer.create(context, R.raw.sonido_p)
    val activo = modeloVista.estadoActual.collectAsState().value.boton_activo
    val estado = modeloVista.estadoActual
    
    Button(onClick = { // Se intento implementar la logica de sonido buscada pero no se logro
        mediaPlayer.start()
        modeloVista.incrementandoLista(color) }, enabled = activo, colors = ButtonDefaults.buttonColors(color.color)) {
        Text(color.txt)
    }
}
