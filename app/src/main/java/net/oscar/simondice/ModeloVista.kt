package net.oscar.simondice

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ModeloVista : ViewModel() {
    var estadoActual = MutableStateFlow(Estados.INICIO)
    var puntuacion = MutableStateFlow(0)
    private val tagLOG = "ModeloDebug"

    /**
     * Añado un nuevo [color] a la lista de la secuencia que yo estoy poniendo
     */
    fun incrementandoLista(color: Colores) {
        if (finalizoJuego(color)) {
            estadoActual.value = Estados.FINALIZANDO
        }
        if (Datos.secuenciaAdivinando.size<Datos.secuenciaAdivinar.size) {
            Log.d(tagLOG,"Añadiendo color ${color.color} a la secuencia")
            Datos.secuenciaAdivinando.add(color)
        } else {
            puntuacion.value += 1
            estadoActual.value = Estados.ADIVINAR
        }
    }

    /**
     * Comprueba si el juego a finalizado
     */
    fun finalizoJuego(color: Colores): Boolean {
        return !Datos.secuenciaAdivinar.contains(color)
    }
    fun iniciarJuego() {
        estadoActual.value = Estados.GENERANDO
        if (!Datos.secuenciaAdivinando.isEmpty()) {
            Datos.secuenciaAdivinando.removeAll(Datos.secuenciaAdivinando)
        }
        Log.d(tagLOG,"Cambiando estado a Adivinar")
        Datos.secuenciaAdivinar.forEach { v -> Log.d(tagLOG,v.txt) }
        estadoActual.value = Estados.ADIVINAR
    }
}