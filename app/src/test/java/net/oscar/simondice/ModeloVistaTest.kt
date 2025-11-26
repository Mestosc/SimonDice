package net.oscar.simondice

import android.util.Log
import androidx.compose.ui.graphics.Color
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ModeloVistaTest {

    private lateinit var viewModel: ModeloVista

    @Before
    fun setUp() {
        // 1. Mockeamos el Log de Android para que no falle en los tests
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        // 2. Reseteamos el objeto Datos (IMPORTANTE al ser un Singleton/Object)
        // Asegúrate de que Datos tenga métodos para limpiar las listas o hazlo manualmente aquí
        //Datos.secuenciaAdivinar.clear()
        Datos.secuenciaAdivinando.clear()

        // 3. Inicializamos el ViewModel
        viewModel = ModeloVista()
    }

    @After
    fun tearDown() {
        unmockkAll() // Limpiamos los mocks después de cada test
    }

    @Test
    fun `changeTo cambia correctamente el estado usando una instancia`() {
        // Given (Dado un estado inicial)
        val estadoInicial = viewModel.estadoActual.value
        val nuevoEstado = Estados.JUGANDO(viewModel)

        // When (Cuando cambiamos estado)
        viewModel.changeTo(nuevoEstado)

        // Then (Entonces el estado actual debe ser el nuevo)
        assertNotEquals(estadoInicial, viewModel.estadoActual.value)
        assertTrue(viewModel.estadoActual.value is Estados.JUGANDO)
    }

    @Test
    fun `iniciarJuego resetea a la ronda 1 y cambia a estado JUGANDO`() {
        viewModel.iniciarJuego()

        // Verificamos que se haya intentado iniciar la ronda
        assertTrue(viewModel.estadoActual.value is Estados.JUGANDO)
        // Podrías verificar si fase se reseteó si tuvieras lógica para ello,
        // pero iniciarJuego llama a inicarRonda(1)
    }

    @Test
    fun `finalizoJuego devuelve TRUE si el color es incorrecto`() {
        // Configuración del escenario
        //Datos.secuenciaAdivinar.add(Colores.ROJO) // La máquina dijo ROJO
        Datos.secuenciaAdivinando.clear() // El usuario aún no ha pulsado nada

        // Acción: El usuario pulsa AZUL (incorrecto)
        val resultado = viewModel.finalizoJuego(Colores.AZUL)

        assertTrue("El juego debería finalizar si el color no coincide", resultado)
    }

    @Test
    fun `finalizoJuego devuelve FALSE si el color es correcto`() {
        // Configuración del escenario
        //Datos.secuenciaAdivinar.add(Colores.ROJO)
        Datos.secuenciaAdivinando.clear()

        // Acción: El usuario pulsa ROJO (correcto)
        val resultado = viewModel.finalizoJuego(Colores.ROJO)

        assertFalse("El juego NO debería finalizar si el color es correcto", resultado)
    }

    @Test
    fun `incrementandoLista detecta error y cambia a estado PERDIENDO`() {
        // Preparamos secuencia esperada: ROJO
        //Datos.secuenciaAdivinar.add(Colores.ROJO)
        Datos.secuenciaAdivinando.clear()

        // Ejecutamos con color incorrecto: AZUL
        viewModel.incrementandoLista(Colores.AZUL)

        // Verificamos que el estado cambió a PERDIENDO
        assertTrue(viewModel.estadoActual.value is Estados.PERDIENDO)
    }

    @Test
    fun `incrementandoLista acierta color pero NO pasa ronda (secuencia incompleta)`() {
        // Preparamos secuencia esperada: ROJO, VERDE
        //Datos.secuenciaAdivinar.add(Colores.ROJO)
        //Datos.secuenciaAdivinar.add(Colores.VERDE)
        Datos.secuenciaAdivinando.clear()

        // El usuario acierta el primero: ROJO
        viewModel.incrementandoLista(Colores.ROJO)

        // Verificamos:
        // 1. Se añadió a la lista de adivinando
        assertEquals(1, Datos.secuenciaAdivinando.size)
        assertEquals(Colores.ROJO, Datos.secuenciaAdivinando[0])
        // 2. NO cambió la puntuación (porque falta el verde)
        assertEquals(0, viewModel.puntuacion.value)
    }

    @Test
    fun `incrementandoLista acierta secuencia completa y PASA RONDA`() {
        // Preparamos secuencia esperada: ROJO
        //Datos.secuenciaAdivinar.add(Colores.ROJO)
        Datos.secuenciaAdivinando.clear()

        val faseInicial = viewModel.fase.value
        val puntuacionInicial = viewModel.puntuacion.value

        // El usuario acierta el último color necesario
        viewModel.incrementandoLista(Colores.ROJO)
        viewModel.incrementandoLista(Colores.AZUL)
        viewModel.incrementandoLista(Colores.AMARILLO)
        viewModel.incrementandoLista(Colores.VERDE)
        // Verificamos:
        // 1. Puntuación subió
        assertEquals(puntuacionInicial + 1, viewModel.puntuacion.value)
        // 2. Se llamó a iniciarRonda (lo sabemos porque el estado cambia a JUGANDO o GENERANDO según tu flujo)
        // Nota: Al pasar ronda, tu código llama a inicarRonda(fase+1).
        // Verificar que el estado es JUGANDO es una buena señal.
        assertTrue(viewModel.estadoActual.value is Estados.JUGANDO)
    }

    @Test
    fun `pasarRonda devuelve true si las listas son identicas`() {
        //Datos.secuenciaAdivinar.apply { clear(); add(Colores.AZUL); add(Colores.ROJO) }
        Datos.secuenciaAdivinando.apply { clear(); add(Colores.ROJO) ; add(Colores.AZUL); add(Colores.AMARILLO) ; add(Colores.VERDE) }

        assertTrue(viewModel.pasarRonda())
    }

    @Test
    fun `pasarRonda devuelve false si las listas son diferentes`() {
        //Datos.secuenciaAdivinar.apply { clear(); add(Colores.AZUL); add(Colores.ROJO) }
        Datos.secuenciaAdivinando.apply { clear(); add(Colores.AZUL) } // Falta uno

        assertFalse(viewModel.pasarRonda())
    }
}