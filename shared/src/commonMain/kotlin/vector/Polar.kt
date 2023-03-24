package vector

import kotlin.math.atan2

/**
 * Created by abdulbasit on 11/03/2023.
 */
data class Polar(val theta: Double, val radius: Double = 1.0) : LinearType<Polar> {

    /**
     * make a safe version by bringing [theta] between 0 and 360
     */
    fun makeSafe() = Polar(
        mod(theta, 360.0),
        radius
    )

    companion object {
        /** Constructs equivalent polar coordinates from the Cartesian coordinate system. */
        fun fromVector(vector: Vector2): Polar {
            val r = vector.length
            return Polar(
                if (r == 0.0) 0.0 else atan2(vector.y, vector.x).asDegrees,
                r
            )
        }
    }

    /** Constructs equivalent Cartesian coordinates from the polar representation. */
    val cartesian: Vector2
        get() {
            return Vector2.fromPolar(this)
        }

    override operator fun plus(s: Polar) = Polar(theta + s.theta, radius + s.radius)
    override operator fun minus(s: Polar) = Polar(theta - s.theta, radius - s.radius)
    operator fun times(s: Polar) = Polar(theta * s.theta, radius * s.radius)

    override operator fun times(s: Double) = Polar(theta * s, radius * s)
    override operator fun div(s: Double) = Polar(theta / s, radius / s)
}

fun mod(a: Double, b: Double) = ((a % b) + b) % b
inline val Double.asDegrees: Double get() = this * 57.29577951308232

