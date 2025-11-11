package net.oscar.simondice

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
Funcion principal de interfaz recibe el [modeloVista]
**/
@Composable
fun IU(modeloVista: ModeloVista) {
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
        MostrarRonda(modeloVista)
        MostrarPuntuacion(modeloVista)
        MostrarTextoFinal(modeloVista)
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
fun CrearBotonStart(modeloVista: ModeloVista,color: Colores) {
    val start_activo = modeloVista.estadoActual.collectAsState().value.start_activo
    Button(onClick = {modeloVista.iniciarJuego()}, enabled = start_activo) { Text(color.txt) }
}

@Composable
fun BotonesNormales(modeloVista: ModeloVista,color: Colores) {
    val activo = modeloVista.estadoActual.collectAsState().value.boton_activo
    val context = LocalContext.current
    val mMediaPlayer = MediaPlayer.create(context,obtenerAudio(color))
    Button(onClick = {

        modeloVista.incrementandoLista(color) }, enabled = activo, colors = ButtonDefaults.buttonColors(color.color)) {
        Text(color.txt)
    }
}
fun obtenerAudio(color: Colores): R.raw {
    when (color) {

    }
}
