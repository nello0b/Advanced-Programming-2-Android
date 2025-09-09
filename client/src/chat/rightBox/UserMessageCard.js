import React from 'react';

function UserMessageCard(props) {
  const { name, avatarSrc, messageText, timestamp } = props;

  return (
    <li className="d-flex justify-content-between mb-4">
      <div className="image-container">
        <img
          src={avatarSrc}
          alt="avatar"
          className="rounded-circle d-flex align-self-start me-3 shadow-1-strong"
          width={60}
        />
      </div>
      <div className="card w-100">
        <div className="card-header d-flex justify-content-between p-3">
          <p className="fw-bold mb-0">{name}</p>
          <p className="text-muted small mb-0">
            <i className="far fa-clock" /> {timestamp}
          </p>
        </div>
        <div className="card-body">
          <p className="mb-0 message-text-left">
            {messageText}
          </p>
        </div>
      </div>
    </li>
  );
}

export default UserMessageCard;
