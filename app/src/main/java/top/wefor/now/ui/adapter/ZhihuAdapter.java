package top.wefor.now.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.wefor.now.R;
import top.wefor.now.http.ZhihuApi;
import top.wefor.now.model.entity.Zhihu;
import top.wefor.now.ui.WebActivity;

/**
 * Created by ice on 15/10/26.
 */
public class ZhihuAdapter extends TestRecyclerViewAdapter<Zhihu> {

    public ZhihuAdapter(Context context, List<Zhihu> contents) {
        super(context, contents);
        setBigViewResId(R.layout.item_empty_head);
        setSmallViewResId(R.layout.item_zhihu);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(getBigViewResId(), parent, false);
                return new CardViewHolder(view, TYPE_HEADER) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(getSmallViewResId(), parent, false);
                return new CardViewHolder(view) {
                };
            }
        }
        return null;
    }

    @Override
    protected void bindCellViewHolder(RecyclerView.ViewHolder cellViewHolder, int position) {
        super.bindCellViewHolder(cellViewHolder, position);
        Zhihu news = contents.get(position);
        Logger.d(position + "");
        CardViewHolder cardViewHolder = (CardViewHolder) cellViewHolder;
        // 图像地址（官方 API 使用数组形式，目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
        Uri imgUri = Uri.parse(news.images.get(0));
        cardViewHolder.mSimpleDraweeView.setImageURI(imgUri);
        cardViewHolder.mTitle.setText(news.title);
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.simpleDraweeView)
        SimpleDraweeView mSimpleDraweeView;
        @Bind(R.id.tv_title)
        TextView mTitle;

        public CardViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public CardViewHolder(View v, int viewType) {
            super(v);
            if (viewType == TYPE_CELL)
                ButterKnife.bind(this, v);
        }

        @OnClick(R.id.ll_card_parent)
        void onClick(View v) {
            // TODO do what you want :) you can use WebActivity to load detail content
            Zhihu news = contents.get(getLayoutPosition());
            String news_url = ZhihuApi.getNewsContent(news.id);
            Intent intent = new Intent(v.getContext(), WebActivity.class);
            intent.putExtra(WebActivity.EXTRA_TITLE, news.title);
            intent.putExtra(WebActivity.EXTRA_URL, news_url);
            intent.putExtra(WebActivity.EXTRA_PIC_URL, news.images.get(0));
            v.getContext().startActivity(intent);
        }
    }

}
