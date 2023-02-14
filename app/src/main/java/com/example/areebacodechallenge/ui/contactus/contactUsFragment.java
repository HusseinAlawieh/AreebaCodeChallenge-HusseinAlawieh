package com.example.areebacodechallenge.ui.contactus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.areebacodechallenge.R;
import com.example.areebacodechallenge.databinding.FragmentContactusBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class contactUsFragment extends Fragment {

    private FragmentContactusBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContactusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText name = root.findViewById(R.id.name);
        EditText email = root.findViewById(R.id.email);
        EditText phone = root.findViewById(R.id.phone);
        EditText message = root.findViewById(R.id.message);
        Button submitButton=root.findViewById(R.id.submitButton);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateName(name)&&
                validateEmail(email)&&
                validatePhoneNumber(phone)&&
                validateMessage(message)){
                    addDataToFirestore(name.getText().toString(),email.getText().toString(),phone.getText().toString(),message.getText().toString());
                }

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean validateName(EditText name) {
        String val = name.getText().toString().trim();
        if (val.isEmpty()) {
            name.setError("Field can not be empty");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    private boolean validateEmail(EditText email) {
        String val = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber(EditText phoneNumber) {
        String phone;
        if (!phoneNumber.getText().toString().isEmpty()) {
            // storing the entered number in to string
             phone= phoneNumber.getText().toString().trim();
        } else {
            phoneNumber.setError("Enter valid phone number");
            return false;
        }
        if(android.util.Patterns.PHONE.matcher(phone).matches())
        // using android available method of checking phone
        {
            phoneNumber.setError(null);
            return true;        }
        else
        {
            phoneNumber.setError("Enter valid phone number");
            return false;
        }
    }

    private boolean validateMessage(EditText message) {
        String val = message.getText().toString();
        if (val.isEmpty()) {
            message.setError("Field can not be empty");
            return false;
        } else {
            message.setError(null);
            return true;
        }
    }

    private void addDataToFirestore(String name,String email,String phone,String messageS){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new message with
        Map<String, Object> message = new HashMap<>();
        message.put("name", name);
        message.put("email", email);
        message.put("phone", phone);
        message.put("message",messageS);

// Add a new document with a generated ID
        db.collection("messages")
                .add(message)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Message sent", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error sending message , check your internet connection", Toast.LENGTH_SHORT).show();

                    }
                });
    }



}