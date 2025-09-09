import { useContext } from 'react';
import { Link } from 'react-router-dom';
import { SelectedFriendContext } from '../../context/SelectedFriendContext';

function ContactBox() {
    const { selectedFriend } = useContext(SelectedFriendContext);
    return (
        <div className="row">
            <div className="d-flex justify-content-between align-items-center">
                <div className="d-flex align-items-center">
                    <div className="image-container">
                        <img
                            src={selectedFriend.profilePic}
                            alt="avatar"
                            className="rounded-circle d-flex align-self-start me-3 shadow-1-strong"
                            width={60}
                        />
                    </div>
                    <p className="fw-bold mb-0">{selectedFriend.displayName}</p>
                </div>
                <Link to="/" className="btn btn-danger">
                    Logout
                </Link>
            </div>
            <div>
                <hr />
            </div>
        </div>
    );
}

export default ContactBox;
