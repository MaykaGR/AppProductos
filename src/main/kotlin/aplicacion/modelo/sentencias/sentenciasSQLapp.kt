package aplicacion.modelo.sentencias

//Sentencias SQL para gestionar tanto los productos, como los clientes, como realizar pedidos
object sentenciasSQLapp {
    val selectAllProducts: String = "Select * from productos where cantidad > 0;"
    val deleteProducts: String = "Delete from productos where id = ?;"
    val deleteClient: String = "Delete from clientes where dni = ?;"
    val insertProducts: String = "Insert into productos values(?,?,?,?,?);"
    val insertClient: String = "Insert into clientes values(?,?,?,?);"
    val selectProductsBy: String = "Select * from productos where id = ?;"
    val selectStock: String = "Select stock from productos where id = ?;"
    val selectClientBy: String = "Select * from clientes where dni = ?;"
    val updateProducts: String = "Update productos set cantidad = ? where id = ?;"
    val insertPedido: String = "Insert into pedidos values(?,?, SYSDATE);"
}