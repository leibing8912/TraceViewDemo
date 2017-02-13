package cn.jianke.traceviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import static android.os.Debug.startMethodTracing;
import static android.os.Debug.stopMethodTracing;

/**
 * @className: MainActivity
 * @classDescription: 测试TraceView分析性能
 * @author: leibing
 * @createTime: 2017/2/13
 */
public class MainActivity extends AppCompatActivity {
    // 跟踪生成分析文件名
    private final static String TRACE_VIEW_NAME = "MainActivity";
    // 统计次数
    private int count = 0;
    private long longCount=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 跟踪
        startMethodTracing(TRACE_VIEW_NAME);
        // 线程1
        new Thread(new Runnable() {
            @Override
            public void run() {
                printNum();
            }
        },"printNum_thread").start();
        // 线程2
        new Thread(new Runnable() {
            @Override
            public void run() {
                calculate();
            }
        },"calculate_thread").start();
    }

    /**
     * 模拟一个自身占用时间不长，但调用却非常频繁的函数
     * @author leibing
     * @createTime 2017/2/13
     * @lastModify 2017/2/13
     * @param
     * @return
     */
    private void printNum() {
        for (int i = 0; i < 20000; i++) {
            print();
        }
    }

   /**
    * 模拟一个自身占用时间不长，但调用却非常频繁的函数
    * @author leibing
    * @createTime 2017/2/13
    * @lastModify 2017/2/13
    * @param
    * @return
    */
    private void   print(){
        count = count++;
    }

    /**
     * 模拟一个调用次数不多，但每次调用却需要花费很长时间的函数
     * @author leibing
     * @createTime 2017/2/13
     * @lastModify 2017/2/13
     * @param
     * @return
     */
    private  void  calculate(){
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                for (int l = 0; l < 1000; l++) {
                    if(longCount>10){
                        longCount=-10;
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        // 结束追踪
        stopMethodTracing();
        super.onDestroy();
    }
}
