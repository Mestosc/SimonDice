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
            if (Datos.secuenciaAdivinando.size<Datos.secuenciaAdivinar.size) { // Si sigue por no haber hecho toda la secuencia y no fallar
                Log.d(tagLOG,"Añadiendo color ${color.color} a la secuencia")
                Datos.secuenciaAdivinando.add(color)
            } else if (pasarRonda()) { // Si la lista de numeros a adivnar y la lista adivinando es igual
                inicarRonda(fase.value+1)
            } else { // En caso contrario seguimos
                puntuacion.value += 1
                estadoActual.value = Estados.ADIVINAR
            }
        }
    }
    fun pasarRonda(): Boolean {
        return Datos.secuenciaAdivinar.containsAll(Datos.secuenciaAdivinando) && Datos.secuenciaAdivinar.size == Datos.secuenciaAdivinando.size
    }
    /**
     * Comprueba si el juego a finalizado
     */
    fun finalizoJuego(color: Colores): Boolean {
        return !Datos.secuenciaAdivinar.contains(color)
    }
    fun inicarRonda(num_ronda: Int) {
        estadoActual.value = Estados.GENERANDO
        if (!Datos.secuenciaAdivinando.isEmpty()) {
            Datos.secuenciaAdivinando.removeAll(Datos.secuenciaAdivinando)
        }
        Datos.secuenciaAdivinar.forEach { v -> Log.d(tagLOG,v.txt) }
        Log.d(tagLOG,"Cambiando estado a Adivinar")
        estadoActual.value = Estados.ADIVINAR
        fase.value = num_ronda
    }
    fun iniciarJuego() {
        inicarRonda(1)
    }
}