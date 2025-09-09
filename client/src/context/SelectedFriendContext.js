import React, { createContext, useState } from 'react';

// Create the context
export const SelectedFriendContext = createContext();


// Create the context provider component
export const SelectedFriendProvider = ({ children }) => {
    // Define your state variables here
    const [selectedFriend, setSelectedFriend] = useState(null);


    // Create an object with the values and functions to be passed down
    const contextValue = {
        selectedFriend, setSelectedFriend
    };

    // Render the provider component with the context value and children
    return (
        <SelectedFriendContext.Provider value={contextValue}>
            {children}
        </SelectedFriendContext.Provider>
    );
};

