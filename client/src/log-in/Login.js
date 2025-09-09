import React from "react";
import LoginForm from "./Login-Form";
import LogoContainer from "./Logo-Container";
import "./log-in.css";

function Login(props) {  
  return (
    <div className="container">
      <div
        className="row justify-content-center align-items-center"
        style={{ height: "100vh" }}>
        <LogoContainer />
        <LoginForm />
      </div>
    </div>
  );
}

export default Login;
