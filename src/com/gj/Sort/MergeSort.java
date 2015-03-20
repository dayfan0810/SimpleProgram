package com.gj.Sort;

import com.gj.control.SortImage;
import com.gj.frame.MyRectangle;

public class MergeSort extends Algorithm {
	public MergeSort(SortImage si) {
		super(si);
	}

	@Override
	public void startSort(MyRectangle[] rects) {
		mergeSort(rects, new MyRectangle[rects.length], 0, rects.length);
		
	}
	public void mergeSort(MyRectangle[] rects, MyRectangle[] tmp, int low, int high) {//[low, high) 
		if(high - low <= 1) return ;
		int mid = (low + high) >> 1;
		mergeSort(rects, tmp, low, mid);
		mergeSort(rects, tmp, mid, high);
		int x = low, y = mid, p = x;
		for(int i = low; i < high; i ++) {
			tmp[i] = (MyRectangle)rects[i].clone();
		}
		while(x < mid || y < high) {
			if ( y == high || (x < mid && compareTo(rects[x], rects[y]) < 0) )
				swap(tmp[p++], rects[x++]);
			else
				swap(tmp[p++], rects[y++]);
		}
		for(int i = low; i < high; i ++ ) {
			swap(rects[i], tmp[i]);
		}
	}
}
