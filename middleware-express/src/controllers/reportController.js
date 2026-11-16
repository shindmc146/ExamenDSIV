const {validateReport, validateId}  = require('../utils/validations/validateReport');
const { getReports:getReportsService, createReport: createReportService,updateReport: updateReportService,  deleteReport: deleteReportService, findById: findByIdService} = require('../services/report.service');

const getReports = async (req, res) => {
    try{
        const result = await getReportsService();
        res.status(result.status).json(result.data);
    }catch(error){
        res.status(502).json({error: "Error al llamar al servicio"});
        console.error(error);
    }
}

const getReportById = async (req, res) => {
    const reportId = req.params.id;
    const idCheck = validateId(reportId);
    if (idCheck.error) {
        return res.status(400).json({ error: idCheck.error });
    }
    try{
        const result = await findByIdService(reportId);
        return res.status(result.status).json(result.data);
    }catch(error){
        res.status(502).json({error: "Error al llamar al servicio"});
        console.error(error); 
    }
}


const createReport = async (req, res) => {
    const data = req.body;
    const validate = validateReport(data);
    if (validate.error) {
        return res.status(400).json({ error: validate.error });
    }
    try {
        const result = await createReportService(data);
        res.status(result.status).json(result.data);
    } catch (error) {
        res.status(502).json({error: "Error al llamar al servicio"});
        console.error(error);
    }
}

const updateReport = async (req, res) => {
    const reportId = req.params.id;
    const idCheck = validateId(reportId);
    if (idCheck.error) {
        return res.status(400).json({ error: idCheck.error });
    }
    const data = req.body;
    const validate = validateReport(data, true);
    if (validate.error) {
        return res.status(400).json({ error: validate.error });
    }
    try {
        const result = await updateReportService(reportId, data);
        res.status(result.status).json(result.data);
    } catch (error) {
        res.status(502).json({error: "Error al llamar al servicio"});
        console.error(error);
    }
}

const deleteReport = async (req, res) => {
    const reportId = req.params.id;
    const idCheck = validateId(reportId);
    if (idCheck.error) {
        return res.status(400).json({ error: idCheck.error });
    }
    try {
        const result = await deleteReportService(reportId);
        res.status(result.status).json(result.data);
    } catch (error) {
        res.status(502).json({error: "Error al llamar al servicio"});
        console.error(error);
    }
}

module.exports = { getReports, createReport, updateReport, deleteReport, getReportById };