package net.oscar.simondice

import android.util.Log

sealed class Estados(val modeloVista: ModeloVista) {
    var tagLOG = "StateProgram"
    var boton_activo = false
    var start_activo = true
    abstract fun on_enter()
    abstract fun on_end()
    class INICIO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun on_enter() {
            Log.d(tagLOG,"Inciando estado INICIO")
        }

        override fun on_end() {

        }

    }
    class GENERANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun on_enter() {
            Log.d(tagLOG,"Iniciando estado Generando")
            boton_activo = false
            start_activo = false
            if (!Datos.secuenciaAdivinando.isEmpty()) {
                Log.d(tagLOG,"Limpiando secuencia existente")
                Datos.secuenciaAdivinando.clear() /* Nos aseguramos de que en cada
            ronda la secuencia que componemos al pulsar los botones de la interfaz en este caso
            este vacia para que al intentar adivnar la lista en cada ronda no genere problemas*/
            }
            Datos.secuenciaAdivinar.forEach { v -> Log.d("Color",v.txt) }
        }

        override fun on_end() {

        }
    }
    class JUGANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun on_enter() {
            boton_activo = true
            start_activo = false
        }

        override fun on_end() {
        }
    }
    class FINALIZANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun on_enter() {
            boton_activo = false
            start_activo = true
        }

        override fun on_end() {
        }
    }
}