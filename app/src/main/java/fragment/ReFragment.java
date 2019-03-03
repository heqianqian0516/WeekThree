package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwei.weekthree.DetailActivity;
import com.bwei.weekthree.MainActivity;
import com.bwei.weekthree.R;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import adapter.GridAdapter;
import api.Apis;
import bean.BannnerBean;
import bean.DetailBean;
import bean.EventBean;
import bean.ShowBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import presenter.PresenterImp;
import view.IView;

public class ReFragment extends Fragment implements IView {
    @BindView(R.id.banner)
    XBanner mBanner;
    @BindView(R.id.recy)
    RecyclerView mRecy;
    private View view;
    private Unbinder unbinder;
    private PresenterImp presenter;
    private List<String> mImage;
    private GridAdapter gridAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.re_layout_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        presenter = new PresenterImp(this);
        mImage = new ArrayList<>();
        initLoad();
        initView();
        return view;

    }

    private void initView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecy.setLayoutManager(staggeredGridLayoutManager);
        gridAdapter = new GridAdapter(getActivity());
        mRecy.setAdapter(gridAdapter);
    }

    //请求网络
    private void initLoad() {
        presenter.startRequestGet(Apis.URL_BANNER,BannnerBean.class);
        presenter.startRequestGet(Apis.URL_SHOW,ShowBean.class);
    }

    @Override
    public void onRequestSuccess(Object o) {
          if (o instanceof BannnerBean){
              BannnerBean bannnerBean= (BannnerBean) o;
              if (bannnerBean==null){
                  Toast.makeText(getActivity(),bannnerBean.getMessage(),Toast.LENGTH_SHORT).show();
              }else{
                  for (int i = 0; i < bannnerBean.getResult().size(); i++) {
                      mImage.add(bannnerBean.getResult().get(i).getImageUrl());
                      //加载图片
                      initImageData();
                  }
              }
          }
          else if (o instanceof ShowBean){
              final ShowBean showBean= (ShowBean) o;
              if (showBean==null){
                  Toast.makeText(getActivity(),showBean.getMessage(),Toast.LENGTH_SHORT).show();
              }else{
                  //热销生活数据
                  gridAdapter.setmData(showBean.getResult().getRxxp().getCommodityList());
                 gridAdapter.setHttpSetOnclickListener(new GridAdapter.HttpSetOnclickListener() {
                     @Override
                     public void onClickListener(int position) {
                         int commodityId=showBean.getResult().getRxxp().getCommodityList().get(position).getCommodityId();
                         getGoods(commodityId);
                     }
                 });
              }
          }
          else if(o instanceof DetailBean){
              DetailBean detailBean= (DetailBean) o;

              EventBus.getDefault().postSticky(new EventBean("goods",o));

              startActivity(new Intent(getActivity(),DetailActivity.class));
          }
    }
    private void getGoods(int id){
        presenter.startRequestGet(Apis.URL_DETAIL+"?commodityId="+id,DetailBean.class);
    }
    private void initImageData() {
        mBanner.setData(mImage, null);
        mBanner.loadImage(new XBanner.XBannerAdapter() {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDeath();
    }
}
