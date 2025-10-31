import React from 'react';
import { Container, Navbar } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';

function BsNavBar() {
    return <>
        <Navbar fixed="top" expand="md" className="bg-secondary" variant="dark">
            <Container>
                <Navbar.Brand as={NavLink} to="/">Gym</Navbar.Brand>
            </Container>
        </Navbar>
    </>
}

export default BsNavBar;