package br.com.fiap.learnmatch

import android.util.Log

class User {
    var id: Long = 0
    var email: String? = null
        get() = field
        set(value) {
            field = value
        }

    var password: String? = null
        get() = field
        set(value) {
            field = value
        }

    var type: String? = null
        get() = field
        set(value) {
            field = value
        }

    var saveLoginCheckBox: Boolean? = null
        get() = field
        set(value) {
            field = value
        }

    var name: String? = null
        get() = field
        set(value) {
            field = value
        }

    var cpf: Int? = null
        get() = field
        set(value) {
            field = value
        }

    var dateOfBirth: String? = null
        get() = field
        set(value) {
            field = value
        }

    var cep: Int? = null
        get() = field
        set(value) {
            field = value
        }

    var street: String? = null
        get() = field
        set(value) {
            field = value
        }

    var city: String? = null
        get() = field
        set(value) {
            field = value
        }

    var state: String? = null
        get() = field
        set(value) {
            field = value
        }

    var sex: String? = null
        get() = field
        set(value) {
            field = value
        }

    var age: Int? = null
        get() = field
        set(value) {
            field = value
        }

    var educationalInstitution: String? = null
        get() = field
        set(value) {
            field = value
        }

    var course: String? = null
        get() = field
        set(value) {
            field = value
        }

    var dateStart: String? = null
        get() = field
        set(value) {
            field = value
        }

    var typeTeaching: String? = null
        get() = field
        set(value) {
            field = value
        }

    var durationCourse: String? = null
        get() = field
        set(value) {
            field = value
        }

    var interest: Array<String>? = null
        get() = field
        set(value) {
            field = value
            // Adiciona um log para imprimir o valor de interest
            Log.i("@erika", "Interest: ${field?.contentToString()}")
        }

    var operatingTime: String? = null
        get() = field
        set(value) {
            field = value
        }

    var occupationArea: String? = null
        get() = field
        set(value) {
            field = value
        }

    var office: String? = null
        get() = field
        set(value) {
            field = value
        }

    var experience: String? = null
        get() = field
        set(value) {
            field = value
        }


    fun insertData(
        id: Long?,
        email: String?,
        password: String?,
        type: String?,
        saveLoginCheckBox: Boolean?,
        name: String?,
        cpf: Int?,
        dateOfBirth: String?,
        cep: Int?,
        street: String?,
        city: String?,
        state: String?,
        sex: String?,
        age: Int?,
        educationalInstitution: String?,
        course: String?,
        dateStart: String?,
        typeTeaching: String?,
        durationCourse: String?,
        interest: Array<String>?,
        operatingTime: String?,
        occupationArea: String?,
        office: String?,
        experience: String?
    ) {
        this.id = id ?: 0
        this.email = email
        this.password = password
        this.type = type
        this.saveLoginCheckBox = saveLoginCheckBox
        this.name = name
        this.cpf = cpf
        this.dateOfBirth = dateOfBirth
        this.cep = cep
        this.street = street
        this.city = city
        this.state = state
        this.sex = sex
        this.age = age
        this.educationalInstitution = educationalInstitution
        this.course = course
        this.dateStart = dateStart
        this.typeTeaching = typeTeaching
        this.durationCourse = durationCourse
        this.interest = interest
        this.operatingTime = operatingTime
        this.occupationArea = occupationArea
        this.office = office
        this.experience = experience
    }



}