package tw.edu.pu.csim.s1141244.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import kotlinx.coroutines.delay
import tw.edu.pu.csim.s1141244.canvas.ui.theme.CanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CanvasScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CanvasScreen(modifier: Modifier = Modifier) {
    var CanvasWidth by remember { mutableStateOf(value = 0f) }
    var CanvaHeight by remember { mutableStateOf(value = 0f) }

    var minionsX by remember { mutableStateOf(value = 0f) }
    var minionsY by remember { mutableStateOf(value = 0f) }

    var gameOver by remember { mutableStateOf(value = false) }
    // 小小兵向右移動的累積距離 (px)
    var moveX by remember { mutableStateOf(0f) }

    // 載入圖片資源 (小小兵)
    val minionsImage: ImageBitmap =
        ImageBitmap.imageResource(id = R.drawable.minions)

    // 每秒向右移動 10px
    LaunchedEffect(gameOver) {
        while (!gameOver) {
            delay(100L)
            minionsX += 5
            minionsY += 5
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // 繪製洋紅色的圓, 半徑 100, 圓心 (100,100)
            drawCircle(
                Color.Magenta,
                100f,
                Offset(100f, 100f)
            )
            // 繪製藍色空心矩形, 左上角 (300,200), 寬度與高度 200
            drawRect(
                Color.Blue,
                Offset(300f, 200f),
                Size(200f, 200f),
                style = Stroke(width = 5f)
            )

            CanvasWidth = size.width
            CanvaHeight = size.height

            drawLine(
                Color.Red,
                Offset(200f, 800f),
                Offset(size.width, size.height),
                strokeWidth = 10f
            )

            // 取得小小兵圖檔的寬度與高度
            val minionsWidth = minionsImage.width
            val minionsHeight = minionsImage.height

            // 置中公式：(畫布寬高 - 圖片寬高) / 2f
            val offsetX = (size.width - minionsWidth) / 2f
            val offsetY = (size.height - minionsHeight) / 2f

            // 在置中位置的基礎上,再加上向右移動的距離
            translate(minionsX, minionsY) {
                drawImage(minionsImage)
            }
        }
        Column {
        Text("畫布寬度 $CanvasWidth, 長度 $CanvaHeight")
            Button (
                onClick = { gameOver = !gameOver}
            )
            {
                if (gameOver){ Text("繼續遊戲") }
                else { Text ("停止遊戲") }
            }
        }
    }
}