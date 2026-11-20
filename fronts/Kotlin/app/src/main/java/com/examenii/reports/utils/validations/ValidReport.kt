package com.examenii.reports.utils.validations

fun validReport(name : String, companyName : String, type : String, subject : String, description : String, tripDate : String, imageUri : String) : String{
    return if (name == "" || name.isEmpty()){
        "El nombre es obligatorio.";
    }else if (companyName == "" || companyName.isEmpty()){
        "El nombre de la empresa es obligatorio.";
    }else if (type == "" || type.isEmpty()){
         "El tipo de reporte es obligatorio.";
    }else if (subject == "" || subject.isEmpty()){
         "El asunto es obligatorio.";
    } else if (description == "" || description.isEmpty()){
         "La descripción es obligatoria.";
    }
    else if (tripDate == "" || tripDate.isEmpty()){
         "La fecha del viaje es obligatoria.";
    }else{
        ""
    }

}