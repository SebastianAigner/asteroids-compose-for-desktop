package vector

import kotlin.math.sqrt

/**
 * Created by abdulbasit on 11/03/2023.
 */
data class Vector4(val x: Double, val y: Double, val z: Double, val w: Double) :
    LinearType<Vector4> {
    constructor(x: Double) : this(x, x, x, x)

    val xy: Vector2 get() = Vector2(x, y)
    val yx: Vector2 get() = Vector2(y, x)
    val xz: Vector2 get() = Vector2(x, z)
    val yz: Vector2 get() = Vector2(y, z)
    val zx: Vector2 get() = Vector2(z, x)
    val zy: Vector2 get() = Vector2(z, y)

    /** Downcasts to [Vector3] by discarding [w]. */
    val xyz: Vector3 get() = Vector3(x, y, z)

    /** Calculates [Vector3] by dividing [x], [y], [z] by [w]. */
    val div: Vector3 get() = Vector3(x / w, y / w, z / w)

    /** The Euclidean length of the vector. */
    val length get() = sqrt(x * x + y * y + z * z + w * w)

    /** The squared Euclidean length of the vector. */
    val squaredLength get() = x * x + y * y + z * z + w * w

    companion object {
        val UNIT_X = Vector4(1.0, 0.0, 0.0, 0.0)
        val UNIT_Y = Vector4(0.0, 1.0, 0.0, 0.0)
        val UNIT_Z = Vector4(0.0, 0.0, 1.0, 0.0)
        val UNIT_W = Vector4(0.0, 0.0, 0.0, 1.0)
        val ZERO = Vector4(0.0, 0.0, 0.0, 0.0)
        val ONE = Vector4(1.0, 1.0, 1.0, 1.0)
    }

    /** Returns a normalized version of the vector. (i.e. unit vector) */
    val normalized: Vector4
        get() {
            val l = 1.0 / length
            if (l.isNaN() || l.isInfinite()) {
                return ZERO
            }
            return this * l
        }

    operator fun unaryMinus() = Vector4(-x, -y, -z, -w)

    override operator fun plus(v: Vector4) = Vector4(x + v.x, y + v.y, z + v.z, w + v.w)
    operator fun plus(d: Double) = Vector4(x + d, y + d, z + d, w + d)
    override operator fun minus(v: Vector4) = Vector4(x - v.x, y - v.y, z - v.z, w - v.w)
    operator fun minus(d: Double) = Vector4(x - d, y - d, z - d, w - d)
    operator fun times(v: Vector4) = Vector4(x * v.x, y * v.y, z * v.z, w * v.w)
    override operator fun times(s: Double) = Vector4(x * s, y * s, z * s, w * s)
    operator fun div(v: Vector4) = Vector4(x / v.x, y / v.y, z / v.z, w / v.w)
    override operator fun div(s: Double) = Vector4(x / s, y / s, z / s, w / s)

    operator fun get(i: Int): Double {
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            3 -> w
            else -> throw IllegalArgumentException("unsupported index")
        }
    }

    /** Calculates the Euclidean distance to [other]. */
    fun distanceTo(other: Vector4): Double {
        val dx = other.x - x
        val dy = other.y - y
        val dz = other.z - z
        val dw = other.z - z
        return sqrt(dx * dx + dy * dy + dz * dz + dw * dw)
    }

    /** Calculates the squared Euclidean distance to [other]. */
    fun squaredDistanceTo(other: Vector4): Double {
        val dx = other.x - x
        val dy = other.y - y
        val dz = other.z - z
        val dw = other.w - w
        return dx * dx + dy * dy + dz * dz + dw * dw
    }

    fun mix(o: Vector4, mix: Double): Vector4 = this * (1 - mix) + o * mix

    /** Casts to [DoubleArray]. */
    fun toDoubleArray() = doubleArrayOf(x, y, z, w)

    /** Casts to [IntVector4]. */
    fun toInt() = IntVector4(x.toInt(), y.toInt(), z.toInt(), w.toInt())
}

operator fun Double.times(v: Vector4) = v * this

fun min(a: Vector4, b: Vector4): Vector4 = Vector4(
    kotlin.math.min(a.x, b.x),
    kotlin.math.min(a.y, b.y),
    kotlin.math.min(a.z, b.z),
    kotlin.math.min(a.w, b.w)
)

fun max(a: Vector4, b: Vector4): Vector4 = Vector4(
    kotlin.math.max(a.x, b.x),
    kotlin.math.max(a.y, b.y),
    kotlin.math.max(a.z, b.z),
    kotlin.math.max(a.w, b.w)
)

fun mix(a: Vector4, b: Vector4, mix: Double): Vector4 = a * (1 - mix) + b * mix

interface CastableToVector4 {
    fun toVector4(): Vector4
}
