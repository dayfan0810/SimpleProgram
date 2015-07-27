public class Solution {
	/**
	 *	将路径数组变为统计数组
	 *	输入：代表一张图的数组paths
	 *	将paths数组变为距离数组numArr
	 */
	public void pathArrToNumArr(int[] paths) {
		getDisArray(paths);
		for(int i = 0; i < paths.length; i ++) {
			getCount(paths, i);
		}
		paths[0] = 1;
		for(int i = 0; i < paths.length; i ++) {
			System.out.println(paths[i]);
		}
	}
	
	private void getDisArray(int[] paths) {
		int origin = dp(paths, 0, -1, -1);
		for(int i = 1; i < paths.length; i ++) if(paths[i] >= 0){
			origin = dp(paths, i, origin, -1);
		}
	}
	
	private void getCount(int[] paths, int n) {
		if(paths[n] < 0) {
			int tmp = -paths[n];
			paths[n] = 0;
			getCount(paths, tmp);
			paths[tmp]++;
		}
	}
	
	private int dp(int[] paths, int u, int origin, int op) {
		if(origin == u || u == paths[u]) {
			paths[u] = 0;
			return origin = u;
		}
		if(paths[u] < 0) return origin;
		origin = dp(paths, paths[u], origin, op);
		paths[u] = paths[paths[u]] + op;
		return origin;
	}
	
	public static void main(String[] args) {
		Solution s = new Solution();
		int[] paths = {9,1,4,9,0,4,8,9,0,1};
		s.pathArrToNumArr(paths);
	}
	
}
