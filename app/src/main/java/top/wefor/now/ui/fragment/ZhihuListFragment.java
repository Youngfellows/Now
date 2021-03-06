package top.wefor.now.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import java.util.Calendar;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import top.wefor.now.database.ZhihuDbHelper;
import top.wefor.now.http.BaseObserver;
import top.wefor.now.http.ZhihuApi;
import top.wefor.now.model.ZhihuDailyResult;
import top.wefor.now.model.entity.Zhihu;
import top.wefor.now.ui.adapter.ZhihuAdapter;
import top.wefor.now.utils.Constants;

public class ZhihuListFragment extends BaseListFragment<Zhihu> {

    private ZhihuApi mZhihuApi = new ZhihuApi();
    private ZhihuAdapter mAdapter;
    private String date;

    public static ZhihuListFragment newInstance() {
        ZhihuListFragment fragment = new ZhihuListFragment();
        // TODO you can use bundle to transfer data

        int i = 0;
        Calendar dateToGetUrl = Calendar.getInstance();
        dateToGetUrl.add(Calendar.DAY_OF_YEAR, 1 - i);
        String date = Constants.simpleDateFormat.format(dateToGetUrl.getTime());
        Bundle bundle = new Bundle();
        bundle.putBoolean("first_page?", i == 0);
        bundle.putBoolean("single?", false);
        bundle.putString("date", date);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        date = bundle.getString("date");
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // set LayoutManager for your RecyclerView
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ZhihuAdapter(getActivity(), mList);
        mRecyclerView.setItemAnimator(new FadeInAnimator());
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        mRecyclerView.setAdapter(scaleAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        if (mList.size() < 1) {
            getData();
        }
    }

    @Override
    public void getData() {
        // Debug url
//        String url = "http://news.at.zhihu.com/api/4/news/before/20150822";
        mZhihuApi.getDailyNews(date)
                .subscribe(new BaseObserver<ZhihuDailyResult>() {
                    @Override
                    protected void onSucceed(ZhihuDailyResult result) {
                        if (result.stories != null) {
                            mList.clear();
                            for (Zhihu item : result.stories) {
                                mList.add(item);
                            }
                            ZhihuDbHelper zcoolDbHelper = new ZhihuDbHelper(mList, mRealm);
                            zcoolDbHelper.saveToDatabase();
                        }
                        showList();
                    }

                    @Override
                    protected void onFailed(String msg) {
                        super.onFailed(msg);
                        showList();
                    }
                });
    }

    @Override
    public void showList() {
        //如果页数*分页数目大于列表长度，那么只能是列表正在加载或数据库数据见底，两种情况都不需要继续加载。
        if ((mPage - 1) * Constants.LIST_PAGE_SIZE > mList.size()) return;
        ZhihuDbHelper zcoolDbHelper = new ZhihuDbHelper(mList, mRealm);
        zcoolDbHelper.getFromDatabase(PAGE_SIZE, mPage++);
        mAdapter.notifyDataSetChanged();
    }

}
