package net.oscar.simondice

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ModeloVista : ViewModel() {
    var estadoActual = MutableStateFlow(Estados.INICIO)

    fun incrementandoLista(color: Colores) {
        if (Datos.secuenciaAdivinando.size==Datos.secuenciaAdivinar.size) {
            estadoActual.value = Estados.FINALIZANDO // Hacemos que el estado sea Finalizado independientemente de si ganamos o perdemos
            return
        }
        if (Datos.secuenciaAdivinar.size<Datos.secuenciaAdivinando.size) {
            Datos.secuenciaAdivinando.add(color)
        }
    }
}