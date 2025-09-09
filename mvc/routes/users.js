const usersController = require('../controllers/users');

const express = require('express');
const router = express.Router();

router.route('/:username').get(usersController.getUserByUsername);
router.route('/').post(usersController.createUser);

module.exports = router;
