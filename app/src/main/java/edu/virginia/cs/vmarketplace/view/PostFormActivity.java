package edu.virginia.cs.vmarketplace.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.PostDataHolder;
import edu.virginia.cs.vmarketplace.view.fragments.AbstractFragment;

public class PostFormActivity extends AppCompatActivity {
    private AbstractFragment[] fragments;
    private String mCurrentPhotoPath;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    // request code for popup window
    private static final int REQUEST_USE_ALBUM = 0;
    private static final int REQUEST_TAKE_PHOTO = 1;
    final PostDataHolder postDataHolder = PostDataHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        EditText writeTitle = findViewById(R.id.post_title);
        writeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast msg = Toast.makeText(getBaseContext(), "only 1 line allowed", Toast.LENGTH_LONG);
                msg.show();
            }
        });

        EditText writeContent = findViewById(R.id.post_content);
        writeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast msg = Toast.makeText(getBaseContext(), "maximum 2000 words", Toast.LENGTH_LONG);
                msg.show();
            }
        });

        // for addImage icon
        final ImageView addImageView = findViewById(R.id.add_image);
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show popup window
                showPopup(PostFormActivity.this, REQUEST_TAKE_PHOTO);
            }
        });

//        // dynamically create image views
//        GridLayout gridLayout = findViewById(R.id.gridlayout_insert_point);
//        int total = postDataHolder.getImageCount();
//        int column = 4;
//        int row = 3;
//        gridLayout.setColumnCount(column);
//        gridLayout.setRowCount(row);
//        for(int i=0, c=0, r=0; i<postDataHolder.getSavedImage().size() && i <= total; i++, c++) {
//            if(c == column) {
//                c = 0;
//                r++;
//            }
//            ImageView oImageView = new ImageView(this);
//            oImageView.setId(r * column + c);
//            String curBitmapPath = postDataHolder.getSavedImage().get(r * column + c);
//            oImageView.setImageBitmap(postDataHolder.getBitmapMap().get(curBitmapPath));
//            oImageView.setLayoutParams(new ViewGroup.LayoutParams(oImageView.getDrawable().getIntrinsicWidth(),
//                    oImageView.getDrawable().getIntrinsicHeight()));
//            GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
//            GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
//            if(r == 0 && c == 0) {
//                colSpan = GridLayout.spec(GridLayout.UNDEFINED, 2);
//                rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 2);
//            }
//            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colSpan);
//            gridParam.setMarginStart(8);
//            gridLayout.addView(oImageView, gridParam);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // dynamically create image views
        GridLayout gridLayout = findViewById(R.id.gridlayout_insert_point);
        int total = postDataHolder.getImageCount();
        int column = 4;
        int row = 3;
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row);
        for(int i=0, c=0, r=0; i<postDataHolder.getSavedImage().size() && i <= total; i++, c++) {
            if(c == column) {
                c = 0;
                r++;
            }
            ImageView oImageView = new ImageView(this);
            oImageView.setId(r * column + c);
            String curBitmapPath = postDataHolder.getSavedImage().get(r * column + c);
            oImageView.setImageBitmap(postDataHolder.getBitmapMap().get(curBitmapPath));
            oImageView.setLayoutParams(new ViewGroup.LayoutParams(oImageView.getDrawable().getIntrinsicWidth(),
                    oImageView.getDrawable().getIntrinsicHeight()));
            GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
            GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
            if(r == 0 && c == 0) {
                colSpan = GridLayout.spec(GridLayout.UNDEFINED, 2);
                rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 2);
            }
            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colSpan);
            gridParam.setMarginStart(8);
            gridLayout.addView(oImageView, gridParam);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                // use camera succeeded
                if(resultCode == RESULT_OK) {
                    ImageView requester = findViewById(R.id.add_image);
                    shrinkImage(requester);
                }
        }
    }

    // The method that displays the popup.
    private void showPopup(final Activity context, final int request_code) {
        // Inflate the popup_layout.xml
        LinearLayout viewGroup = context.findViewById(R.id.popup_layout);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.get_image_popup, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);

        // use photo album logic goes here
        TextView useAlbum = layout.findViewById(R.id.choose_from_album);
        useAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // use camera logic goes here
        TextView useCamera = layout.findViewById(R.id.use_camera);
        useCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_REQUEST_CODE);
                }else {
                    handleUseCamera();
                }

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                "edu.virginia.cs.vmarketplace.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, request_code);
                        // add image to gallery
                        galleryAddPic();

                        popup.dismiss();
                    }
                }
            }
        });

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);

        // Getting a reference to Close button, and close the popup when clicked.
        TextView close = layout.findViewById(R.id.close_popup);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }

    private void handleUseCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        "edu.virginia.cs.vmarketplace.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                // add image to gallery
                galleryAddPic();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        PostDataHolder postDataHolder = PostDataHolder.getInstance();
        postDataHolder.setCurImageLocation(mCurrentPhotoPath);
        postDataHolder.getSavedImage().add(mCurrentPhotoPath);
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void shrinkImage(ImageView view) {
        // Get the dimensions of the View
        int targetW = view.getDrawable().getIntrinsicWidth();
        int targetH = view.getDrawable().getIntrinsicHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        PostDataHolder postDataHolder = PostDataHolder.getInstance();
        BitmapFactory.decodeFile(postDataHolder.getCurImageLocation(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(postDataHolder.getCurImageLocation(), bmOptions);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        postDataHolder.getBitmapMap().put(postDataHolder.getCurImageLocation(), rotatedBitmap);
        System.out.println("*********");
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleUseCamera();
            }
        }
    }
}