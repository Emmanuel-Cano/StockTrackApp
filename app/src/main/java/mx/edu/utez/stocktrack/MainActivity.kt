package mx.edu.utez.stocktrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.edu.utez.stocktrack.ui.Navigation
import mx.edu.utez.stocktrack.ui.theme.StockTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockTrackTheme {

                Navigation()
            }


        }
    }
}
