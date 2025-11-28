package net.oscar.simondice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.oscar.simondice.recordJuegoDataSystem.RecordDataManagment
import net.oscar.simondice.recordJuegoDataSystem.RecordDataStoreHandler
import kotlin.reflect.KClass

class ModeloVista : ViewModel() {
    var estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO(this))
    var puntuacion = MutableStateFlow(0)
    var fase = MutableStateFlow(0)
    var botonIluminado = MutableStateFlow<Colores?>(null)
    private val tagLOG = "ModeloDebug"
    private val dataManagment: RecordDataManagment = RecordDataStoreHandler()

    init {
        startState()
    }

    /**
     * Cambiar a un nuevo estado
     * @param newState Referencia al nuevo estado al que quiero ir
     */
    fun <T:Estados> changeTo(newState: KClass<T>): Estados {
        estadoActual.value.onEnd()
        estadoActual.value = newState.constructors.first().call(this) // OJO call no respeta valores por defecto
        startState()
        return estadoActual.value
    }

    /**
     * Cambiar a un nuevo estado
     * @param newState El estado al que quieres pasar con todos sus parametros
     */
    fun <T:Estados> changeTo(newState: T): Estados {
        estadoActual.value.onEnd()
        estadoActual.value = newState
        startState()
        return estadoActual.value
    }
    private fun startState() {
        estadoActual.value.onEnter()
    }

    /**
     * Añado un nuevo [color] a la lista de la secuencia que yo estoy poniendo
     */
    fun incrementandoLista(color: Colores) {
        if (finalizoJuego(color)) { // Si el juego no finalizo por fallar una parte de la secuencia
            changeTo(Estados.PERDIENDO::class)
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
        changeTo(Estados.GENERANDO::class)
        Log.d(tagLOG,"Cambiando estado a Adivinar")
        changeTo(Estados.JUGANDO::class)
        if (estadoActual.value is Estados.JUGANDO) (estadoActual.value as Estados.JUGANDO).iniciarRonda(numRonda)
    }
    private suspend fun mostrarSecuencia() {
        delay(500)

        Datos.secuenciaAdivinar.forEach { color ->
            botonIluminado.value = color
            delay(600)

            botonIluminado.value = null
            delay(300)
        }
    }
    fun iniciarJuego() {
        inicarRonda(1)
    }
}