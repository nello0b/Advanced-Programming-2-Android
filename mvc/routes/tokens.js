const tokensController = require('../controllers/tokens');

const express = require('express');
const router = express.Router();

router.route('/').post(tokensController.createToken);

module.exports = router;