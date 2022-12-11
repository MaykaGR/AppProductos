package aplicacion.controlador

import aplicacion.modelo.GestorModelo
import aplicacion.modelo.clases.Producto
import aplicacion.vista.AppVista
import java.util.*

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
    fun onHacerPedido(){
        val dni = vista.login()
        val gestor = GestorModelo.getInstance()
        gestor.conexion()
        val cliente = gestor.selectCliente(dni)
        if(cliente!= null){
            gestor.crearPedido(cliente,vista.hacerPedido(cliente))
        }
        else{
            val respuesta = vista.noRegistrado().uppercase(Locale.getDefault())
            if(respuesta=="S"){
                val cliente = vista.registrar()
                gestor.insertCliente(cliente)
            }
            else{
                onHacerPedido()
            }
        }
    }
    fun onExit() {
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.desconexion()
        vista.salir()
    }

    fun onDarDeBaja(){
        val gestor: GestorModelo = GestorModelo.getInstance()
        gestor.conexion()
        val dni = vista.baja()
        gestor.deleteCliente(dni)
    }
}