import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Asteroid(asteroidData: AsteroidData) {
    val asteroidSize = asteroidData.size.dp
    Box(
        Modifier
            .offset(asteroidData.xOffset, asteroidData.yOffset)
            .size(asteroidSize)
            .rotate(asteroidData.angle.toFloat())
            .clip(CircleShape)
            .background(Color(102, 102, 153))
    )
}