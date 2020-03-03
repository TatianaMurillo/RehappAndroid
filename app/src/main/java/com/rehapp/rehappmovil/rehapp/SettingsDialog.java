package com.rehapp.rehappmovil.rehapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Utils.DataValidation;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

import java.util.Arrays;
import java.util.List;

public class SettingsDialog extends AppCompatDialogFragment {

    FragmentManager manager;
    private Context mContext;

    ImageView btnEditIp;
    EditText etsetIp;
    LinearLayout lyEditIp;
    TextView tvCurrentIP;
    SharedPreferences sharedpreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        manager = this.getFragmentManager();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.configure_current_ip,null);
        sharedpreferences =mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        btnEditIp=view.findViewById(R.id.btnEditIp);
        etsetIp=view.findViewById(R.id.etsetIp);
        lyEditIp=view.findViewById(R.id.lyEditIp);
        tvCurrentIP=view.findViewById(R.id.tvCurrentIP);

        btnEditIp.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             lyEditIp.setVisibility(View.VISIBLE);
                                         }
                                     }
        );

        showIp();

        builder
                .setView(view)
                .setTitle(R.string.settings)
                .setNegativeButton(R.string.CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(R.string.SaveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveIp();
                    }
                });

        return builder.create();
    }

    private void saveIp() {
        String ip=etsetIp.getText().toString();
        List<Object> dataValidation=ValidateInputs.validate().ValidateDataObject(Arrays.asList(new DataValidation(ip,"Ip").noEmptyValue().textMaxLength(50)));
        boolean checked=Boolean.parseBoolean(dataValidation.get(0).toString());
        String msg = dataValidation.get(1).toString();

        if(checked){
            storeStringSharepreferences(PreferencesData.CurrentIp, ip);
            showIp();
            Toast.makeText(mContext, PreferencesData.setCurrentIpSuccesstMsg, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }
    }

    private void showIp(){
        String ip=sharedpreferences.getString(PreferencesData.CurrentIp,"");
        tvCurrentIP.setText(ip);
    }

    private  void storeStringSharepreferences(String key, String value){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
}
