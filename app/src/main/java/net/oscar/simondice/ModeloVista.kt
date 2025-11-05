package net.oscar.simondice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ModeloVista : ViewModel() {
    var secuenciaAdivinar: MutableStateFlow<List<Colores>> = MutableStateFlow(mutableListOf());
    var estadoActual = MutableStateFlow(Estados.INICIO)
}