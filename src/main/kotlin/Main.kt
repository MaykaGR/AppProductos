fun main() {
    val bdd = GestorBDD.getInstance()
    bdd.conexion()
    bdd.insert(1,"adhiush",6)
    bdd.insert(2,"adhygu",8)
    bdd.selectAll()
    bdd.delete(1)
    bdd.selectAll()
    bdd.delete(2)
    bdd.desconexion()
}