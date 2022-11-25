package aplicacion.modelo

import aplicacion.modelo.clases.Producto
import aplicacion.modelo.sentencias.sentenciasSQLapp
import java.sql.*

class GestorModelo {
    companion object {
        private var instance: GestorModelo? = null

        fun getInstance(): GestorModelo {
            if (instance == null) {
                instance = GestorModelo()
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

    fun selectAll() {
        if (con != null) {
            val st = con!!.createStatement()
            val rs = st.executeQuery("select * from productos;")
            val rsmtd = rs.metaData
            while (rs.next()) {
                for (i in 1..rsmtd.columnCount) {
                    print(rsmtd.getColumnName(i)+": "+rs.getString(i)+" | ")
                }
                println("")
            }
        }
    }

    fun select(name: String) {
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
            val rs = st.executeUpdate("insert into productos values ($id,'$nombre',$precio)")
        }
    }
    /*
    * Función que muestra los productos en stock
    * */
    fun checkProductosConStock(): List<Producto>?{
        try{
        if(con != null){
            val ps: PreparedStatement = con!!.prepareStatement(sentenciasSQLapp.selectAllProducts)
            val rs: ResultSet = ps.executeQuery()
            var listaProductos: MutableList<Producto> = mutableListOf()

            while(rs.next()){
                val id = rs.getString(1)
                val nombre = rs.getString(2)
                val precio = rs.getInt(3)
                val cantidad = rs.getInt(4)
                val descr = rs.getString(5)
                listaProductos.add(Producto(id,nombre, precio, cantidad, descr))
            }
            ps.close()
            rs.close()
            return listaProductos
        }
        else{
            //Devolvemos null indicando que no hay conexión
            return null
        }}
    catch(e:Exception){
        return mutableListOf<Producto>().toList()
    }}
}