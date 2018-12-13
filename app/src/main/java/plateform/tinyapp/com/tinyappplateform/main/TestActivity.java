package plateform.tinyapp.com.tinyappplateform.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.sun.weexandroid_module.WxRvUtils;

/**
 * Created by sun on 2018/8/10
 */
// TODO: 2018/8/10 DELETE
public class TestActivity extends AppCompatActivity {

    private static final String TAG = "sunHy_TestActivity";

    public static final String KEY_PATH = "KEY_PATH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sun.weexandroid_module.R.layout.activity_test);

        LinearLayout ll = (LinearLayout) findViewById(com.sun.weexandroid_module.R.id.ll);

        Intent intent = getIntent();
//        if (intent == null) {
//            return;
//        }
        String path = "index.js";
        WxRvUtils.getJsView(this, ll, path, new WxRvUtils.CallBak() {
            @Override
            public void onFailed() {

            }

            @Override
            public void onSuccess() {

            }
        });
    }

}
