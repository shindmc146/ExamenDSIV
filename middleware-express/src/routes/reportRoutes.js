const express = require('express');
const router = express.Router();
const { getReports, createReport, updateReport, deleteReport, getReportById} = require('../controllers/reportController');

router.get('/', getReports);
router.get('/:id', getReportById);
router.post('/', createReport);
router.put('/:id', updateReport);
router.delete('/:id', deleteReport);

module.exports = router;