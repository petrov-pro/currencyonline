package currency.online;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CurrencyActivity extends Activity {

	CurrencyModel model;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//startService(new Intent(this, CurrencyService.class));
		model = (CurrencyModel) getLastNonConfigurationInstance();
		if (model == null) {
			model = new CurrencyModel(this);
			model.preloadData();
		} else {
			model.reload(this);
		}

	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return model;
	}

	public void screenCalculate(View v) {
		MyParcelable mp = new MyParcelable(model.currency, model.getCurrentCurrency());
		Intent intent = new Intent(this, CurrencyCalculator.class);
		intent.putExtra(MyParcelable.class.getCanonicalName(), mp);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		model.parseDataFromIntentAndRecalc(data);
		
	}
}
