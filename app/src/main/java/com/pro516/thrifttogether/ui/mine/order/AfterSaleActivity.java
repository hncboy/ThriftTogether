package com.pro516.thrifttogether.ui.mine.order;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.mine.order.tools.BitmapUtils;
import com.pro516.thrifttogether.ui.mine.order.tools.CleanCacheManager;
import com.pro516.thrifttogether.ui.mine.order.tools.FileUtils;
import com.pro516.thrifttogether.ui.mine.order.tools.KeyBoardManager;
import com.pro516.thrifttogether.ui.mine.order.tools.PermissionCheckUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class AfterSaleActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private String[] mReason = {"质量问题", "服务态度问题", "商品错误", "速度过慢", "其他"};
    private EditText mEtCommentContent;
    private TextView mSubmit;
    private ImageView mIvChooseGoodsPic;
    private HorizontalScrollView mhsvCommentImgs;
    private TextView mReasonText;
    private List<String> mImageUrls;//所有晒图图片路径
    private InputMethodManager manager;
    public Context context;
    public static final String KEY_IMAGE_LIST = "imageList";
    public static final String KEY_CURRENT_INDEX = "currentIndex";
    private final int REQUEST_CODE_PICTURE = 1;
    private final int RESULT_CODE_LARGE_IMAGE = 1;
    //晒单图片最多选择四张
    private final int MAX_PIC = 4;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_mine_order_after_sale;
    }

    @Override
    protected void init() {
        initData();
        initView();
        initListener();
        context = this;

        AppCompatImageButton backBtn = findViewById(R.id.common_toolbar_function_left);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setImageDrawable(getDrawable(R.drawable.ic_arrow_back_24dp));
        backBtn.setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.title);
        title.setText("售后服务");
        RelativeLayout mLinearLayout = findViewById(R.id.mine_order_after_sale_reason);
        mLinearLayout.setOnClickListener(this);
    }

    public void singleChoice() {
        MaterialDialog.Builder mBuilder = new MaterialDialog.Builder(AfterSaleActivity.this);
        mBuilder.title("选择退款原因");
        mBuilder.titleGravity(GravityEnum.CENTER);
        mBuilder.titleColor(Color.parseColor("#000000"));
        mBuilder.items(mReason);
        mBuilder.autoDismiss(false);
        mBuilder.widgetColor(Color.parseColor("#4693EC"));
        mBuilder.positiveText("确定");
        mBuilder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

            }
        });

        mBuilder.itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(AfterSaleActivity.this, "请选择", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AfterSaleActivity.this, text, Toast.LENGTH_LONG).show();
                    mReasonText.setText(text);
                    dialog.dismiss();
                }
                return false;
            }
        });
        MaterialDialog mMaterialDialog = mBuilder.build();
        mMaterialDialog.show();
    }

    private void initData() {
        mImageUrls = new ArrayList<>();
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void initListener() {
        mSubmit.setOnClickListener(this);
        mIvChooseGoodsPic.setOnClickListener(this);
        mEtCommentContent.addTextChangedListener(this);
    }

    private void initView() {
        mSubmit = findViewById(R.id.tv_submit);
        mEtCommentContent = findViewById(R.id.et_comment_content);
        mIvChooseGoodsPic = findViewById(R.id.iv_choose_goods_pic);
        mhsvCommentImgs = findViewById(R.id.hsv_comment_imgs);
        mReasonText =findViewById(R.id.mine_order_after_sale_reason_text);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //清除临时压缩图片文件
        CleanCacheManager.cleanExternalCache(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardManager.isShouldHideInput(v, ev)) {
                if (manager != null) {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_order_after_sale_reason:
                singleChoice();
                break;
            case R.id.tv_submit:
                //提交
                validateComment();
                if (mImageUrls.isEmpty()) {
                    Toast.makeText(context, "没有图片: ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "第一张图片的路径: "+ mImageUrls.get(0), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.iv_choose_goods_pic:
                //检查是否有打开照相机和文件读写的权限
                if (PermissionCheckUtil.checkCameraAndExternalStoragePermission(this))
                    //权限已经开启, 调出图片选择界面
                    MultiImageSelector.create().count(MAX_PIC - mImageUrls.size()).start(this, REQUEST_CODE_PICTURE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限允许
            } else {
                openSettingActivity(this, "您没有打开相机或文件存储权限，请在设置中打开授权");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICTURE) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                mImageUrls.addAll(path);
                handleCommentPicList(mImageUrls, false);
            }
        } else if (resultCode == RESULT_CODE_LARGE_IMAGE) {
            //晒单大图页返回, 重新设置晒单图片
            handleCommentPicList(mImageUrls = data.getStringArrayListExtra(KEY_IMAGE_LIST), true);
        }
    }

    /**
     * 处理选择的评价图片
     *
     * @param paths      图片的路径集合
     * @param isFromBack 是否来自LargeImageActivity返回
     */
    private void handleCommentPicList(final List<String> paths, boolean isFromBack) {
        LinearLayout rootView = new LinearLayout(context);
        View commentView;
        SimpleDraweeView sdv_pic;
        for (int i = 0, len = paths.size(); i < len; i++) {
            commentView = getLayoutInflater().inflate(R.layout.order_comment_pic_item, null);
            sdv_pic = commentView.findViewById(R.id.sdv_pic);
            if (isFromBack) {
                //来自LargeImageActivity
                sdv_pic.setImageURI(Uri.parse("file://" + paths.get(i)));
            } else {
                //来自图片选择器
                String path = FileUtils.getCachePath(context);//获取app缓存路径来存放临时图片
                BitmapUtils.compressImage(paths.get(i), path, 95);
                sdv_pic.setImageURI(Uri.parse("file://" + path));
                mImageUrls.set(i, path);
            }

            final int finalI = i;
            sdv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击HorizontalScrollView里的晒单图进入图片详情页
                    Intent intent = new Intent(context, CommentLargeImageActivity.class);
                    intent.putExtra(KEY_CURRENT_INDEX, finalI);
                    intent.putStringArrayListExtra(KEY_IMAGE_LIST, (ArrayList<String>) paths);
                    startActivityForResult(intent, REQUEST_CODE_PICTURE);
                }
            });
            AutoUtils.auto(commentView);
            rootView.addView(commentView);
        }
        mhsvCommentImgs.removeAllViews();
        mhsvCommentImgs.addView(rootView);
    }

    /**
     * 评价内容验证
     */
    private void validateComment() {
        String content = mEtCommentContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(context, "评论内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String content = mEtCommentContent.getText().toString();
        if (content.length() >= 255) {
            Toast.makeText(context, "评论内容不能多于255个字符", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void openSettingActivity(final Activity activity, String message) {
        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //打开权限设置页面
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                startActivity(intent);
            }
        });
    }

    private void showMessageOKCancel(Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
}
