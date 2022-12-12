package aplicacion

import aplicacion.controlador.AppController
import aplicacion.vista.AppVista

fun main(){
    val vista: AppVista = AppVista()
    val controlador: AppController = AppController(vista)
    var salir = false
    while(!salir){
    when(controlador.onStart()){
        3 -> controlador.onDarDeBaja()
        2 -> controlador.onHacerPedido()
        1 -> controlador.onAllProducts()
        0 -> salir = true
    }}
    controlador.onExit()
}