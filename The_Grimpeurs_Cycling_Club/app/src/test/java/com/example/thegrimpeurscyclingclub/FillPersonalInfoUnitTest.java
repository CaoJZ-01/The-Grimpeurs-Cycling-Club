package com.example.thegrimpeurscyclingclub;
import android.content.Context;
import android.provider.Telephony;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class FillPersonalInfoUnitTest {
    @Mock
    Context mMockContext;

    @Test //test 3 test valid Age
    public void testValidAge(){
        String validAge = "21";
        String errorMessage = FillPersonalInfo.validateAge(validAge);
        assertEquals("passed",errorMessage);
    }


    @Test //test 4 test invalid Age
    public void testInvalidAge(){
        String invalidAge = "19";
        String errorMessage = FillPersonalInfo.validateAge(invalidAge);
        assertEquals("Your Are Under Age Limit       \n",errorMessage);
    }

    @Test //test 5 test invalid Age input
    public void testInvalidAgeInput(){
        String invalidAgeInput = "abcd";
        String errorMessage = FillPersonalInfo.validateAge(invalidAgeInput);
        assertEquals("Please enter a Number for Age",errorMessage);
    }

    @Test //test 6 test valid level
    public void testValidLevel(){
        String validLevel = "2000";
        String errorMessage = FillPersonalInfo.validateLevel(validLevel);
        assertEquals("passed",errorMessage);
    }

    @Test //test 7 test invalid level
    public void testInvalidLevel(){
        String invalidLevel = "0";
        String errorMessage = FillPersonalInfo.validateLevel(invalidLevel);
        assertEquals("Your Are Under Level Limit       \n",errorMessage);
    }

    @Test //test 8 test invalid level
    public void testInvalidLevelInput(){
        String invalidLevelInput = "19.9";
        String errorMessage = FillPersonalInfo.validateLevel(invalidLevelInput);
        assertEquals("Please enter a Number for Level",errorMessage);
    }

    @Test //test 9 test valid Pace
    public void testValidPace(){
        String validPace = "20.10";
        String errorMessage = FillPersonalInfo.validatePace(validPace);
        assertEquals("passed",errorMessage);
    }

    @Test //test 10 test invalid Pace
    public void testInvalidPace(){
        String invalidPace = "0.01";
        String errorMessage = FillPersonalInfo.validatePace(invalidPace);
        assertEquals("Your Are Under Pace Limit       \n",errorMessage);
    }

    @Test //test 11 test invalid Pace input
    public void testInvalidPaceInput(){
        String invalidPaceInput = "===";
        String errorMessage = FillPersonalInfo.validatePace(invalidPaceInput);
        assertEquals("Please enter a Number for Pace",errorMessage);
    }

}
