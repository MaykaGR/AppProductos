package aplicacion.modelo

import aplicacion.modelo.clases.Cliente
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



    fun selectCliente(dni: String): Cliente? {
        var cliente: Cliente? = null

        val ps = con!!.prepareStatement(sentenciasSQLapp.selectClientBy)
        ps.setString(1, dni)
        val rs = ps.executeQuery()
        while (rs.next()) {
            val dni = rs.getString(1)
            val nombre = rs.getString(2)
            val tlf = rs.getString(3)
            val dir = rs.getString(4)
            cliente = Cliente(dni, nombre, tlf, dir)
        }
        ps.close()
        rs.close()
        return cliente
    }

    fun update(id: String, stock: Int) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.updateProducts)
            ps.setInt(1, stock)
            ps.setString(2,id)
            ps.executeQuery()
            ps.close()
        }
    }

    fun deleteProductos(id: String) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.deleteProducts)
            ps.setString(1, id)
            ps.executeUpdate()
            ps.close()
        }
    }

    fun insertProducto(id: String, nombre: String, precio: Int, cantidad: Int, descr: String) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.insertProducts)
            ps.setString(1, id)
            ps.setString(2,nombre)
            ps.setInt(3,precio)
            ps.setInt(4,cantidad)
            ps.setString(5,descr)
            ps.executeQuery()
            ps.close()
        }
    }

    /*
    * Función que muestra los productos en stock
    * */
    fun checkProductosConStock(): List<Producto>? {
        try {
            if (con != null) {
                val ps: PreparedStatement = con!!.prepareStatement(sentenciasSQLapp.selectAllProducts)
                val rs: ResultSet = ps.executeQuery()
                var listaProductos: MutableList<Producto> = mutableListOf()

                while (rs.next()) {
                    val id = rs.getString(1)
                    val nombre = rs.getString(2)
                    val precio = rs.getInt(3)
                    val cantidad = rs.getInt(4)
                    val descr = rs.getString(5)
                    listaProductos.add(Producto(id, nombre, precio, cantidad, descr))
                }
                ps.close()
                rs.close()
                return listaProductos
            } else {
                //Devolvemos null indicando que no hay conexión
                return null
            }
        } catch (e: Exception) {
            return mutableListOf<Producto>().toList()
        }
    }

    fun insertCliente(dni: String, nombre: String, tlf: String, dir: String) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.insertClient)
            ps.setString(1, dni)
            ps.setString(2,nombre)
            ps.setString(3,tlf)
            ps.setString(4,dir)
            ps.executeQuery()
            ps.close()
        }
    }

    fun crearPedido(cliente: Cliente, listaP: List<Producto>){
        con!!.autoCommit = false
        var savePoint: Savepoint? = null
        val ps = con!!.prepareStatement(sentenciasSQLapp.insertPedido)
        for(i in 0..listaP.size-1){
            try{
                ps.setString(1,cliente.dni)
                ps.setString(2,listaP[i].id)
                ps.executeUpdate()
                savePoint = con!!.setSavepoint("enProductos")
            } catch(e: Exception){
                con!!.rollback(savePoint)
            }
        }
        con!!.commit()
        ps.close()
    }
}