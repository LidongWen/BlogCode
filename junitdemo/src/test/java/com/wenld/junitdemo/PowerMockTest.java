package com.wenld.junitdemo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * <p/>
 * Author: wenld on 2017/8/21 15:00.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CaculeClass.class})  //PrepareForTest 后面要加准备被mock或stub的类，单个class直接()起来即可，多个用{}，并用逗号隔开。
//@PowerMockIgnore({"sun.security.*", "javax.net.*","javax.management.*","org.mockito.*"})
public class PowerMockTest {

    @Mock
    private CaculeClass mCalc;
    @Test
    public void testPublicField() {
//        CaculeClass mCalc=new CaculeClass();
        Assert.assertEquals(0, 0);
        Assert.assertEquals(mCalc.mPublicFinalField, 0);
        Assert.assertEquals(CaculeClass.mPublicStaticField, 0);
        Assert.assertEquals(CaculeClass.mPublicStaticFinalField, 0);

        mCalc.mPublicField = 1;
        CaculeClass.mPublicStaticField = 2;

         Assert.assertEquals(mCalc.mPublicField, 1);
         Assert.assertEquals(mCalc.mPublicFinalField, 0);
         Assert.assertEquals(CaculeClass.mPublicStaticField, 2);
    }

    @Test
    public void testPrivateField() throws IllegalAccessException {
        PowerMockito.mockStatic(CaculeClass.class);

         Assert.assertEquals(Whitebox.getField(CaculeClass.class, "mPrivateField").getInt(mCalc), 0);
         Assert.assertEquals(Whitebox.getField(CaculeClass.class, "mPrivateFinalField").getInt(mCalc), 0);
         Assert.assertEquals(Whitebox.getField(CaculeClass.class, "mPrivateStaticField").getInt(null), 0);
         Assert.assertEquals(Whitebox.getField(CaculeClass.class, "mPrivateStaticFinalField").getInt(null), 0);


        Whitebox.setInternalState(mCalc, "mPrivateField", 1);
        Whitebox.setInternalState(CaculeClass.class, "mPrivateStaticField", 1, CaculeClass.class);

         Assert.assertEquals(Whitebox.getField(CaculeClass.class, "mPrivateField").getInt(mCalc), 1);
         Assert.assertEquals(Whitebox.getField(CaculeClass.class, "mPrivateFinalField").getInt(mCalc), 0);
         Assert.assertEquals(Whitebox.getField(CaculeClass.class, "mPrivateStaticField").getInt(null), 1);
         Assert.assertEquals(Whitebox.getField(CaculeClass.class, "mPrivateStaticFinalField").getInt(null), 0);
    }

    @Test
    public void testAddPublicMethod() {
        //when
        when(mCalc.addPublic(anyInt(), anyInt()))
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(2)
                .thenReturn(3)
                .thenReturn(4)
                .thenReturn(5);

        //call method
        for (int i = 0; i < 6; i++) {

//            System.out.println(mCalc.addPublic(i, i));
            //verify
             Assert.assertEquals(mCalc.addPublic(i, i), i);
        }

        //verify
        verify(mCalc,  Mockito.times(6)).addPublic(anyInt(), anyInt());
        verify(mCalc,  Mockito.atLeast(1)).addPublic(anyInt(), anyInt());
        verify(mCalc,  Mockito.atLeastOnce()).addPublic(anyInt(), anyInt());
        verify(mCalc,  Mockito.atMost(6)).addPublic(anyInt(), anyInt());
    }


    @Test
    public void testAddPrivateMethod() throws Exception {
        PowerMockito.mockStatic(CaculeClass.class);

        //when
        PowerMockito.when(mCalc,"addPrivate",anyInt(),anyInt())
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(2)
                .thenReturn(3)
                .thenReturn(4)
                .thenReturn(5);

        //call method
        for (int i = 0; i < 6; i++) {

            //verify
             Assert.assertEquals(Whitebox.invokeMethod(mCalc,"addPrivate",i,i), i);
        }

        //verify static
        PowerMockito.verifyPrivate(mCalc,Mockito.times(6)).invoke("addPrivate",anyInt(),anyInt());
        PowerMockito.verifyPrivate(mCalc,Mockito.atLeast(1)).invoke("addPrivate",anyInt(),anyInt());
    }


    @Test
    public void testAddPublicVoidMethod() {
        //when
       Mockito.doNothing().when(mCalc).voidPublic(anyInt(), anyInt());

        mCalc.voidPublic(anyInt(), anyInt());
        mCalc.voidPublic(anyInt(), anyInt());

        Mockito.verify(mCalc, Mockito.atLeastOnce()).voidPublic(anyInt(), anyInt());
        Mockito.verify(mCalc, Mockito.atLeast(2)).voidPublic(anyInt(), anyInt());
    }


    @Test
    public void testAddPublicStaicMethod() throws Exception {
        PowerMockito.mockStatic(CaculeClass.class);

        PowerMockito.when(CaculeClass.class, "addPublicStatic", anyInt(), anyInt())
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(2)
                .thenReturn(3)
                .thenReturn(4)
                .thenReturn(5);


        //call method
        for (int i = 0; i < 6; i++) {

            //verify
             Assert.assertEquals(CaculeClass.addPublicStatic(i, i), i);
        }


        //verify static
        verifyStatic(Mockito.times(6));
    }

}
