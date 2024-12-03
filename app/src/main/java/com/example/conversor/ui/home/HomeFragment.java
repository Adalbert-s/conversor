package com.example.conversor.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.conversor.R;
import com.example.conversor.utils.CurrencyConverter;

public class HomeFragment extends Fragment {

    private EditText editBaseCurrency, editTargetCurrency, editAmount;
    private TextView textResult;
    private Button btnConvert;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Vincular os componentes do layout
        editBaseCurrency = root.findViewById(R.id.edit_base_currency);
        editTargetCurrency = root.findViewById(R.id.edit_target_currency);
        editAmount = root.findViewById(R.id.edit_amount);
        textResult = root.findViewById(R.id.text_result);
        btnConvert = root.findViewById(R.id.btn_convert);

        // Configurar o botão de conversão
        btnConvert.setOnClickListener(v -> convertCurrency());

        return root;
    }

    private void convertCurrency() {
        String baseCurrency = editBaseCurrency.getText().toString().trim();
        String targetCurrency = editTargetCurrency.getText().toString().trim();
        String amountString = editAmount.getText().toString().trim();

        // Validação básica dos campos
        if (baseCurrency.isEmpty() || targetCurrency.isEmpty() || amountString.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Digite um valor numérico válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Instanciar o conversor e realizar a conversão
        CurrencyConverter converter = new CurrencyConverter();
        converter.convertCurrency(baseCurrency, targetCurrency, amount, new CurrencyConverter.CurrencyConversionListener() {
            @Override
            public void onConversionSuccess(double result) {
                textResult.setText(String.format("Resultado: %.2f %s", result, targetCurrency));
            }

            @Override
            public void onConversionFailure(String errorMessage) {
                Toast.makeText(getContext(), "Erro: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}