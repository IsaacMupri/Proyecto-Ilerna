import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Screens.Login
import com.Screens.Registro

@Composable
fun NavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") { Login(navController) }
        composable("Registro") { Registro(navController) }
    }
}
