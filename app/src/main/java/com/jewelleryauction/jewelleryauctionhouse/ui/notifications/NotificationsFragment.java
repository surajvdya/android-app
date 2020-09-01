package com.jewelleryauction.jewelleryauctionhouse.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jewelleryauction.jewelleryauctionhouse.AboutUsActivity;
import com.jewelleryauction.jewelleryauctionhouse.LoginActivity;
import com.jewelleryauction.jewelleryauctionhouse.R;
import com.jewelleryauction.jewelleryauctionhouse.SearchActivity;

public class NotificationsFragment extends Fragment {

    Button btnLocation,btnlogout,btnAbout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        btnLocation=root.findViewById(R.id.btnlocation);
        btnAbout=root.findViewById(R.id.btnAbout);
        btnlogout=root.findViewById(R.id.btnLogout);
   btnLocation.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Intent intent=new Intent(getContext(),SearchActivity.class);
           startActivity(intent);
       }
   });
   btnlogout.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Intent intent1=new Intent(getContext(), LoginActivity.class);
           startActivity(intent1);
           getActivity().finish();
       }
   });
btnAbout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent2=new Intent(getContext(), AboutUsActivity.class);
        startActivity(intent2);
    }
});
        return root;


    }
}