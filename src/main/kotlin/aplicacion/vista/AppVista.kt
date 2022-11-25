package aplicacion.vista

import aplicacion.modelo.clases.Producto

class AppVista {
    fun baseDeDatosCaída() {
        println("Base de datos caida")
    }

    fun noProdStock() {
        println("No existen productos")
    }

    fun showProduct(producto: Producto) {
        println("Producto:")
        println("Id: ${producto.id}")
        println("Nombre: ${producto.nombre}")
        println("Descripcion: ${producto.descr}")
        println("Precio: ${producto.precio}")
        println("Cantidad: ${producto.cantidad}")
    }

    fun errorGeneral() {
        println("*****Error Fatal*****")
    }

    fun mainMenu():Int {
        println("Bienvenid@, elige un opción")
        println("1. Mostar productos con stock")
        println("0. Salir")
        return readln().trim().toInt()
    }

    fun salir() {
        println("Adios")
    }
}