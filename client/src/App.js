import './App.css';
import RegisterComp from './registration/RegisterComp';
import Login from './log-in/Login';
import ChatScreen from './chat/Chat-Screen';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import React from 'react';
import { SelectedFriendProvider } from './context/SelectedFriendContext';
import { TokenContextProvider } from './context/TokenContext';

function App() {
  return (
    <TokenContextProvider>
      <SelectedFriendProvider>
        <div className="App">
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Login />} />
              <Route path="/register" element={<RegisterComp />} />
              <Route path="/chat" element={<ChatScreen />} />
            </Routes>
          </BrowserRouter>
        </div>
      </SelectedFriendProvider>
    </TokenContextProvider>
  );
}

export default App;
