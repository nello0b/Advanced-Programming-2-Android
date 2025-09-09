import React, { useContext } from 'react';
import LeftBox from './leftBox/LeftBox';
import "./chat-screen.css"
import RightBox from './rightBox/RightBox';
import Modal from './Modal';
import Logout from './rightBox/Logout';
import { ChatContextProvider } from "../context/ChatContext.js";
import { SelectedFriendContext } from '../context/SelectedFriendContext.js';
import { UserContextProvider } from '../context/UserContext';
import { SocketContextProvider } from '../context/SocketContext';


function ChatScreen() {

    // Get the selected friend from SelectedFriendContext
    const { selectedFriend } = useContext(SelectedFriendContext);

    var rightBox;
    if (selectedFriend === null) {
        rightBox = <Logout />;
    } else {
        rightBox = <RightBox />;
    }

    return (
        <SocketContextProvider>
            <UserContextProvider>
                <ChatContextProvider>
                    <section>
                        <div className="container py-2 vh-100 d-flex">
                            <div className="row">
                                {/* Contacts Right Side */}
                                {<LeftBox />}
                                {/* Chat Left Side */}
                                {rightBox}
                                {/* Chat Input */}
                                <Modal />
                            </div>
                        </div>
                    </section>
                </ChatContextProvider>
            </UserContextProvider>
        </SocketContextProvider>
    );
}

export default ChatScreen;

