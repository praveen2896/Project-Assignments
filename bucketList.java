package Basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class bucketList {
	
	public static void main(String[] args) {
		Scanner s= new Scanner(System.in);
		System.out.println("Enter the values of N");		
		int N=s.nextInt();
		System.out.println("Enter the values of M");
		int M=s.nextInt();					
		if(N%M==0)
		{
			int[] A=new int[N];
			System.out.println("Enter the elements of A");
			for(int i=0;i<N;i++)
			{
				A[i] = s.nextInt();
			}
			int[] output=getBucket(N,M,A);
			for (Integer x : output) 
	            System.out.print(x + ",");
		}
		else {
			System.out.println("Invalid input ("+N +" is not divisible by "+M+")");
		}
		s.close();
	}

	private static int[] getBucket(int n, int m, int[] a) {
		List<Integer> finalList = new ArrayList<>();
		Arrays.sort(a);
		int start=0;
		int end=m;		
		for (int i = 0; i <n/m; i++) {
			//System.out.println("------------"+start+" "+end);
			List<Integer> tempList= new ArrayList<>();
			for (int j = start; j <end; j++) {				
				tempList.add(a[j]);							
			}
			//System.out.println(tempList);
			start=start+m;
			end=end+m;
			Collections.sort(tempList,Collections.reverseOrder());			
			finalList.addAll(tempList);
		}
		//System.out.println(finalList);
		int[] arr = new int[finalList.size()]; 
		// ArrayList to Array Conversion 
        for (int i = 0; i < finalList.size(); i++) 
            arr[i] = finalList.get(i); 
		return arr;
	}

}
