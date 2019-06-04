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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pro516.thrifttogether.R;
import com.pro516.thrifttogether.app.cos.CosModel;
import com.pro516.thrifttogether.app.cos.IDataRequestListener;
import com.pro516.thrifttogether.entity.mine.SimpleReviewVO;
import com.pro516.thrifttogether.ui.base.BaseActivity;
import com.pro516.thrifttogether.ui.mine.order.tools.BitmapUtils;
import com.pro516.thrifttogether.ui.mine.order.tools.CleanCacheManager;
import com.pro516.thrifttogether.ui.mine.order.tools.FileUtils;
import com.pro516.thrifttogether.ui.mine.order.tools.KeyBoardManager;
import com.pro516.thrifttogether.ui.mine.order.tools.PermissionCheckUtil;
import com.pro516.thrifttogether.ui.network.HttpUtils;
import com.pro516.thrifttogether.util.ThreadUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.pro516.thrifttogether.ui.network.Url.REVIEW;

public class OrderCommentActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private List<String> mReviewImages = new ArrayList<>();
    private CountDownLatch latch;
    private EditText mEtCommentContent;
    private TextView mTvSubmit;
    private ImageView mIvChooseGoodsPic;
    private HorizontalScrollView mHsvCommentImgs;
    private ImageView iv_comment_star_1, iv_comment_star_2, iv_comment_star_3, iv_comment_star_4, iv_comment_star_5;
    private List<ImageView> starList;
    private List<String> imageUrls;//所有晒图图片路径
    private int currentStarCount;
    private InputMethodManager manager;
    public Context context;
    public static final String KEY_IMAGE_LIST = "imageList";
    public static final String KEY_CURRENT_INDEX = "currentIndex";
    private final int REQUEST_CODE_PICTURE = 1;
    private SimpleReviewVO simpleReviewVO;
    private String orderID;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_order_comment;
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
        title.setText("评价");
    }

    private void initData() {
        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");
        Log.d("adwd", "initData: "+orderID);
        starList = new ArrayList<>();
        imageUrls = new ArrayList<>();
        currentStarCount = 5;//默认为五星好评
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void initListener() {
        mTvSubmit.setOnClickListener(this);
        mIvChooseGoodsPic.setOnClickListener(this);
        iv_comment_star_1.setOnClickListener(this);
        iv_comment_star_2.setOnClickListener(this);
        iv_comment_star_3.setOnClickListener(this);
        iv_comment_star_4.setOnClickListener(this);
        iv_comment_star_5.setOnClickListener(this);
        mEtCommentContent.addTextChangedListener(this);
    }

    private void initView() {
        mTvSubmit = findViewById(R.id.tv_submit);
        mEtCommentContent = findViewById(R.id.et_comment_content);
        mIvChooseGoodsPic = findViewById(R.id.iv_choose_goods_pic);
        mHsvCommentImgs = findViewById(R.id.hsv_comment_imgs);
        starList.add(iv_comment_star_1 = findViewById(R.id.iv_comment_star_1));
        starList.add(iv_comment_star_2 = findViewById(R.id.iv_comment_star_2));
        starList.add(iv_comment_star_3 = findViewById(R.id.iv_comment_star_3));
        starList.add(iv_comment_star_4 = findViewById(R.id.iv_comment_star_4));
        starList.add(iv_comment_star_5 = findViewById(R.id.iv_comment_star_5));
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

    private void submit() {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpUtils.doPost(REVIEW, simpleReviewVO);
                    Log.d("----------", "run: ok");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("----------", "run: fail");
                }
            }
        }.start();
    }
    public void ConfirmationDialog() {
        MaterialDialog.Builder mBuilder = new MaterialDialog.Builder(context);
        mBuilder.content("评价已成功！");
        mBuilder.contentColor(Color.parseColor("#000000"));
        mBuilder.positiveText("确定");
        mBuilder.titleGravity(GravityEnum.CENTER);
        mBuilder.buttonsGravity(GravityEnum.START);
        mBuilder.negativeText("取消");
        mBuilder.cancelable(false);
        MaterialDialog mMaterialDialog = mBuilder.build();
        mMaterialDialog.show();
        mBuilder.onAny((dialog, which) -> {
            if (which == DialogAction.POSITIVE) {
                mMaterialDialog.dismiss();
                finish();
            } else if (which == DialogAction.NEGATIVE) {
                mMaterialDialog.dismiss();
                finish();
            }
        });
    }
    @Override
    public void onClick(View v) {
        //晒单图片最多选择四张
        int MAX_PIC = 4;
        switch (v.getId()) {
            case R.id.tv_submit:
                //评价提交
                validateComment();
                if (imageUrls.isEmpty()) {
                    Toast.makeText(context, "没有图片: " + " 评分: " + currentStarCount, Toast.LENGTH_SHORT).show();
                } else {
                    uploadReviewImages();
                    MaterialDialog materialDialog =  new MaterialDialog.Builder(this)
                            .title("Progress")
                            .content("Please Wait...")
                            .progress(true, 0)
                            .show();
                    ThreadUtils.runOnBackgroundThread(() -> {
                        if (latch != null) {
                            Log.i("latch", "run: latch ");
                            try {
                                latch.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        materialDialog.dismiss();
                        Log.i("评论图片上传", "上传结束");
                        simpleReviewVO = new SimpleReviewVO();
                        simpleReviewVO.setUserId(1);
                        Log.i("评论图片上传", orderID);
                        simpleReviewVO.setOrderNo(orderID);
                        simpleReviewVO.setReviewContent(mEtCommentContent.getText().toString());
                        simpleReviewVO.setReviewPicUrlList(mReviewImages);
                        simpleReviewVO.setReviewScore(currentStarCount);
                        submit();
                    });
                    ConfirmationDialog();
                }
                break;
            case R.id.common_toolbar_function_left:
                finish();
                break;
            case R.id.iv_choose_goods_pic:
                //检查是否有打开照相机和文件读写的权限
                if (PermissionCheckUtil.checkCameraAndExternalStoragePermission(this))
                    //权限已经开启, 调出图片选择界面
                    MultiImageSelector.create().count(MAX_PIC - imageUrls.size()).start(this, REQUEST_CODE_PICTURE);
                break;

            case R.id.iv_comment_star_1:
                currentStarCount = 1;
                break;

            case R.id.iv_comment_star_2:
                currentStarCount = 2;
                break;

            case R.id.iv_comment_star_3:
                currentStarCount = 3;
                break;

            case R.id.iv_comment_star_4:
                currentStarCount = 4;
                break;

            case R.id.iv_comment_star_5:
                currentStarCount = 5;
                break;

            default:
                break;
        }
        for (int i = 0, len = starList.size(); i < len; i++) {
            starList.get(i).setImageResource(i < currentStarCount ? R.drawable.icon_comment_star_red : R.drawable.icon_comment_star_gray);
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
        int RESULT_CODE_LARGE_IMAGE = 1;
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICTURE) {
                // 获取返回的图片列表
                List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                imageUrls.addAll(paths);
                handleCommentPicList(imageUrls, false);

            }
        } else if (resultCode == RESULT_CODE_LARGE_IMAGE) {
            //晒单大图页返回, 重新设置晒单图片
            handleCommentPicList(imageUrls = data.getStringArrayListExtra(KEY_IMAGE_LIST), true);
        }
    }

    /**
     * 上传评论图片
     */
    private void uploadReviewImages() {
        // 设置一个数量锁
        latch = new CountDownLatch(imageUrls.size());
        CosModel cosModel = new CosModel(getApplication());
        for (String path : imageUrls) {
            System.out.println("评论path = " + path);
            String fileName = "file://" + path;
            Uri pathUri = Uri.parse(fileName);
            try {
                InputStream inputStream = getContentResolver().openInputStream(pathUri);
                String str = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
                String filename = str.substring(1, 10);
                Log.i("评论图片", "uploadImage: fileName = " + filename);
                cosModel.uploadReviewPic(filename, inputStream, new IDataRequestListener() {
                    @Override
                    public void loadSuccess(Object object) {
                        //加上http
                        String url = object.toString();
                        Log.i("评论图片url", url);
                        mReviewImages.add(url);
                        latch.countDown();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
                imageUrls.set(i, path);
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
        mHsvCommentImgs.removeAllViews();
        mHsvCommentImgs.addView(rootView);
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
