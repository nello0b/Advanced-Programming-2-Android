const usersService = require('../services/users.js');

const getUserByUsername = async (req, res) => {
    const user = await usersService.getUserByUsername(req.params.username);
    if (!user) {
        return res.status(404).json({ error: 'User not found' });
    }
    res.status(200).json(user); 
}

const createUser = async (req, res) => {
    const user = await usersService.createUser(req.body.username, req.body.password, req.body.displayName, req.body.profilePic);
    if (!user) {
        return res.status(404).json({ error: 'didnt create user' });
    }
    res.status(200).json(user); 
}

module.exports = { getUserByUsername, createUser }
