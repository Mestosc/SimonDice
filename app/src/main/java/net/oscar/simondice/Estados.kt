package net.oscar.simondice

sealed class Estados(val boton_activo: Boolean,val start_activo: Boolean,val modeloVista: ModeloVista) {
    abstract fun on_enter()
    abstract fun on_end()
    class INICIO(modeloVista: ModeloVista,boton_activo: Boolean = false,start_activo: Boolean = true) : Estados(boton_activo,start_activo,modeloVista) {
        override fun on_enter() {
            TODO("Not yet implemented")
        }

        override fun on_end() {
            TODO("Not yet implemented")
        }

    }
    class GENERANDO(modeloVista: ModeloVista,boton_activo: Boolean = false,start_activo: Boolean = false) : Estados(boton_activo,start_activo,modeloVista) {
        override fun on_enter() {
            TODO("Not yet implemented")
        }

        override fun on_end() {
            TODO("Not yet implemented")
        }
    }
    class JUGANDO(modeloVista: ModeloVista,boton_activo: Boolean = true,start_activo: Boolean = false) : Estados(boton_activo,start_activo,modeloVista) {
        override fun on_enter() {
            TODO("Not yet implemented")
        }

        override fun on_end() {
            TODO("Not yet implemented")
        }
    }
    class FINALIZANDO(modeloVista: ModeloVista,boton_activo: Boolean = false,start_activo: Boolean = true) : Estados(boton_activo,start_activo,modeloVista) {
        override fun on_enter() {
            TODO("Not yet implemented")
        }

        override fun on_end() {
            TODO("Not yet implemented")
        }
    }
}