package com.tufei.unittest.powermock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author TuFei
 * @date 2018/3/22.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PowerMockSample.class)
public class PowerMockSampleTest {

    private PowerMockSample powerMockSample;

    @Before
    public void setup() {
        powerMockSample = new PowerMockSample();
    }

    @Test
    public void mockPrivateFiled() {
        String newValue = "mockPrivateFiled";

        //第一个参数传的是实例
        Whitebox.setInternalState(powerMockSample, "privateFiled", newValue);

        assertEquals(newValue, powerMockSample.getPrivateFiled());
    }

    @Test
    public void mockPrivateStaticField() {
        String newValue = "mockPrivateStaticField";

        //第一个参数，传的是class
        Whitebox.setInternalState(PowerMockSample.class, "privateStaticField", newValue);

        assertEquals(newValue, PowerMockSample.getPrivateStaticField());
    }

    @Test
    public void mockPrivateStaticFinalField() {
        String newValue = "mockPrivateStaticFinalField";

        //第一个参数，传的是class
        Whitebox.setInternalState(PowerMockSample.class, "privateStaticFinalField", newValue);

        assertNotEquals(newValue, PowerMockSample.getPrivateStaticFinalField());
    }

}