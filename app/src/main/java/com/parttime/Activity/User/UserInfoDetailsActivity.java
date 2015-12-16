package com.parttime.Activity.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.parttime.Activity.Common.ChangePassActivity_;
import com.parttime.BaseLibs.BaseActivity;
import com.parttime.Modules.ConstantsConfig;
import com.parttime.Modules.User;
import com.parttime.R;
import com.parttime.Utils.PhotoUtil;
import com.parttime.Utils.StringUtil;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 个人用户
 * 详细信息
 */
@EActivity(R.layout.activity_user_info_details)
public class UserInfoDetailsActivity extends BaseActivity {


    @ViewById(R.id.user_info_details_head)
    SimpleDraweeView mHead;
    @ViewById(R.id.user_info_details_nick)
    TextView mNickname;
    @ViewById(R.id.user_info_details_remark)
    TextView mRemark;
    @ViewById(R.id.user_info_details_ContactMethod)
    TextView mPhone;

    public String filePath = "";

    @AfterViews
    void start() {
        User user = User.getUserInfo(this);
        mHead.setImageURI(Uri.parse(user.getHead()));
        mNickname.setText(user.getNickname());
        mRemark.setText(user.getRemark());
        mPhone.setText(user.getMobilePhoneNumber());
    }

    @Click({R.id.user_info_details_ll1, R.id.user_info_details_ll2, R.id.user_info_details_ll3,
            R.id.user_info_details_ll4, R.id.user_info_details_ll5, R.id.user_info_details_ll6,
            R.id.user_info_details_ll7})
    void lvClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_details_ll1: {
                //HEAD IMAGE
                doPhoto();
                break;
            }
            case R.id.user_info_details_ll2: {
                ChangeInfoActivity_.intent(this).extra("key",111).startForResult(111);
                break;
            }
            case R.id.user_info_details_ll3: {
                ChangeInfoActivity_.intent(this).extra("key",222).startForResult(222);
                break;
            }
            case R.id.user_info_details_ll4: {
                break;
            }
            case R.id.user_info_details_ll5: {
                break;
            }
            case R.id.user_info_details_ll6: {
                ChangeInfoActivity_.intent(this).extra("key",333).startForResult(333);
                break;
            }
            case R.id.user_info_details_ll7: {
                //change password
                ChangePassActivity_.intent(this).start();
                break;
            }
            default:
                break;
        }
    }

    private void doPhoto() {
        String [] items = new String[]{"拍照","相册"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:{
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
                            case 1:{
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intent, ConstantsConfig.REQUESTCODE_UPLOADAVATAR_LOCATION);
                                break;
                            }
                            default:break;
                        }
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(true);
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
                if (data == null){
                    return;
                }
                Uri uri;
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
                if (data == null){
                    return;
                }
                saveCropHeadImg(data);
                break;
            }
            default:
                break;
        }
        if (data == null){
            return;
        }
        String text = data.getStringExtra("text");
        if (StringUtil.isNullOrEmpty(text)){
            return;
        }
        if (requestCode == 111 && resultCode == 1110){
            mNickname.setText(text);
        } else if (requestCode == 222 && resultCode == 2220){
            mRemark.setText(text);
        } else if (requestCode == 333 && resultCode == 3330){
            mPhone.setText(text);
        }

    }
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
                String path = ConstantsConfig.MyAvatarDir + filename;
                PhotoUtil.saveBitmap(ConstantsConfig.MyAvatarDir, filename,
                        bitmap, true);
                // 上传头像--保存之后再上传
                uploadHeadImg(User.getCurrentUser(this,User.class),path);
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        } else {
            Log.w("Jumy","data is null");
        }
    }
    /**
     * 先上传头像到服务器，再根据图像的url存进User缓存然后更新信息
     * @param user
     */
    private void uploadHeadImg(final User user, String path) {
        BmobProFile.getInstance(this).upload(path, new UploadListener() {
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

    private void updateUserMessage(final String url, User user) {
        user.setHead(url);
        user.update(this, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.w("Jumy","头像更新成功！");
                mHead.setImageURI(Uri.parse(url));
            }

            @Override
            public void onFailure(int i, String msg) {
                Log.w("Jumy","头像更新失败：" + msg);
            }
        });
    }
}
