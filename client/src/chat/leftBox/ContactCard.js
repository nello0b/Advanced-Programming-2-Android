import React from 'react';
import { useState, useContext } from 'react';
import { SelectedFriendContext } from '../../context/SelectedFriendContext';
function ContactCard(props) {
  const { setSelectedFriend } = useContext(SelectedFriendContext);
  const [isHovered, setIsHovered] = useState(false);
  let lastMessageContent = "";
  let lastMessageTimestamp = "";
  var lastMessage = props.chat.lastMessage;
  let formattedDateTime = "";
  const isTextGlowing = false;

  if (lastMessage) {
    if (lastMessage.content.length > 21) {
      lastMessageContent = lastMessage.content.slice(0, 15) + "...";
    } else {
      lastMessageContent = lastMessage.content;
    }

    lastMessageTimestamp = lastMessage.created;
    const dateTime = new Date(lastMessageTimestamp);
    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', hour12: false };
    formattedDateTime = dateTime.toLocaleString(undefined, options); // Assign the formatted date and time
  }

  const goToMessages = () => {
    setSelectedFriend(props.chat.user);
  };

  const handleMouseEnter = () => {
    setIsHovered(true);
  };

  const handleMouseLeave = () => {
    setIsHovered(false);
  };

  return (
    <li
      onClick={goToMessages}
      id="contact-card"
      className="p-2 border-bottom"
      style={{ backgroundColor: props.backgroundColor }}
    >
      <span
        className="d-flex justify-content-between"
        style={{
          textDecoration: 'none',
          cursor: isHovered ? 'pointer' : 'default'
        }}
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
      >
        <div className="d-flex flex-row">
          <div className="image-container">
            <img
              src={props.chat.user.profilePic}
              alt="avatar"
              className="rounded-circle d-flex align-self-center me-3 shadow-1-strong"
              width={60}
            />
          </div>
          <div className="pt-1">
            <p className={`fw-bold mb-0 ${isTextGlowing ? "glow": ""}`}>{props.chat.user.displayName}</p>
            <p className={`small text-muted ${isTextGlowing ? "glow": ""}`}>{lastMessageContent}</p>
          </div>
        </div>
        <div className="pt-1">
          {lastMessage ? (
            <p className="small text-muted mb-1">{formattedDateTime}</p>
          ) : (
            <p className="small text-muted mb-1">No messages</p>
          )}
        </div>
      </span>
    </li>
  );
}

export default ContactCard;
