import React from 'react';
import placeholder from './placeholder.png';
import { useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './RegisterComp.css';
import UserContext from "../context/UserContext.js";

const urlAddUser = "http://localhost:5000/api/Users";
const urlToken = "http://localhost:5000/api/Tokens";

function validatePassword(password) {
    if (password.length < 8 || password.length > 20) {
        return false;
    }
    const hasLetter = /[a-zA-Z]/.test(password);
    const hasNumber = /\d/.test(password);
    return hasLetter && hasNumber;
}

function validateName(name) {
    if (name.length < 2 || name.length > 20) {
        return false;
    }
    const hasOnlyLettersAndSpaces = /^[a-zA-Z ]+$/.test(name);
    return hasOnlyLettersAndSpaces;
}

function validateImageFile(file) {
    const validTypes = ['image/jpeg', 'image/png', 'image/gif'];
    if (!file) {
        return false;
    }
    return validTypes.includes(file.type);
}

function RegisterComp() {
    const navigate = useNavigate();
    const usernameInput = useRef(null);
    const passwordInput = useRef(null);
    const displayNameInput = useRef(null);
    const imageInput = useRef(null);
    const imageRef = useRef(null);
    const spanRef = useRef(null);

    const addUser = (async (event) => {
        event.preventDefault();
        var username = usernameInput.current.value;
        var password = passwordInput.current.value;
        var displayName = displayNameInput.current.value;
        var addFlag = true;

        const newUser = {
            username: '',
            password: '',
            displayName: '',
            profilePic: '',
        };

        spanRef.current.innerHTML = "";

        if (username.length === 0 || username.length > 20) {
            spanRef.current.innerHTML += "username needs to be at most 20 characters<br>";
            addFlag = false;
        } else {
            newUser.username = username;
        }

        if (!validatePassword(password)) {
            spanRef.current.innerHTML += "password needs to be at least 8 charcters and at most 20 characters and be made by numbers and letters<br>";
            addFlag = false;
        } else {
            newUser.password = password;
        }

        if (!validateName(displayName)) {
            spanRef.current.innerHTML += "name needs to be at least 2 charcters and at most 20 characters and by made by only letters<br>";
            addFlag = false;
        } else {
            newUser.displayName = displayName;
        }

        if (imageRef.current.src === placeholder) {
            spanRef.current.innerHTML += "please add an image<br>";
            addFlag = false;
        } else {
            newUser.profilePic = imageRef.current.src;
        }

        if (addFlag) {
            spanRef.current.innerHTML = "";

            const res = await fetch(urlAddUser, {
                'method': 'post',
                'headers': {
                    'Content-Type': 'application/json',
                },
                'body': JSON.stringify(newUser)
            });

            if (res.status != 200) {
                spanRef.current.innerHTML = "username already exist";
            } else {
                navigate('/');
            }
        } else {
            event.preventDefault();
        }
    });

    const handleImageChange = (() => {
        const imageFile = imageInput.current.files[0];
        if (imageFile) {
            if (validateImageFile(imageFile)) {
                const reader = new FileReader();
                reader.onload = (() => {
                    imageRef.current.src = reader.result;
                });
                reader.readAsDataURL(imageFile);
            } else {
                imageInput.current.value = "";
                imageRef.current.src = placeholder;
                spanRef.current.innerHTML += "only images file are allowed";
            }
        } else {
            imageRef.current.src = placeholder;
        }
    });

    return (
        <div className="container containerBackground">
            <form method="get">
                <div className="row rowMargin">
                    <div className="col-3">
                        <label className="custom-label">Username</label>
                    </div>
                    <div className="col-9">
                        <input type="text" ref={usernameInput} className="form-control" required></input>
                    </div>
                </div>
                <div className="row rowMargin">
                    <div className="col-3">
                        <label className="custom-label">Password</label>
                    </div>
                    <div className="col-9">
                        <input type="password" ref={passwordInput} className="form-control" required></input>
                    </div>
                </div>
                <div className="row rowMargin">
                    <div className="col-3">
                        <label className="custom-label">Display name</label>
                    </div>
                    <div className="col-9">
                        <input type="text" ref={displayNameInput} className="form-control" required></input>
                    </div>
                </div>
                <div className="row rowMargin">
                    <div className="col-3">
                        <label className="custom-label">Picture</label>
                    </div>
                    <div className="col-9">
                        <input type="file" ref={imageInput} onChange={handleImageChange} className="form-control" required></input>
                    </div>
                </div>
                <div className="row rowMargin">
                    <div className="col-3">
                        <img src={placeholder} ref={imageRef} alt="chosen img"></img>
                    </div>
                    <div className="col-9">
                        <span ref={spanRef} className='error-span'></span>
                    </div>
                </div>
                <div className="row rowMargin">
                    <div className="col-3">
                        <Link to='/'>
                            <button type="submit" onClick={addUser} className="btn btn-primary">Register</button>
                        </Link>
                    </div>
                    <div className="col-9">
                        <span className="custom-label">Already registered? <Link to="/" className="custom-link">Click
                            here</Link> to login</span>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default RegisterComp;