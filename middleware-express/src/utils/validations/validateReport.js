const MANDATORY_FIELDS = {
    passengerName: "El nombre del pasajero es obligatorio",
    companyName: "El nombre de la empresa es obligatorio",
    type: "El tipo es obligatorio",
    subject: "El asunto es obligatorio",    
    description: "La descripción es obligatoria",
    tripDate: "La fecha de viaje es obligatoria",
    pictureURL: "La imagen es obligatoria"
}

const FIELD_LABELS = {
    passengerName: "El nombre de pasajero",
    companyName: "El nombre de empresa",
    type: "El tipo",
    subject: "El asunto",
    description: "La descripción",
    tripDate: "La fecha",
    pictureURL: "La imagen"
}

const VALID_EXTENSIONS = [".jpg",".jpeg",".png",".webp"];

const VALID_TYPES = ["COMPLAINT", "LOSTITEM", "SUGGESTION"];

function validateReport(data) {
    if (data === undefined || data === null || typeof data !== "object") {
        return { error: "Los datos del reporte son obligatorios." };
    }
    const { companyName, passengerName, type, subject, pictureURL, tripDate, description, isUrgent } = data;


    for (const field in MANDATORY_FIELDS) {
        const message = MANDATORY_FIELDS[field];
        const value = data[field];
        if (value === undefined || value === null) {
            return { error: message }
        }
        if (typeof value !== "string") {
            return { error: `${FIELD_LABELS[field]} debe ser texto.` };
        }
        if (value.trim() === "") {
            return { error: message };
        }
    }

    if (!validFileName(pictureURL)){
        return {error : "La imagen no es válida."}
    }
    
    const name = passengerName.trim();

    if (name.length < 3) {
        return { error: "El nombre del pasajero debe tener al menos 3 caracteres." };
    }
    if (name.length > 30) {
        return { error: "El nombre del pasajero no puede tener más de 30 caracteres." };
    }
    if (hasNumber(name)) {
        return { error: "El nombre del pasajero no puede tener números." };
    }
     if (!onlyLetters(name)){
        return {error: "Nombre de pasajero inválido, solo se permiten letras"}
    }

    if (companyName.trim().length < 2) {
        return { error: "El nombre de la empresa debe tener al menos 2 caracteres." };
    }

    if (companyName.trim().length > 50) {
        return { error: "El nombre de la empresa no puede tener más de 50 caracteres." };
    }
    
    

   

    if (!VALID_TYPES.includes(type)) {
        return { error: "Tipo inválido." };
    }

    if (subject.trim().length < 5) {
        return { error: "El asunto debe tener al menos 5 caracteres." };
    }

    if (subject.trim().length > 30) {
        return { error: "El asunto no puede tener más de 30 caracteres." };
    }
    if (hasNumber(subject)){
        return {error: "El asunto no puede contener números"};
    }
    if (!onlyLetters(subject)){
        return {error: "Asunto inválido, solo se permiten letras"}
    }

    if (description.trim().length < 10) {
        return { error: "La descripción debe tener al menos 10 caracteres." };
    }
    if (description.trim().length > 250) {
        return { error: "La descripción no puede tener más de 250 caracteres." };
    }


    if (!validDateFormat(tripDate)) {
        return { error: "Formato de fecha inválido." };
    }

    if (validDate(tripDate) !== true) {
        return { error: validDate(tripDate) };
    }

    if (typeof isUrgent !== "boolean") {
        return { error: "La urgencia debe ser true o false." };
    }


    if (tripDate > todaysDate()) {
        return { error: "La fecha de viaje no puede ser futura." };
    }
    return { error: null };

}

function hasNumber(text) {
    for (let i = 0; i < text.length; i++) {
        if (isNumber(text[i])) {
            return true;
        }
    }
    return false;
}

function validDateFormat(text) {
    if (text.length !== 10) {
        return false;
    }

    if (text[4] !== "-" || text[7] != "-") {
        return false;
    }

    const year = text.substring(0, 4);
    const month = text.substring(5, 7);
    const day = text.substring(8, 10);

    return onlyNumbers(day) && onlyNumbers(month) && onlyNumbers(year);
}

function validDate(text){
    const year = text.substring(0, 4);
    const month = text.substring(5, 7);
    const day = text.substring(8, 10);

    if ((Number(day) >= 32 || Number(day) <= 0)) {
            return "Dia inválido";
        }
        if ((Number(month) > 12 || Number(month) <= 0)) {
            return "Mes inválido.";
        }

        if (Number(month) === 2 && Number(day) > 29) {
            return "Febrero no tiene más de 29 días.";
        }

        if (Number(month) === 2 && Number(day) === 29) {
            if (Number(year) % 4 !== 0 || Number(year) % 100 === 0 && Number(year) % 400 !== 0) {
                return "Febrero no tiene 29 días en un año no bisiesto.";
            }
        }

        if (Number(day) > 30){
            if (Number(month) === 4 || Number(month) === 6 || Number(month) ===  9|| Number(month) === 11){
                return "Ese mes no tiene más de 30 días.";
            }
        }
        


        
        const currentYear = new Date().getFullYear();
        if (Number(year) < 1970) {
            return "No se permiten años anteriores a 1970.";
        }
        if (Number(year) < currentYear - 5){
            return "El año no puede ser anterior a 5 años atrás.";
        }

        return true;
}


function onlyNumbers(text) {
    if (text.length === 0) {
        return false;
    }

    for (let i = 0; i < text.length; i++) {
        if (!isNumber(text[i])) {
            return false;
        }
    }
    return true;

}


function onlyLetters(text){
    for(let i = 0; i<text.length; i++){
        if (!isValidChar(text[i])){
            return false;
        }
    }
    return true;
}



function isNumber(num) {
    return num >= "0" && num <= "9";


}
function isValidChar(letter){
    const valid = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZáéíóúÁÉÍÓÚüÜ "
    for(let i = 0; i<valid.length; i++){
        if (letter === valid[i]){
            return true;
        }
    }
    return false;
}

function validLetter(letter){
    const valid = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    for (let i = 0; i < valid.length; i++) {
        if (letter === valid[i]){
            return true;
        }
    }

    return false;
}

function validFileName(fileName){
    let dotCount = 0;
    for (let i = 0; i < fileName.length; i++) {
        const c = fileName[i]
        if (c === '.'){
            dotCount++
        }else if (!validLetter(c) && !isNumber(c) && c !== '-'){
            return false
        }
    }

    if (dotCount != 1 || fileName.lastIndexOf(".") === 0){
        return false
    }

    const extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase()
    return VALID_EXTENSIONS.includes(extension);
}



function validateId(id) {
    if (!onlyNumbers(id) || Number(id) <= 0) {
        return { error: "El id debe ser un número entero positivo." };
    }
    return { error: null };
}

function todaysDate(){
    const today = new Date();
    const year = today.getFullYear();
    const month = String((today.getMonth() + 1)).padStart(2,"0")
    const day = String((today.getDate())).padStart(2,"0")
    return `${year}-${month}-${day}`
}
module.exports = { validateReport, validateId };