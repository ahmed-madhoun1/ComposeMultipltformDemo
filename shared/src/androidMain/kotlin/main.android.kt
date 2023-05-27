import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Mobile"

@Composable fun MainView() = App(getPlatformName())
