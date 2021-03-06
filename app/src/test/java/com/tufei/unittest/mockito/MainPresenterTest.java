package com.tufei.unittest.mockito;

import com.tufei.unittest.mockito.mvp.GetDataCallback;
import com.tufei.unittest.mockito.mvp.MainContract;
import com.tufei.unittest.mockito.mvp.MainPresenter;
import com.tufei.unittest.mockito.mvp.Person;
import com.tufei.unittest.mockito.mvp.TestRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author TuFei
 * @date 2018/4/9.
 */
public class MainPresenterTest {

    @Mock
    private TestRepository testRepository;
    @Mock
    private MainContract.View view;
    private MainPresenter mainPresenter;
    /**
     * {@link ArgumentCaptor}Captor是一个功能强大的Mockito API，用于捕获参数值并使用它们,
     * 对它们进行进一步的行动或断言。但我在自己的项目里，相比回调。更多的时候，用的都是
     * RxJava来获取model层的数据。好处是，不用声明各种回调接口,而且RxJava在设计的时候，就考
     * 虑到了测试的问题，更易于写测试代码。
     */
    @Captor
    private ArgumentCaptor<GetDataCallback> getDataCallbackCaptor;
    private List<Person> peoples;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(testRepository);
        mainPresenter.attachView(view);
        peoples = Arrays.asList(new Person("Tony"), new Person("Alice"));
    }

    @Test
    public void loadData() {
        mainPresenter.loadData();
        verify(testRepository, times(1)).getData(getDataCallbackCaptor.capture());

        //验证获取数据成功后的逻辑是否顺利完成
        getDataCallbackCaptor.getValue().onSuccess(peoples);
        verify(view, times(1)).showPersons(peoples);

        //验证获取数据失败后的逻辑是否顺利完成
        getDataCallbackCaptor.getValue().onError();
        verify(view, times(1)).showNotDataToast();
    }
}