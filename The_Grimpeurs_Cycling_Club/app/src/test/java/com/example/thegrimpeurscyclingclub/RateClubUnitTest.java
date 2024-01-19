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
public class RateClubUnitTest {

    @Mock
    Context mMockContext;

    @Test //test1
    public void testValidRate(){
        int validRate = 1;
        String errorMessage = RateClub.validateRate(validRate);
        assertEquals("passed",errorMessage);

    }

    @Test //test2
    public void testInvalidRate(){
        int validRate = -1000;
        String errorMessage = RateClub.validateRate(validRate);
        assertEquals("Please Enter a Integer From 1 to 5",errorMessage);

    }








}

