package cadnunsdev.androidutils;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.cadnunsdev.horasdecampo.R;

public class Dialogs {
	public static void Confirm(Activity ctx, String msg, DialogInterface.OnClickListener callbackSucess){
		new AlertDialog.Builder(ctx)
		//.setIcon(R.drawable.ic_launcher)
		.setMessage(msg)
		.setPositiveButton(R.string.string_excluir_confirm, callbackSucess)
		.setNegativeButton(R.string.string_cancelar, null)
		.show();
	}
	
	
}
