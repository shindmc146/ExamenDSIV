const BASE_URL = require('../config/config').SPRING_URL;

const {handleResponse} = require("../utils/handleResponse");

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



module.exports = { getReports, createReport, updateReport, deleteReport, findById };