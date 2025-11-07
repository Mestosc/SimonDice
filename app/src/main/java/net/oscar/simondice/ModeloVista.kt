package net.oscar.simondice

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ModeloVista : ViewModel() {
    var estadoActual = MutableStateFlow(Estados.INICIO)
    private val TAGLOG = "ModeloDebug"
    fun incrementandoLista(color: Colores) {
        if (Datos.secuenciaAdivinando.size==Datos.secuenciaAdivinar.size) {
            Log.d(TAGLOG,"Cambiando estado a finalizado")
            estadoActual.value = Estados.FINALIZANDO
            return
        }
        if (Datos.secuenciaAdivinando.size<Datos.secuenciaAdivinar.size) {
            Log.d(TAGLOG,"Añadiendo color ${color.color} a la secuencia")
            Datos.secuenciaAdivinando.add(color)
            if (Datos.secuenciaAdivinando==Datos.secuenciaAdivinar) {
                estadoActual.value = Estados.FINALIZANDO
            }
        }
    }
    fun iniciarJuego() {
        estadoActual.value = Estados.GENERANDO
        if (!Datos.secuenciaAdivinando.isEmpty()) {
            Datos.secuenciaAdivinando.removeAll(Datos.secuenciaAdivinando)
        }
        Log.d(TAGLOG,"Cambiando estado a Adivinar")
        estadoActual.value = Estados.ADIVINAR
    }
}