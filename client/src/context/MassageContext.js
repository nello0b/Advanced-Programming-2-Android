import React, { createContext, useState, useEffect, useContext } from 'react';
import { ChatContext } from './ChatContext';
import { SelectedFriendContext } from './SelectedFriendContext';
import { useNavigate } from 'react-router-dom';
import { SocketContext } from './SocketContext';
import { TokenContext } from './TokenContext';

// Create the context
export const MassageContext = createContext();

// Create the context provider component
export const MassagesContextProvider = ({ children }) => {
    // Define your state variables here
    const [Massages, setMassages] = useState([]); // Set initial value to an empty array
    const { selectedFriend } = useContext(SelectedFriendContext);
    const { chats, updateChats } = useContext(ChatContext);
    const { token } = useContext(TokenContext);
    const { socket } = useContext(SocketContext);
    const navigate = useNavigate();

    const getMassages = async () => {
        if (!token) {
            // Handle case where token doesn't exist
            console.error('Token not found');
            navigate('/');
            return;
        }
        if (selectedFriend === null) {
            return;
        }
        if (chats === null) {
            return;
        }
        // Find the chat with the selected friend
        const chat_with_contact = chats.find((chat) => {
            return chat.user.username === selectedFriend.username;
        });
        // Get the chat id
        const id = chat_with_contact.id;

        const res = await fetch("http://localhost:5000/api/Chats/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'authorization': `Bearer ${token}`
            },
        });
        if (res.status === 200) {
            const fullChatJson = await res.json();
            setMassages(fullChatJson);
        } else {
            console.error('Failed to fetch messages:', res.status);
            navigate('/');
        }
    };




    useEffect(() => {
        const handleNewMessage = () => {
            getMassages();
            updateChats();
        };
        if (socket) {
            socket.on('newMessage', handleNewMessage);
        }
        getMassages(); // Initial API call on component mount
        return () => {
            socket.off('newMessage', handleNewMessage);
        };
    }, [selectedFriend, chats, token]);

    // Create an object with the values and functions to be passed down
    const contextValue = {
        Massages,
        updateMassages: getMassages,
    };

    // Render the provider component with the context value and children
    return (
        <MassageContext.Provider value={contextValue}>
            {children}
        </MassageContext.Provider>
    );
};
