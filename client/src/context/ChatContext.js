import React, { createContext, useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { SocketContext } from './SocketContext';
import { TokenContext } from './TokenContext';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

// Create the context
export const ChatContext = createContext();

// Create the context provider component
export const ChatContextProvider = ({ children }) => {
    // Define your state variables here
    const [chats, setChats] = useState([]);
    const { socket } = useContext(SocketContext);
    const { token } = useContext(TokenContext);

    const urlGetChats = "http://localhost:5000/api/Chats";

    const navigate = useNavigate();

    // API call to get the chat list
    const getChats = async () => {
        if (!token) {
            // Handle case where token doesn't exist
            console.error('Token not found');
            navigate('/');
            return;
        }

        const res = await fetch(urlGetChats, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                authorization: `Bearer ${token}`,
            },
        });
        if (res.ok) {
            const jsonChat = await res.json();
            setChats(jsonChat);
        } else {
            // Handle error when the response is not ok
            console.error('Failed to fetch chats:', res.status);
            navigate('/');
        }
    };

    if (socket) {
        socket.on('newChat', async () => {
            await getChats(); 
        });
    }

    useEffect(() => {
        const handleNewChat = async () => {
            await getChats();
        };
        if (socket) {
            socket.on('newChat', handleNewChat);
        }
        getChats(); // Initial API call on component mount
        return () => {
            socket.off('newChat', handleNewChat);
          };
    }, []);


    useEffect(() => {
        const handleNewChat = async () => {
            await getChats();
        };
        if (socket) {
            socket.on('newChat', handleNewChat);
        }
        getChats(); // Initial API call on component mount
        return () => {
            socket.off('newChat', handleNewChat);
          };
    }, [token]);

    // Create an object with the values and functions to be passed down
    const contextValue = {
        chats,
        updateChats: getChats,
    };

    // Render the provider component with the context value and children
    return (
        <ChatContext.Provider value={contextValue}>
            {children}
             <ToastContainer />
        </ChatContext.Provider>
    );
};
