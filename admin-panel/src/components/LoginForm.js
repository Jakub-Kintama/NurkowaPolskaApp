import React from 'react';

export default function LoginForm( {setIsAdmin} ) {
    const clickHandler = async() => {
        setIsAdmin(true);
    }
    return (
        <>
        <div className='LoginForm'>
            <p>Aby zobaczyć listę osób uprawnionych musisz się zalogować</p>
            <img onClick={clickHandler} src='/placeholder.png' alt='Zaloguj się poprzez konto Google'></img>
        </div>
        </>
    );
}