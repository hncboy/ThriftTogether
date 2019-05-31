package com.pro516.thrifttogether.ui.buy.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseFragment;
import com.pro516.thrifttogether.ui.buy.activity.SubmitOrderActivity;
import com.pro516.thrifttogether.ui.buy.adapter.SingleProductAdapter;
import com.pro516.thrifttogether.ui.buy.entity.DetailAndNoteEntity;
import com.pro516.thrifttogether.ui.buy.entity.SingleProductEntity;
import com.pro516.thrifttogether.ui.buy.layoutManage.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SingleProductFragment extends BaseFragment {

    TextView title, saleNum;
    RecyclerView detailRv, noteRv;
    Button buyButton;
    public SingleProductFragment() {
        // Required empty public constructor
    }

    @Override
    protected void init(View view) {
        detailRv = view.findViewById(R.id.buy_single_product_package_details_rv);
        noteRv = view.findViewById(R.id.buy_single_product_sweat_note_rv);
        title = view.findViewById(R.id.buy_single_product_name_text);
        saleNum = view.findViewById(R.id.buy_single_product_sale);
        buyButton = view.findViewById(R.id.buy_single_product_bt);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SubmitOrderActivity.class);
            }
        });
        initRecyclerView();
    }

    public void initRecyclerView() {
        // 将网络请求获取到的json字符串转成的对象进行二次重组，生成集合List<Object>
        List<Object> list = null;
        DetailAndNoteEntity.CategoryDetailEntity a = new DetailAndNoteEntity.CategoryDetailEntity("aaa", 2);
        List<DetailAndNoteEntity.CategoryDetailEntity> b = new ArrayList<>();
        b.add(a);
        DetailAndNoteEntity c = new DetailAndNoteEntity("vvv", b);
        List<DetailAndNoteEntity> e = new ArrayList<>();
        e.add(c);
        SingleProductEntity d = new SingleProductEntity(200, "no message", e);
        //SingleProductEntity data = new Gson().fromJson(HttpUtils.getStringFromServer(""),SingleProductEntity.class);
        list = sortData(d);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        MyLinearLayoutManager manager2 = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        detailRv.setLayoutManager(manager);
        manager.setScrollEnabled(false);
        manager2.setScrollEnabled(false);
        noteRv.setLayoutManager(manager2);
        SingleProductAdapter adapter = new SingleProductAdapter(list);
        detailRv.setAdapter(adapter);
        noteRv.setAdapter(adapter);
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
