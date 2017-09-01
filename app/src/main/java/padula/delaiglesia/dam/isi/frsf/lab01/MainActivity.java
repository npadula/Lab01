package padula.delaiglesia.dam.isi.frsf.lab01;

import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

Button boton;
    EditText editTextImporte;
    SeekBar seekBar;
    TextView txtCantDiasSeekbar;
    TextView resultMessage;
    TextView txtViewDinero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button) findViewById(R.id.button);
        editTextImporte = (EditText) findViewById(R.id.txtImporte);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        txtViewDinero = (TextView)  findViewById(R.id.txtViewDinero);

         final DecimalFormat df2 = new DecimalFormat(".##");

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                double resultado = calcularResultado();

                resultMessage = (TextView) findViewById(R.id.txtResultMessage);
                String msg = "Plazo fijo realizado, recibirá $" + df2.format(resultado) + " al final del vencimiento.";
                String.valueOf(resultado);
                resultMessage.setText(msg);
                resultMessage.setVisibility(View.VISIBLE);

                if(resultado >= 0){
                    resultMessage.setTextColor(getColor(R.color.mensajeExito));
                    resultMessage.setText("$" +df2.format(resultado) );
                }
                else {
                    resultMessage.setTextColor(getColor(R.color.mensajeError));
                    resultMessage.setText("error" );
                }


            }
        });

        editTextImporte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                double resultado = calcularResultado();

                if(resultado >= 0){
                    txtViewDinero.setTextColor(getColor(R.color.mensajeExito));
                    txtViewDinero.setText("$" +df2.format(resultado) );
                }
                else {
                    txtViewDinero.setTextColor(getColor(R.color.mensajeError));
                    txtViewDinero.setText("error" );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            txtCantDiasSeekbar = (TextView) findViewById(R.id.txtViewCantDiasSeekBar);

            txtCantDiasSeekbar.setText(String.valueOf(i));
                double resultado = calcularResultado();



                if(resultado >= 0){
                    txtViewDinero.setTextColor(getColor(R.color.mensajeExito));
                    txtViewDinero.setText("$" +df2.format(resultado) );
                }
                else {
                    txtViewDinero.setTextColor(getColor(R.color.mensajeError));
                    txtViewDinero.setText("error" );
                }
        }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private double getImporte(){
        try {
        return Double.parseDouble(editTextImporte.getText().toString());
        }
        catch (Exception ex){
            return 0;
        }
    }

    private int getDias(){
        try{
        return Integer.parseInt(txtCantDiasSeekbar.getText().toString());
        }
        catch (Exception ex){
            return 0;
        }
    }

    private  double calcularResultado(){
        double importe = getImporte();
        int dias = getDias();

        double resultado = calcularPlazoFijo(importe,dias);

        return resultado;
    }

    private double calcularPlazoFijo(double importe, int dias) {
        double tasa = 0;
        double resultado;


        if(importe <= 5000 && dias <= 30){
            tasa = Double.parseDouble(getString(R.string.hasta5000Menor30));


        }
        if(importe <= 5000 && dias >= 30){
            tasa = Double.parseDouble(getString(R.string.hasta5000Mayor30));

        }
        if(((importe >= 5000) &&  (importe <=99999)) && dias < 30){
            tasa = Double.parseDouble(getString(R.string.hasta99000Menor30));
        }
        if(((importe > 5000) &&  (importe <=99999)) && dias >= 30){
            tasa = Double.parseDouble(getString(R.string.hasta990000Mayor30));
        }
        if(importe > 99999 && dias < 30){
            tasa = Double.parseDouble(getString(R.string.masDe99000Menor30));
        }
        if(importe > 99999 && dias >= 30){
            tasa = Double.parseDouble(getString(R.string.masDe99000Mayor30));
        }

        //resultMessage.setText(String.valueOf(tasa));
        double base = 1 + (tasa/100);
        double exp = ((double)dias/360);
        double aux = (Math.pow(base,exp));
        resultado = importe * (aux -1);
        return  resultado;

    }
}
