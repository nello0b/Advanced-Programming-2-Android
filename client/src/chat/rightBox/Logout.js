import React from 'react';
import { Link } from 'react-router-dom';

function Logout(props) {
    return (
        <div className="col-md-6 col-lg-7 col-xl-8 col">               
            <div className="row">
                <div className="d-flex justify-content-between align-items-center">
                    <div className="d-flex align-items-center">
                    <img 
                        src="/static/media/trump.7c2f632dd93185258d76.png"
                        alt="avatar" 
                        className="rounded-circle d-flex align-self-start me-3 shadow-1-strong" 
                        style={{ opacity: 0 }} 
                        width="60"></img>
                    </div>
                    <Link to="/" className="btn btn-danger">
                        Logout
                    </Link>
                </div>
                <div>
                   <hr/>
                </div>
            </div>
        </div>
    );
}

export default Logout;
