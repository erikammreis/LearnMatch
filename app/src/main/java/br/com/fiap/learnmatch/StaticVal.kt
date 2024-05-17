package br.com.fiap.learnmatch

object StaticVal {
    var currentJsonIndex: Int? = 0
        get() = field
        set(value) {
            field = value
        }
}