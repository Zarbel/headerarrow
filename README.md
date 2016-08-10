# HeaderArrowView #

HeaderArrowView is a customizable library that allows to use a header that hides or shows his related content. 

![](https://bytebucket.org/juzabel/headerarrowlib/raw/4328f9f407c91a427d51ecf6dd235fce5fe2efcc/images/GIFLibrary.gif?token=842ab6f141191306f23da84c344a809c85261467)


### Usage ###

* Simply add this view on a layout, and set the content layout that will be shown or hidden with his title.

```xml
 <net.zarbel.headerarrowlib.HeaderArrowView
	        android:id="@+id/header"
	        hav:content_layout="@layout/layout_test"
	        hav:header_title="@string/app_name"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"/>
```

### Advanced usage ###

* Custom colors and icon:
```xml
   <net.zarbel.headerarrowlib.HeaderArrowView
        hav:content_layout="@layout/layout_test"
        android:id="@+id/headerbbb"
        hav:header_icon_position="left"
        hav:header_icon="@android:drawable/ic_btn_speak_now"
        hav:header_icon_color="#000"
        hav:title_color="#000"
        hav:content_background_tint_color="#ff6464"
        hav:header_background_tint_color="#91ff91"
        hav:header_title="Test"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
* Custom drawable backgrounds for header and/or footer
```xml
<net.zarbel.headerarrowlib.HeaderArrowView
        hav:content_layout="@layout/layout_test"
        android:id="@+id/header1"
        hav:header_icon_position="left"
        hav:header_icon="@android:drawable/ic_btn_speak_now"
        hav:header_icon_color="#fff"
        hav:title_color="#fff"
        hav:content_background_resource="@drawable/bg_res"
        hav:header_background_resource="@drawable/bg_res"
        hav:header_title="Test"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
* Custom drawable backgrounds and custom tint color
```xml
<net.zarbel.headerarrowlib.HeaderArrowView
        hav:content_layout="@layout/layout_test"
        android:id="@+id/header2"
        hav:header_icon_position="left"
        hav:header_icon="@android:drawable/ic_btn_speak_now"
        hav:header_icon_color="#000"
        hav:title_color="#000"
        hav:content_background_resource="@drawable/bg_res"
        hav:header_background_resource="@drawable/bg_res"
        hav:content_background_tint_color="#555"
        hav:header_background_tint_color="#ffc1c1"
        hav:header_title="Test"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
* With custom icon animation
```java
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
```
* With listener
```java
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
```

### Contact ###

* zarbel-feedback@outlook.com

### License ###

Copyright (C) 2016 Julián Zaragoza

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.