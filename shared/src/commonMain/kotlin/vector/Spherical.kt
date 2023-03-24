package vector

import kotlin.math.acos
import kotlin.math.atan2

/**
 * Created by abdulbasit on 11/03/2023.
 */

private const val EPS = 0.000001

data class Spherical(val theta: Double, val phi: Double, val radius: Double) :
    LinearType<Spherical> {

    fun makeSafe() = Spherical(
        theta,
        clamp(phi, EPS, 180 - EPS),
        radius
    )

    companion object {
        fun fromVector(vector: Vector3): Spherical {
            val r = vector.length
            return Spherical(
                if (r == 0.0) 0.0 else atan2(vector.x, vector.z).asDegrees,
                if (r == 0.0) 0.0 else acos(clamp(vector.y / r, -1.0, 1.0)).asDegrees,
                r
            )
        }
    }

    val cartesian: Vector3
        get() {
            return Vector3.fromSpherical(this)
        }

    override operator fun plus(s: Spherical) =
        Spherical(theta + s.theta, phi + s.phi, radius + s.radius)

    override operator fun minus(s: Spherical) =
        Spherical(theta - s.theta, phi - s.phi, radius - s.radius)

    operator fun times(s: Spherical) = Spherical(theta * s.theta, phi * s.phi, radius * s.radius)
    override operator fun times(s: Double) = Spherical(theta * s, phi * s, radius * s)
    override operator fun div(s: Double) = Spherical(theta / s, phi / s, radius / s)
}

fun clamp(value: Double, min: Double, max: Double) =
    kotlin.math.max(min, kotlin.math.min(max, value))

