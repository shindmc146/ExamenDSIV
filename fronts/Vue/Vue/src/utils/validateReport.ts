import type { ReportPayload } from "@/schema/report.types";

const MANDATORY_FIELDS: Record<string,string> = {
    passengerName: "El nombre del pasajero es obligatorio",
    companyName: "El nombre de la empresa es obligatorio",
    type: "El tipo es obligatorio",
    subject: "El asunto es obligatorio",    
    description: "La descripción es obligatoria",
    tripDate: "La fecha de viaje es obligatoria"
}

const FIELD_LABELS: Record<string,string> = {
    passengerName: "El nombre de pasajero",
    companyName: "El nombre de empresa",
    type: "El tipo",
    subject: "El asunto",
    description: "La descripción",
    tripDate: "La fecha"
}

const VALID_TYPES = ["COMPLAINT", "LOSTITEM", "SUGGESTION"];

export function validateReport(data: ReportPayload, hasImage: boolean) {
    if (data === undefined || data === null || typeof data !== "object") {
        return "Los datos del reporte son obligatorios."
    }
    const { companyName, passengerName, type, subject, tripDate, description } = data;


    for (const field in MANDATORY_FIELDS) {
        const message = MANDATORY_FIELDS[field];
        const value = data[field as keyof ReportPayload]
        if (value === undefined || value === null) {
            return message
        }
        if (typeof value !== "string") {
            return `${FIELD_LABELS[field]} debe ser texto.`
        }
        if (value.trim() === "") {
            return message
        }
    }

    if (!hasImage){
        return "La imagen es obligatoria"
    }
    
    const name = passengerName.trim();

    if (name.length < 3) {
        return  "El nombre del pasajero debe tener al menos 3 caracteres." 
    }
    if (name.length > 30) {
        return "El nombre del pasajero no puede tener más de 30 caracteres." 
    }
    if (hasNumber(name)) {
        return "El nombre del pasajero no puede tener números." 
    }

    if (!onlyLetters(name)){
        return "Nombre de pasajero inválido, solo se permiten letras"
    }

    if (companyName.trim().length < 2) {
        return "El nombre de la empresa debe tener al menos 2 caracteres."
    }

    if (companyName.trim().length > 50) {
        return "El nombre de la empresa no puede tener más de 50 caracteres."
    }


    if (!VALID_TYPES.includes(type)) {
        return "Tipo inválido."
    }

    if (subject.trim().length < 5) {
        return "El asunto debe tener al menos 5 caracteres."
    }
        if (subject.trim().length > 30) {
        return "El asunto no puede tener más de 30 caracteres."
    }
    if (hasNumber(subject)){
        return  "El asunto no puede contener números"
    }
    if (!onlyLetters(subject)){
        return "Asunto inválido, solo se permiten letras"
    }

    if (description.trim().length < 10) {
        return "La descripción debe tener al menos 10 caracteres."
    }
    if (description.trim().length > 250) {
        return "La descripción no puede tener más de 250 caracteres."
    }


    if (!validDateFormat(tripDate)) {
        return "Formato de fecha inválido."
    }

    if (validDate(tripDate) != null) {
        return validDate(tripDate)
    }


    if (tripDate > todaysDate()) {
        return "La fecha de viaje no puede ser futura."
    }
    return null

}

function hasNumber(text:string) {
    for (let i = 0; i < text.length; i++) {
        if (isNumber(text.charAt(i))) {
            return true;
        }
    }
    return false;
}

function validDateFormat(text:string) {
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

function validDate(text:string){
    const year = text.substring(0, 4);
    const month = text.substring(5, 7);
    const day = text.substring(8, 10);

    if (Number(day) >= 32 || Number(day) <= 0) {
            return "Dia inválido";
        }

        if (Number(month) > 12 || Number(month) <= 0) {
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

        return null;
}


function onlyNumbers(text:string) {
    if (text.length === 0) {
        return false;
    }

    for (let i = 0; i < text.length; i++) {
        if (!isNumber(text.charAt(i))) {
            return false;
        }
    }
    return true;
}


function onlyLetters(text:string){
    for(let i = 0; i<text.length; i++){
        if (!isValidChar(text.charAt(i))){
            return false;
        }
    }
    return true;
}



function isNumber(num:string) {
    return num >= "0" && num <= "9";
}
function isValidChar(letter: string){
    const valid = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZáéíóúÁÉÍÓÚüÜ "
    for(let i = 0; i<valid.length; i++){
        if (letter === valid[i]){
            return true;
        }
    }
    return false;
}

function todaysDate(){
    const today = new Date();
    const year = today.getFullYear();
    const month = String((today.getMonth() + 1)).padStart(2,"0")
    const day = String((today.getDate())).padStart(2,"0")
    return `${year}-${month}-${day}`
}
