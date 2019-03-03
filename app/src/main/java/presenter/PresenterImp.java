package presenter;

import java.util.Map;

import callBack.MyCallBack;
import model.ModelImp;
import view.IView;

public class PresenterImp implements Ipresenter {
    private IView mIview;
    private ModelImp model;

    public PresenterImp(IView iView){
        this.mIview=iView;
        model = new ModelImp();
    }
    @Override
    public void startRequestGet(String url, Class clazz) {
        model.onRequestGet(url, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object o) {
                mIview.onRequestSuccess(o);
            }

            @Override
            public void onFail(String error) {
                mIview.onRequestFail(error);
            }
        });
    }

    @Override
    public void startRequestPost(String url, Map map, Class clazz) {
         model.onRequestPost(url, map, clazz, new MyCallBack() {
             @Override
             public void onSuccess(Object o) {
                 mIview.onRequestSuccess(o);
             }

             @Override
             public void onFail(String error) {
                mIview.onRequestFail(error);
             }
         });
    }
    public void onDeath(){
        if (model!=null){
            model=null;
        }
        if (mIview!=null){
            mIview=null;
        }
    }
}
