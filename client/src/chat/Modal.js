import React, { useState, useRef, useContext } from 'react';
import { ChatContext } from '../context/ChatContext.js';
import { UserContext } from '../context/UserContext.js';
import { useNavigate } from 'react-router-dom';
import { SocketContext } from '../context/SocketContext.js';
import { TokenContext } from '../context/TokenContext.js';

function Modal(props) {
    const [newFriend, setNewFriend] = useState("");
    const modalRef = useRef(null);
    const { chats, updateChats } = useContext(ChatContext);
    const { user } = useContext(UserContext);
    const navigate = useNavigate();
    const { socket } = useContext(SocketContext);
    const { token } = useContext(TokenContext);

    const addFriend = async () => {
        if (!token) {
            // Handle case where token doesn't exist
            console.error('Token not found');
            modalRef.current.click(); // Programmatically invoke the click event of the close button
            navigate('/');
            return;
        }
        if (newFriend === user.username) {
            alert(`cannot add yourself`);
            return;
        }
        for (const item of chats) {
            if (item.user.username === newFriend) {
                // Perform actions or access properties of the matched item
                alert(`${newFriend} is already in the friend list.`);
                return;
            }
        }
        const urlChat = `http://localhost:5000/api/Chats`;
        //the user not in the chat array so we need to a new chat
        const fetchChatPost = async () => {
            const res = await fetch(urlChat, {
                method: 'POST', // Corrected method from 'GET' to 'POST'
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': `Bearer ${token}`,
                },
                body: JSON.stringify({
                    "username": newFriend,
                })
            });
            switch (res.status) {
                case 200:
                    return true;
                case 400:
                    alert(`The username ${newFriend} does not exist.`);
                    break;
                case 404:
                default:
                    console.error('Failed to add a chat:', res.status);
                    modalRef.current.click(); // Programmatically invoke the click event of the close button
                    navigate('/');
                    break;
            }
            return false;
        }
        fetchChatPost().then(res => {
            if (res) {
                updateChats();
                if (socket) {
                    socket.emit('addChat', newFriend);
                }
                setNewFriend(""); // Clear the newFriend state
                modalRef.current.click(); // Programmatically invoke the click event of the close button
            }
        });
    };

    return (
        <div className="modal fade" id="Modal" tabIndex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="ModalLabel">Add to Contacts</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" ref={modalRef}></button>
                    </div>
                    <div className="modal-body">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Contact's Username"
                            value={newFriend}
                            onChange={(e) => setNewFriend(e.target.value)}
                        />
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" className="btn btn-success" onClick={addFriend}>Add</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Modal;
