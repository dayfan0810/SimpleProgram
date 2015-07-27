package com.gj.Sort;

import com.gj.control.SortImage;
import com.gj.frame.MyRectangle;

public class InsertionSort extends Algorithm{
	
	public InsertionSort(SortImage si) {
		super(si);
	}

	public void startSort(MyRectangle[] rects) {
		Algorithm.swapTime = Algorithm.compareTime = 0;
		insertionSort(rects);
		
	}
	
	public void insertionSort(MyRectangle[] arr) {
		for(int i = 1; i < arr.length; i ++) {
			int k = i - 1;
			while(k >= 0 && compareTo(arr[k], arr[i]) > 0) k --;
			k ++;
			while(k < i) {
				swap(arr[k], arr[i]);
				k ++;
			}
		}
	}
}
