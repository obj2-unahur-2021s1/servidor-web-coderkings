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
    fun extension(): String {
        val separarUrl = url.split(".")
        val division = separarUrl[separarUrl.size - 2]
        val separar = division.split("/")
        return separar[separar.size - 1]
    }
}
class Respuesta(val codigo: CodigoHttp, val body: String, val tiempo: Int, val pedido: Pedido)
class ServidorWeb {
    val modulos = mutableListOf<Modulo>()

    fun recibePedido(pedido: Pedido): Respuesta {
        val modulo = buscarModulo(pedido.extension())

        when {
            !pedido.contieneHttp() -> return Respuesta(CodigoHttp.NOT_IMPLEMENTED, "No recibido", 18, pedido)
            !hayModulo(pedido.extension()) -> return Respuesta(CodigoHttp.NOT_FOUND, "", 10, pedido)
            else -> return Respuesta(CodigoHttp.OK, modulo.body, modulo.tiempoRespuesta, pedido)
        }
    }

    fun buscarModulo(extension: String) = modulos.first { m -> m.extensiones.contains(extension) }
    fun hayModulo(extension: String) = modulos.any { m -> m.extensiones.contains(extension) }
}

class Modulo(val extensiones: MutableList<String>,val body: String, val tiempoRespuesta: Int) {

}