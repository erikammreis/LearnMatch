package br.com.fiap.learnmatch

class SettingsUser {

    var period: Array<String>? = arrayOf("Manhã", "Tarde", "Noite")
        get() = field
        set(value) {
            field = value
        }

    var dayOfTheWeek: Array<String>? = arrayOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom")
        get() = field
        set(value) {
            field = value
        }


    var locationSettings: String? = "Todas"
        get() = field
        set(value) {
            field = value
        }

    var sexSettings: String? = "Todos"
        get() = field
        set(value) {
            field = value
        }

    var fieldOfWorkSettings: String? = null
        get() = field
        set(value) {
            field = value
        }

    public fun default() : SettingsManager {
        val settingsUser = SettingsManager
        period = arrayOf("Manhã", "Tarde", "Noite")
        dayOfTheWeek = arrayOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom")
        locationSettings = "Todas"
        sexSettings = "Todos"
        fieldOfWorkSettings = null
        return settingsUser
    }

     fun insertSettings(
        period: Array<String>?,
        dayOfTheWeek: Array<String>?,
        locationSettings: String?,
        sexSettings: String?,
        fieldOfWorkSettings: String?,
    ) {
        this.period = period
        this.dayOfTheWeek = dayOfTheWeek
        this.locationSettings = locationSettings
        this.sexSettings = sexSettings
        this.fieldOfWorkSettings = fieldOfWorkSettings

    }

    public fun checkSetupDefault(): Boolean {
        if (period.toString() == "[Manhã, Tarde, Noite]"
            && dayOfTheWeek.toString() == "[Seg, Ter, Qua, Qui, Sex, Sab, Dom]" && locationSettings == "Todas" && sexSettings == "Todos" && fieldOfWorkSettings == "Todas") {
            return true
        } else {
            return false
        }

    }

}