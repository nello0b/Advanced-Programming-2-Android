import React, { createContext, useEffect, useState } from 'react';
import { io } from 'socket.io-client';
import { TokenContext } from './TokenContext.js';

// Create the context
export const SocketContext = createContext();

// Create the context provider component
export const SocketContextProvider = ({ children }) => {
    // Define your state variables here
    const [socket, setSocket] = useState(null);
    const { username } = React.useContext(TokenContext);

    useEffect(() => {
        const newSocket = io('http://localhost:7000');
        setSocket(newSocket);
        newSocket.emit('UserConnect', username);
        return () => {
            newSocket.disconnect();
        };
    }, []);

    // Create an object with the values and functions to be passed down
    const contextValue = {
        socket,
    };

    // Render the provider component with the context value and children
    return (
        <SocketContext.Provider value={contextValue}>
            {children}
        </SocketContext.Provider>
    );
};
