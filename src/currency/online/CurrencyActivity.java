package currency.online;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;

public class CurrencyActivity extends Activity {

	CurrencyModel model;
	Activity a = this;
	final int REQUEST_CODE_CALC = 1;
	final int REQUEST_CODE_SERVICE = 2;

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
		startActivityForResult(intent, REQUEST_CODE_CALC);
	}

	@Override
	public void onPause() {
		super.onPause();
		CProgressBar.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case REQUEST_CODE_CALC:
					if (data == null) {
						return;
					}
					model.parseDataFromIntentAndRecalc(data);
					break;

				case REQUEST_CODE_SERVICE:

					break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.add(R.string.reload);
		menuItem.setIcon(R.drawable.reload);
		menuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			public boolean onMenuItemClick(MenuItem _menuItem) {
				model = new CurrencyModel(a);
				model.preloadData();
				return true;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}
}
