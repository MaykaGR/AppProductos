import aplicacion.modelo.GestorModelo
import aplicacion.modelo.clases.Cliente
import aplicacion.modelo.clases.Producto

fun main() {
    val gestor: GestorModelo = GestorModelo.getInstance()
    gestor.conexion()
    gestor.crearTabla("productos","(id varchar(4) PRIMARY KEY, nombre varchar(30), precio integer, cantidad integer, descr varchar(200))")
    gestor.crearTabla("clientes","(dni varchar(9) primary key, nombre varchar(30), tlf varchar(9), dir varchar(200))")
    gestor.crearTabla("pedidos","(dni varchar(9) references clientes(dni), id varchar(4) references productos(id))")
    val lista = listOf(Producto("0001","bolígrafo",2,20,"azul"),Producto("0002","acrílico",3,10,"variados"),
        Producto("0003","óleo",5,5,"variados"))
    for(i in 0..lista.size-1){
        gestor.insertProducto(lista[i].id,lista[i].nombre,lista[i].precio,lista[i].cantidad, lista[i].descr)
    }
    val cliente = Cliente("86759044T","Manolito","675362544","Wherever")
    gestor.insertCliente(cliente)
}