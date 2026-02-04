package net.oscar.simondice.puntuacionMasAlta.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.reflect.KClass

/**
 * Controlador de Bajo Nivel para la Gestión de Base de Datos con Room 🗄️
 *
 * Esta clase abstracta se encarga de la inicialización y configuración de la base de datos Room.
 * Implementa un patrón genérico que permite trabajar con cualquier tipo de RoomDatabase.
 *
 * @param T Tipo genérico que debe heredar de RoomDatabase (ej: DataBase)
 * @param context Contexto de la aplicación Android necesario para inicializar Room
 * @param room Referencia de clase (KClass) de la base de datos a instanciar
 *
 * @author Desarrollado con Room v2.8.4
 * @see ControllerAltoNivel Para operaciones de negocio de alto nivel
 * @see DataBase Implementación concreta de RoomDatabase
 */
abstract class ControllerBajoNivel<T: RoomDatabase>(context: Context, room: KClass<T>)  {

    /**
     * Instancia de la base de datos Room 🚀
     *
     * Se inicializa utilizando el patrón Builder de Room:
     * - Room.databaseBuilder(): Crea el builder especificando el contexto, clase DB y nombre
     * - build(): Construye y retorna la instancia finalizada
     * @property db La instancia única de la base de datos inicializada
     */
    val db = Room.databaseBuilder(context, room.java, "database-name")
        .fallbackToDestructiveMigration() // Añadido para evitar fallos si cambia el esquema
        .build()

    /**
     * Objeto de Acceso a Datos (DAO) 🔄
     *
     * Se obtiene dinámicamente mediante reflexión a partir de la instancia de base de datos.
     * Implementa la interfaz InterfazDao para un acceso polimórfico.
     *
     * Este DAO proporciona métodos para:
     * - Insertar registros de puntuaciones
     * - Consultar puntuaciones
     * - Eliminar registros
     *
     * @property recordDAO El Data Access Object tipado como InterfazDao
     * @see obtenerTipoDao Para la lógica de obtención del DAO
     */
    val recordDAO = obtenerTipoDao(db)

    /**
     * Obtiene el Data Access Object (DAO) apropiado según el tipo de base de datos 🎯
     *
     * Utiliza un patrón when (similar a switch) para identificar el tipo concreto de
     * RoomDatabase y retornar su DAO correspondiente.
     *
     * Flujo:
     * 1. Recibe una instancia genérica de RoomDatabase
     * 2. Verifica si es una instancia de DataBase mediante "is"
     * 3. Si es DataBase, retorna su DAO mediante roomer.recordDao()
     * 4. Si no coincide, lanza una excepción informando que no existe implementación
     *
     * @param T Tipo genérico de RoomDatabase
     * @param roomer Instancia de la base de datos a analizar
     *
     * @return El DAO asociado a la base de datos (InterfazDao)
     *
     * @throws RuntimeException Si la base de datos no tiene implementación DAO disponible
     *
     * @example
     * val dao = obtenerTipoDao(miBaseDatos)  // Retorna el RecordDAO
     */
    private fun <T: RoomDatabase> obtenerTipoDao(roomer: T): InterfazDao {
        // Estructura when (switch en Java) para verificar el tipo de base de datos recibida
        return when (roomer) {
            // ✅ Caso 1: Si la instancia es de tipo DataBase
            // Utiliza smart casting automático de Kotlin después del "is"
            is DataBase -> {
                // Retorna el DAO específico de DataBase (RecordDAO)
                // Este método es definido en la clase DataBase abstracta
                roomer.recordDao()
            }
            // ❌ Caso 2: Si no es ninguno de los tipos anteriores (else)
            // Esta rama nunca debería alcanzarse en operación normal
            else -> {
                // Lanza una excepción informando que no hay implementación disponible
                // Esto ayuda a identificar problemas de configuración rápidamente
                throw RuntimeException("No existe una implementacion DAO para el tipo: ${roomer::class.simpleName}")
            }
        }
    }
}