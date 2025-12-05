package net.oscar.simondice

import android.util.Log
import net.oscar.simondice.Datos.Datos
import net.oscar.simondice.Datos.PuntuacionMasAlta
import java.time.LocalDateTime

sealed class Estados(val modeloVista: ModeloVista) {
    var tagLOG = "StateProgram"
    var botonActivo = false
    var startActivo = true
    abstract fun onEnter()
    abstract fun onEnd()
    override fun toString(): String = this::class.simpleName ?: "Estado"
    class INICIO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() {
            Log.d(tagLOG,"Inciando estado $this")
        }

        override fun onEnd() {
            Log.d(tagLOG,"Finalizando estado $this")
        }

    }
    class GENERANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() {
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
    class MOSTRANDO_SECUENCIA(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() {

        }

        override fun onEnd() {

        }

    }
    class JUGANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() {
            Log.d(tagLOG,"Entrando en $this")
            botonActivo = true
            startActivo = false
        }
        fun iniciarRonda(numRonda: Int) {
            modeloVista.fase.value = numRonda
        }
        override fun onEnd() {
            Log.d(tagLOG,"Finalizando estado $this")
        }
    }
    class PERDIENDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() {
            Log.d(tagLOG,"Entrando en $this")
            botonActivo = false
            startActivo = true
            if (modeloVista.puntuacion.value>modeloVista.obtenerRecord().puntuacionMasAlta) {
                modeloVista.record.value = PuntuacionMasAlta(modeloVista.puntuacion.value,LocalDateTime.now())
                modeloVista.guardarRecord()
            };
            modeloVista.puntuacion.value = 0 // Haciendo que si fallas y acaba el juego se reinicie la puntuacion
        }
        override fun onEnd() {
            Log.d(tagLOG,"Finalizando estado $this")
        }
    }
    class GANANDO(modeloVista: ModeloVista) : Estados(modeloVista) {
        override fun onEnter() {

        }

        override fun onEnd() {

        }
    }
}