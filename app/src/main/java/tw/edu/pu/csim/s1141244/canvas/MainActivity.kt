package tw.edu.pu.csim.s1141244.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
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
    // 載入圖片資源 (小小兵)
    val minionsImage: ImageBitmap =
        ImageBitmap.imageResource(id = R.drawable.minions)
    Box(modifier = modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            //繪製洋紅色的圓, 半徑 100, 圓心 (100,100)
            drawCircle(
                Color.Magenta,
                100f,
                Offset(100f, 100f)
            )
            //繪製藍色空心矩形, 左上角 (300,200), 寬度與高度 200
            drawRect(
                Color.Blue,
                Offset(300f, 200f),
                Size(200f, 200f),
                style = Stroke(width = 5f) // 使用 style 控制外框樣式
            )

            CanvasWidth = size.width
            CanvaHeight = size.height

            drawLine(
                Color.Red,
                Offset(200f, 800f),
                Offset(size.width, size.height),
                strokeWidth = 10f
            )
            //取得小小兵圖檔的寬度與高度
            val minionsWidth = minionsImage.width
            val minionsHeight = minionsImage.height

            // 置中公式：(畫布寬高 - 圖片寬高) / 2f
            val offsetX = (size.width - minionsWidth) / 2f
            val offsetY = (size.height - minionsHeight) / 2f

            // 將畫布起點移至置中位置後畫圖
            translate(offsetX, offsetY) {
                drawImage(minionsImage)
            }
        }
        }
        Text("畫布寬度 $CanvasWidth, 長度 $CanvaHeight")

}