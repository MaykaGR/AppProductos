package aplicacion.modelo.sentencias

object sentenciasSQLapp {
    val selectAllProducts: String = "Select * from productos where cantidad > 0;"
    val selectAllClient: String = "Select * from clientes;"
    val deleteProducts: String = "Delete from productos where id = ?;"
    val deleteClient: String = "Delete from clientes where dni = ?;"
    val insertProducts: String = "Insert into productos values(?,?,?,?,?);"
    val insertClient: String = "Insert into clientes values(?,?,?,?);"
    val selectProductsBy: String = "Select * from productos where ? = ?;"
    val selectClientBy: String = "Select * from clientes where dni = ?;"
    val updateProducts: String = "Update productos set cantidad = ? where id = ?;"
}