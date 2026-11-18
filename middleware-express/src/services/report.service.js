const BASE_URL = require('../config/config').SPRING_URL;

async function getReports(){
    const response = await fetch(`${BASE_URL}/reports`);
    return handleResponse(response);
}

async function findById(id){
    const response = await fetch(`${BASE_URL}/reports/${id}`);
    return handleResponse(response);
}

async function createReport(reportData) {
    const response = await fetch(`${BASE_URL}/reports`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reportData)
    });
    return handleResponse(response);
}

async function updateReport(reportId, reportData) {
    const response = await fetch(`${BASE_URL}/reports/${reportId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reportData)
    });
    
    return handleResponse(response);
}

async function deleteReport(reportId) {
    const response = await fetch(`${BASE_URL}/reports/${reportId}`, {
        method: 'DELETE'
    });
    return handleResponse(response);
}

async function handleResponse(response) {
    if (response.status === 204){
        return {status: response.status, data: null};
    }

    try {
        const data = await response.json();
        if (!response.ok){
            let message;
            if (response.status < 500 && data.message){
                message = data.message;
            }else {
                message = "Error en el servidor"
            }

            if (response.status >= 500) {
                console.error("Error de Spring: ", data.message);
            }
            return{status: response.status, data: {error: message}};
        }
        return {status: response.status, data: data};
    }catch (error) {
        console.error(error);
        return {status: 502, data: {error: "Error al procesar la respuesta del servidor"}};
    }
}

module.exports = { getReports, createReport, updateReport, deleteReport, findById };