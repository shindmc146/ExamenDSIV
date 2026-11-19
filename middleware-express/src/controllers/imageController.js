
const {sendImage} = require("../services/image.service");
const validImage = require("../utils/validations/validateImage");

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

module.exports = {uploadImage}