import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize

fun main() = Window(size = IntSize(800, 900)) {
    val game = remember { Game() }
    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos {
                game.update(it)
            }
        }
    }

    Column {
        Row {
            Button({
                game.ship.speed += 1
            }) {
                Text("Forward")
            }
            Button({
                game.ship.angle += 30
            }) {
                Text("Right")
            }
            Button({
                game.ship.fire(game)
            }) {
                Text("Fire")
            }
            Button({
                game.startGame()
            }) {
                Text("Play")
            }
            Text(game.gameStatus)
        }
        Box(modifier = Modifier
            .aspectRatio(1.0f)
            .background(Color.DarkGray)
            .fillMaxWidth()
            .fillMaxHeight()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clipToBounds()
                .onSizeChanged {
                    with(density) {
                        game.width = it.width.toDp()
                        game.height = it.height.toDp()
                    }
                }) {
                game.gameObjects.forEach {
                    when (it) {
                        is ShipData -> Ship(it)
                        is BulletData -> Bullet(it)
                        is Asteroid -> Asteroid(it)
                    }
                }
                Ship(game.ship)
            }
        }
    }
}