import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Screens.Login
import com.Screens.Registro
import com.example.proyectoilerna.util.AuthManager

@Composable
fun NavHost(context: Context) {
    val navController = rememberNavController()
    val authManager = AuthManager(context = context)

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") { Login(navController, authManager) }
        composable("Registro") { Registro(navController, authManager) }
    }
}
