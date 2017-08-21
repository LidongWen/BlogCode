package com.wenld.junitdemo.database;

import com.wenld.junitdemo.NoteBean;

import org.greenrobot.greendao.AbstractDao;

/**
 * <p/>
 * Author: wenld on 2017/8/21 17:06.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class NoteManager extends AbstractDatabaseManager<NoteBean,Long>{
    @Override
    AbstractDao<NoteBean, Long> getAbstractDao() {
        return daoSession.getNoteBeanDao();
    }
}
