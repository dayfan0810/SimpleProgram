package com.gj.test;

import com.gj.frame.MainFrame;
import com.gj.frame.MyRectangle;

public class TestSort {
	
	public static final MyRectangle r = new MyRectangle(0, 1, 2, 3); 
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.registSort("com.gj.Sort.BubbleSort");
		frame.registSort("com.gj.Sort.HeapSort");
		frame.registSort("com.gj.Sort.InsertionSort");
		frame.registSort("com.gj.Sort.MergeSort");
		frame.registSort("com.gj.Sort.MonkeySort");
		frame.registSort("com.gj.Sort.MySort");
		frame.registSort("com.gj.Sort.QuickSort");
		frame.registSort("com.gj.Sort.SelectionSort");
		frame.registSort("com.gj.Sort.ShellSort");
		frame.run();
	}
}
