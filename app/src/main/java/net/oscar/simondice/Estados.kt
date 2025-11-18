package net.oscar.simondice

import android.util.Log

sealed class Estados(val modeloVista: ModeloVista) {
    var tagLOG = "StateProgram"
    var boton_activo = true
    var start_activo = false
    abstract fun on_enter()
    abstract fun on_end()
    class INICIO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun on_enter() {
            boton_activo = false
            start_activo = true
        }

        override fun on_end() {

        }

    }
    class GENERANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun on_enter() {
            boton_activo = false
            start_activo = false
            if (!Datos.secuenciaAdivinando.isEmpty()) {
                Datos.secuenciaAdivinando.clear() /* Nos aseguramos de que en cada
            ronda la secuencia que componemos al pulsar los botones de la interfaz en este caso
            este vacia para que al intentar adivnar la lista en cada ronda no genere problemas*/
            }
            Datos.secuenciaAdivinar.forEach { v -> Log.d(tagLOG,v.txt) }
        }

        override fun on_end() {

        }
    }
    class JUGANDO(modeloVista: ModeloVista,boton_activo: Boolean = true,start_activo: Boolean = false) : Estados(modeloVista) {
        override fun on_enter() {
            boton_activo = true
            start_activo = false


        }

        override fun on_end() {
            TODO("Not yet implemented")
        }
    }
    class FINALIZANDO(modeloVista: ModeloVista,boton_activo: Boolean = false,start_activo: Boolean = true) : Estados(modeloVista) {
        override fun on_enter() {
            boton_activo = false
            start_activo = false
        }

        override fun on_end() {
            TODO("Not yet implemented")
        }
    }
}