package com.gj.Sort;

import com.gj.control.SortImage;
import com.gj.frame.MyRectangle;

public class HeapSort extends Algorithm{
	
	public HeapSort(SortImage si) {
		super(si);
	}

	public void startSort(MyRectangle[] rects) {
		heapSort(rects);
	}
	
	private void heapAdjust(MyRectangle[] arr, int i, int n) {
		int j = 2 * i + 1;
		MyRectangle tmp = (MyRectangle)arr[i].clone();
		while(j < n) {
			if(j + 1 < n && compareTo(arr[j + 1], arr[j]) > 0) j ++;
			if(compareTo(arr[j], tmp) <= 0) break;
			swap(arr[i], arr[j]);
			i = j; 
			j = i * 2 + 1;
		}
		swap(arr[i], tmp);
	}
	
	public void heapSort(MyRectangle[] rects) {
		for(int i = rects.length / 2 - 1; i >= 0; i --) {
			heapAdjust(rects, i, rects.length);
		}
		for(int i = rects.length - 1; i >= 0; i --) {
			swap(rects[0], rects[i]);
			heapAdjust(rects, 0, i);
		}
	}

}
