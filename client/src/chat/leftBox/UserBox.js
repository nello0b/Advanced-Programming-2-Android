import { useContext } from 'react';
import { UserContext } from '../../context/UserContext';

function UserBox() {
    const { user } = useContext(UserContext);    
    return (
        <div className="row">
            <div className="d-flex justify-content-between align-items-center">
                <div className="d-flex align-items-center">
                    <div className="image-container">
                        <img
                            src={user.profilePic}
                            alt="avatar"
                            className="rounded-circle d-flex align-self-start me-3 shadow-1-strong"
                            width={60}
                        />
                    </div>
                    <p className="fw-bold mb-0">{user.displayName}</p>
                </div>
                <button
                    type="button"
                    className="btn fas fa-user-plus down"
                    id="add-contact"
                    data-bs-toggle="modal"
                    data-bs-target="#Modal"
                ></button>
            </div>
            <div>
                <hr />
            </div>
        </div>
    );
}

export default UserBox;
