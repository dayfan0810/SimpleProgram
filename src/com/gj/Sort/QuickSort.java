package com.gj.Sort;

import com.gj.control.SortImage;
import com.gj.frame.MyRectangle;

public class QuickSort extends Algorithm {
	public QuickSort(SortImage si) {
		super(si);
	}

	@Override
	public void startSort(MyRectangle[] rects) {
		quickSort(rects, 0, rects.length - 1);
		
	}
	
	public void quickSort(MyRectangle[] rects, int low, int high) {
		if(low >= high) return ;
		int x = low, y = high, mid = (low + high) / 2;
		swap(rects[low], rects[mid]);
		MyRectangle rec = (MyRectangle)rects[low].clone();
		while(x < y) {
			if(x < y && compareTo(rec, rects[y]) <= 0) y--;
			swap(rects[x], rects[y]);
			if(x < y && compareTo(rec, rects[x]) >= 0) x++;
			swap(rects[x], rects[y]);
		}
		swap(rects[x], rec);
		quickSort(rects, low, x - 1);
		quickSort(rects, x + 1, high);
	}
	
}
