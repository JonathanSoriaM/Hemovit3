package com.jonathan.Hemovit3;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import de.hdodenhof.circleimageview.CircleImageView;

public class Custom2DialogClass extends Dialog implements View.OnClickListener {

    CircleImageView check68;
    CircleImageView tacha78;
    TextView caja5;

    RelativeLayout relativeLayout;

    String unidad;
    String tipo;

    public Custom2DialogClass(@NonNull Context context, String s, String unidad) {
        super(context);
        this.unidad = unidad;
        this.tipo = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.confirmar);

        check68 = (CircleImageView) findViewById(R.id.check68);
        caja5 = (TextView) findViewById(R.id.caja5);
        tacha78 = (CircleImageView) findViewById(R.id.tacha78);
        tacha78.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        caja5.setText(tipo + " " + unidad);

        relativeLayout = (RelativeLayout) findViewById(R.id.rel);


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        check68.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        // codigo de ubicacion
        LocationRequest request = new LocationRequest();
        request.setInterval(30000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getContext());


        //String nombre = getNombre();


        int permiso = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permiso == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    Location location = locationResult.getLastLocation();

                    if (location != null && !location.equals(null)) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        Log.v("ubicacion", String.valueOf(latitude));
                        //aqui termina

                        Custom3DialogClass customDialogClass = new Custom3DialogClass(getContext(), unidad, tipo, latitude, longitude,"Jonathan");
                        customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dismiss();
                        //limpiar ubicacion
                        client.removeLocationUpdates(this);
                        customDialogClass.show();
                    } else {
                        return;
                    }
                }

                @Override
                public void onLocationAvailability(LocationAvailability locationAvailability) {
                    super.onLocationAvailability(locationAvailability);
                }

            }, null);


        }
    }


}