import androidx.compose.ui.window.ComposeUIViewController

actual fun getPlatformName(): String = "Mobile"

fun MainViewController() = ComposeUIViewController { App(getPlatformName()) }