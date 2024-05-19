package br.com.fiap.learnmatch

object StaticStudentIndex {
    var currentJsonIndex: Int? = 0
        get() = field
        set(value) {
            field = value
        }
}