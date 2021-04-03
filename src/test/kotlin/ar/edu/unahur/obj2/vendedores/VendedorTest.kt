package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class VendedorTest : DescribeSpec({
  val misiones = Provincia(1300000)
  val sanIgnacio = Ciudad(misiones)
  val buenosAires = Provincia(17500000)
  val cordoba = Provincia(2000000)

  describe("Vendedor fijo") {
    val obera = Ciudad(misiones)
    val vendedorFijo = VendedorFijo(obera)

    describe("puedeTrabajarEn") {
      it("su ciudad de origen") {
        vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
      }
      it("otra ciudad") {
        vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }

    it("esInfluyente") {
      vendedorFijo.esInfluyente().shouldBeFalse()
    }
  }

  describe("Viajante") {
    val villaDolores = Ciudad(cordoba)
    val viajante = Viajante(listOf(misiones))

    describe("puedeTrabajarEn") {
      it("una ciudad que pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("una ciudad que no pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }

    it("esInfluyente") {
      viajante.esInfluyente().shouldBeFalse()
    }
  }

  describe("Viajante2") {
    val viajante = Viajante(listOf(buenosAires))
    it("esInfluyente") {
      viajante.esInfluyente().shouldBeTrue()
    }
  }

  describe("Comercio") {
    val entreRios = Provincia(1300000)
    val santaFe = Provincia(3300000)
    val rosario = Ciudad(santaFe)
    val rafaela = Ciudad(santaFe)
    val diamante = Ciudad(entreRios)

    describe("Corresponsal1") {
      val chivilcoy = Ciudad(buenosAires)
      val bragado = Ciudad(buenosAires)
      val lobos = Ciudad(buenosAires)
      val pergamino = Ciudad(buenosAires)
      val zarate = Ciudad(buenosAires)
      val comercio = ComercioCorresponsal(listOf(chivilcoy, bragado, lobos, pergamino, zarate))

      it("esInfluyente") {
        comercio.esInfluyente().shouldBeTrue()
      }
    }

    describe("Corresponsal2") {
      val sanFrancisco = Ciudad(cordoba)
      val comercio = ComercioCorresponsal(listOf(rosario, rafaela, sanFrancisco, diamante))

      it("esInfluyente") {
        comercio.esInfluyente().shouldBeTrue()
      }
    }

    describe("Corresponsal3") {
      val armstrong = Ciudad(santaFe)
      val comercio = ComercioCorresponsal(listOf(rosario, rafaela, armstrong, diamante))

      it("esInfluyente") {
        comercio.esInfluyente().shouldBeFalse()
      }
    }
  }
})
