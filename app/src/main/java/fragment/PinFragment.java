package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwei.weekthree.R;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import adapter.LinerAdapter;
import api.Apis;
import bean.BannnerBean;
import bean.ShowBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import presenter.PresenterImp;
import view.IView;

public class PinFragment extends Fragment implements IView {

    @BindView(R.id.pbanner)
    XBanner mPbanner;
    @BindView(R.id.precy)
    RecyclerView mPrecy;
    private View view;
    private Unbinder unbinder;
    private PresenterImp presenter;
    private List<String> mImage;
    private LinerAdapter linerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pin_layout_fragment, null);
        presenter = new PresenterImp(this);
        unbinder = ButterKnife.bind(this, view);
        mImage = new ArrayList<>();
        initLoad();
         intiView();
        return view;
    }

    private void intiView() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mPrecy.setLayoutManager(linearLayoutManager);
        linerAdapter = new LinerAdapter(getActivity());
        mPrecy.setAdapter(linerAdapter);
    }

    private void initLoad() {
        presenter.startRequestGet(Apis.URL_BANNER, BannnerBean.class);
        presenter.startRequestGet(Apis.URL_SHOW, ShowBean.class);
    }

    @Override
    public void onRequestSuccess(Object o) {
        if (o instanceof BannnerBean) {
            BannnerBean bannnerBean = (BannnerBean) o;
            if (bannnerBean == null) {
                Toast.makeText(getActivity(), bannnerBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < bannnerBean.getResult().size(); i++) {
                    mImage.add(bannnerBean.getResult().get(i).getImageUrl());
                    //加载图片
                    initImageData();
                }
            }
        }
        else if (o instanceof ShowBean){
            ShowBean showBean= (ShowBean) o;
            if (showBean==null){
                Toast.makeText(getActivity(),showBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                //魔力时尚数据
                linerAdapter.setmData(showBean.getResult().getMlss().getCommodityList());
            }
        }
    }

    private void initImageData() {
        mPbanner.setData(mImage, null);
        mPbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(mImage.get(position)).into((ImageView) view);
            }
        });
    }

    @Override
    public void onRequestFail(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDeath();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
