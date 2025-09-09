import React, { useRef, useEffect, useState, useContext } from 'react';
import ContactMessageCard from './ContactMessageCard';
import UserMessageCard from './UserMessageCard';
import ChatInput from './ChatInput';
import { SelectedFriendContext } from '../../context/SelectedFriendContext';
import { MassageContext } from '../../context/MassageContext';
import { UserContext } from '../../context/UserContext';

function ChatBox() {
    const { Massages, updateMassages } = useContext(MassageContext);
    const { selectedFriend, setSelectedFriend } = useContext(SelectedFriendContext);
    const { user } = useContext(UserContext);
    const chatContainerRef = useRef(null);
    const [handleSendMessage, setHandleSendMessage] = useState(null);
    const contact = selectedFriend;

    useEffect(() => {
        // Scroll to the bottom of the chat container
        chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
    }, [Massages]);

    return (
        <div className="row dialog">
            {/* Chat Cards */}
            <div className="overflow-auto" id="chat_container" ref={chatContainerRef}>
                {Massages && Massages.messages && (
                    <ul className="list-unstyled w-100">
                        {Massages.messages.map((messageJson, index) => {
                            const timeWithoutSeconds = messageJson.created.split("T")[1].slice(0, 5);
                            if (messageJson.sender.username === user.username) {
                                return (
                                    <UserMessageCard
                                        name={user.displayName}
                                        avatarSrc={user.profilePic}
                                        messageText={messageJson.content}
                                        timestamp={timeWithoutSeconds}
                                        key={index}
                                    />
                                );
                            } else {
                                return (
                                    <ContactMessageCard
                                        name={contact.displayName}
                                        avatarSrc={contact.profilePic}
                                        messageText={messageJson.content}
                                        timestamp={timeWithoutSeconds}
                                        key={index}
                                    />
                                );
                            }
                        })}
                    </ul>
                )}
            </div>
            {/* Chat Input */}
            <ChatInput setHandleSendMessage={setHandleSendMessage} handleSendMessage={handleSendMessage} />
        </div>
    );
}

export default ChatBox;
