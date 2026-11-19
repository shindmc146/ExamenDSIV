const express = require("express");
const router = express.Router();
const upload = require("../config/upload");
const {uploadImage} = require("../controllers/imageController");

router.post("/", upload.single("file"), uploadImage);

module.exports = router