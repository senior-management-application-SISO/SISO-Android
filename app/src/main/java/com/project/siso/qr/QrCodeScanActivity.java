package com.project.siso.qr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.siso.databinding.ActivityQrCodeScanBinding;

public class QrCodeScanActivity extends AppCompatActivity {

    private ActivityQrCodeScanBinding binding;

    private IntentIntegrator integrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrCodeScanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WebSettings webSettings = binding.wv.getSettings();

        //자바 스크립트 사용을 할 수 있도록 합니다.
        webSettings.setJavaScriptEnabled(true);

        binding.wv.setWebViewClient(new WebViewClient(){
            //페이지 로딩이 끝나면 호출됩니다.
            @Override
            public void onPageFinished(WebView view,String url){
                Toast.makeText(QrCodeScanActivity.this,"로딩 끝", Toast.LENGTH_SHORT).show();
            }
        });
        binding.et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //bt의 onClick을 실행
                    binding.bt.callOnClick();
                    //키보드 숨기기
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });


        integrator = new IntentIntegrator(this);



        //바코드 안의 텍스트
        integrator.setPrompt("QR코드를 사각형 안에 비춰주세요.");

        //바코드 인식 시 소리 여부
        integrator.setBeepEnabled(false);


        integrator.setBarcodeImageEnabled(true);

        integrator.setCaptureActivity(CaptureActivity.class);

        //바코드 스캐너 시작
        integrator.initiateScan();
    }

    public void onClick(View view){
        String address = binding.et.getText().toString();

        if(!address.startsWith("http://")){
            address = "http://" + address;
        }

        binding.wv.loadUrl(address);
    }

    @Override
    public void onBackPressed() {
        if(binding.wv.isActivated()){
            if(binding.wv.canGoBack()){
                binding.wv.goBack();
            }else{
                //스캐너 재시작
                integrator.initiateScan();
            }

        }else{
            super.onBackPressed();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents() == null){

            }else{
                //qr코드를 읽어서 EditText에 입력해줍니다.
                binding.et.setText(result.getContents());

                //Button의 onclick호출
                binding.bt.callOnClick();

                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}