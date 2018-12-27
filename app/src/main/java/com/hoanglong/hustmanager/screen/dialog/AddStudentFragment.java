package com.hoanglong.hustmanager.screen.dialog;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.database.Class;
import com.hoanglong.hustmanager.database.DatabaseHelper;
import com.hoanglong.hustmanager.database.Student;
import com.hoanglong.hustmanager.utils.Constants;
import com.hoanglong.hustmanager.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.hoanglong.hustmanager.DetailClassActivity.BUNDLE_CLASS;

public class AddStudentFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = AddStudentFragment.class.getName();

    @BindView(R.id.edit_ma_sv)
    EditText mEditMaSV;
    @BindView(R.id.edit_diem_ck)
    EditText mEditDiemCK;
    @BindView(R.id.edit_diem_qt)
    EditText mEditDiemQT;
    @BindView(R.id.edit_ho_ten)
    EditText mEditHoTen;
    @BindView(R.id.edit_lop_sv)
    EditText mEditLopSV;
    @BindView(R.id.text_save_new_subject)
    TextView mTextSave;
    @BindView(R.id.text_cancel_new_subject)
    TextView mTextCancle;
    @BindView(R.id.image_student)
    ImageView mImageStudent;
    private DatabaseHelper db;
    private OnChangeListener mOnChangeListener;
    private Class mClass;

    private static final int IMG_REQUEST = 1;
    private static final int TAKE_PHOTO_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;
    private static final String DOMAIN = "HUST_MANAGER";
    private Uri mPath;
    boolean mIsGallery = true;
    private Bitmap mBitmap;
    private String mNameOfImage;
    private ProgressDialog mProgressDialog;
    private String image;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnChangeListener = (OnChangeListener) context;
    }

    public static AddStudentFragment newInstance(Class aClass) {

        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_CLASS, aClass);
        AddStudentFragment fragment = new AddStudentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mClass = getArguments().getParcelable(BUNDLE_CLASS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_add_student, container, false);
    }

    public interface OnChangeListener {
        void onChangeListener(long id);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        db = new DatabaseHelper(getContext());

        mTextCancle.setOnClickListener(this);
        mTextSave.setOnClickListener(this);
        mImageStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_save_new_subject:
                checkValid();
                break;
            case R.id.text_cancel_new_subject:
                getDialog().dismiss();
                break;
            case R.id.image_student:
                chooseImage();
                break;
        }
    }

    private void chooseImage() {
        if (getContext() == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, IMG_REQUEST);
                                break;
                            case 1:
                                showCameraPreview();
                                break;
                        }
                    }
                });

        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(getContext(), "camera_permission_denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PHOTO_CODE);
    }

    private void showCameraPreview() {
        if (getContext() == null) return;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            startCamera();
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        if (getActivity() == null) {
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showProgress("Uploading...");
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            if (getActivity() == null) return;
            mPath = data.getData();
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mPath);
                new UploadImage().execute();
                mImageStudent.setImageBitmap(mBitmap);

            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            mIsGallery = true;
        }
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            if (data.getExtras() == null) {
                return;
            }
            mBitmap = (Bitmap) data.getExtras().get("data");
            mImageStudent.setImageBitmap(mBitmap);
            mIsGallery = false;
        }
    }

    private void uploadImage() {
        if (getActivity() == null) return;
        Map config = new HashMap();
        config.put("cloud_name", Constants.Cloudinary.CLOUD_NAME);
        config.put("api_key", Constants.Cloudinary.API_KEY);
        config.put("api_secret", Constants.Cloudinary.SECRET_KEY);
        Cloudinary cloudinary = new Cloudinary(Constants.Cloudinary.URL);
        //TODO: id fb of user
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        String time = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.DATE)
                + "" + currentTime.getHours() + "" + currentTime.getMinutes() + "" + currentTime.getSeconds();
        mNameOfImage = DOMAIN + time;
        cloudinary.url()
                .transformation(new Transformation().width(300).crop("fill"))
                .generate(mNameOfImage + ".jpg");
        try {

            InputStream inputStream;
            if (mIsGallery) {
                inputStream = getActivity().getContentResolver().openInputStream(mPath);
                cloudinary.uploader().upload(inputStream, ObjectUtils.asMap("public_id", mNameOfImage));
            } else {
                String myBase64Image = encodeToBase64(mBitmap);
                cloudinary.uploader().upload(myBase64Image, ObjectUtils.asMap("public_id", mNameOfImage));

            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideProgress();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    class UploadImage extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            uploadImage();
            return null;
        }
    }

    public void showProgress(String title) {
        if (mProgressDialog != null) {
            return;
        }
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(title);
        mProgressDialog.show();
    }

    public void hideProgress() {
        if (mProgressDialog == null)
            return;
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    private void checkValid() {
        if (mEditMaSV.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống mã học phần", Toast.LENGTH_SHORT).show();
        } else if (
                mEditHoTen.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống tên học phần", Toast.LENGTH_SHORT).show();
        } else if (mEditLopSV.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống số tín chỉ học phần", Toast.LENGTH_SHORT).show();
        } else {
            saveStudent();
            Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
        }
    }

    private void saveStudent() {
        if (mNameOfImage != null) {
            image = "https://res.cloudinary.com/hoanglongb/image/upload/v1544258437/" + mNameOfImage + ".jpg";
            String name = mEditHoTen.getText().toString();
            String mssv = mEditMaSV.getText().toString();
            String lopsv = mEditLopSV.getText().toString();
            String hocphan = mClass.getMaLH();
            Float diemQT = Float.parseFloat(mEditDiemQT.getText().toString());
            Float diemCK = Float.parseFloat(mEditDiemCK.getText().toString());
            String idStudent = Utils.getTime();

            Student student = new Student();
            student.setImage(image);
            student.setId(idStudent);
            student.setNameStudent(name);
            student.setMaSV(mssv);
            student.setMaLopHoc(lopsv);
            student.setMaLopMonHoc(hocphan);
            student.setDiemCK(diemCK);
            student.setDiemQT(diemQT);

            long id = db.insertStudent(student);
            mOnChangeListener.onChangeListener(id);
        }
    }
}
