package net.oscar.simondice

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class PuntuacionMasAltaSharedPreferencesTest {

    @Mock
    private lateinit var mockApplication: Application

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    private lateinit var mockEditor: SharedPreferences.Editor

    private lateinit var formatter: DateTimeFormatter
    private lateinit var handler: PuntuacionMasAltaSharedPreferences

    @Before
    fun setup() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        // Configurar el comportamiento de los mocks
        whenever(mockApplication.getSharedPreferences("Records", Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)

        whenever(mockSharedPreferences.edit()).thenReturn(mockEditor)
        whenever(mockEditor.putInt(any(), any())).thenReturn(mockEditor)
        whenever(mockEditor.putString(any(), any())).thenReturn(mockEditor)
        whenever(mockEditor.remove(any())).thenReturn(mockEditor)

        handler = PuntuacionMasAltaSharedPreferences(mockApplication, formatter)
    }

    @Test
    fun `obtenerRecord deberia recuperar puntuacion y fecha correctamente`() {
        // Given
        val puntuacionEsperada = 150
        val fechaString = "15/12/2025 14:30"
        val fechaEsperada = LocalDateTime.parse(fechaString, formatter)

        whenever(mockSharedPreferences.getInt("Record", 0)).thenReturn(puntuacionEsperada)
        whenever(mockSharedPreferences.getString("Record", "03/12/2025 11"))
            .thenReturn(fechaString)

        // When
        val resultado = handler.obtenerRecord("Record")

        // Then
        assertEquals(puntuacionEsperada, resultado.puntuacionMasAlta)
        assertEquals(fechaEsperada, resultado.marcaTiempo)
        verify(mockSharedPreferences).getInt("Record", 0)
        verify(mockSharedPreferences).getString("Record", "03/12/2025 11")
    }

    @Test
    fun `obtenerRecord deberia devolver valores por defecto cuando no hay datos`() {
        // Given
        whenever(mockSharedPreferences.getInt("Record", 0)).thenReturn(0)
        whenever(mockSharedPreferences.getString("Record", "03/12/2025 11"))
            .thenReturn("03/12/2025 11")

        // When
        val resultado = handler.obtenerRecord("Record")

        // Then
        assertEquals(0, resultado.puntuacionMasAlta)
        assertEquals(
            LocalDateTime.parse("03/12/2025 11", formatter),
            resultado.marcaTiempo
        )
    }

    @Test
    fun `anadirRecord deberia guardar puntuacion y fecha correctamente`() {
        // Given
        val fecha = LocalDateTime.of(2025, 12, 20, 16, 45)
        val puntuacion = PuntuacionMasAlta(
            puntuacionMasAlta = 200,
            marcaTiempo = fecha
        )
        val fechaFormateada = fecha.format(formatter)

        // When
        handler.anadirRecord(puntuacion)

        // Then
        verify(mockEditor).putInt("Record", 200)
        verify(mockEditor).putString("Record", fechaFormateada)
        verify(mockEditor).apply()
    }

    @Test
    fun `eliminarRecord deberia eliminar el record guardado`() {
        // Given
        val puntuacion = PuntuacionMasAlta(
            puntuacionMasAlta = 100,
            marcaTiempo = LocalDateTime.now()
        )

        // When
        handler.eliminarRecord(puntuacion)

        // Then
        verify(mockEditor).remove("Record")
        verify(mockEditor).apply()
    }

    @Test
    fun `anadirRecord deberia sobrescribir el record anterior`() {
        // Given
        val primeraFecha = LocalDateTime.of(2025, 12, 10, 10, 0)
        val primerRecord = PuntuacionMasAlta(100, primeraFecha)

        val segundaFecha = LocalDateTime.of(2025, 12, 15, 15, 30)
        val segundoRecord = PuntuacionMasAlta(250, segundaFecha)

        // When
        handler.anadirRecord(primerRecord)
        handler.anadirRecord(segundoRecord)

        // Then
        verify(mockEditor, times(2)).putInt("Record", anyInt())
        verify(mockEditor).putInt("Record", 250)
        verify(mockEditor).putString("Record", segundaFecha.format(formatter))
    }
}

// Clase de datos para el test (asumiendo que existe en tu proyecto)
data class PuntuacionMasAlta(
    val puntuacionMasAlta: Int,
    val marcaTiempo: LocalDateTime
)