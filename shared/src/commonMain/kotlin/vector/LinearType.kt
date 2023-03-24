package vector

/**
 * Created by abdulbasit on 11/03/2023.
 */
interface LinearType<T : LinearType<T>> {
    operator fun plus(right: T): T
    operator fun minus(right: T): T
    operator fun times(scale: Double): T
    operator fun div(scale: Double): T
}