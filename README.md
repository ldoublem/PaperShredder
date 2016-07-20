# PaperShredder
a PaperShredder view for android
碎纸机动画，点子出自[dribbble](https://dribbble.com/shots/2125581-Deleting-AE-Freebie)
---
#Preview
![png](https://github.com/ldoublem/PaperShredder/blob/master/screenshot/shot.png)

![gif](https://github.com/ldoublem/PaperShredder/blob/master/screenshot/1.gif)
![gif](https://github.com/ldoublem/PaperShredder/blob/master/screenshot/2.gif)
![gif](https://github.com/ldoublem/PaperShredder/blob/master/screenshot/3.gif)


##Usage  xml
```
 <com.ldoublem.PaperShredderlib.PaperShredderView
        android:layout_width="200dp"
        android:id="@+id/ps_delete2"
        android:layout_height="220dp"
        paper:sherderBgColor="#f4c600"
        paper:sherderText="清理中"
        paper:sherderType="0"
        paper:sherderPaperEnterColor="#56abe4"
        paper:sherderTextShadow="false"
        paper:sherderTextColor="#99101010"
        paper:sherderPaperColor="#dbdbdb"
        paper:sherderProgress="false"
      />
```
##java
```
       mPaperShredderView.setShrededType(PaperShredderView.SHREDEDTYPE.Piece);//纸片效果和纸条效果
       mPaperShredderView.setSherderProgress(false);
       mPaperShredderView.setTitle("清除数据");
       mPaperShredderView.setTextColor(Color.BLACK);
       mPaperShredderView.setPaperColor(Color.BLACK);
       mPaperShredderView.setBgColor(Color.WHITE);
       mPaperShredderView.setTextShadow(false);
       mPaperShredderView.setPaperEnterColor(Color.BLACK);
       mPaperShredderView.startAnim(1000);
       mPaperShredderView.stopAnim();
```
## About me

An android developer in Hangzhou.

If you want to make friends with me, You can email to me.
my [email](mailto:1227102260@qq.com) :smiley:


License
=======

    The MIT License (MIT)

	Copyright (c) 2016 ldoublem

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.



