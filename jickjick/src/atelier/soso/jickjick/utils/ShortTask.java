package atelier.soso.jickjick.utils;

import android.content.Context;
import android.widget.Toast;
import android.util.Log;

/**
 * @author taroguru
 * 짧은 업무를 빠르게 처리하기 위한 유틸클래스.
 */

public class ShortTask {
	public static void Log(String message){
		Log.v("General", message);
	}
	
	public static void showToast(Context context, CharSequence text)
	{	
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	public static void showToast(Context context, int stringId)
	{
		int duration = Toast.LENGTH_SHORT;

		String text = context.getString(stringId);
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
