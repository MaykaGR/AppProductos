import java.sql.Connection
import java.sql.DriverManager

class GestorBDD private constructor() {
    companion object {
        private var instance: GestorBDD? = null

        fun getInstance(): GestorBDD {
            if (instance == null) {
                instance = GestorBDD()
            }
            return instance!!
        }
    }

    private val url: String = "jdbc:mysql://localhost/"
    private val bd: String = "bddproductos"
    private val user: String = "root"
    private val pass: String = ""

    private var con: Connection? = null

    fun conexion() {
        if (con == null) {
            Class.forName("com.mysql.cj.jdbc.Driver")
            con = DriverManager.getConnection(url + bd, user, pass)
            println("[Conexión realizada]")
        } else {
            println("[Conexión no realizada]")
        }
    }

    fun desconexion() {
        con?.close()
        println("[Desconexión de la base de datos]")
    }



}