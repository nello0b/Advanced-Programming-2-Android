import React, { createContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { TokenContext } from './TokenContext.js';

// Create the context
export const UserContext = createContext();

// Create the context provider component
export const UserContextProvider = ({ children }) => {
  // Define your state variables here
  const [user, setUser] = useState({});
  const { token, username } = React.useContext(TokenContext);

  const urlUser = `http://localhost:5000/api/Users/${username}`;

  const navigate = useNavigate();

  const getUser = async () => {
    if (!username || !token) {
      // Handle case where username or token doesn't exist
      console.error('Username or token not found');
      navigate('/');
      return;
    }
    const res = await fetch(urlUser, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        authorization: `Bearer ${token}`,
      },
    });
    if (res.ok) {
      const userJson = await res.json();
      setUser(userJson);
    } else {
      // Handle error when the response is not ok (e.g., user not found)
      console.error('Failed to fetch user:', res.status);
      alert('Failed to fetch user. Returning to start.');
      navigate('/');
    }
  };

  useEffect(() => {
    getUser(); // Initial API call on component mount
  }, []);

  // Create an object with the values and functions to be passed down
  const contextValue = {
    user,
    updateUser: getUser,
  };

  // Render the provider component with the context value and children
  return (
    <UserContext.Provider value={contextValue}>{children}</UserContext.Provider>
  );
};
