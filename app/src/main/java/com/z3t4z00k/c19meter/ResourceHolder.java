package com.z3t4z00k.c19meter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

class ResourceHolder extends RecyclerView.ViewHolder {
    private TextView city, orga, desc;
    private Button webs, phon;

    ResourceHolder(@NonNull View itemView) {
        super(itemView);
        city = itemView.findViewById(R.id.loc);
        orga = itemView.findViewById(R.id.org);
        desc = itemView.findViewById(R.id.des);
        webs = itemView.findViewById(R.id.web);
        phon = itemView.findViewById(R.id.pho);
    }

    void bind(final ResourceModel resourceModel, final Context context) {
        city.setText(resourceModel.getCit());
        orga.setText(resourceModel.getOrg());
        desc.setText(resourceModel.getDes());
        webs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(resourceModel.getWeb())));
            }
        });
        phon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Please grant CALL_PHONE permission from the settings, to make calls.", Toast.LENGTH_LONG).show();
                    return;
                }
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+resourceModel.getPho())));
            }
        });
    }
}
