package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class VendedorTest : DescribeSpec({
  val misiones = Provincia(1300000)
  val sanIgnacio = Ciudad(misiones)
  val buenosAires = Provincia(17500000)
  val cordoba = Provincia(2000000)
  val certDeproducto15 = Certificacion(true, 15)
  val otraCertificacion10 = Certificacion(false, 10)

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

    describe("esVersatil") {
      it("1 certificacion de productos y 1 que no es de productos)") {
        vendedorFijo.agregarCertificacion(certDeproducto15)
        vendedorFijo.agregarCertificacion(otraCertificacion10)
        vendedorFijo.esVersatil().shouldBeFalse()
      }

      it("2 certificaciones sobre productos y 1 que no es sobre productos") {
        //acá tenemos 1 certificacion de producto y otra que no es de producto
        vendedorFijo.agregarCertificacion(certDeproducto15) //y le agregamos una mas de producto
        vendedorFijo.esVersatil().shouldBeTrue()
      }

      it("3 certificaciones sobre productos") {
        //tenemos 2 sobre productos y 1 que no
        vendedorFijo.certificaciones.remove(otraCertificacion10) //quitamos la que no es de producto
        vendedorFijo.agregarCertificacion(certDeproducto15) // agregamos la 3° certif. de producto
        vendedorFijo.esVersatil().shouldBeFalse()
      }

      it("3 certificaciones que no son sobre productos") {
        vendedorFijo.certificaciones.remove(certDeproducto15) //quitamos las 3 certif. de producto
        vendedorFijo.certificaciones.remove(certDeproducto15)
        vendedorFijo.certificaciones.remove(certDeproducto15)
        vendedorFijo.agregarCertificacion(otraCertificacion10) //agregamos las 3 que no son de producto
        vendedorFijo.agregarCertificacion(otraCertificacion10)
        vendedorFijo.agregarCertificacion(otraCertificacion10)
        vendedorFijo.esVersatil().shouldBeFalse()
      }
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

    it("NO esInfluyente") {
      viajante.esInfluyente().shouldBeFalse()
    }

    describe("esFirme") {
      it("puntaje total de certificaciones >= 30") {
        viajante.agregarCertificacion(certDeproducto15)
        viajante.agregarCertificacion(certDeproducto15)
        viajante.esFirme().shouldBeTrue()
        viajante.certificaciones.remove(certDeproducto15)
      }

      it("puntaje total de certificaciones < 30") {
        viajante.agregarCertificacion(otraCertificacion10)
        viajante.esFirme().shouldBeFalse()
        //tenemos 25 puntos ya que hay 1 certificacion de producto del anterior test
      }
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

      describe("puedeTrabajarEn"){
        it("una ciudad en la que tiene sucursal") {
          comercio.puedeTrabajarEn(chivilcoy).shouldBeTrue()
        }

        it("una ciudad en la que NO tiene sucursal") {
          comercio.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
        }
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
