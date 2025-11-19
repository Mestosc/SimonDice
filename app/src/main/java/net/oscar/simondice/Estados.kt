package net.oscar.simondice

import android.util.Log

sealed class Estados(val modeloVista: ModeloVista) {
    var tagLOG = "StateProgram"
    var botonActivo = false
    var startActivo = true
    abstract fun on_enter(vararg info: Any)
    abstract fun on_end()
    class INICIO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun on_enter(vararg info: Any) {
            Log.d(tagLOG,"Inciando estado INICIO")
        }

        override fun on_end() {

        }

    }
    class GENERANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun on_enter(vararg info: Any) {
            Log.d(tagLOG,"Iniciando estado Generando")
            botonActivo = false
            startActivo = false
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
        override fun on_enter(vararg info: Any) {
            botonActivo = true
            startActivo = false
            modeloVista.fase.value = info[0] as Int
        }

        override fun on_end() {
        }
    }
    class PERDIENDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun on_enter(vararg info: Any) {
            botonActivo = false
            startActivo = true
            modeloVista.puntuacion.value = 0 // Haciendo que si fallas y acaba el juego se reinicie la puntuacion
        }

        override fun on_end() {
        }
    }
}