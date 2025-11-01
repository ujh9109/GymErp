import React from 'react';
import { Container, Navbar, Nav } from "react-bootstrap";
import { NavLink } from 'react-router-dom';

function BsNavBar() {
    return <>
        <Navbar fixed="top" expand="md" className="bg-secondary" variant="dark">
            <Container>
                <Navbar.Brand as={NavLink} to="/">Gym</Navbar.Brand>
                <Navbar.Toggle aria-controls="collapse" />


                <Nav className="me-auto">
                    
                    
                    <Nav.Link as={NavLink} to="/schedule">일정 관리</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    </>
}

export default BsNavBar;