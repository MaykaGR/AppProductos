package aplicacion

import aplicacion.controlador.AppController
import aplicacion.vista.AppVista

fun main(){
    val vista: AppVista = AppVista()
    val controlador: AppController = AppController(vista)

    when(controlador.onStart()){
        1 -> controlador.onAllProducts()
        0 -> controlador.onExit()
    }
    controlador.onExit()
}