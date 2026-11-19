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


module.exports = {handleResponse}