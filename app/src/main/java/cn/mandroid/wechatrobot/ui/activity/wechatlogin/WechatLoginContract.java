package cn.mandroid.wechatrobot.ui.activity.wechatlogin;

import android.graphics.Bitmap;

import cn.mandroid.wechatrobot.ui.activity.common.IBasePresenter;
import cn.mandroid.wechatrobot.ui.activity.common.IBaseView;

/**
 * Created by wrBug on 2016/12/31.
 */

public class WechatLoginContract {
    public interface View extends IBaseView {
        void setQrcodeImage(String url);

        void setPcOpenNotice(String url);

        void loginSuccess();
    }

    public interface Presenter extends IBasePresenter {
        /**
         * 获取uuid
         */
        void getUUID();
    }
}
