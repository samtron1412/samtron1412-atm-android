package vn.com.atm;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	public static final int LOGIN = 0;
	public static final int SEARCH = 1;
	public static final int MORE = 2;
	public static final int ABOUT = 6;
	public static final int ATM = 4;
	public static final int USER = 5;
	public static final int EXIT = 3;

	//public static Context context;

	private GridView gridView;
	private ArrayList<ScreenHomeItem> items;
	private ScreenHomeAdapter adapter;
	 @Override
	    protected void onStart() {
	    	// TODO Auto-generated method stub
	    	super.onStart();
	    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.items = new ArrayList<ScreenHomeItem>();
        
        
		// them cac phan tu vao cho mang
		this.items.add(new ScreenHomeItem(BitmapFactory.decodeResource(
				getResources(), R.drawable.login), "Đăng nhập"));
		
		
		
		// them cac phan tu vao cho mang
		this.items.add(new ScreenHomeItem(BitmapFactory.decodeResource(
				getResources(), R.drawable.gtk_find), "Tìm kiếm"));
		
		// them cac phan tu vao cho mang
		this.items.add(new ScreenHomeItem(BitmapFactory.decodeResource(
				getResources(), R.drawable.more), "Thông tin"));

	/*	// them cac phan tu vao cho mang
		this.items.add(new ScreenHomeItem(BitmapFactory.decodeResource(
				getResources(), R.drawable.about_48), "About"));
*/
		
		// them cac phan tu vao cho mang
		this.items.add(new ScreenHomeItem(BitmapFactory.decodeResource(
				getResources(), R.drawable.exit), "Thoát"));
		Bundle bundle = getIntent().getExtras();
        if(bundle!= null)
        {
        	String str = bundle.getString("Status");
        	if(str.equalsIgnoreCase("Success"))
    		{
    			this.items.add(new ScreenHomeItem(BitmapFactory.decodeResource(
    			getResources(), R.drawable.credit_cards), "ATM"));
    			this.items.add(new ScreenHomeItem(BitmapFactory.decodeResource(
    	    	getResources(), R.drawable.user), "Người dùng"));
    		}
        }
		// tao 1 adapter do chuyen cac phan tu cua mang vao gridview
		this.adapter = new ScreenHomeAdapter(this.items);
		
		
		this.gridView = (GridView) this.findViewById(R.id.screen_home_gridview);
		
		// gan adapter cho gridview
		this.gridView.setAdapter(this.adapter);

		// ham nay de lang gnhe khi co su kien click vao 1 item nao do cua gridview
		this.gridView_setOnItemClickListener();
        
    }
   
   @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
 // ham nay thuc hien khi co su kien click vao cac item
 	public void gridView_setOnItemClickListener() {
 		this.gridView.setOnItemClickListener(new OnItemClickListener() {
 			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
 				switch (position) {
 				case LOGIN:
 					startActivity(new Intent(getBaseContext(), LoginActivity.class));
 					break;
 				case SEARCH:
 					startActivity(new Intent(getBaseContext(), SearchActivity.class));
 					break;
 				case MORE:
 					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://vhc.com.vn/")));
 					break;
 				case ATM:
 					startActivity(new Intent(getBaseContext(), AllATMActivity.class));
 					break;
 				case USER:
 					startActivity(new Intent(getBaseContext(), SearchActivity.class));
 					break;
 				case EXIT:
 					finish();
 					break;
 				
 				default:
 					break;
 				}

 			}
 		});
 	}
 	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    /*=============================================================*/
    private class ScreenHomeItem {
		private final Bitmap icon;
		private final String text;

		private ScreenHomeItem(Bitmap icon, String text) {
			this.icon = icon;
			this.text = text;
		}
	}
   /* =================================================================*/

	private class ScreenHomeAdapter extends BaseAdapter {
		private ArrayList<ScreenHomeItem> items;

		private ScreenHomeAdapter(ArrayList<ScreenHomeItem> items) {
			this.items = items;
		}
     // tra ve so luong phan tu trong gridview
		public int getCount() {
			return this.items.size();
		}
     // tra ve doi tuong Item cua gridview
		public Object getItem(int position) {
			return null;
		}
     // lay id cua item ma chung da da lay
		public long getItemId(int position) {
			return 0;
		}
		// lay view cua item, o day no la viewGroup gom 2 view la imageview va Textview 
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;  // lay view cua 1 item trong gridview
			ScreenHomeItem item;      // lay doi tuong item

			if (view == null) {
				// neu view null tuc la ko co gi thi gan giao dien ma chung ta thiet ket home_item cho view do
				view = getLayoutInflater().inflate(R.layout.home_item, null);
			}
			if ((item = this.items.get(position)) == null) {
				// neu doi tuong item ma chung ta lay = null thi tra ve view vua set.
				return view;
			}

			// khai bao cac thanh phan trong view vua thiet lap cho item cua gridview
			ImageView iv = (ImageView) view.findViewById(R.id.screen_home_item_icon);
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			TextView tv = (TextView) view.findViewById(R.id.screen_home_item_text);

			// gan gia tri cho 2 thanh phan
			tv.setText(item.text);
			iv.setImageBitmap(item.icon);

			return view;
		}
	}

 
}
