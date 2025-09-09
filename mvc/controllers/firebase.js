const firebase =  require('../services/firebase')

// add new token
const addNewToken = async (req, res) => {
    const username = req.body.username;
    const token = req.body.fireBaseToken;
    firebase.addNewToken(username, token);
    res.status(200).json({ message: 'token added' });    
}

module.exports = { addNewToken }