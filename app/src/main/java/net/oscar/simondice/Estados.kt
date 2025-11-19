package net.oscar.simondice

import android.util.Log

sealed class Estados(val modeloVista: ModeloVista) {
    var tagLOG = "StateProgram"
    var botonActivo = false
    var startActivo = true
    abstract fun onEnter(vararg info: Any)
    abstract fun onEnd()
    class INICIO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter(vararg info: Any) {
            Log.d(tagLOG,"Inciando estado INICIO")
        }

        override fun onEnd() {

        }

    }
    class GENERANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter(vararg info: Any) {
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

        override fun onEnd() {

        }
    }
    class JUGANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter(vararg info: Any) {
            botonActivo = true
            startActivo = false
            val numRonda = info.getOrNull(0) as? Int ?: 1
            modeloVista.fase.value = numRonda
        }

        override fun onEnd() {
        }
    }
    class PERDIENDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter(vararg info: Any) {
            botonActivo = false
            startActivo = true
            modeloVista.puntuacion.value = 0 // Haciendo que si fallas y acaba el juego se reinicie la puntuacion
        }

        override fun onEnd() {
        }
    }
}