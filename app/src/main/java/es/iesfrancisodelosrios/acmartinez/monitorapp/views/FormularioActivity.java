package es.iesfrancisodelosrios.acmartinez.monitorapp.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.iesfrancisodelosrios.acmartinez.monitorapp.R;
import es.iesfrancisodelosrios.acmartinez.monitorapp.interfaces.FormularioInterface;
import es.iesfrancisodelosrios.acmartinez.monitorapp.presenter.FormularioPresenter;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;

public class FormularioActivity extends AppCompatActivity implements FormularioInterface.View {

    private FormularioPresenter presenter;
    private Context myContext;
    private Bitmap bmp;
    final private int CODE_READ_EXTERNAL_STORAGE_PERMISION=1234;
    private ImageButton img;
    private static final int REQUEST_CAPTURE_IMAGE = 200;
    private static final int REQUEST_SELECT_IMAGE = 201;
    final String pathFotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/demoAndroidImages/";
    private Uri uri;
    private String TAG = "MonitorApp";
    private Button acept;
    private Long id;

    private Person p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        myContext=this;
        setContentView(R.layout.activity_formulario);
        presenter=new FormularioPresenter(this, myContext);
        Button cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View view) {
                presenter.cancel();
            }
        });
        Button add=findViewById(R.id.acept);
        cancel.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View view) {
                presenter.add();
            }
        });

        img= (ImageButton) findViewById(R.id.imageAvatar);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickAddImage(myContext);
            }
        });

        ArrayList<String> secciones=presenter.getSpinner();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, secciones);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.seccion);
        spinner.setAdapter(adapter);

        Button exit=(Button) findViewById(R.id.cancel);
        exit.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.toExit();
            }
        });

        Button addSpinner = (Button) findViewById(R.id.addSpinner);
        addSpinner.setOnClickListener(new android.view.View.OnClickListener(){

            @Override
            public void onClick(View v) {
                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
                alertDialog.setView(viewAlertDialog);
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

                alertDialog
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        adapter.add(dialogInput.getText().toString());
                                        spinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });
        final EditText date = (EditText) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.date:
                        showDatePickerDialog(date);
                        break;
                }
            }
        });

        TextInputEditText email = (TextInputEditText) findViewById(R.id.email);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    Log.d("AppCRUD", et.getText().toString());
                    Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
                    Matcher matcher = pattern.matcher(et.getText().toString());
                    if (matcher.find()) {
                        TextInputLayout nombreInputLayout = (TextInputLayout) findViewById(R.id.nombreInputLayout);
                        nombreInputLayout.setError("");
                    } else {
                        TextInputLayout nombreInputLayout = (TextInputLayout) findViewById(R.id.nombreInputLayout);
                        nombreInputLayout.setError("E-mail no valido");
                    }
                }
            }
        });

        acept=findViewById(R.id.acept);
        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.add();
            }
        });
        Log.d(TAG,String.valueOf(getIntent().getLongExtra("EXTRA_PERSON_ID",-1)));
        if(getIntent().getLongExtra("EXTRA_PERSON_ID",-1)!=-1){
             EditText EbirthDate= ((EditText) findViewById(R.id.date));
             CheckBox Cmtl=((CheckBox) findViewById(R.id.MTL));
             Spinner Ssection= ((Spinner) findViewById(R.id.seccion));
             EditText ElastName= ((EditText) findViewById(R.id.lastName));
             EditText Eemail= ((EditText) findViewById(R.id.email));
             EditText Ename= ((EditText) findViewById(R.id.name));
             EditText Enif= (EditText) findViewById(R.id.dni);
             EditText Ephone= (EditText) findViewById(R.id.telefono);
             ImageButton avatar= (ImageButton) findViewById(R.id.imageAvatar);

            id=getIntent().getLongExtra("EXTRA_PERSON_ID",-1);
            p=presenter.selectById(id);
            Ename.setText(p.getName());
            ElastName.setText(p.getLastName());
            Eemail.setText(p.getEmail());
            EbirthDate.setText(p.getBirthDate());
            Cmtl.setChecked(p.getMtl());
            Ephone.setText(p.getPhone());
            Enif.setText(p.getNif());
            for(int i=0;i<secciones.size();i++){
                if(secciones.get(i).equals(p.getSection())){
                    Ssection.setSelection(i);
                }
            }
            if(p.getPhoto()!=null) {
                byte[] decodedString = Base64.decode(p.getPhoto(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(
                        decodedString,
                        0,
                        decodedString.length);
                avatar.setImageBitmap(decodedByte);
            }else{
                String imgDefault="/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQECAgICAgICAgICAgMDAwMDAwMDAwMBAQEBAQEBAQEBAQICAQICAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDA//CABEIASwBLAMBEQACEQEDEQH/xAAeAAEAAgICAwEAAAAAAAAAAAAACAkHCgIGAwQFAf/aAAgBAQAAAACZAAAAAAAAAPHlfuXhxr0PzgAAAB9CxGxeZ3ahiaDVZ8OuYAAAOFjF3+XABD+h+N4AAA+pfrZOAB86iyrDmAAB9LZCm4AAKUqeuQAAcL/7NAAAa8NdoAAT92MwAAOn6o2OAADy7WudgAAFVdEYABPXY7AAAPiaivVAAGwNZUAAANe+t8AD19tbMQAAAqxodAA+nuNcwAABCzWiAAyJt2AAABFrVz/QAdx2/wAAAAIl6wYAHj3GftgAACvvXTAA47PctwAABTBTcAAuTudAAAGr5FIABmTbE9gAABHjVU5AAPzYAsxAAAa89c4AAyNtWd+AABCLWq5AABNXZO90AAYc1d8dAAAWB7C31wAGFtabCIAABKXYVzsACBdAHQgAAA+raxbTloBE6nOBXIAAABzmBMeQHfPTxnGuD+AfIAAAB4fL+gA48PKAABwmVZ/PWDVUcQuYD6dglufeq56tsOAACSt7svgxLCOMeF+rex3rOksJsdlHqVgUndKABwtdvI94AAAYq1z4qADhc5cuAAAB8DWyh2AWS7BYAAAB0zVrwsBlfax7QAAAAIfayAPzYDsuAAAABryV1Bl7bJ9kAAAAEbtV3mLmblwAAAAGsBE48O1lIQAAAABUbSCcptAAAAAHQ4vv/8QAFAEBAAAAAAAAAAAAAAAAAAAAAP/aAAgBAhAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP/8QAFAEBAAAAAAAAAAAAAAAAAAAAAP/aAAgBAxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP/8QATBAAAQMCAgQKBQcICAcAAAAAAQIDBAUGERIABxMhCBQiKDFAQVFnpRUwMkJhFiAjM2JxgSQ0UFJjcoKSFyZDU4ORk6EQNURGZHN0/9oACAEBAAE/Af0s2rbubFgKkPY4bGMhch7Hu2TKVuY/hpBsS+qmkLp9lXXLQroWihVBtH877LKRoNUetRQxGr648Pi1BQf5XJyVaOap9aLW9er+5sP2cWO//tHlOq0qFs3PSMfSts3HTsMcVTKHU2UDDtLhjbMD8dA62pRSFpK0+0jMM6f3k+0OvQocypTGadTIcupVGQQGIFPjOzJjxJwGSOwlbmXH3jgkdp0tLgxXxWw3JuWXDtCCvKri5CKtW1IUMfzZh1MCIv8AfeWR2p0t3g26sKIELnU2Vc8sBBU/X5a32NonpU3To3FYCQT2KbXh36Uyh0WioDVHpFMpTQTlDdOgRYScB2YR2m8fm1yw7LuVC0V616HU8+OZ2TTYxkb+kplJbTJQfiFDS5eCxZNRDj1tVGq2vKJKkM7U1ilY9iDFnL422jH9SQnDu0vHUTrGs0OyV0tNx0psFRqluB2WptsJzFcqkqSKkwEjpKEvJH62gUFY4HHKopV3pWncpCx0oWk9IO8dZUoJGZRwG7eficAPiSegdp01bcHW5LvEerXSqTatuuZHWoxbCbjqjJ34tx3kKRR47qehx5KniDiGx06WlYlqWNC4jbFGi01KgOMSUpLtQmqGGLk6oPFyXKWojHlKwHYB6vWDqSsnWAHZciJ6FuBQOS4aQhtiYtfKI9IsYcWqreY79sCv9VadNYWqq7tWr5VWYyZtEccDcS5achw0x0r+rZnJVi7SpiujI7yFEchaurwYM6qTodLpcORUanUHhHgwIiNpJlPHflbTuAShIzLWohCEglRA01RcH+l2cItw3YmNWbuADzDGAepFurUNyIKFjLMqLYPKlLG5X1QSN6vWy4kWfGfhTo7EyHKaUzJiymm348hlYwW08y6lTbjah0gjDTXBweX7dRLuiwWH5lBbzyKlbadpInUdvetyVRycz02mtDeqOcXmU70Zk8lKVJWkKSQpKhilQOIIPaD1SFCm1ObDplMiPT6lUZDcOBBjjM/KkunkNI7EjAEqUcEoQCpRAB01Oanafq2p3Hp2xqF41FhAqlTCczUFpWC/RFJzDM3CZV7a9y5CxmVuypT1DX3qOEUTr+smEdj9JMui34je5I3rkV2ksIG5SfalMJHKGLiRiFBQIUApJBSoApI3gg7wQe0EdSUpKEqWo4JSCpRPYB0nTg96pPkpTUXlcUTLdNaijiMV5I2lvUZ8BaGMp+qqlQRguSfaQnBrdgvN1LX/AKp02RVhdNAjhFp16WUyIrScGqBW3yV7BCRyWqZVFYqZHstPYt7gpsdS4PercXrdRrtUj7S27ReYkuNuIzM1OvHB+nwCFcl1iAkCS8N4x2STuUeqXBQqZc9Fqdv1iOJNMq0R2HLZOAJbcG5xpRB2b7DgC219KFpChvGl32tUbJuar2tVCVyKU/lZlZcqajTnhtKfUmx0YS4+GYD2HQpPu9QbaffdZjxWVSJcp5mLEjoBK5EqS6liMwgDfmdecCfx01a2XHsCzaNbbORciMxxiqyUgfltYl/T1GUSACpKn1FKMehpKR2dV4UdjCqW7DvqC0DULXIi1UpHKft2a8ApajjifRU5aXRu3NuO9Q4OFpi5NYzNTkN54FnxDWV5gciqrIK4dHRiN2Zo7Z8fFkdWqVPiVenTqVPaD0KpQ5MGWyeh2NLZWw8j4Zm1nSvUSTbNdrVuTCVSaFU5dMWs/wBsiO5hGkY9vGYhQ5/F6/guW8KZq/k11xspk3TWZcoLPvU+mH0XBA+ztGHl/wCJ1fhQ2+KXrBg1xpBDN0UVtbqsMEekaKsQnsCOlS4Tsf48n1zy9m04sDEobWoAdJISSAPiTpq9oyLfsW0qMhOTiFv0ppxP/kGI05JP8Uhaj1fhX0kSLMt+tj26NcjbB/8AnrMR+MvE93GWWfxw9dBj8cqNMh4Y8dqtLh4d4lz48c/7OaJSEgJSMEpASkDoAG4AfcOr8IaGmZqiuxShiqE3Tai38FRKtBWo/g1m9dZze1vKz2jl5d1W8nl+x/zWKeV8N3WNdre11TX8jFI/q7MVivoBRkWPxxTu+PrrcfEW5bYkq9li5bfcVj3Crw8T9wx6xr2cDeqK/CffoqmP4pMqNHT+OZ31ynVR8JKfbjLRJRh05o60vJw+OKNKbMRUadAqDeGznwosxGHRklMIfTh8MF9X4TVQ4lqqnRgd9XrNDpuGPtJ46me5/k1AJ/D1xAIIPQRgfuOmouu+n9VdpSFO7WTAgGhzD2pkUV1ynZT8SywhX3K6vwta2kmyrZQs59pUrglIHRkabRTYJUPiuQ/h+76/gm3Nh8qrMfc3pcYuWmtnDDI8lun1VKd+JKHmo6/8Tq+u+5hdWs65JTLm0g0lxq24BCwtst0jOiY42Ruyu1R18/cB6/V7dqrFvW37oxPFYMvYVVKd+0os8cVqYw7SyyvbJ+20NGnW32m3mVpdZebQ606ghSHG3EhaFoUNykLScQeq61LzRYdjVy4ApPHkRuI0Zsne/WZ/5NT0AYHMGnV7Vf7NtWgxw5S1OLJKnHFb1uuLJU46s9q3FkqPxPr+ncd4PSD26cGnWD8orWXaFRfz1q0G2moxcVi7NtxZyU58FSipaqcRxVzuCWyfb6rwkdYCbou1u1qc9tKLZ7jqJK0K+jmXI8jZzD3LTSI52A7nVu93UbRuqqWRclLuikcqXTHjtYpVkbqVPewRPpjx6A3LZ9k+46EL93S17lpN4UGm3HRH+MU6qR0vsk7nWV70PxZLeJ2UqI8lTbiPdWk9T15a0EavLZMemvIN2V9D0OhtblKhN5QmXXHkf3NOSsbPHc4+pCd4zYAd6lLUSpS3HFFbji1kqcdcWd63HFkqUTvJOPUtS2tl7VrWVw6mt12zK1IQqrMpCnFUeaQltNdiNJxJRkSEy20jFbYCwCpGCo0mPMjsy4jzUmLJabfjSWHEPMSGHkBxp5l1sqQ404hQKVA4EdRvu+KJq+t6VcNbdOza+ihQWinjlVnuA8Xp8JtR5bzxG8+y2gFasEg6XbdVYva4Z9zV10LnTlBLcdskxaZAaKuKUuCD0RoqVdPS64VLVvV1TUvrvk6vnGrcuJT0yyHnTsXEhT0u1nHVlS3YracVyKK44rM6wMVMnFbQOKkGBPhVSHGqNOlR50CYyiREmRXUPx5DDgxQ6062ShaFDu9ffV+27q8oi61cMkoCipqBT2MrlRqsvLmTEgRypO0cPvKODbad6yBpf+sCvax66a3XFBllgOM0ejsLUuFRobhGZpokJ282RlBkPkAuEYAJQEpHVdWety5dWUnZQT6Vtt97az7bku5Gcyj9LKpEghXoyartGBZdPtpx5QsXWPamsSn8dt2oBx9lI4/SZIEerU1Zw5EyEVFaUEnkuJzNL91R9brQ19W1YQkUql7K5LsSFo9GRnvyKlu4YJXXJreZMfKo/UIzSFYdCRytLmuiv3lV3a7ctRcqVRcBbbxGziQY2bMIVNiAluHESewcpZ3rKlb+sQJ0+lTWKnSp0umVKKc0aoQH1xpbPeEutkFTaveQrFCu0HSxeFLUYQZp+sGmGpsJAR8oaI0hucAMeXUKRihiQejFcdSD+y0ta+rRvSPxm2a9T6qAMXGGXsk6P0YiVAeDcyORj76B6m89der2yA4zPrTdSqqByaLQ8lTqObeMHw0vi0EZhvL7jel/cIa9bxD0CkKNn0JzMhTFOkFdamMqGGWZV0hBjJUknFEYI6fbOgASMAMN5UfipRxUpRO9SlHeSd5PW21LYeRJjuuxpTWBalRnXI0log4gtyGFNvI39ytLe19607dyNpuFNdioP5tckVNRUQMOSJ7SotRAw73VaUjhbPJSlNwWRmX7z9CqySj7xEqLDSh/rH8dInCn1avpTxuPdFOWelL1GElI/jgSpeIH3aN8JDVI4MTcEtno5LtDrIVvGPuwlDdpI4SuqZgEorNSlYdkag1dZO7Hdnit6VDhXWIwk+jKJdNUc7AqHCpzR/xJc7aD+TSt8LG5ZOdFvWpSaUlSSEyKvNkVWQg9ixHiIgMZh3Fah9+ly60dYd3hbdduupLiObl06mqFHpxH6qo9O2K3k/8AtW5olCEDBCUpGOOCQAMT0nd2nrQWlW0y8vYpK3tmC5sUDpceKAdi2P1lYDQEEYggg7wRvBHeD3etx3pTvKnFZG0JBUtxZ6ENoTitxZ7gCdAtCipIUM6DgtHQtB7loPKQfgeqKUlOGY4YkJT3qUrclCAN61qPQBvOlm6h9Yt4hqT6NTbVKdAUKlcYdjOuNqAUFxaQgekHsyTyS4GUnv0tfgvWHSAh64n6jd0sHFSJjhp9Jx7k02AtCnEg/wB687jpS7bt+iQ1U+kUSlU2EtvYuRYUCLHZdaKcpQ8hptIeCk9ObHHt0vjg1WVcqnp9vKXZtXdKnFejWUO0OS6oqUTIoqlIaYK1HeqMtj7jpdmozWVaJcddohuCnN5j6TtrPUEhtAzKXIppSipx8B04NuJ+1pmAWppWKHkbnGXEqbebPc4y4Eutn4ED1NOgz6xKTBo8CdV5q1BKYlLiPz5GY9AUiMhzZ496sBpaHBmvqvFuTcb0WzqerKS27s6nXHEKBJyQ47nEoihh/auqI/U0sXU5Yur/ACSKTTOO1gJwXX6uUzqqSfa4u4pCWYCCfdYQ3u6cdLl1cWNd4Pyhtik1B04njhjJj1BKjjyk1CLsJqSMf19Lr4KEBwLfsi45FPc6U0u4QahCJ6crdSYSioRx3Z0v6Xdq+vOxHct0UKTBjZsrdVY/LqK93ZalHBaaJHuvBpfw6jq81T3drJeDlHjpgUJDhbk3LUUOCmpKPrGqe2nK7VpKejBshtJ9txOmr/UlZOr8My40P0zcCAM9w1dDcialzkk+j2cvFqU3mG4MpC+9avnXHYdm3akpuO2qPVicPp5MNvjicBgMk5sNzG8PsrGlb4K9gzs66LUbgt11SiQhqY3VIaPspj1Np54JHweGlS4JlyNKV6HvGizGx7KapTJtPdP3riPVBA/l0lcGnWvHUQzDt6eke/HruxzfciXDY6fidF8HrW+j/taM50/VXBRFdHac8xs4HRvg7633MP6twWsRj9NcNJGX4K2T7xx/z0g8F/WfKP5W9a1LHaXqpLmED92JTiCfx0pfBIlq312+UN97VFo2JI7hIqEpeH+kdKHwadV1IKXJtPqFxvADFVdqLr0fMPe4jDEKGRj2KQrSl0akUOMIdGpdPpMUYfk9Ohx4bXJGAJRHbbSpXxO/5r7DMplyPJZakMPILbzD7aHmXW1blIcacCkLQodhGGmsLgyW/Wg/U7GdbtirqzOGlrC3LcmrJxKQwnM9R3F7+Uxi1j0taXDbdetKqOUW5aXIpNSbBUlp/BTMpkEp4zT5beMedFJHttk4e8End61SkoSVKISlO8k9AGmqDg7u1xEW59YUd+JSF5Hqbay87EupN7lIlV0jK7EhOe7FGDjg3uFI5BjRY0KOzEhx2IkWO2lmPGjNIYjsMoGVDTLLSUttNoTuAAAHV7wsq277pLlGuWnNzopxXHd+qm0+QRgmXTpaPpoklHek4HoUCnEaa0dUlf1YzUrkFdVtiY/sqZcTbWQJcXiW6fWGkciFUMByFfVSMORgrFA9UpSUpKlEJSkFSlHcABvJJ7gNNQmo5OWHft7QcXlbOXbFAmN7oqDgtmt1WOscqa4MFRmVfUJ5ahtCMnWapS6dW6dMpNWhx6hTZ7C40yFKbDrEhlftIWhX+YPSlQBG8aa4dUk7VjVUvxdvNs+qvlFHqLmLj1PkEFfoSquYfnCUgmO6fzhtJx+kScfU8HvVOm76mLyuCMHLXokvLTIj6cWq9W4qwS442rc9S6Q6OUDyHpIy7w2sHrdw2/Sbpo1QoFciom0upx1R5TCtxwOCkOtLHKakMOJC21jlIWkEbxprBsap6uromW3UVLkNJHG6NUyjImrUhxZSxKwHJTKZI2chA9h0buSpJPz7Hs+oX7dNLtanFbJnOF2fNSnMKZSIxSqo1A48nO22oIaB9p9xA0otGp1vUmnUOkRkQ6bS4jMKHHbGCW2WE5U4n33F+0tR3rUSTvPXddWrZvWNaLzERCBctF2tStuQohOMoIHGKY6s/wDS1ZlGzV2JcyL9zTlAqStC2nEKW2604nK6y62otusuoO9DrTiSlQ7CPndG86cGewhb1pLu6ezlrF4BqQxnQQ7Et1kqNMj8pIUgzipUpfeHED3ev8JOxBbF4t3PAZyUe8i68+ED6ONckdIVPT25fSsfCQO9xLvzrAtNy+bzoFrAK4vUJgdqi04AtUWEONVReJ3AuR0bJP23U6MstR2mo7DaGmGG0MstNpCW2mmkhDbaEjclCEjADu6/raswX5YdcoTaEqqKWRUqIsjEt1mnYyIWXeMOMEFhX2HVaJJIxKShXQpCty21jcttY7FtqGB+I+bwTrWGzua95De91xFtUpZw3MxtnNqzid2I2slxlvH9if0DrvtX5I6y6/FZa2VPrKkXJTMEBDeyqqnDOZaA3ZY9VbeHwSofMcXs0LXgTkSVYDpOAxwA7SdNVNtfJHV7atDWjLKZpTMqocnIpVSqONQnlY6SpMqSpP3D9A8LG2w/RLZu5ptO1pFRco05wA5zArCAuPm7MjVRiIA+LvzBjmTh7WdGTv2mYbPD7WfDD46c5Pxe8105yfi95rpzk/F7zXTnJ+L3munOT8XvNdOcn4vea6c5Pxe8105yfi95rpzk/F7zXTnJ+L3munOT8XvNdOcn4vea6c5Pxe8105yfi95rpzk/F7zXTnJ+L3munOT8XvNdOcn4vea6c5Pxe8105yfi95rpzk/F7zXTnJ+L3munOT8XvNdOcn4vea6c5Pxe8105yfi95rpzk/F7zXTnJ+L3munOT8XvNdOcn4vea6c5Pxe8105yfi95rpzk/F7zXS5f6avQ0r5Z/wBIfyezMcd+UPHvRWbbt8V2/GPo83GsmTtz4f8AH//EACcQAQEAAgICAgEEAgMAAAAAAAERACExQUBRMGEgUHGBkRDwobHB/9oACAEBAAE/EP1YtCwJS4CpdGzhKirh7Lew7TCDqvq3sN/IYNHhanD9/fUuMA0og9lfP2kxp8QKHpWv0h5wddOUkbKRDoBhLASIXEQcIneUTIdJB4ESO04hslwSkOoGQg/fl/FkPqDuyNrutcmr0MwtGmjrNi7QC7vEcy+ITsbwpFBjTlDCEEIRB8lOA0PBSNKrgAqAbcufQGuePKEAJMy5vMf4hMzu6mvjuv8AbuJh3ERQORjkm+9e51oXjj+PPjnOa7FkAsiuIMXVrB2OXdQ3zTsQasnRWiyOcBgksV2AtUIobgA/hIdQGkTxBo9DTgLCo9bDHZXTSFyidejQ8HjIBosuhajNRbKPYSsHg6iFE0nhECvigqkFYHW3DFKwwKTdVPC/CRLp2Oke8vHU3XA15EhjwkByJiHcFKDZjR+J0kkEJycccFDDQdUGIwoSmJydeACbIwaAoahprNFTDFBysBNxceKEkIPrHQTVxZ4AT7+fHxrxPDf38WYhjaB1HQhmneVCDcq1AILsifPkZQ2uAyjA3KvtfGOF0iwaAHZtVdT5kXAAUEDsEPvG11tbW13V13Xxy03POAOFHod06+baYqnaDYVllnBgfCRQcAOAIePWDVlnr6U2+YJSOL0H/cOfI3I00Cyq8f8AEMGg8UH+/llSwgpMQJop2a8gORDR+k/2U+/mEtqwpejyg59476T1UirtHPHcAC+phYDX5pCSqg9gifyOEXUQUWVVBg9Hj2JSF9YE3K714fnAsajCZ9CABTd146XOjITT6Eo7Dz88IceC2I6CF/bx98G3h9MwKIj4pOiAxTVBFHSOsJpjb7FYTntH50AgAQFA6RHkTCTkG294GkDvweLZOTS0qEpPEFHR8FjbiGnfQOKMpsYJsUQQgiOiVAqAvhUYSnaj8Ew4kYSA32gNSreJWW3wjTgAhODUqxwUgnaYCBbJKEUfBiaA7NZRVq7CJHLqt3AJfVhZTeI2zIvscYB6ojBAmKM27YNKfXziJY0WkwJFzXN8bzD0blyGd/jZgvsLbZgqNpFiE5NHwwr0VAF3dqfIsZZPwpW7OchcF/1xSk6a632aPkKvvi+3pwAPo2sMhA5siyEQkUS4LjoxgJ2/DGoxZ8LhO1ooSYIQI0HA+bd+E/sPpCePBBmVWnpbqCiqvlvckPsI1gOLjCSGKSoEoiX+a7xS9zW7Q+xSVODtmqGBs2WXE8o11j1x2e2Ibbp3ziwCJeegnU4am8kRv+qUfy5AXI7MQNwaBkjGGcHk/wCDFDJQc2qsmtNKaqAFGryvlLBthGNSiHmD24AAAgIFAUUcJ8qAwMB8KCgNOOjHKEOYfMgvYCeIChjzV6BLCCUYC4Op56FfexDeu8bQ6zQJSJ6INJLUh9RqHSQm/btXCtBgJNqcIAKvm2SSzCRfWCO0XFXIoR6iclNrHwon4vNLPKQrv25fUSwoFWgK6dvZk7Y0cBFYbS1OzKf+Y4RXRJJN3mxYdtsukBoT9tsmyrEIumuMAsbwRBERBEaI7ETSJ4C4cnLNGuKc4I1hUWZ8qWRayJnM/JOzIrlBoiTSh6JFWDRLTiGgBPvePRsqQ8h69UnvXGXrVLCGFsA3oxsNhFKPod0avsMEbHbVTIFDlgJ3dYSKIo0ZQsG5I+8IN6IdAoe5q/dDGg+pSbClHEzTcOXZEPQsAXQqtd/icykJvBkhQjkyUrH7l5QoIoENhPMevshvSl1oXyAPCtAdr7ug5VhvJ8BesrQRFrtTiQw8+oSaA4EDx4H5lYK0JgkiWSwq8wiLqhgZYovjSLyhB6s0ArghRbbqD4QKiEF8qadxdcCJRAUYQCUPf8xvqmuAKH4h/wAY0QYJegQFiXvf7Hry2eiSs2zz3xZAOKGPozvFvcRb8CnDJNcJkGmBxFcLjYwFYgVDSaai+ZQDEGUMaCsgH4DEDNbc9AY9uHj8lAoACqsANqroAwQM8SgwrQFHWz59SN8kWAwG2XQ/JVZxfYsgt6htMJgmxLlAMGgAee9AqXBSmffUm8F7eHUK52yN4Q6/GJDfxoPVoUdpe5+gGkDoHwWsYJBhx+AKhjdvA21kDtcig1ZVFQUgdxNBD9AoiHSiqKFE8L71/n+pzPs3XtPTPvt/rrX9foMkkkkkkkkkkkkkkkn+4VM//Ejf+f/EABQRAQAAAAAAAAAAAAAAAAAAAKD/2gAIAQIBAT8ANJ//xAAUEQEAAAAAAAAAAAAAAAAAAACg/9oACAEDAQE/ADSf/9k=";
                byte[] decodedString = Base64.decode(imgDefault, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(
                        decodedString,
                        0,
                        decodedString.length);
                avatar.setImageBitmap(decodedByte);
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void exit(){
        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
        View viewAlertDialog = layoutActivity.inflate(R.layout.alert_exit, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                myContext);
        alertDialog
                .setMessage("Seguro que desea salir?, se perderan los datos")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        FormularioActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
            .create()
            .show();

    }

    @Override
    public void VOID() {

    }

    @Override
    public void lazarList() {
        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
        View viewAlertDialog = layoutActivity.inflate(R.layout.alert_exit, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                myContext);
        alertDialog
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        FormularioActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();

    }

    @Override
    public void add() {
        EditText EbirthDate= ((EditText) findViewById(R.id.date));
        CheckBox Cmtl=((CheckBox) findViewById(R.id.MTL));
        Spinner Ssection= ((Spinner) findViewById(R.id.seccion));
        EditText ElastName= ((EditText) findViewById(R.id.lastName));
        EditText Eemail= ((EditText) findViewById(R.id.email));
        EditText Ename= ((EditText) findViewById(R.id.name));
        EditText Ephone= (EditText) findViewById(R.id.telefono);
        EditText Enif= (EditText) findViewById(R.id.dni);
        String email= Eemail.getText().toString();
        String name= Ename.getText().toString();
        String photo=null;
        //photo="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";
        if(this.bmp!=null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            photo = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        Boolean mtl=Cmtl.isChecked();
        String section= Ssection.getSelectedItem().toString();
        String lastName= ElastName.getText().toString();
        String birthDate= EbirthDate.getText().toString();
        String nif=Enif.getText().toString();
        String phone=Ephone.getText().toString();
        if(p!=null) {
            if (p.getId() != null) {
                p.setEmail(email);
                p.setName(name);
                p.setSection(section);
                p.setPhoto(photo);
                p.setBirthDate(birthDate);
                p.setMtl(mtl);
                p.setLastName(lastName);
                p.setPhone(phone);
                p.setNif(nif);
                presenter.update(p);
            }
        } else {
            p = new Person(email, photo, name, lastName, mtl, section, birthDate, phone, nif);
            presenter.insert(p);
        }
        FormularioActivity.this.finish();
    }


    public void showDatePickerDialog(final EditText date) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                date.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    @Override
    public void requestPermision(){
        ActivityCompat.requestPermissions(
                FormularioActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
            CODE_READ_EXTERNAL_STORAGE_PERMISION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_READ_EXTERNAL_STORAGE_PERMISION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("AppCRUD", "aceptado");
                    presenter.selectImg();
                } else {
                    Log.d("AppCRUD", "rechazado");
                    //view.showError Snackbar.make(constraintLayoutMainActivity, getResources().getString(R.string.read_permission_accepted), Snackbar.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void selectPicture(){
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.Seleccione_Imagen)),
                REQUEST_SELECT_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case (REQUEST_CAPTURE_IMAGE):
                if(resultCode == Activity.RESULT_OK){
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageButton imageButton = findViewById(R.id.imageAvatar);
                    imageButton.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break;

            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        this.bmp = BitmapFactory.decodeStream(imageStream);

                        // Se carga el Bitmap en el ImageView
                        ImageButton imageButton = findViewById(R.id.imageAvatar);
                        imageButton.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }

    private String getFileCode()
    {
        // Se crea un código a partir de la fecha y hora
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", java.util.Locale.getDefault());
        String date = dateFormat.format(new Date());
        // Se devuelve el código
        return "pic_" + date;
    }




    /*
    public void match(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                TextInputEditText et = (TextInputEditText) v;
                Log.d("AppCRUD", et.getText().toString());
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
                Matcher matcher = pattern.matcher(et.getText().toString());
                if (matcher.find()) {
                    TextInputLayout nombreInputLayout = (TextInputLayout) findViewById(R.id.nombreInputLayout);
                    nombreInputLayout.setError("");
                } else {
                    TextInputLayout nombreInputLayout = (TextInputLayout) findViewById(R.id.nombreInputLayout);
                    nombreInputLayout.setError("E-mail no valido");
                }
            }
        }
    }
    */


}
