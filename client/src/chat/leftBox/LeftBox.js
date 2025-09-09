import React from 'react';
import ContactCardDeck from './ContactCardDeck';
import UserBox from './UserBox';

function LeftBox() {
    return (
        <div className="col-md-6 col-lg-5 col-xl-4 col">
            <UserBox/>
            <ContactCardDeck />
        </div>
    );
}

export default LeftBox;
