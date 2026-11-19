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

module.exports = {sendImage}