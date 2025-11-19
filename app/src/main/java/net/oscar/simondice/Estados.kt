package net.oscar.simondice

import android.util.Log

sealed class Estados(val modeloVista: ModeloVista) {
    var tagLOG = "StateProgram"
    var boton_activo = false
    var start_activo = true
    abstract fun onEnter()
    abstract fun onEnd()
    override fun toString(): String = this::class.simpleName ?: "Estado"
    class INICIO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() {
            Log.d(tagLOG,"Inciando estado $this")
        }

        override fun onEnd() {

        }

    }
    class GENERANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() {
            Log.d(tagLOG,"Iniciando estado $this")
            if (!Datos.secuenciaAdivinando.isEmpty()) {
                Log.d(tagLOG,"Limpiando secuencia existente")
                Datos.secuenciaAdivinando.clear() /* Nos aseguramos de que en cada
            ronda la secuencia que componemos al pulsar los botones de la interfaz en este caso
            este vacia para que al intentar adivnar la lista en cada ronda no genere problemas*/
            }
            boton_activo = false
            start_activo = false
            Datos.secuenciaAdivinar.forEach { v -> Log.d("Color",v.txt) }
        }

        override fun onEnd() {

        }
    }
    class JUGANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() {
            Log.d(tagLOG,"Iniciando estado $this")
            boton_activo = true
            start_activo = false
        }

        override fun onEnd() {
        }
    }
    class FINALIZANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() { // Funcion que se ejecuta al entrar en el estado de Finalizacion
            Log.d(tagLOG,"Inciando estado $this")
            boton_activo = false
            start_activo = true
            modeloVista.puntuacion.value = 0 // Haciendo que si fallas y acaba el juego se reinicie la puntuacion
        }

        override fun onEnd() {
        }
    }
}