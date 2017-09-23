package infnet.edu.br.tp3fundamentosandroidjava.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import infnet.edu.br.tp3fundamentosandroidjava.R;
import infnet.edu.br.tp3fundamentosandroidjava.model.Usuario;

/**
 * Created by joaoluisdomingosxavier on 21/09/17.
 */

public class ItemAdapter extends ArrayAdapter {
    private ArrayList<Usuario> usuarios;
    private Context context;

    public ItemAdapter(@NonNull Context c, @NonNull ArrayList<Usuario> objects) {
        super(c, 0, objects);
        this.context = c;
        this.usuarios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_nome_lista, parent, false);
        }

        Usuario usuario = usuarios.get(position);
        if (usuario != null){
            TextView txtNome = view.findViewById(R.id.txtNome);

            if (txtNome != null) {
                txtNome.setText(usuario.getNome());
            }
        }
        return view;
        // return super.getView(position, convertView, parent);
    }


}
