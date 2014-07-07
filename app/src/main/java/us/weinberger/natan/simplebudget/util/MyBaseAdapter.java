package us.weinberger.natan.simplebudget.util;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.TextView;

import us.weinberger.natan.simplebudget.R;
import us.weinberger.natan.simplebudget.Transaction;

public class MyBaseAdapter extends BaseAdapter {
	ArrayList<Transaction> list = new ArrayList<Transaction>();
	LayoutInflater inflater;
	Context context;
    String date;
	
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

        if (position%2 == 0) {
            convertView.setBackgroundResource(R.drawable.list_bg_even);
        }

        else {
            convertView.setBackgroundResource(R.drawable.list_bg_odd);
        }

        date = list.get(position).getMonth() + "/" + list.get(position).getDay() + "/" + list.get(position).getYear();

		mViewHolder.amount = detailTextView(convertView, R.id.amount, list.get(position).getAmount());
		mViewHolder.date = detailTextView(convertView, R.id.date, date);
		mViewHolder.location = detailTextView(convertView, R.id.location, list.get(position).getLocation());
		mViewHolder.typeColor = detailImageView(convertView, R.id.typeTransaction, list.get(position).getIcon());

//        Typeface roboFont = Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf");
//        mViewHolder.amount.setTypeface(roboFont);
//        mViewHolder.date.setTypeface(roboFont);
//        mViewHolder.location.setTypeface(roboFont);

		return convertView;
	}


	
	private TextView detailTextView(View view, int resId, String desc) {
		TextView textview = (TextView) view.findViewById(resId);
		textview.setText(desc);
		return textview;
	}
	
	private ImageView detailImageView(View view, int resId, String icon) {
		ImageView imageView = (ImageView) view.findViewById(resId);
		imageView.setBackgroundColor(Color.parseColor(icon));
		
		return imageView;
	}

	private class MyViewHolder {
		TextView amount, date, location;
		ImageView typeColor;
	}
}

