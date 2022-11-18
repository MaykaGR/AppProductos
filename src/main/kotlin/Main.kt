fun main() {
    val bdd = GestorBDD.getInstance()
    bdd.conexion()
    bdd.insert(1,"Pantalla",10)
    bdd.mostrarAlgo("Pantalla")
    bdd.desconexion()
}