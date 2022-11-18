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

    fun mostrarTodo() {
        if (con != null) {
            val st = con!!.createStatement()
            val rs = st.executeQuery("select * from productos;")
            val rsmtd = rs.metaData
            while (rs.next()) {
                for (i in 1..rsmtd.columnCount) {
                    println(rs.getString(i))
                }
            }
        }
    }

    fun mostrarAlgo(name: String) {
        if (con != null) {
            val st = con!!.createStatement()
            val rs = st.executeQuery("select * from productos where nombre = '$name'")
            val rsmtd = rs.metaData
            while (rs.next()) {
                for (i in 1..rsmtd.columnCount) {
                    println(rs.getString(i))
                }
            }
        }
    }
    fun update(id: Int, precio: Int){
        if(con!= null){
            val st = con!!.createStatement()
            val rs = st.executeUpdate("update productos set precio = $precio where id = $id")
        }
    }

    fun delete(id:Int){
        if (con!= null){
            val st = con!!.createStatement()
            val rs = st.executeUpdate("delete from productos where id = $id")
        }
    }

    fun insert(id: Int,nombre:String,precio: Int){
        if (con!= null){
            val st = con!!.createStatement()
            val rs = st.executeUpdate("insert into productos values ('$id','$nombre','$precio')")
        }
    }
}