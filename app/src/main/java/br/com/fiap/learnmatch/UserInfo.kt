
import android.content.Context
import android.util.Log
import br.com.fiap.learnmatch.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileWriter

object UserInfo {
    private const val USER_INFO_FILE_NAME = "userInfo.json"

    var id: Long = 1
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

    var cpf: String? = null
        get() = field
        set(value) {
            field = value
        }

    var dateOfBirth: String? = null
        get() = field
        set(value) {
            field = value
        }

    var cep: String? = null
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

    var period: Array<String>? = null
        get() = field
        set(value) {
            field = value
        }

    var dayOfTheWeek: Array<String>? = null
        get() = field
        set(value) {
            field = value
        }


    var locationSettings: String? = null
        get() = field
        set(value) {
            field = value
        }

    var sexSettings: String? = null
        get() = field
        set(value) {
            field = value
        }

    var fieldOfWorkSettings: String? = null
        get() = field
        set(value) {
            field = value
        }

    var evaluationNote: Int? = null
        get() = field
        set(value) {
            field = value
        }

    var potentialMatch: Array<Long>? = null
        get() = field
        set(value) {
            field = value
            // Adiciona um log para imprimir o valor de interest
            Log.i("@erika", "Interest: ${field?.contentToString()}")
        }
    var match: Array<Long>? = null
        get() = field
        set(value) {
            field = value
            // Adiciona um log para imprimir o valor de interest
            Log.i("@erika", "Interest: ${field?.contentToString()}")
        }

    var chats: Array<Long>? = arrayOf(3,2,4,12,11,10)
        get() = field
        set(value) {
            field = value
            // Adiciona um log para imprimir o valor de interest
            Log.i("@erika", "Interest: ${field?.contentToString()}")
        }
    fun valorId() : Long{
        if(type.equals("Mentor")){
            return 9
        }else{
            return 1
        }
    }
    fun salveAndGetJson(context: Context):String {
        val user = User()
        user.insertData(
            id = valorId(),
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
            experience = experience,
            period = arrayOf(""),
            dayOfTheWeek = arrayOf(""),
            locationSettings = "",
            sexSettings = "",
            fieldOfWorkSettings = "",
            evaluationNote = 0 ,
            potentialMatch = arrayOf(),
            match = arrayOf(),
            chats = arrayOf(),
        )
        setUserInf(context, user)
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(user)

    }

    public fun getUserInf(context: Context): User {
        val jsonString = readUserInfoFromFile(context)
        return Gson().fromJson(jsonString, User::class.java) ?: User()
    }

    private  fun setUserInf(context: Context, userInfo: User) {
        val json = Gson().toJson(userInfo)
        writeUserInfoToFile(context, json)
    }

    private fun readUserInfoFromFile(context: Context): String {
        return try {
            val file = File(context.filesDir, USER_INFO_FILE_NAME)
            if (file.exists()) {
                file.readText()
            } else {
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

     fun writeUserInfoToFile(context: Context, json: String) {
        try {
            val file = File(context.filesDir, USER_INFO_FILE_NAME)
            FileWriter(file).use { it.write(json) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun salveAndGetJsonUser(context: Context ,user : User):String {
        user.insertData(
            id = valorId(),
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
            experience = experience,
            period = arrayOf(""),
            dayOfTheWeek = arrayOf(""),
            locationSettings = "",
            sexSettings = "",
            fieldOfWorkSettings = "",
            evaluationNote = 0 ,
            potentialMatch = arrayOf(),
            match = arrayOf(),
            chats = arrayOf(),
        )
        setUserInf(context, user)
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(user)

    }



}
