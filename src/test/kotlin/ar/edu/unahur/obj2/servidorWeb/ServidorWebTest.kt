package ar.edu.unahur.obj2.servidorWeb

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ServidorWebTest : DescribeSpec({
  describe("Servidor Web sin modulo") {
    describe("Servidor web, Cumple protocolo?") {
        val servidorWeb = ServidorWeb()
        it("cumple protocolo. codigo OK. que es 200") {
            val pedido1 = Pedido("198.234.343:2343", "http://pepito.com.ar/documentos/doc1.html", LocalDateTime.now())
            val respuesta1 = servidorWeb.recibePedido(pedido1)
            respuesta1.codigo.shouldBe(CodigoHttp.OK)
        }
        it("No cumple protocolo. codigo NOT_IMPLEMENTED. que es 501") {
            val pedido2 = Pedido("232.121.122:2323", "https://carlitos.tv/action/1.mp4.html", LocalDateTime.now())
            val respuesta2 = servidorWeb.recibePedido(pedido2)
            respuesta2.codigo.shouldBe(CodigoHttp.NOT_IMPLEMENTED)
        }

    }


  }
})
