package com.ylcoder.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;




/**
 * ===================================================题1解法1===========================================================
 * 题目链接：https://leetcode-cn.com/problems/two-sum/
 * 思路：双层for循环暴力求解
 */
//public class LeetCode {
//
//    public static void main(String[] args) {
//        int[] nums = new int[1000];
//        int target=0,index=0;
//        Scanner sc = new Scanner(System.in);
//        if (sc.hasNextLine()) {
//            String tmp = sc.nextLine();
//            String[] tmpS = tmp.split(" ");
//            for (String s : tmpS) {
//                int ss = Integer.parseInt(s);
//                nums[index++] = ss;
//            }
//        }
//        System.out.println(Arrays.toString(nums));
//        Scanner scan = new Scanner(System.in);
//        if(scan.hasNextLine()) {
//            target = scan.nextInt();
//            System.out.println(target);
//        }
//        Solution solution = new Solution();
//        System.out.println(Arrays.toString(solution.twoSum(nums, target)));
//
//    }
//
//}
//
//class Solution {
//    public int[] twoSum(int[] nums, int target) {
//        int[] ans = new int[2];
//        for(int i=0; i<nums.length; i++) {
//            for (int j=i+1; j<nums.length; j++) {
//                if (nums[i] + nums[j] == target) {
//                    ans[0] = i;
//                    ans[1] = j;
//                    break;
//                }
//            }
//        }
//        return ans;
//    }
//}


/**
 * ===================================================题1解法2===========================================================
 * 题目链接：https://leetcode-cn.com/problems/two-sum/
 * 思路：双层for循环暴力求解
 */
public class LeetCode {

    public static void main(String[] args) {
        int[] nums = new int[1000];
        int target=0,index=0;
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
            String tmp = sc.nextLine();
            String[] tmpS = tmp.split(" ");
            for (String s : tmpS) {
                int ss = Integer.parseInt(s);
                nums[index++] = ss;
            }
        }
        System.out.println(Arrays.toString(nums));
        Scanner scan = new Scanner(System.in);
        if(scan.hasNextLine()) {
            target = scan.nextInt();
            System.out.println(target);
        }
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.twoSum(nums, target)));

    }

}

class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> maps = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if (maps.containsKey(target - nums[i])) {
                return new int[]{maps.get(target-nums[i]), i};
            }
            maps.put(nums[i], i);
        }
        return new int[0];
    }
}