const express = require('express');
const { PORT } = require('./config/config');
const app = express();
const reportRoutes = require('./routes/reportRoutes');
const imageRouter = require('./routes/imageRoutes');
const multer = require('multer');
app.use(express.json());

app.use('/api/reports', reportRoutes);
app.use('/api/images', imageRouter);
app.use((err, req, res, next) => {
    if (err.type === "entity.parse.failed"){
        return res.status(400).json({error: "Petición con JSON inválido"});
    }

    if (err instanceof multer.MulterError && err.code === "LIMIT_FILE_SIZE"){
        return res.status(413).json({error: "La imagen no puede pesar más de 5MB"});
    }

    if (err instanceof multer.MulterError && err.code === "LIMIT_UNEXPECTED_FILE"){
        return res.status(400).json({error: "Solo se permite una imagen"});
    }
    console.error(err);
    res.status(500).json({error: "Error interno del middleware"});
    
})
app.listen(PORT, () => {
    console.log(`Middleware escuchando en el puerto ${PORT}`);
});