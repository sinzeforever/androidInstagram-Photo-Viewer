package com.yahoo.instagramphotoviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by sinze on 7/24/15.
 */
public class InstagramItemAdapter extends ArrayAdapter<InstagramItem> {
    public InstagramItemAdapter(Context context, List<InstagramItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.instagram_card, parent, false);
        }

        // Todo: use view holder to hold the view instead of finding it every times
        TextView tvText = (TextView) convertView.findViewById(R.id.item_text);
        String formattedText = "<b>" + item.user + "</b>    " + item.caption;
        tvText.setText(Html.fromHtml(formattedText));


        // set comment
        TextView tvCommentNumber = (TextView) convertView.findViewById(R.id.item_comment_number);
        if (item.commentList != null) {
            tvCommentNumber.setText("view all " + item.commentNumber + " comments");
        }

        TextView tvCommentUser1 = (TextView) convertView.findViewById(R.id.comment_user1);
        TextView tvCommentUser2 = (TextView) convertView.findViewById(R.id.comment_user2);
        TextView tvCommentText1 = (TextView) convertView.findViewById(R.id.comment_text1);
        TextView tvCommentText2 = (TextView) convertView.findViewById(R.id.comment_text2);

        if (item.commentNumber > 0) {
            tvCommentUser1.setText(item.commentList.get(0).user);
            tvCommentText1.setText(item.commentList.get(0).text);
        } else {
            tvCommentUser1.setVisibility(View.GONE);
            tvCommentText1.setVisibility(View.GONE);
        }
        if (item.commentNumber > 1) {
            tvCommentUser2.setText(item.commentList.get(1).user);
            tvCommentText2.setText(item.commentList.get(1).text);
        } else {
            tvCommentUser2.setVisibility(View.GONE);
            tvCommentText2.setVisibility(View.GONE);
        }


        // image
        ImageView ivImg = (ImageView) convertView.findViewById(R.id.item_img);
        // clear old image
        ivImg.setImageResource(R.drawable.loading);
        // insert the image using picasso
        Picasso.with(getContext())
            .load(item.imageUrl)
            .placeholder(R.drawable.loading)
            .into(ivImg);

        // user profile
        ImageView ivUserProfile = (ImageView) convertView.findViewById(R.id.user_icon);
        ivImg.setImageResource(0);
        Transformation transformation = new RoundedTransformationBuilder()
                .borderWidthDp(1)
                .borderColor(Color.parseColor("#f0f0f0"))
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(getContext())
                .load(item.userProfile)
                .fit()
                .transform(transformation)
                .into(ivUserProfile);

        // header user
        TextView tvHdUser = (TextView) convertView.findViewById(R.id.hdUser);
        tvHdUser.setText(item.user);

        // post time
        TextView tvPostTime = (TextView) convertView.findViewById(R.id.postTime);
        tvPostTime.setText(item.getPostTimeText());
        return convertView;
    }
}
