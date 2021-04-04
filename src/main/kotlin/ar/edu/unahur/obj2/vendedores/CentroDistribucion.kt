package ar.edu.unahur.obj2.vendedores

class CentroDistribucion(val ciudad: Ciudad) {
    val vendedores = mutableListOf<Vendedor>()

    fun agregarVendedor(vendedor: Vendedor) {
        if(vendedores.contains(vendedor)) {
            error("El vendedor ya está registrado en éste Centro.")
        }
        vendedores.add(vendedor)
    }

    fun vendedorEstrella() = vendedores.maxBy { v -> v.puntajeCertificaciones() }
    fun puedeCubrir(ciudad: Ciudad) = vendedores.any { v -> v.puedeTrabajarEn(ciudad) }
    fun vendedoresGenericos() = vendedores.filter { v -> v.otrasCertificaciones() >= 1 }.toSet() //agregué el toSet() porque el enunciado dice "la colección", pero no es necesario
    fun esRobusto() = vendedores.count { v -> v.esFirme() } >= 3
}