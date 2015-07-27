package com.gj.Sort;

import com.gj.control.SortImage;
import com.gj.frame.MyRectangle;

public class BubbleSort extends Algorithm {
	
	public BubbleSort(SortImage si) {
		super(si);
	}

	public void startSort(MyRectangle[] rects) {
		Algorithm.swapTime = Algorithm.compareTime = 0;
		for(int i = 0; i < rects.length - 1; i ++) {
			for(int j = rects.length - 1; j > i; j --) {
				if(compareTo(rects[j], rects[j - 1]) < 0) {
					swap(rects[j - 1], rects[j]); 
				}
			}
		}
	}
}
