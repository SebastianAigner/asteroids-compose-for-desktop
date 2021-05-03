import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.openrndr.math.Vector2
import kotlin.math.atan2
import kotlin.random.Random

enum class GameState {
    STOPPED, RUNNING
}

fun Vector2.angle(): Double {
    val rawAngle = atan2(y = this.y, x = this.x)
    return (rawAngle / Math.PI) * 180
}

class Game {
    var prevTime = 0L
    val ship = ShipData()

    var targetLocation by mutableStateOf<DpOffset>(DpOffset.Zero)

    var gameObjects = mutableStateListOf<GameObject>()
    var gameState by mutableStateOf(GameState.RUNNING)
    var gameStatus by mutableStateOf("Let's play!")

    fun startGame() {
        gameObjects.clear()
        ship.position = Vector2(width.value / 2.0, height.value / 2.0)
        ship.movementVector = Vector2.ZERO
        gameObjects.add(ship)
        repeat(3) {
            gameObjects.add(AsteroidData().apply {
                position = Vector2(100.0, 100.0); angle = Random.nextDouble() * 360.0; speed = 2.0
            })
        }
        gameState = GameState.RUNNING
        gameStatus = "Good luck!"
    }

    fun update(time: Long) {
        val delta = time - prevTime
        val floatDelta = (delta / 1E8).toFloat()
        prevTime = time

        if (gameState == GameState.STOPPED) return

        val cursorVector = Vector2(targetLocation.x.value.toDouble(), targetLocation.y.value.toDouble())
        val shipToCursor = cursorVector - ship.position
        val angle = atan2(y = shipToCursor.y, x = shipToCursor.x)

        ship.visualAngle = shipToCursor.angle()
        ship.movementVector = ship.movementVector + (shipToCursor.normalized * floatDelta.toDouble())

        for (gameObject in gameObjects) {
            gameObject.update(floatDelta, this)
        }


        val bullets = gameObjects.filterIsInstance<BulletData>()

        // Limit number of bullets at the same time
        if (bullets.count() > 3) {
            gameObjects.remove(bullets.first())
        }
        val asteroids = gameObjects.filterIsInstance<AsteroidData>()

        // Bullet <-> Asteroid interaction
        asteroids.forEach { asteroid ->
            val least = bullets.firstOrNull { it.overlapsWith(asteroid) } ?: return@forEach
            if (asteroid.position.distanceTo(least.position) < asteroid.size) {
                gameObjects.remove(asteroid)
                gameObjects.remove(least)

                if (asteroid.size < 50.0) return@forEach
                // it's still pretty big, let's spawn some smaller ones
                repeat(2) {
                    gameObjects.add(AsteroidData(asteroid.speed * 2,
                        Random.nextDouble() * 360.0,
                        asteroid.position).apply {
                        size = asteroid.size / 2
                    })
                }
            }
        }

        // Asteroid <-> Ship interaction
        if (asteroids.any { asteroid -> ship.overlapsWith(asteroid) }) {
            endGame()
        }

        // Win condition
        if (asteroids.isEmpty()) {
            winGame()
        }
    }

    fun endGame() {
        gameObjects.remove(ship)
        gameState = GameState.STOPPED
        gameStatus = "Better luck next time!"
    }

    fun winGame() {
        gameState = GameState.STOPPED
        gameStatus = "Congratulations!"
    }

    var width by mutableStateOf(0.dp)
    var height by mutableStateOf(0.dp)
}