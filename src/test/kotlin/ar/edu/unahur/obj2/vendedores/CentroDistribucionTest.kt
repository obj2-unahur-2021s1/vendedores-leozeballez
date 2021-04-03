package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec

class CentroDistribucionTest : DescribeSpec({
    val buenosAires = Provincia(15000000)
    val moron = Ciudad(buenosAires)
    val centroMoron = CentroDistribucion(moron)
})