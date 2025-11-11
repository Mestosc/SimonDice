package net.oscar.simondice

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ModeloVista : ViewModel() {
    var estadoActual = MutableStateFlow(Estados.INICIO)
    var puntuacion = MutableStateFlow(0)
    var fase = MutableStateFlow(0)
    private val tagLOG = "ModeloDebug"

    /**
     * Añado un nuevo [color] a la lista de la secuencia que yo estoy poniendo
     */
    fun incrementandoLista(color: Colores) {
        if (finalizoJuego(color)) { // Si el juego no finalizo por fallar una parte de la secuencia
            estadoActual.value = Estados.FINALIZANDO
        } else {
            Log.d(tagLOG,"Añadiendo color ${color.color} a la secuencia")
            Datos.secuenciaAdivinando.add(color)
            if (pasarRonda()) { // Si la lista de numeros a adivnar y la lista adivinando es igual
                puntuacion.value += 1
                inicarRonda(fase.value+1)
            }
        }
    }
    fun pasarRonda(): Boolean {
        return Datos.secuenciaAdivinar == Datos.secuenciaAdivinando
    }
    /**
     * Comprueba si el juego a finalizado
     */
    fun finalizoJuego(color: Colores): Boolean {
        val posicionActual = Datos.secuenciaAdivinando.size
        return Datos.secuenciaAdivinar[posicionActual] != color
    }
    fun inicarRonda(numRonda: Int) {
        estadoActual.value = Estados.GENERANDO
        if (!Datos.secuenciaAdivinando.isEmpty()) {
            Datos.secuenciaAdivinando.clear()
        }
        Datos.secuenciaAdivinar.forEach { v -> Log.d(tagLOG,v.txt) }
        Log.d(tagLOG,"Cambiando estado a Adivinar")
        estadoActual.value = Estados.ADIVINAR
        fase.value = numRonda
    }
    fun iniciarJuego() {
        inicarRonda(1)
    }
}