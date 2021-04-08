package ar.edu.unahur.obj2.vendedores

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class CentroDistribucionTest : DescribeSpec({
    val buenosAires = Provincia(15000000)
    val moron = Ciudad(buenosAires)
    val centroMoron = CentroDistribucion(moron)
    val vendedorFijo = VendedorFijo(moron)
    val viajante = Viajante(listOf(buenosAires))
    val certDeproducto15 = Certificacion(true, 15)
    val otraCertificacion10 = Certificacion(false, 10)
    centroMoron.agregarVendedor(vendedorFijo)
    centroMoron.agregarVendedor(viajante)
    vendedorFijo.agregarCertificacion(certDeproducto15)
    viajante.agregarCertificacion(otraCertificacion10)

    describe("agregarVendedor") {
        it("error al querer agregar vendedor existente") {
            shouldThrowAny { centroMoron.agregarVendedor(vendedorFijo) }
        }
    }

    describe("vendedorEstrella") {
        it("VendedorFijo es vendedorEstrella") {
            centroMoron.vendedorEstrella().shouldBe(vendedorFijo)
        //arroja error si la lista está vacía, por lo que habría que
        //implementar algo cuando esto suceda. No lo hice debido a que no está en el requerimiento.
        }
    }

    describe("puede cubrir una ciudad") {
        val laPlata = Ciudad(buenosAires)
        val santaFe = Provincia(3300000)
        val rosario = Ciudad(santaFe)
        it("viajante puede trabajar en laPlata") {
            centroMoron.puedeCubrir(laPlata).shouldBeTrue()
        }
        it("nadie puede trabajar en rosario") {
            centroMoron.puedeCubrir(rosario).shouldBeFalse()
        }
    }

    describe("vendedoresGenericos") {
        it("solo el vendedorViajante es generico") {
            centroMoron.vendedoresGenericos().shouldContain(viajante)
            centroMoron.vendedoresGenericos().size.shouldBe(1)
        }
    }

    describe("esRobusto") {
        val vendedorFijo2 = VendedorFijo(moron)
        val vendedorFijo3 = VendedorFijo(moron)
        val vendedorFijo4 = VendedorFijo(moron)
        centroMoron.agregarVendedor(vendedorFijo2)
        centroMoron.agregarVendedor(vendedorFijo3)
        centroMoron.agregarVendedor(vendedorFijo4)
        repeat(2) {vendedorFijo2.agregarCertificacion(certDeproducto15)}
        repeat(2) {vendedorFijo3.agregarCertificacion(certDeproducto15)}
        it("tiene 3 vendedores pero solo 2 son firmes") {
            centroMoron.esRobusto().shouldBeFalse()
        }

        it("tiene 3 vendedores firmes") {
            repeat(2) {vendedorFijo4.agregarCertificacion(certDeproducto15)}
            centroMoron.esRobusto().shouldBeTrue()
        }
    }
})