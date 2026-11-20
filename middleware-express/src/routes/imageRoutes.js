const express = require("express");
const router = express.Router();
const upload = require("../config/upload");
const {uploadImage, getImage} = require("../controllers/imageController");

router.post("/", upload.single("file"), uploadImage);
router.get("/:fileName", getImage)
module.exports = router