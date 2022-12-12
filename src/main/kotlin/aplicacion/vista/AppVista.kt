package aplicacion.vista

import aplicacion.modelo.clases.Cliente
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
        println("********************")
    }

    fun errorGeneral() {
        println("*****Error Fatal*****")
    }

    fun login(): String {
        println("Introduce tu dni para loguearte: ")
        val dni = readln()
        return dni
    }

    fun baja(): String{
        println("Introduce el dni: ")
        val dni = readln()
        return dni
    }

    fun noRegistrado(): String {
        println("Usuario no registrado. ¿Desea registrarse? S/N")
        val respuesta = readln()
        return respuesta
    }

    fun hacerPedido(): MutableList<String> {
        var lista: MutableList<String> = mutableListOf()
        var salir = false
        println("Pulsa intro para introducir cada id de producto que desees, y S cuando desees terminar: ")
        var accion = readln()
        if (accion !="S\n") {
            while (!salir) {
                if (readln().toUpperCase() != "S") {
                    val id = readln()
                    lista.add(id)
                } else salir = true
            }
        } else salir = true
        return lista
    }

    fun registrar(): Cliente {
        println("Introduce tu dni: ")
        val dni = readln()
        println("Introduce tu nombre: ")
        val nombre = readln()
        println("Introduce tu número de teléfono: ")
        val tlf = readln()
        println("Introduce tu dirección: ")
        val dir = readln()
        return Cliente(dni, nombre, tlf, dir)
    }

    fun mainMenu(): Int {
        println("Bienvenid@, elige un opción")
        println("1. Mostar productos con stock")
        println(
            "2. Hacer un pedido (es necesario introducir los productos por su id, para pedir más de una unidad " +
                    "de un mismo producto debe introducir de nuevo el id tantas veces como unidades quiera.)"
        )
        println("3. Dar de baja un cliente")
        println("0. Salir")
        return readln().trim().toInt()
    }

    fun salir() {
        println("Adios")
    }
}