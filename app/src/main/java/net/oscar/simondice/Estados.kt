package net.oscar.simondice

import android.util.Log

sealed class Estados(val modeloVista: ModeloVista) {
    var tagLOG = "StateProgram"
    var botonActivo = false
    var startActivo = true
    abstract fun onEnter(vararg info: Any)
    abstract fun onEnd()
    override fun toString(): String = this::class.simpleName ?: "Estado"
    class INICIO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter(vararg info: Any) {
            Log.d(tagLOG,"Inciando estado $this")
        }

        override fun onEnd() {
            Log.d(tagLOG,"Finalizando estado $this")
        }

    }
    class GENERANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter(vararg info: Any) {
            Log.d(tagLOG,"Iniciando estado $this")
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
            Log.d(tagLOG,"Finalizando Estado $this")
        }
    }
    class JUGANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter(vararg info: Any) {
            Log.d(tagLOG,"Entrando en $this")
            botonActivo = true
            startActivo = false
            if (info.size==1 && info[0] is Int) {
                val numRonda = info[0] as Int
                modeloVista.fase.value = numRonda
            }
        }

        override fun onEnd() {
            Log.d(tagLOG,"Finalizando estado $this")
        }
    }
    class PERDIENDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter(vararg info: Any) {
            Log.d(tagLOG,"Entrando en $this")
            botonActivo = false
            startActivo = true
            modeloVista.puntuacion.value = 0 // Haciendo que si fallas y acaba el juego se reinicie la puntuacion
        }

        override fun onEnd() {
            Log.d(tagLOG,"Finalizando estado $this")
        }
    }
}