package com.example.lq.myapplication.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.adapter.MyNavigationAdapter;
import com.example.lq.myapplication.base.BaseFragment;
import com.example.lq.myapplication.bean.NaviBean;
import com.example.lq.myapplication.presnter.NavPresnter;
import com.example.lq.myapplication.utils.HindMain;
import com.example.lq.myapplication.view.NaiView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavFragment extends BaseFragment<NaiView, NavPresnter<NaiView>> implements NaiView {


    @BindView(R.id.tab_navigation)
    VerticalTabLayout mTabNavigation;
    @BindView(R.id.recy_navigation)
    RecyclerView mRecyNavigation;
    Unbinder unbinder;
    @BindView(R.id.floatbutton)
    FloatingActionButton mFloatbutton;
    private boolean isScroll;
    private ArrayList<NaviBean.DataBean> dataBeans;
    private MyNavigationAdapter myNavigationAdapter;

    public NavFragment() {
        // Required empty public constructor
    }

    @Override
    public void initView() {


        mRecyNavigation.setLayoutManager(new LinearLayoutManager(getContext()));
        dataBeans = new ArrayList<>();
        myNavigationAdapter = new MyNavigationAdapter(getActivity(), dataBeans);
        mRecyNavigation.setAdapter(myNavigationAdapter);
        mTabNavigation.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyNavigation.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(position, 0);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        mRecyNavigation.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //??????????????????????????????recyclerview???????????????
                //0?????? ???1,2????????????
                if (newState == 0) {
                    isScroll = false;
                } else {
                    isScroll = true;
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyNavigation.getLayoutManager();
                //????????????????????????
                int top = layoutManager.findFirstVisibleItemPosition();
                mTabNavigation.setTabSelected(top);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //???????????????recyclerview????????????tab???????????????

               /*recyclerView : ???????????????view
                dx : ??????????????????
                dy : ??????????????????
                dx > 0 ????????????????????????,?????????????????????????????????
                dx < 0 ????????????????????????,?????????????????????????????????
                dy > 0 ????????????????????????,?????????????????????????????????
                dy < 0 ????????????????????????,?????????????????????????????????*/
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyNavigation.getLayoutManager();
                //????????????????????????
                int top = layoutManager.findFirstVisibleItemPosition();
                //???????????????????????????
                //int bottom = layoutManager.findLastVisibleItemPosition();
                if (isScroll) {
                    if (dy > 0) {
                        mTabNavigation.setTabSelected(top);
                    }
                }
            }
        });
        //  initRecy();
    }

    @Override
    public void initData() {
        super.initData();
        mFloatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyNavigation.smoothScrollToPosition(0);
            }
        });
        presenter.getData();
        //????????????
        RadioGroup rg = getActivity().findViewById(R.id.rp);
        //  FloatingActionButton flb = getActivity().findViewById(R.id.floatbutton);
        HindMain.hind(mRecyNavigation, rg, mFloatbutton);
    }


    /*@SuppressLint("ClickableViewAccessibility")
    private void initRecy() {
        mRecyNavigation.setOnTouchListener(new View.OnTouchListener() {
            public float mEndY;
            public float mStartY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mEndY = event.getY();
                        float v1 = mEndY - mStartY;
                        if (v1 > 1) {

                            //???????????????fragment???????????? ???????????????activity????????????
                            getActivity().findViewById(R.id.toobar).setVisibility(View.VISIBLE);
                            getActivity().findViewById(R.id.rdg).setVisibility(View.VISIBLE);
                            mBtnMain.setVisibility(View.VISIBLE);
                            //????????????????????????????????????id
                            //.setVisibility(View.VISIBLE);
                        } else if (v1 < -1) {
                            getActivity().findViewById(R.id.rdg).setVisibility(View.GONE);
                            getActivity().findViewById(R.id.toobar).setVisibility(View.GONE);
                            mBtnMain.setVisibility(View.GONE);
                        }
                        break;
                }
                return gestureDetector.onTouchEvent(event);
            }
        });*/

    /*        GestureDetector gestureDetector = new GestureDetector(getContext(),
                    new GestureDetector.OnGestureListener() {
                        @Override
                        public boolean onDown(MotionEvent e) {
                            return false;
                        }

                        @Override
                        public void onShowPress(MotionEvent e) {
                        }

                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            // do something
                            return true;
                        }

                        @Override
                        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                            return false;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            return false;
                        }
                    });
        }*/
    @Override
    public void onShow(NaviBean bean) {
        final List<NaviBean.DataBean> data = bean.getData();
        myNavigationAdapter.addData(data);
        mTabNavigation.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new TabView.TabTitle.Builder()
                        .setContent(data.get(position).getName())
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });
    }

    @Override
    public void onFail(String str) {

    }

    @Override
    protected NavPresnter<NaiView> initPresenter() {
        return new NavPresnter<>();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_nav;
    }

    @Override
    public void showProgressbar() {

    }

    @Override
    public void hideProgressBar() {

    }

}
