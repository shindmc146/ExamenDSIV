const VALID_EXTENSIONS = [".jpg","jpeg",".png",".webp"];
const VALID_MIME_TYPES = ["image/jpeg","image/png","image/webp"];
function validImage(file) {
       if (file === undefined || file === null){
            return {error: "La imagen es obligatoria"}
       }

       const originalName = file.originalname;
       console.log(typeof(originalName));
       const extension = originalName.substring(originalName.lastIndexOf("."));

       if (!VALID_EXTENSIONS.includes(extension)){
            return {error: "Extensión de archivo inválida. Solo se permiten imagenes jpg, jpeg, png y webp"}
       }

       if (!VALID_MIME_TYPES.includes(file.mimetype)){
        return {error: "Tipo de archivo inválido"}
       }


       if (extension !== "")
       return {error:null};
}

module.exports=validImage