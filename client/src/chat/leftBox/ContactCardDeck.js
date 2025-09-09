import React, { useContext, useRef, useState, useEffect } from 'react';
import ContactCard from './ContactCard';
import { ChatContext } from "../../context/ChatContext.js";
import { SelectedFriendContext } from '../../context/SelectedFriendContext';

function getUsernameListByUserList(usersList) {
    return usersList.map((user) => {
        return user.username;
    });
}

function ContactCardDeck(props) {
    const { selectedFriend } = useContext(SelectedFriendContext);
    const [isFiltered, setIsFiltered] = useState(false);
    const [filteredChatList, setFilteredChatList] = useState([]);
    const searchInput = useRef(null);
    const { chats } = useContext(ChatContext);

    useEffect(() => {
        // Update the chat list using the context value instead of making an API call
        setFilteredChatList(chats);
    }, [chats]);

    const search = () => {
        const textSearch = searchInput.current.value;

        if (textSearch !== "") {
            const filteredChats = chats.filter((chat) => {
                return chat.user.displayName.toLowerCase().startsWith(textSearch.toLowerCase());
            });
            setFilteredChatList(filteredChats);
            setIsFiltered(true);
        } else {
            setIsFiltered(false);
        }
    };

    var liComp;
    if (!isFiltered) {
        liComp = chats.map((item, index) => {
            var backgroundColor = "white";
            if (selectedFriend != null) {
                if (selectedFriend.username === item.user.username) {
                    backgroundColor = "#eee";
                } else {
                    backgroundColor = "white";
                }
            }

            return (
                <ContactCard key={index} chat={item} backgroundColor={backgroundColor} />
            );
        });
    } else {
        liComp = filteredChatList.map((item, index) => {
            var backgroundColor = "white";
            if (selectedFriend != null) {
                if (selectedFriend.username === item.user.username) {
                    backgroundColor = "#eee";
                } else {
                    backgroundColor = "white";
                }
            }

            return (
                <ContactCard key={index} chat={item} backgroundColor={backgroundColor} />
            );
        });
    }

    return (
        <div className="row" id="contacts">
            {/* Contacts Cards */}
            <div className="card mb-2 p-0">
                <div className="card-body">
                    <div className="input-group pb-1">
                        <input
                            ref={searchInput}
                            type="text"
                            className="form-control"
                            placeholder="Search..."
                            aria-describedby="basic-addon2"
                        />
                        <div className="input-group-append">
                            <button onClick={search} className="btn btn-outline-secondary" type="button">
                                Search
                            </button>
                        </div>
                    </div>
                    <ul className="list-unstyled mb-0 overflow-auto">
                        {liComp}
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default ContactCardDeck;

