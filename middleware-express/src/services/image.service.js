const BASE_URL = require('../config/config').SPRING_URL;

const {handleResponse} = require("../utils/handleResponse");



async function sendImage(file){
    const form = new FormData();
    form.append("file", 
    new Blob([file.buffer],
        {type: file.mimetype}),
        file.originalname);

        const response = await fetch(`${BASE_URL}/images`, {method: "POST",body: form});

        return handleResponse(response);
}

async function getImage(fileName){
    const response = await fetch(`${BASE_URL}/uploads/reports/${fileName}`)
    console.log(`${BASE_URL}/images/${fileName}`)
    if (response.ok){
        return {
            status: 200,
            contentType: response.headers.get("content-type"),
            bytes : Buffer.from(await response.arrayBuffer())
        }
    }else {
        return {status: 404, data: {error: "La imagen no existe"}}
    }
}



module.exports = {sendImage, getImage}