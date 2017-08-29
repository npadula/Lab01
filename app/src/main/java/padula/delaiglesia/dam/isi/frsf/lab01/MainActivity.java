package padula.delaiglesia.dam.isi.frsf.lab01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

Button boton;
    EditText txtImporte;
    SeekBar seekBar;
    TextView txtCantDiasSeekbar;
    TextView resultMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button) findViewById(R.id.button);
        txtImporte = (EditText) findViewById(R.id.txtImporte);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double importe = Double.parseDouble(txtImporte.getText().toString());
                int dias = Integer.parseInt(txtCantDiasSeekbar.getText().toString());

                double resultado = calcularPlazoFijo(importe,dias);

                resultMessage = (TextView) findViewById(R.id.txtResultMessage);


                resultMessage.setText(String.valueOf(resultado));
                resultMessage.setVisibility(View.VISIBLE);


            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            txtCantDiasSeekbar = (TextView) findViewById(R.id.txtViewCantDiasSeekBar);

            txtCantDiasSeekbar.setText(String.valueOf(i));
        }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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
            tasa = Double.parseDouble(getString(R.string.hastar99000Menor30));
        }
        if(((importe > 5000) &&  (importe <=99999)) && dias >= 30){
            tasa = Double.parseDouble(getString(R.string.hasta5000Mayor30));
        }
        if(importe > 99999 && dias < 30){
            tasa = Double.parseDouble(getString(R.string.masDe99000Menor30));
        }
        if(importe > 99999 && dias >= 30){
            tasa = Double.parseDouble(getString(R.string.masDe99000Mayor30));
        }

        //resultMessage.setText(String.valueOf(tasa));
        double base = 1 + (tasa/100);
        double exp = (dias/360) - 1;
        resultado = importe * (Math.pow(base,exp));
        return  resultado;
    }
}
