import React, { useState, useContext } from 'react';
import { ChatContext } from "../../context/ChatContext.js";
import { SelectedFriendContext } from '../../context/SelectedFriendContext';
import { MassageContext } from '../../context/MassageContext';
import { useNavigate } from 'react-router-dom';
import { SocketContext } from '../../context/SocketContext';
import { TokenContext } from '../../context/TokenContext';

function ChatInput(props) {
    const { chats, updateChats } = useContext(ChatContext);
    const { selectedFriend } = useContext(SelectedFriendContext);
    const { updateMassages } = useContext(MassageContext);
    const { socket } = useContext(SocketContext);
    const { token } = useContext(TokenContext);
    const navigate = useNavigate();

    const [message, setMessage] = useState('');

    const handleInputChange = (e) => {
        setMessage(e.target.value);
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSendMessage();
        }
    };

    const handleSendMessage = async () => {
        if (!token) {
            // Handle case where token doesn't exist
            console.error('Token not found');
            navigate('/');
            return;
        }
        if (message.trim() === '') {
            // Empty message, do nothing
            return;
        }

        //find the chat with the selected friend
        const chat_with_contact = chats.find((chat) => {
            return chat.user.username === selectedFriend.username
        });
        //get the chat id
        const id = chat_with_contact.id;
        const urlMessage = `http://localhost:5000/api/Chats/${id}/Messages`;
        const fetchChatGet = async () => {
            const res = await fetch(urlMessage, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    "msg": message,
                })
            });
            if (res.status !== 200) {
                console.error('Failed to add massage:', res.status);
                navigate('/');
            }
        };
        if (socket) {
            socket.emit('addMessage', selectedFriend.username);
            socket.emit('addChat', selectedFriend.username);
        }
        await fetchChatGet();
        await updateMassages();
        await updateChats();
        setMessage('');
    };

    return (
        <div className="card mb-3 p-0" id="input">
            <div className="card-body">
                <div className="input-group">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Want to ChitChat?"
                        value={message}
                        onChange={handleInputChange}
                        onKeyPress={handleKeyPress} // Added event handler for Enter key press
                    />
                    <div className="input-group-append">
                        <button
                            className="btn btn-outline-secondary"
                            type="button"
                            onClick={handleSendMessage}
                        >
                            Send
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ChatInput;
