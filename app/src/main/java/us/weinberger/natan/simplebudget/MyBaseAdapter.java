package us.weinberger.natan.simplebudget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.TextView;

public class MyBaseAdapter extends BaseAdapter {
	ArrayList<Transaction> list = new ArrayList<Transaction>();
	LayoutInflater inflater;
	Context context;
	
	public MyBaseAdapter(Context context, ArrayList<Transaction> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(this.context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewHolder mViewHolder;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_row_layout, null);
			mViewHolder = new MyViewHolder();
			convertView.setTag(mViewHolder);
		}
		else {
			mViewHolder = (MyViewHolder) convertView.getTag();
		}
		
		mViewHolder.amount = detail(convertView, R.id.amount, list.get(position).getAmount());
		mViewHolder.date = detail(convertView, R.id.date, list.get(position).getDate());
		mViewHolder.location = detail(convertView, R.id.location, list.get(position).getLocation());
		mViewHolder.icon = detail(convertView, R.id.typeTransaction, list.get(position).getIcon());

//        Typeface roboFont = Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf");
//        mViewHolder.amount.setTypeface(roboFont);
//        mViewHolder.date.setTypeface(roboFont);
//        mViewHolder.location.setTypeface(roboFont);

		return convertView;
	}

	
	private TextView detail(View view, int resId, String desc) {
		TextView textview = (TextView) view.findViewById(resId);
		textview.setText(desc);
		return textview;
	}
	
	private ImageView detail(View view, int resId, int icon) {
		ImageView imageView = (ImageView) view.findViewById(resId);
		imageView.setImageResource(icon);
		
		return imageView;
	}

	private class MyViewHolder {
		TextView amount, date, location;
		ImageView icon;
	}
}

