package net.zarbel.headerarrow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import net.zarbel.headerarrow.R;
import net.zarbel.headerarrowlib.HeaderArrowView;
import net.zarbel.headerarrowlib.listeners.OnPreIconAnimationListener;
import net.zarbel.headerarrowlib.listeners.OnVisibilityChangeListener;

/**
 * Created by julian on 8/8/16.
 */
public class FragmentExample3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_example3, container, false);

        HeaderArrowView headerArrowView = (HeaderArrowView) view.findViewById(R.id.header_animation);
        headerArrowView.setOnPreIconAnimationListener(new OnPreIconAnimationListener() {
            @Override
            public void onPreShow(ImageView headerImageView) {
                headerImageView.animate().scaleX(2).scaleY(2).alpha(0.2f);
            }

            @Override
            public void onPreHide(ImageView headerImageView) {
                headerImageView.animate().scaleX(1).scaleY(1).alpha(1);
            }
        });

        HeaderArrowView headerArrowViewWithListener = (HeaderArrowView) view.findViewById(R.id.header_listener);

        headerArrowViewWithListener.addOnVisibilityChangeListener(new OnVisibilityChangeListener() {
            @Override
            public void onContentShow() {
                Toast.makeText(getContext(), "OPEN", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onContentHide() {
                Toast.makeText(getContext(), "CLOSE", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
