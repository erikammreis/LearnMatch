import android.util.Log
import java.util.Date

object UserInfo {
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

    var dateOfBirth: Date? = null
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

    var dateStart: Date? = null
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

}
