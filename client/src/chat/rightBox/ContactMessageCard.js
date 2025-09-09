import React from 'react';

function ContactMessageCard(props) {
    const { name, avatarSrc, messageText, timestamp } = props;

    return (
        <li className="d-flex justify-content-between mb-4">
            <div className="card w-100">
                <div className="card-header d-flex justify-content-between p-3">
                    <p className="text-muted small mb-0">
                        <i className="far fa-clock" /> {timestamp}
                    </p>
                    <p className="fw-bold mb-0">{name}</p>
                </div>
                <div className="card-body">
                    <p className="mb-0 message-text-right">
                        {messageText}
                    </p>
                </div>
            </div>
            <div className="image-container">
                <img
                    src={process.env.PUBLIC_URL + avatarSrc}
                    alt="avatar"
                    className="rounded-circle d-flex align-self-start ms-3 shadow-1-strong"
                    width={60}
                />
            </div>
        </li>
    );
}

export default ContactMessageCard;
