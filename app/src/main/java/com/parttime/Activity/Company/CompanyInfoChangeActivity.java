package com.parttime.Activity.Company;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.ConstantsConfig;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.UI.Interface.TopBarStatus;
import com.parttime.UI.TopBar;
import com.parttime.Utils.PhotoUtil;
import com.parttime.Utils.StringUtil;
import org.androidannotations.annotations.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 企业
 * 企业信息修改
 */
@EActivity(R.layout.activity_company_info_change)
public class CompanyInfoChangeActivity extends BaseActivity {

    @ViewById(R.id.company_info_change_logo)
    SimpleDraweeView mLogo;
    @ViewById(R.id.company_info_change_name)
    EditText mName;
    @ViewById(R.id.company_info_change_hr)
    EditText mHr;
    @ViewById(R.id.company_info_change_phone)
    EditText mPhone;
    @ViewById(R.id.company_info_change_TEL)
    EditText mTEL;
    @ViewById(R.id.company_info_change_location)
    EditText mLoc;
    @ViewById(R.id.company_info_change_remark)
    EditText mRemark;
    @ViewById(R.id.company_info_change_topBar)
    TopBar mTopbar;

    Context context = this;
    public String filePath = "";

    @AfterViews
    void start() {
        mTopbar.setTitle("企业资料修改");
        mTopbar.setBackIconVisible();
        mTopbar.setTopBarStatusListener(new TopBarStatus() {
            @Override
            public void onTopBarBackClickDelegate() {
                backToPreActivity();
            }
        });
        initViews();
    }

    private void initViews() {
        User user;
        user = User.getCurrentUser(this, User.class);
        if (user == null) {
            user = User.getUserInfo(this);
        }
        Log.w("Jumy_Uri:",Uri.parse(user.getHead()).toString());
        mLogo.setImageURI(Uri.parse(user.getHead()));
        mName.setText(user.getCompanyName());
        mHr.setText(user.getBoss());
        mPhone.setText(user.getMobilePhoneNumber());
        mTEL.setText(user.getTEL());
        mLoc.setText(user.getLocation());
        mRemark.setText(user.getRemark());
    }


    @Click({R.id.company_info_change_submit, R.id.company_info_change_takePhoto, R.id.company_info_change_select})
    void submitClick(View view) {
        switch (view.getId()) {
            case R.id.company_info_change_submit: {
                User user = User.getCurrentUser(context,User.class);
                user.setCompanyName(mName.getText().toString());
                user.setBoss(mHr.getText().toString());
                user.setMobilePhoneNumber(mPhone.getText().toString());
                user.setTEL(mTEL.getText().toString());
                user.setLocation(mLoc.getText().toString());
                user.setRemark(mRemark.getText().toString());
                uploadHeadImg(user);
                setResult(1110);
                break;
            }
            case R.id.company_info_change_takePhoto: {
                //coding  select photo
                File dir = new File(ConstantsConfig.MyAvatarDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                //原图
                File file = new File(dir, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                filePath = file.getAbsolutePath();//获取图片的保存路径
                Uri uri = Uri.fromFile(file);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                startActivityForResult(intent, ConstantsConfig.REQUESTCODE_UPLOADAVATAR_CAMERA);
                break;
            }
            case R.id.company_info_change_select: {

                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, ConstantsConfig.REQUESTCODE_UPLOADAVATAR_LOCATION);
                break;
            }
        }
    }

    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent = null;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        }
        else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    boolean isFromCamera = false;// 区分拍照旋转
    int degree = 0;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantsConfig.REQUESTCODE_UPLOADAVATAR_CAMERA: {
                //拍照修改头像
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        showToast("SD卡不可用");
                        return;
                    }
                    isFromCamera = true;
                    File file = new File(filePath);
                    degree = PhotoUtil.readPictureDegree(file.getAbsolutePath());
                    Log.i("life", "拍照后的角度：" + degree);
                    startImageAction(Uri.fromFile(file), 200, 200,
                            ConstantsConfig.REQUESTCODE_UPLOADAVATAR_CROP, true);
                }
                break;
            }
            case ConstantsConfig.REQUESTCODE_UPLOADAVATAR_LOCATION: {
                Uri uri;
                if ((data == null)) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if ((!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))) {
                        showToast("SD卡不可用");
                        return;
                    }
                    isFromCamera = false;
                    uri = data.getData();
                    startImageAction(uri, 200, 200, ConstantsConfig.REQUESTCODE_UPLOADAVATAR_CROP, true);
                }
                else {
                    showToast("获取图片失败");
                }
                break;
            }
            case ConstantsConfig.REQUESTCODE_UPLOADAVATAR_CROP: {
                //裁剪图像返回
                if (data == null) {
                    return;
                }
                else {
                    saveCropHeadImg(data);
                }
                break;
            }
            default:
                break;
        }
    }

    /**
     * 先上传头像到服务器，再根据图像的url存进User缓存然后更新信息
     * @param user
     */
    private void uploadHeadImg(final User user) {
        BmobProFile.getInstance(context).upload(path, new UploadListener() {
            @Override
            public void onSuccess(String s, String s1, BmobFile bmobFile) {
                Log.w("Jumy", "头像上传文件系统成功");
                updateUserMessage(bmobFile.getUrl(),user);
            }

            @Override
            public void onProgress(int i) {

            }

            @Override
            public void onError(int i, String s) {
                Log.w("jumy", "头像上传文件系统失败" + s);
            }
        });

    }

    /**
     * 更新本地显示头像 refreshHeadImg
     */
    @UiThread
    void refreshHeadImg(String HeadImg) {
        if (!StringUtil.isNullOrEmpty(HeadImg)) {
            final Uri uri = Uri.parse(HeadImg);
            mLogo.setImageURI(uri);
        }
    }

    private void updateUserMessage(final String url, User user) {
        user.setHead(url);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                showToast("信息更新成功！");
            }

            @Override
            public void onFailure(int i, String msg) {
                showToast("信息更新失败：" + msg);
            }
        });
    }

    String path;

    /**
     * 保存裁剪的头像
     *
     * @param data
     */
    private void saveCropHeadImg(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            Log.i("life", "HeadImg - bitmap = " + bitmap);
            if (bitmap != null) {
                //bitmap = PhotoUtil.toRoundCorner(bitmap, 10); 无需裁剪
                if (isFromCamera && degree != 0) {
                    bitmap = PhotoUtil.rotaingImageView(degree, bitmap);
                }
                // 保存图片
                String filename = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date()) + ".png";
                path = ConstantsConfig.MyAvatarDir + filename;
                PhotoUtil.saveBitmap(ConstantsConfig.MyAvatarDir, filename,
                        bitmap, true);
                // 上传头像--保存之后再上传－－这里先设置显示这个刚照好的Image
                refreshHeadImg(path);
                //uploadHeadImg();
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }
}
