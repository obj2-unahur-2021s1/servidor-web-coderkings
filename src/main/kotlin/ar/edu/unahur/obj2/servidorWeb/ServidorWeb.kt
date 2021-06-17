package ar.edu.unahur.obj2.servidorWeb

import java.time.LocalDateTime

// Para no tener los códigos "tirados por ahí", usamos un enum que le da el nombre que corresponde a cada código
// La idea de las clases enumeradas es usar directamente sus objetos: CodigoHTTP.OK, CodigoHTTP.NOT_IMPLEMENTED, etc
enum class CodigoHttp(val codigo: Int) {
  OK(200),
  NOT_IMPLEMENTED(501),
  NOT_FOUND(404),
}

class Pedido(val ip: String, val url: String, val fechaHora: LocalDateTime) {

    fun contieneHttp() = url.startsWith("http:")
}
class Respuesta(val codigo: CodigoHttp, val body: String, val tiempo: Int, val pedido: Pedido)
class ServidorWeb {
    fun recibePedido(pedido: Pedido): Respuesta = if (pedido.contieneHttp())
        Respuesta(CodigoHttp.OK, "recibido", 8, pedido) else Respuesta(CodigoHttp.NOT_IMPLEMENTED, "No recibido", 18, pedido)

}