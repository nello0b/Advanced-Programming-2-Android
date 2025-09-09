const tokensService = require('../services/tokens.js');
const firebase = require('../services/firebase');

const createToken = async (req, res) => {
    const token = await tokensService.createToken(req.body.username, req.body.password);
    if (!token) {
        return res.status(404).json({ error: `didnt created a token` });
    }
    firebase.removeToken(req.body.username);
    res.status(200).json(token);
}

const getUsernameFromToken = async (req, res) => {
    if (req.headers.authorization) {
        // return the token but with like this "TOKEN" at the end of it (with quotes marks)
        const token = req.headers.authorization.split(" ")[1];
        // remove the quotes marks from the token
        const tokenWithOutQuotesMarks = token.substring(1, token.length - 1);
        try {
            let returnVal = tokensService.getUsernameFromToken(tokenWithOutQuotesMarks);
            return returnVal;
        }
        catch (err) {
            return res.status(401).send("Invalid Token");
        }
    }
    else
        return res.status(403).send('Token required');
};

module.exports = { createToken, getUsernameFromToken }
