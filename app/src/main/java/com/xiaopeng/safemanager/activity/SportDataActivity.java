package com.xiaopeng.safemanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.xiaopeng.safemanager.R;
import com.xiaopeng.safemanager.bean.GsonObjModel;
import com.xiaopeng.safemanager.bean.SportDataBean;
import com.xiaopeng.safemanager.bean.UserInfoBean;
import com.xiaopeng.safemanager.config.Config;
import com.xiaopeng.safemanager.utils.HttpPost;
import com.xiaopeng.safemanager.utils.SharedPreferencesUtil;
import com.xiaopeng.safemanager.utils.ToastUtil;
import com.xiaopeng.safemanager.views.cardswipelayout.CardConfig;
import com.xiaopeng.safemanager.views.cardswipelayout.CardItemTouchHelperCallback;
import com.xiaopeng.safemanager.views.cardswipelayout.CardLayoutManager;
import com.xiaopeng.safemanager.views.cardswipelayout.OnSwipeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SportDataActivity extends AppCompatActivity {

    @BindView(R.id.activity_all_user_back)
    ImageView activityAllUserBack;
    @BindView(R.id.listView)
    ListView listView;
    SportDataAdapter adapter;
    private List<Integer> list = new ArrayList<>();
    List<SportDataBean> sportDatas;
    List<UserInfoBean> userInfoBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_all_user);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyAdapter());
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), list);
        cardCallback.setOnSwipedListener(new OnSwipeListener<Integer>() {

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                if (direction == CardConfig.SWIPING_LEFT) {
                    myHolder.dislikeImageView.setAlpha(Math.abs(ratio));
                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    myHolder.likeImageView.setAlpha(Math.abs(ratio));
                } else {
                    myHolder.dislikeImageView.setAlpha(0f);
                    myHolder.likeImageView.setAlpha(0f);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer o, int direction) {
                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
                myHolder.dislikeImageView.setAlpha(0f);
                myHolder.likeImageView.setAlpha(0f);
                Toast.makeText(SportDataActivity.this, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
                if (userInfoBeanList.size() > 0) {
                    userInfoBeanList.remove(0);
                }
            }

            @Override
            public void onSwipedClear() {
                Toast.makeText(SportDataActivity.this, "data clear", Toast.LENGTH_SHORT).show();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 3000L);
            }

        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void initData() {
        adapter=new SportDataAdapter();
        querySportDatas();
        listView.setAdapter(adapter);
        list.add(R.drawable.img_avatar_01);
        list.add(R.drawable.img_avatar_02);
        list.add(R.drawable.img_avatar_03);
        list.add(R.drawable.img_avatar_04);
        list.add(R.drawable.img_avatar_05);
        list.add(R.drawable.img_avatar_06);
        list.add(R.drawable.img_avatar_07);
    }

    //查询运动信息
    private void querySportDatas() {
        String url = Config.baseUrl+"safe/querySportData";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("username", SharedPreferencesUtil.getString("username","admin"));
        Log.i("xiaopeng", "url----:" + url + "?" + params.getQueryStringParams().toString().replace(",", "&").replace("[", "").replace("]", "").replace(" ", ""));
        new HttpPost<GsonObjModel<List<SportDataBean>>>(url,this,params){
            @Override
            public void onParseSuccess(GsonObjModel<List<SportDataBean>> response, String result) {
                super.onParseSuccess(response, result);
                Log.i("xiaopeng--success", ""+result);
                if (response.code==200){
                    sportDatas=response.data;
                    adapter.notifyDataSetChanged();
                }
                if (response.code==400){
                    ToastUtil.show(getApplicationContext(),response.msg);
                }
            }

            @Override
            public void onParseError(GsonObjModel<String> response, String result) {
                super.onParseError(response, result);
                Log.i("xiaopeng--error", ""+result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                super.onFailure(e, s);
                Log.i("xiaopeng--success", ""+s+e);
            }
        };
    }

    @OnClick(R.id.activity_all_user_back)
    public void onViewClicked() {
        finish();
    }

    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
            TextView userName = ((MyViewHolder) holder).userName;
            avatarImageView.setImageResource(list.get(position));
            if (userInfoBeanList != null) {
                if (position < userInfoBeanList.size()) {
                    userName.setText("名字:" + userInfoBeanList.get(position).getUsername() +
                            " 性别:" + userInfoBeanList.get(position).getUsersex() +
                            " 体重:" + userInfoBeanList.get(position).getUserweight() +
                            "身高:" + userInfoBeanList.get(position).getUserheight());
                }
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView avatarImageView;
            ImageView likeImageView;
            ImageView dislikeImageView;
            TextView userName;

            MyViewHolder(View itemView) {
                super(itemView);
                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
                likeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
                dislikeImageView = (ImageView) itemView.findViewById(R.id.iv_dislike);
                userName = (TextView) itemView.findViewById(R.id.tv_name);
            }

        }
    }

    public class SportDataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sportDatas == null ? 0 : sportDatas.size()>7?7:sportDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_userinfo, null);
            }
            TextView userName = (TextView) convertView.findViewById(R.id.item_user_info_username);
            TextView userSex = (TextView) convertView.findViewById(R.id.item_user_info_usersex);
            TextView userHeight = (TextView) convertView.findViewById(R.id.item_user_info_userheight);
            TextView userWeight = (TextView) convertView.findViewById(R.id.item_user_info_userweight);
            userName.setText(sportDatas.get(position).getUserName());
            userSex.setText(sportDatas.get(position).getDate());
            userHeight.setText(sportDatas.get(position).getStep());
            userWeight.setText(sportDatas.get(position).getEnergy());
            return convertView;
        }
    }
}
