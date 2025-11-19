package net.oscar.simondice

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass

class ModeloVista : ViewModel() {
    var estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO(this))
    var puntuacion = MutableStateFlow(0)
    var fase = MutableStateFlow(0)
    private val tagLOG = "ModeloDebug"

    init {
        startState()
    }

    /**
     * Cambiar a un nuevo estado
     * @param newState Referencia al nuevo estado al que quiero ir
     */
    fun <T:Estados> changeState(newState: KClass<T>) {
        if (newState != estadoActual.value::class) {
            Log.d(tagLOG,"Finalizando Estado")
            estadoActual.value.onEnd()
            estadoActual.value = newState.constructors.first().call(this) // OJO call no respeta valores por defecto, aqui solo necesitamos que posea el viewModel así que da igual lo hago así porque no quiero escribir nombreEstado(this) cada vez que hago el cambio de estado
            startState()
        }
    }
    fun startState() {
        estadoActual.value.onEnter()
    }

    /**
     * Añado un nuevo [color] a la lista de la secuencia que yo estoy poniendo
     */
    fun incrementandoLista(color: Colores) {
        if (finalizoJuego(color)) { // Si el juego no finalizo por fallar una parte de la secuencia
            changeState(Estados.FINALIZANDO::class)
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
        changeState(Estados.GENERANDO::class)
        Log.d(tagLOG,"Cambiando estado a Adivinar")
        changeState(Estados.JUGANDO::class)
        fase.value = numRonda
    }
    fun iniciarJuego() {
        inicarRonda(1)
    }
}