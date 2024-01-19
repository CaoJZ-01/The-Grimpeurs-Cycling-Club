package com.example.thegrimpeurscyclingclub;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RegisterTest {

    //  Test only
    @Test
    public void emptyName() {
        String emptyName = null;
        String errorMessage = Register.invalidUserID(emptyName);
        assertEquals("User ID Can not be Empty\n", errorMessage);
    }

    @Test
    public void spaceName() {
        String spaceName = "test 1";
        String errorMessage = Register.invalidUserID(spaceName);
        assertEquals("User ID Contain Space\n", errorMessage);
    }

    @Test
    public void spacePassword(){
        String spacePassword = "123 45";
        //Register mockedRegister = Mockito.mock(Register.class);
        //Mockito.when(mockedRegister.invalidPassword(spacePassword)).thenReturn("Password Contain Space\n");
        String errorMessage = Register.invalidPassword(spacePassword);
        assertEquals("Password Contain Space\n", errorMessage);
    }

}





