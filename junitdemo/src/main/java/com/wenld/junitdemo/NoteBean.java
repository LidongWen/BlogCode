package com.wenld.junitdemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p/>
 * Author: wenld on 2017/8/21 16:48.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */
@Entity(indexes = {
        @Index(value = "name DESC", unique = true)
})
public class NoteBean {
    @Id
    long id;
    @NotNull
    String name = "";

    @Generated(hash = 219526012)
    public NoteBean(long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 451626881)
    public NoteBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

}
