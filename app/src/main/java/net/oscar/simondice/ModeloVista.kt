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

    /**
     * Determina si puedo pasar a la siguiente ronda esto, viendo si la lista de numeros que compongo
     * al poner la secuencia es igual a la secuencia que hay replicar
     */
    fun pasarRonda(): Boolean {
        return Datos.secuenciaAdivinar == Datos.secuenciaAdivinando
    }
    /**
     * Comprueba si el juego a finalizado, pasandole el [color] a ver
     * si corresponde con su homonimo en la secuencia que quiero adivinar
     */
    fun finalizoJuego(color: Colores): Boolean {
        val posicionActual = Datos.secuenciaAdivinando.size
        return Datos.secuenciaAdivinar[posicionActual] != color
    }

    /**
     * Inicia una ronda pasandole el [numRonda] que representa en que ronda estoy
     */
    fun inicarRonda(numRonda: Int) {
        estadoActual.value = Estados.GENERANDO
        if (!Datos.secuenciaAdivinando.isEmpty()) {
            Datos.secuenciaAdivinando.clear() /* Nos aseguramos de que en cada
            ronda la secuencia que componemos al pulsar los botones de la interfaz en este caso
            este vacia para que al intentar adivnar la lista en cada ronda no genere problemas*/
        }
        Datos.secuenciaAdivinar.forEach { v -> Log.d(tagLOG,v.txt) }
        Log.d(tagLOG,"Cambiando estado a Adivinar")
        estadoActual.value = Estados.JUGANDO
        fase.value = numRonda
    }
    fun iniciarJuego() {
        inicarRonda(1)
    }
}