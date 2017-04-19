package com.joyful.joyfulkitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.joyful.joyfulkitchen.R;
import com.joyful.joyfulkitchen.model.SearchMeauList;
import com.joyful.joyfulkitchen.util.BitmapCache;

import java.util.List;

import static com.joyful.joyfulkitchen.R.id.albums;


/**
 * 根据 name lai
 */
public class SearchListAdapter extends BaseAdapter {
	private List<SearchMeauList> data;
	private Context context;
	private LayoutInflater inflater;

	private ImageLoader mImageLoader;


	public SearchListAdapter(Context context, List<SearchMeauList> data) {
		this.context = context;
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		RequestQueue queue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(queue, new BitmapCache());
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	static class ViewHolder{

		public NetworkImageView albums; // 菜图片

		private TextView title,tags; // 菜名 和 tags
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder item;
		if(convertView == null){
			item = new ViewHolder();
			convertView = inflater.inflate(R.layout.search_list_item, null);
			item.albums = (NetworkImageView) convertView.findViewById(albums);
			item.title = (TextView) convertView.findViewById(R.id.title);
			item.tags = (TextView) convertView.findViewById(R.id.tags);

			convertView.setTag(item);
		}else{
			item = (ViewHolder) convertView.getTag();
		}

		item.title.setText(data.get(position).getTitle());

		item.tags.setText(data.get(position).getTags().replaceAll(";","、"));

		// 菜图片处理.....
		String imgUrl = data.get(position).getAlbums().get(0);
		item.albums.setDefaultImageResId(R.mipmap.ic_loading_large);
		item.albums.setErrorImageResId(R.mipmap.ic_loading_large);
		item.albums.setImageUrl(imgUrl, mImageLoader);
		return convertView;
	}


}
