
import android.util.Log
import br.com.fiap.learnmatch.User
import com.google.gson.GsonBuilder
import java.util.Date

object UserInfo {
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

    init {
        id = Date().time
    }
    fun toJson(): String {
        val user = User()
        user.insertData(
            id = id,
            email = email,
            password = password,
            type = type,
            saveLoginCheckBox = saveLoginCheckBox,
            name = name,
            cpf = cpf,
            dateOfBirth = dateOfBirth,
            cep = cep,
            street = street,
            city = city,
            state = state,
            sex = sex,
            age = age,
            educationalInstitution = educationalInstitution,
            course = course,
            dateStart = dateStart,
            typeTeaching = typeTeaching,
            durationCourse = durationCourse,
            interest = interest,
            operatingTime = operatingTime,
            occupationArea = occupationArea,
            office = office,
            experience = experience
        )
        Log.i("@erika", email.toString() + " " + id.toString())
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(user)
    }


}
