var tokens = [];


const addNewToken = ((username, token) => {
    const existingIndex = tokens.findIndex((item) => item.token === token);

    if (existingIndex !== -1) {
        const existingItem = tokens[existingIndex];
        if (existingItem.username === username) {
            // The token/user pair already exists, no changes needed
            return;
        }

        tokens.splice(existingIndex, 1, {
            username: username,
            token: token
        });
    } else {
        tokens.push({
            username: username,
            token: token
        });
    }
});

const removeToken = ((username) => {
    const index = tokens.findIndex((item) => item.username === username);
    if (index !== -1) {
        tokens.splice(index, 1);
    }
});


const getTokens = (() => {
    return tokens;
});

module.exports = { addNewToken, removeToken, getTokens }