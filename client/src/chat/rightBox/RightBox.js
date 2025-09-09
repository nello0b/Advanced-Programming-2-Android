import React from 'react';
import ContactBox from './ContactBox';
import ChatBox from './ChatBox';
import { MassagesContextProvider } from '../../context/MassageContext';

function RightBox() {
    return (
        <MassagesContextProvider>
            <div className="col-md-6 col-lg-7 col-xl-8 col">
                <ContactBox />
                <ChatBox/>
            </div>
        </MassagesContextProvider>
    );
}

export default RightBox;
