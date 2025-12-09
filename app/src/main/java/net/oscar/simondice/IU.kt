package net.oscar.simondice

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.oscar.simondice.datos.Colores
import net.oscar.simondice.datos.ConstantesVarias
import java.time.format.DateTimeFormatter

/**
Funcion principal de interfaz recibe el [modeloVista]
**/
@Composable
fun IU(modeloVista: ModeloVista) {
    Surface(modifier = Modifier.padding(start = 10.dp, top = 30.dp)) {
        Column {
            Row {
                BotonesNormales(modeloVista, Colores.ROJO)
                BotonesNormales(modeloVista, Colores.VERDE)
            }
            Row {
                BotonesNormales(modeloVista, Colores.AMARILLO)
                BotonesNormales(modeloVista, Colores.AZUL)
            }
            CrearBotonStart(modeloVista, Colores.START)
            MostrarRecord(modeloVista)
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
    if (estado !is Estados.PERDIENDO) {
        Text(text = "Puntuacion: $puntuacion")
    }
}

@Composable
fun MostrarRonda(modeloVista: ModeloVista) {
    val fase = modeloVista.fase.collectAsState().value
    val estado = modeloVista.estadoActual.collectAsState().value
    if (estado !is Estados.PERDIENDO) {
        Text(text = "Ronda: $fase")
    }
}
@Composable
fun MostrarTextoFinal(modeloVista: ModeloVista) {
    val estado = modeloVista.estadoActual.collectAsState().value
    val faseAnterior = modeloVista.fase.collectAsState().value-1
    if (estado is Estados.PERDIENDO) {
        Text(text = "Juego Terminado Nivel alcanzado $faseAnterior")
    }
}
@Composable
fun MostrarEstado(modeloVista: ModeloVista) {
    val estado = modeloVista.estadoActual.collectAsState().value
    when (estado) {
        is Estados.GENERANDO -> {
            Text(text = "Simon Muestra")
        }
        is Estados.PERDIENDO -> {
            Text(text = "Has perdido")
        }
        is Estados.JUGANDO -> {
            Text("Tu turno")
        }
        is Estados.INICIO -> {}
        is Estados.GANANDO -> {}
        is Estados.MOSTRANDO_SECUENCIA -> {}
    }
}
@Composable
fun CrearBotonStart(modeloVista: ModeloVista,color: Colores) {
    val startActivo = modeloVista.estadoActual.collectAsState().value.startActivo
    Button(onClick = {modeloVista.iniciarJuego()}, enabled = startActivo
    ) { Text(color.txt) }
}
fun obtenerMediaPlayer(context: Context,enumColores: Colores): MediaPlayer? {
    return when (enumColores) {
        Colores.ROJO -> MediaPlayer.create(context, R.raw.sonido_p)
        Colores.AZUL -> MediaPlayer.create(context,R.raw.sonido)
        Colores.VERDE -> MediaPlayer.create(context,R.raw.sonido_p)
        Colores.AMARILLO -> MediaPlayer.create(context,R.raw.sonido)
        Colores.START -> null
    }
    }
@Composable
fun MostrarRecord(modeloVista: ModeloVista) {
    val record by modeloVista.record.collectAsState()
    Text(text = "${record.marcaTiempo.format(ConstantesVarias.DEFAULT_FORMATTER)} -- ${record.puntuacionMasAlta}")
}
@Composable
fun BotonesNormales(modeloVista: ModeloVista,color: Colores) {
    val context = LocalContext.current
    val mediaPlayer = obtenerMediaPlayer(context,color) ?: MediaPlayer.create(context,R.raw.no_sound)
    val activo = modeloVista.estadoActual.collectAsState().value.botonActivo
    val botonIluminado by modeloVista.botonIluminado.collectAsState()

    // NUEVO: Decidir si este botón debe estar brillante u oscuro
    val colorActual = if (botonIluminado == color) {
        color.color // Brillante
    } else {
        color.colorOscuro // Oscuro
    }
    Button(onClick = { // Se intento implementar la logica de sonido buscada pero no se logro
        mediaPlayer.start()
        modeloVista.botonIluminado.value = color
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch { // Esta corrutina es para que cuando pulse el boton
            kotlinx.coroutines.delay(300)
            modeloVista.botonIluminado.value = null
        }

        modeloVista.incrementandoLista(color)}, enabled = activo, colors = ButtonDefaults.buttonColors(colorActual)) {
        Text(color.txt)
    }
}
