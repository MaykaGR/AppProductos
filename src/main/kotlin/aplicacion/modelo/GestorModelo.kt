package aplicacion.modelo

import aplicacion.modelo.clases.Cliente
import aplicacion.modelo.clases.Producto
import aplicacion.modelo.sentencias.sentenciasSQLapp
import java.sql.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GestorModelo {

    //Crea una instancia para que sólo pueda haber una conexión
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

    //Crea la conexión
    fun conexion() {
        try {
            if (con == null) {
                Class.forName("com.mysql.cj.jdbc.Driver")
                con = DriverManager.getConnection(url + bd, user, pass)
                println("[Conexión realizada]")
            }
        } catch (e: Exception) {
            println("[Conexión no realizada]")
        }
    }

    //Desconecta la base de datos
    fun desconexion() {
        con?.close()
        println("[Desconexión de la base de datos]")
    }


    //Selecciona el cliente usando su dni
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

    //Función que actualiza el stock de un producto
    fun update(id: String, stock: Int) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.updateProducts)
            ps.setInt(1, stock)
            ps.setString(2, id)
            ps.executeUpdate()
            ps.close()
        }
    }

    //Para borrar un producto
    fun deleteProductos(id: String) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.deleteProducts)
            ps.setString(1, id)
            ps.executeUpdate()
            ps.close()
        }
    }

    //Para borrar, o dar de baja, un cliente
    fun deleteCliente(dni: String) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.deleteClient)
            ps.setString(1, dni)
            ps.executeUpdate()
            ps.close()
        }
    }

    //Para insertar un producto en la base de datos
    fun insertProducto(id: String, nombre: String, precio: Int, cantidad: Int, descr: String) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.insertProducts)
            ps.setString(1, id)
            ps.setString(2, nombre)
            ps.setInt(3, precio)
            ps.setInt(4, cantidad)
            ps.setString(5, descr)
            ps.executeUpdate()
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

    //Para registrar un nuevo cliente
    fun insertCliente(cliente: Cliente) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.insertClient)
            ps.setString(1, cliente.dni)
            ps.setString(2, cliente.nombre)
            ps.setString(3, cliente.tlf)
            ps.setString(4, cliente.dir)
            ps.executeUpdate()
            ps.close()
        }
    }


    //Busca un producto usando su id
    private fun selectProductoByID(id: String): Producto? {
        var producto: Producto? = null
        if (con != null) {
            val ps: PreparedStatement = con!!.prepareStatement(sentenciasSQLapp.selectProductsBy)
            ps.setString(1, id)
            val rs: ResultSet = ps.executeQuery()
            while (rs.next()) {
                val id = rs.getString(1)
                val nombre = rs.getString(2)
                val precio = rs.getInt(3)
                val cantidad = rs.getInt(4)
                val descr = rs.getString(5)
                producto = Producto(id, nombre, precio, cantidad, descr)
            }
        }
        return producto
    }

    //Función para crear un pedido, que recibe un cliente y la lista de ids de los pedidos que desea
    fun crearPedido(cliente: Cliente, listaP: MutableList<String>) {
        if (con != null) {
            con!!.autoCommit = false
            var savePoint: Savepoint = con!!.setSavepoint("pre-pedido")
            //val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM.dd.yyy")).toString()
            val ps = con!!.prepareStatement(sentenciasSQLapp.insertPedido)
            for (i in 0..listaP.size - 1) {
                try {
                    //Se busca cada producto por su id
                    val producto = selectProductoByID(listaP[i])
                    ps.setString(1, cliente.dni)
                    ps.setString(2, producto?.id)
                    //ps.setString(3,fecha)
                    ps.executeUpdate()
                    //Una vez realizado el pedido, se resta esa unidad del stock del producto
                    update(producto!!.id, (producto?.cantidad ?: 1) - 1)
                    //Establece un savepoint por si en algún momento hay un error introduciendo un pedido
                    savePoint = con!!.setSavepoint("enProductos")
                } catch (e: Exception) {
                    println(e)
                    con!!.rollback(savePoint)
                }
            }
            con!!.commit()
            ps.close()
        }
    }

    fun crearTabla(nombre: String, valores: String) {
        if (con != null) {
            val ps = con!!.prepareStatement(sentenciasSQLapp.crearTabla + " " + nombre + valores + ";")
            ps.executeUpdate()
            ps.close()
        }
    }
}