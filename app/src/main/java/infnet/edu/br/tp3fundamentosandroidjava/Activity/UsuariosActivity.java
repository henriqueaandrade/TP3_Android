package infnet.edu.br.tp3fundamentosandroidjava.Activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import infnet.edu.br.tp3fundamentosandroidjava.R;
import infnet.edu.br.tp3fundamentosandroidjava.adapter.ItemAdapter;
import infnet.edu.br.tp3fundamentosandroidjava.config.ConfiguracaoFirebase;
import infnet.edu.br.tp3fundamentosandroidjava.model.Usuario;

public class UsuariosActivity extends AppCompatActivity{

    private ListView listView;
    private ItemAdapter itemAdapter;
    private ArrayList<Usuario> usuarios;

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    @Override
    protected void onStop() {
        databaseReference.removeEventListener(valueEventListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        databaseReference.addValueEventListener(valueEventListener);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        listView = (ListView) findViewById(R.id.lst_usuarios);

        usuarios = new ArrayList<>();
        itemAdapter = new ItemAdapter(UsuariosActivity.this, usuarios );
        listView.setAdapter(itemAdapter);

        databaseReference = ConfiguracaoFirebase.getFirebase().child("usuarios");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarios.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Usuario usuario = dados.getValue(Usuario.class);
                    usuarios.add(usuario);
                }
                itemAdapter.notifyDataSetChanged();

                // Verifica se há usuarios cadastrados
                if (!dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Nenhum usuário foi cadastrado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }; // Fim valueEventListener

        databaseReference.addValueEventListener(valueEventListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(UsuariosActivity.this, UsuariosDetalhesActivity.class);
                Usuario usuarioSelecionado = usuarios.get(i);
                intent.putExtra("detalhes", usuarioSelecionado);

                startActivity(intent);
            }
        }); // Fim listView SetOnItemClickListener

    }

}
