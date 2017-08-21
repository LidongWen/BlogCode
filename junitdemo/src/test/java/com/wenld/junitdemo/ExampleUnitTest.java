package com.wenld.junitdemo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Before
    public void init() {
        System.out.println("------method init called------");
    }

    @BeforeClass
    public static void prepareDataForTest() {
        System.out.println("------method prepareDataForTest called------\n");
    }

    @Test
    public void addition_isCorrect() throws Exception {
        int a = 1;
        int b = 2;

        int result = new CaculeClass().add(a, b);
        try {
            assertEquals(result, 4); // 验证result==3，如果不正确，测试不通过
        } catch (Exception e) {
            Assert.fail("exception is:" + e.getMessage());
        }
        System.out.println("------method test1 called------");
    }

    @Test
    public void mockTest() {
        //主要 用作 集成测试 时的 依赖隔离
        IMathUtils mathUtils = mock(IMathUtils.class); // 生成mock对象
        when(mathUtils.abs(-5)).thenReturn(1); // 当调用abs(-5)时，返回1

        int abs = mathUtils.abs(-5); // 输出结果 1

        Assert.assertEquals(abs, 1);// 测试通过
    }

    @After
    public void clearDataForTest() {
        System.out.println("------method clearDataForTest called------");
    }

    @AfterClass
    public static void finish() {
        System.out.println("------method finish called------");
    }

    @org.junit.Test
    public void testAdd2() {
        // 模仿一个对象
        CaculeClass caculeClass = mock(CaculeClass.class);
        caculeClass.add(1, 2);
        caculeClass.add(1, 3);
        caculeClass.add(1, 3);
        try {
            Mockito.verify(caculeClass).add(1, 2);// 验证calculater.add(a, b)是否被调用过 1次，且a==1 && b==2
        } catch (Exception e) {
            System.out.println("------没有调用过 1 ------");
        }
//        try {
//            Mockito.verify(caculeClass, Mockito.times(2)).add(1, 2);// 验证calculater.add(a, b)是否被调用过两次，且a==1 && b==2
//        } catch (Exception e) {
//            System.out.println("------没有调用过 2次 ------");
//        }
        Mockito.verify(caculeClass, Mockito.atMost(5)).add(1, 2);// 验证calculater.add(a, b)是否被调用过 五次以内，且a==1 && b==2
        try {
            Mockito.verify(caculeClass, never()).add(1, 1);
        } catch (Exception e) {
            System.out.println("------ 有被调用过 ------");
        }
        try {
            Mockito.verify(caculeClass, Mockito.atLeast(2)).add(1, 3);
        } catch (Exception e) {
            System.out.println("------ 最少被调用过两次 ------");
        }
        // 测试通过
    }

    @org.junit.Test
    public void testOrder() {
        // A. Single mock whose methods must be invoked in a particular order
// A. 验证mock一个对象的函数执行顺序
        List singleMock = mock(List.class);

//using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

//create an inOrder verifier for a single mock
// 为该mock对象创建一个inOrder对象
        InOrder inOrder = Mockito.inOrder(singleMock);

//following will make sure that add is first called with "was added first, then with "was added second"
// 确保add函数首先执行的是add("was added first"),然后才是add("was added second")
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

// B. Multiple mocks that must be used in a particular order
// B .验证多个mock对象的函数执行顺序
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

//using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

//create inOrder object passing any mocks that need to be verified in order
// 为这两个Mock对象创建inOrder对象
        InOrder inOrders = Mockito.inOrder(firstMock, secondMock);

//following will make sure that firstMock was called before secondMock
// 验证它们的执行顺序
        inOrders.verify(firstMock).add("was called first");
        inOrders.verify(secondMock).add("was called second");
    }
}