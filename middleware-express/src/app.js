const express = require('express');
const { PORT } = require('./config/config');
const app = express();
const reportRoutes = require('./routes/reportRoutes');
app.use(express.json());

app.use('/api/reports', reportRoutes);
app.use((err, req, res, next) => {
    if (err.type === "entity.parse.failed"){
        return res.status(400).json({error: "Petición con JSON inválido"});
    }
    console.error(err);
    res.status(500).json({error: "Error interno del middleware"});
    
})
app.listen(PORT, () => {
    console.log(`Middleware escuchando en el puerto ${PORT}`);
});