package plateform.tinyapp.com.tinyappplateform.weex;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tinyapp.common.base.BaseActivity;
import com.tinyapp.common.base.Layout;
import com.tinyapp.tinyappplateform.R;
import com.tinyapp.utils.common.ToastUtil;
import com.sun.weexandroid_module.WxRvUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhengfei on 2018/8/16.
 */
@Layout(R.layout.activity_weex_base)
public class BaseWeexActivity extends BaseActivity {
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.ll_weex_base)
    LinearLayout llWeexBase;
    public static final String JSPATH = "js_path";
    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        String path = intent.getStringExtra(JSPATH);
        Log.i("zf","path:"+path);
        WxRvUtils.getJsView(this, llWeexBase, path, new WxRvUtils.CallBak() {
            @Override
            public void onFailed() {
                ToastUtil.show(BaseWeexActivity.this,"JS加载错误");
            }

            @Override
            public void onSuccess() {
            }
        });
    }


    @OnClick({R.id.tv_back, R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_close:
                finish();
                break;
        }
    }

    @Override
    protected boolean isimmersive() {
        return false;
    }
}
