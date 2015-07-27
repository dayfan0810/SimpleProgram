package com.gj.Sort;

import com.gj.control.SortImage;
import com.gj.frame.MyRectangle;

public class MySort extends Algorithm {

	public MySort(SortImage si) {
		super(si);
	}

	@Override
	public void startSort(MyRectangle[] rects) {
		Algorithm.swapTime = Algorithm.compareTime = 0;
		mySort(rects);

	}
	
	public void mySort(MyRectangle[] arr) {
		int low = 0, high = arr.length - 1;
		while(low < high) {
			int mi = low, ma = high;
			for(int i = low; i <= high; i ++) {
				if(compareTo(arr[i], arr[mi]) < 0) mi = i;
				else if(compareTo(arr[i], arr[ma]) > 0) ma = i;
			}
			if(mi > low) {
				swap(arr[low], arr[mi]);
				if(ma == low) ma = mi;
			}
			low ++;
			if(ma < high) {
				swap(arr[high], arr[ma]);
			}
			high --;
		}
	}

}
