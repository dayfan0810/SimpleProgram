package com.gj.Sort;

import com.gj.control.SortImage;
import com.gj.frame.MyRectangle;

public class SelectionSort extends Algorithm {
	public SelectionSort(SortImage si) {
		super(si);
	}

	public void startSort(MyRectangle[] rects) {
		Algorithm.swapTime = Algorithm.compareTime = 0;
		selectionSort(rects);
	}
	
	public void selectionSort(MyRectangle[] arr) {
		for(int i = 0; i < arr.length; i ++) {
			int k = i;
			for (int j = i + 1; j < arr.length; j++) {
				if(compareTo(arr[k], arr[j]) > 0) k = j;
			}
			if(k != i) swap(arr[i], arr[k]);
		}
	}
}
