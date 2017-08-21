package com.wenld.junitdemo;

import android.os.Build;

import com.wenld.junitdemo.database.NoteManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * <p/>
 * Author: wenld on 2017/8/21 16:31.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, /*manifest = Config.NONE,*/ sdk = Build.VERSION_CODES.JELLY_BEAN,application = RobolectApp.class)
public class RobolectricTest {
    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void testInsertAndGet() throws Exception {
        NoteBean bean = new NoteBean();
        bean.setId((long) 1);
        bean.setName("wenld");

        new NoteManager().insert(bean);


        NoteBean retBean = new NoteManager().selectByPrimaryKey((long) 1);

        Assert.assertEquals(retBean.getId(), 1);
        Assert.assertEquals(retBean.getName(), "键盘男");
    }
}
