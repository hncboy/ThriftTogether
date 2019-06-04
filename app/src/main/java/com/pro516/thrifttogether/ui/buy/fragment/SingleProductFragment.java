package com.pro516.thrifttogether.ui.buy.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.buy.activity.ProductInfoActivity;
import com.pro516.thrifttogether.ui.buy.activity.SubmitOrderActivity;
import com.pro516.thrifttogether.ui.buy.adapter.ProductInfoAdapter;
import com.pro516.thrifttogether.ui.buy.adapter.SingleProductAdapter;
import com.pro516.thrifttogether.ui.buy.entity.DetailAndNoteEntity;
import com.pro516.thrifttogether.ui.buy.entity.SingleProductEntity;
import com.pro516.thrifttogether.ui.buy.entity.VO.ProductDetailsVO;
import com.pro516.thrifttogether.ui.buy.layoutManage.MyLinearLayoutManager;
import com.pro516.thrifttogether.ui.home.activity.StoreActivity;
import com.pro516.thrifttogether.ui.home.entity.VO.ShopDetailsVO;
import com.pro516.thrifttogether.ui.home.entity.VO.SimpleReviewVO;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.ui.network.JsonParser;
import com.pro516.thrifttogether.ui.network.Url;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;
import static com.pro516.thrifttogether.ui.network.Url.ERROR;

public class SingleProductFragment extends BaseFragment{

    TextView title, saleNum,priceTv,discountTv,orgrialTv;
    RecyclerView detailRv, noteRv;
    Button buyButton;
    ProductDetailsVO mData;
    ImageView img;
    private TextView tagTv;
    private boolean isStar;
    public SingleProductFragment() {
        // Required empty public constructor
    }

    @Override
    protected void init(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mData = (ProductDetailsVO) bundle.getSerializable("data");
        } else {
            mData = new ProductDetailsVO();
        }
        initView(view);
        initRecyclerView();
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SubmitOrderActivity.class);
                intent.putExtra("id",mData.getProductId());
                intent.putExtra("name",mData.getProductName());
                intent.putExtra("price",mData.getProductPrice());
                intent.putExtra("img",mData.getProductCoverUrl());
                startActivity(intent);
            }
        });

    }

    private void initView(View view){
        detailRv = view.findViewById(R.id.buy_single_product_package_details_rv);
        noteRv = view.findViewById(R.id.buy_single_product_sweat_note_rv);
        title = view.findViewById(R.id.buy_single_product_name_text);
        saleNum = view.findViewById(R.id.buy_single_product_sale);
        buyButton = view.findViewById(R.id.buy_single_product_bt);
        tagTv = view.findViewById(R.id.buy_single_product_tag);
        priceTv = view.findViewById(R.id.buy_single_product_price_tv);
        discountTv = view.findViewById(R.id.buy_single_product_discount);
        orgrialTv = view.findViewById(R.id.buy_single_product_max_price);
        img = view.findViewById(R.id.product_detail_img);
        title.setText(mData.getProductName());
        saleNum.setText(String.format("半年销量：%s件",mData.getProductSales()));
        priceTv.setText(String.format("￥%s",mData.getProductPrice().toString()));
        discountTv.setText(String.format("%s折",mData.getProductDiscount()));
        orgrialTv.setText(String.format("最高门店价：￥%s元",mData.getProductOriginalPrice()));
        List<String> tags = mData.getProductTags();
        StringBuilder res = new StringBuilder();
        for (String tag : tags){
            res.append(tag);
            res.append(" | ");
        }
        res.delete(res.length()-3,res.length());
        tagTv.setText(res.toString());
        RoundedCorners roundedCorners = new RoundedCorners(30);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(getActivity()).load(mData.getProductCoverUrl()).apply(options).into(img);
    }
    public void initRecyclerView() {
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        MyLinearLayoutManager manager2 = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        detailRv.setLayoutManager(manager);
        manager.setScrollEnabled(false);
        manager2.setScrollEnabled(false);
        noteRv.setLayoutManager(manager2);
        noteRv.setNestedScrollingEnabled(false);
        detailRv.setNestedScrollingEnabled(false);
        ProductInfoAdapter adapter = new ProductInfoAdapter(R.layout.item_product_detail,mData.getProductContent());
        ProductInfoAdapter adapter1 = new ProductInfoAdapter(R.layout.item_product_detail,mData.getProductReminder());
        detailRv.setAdapter(adapter);
        noteRv.setAdapter(adapter1);
    }

    // 数据拆分重新组装的方法
    private List<Object> sortData(SingleProductEntity bean) {
        List<DetailAndNoteEntity> arrays = bean.getData();
        // 用来进行数据重组的新的集合arrays_obj，之所以泛型设为Object，是因为该例中的集合元素既可能为String有可能是一个bean
        List<Object> arrays_obj = new ArrayList<>();
        for (DetailAndNoteEntity array : arrays) {
            List<DetailAndNoteEntity.CategoryDetailEntity> logs = array.getData();
            // 拿到String值添加进集合arrays_obj
            arrays_obj.add(array.getCategoryTitle());
            // 如果该标题下的集合里面有数据的话，遍历拿到添加进新集合arrays_obj
            if (logs != null && logs.size() > 0) {
                arrays_obj.addAll(logs);
            }
        }
        return arrays_obj;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_single_product;
    }

}
