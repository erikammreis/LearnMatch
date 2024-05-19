package br.com.fiap.learnmatch

data class UserData(
    var id: Long = 1,
    var email: String? = null,
    var password: String? = null,
    var type: String? = null,
    var saveLoginCheckBox: Boolean? = null,
    var name: String? = null,
    var cpf: String? = null,
    var dateOfBirth: String? = null,
    var cep: String? = null,
    var street: String? = null,
    var city: String? = null,
    var state: String? = null,
    var sex: String? = null,
    var age: Int? = null,
    var educationalInstitution: String? = null,
    var course: String? = null,
    var dateStart: String? = null,
    var typeTeaching: String? = null,
    var durationCourse: String? = null,
    var interest: Array<String>? = null,
    var operatingTime: String? = null,
    var occupationArea: String? = null,
    var office: String? = null,
    var experience: String? = null,
    var period: Array<String>? = null,
    var dayOfTheWeek: Array<String>? = null,
    var locationSettings: String? = null,
    var sexSettings: String? = null,
    var fieldOfWorkSettings: String? = null,
    var evaluationNote: Int? = null,
    var potentialMatch: Array<Long>?,
    var match: Array<Long>?

    ) {
}

