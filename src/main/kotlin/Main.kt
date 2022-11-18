fun main() {
    val bdd = GestorBDD.getInstance()
    bdd.conexion()
    bdd.delete(1)
    bdd.selectAll()
    bdd.desconexion()
}