package br.com.fiap.learnmatch

class SettingsUser {

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

    public fun default() {
        period = null
        dayOfTheWeek = null
        locationSettings = null
        sexSettings = null
        fieldOfWorkSettings = null
    }

     fun insertSettings(
        period: Array<String>,
        dayOfTheWeek: Array<String>,
        locationSettings: String,
        sexSettings: String,
        fieldOfWorkSettings: String,
    ) {
        this.period = period
        this.dayOfTheWeek = dayOfTheWeek
        this.locationSettings = locationSettings
        this.sexSettings = sexSettings
        this.fieldOfWorkSettings = fieldOfWorkSettings

    }

    public fun checkSetupDefault(): Boolean {
        if (period == null || dayOfTheWeek == null || locationSettings == null || sexSettings == null || fieldOfWorkSettings == null) {
            return true
        } else {
            return false
        }

    }

}