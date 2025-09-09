import React from "react";
import logo from "./logoChitChat.png";

function LogoContainer() {
  return (
    <div className="col-md-4">
      <img src={logo} alt="Logo" className="custom-logo" id="logo" />
      {/* Logo image with a custom CSS class applied */}
    </div>
  );
}

export default LogoContainer;
