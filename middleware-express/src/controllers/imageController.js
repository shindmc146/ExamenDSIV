
const {sendImage, getImage: getImageService} = require("../services/image.service");
const validImage = require("../utils/validations/validateImage");


const getImage = async(req,res) => {
    // validar aqui el nombre de la imagen para evitar que se metan a otros endpointsw
    try {
        const fileName = req.params.fileName
        console.log(fileName)
        const result = await getImageService(fileName);
        
        if (result.bytes){
            res.set("Content-Type", result.contentTypew);
            res.send(result.bytes);
        }else {
            res.status(result.status).json(result.data);
        }
    } catch (error) {
        res.status(502).json({error: "Error al llamar al servicio"});
        console.error(error)
    } 
}


const uploadImage = async(req,res) => {
    const validate = validImage(req.file);
    if (validate.error){
        return res.status(400).json({error: validate.error});
    }
    try {
        const result = await sendImage(req.file);
        res.status(result.status).json(result.data);
    } catch (error) {
        res.status(502).json({error: "Error al llamar al servicio"})
        console.error(error);
    }
}



module.exports = {uploadImage, getImage}