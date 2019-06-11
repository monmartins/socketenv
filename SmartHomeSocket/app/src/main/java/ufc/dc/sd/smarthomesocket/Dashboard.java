package ufc.dc.sd.smarthomesocket;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Dashboard extends AppCompatActivity {

    // initiate a Switch
    Switch switchPorta;
    Switch switchLampadas;
    Switch switchArCondicionado;
    Switch switchTV;
    Switch switchSom;
    Switch switchVentilador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switchPorta = (Switch) findViewById(R.id.switchPorta);
        switchLampadas = (Switch) findViewById(R.id.switchLampadas);
        switchArCondicionado = (Switch) findViewById(R.id.switchArCondicionado);
        switchTV = (Switch) findViewById(R.id.switchTV);
        switchSom = (Switch) findViewById(R.id.switchSom);
        switchVentilador = (Switch) findViewById(R.id.switchVentilador);
//        conectarSocket();
        switchPorta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getApplicationContext(),"Porta aberta!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controldoor:1");
                    //Add chamada metodo abrir porta
                }
                else {
                    Toast.makeText(getApplicationContext(),"Porta fechada!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controldoor:0");
                    //Add chamada metodo fechar porta
                }
            }
        });

        switchLampadas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getApplicationContext(),"Lâmpadas ligadas!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controllamp:1");
                    //Add chamada metodo ligar lâmpadas
                }
                else {
                    Toast.makeText(getApplicationContext(),"Lâmpadas desligadas!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controllamp:0");
                    //Add chamada metodo desligar lâmpadas
                }
            }
        });

        switchArCondicionado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getApplicationContext(),"Ar condicionado ligado!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controlair:1");
                    //Add chamada metodo ligar ar condicionado
                }
                else {
                    Toast.makeText(getApplicationContext(),"Ar condicionado desligado!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controlair:0");
                    //Add chamada metodo desligar ar condicionado
                }
            }
        });

        switchTV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getApplicationContext(),"TV ligada!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controltv:1");
                    //Add chamada metodo ligar TV
                }
                else {
                    Toast.makeText(getApplicationContext(),"TV desligada!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controltv:0");
                    //Add chamada metodo desligar TV
                }
            }
        });

        switchSom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getApplicationContext(),"Som ligado!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controlsound:1");
                    //Add chamada metodo ligar som
                }
                else {
                    Toast.makeText(getApplicationContext(),"Som desligado!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controlsound:0");
                    //Add chamada metodo desligar som
                }
            }
        });

        switchVentilador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getApplicationContext(),"Ventilador ligado!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controlfan:1");
                    //Add chamada metodo ligar som
                }
                else {
                    Toast.makeText(getApplicationContext(),"Ventilador desligado!", Toast.LENGTH_LONG).show();
                    new SendSocketTask().execute("controlfan:0");
                    //Add chamada metodo desligar som
                }
            }
        });
    }

}

class SendSocketTask extends AsyncTask<String, Integer, Integer> {
    protected Integer doInBackground(String... sendCommand) {
        try {
            DataOutputStream canalSaida = null;
            // getting localhost ip
            InetAddress ip = InetAddress.getByName("192.168.1.105");

            // establish the connection with server port 5056
            Socket socket = new Socket(ip, 5000);

            canalSaida = new DataOutputStream(socket.getOutputStream());
            canalSaida.writeUTF(sendCommand[0]);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    protected void onProgressUpdate(Integer... progress) {
//        setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
//        showDialog("Downloaded " + result + " bytes");
    }
}