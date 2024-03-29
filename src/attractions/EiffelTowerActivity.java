package attractions;

import httptools.HttpUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zbar.lib.CaptureActivity;
import com.zbar.lib.R;

public class EiffelTowerActivity extends Activity {
	private TextView text_info, text_address, text_open, text_ticket, text_length;
	private ImageButton scanner, myroute;
	public LocationManager locationManager;
	private Location location;
    private String baseURL = "http://maps.googleapis.com/maps/api/directions/json?origin=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eiffel);

		scanner = (ImageButton) findViewById(R.id.btn_scanner);
	    myroute = (ImageButton) findViewById(R.id.myroute);
		text_info = (TextView) findViewById(R.id.eiffel_info);
		text_address = (TextView) findViewById(R.id.eiffel_address);
		text_open = (TextView) findViewById(R.id.eiffel_opening);
		text_ticket = (TextView) findViewById(R.id.eiffel_ticket);
		text_length = (TextView) findViewById(R.id.text_length);

		String info = text_info.getText().toString();
		SpannableString sp = new SpannableString(info);
		int index = info.lastIndexOf("W");
		sp.setSpan(new URLSpan("http://en.wikipedia.org/wiki/Eiffel_Tower"),
				index, index + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		text_info.setText(sp);

		info = text_open.getText().toString();
		sp = new SpannableString(info);
		index = info.lastIndexOf("M");
		sp.setSpan(
				new URLSpan(
						"http://www.toureiffel.paris/en/preparing-your-visit/opening-times.html"),
				index, index + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		index = info.indexOf("O");
		sp.setSpan(new StyleSpan(Typeface.BOLD), index, index + 13, 0);
		text_open.setText(sp);

		info = text_ticket.getText().toString();
		sp = new SpannableString(info);
		index = info.lastIndexOf("M");
		sp.setSpan(
				new URLSpan(
						"http://www.toureiffel.paris/en/preparing-your-visit/rates-and-visiting-conditions.html"),
				index, index + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		index = info.indexOf("T");
		sp.setSpan(new StyleSpan(Typeface.BOLD), index, index + 6, 0);
		text_ticket.setText(sp);

		info = text_address.getText().toString();
		sp = new SpannableString(info);
		index = info.indexOf("A");
		sp.setSpan(new StyleSpan(Typeface.BOLD), index, index + 7, 0);
		text_address.setText(sp);

		
		scanner.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EiffelTowerActivity.this, CaptureActivity.class);  
	        	startActivity(intent);
			}
		});

		myroute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EiffelTowerActivity.this, MyRoute.class);  
	        	startActivity(intent);
			}
		});
		
	    baseURL += "Massy%20Palaiseau&destination=Eiffel%20Tower&sensor=false&mode=transit";
	    
	    String jsonString = HttpUtils.getJsonContent(baseURL);
	    text_length.setText(HttpUtils.getTransitTime(jsonString));
	    Toast.makeText(getApplicationContext(), "length"+HttpUtils.getTransitTime(jsonString),Toast.LENGTH_SHORT).show();;
	    HttpUtils.getTransitionDetail(jsonString);

	}
}
