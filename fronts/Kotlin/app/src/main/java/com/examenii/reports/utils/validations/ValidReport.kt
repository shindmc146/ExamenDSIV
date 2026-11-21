package com.examenii.reports.utils.validations


private val VALID_TYPES = listOf("COMPLAINT", "LOSTITEM", "SUGGESTION")
private const val LETTERS = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZáéíóúÁÉÍÓÚüÜ "


fun validReport(passengerName : String, companyName : String, type : String, subject : String, description : String, tripDate : String, imageUri : String) : String{
    if (passengerName.isBlank()) {
        return "El nombre es obligatorio"
    }
    if (companyName.isBlank()) {
        return "El nombre de la empresa es obligatorio"
    }
    if (type.isBlank()) {
        return "El tipo es obligatorio"
    }
    if (subject.isBlank()) {
        return "El asunto es obligatorio"
    }
    if (description.isBlank()) {
        return "La descripción es obligatoria"
    }
    if (tripDate.isBlank()) {
        return "La fecha de viaje es obligatoria"
    }
    if (tripDate == "" || tripDate.isEmpty()){
         return "La fecha del viaje es obligatoria.";
    }

    val name = passengerName.trim()
    if (name.length < 3) {
        return "El nombre del pasajero debe tener al menos 3 caracteres."
    }
    if (name.length > 30) {
        return "El nombre del pasajero no puede tener más de 30 caracteres."
    }
    if (hasNumber(name)) {
        return "El nombre del pasajero no puede tener números."
    }
    if (!onlyLetters(name)) {
        return "Nombre de pasajero inválido, solo se permiten letras"
    }

    val company = companyName.trim()
    if (company.length < 2) {
        return "El nombre de la empresa debe tener al menos 2 caracteres."
    }
    if (company.length > 50) {
        return "El nombre de la empresa no puede tener más de 50 caracteres."
    }

    if (type !in VALID_TYPES) {
        return "Tipo inválido."
    }

    val subj = subject.trim()
    if (subj.length < 5) {
        return "El asunto debe tener al menos 5 caracteres."
    }
    if (subj.length > 30) {
        return "El asunto no puede tener más de 30 caracteres."
    }
    if (hasNumber(subject)) {
        return "El asunto no puede contener números"
    }
    if (!onlyLetters(subject)) {
        return "Asunto inválido, solo se permiten letras"
    }

    val desc = description.trim()
    if (desc.length < 10) {
        return "La descripción debe tener al menos 10 caracteres."
    }
    if (desc.length > 250) {
        return "La descripción no puede tener más de 250 caracteres."
    }

    return ""

}


private fun hasNumber(text: String): Boolean {
    for (c in text) {
        if (isNumber(c)) {
            return true
        }
    }
    return false
}

private fun isNumber(c: Char): Boolean {
    if (c >= '0' && c <= '9') {
        return true
    } else {
        return false
    }
}

private fun onlyNumbers(text: String): Boolean {
    if (text.isEmpty()) {
        return false
    }
    for (c in text) {
        if (!isNumber(c)) {
            return false
        }
    }
    return true
}

private fun onlyLetters(text: String): Boolean {
    for (c in text){
        if (!(c in LETTERS)) {
            return false
        }
    }
    return true
}
