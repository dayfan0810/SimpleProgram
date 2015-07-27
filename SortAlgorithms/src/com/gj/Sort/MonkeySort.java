package com.gj.Sort;

import java.util.Random;

import com.gj.control.SortImage;
import com.gj.frame.MyRectangle;

public class MonkeySort extends Algorithm{

	public MonkeySort(SortImage si) {
		super(si);
	}

	public void startSort(MyRectangle[] rects) {
		Algorithm.swapTime = Algorithm.compareTime = 0;
		monkeySort(rects);
	}
	
	public void monkeySort(MyRectangle[] arr) {
		int cnt = 100000;
		Random r = new Random();
		while(cnt-- >= 0) {
			int a = r.nextInt(arr.length), b = r.nextInt(arr.length);
			if(compareTo(arr[a], arr[b]) > 0) {
				swap(arr[a], arr[b]);
			}
		}
	}

}
