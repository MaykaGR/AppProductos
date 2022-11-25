package aplicacion.controlador

import aplicacion.modelo.GestorModelo
import aplicacion.modelo.clases.Producto
import aplicacion.vista.AppVista

class AppController(val vista: AppVista) {

    fun onAllProducts(){
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.conexion()
        val listaProductos: List<Producto>? = gestor.checkProductosConStock()
        if(listaProductos == null){
            vista.baseDeDatosCaída()
        } else if(listaProductos.size == 0){
            vista.noProdStock()
        } else if(listaProductos.size > 0){
            for(producto in listaProductos){
            vista.showProduct(producto)}
        }
        else {
            vista.errorGeneral()
        }
    }

    fun onStart(): Int {
        return vista.mainMenu()
    }

    fun onExit() {
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.desconexion()
        vista.salir()
    }
}