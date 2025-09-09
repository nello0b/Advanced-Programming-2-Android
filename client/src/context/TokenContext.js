import React, { createContext, useState } from 'react';

// Create the context
export const TokenContext = createContext();


// Create the context provider component
export const TokenContextProvider = ({ children }) => {
    // Define your state variables here
    const [token, setToken] = useState('');
    const [username, setUsername] = useState('');


    // Create an object with the values and functions to be passed down
    const contextValue = {
        token, setToken,
        username, setUsername
    };

    // Render the provider component with the context value and children
    return (
        <TokenContext.Provider value={contextValue}>
            {children}
        </TokenContext.Provider>
    );
};

