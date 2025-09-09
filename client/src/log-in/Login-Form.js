import React, { useEffect, useRef, useContext } from "react";
import { useNavigate, Link } from 'react-router-dom';
import { SelectedFriendContext } from "../context/SelectedFriendContext.js";
import { TokenContext } from "../context/TokenContext.js";

function LoginForm(props) {
    const { setToken, setUsername } = useContext(TokenContext);
    setToken('');
    setUsername('');
    const { setSelectedFriend } = useContext(SelectedFriendContext);

    const usernameInput = useRef(null);
    const passwordInput = useRef(null);
    const spanRef = useRef(null);
    const urlToken = "http://localhost:5000/api/Tokens";
    const navigate = useNavigate();

    useEffect(() => {
        setSelectedFriend(null);
    }, []);

    const login = async (event) => {
        event.preventDefault();
        var username = usernameInput.current.value;
        var password = passwordInput.current.value;

        spanRef.current.innerHTML = "";

        const res = await fetch(urlToken, {
            'method': 'post',
            'headers': {
                'Content-Type': 'application/json',
            },
            'body': JSON.stringify({
                "username": username,
                "password": password
            })
        });
        var token = await res.text();
        if (res.status !== 200) {
            spanRef.current.innerHTML = "username or password is incorrect";
        } else {
            setToken(token);
            setUsername(username);
            navigate('/chat');
        }
    };

    return (
        <div className="col-md-4">
            <form method="get" className="text-center form-login" action="/chat">
                <div className="form-group">
                    <label htmlFor="username" className="custom-label">
                        Username
                    </label>
                    <input type="text" ref={usernameInput} className="form-control" id="username" name="username" required />
                </div>
                <div className="form-group">
                    <label htmlFor="password" className="custom-label">
                        Password
                    </label>
                    <input type="password" ref={passwordInput} className="form-control" id="password" name="password" required />
                </div>
                <button type="submit" onClick={login} className="btn btn-primary">
                    Login
                </button>
                <div className="custom-label">
                    Not registered?{" "}
                    <Link to="/register" className="custom-link">Click here</Link> to register
                </div>
                <span ref={spanRef} className='error-span'></span>
            </form>
        </div>
    );
}

export default LoginForm;
