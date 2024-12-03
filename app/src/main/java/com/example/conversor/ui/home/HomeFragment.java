package com.example.conversor.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.conversor.R;
import com.example.conversor.utils.CurrencyConverter;

public class HomeFragment extends Fragment {

    private Spinner spinnerBaseCurrency, spinnerTargetCurrency;
    private EditText editAmount;
    private TextView textResult;
    private Button btnConvert;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Vincular os componentes do layout
        spinnerBaseCurrency = root.findViewById(R.id.spinner_base_currency);
        spinnerTargetCurrency = root.findViewById(R.id.spinner_target_currency);
        editAmount = root.findViewById(R.id.edit_amount);
        textResult = root.findViewById(R.id.text_result);
        btnConvert = root.findViewById(R.id.btn_convert);

        // Configurar os Spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.currency_list,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBaseCurrency.setAdapter(adapter);
        spinnerTargetCurrency.setAdapter(adapter);

        // Configurar o botão de conversão
        btnConvert.setOnClickListener(v -> convertCurrency());

        return root;
    }

    private void convertCurrency() {
        String baseCurrency = spinnerBaseCurrency.getSelectedItem().toString();
        String targetCurrency = spinnerTargetCurrency.getSelectedItem().toString();
        String amountString = editAmount.getText().toString().trim();

        // Verificar se uma moeda foi selecionada
        if ("Selecione sua moeda".equals(baseCurrency) || "Selecione sua moeda".equals(targetCurrency)) {
            Toast.makeText(getContext(), "Por favor, selecione ambas as moedas.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação básica do valor
        if (amountString.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, insira o valor.", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Digite um valor numérico válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Conversão de moeda
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