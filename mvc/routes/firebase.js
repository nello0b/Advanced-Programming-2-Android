const firebaseController = require('../controllers/firebase');

const express = require('express');
const router = express.Router();

router.route('/').post(firebaseController.addNewToken);

module.exports = router;