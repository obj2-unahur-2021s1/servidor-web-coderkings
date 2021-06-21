package ar.edu.unahur.obj2.servidorWeb

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ServidorWebTest : DescribeSpec({
    describe("Servidor Web con modulo") {
        val servidorWeb = ServidorWeb()

        it("Agregar y quitar modulo al servidor") {
            val moduloImagenes = Modulo(mutableListOf("jpg","png","gif"),"Modulo Imagenes",15)
            servidorWeb.modulos.add(moduloImagenes)
            servidorWeb.modulos.shouldContain(moduloImagenes)
            servidorWeb.modulos.remove(moduloImagenes)
            servidorWeb.modulos.shouldBeEmpty()
        }
        it("Extension de un pedido") {
            val pedidoDoc1 = Pedido("198.234.343:2343", "http://pepito.com.ar/documentos/doc1.html", LocalDateTime.now())
            pedidoDoc1.extension() shouldBe "doc1"
            val pedidoMp4 = Pedido("232.121.122:2323", "https://carlitos.tv/action/1.mp4.html", LocalDateTime.now())
            pedidoMp4.extension() shouldBe "mp4"
        }
        it("Un modulo puede atender un pedido") {
            val moduloMusica = Modulo(mutableListOf("mp3"),"Modulo musica",15)
            servidorWeb.modulos.add(moduloMusica)
            val pedidoMp3 = Pedido("232.121.122:2323", "http://carlitos.tv/action/1.mp3.html", LocalDateTime.now())
            val respuesta = servidorWeb.recibePedido(pedidoMp3)
            respuesta.codigo shouldBe CodigoHttp.OK
            respuesta.body shouldBe "Modulo musica"
            respuesta.tiempo shouldBe 15
        }
        it("Not_implemented") {
            val pedido = Pedido("198.234.343:2343", "https://pepito.com.ar/documentos/doc1.html", LocalDateTime.now())
            val modulo = Modulo(mutableListOf("doc1"), "Modulo documento", 9)
            servidorWeb.modulos.add(modulo)
            val respuesta = servidorWeb.recibePedido(pedido)
            respuesta.codigo shouldBe CodigoHttp.NOT_IMPLEMENTED
        }
        it("Not Found") {
            val pedidofound = Pedido("198.234.343:2343", "http://pepito.com.ar/documentos/doc1.html", LocalDateTime.now())
            val moduloMusica = Modulo(mutableListOf("mp3"),"Modulo musica",10)
            servidorWeb.modulos.add(moduloMusica)
            servidorWeb.hayModulo(pedidofound.extension()).shouldBeFalse()
            shouldThrowAny { servidorWeb.recibePedido(pedidofound) }
            //val respuestaFound = servidorWeb.recibePedido(pedidofound)
            //respuestaFound.codigo shouldBe CodigoHttp.NOT_FOUND

        }



    }


})
