import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.unit.dp

@Composable
fun Ship(shipData: ShipData) {
    val shipSize = shipData.size.dp
    Box(
        Modifier
            .offset(shipData.position.x.dp - (shipSize / 2), shipData.position.y.dp - (shipSize / 2))
            .size(shipSize)
            .rotate(shipData.visualAngle.toFloat())
            .clip(CircleShape)
            .background(Color.Black)
    ) {
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            drawPath(
                color = Color.White,
                path = Path().apply {
                    val size = shipSize.toPx()
                    // Moves to top left position
                    moveTo(0f, 0f)
                    // Add line to right center
                    lineTo(size, size / 2f)
                    // Add line to bottom left corner
                    lineTo(0f, size)
                }
            )
        })
    }
}