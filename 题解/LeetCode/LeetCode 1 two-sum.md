
### 题目链接
https://leetcode-cn.com/problems/two-sum/

### 题目描述

```
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。

You may assume that each input would have exactly one solution, and you may not use the same element twice.
你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。

You can return the answer in any order.
你可以按任意顺序返回答案。
```

### 示例

示例 1：
```
输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
```

示例 2：
```
输入：nums = [3,2,4], target = 6
输出：[1,2]
```

示例 3：

```
输入：nums = [3,3], target = 6
输出：[0,1]
```
 

### 提示：

```
2 <= nums.length <= 104
-109 <= nums[i] <= 109
-109 <= target <= 109
只会存在一个有效答案
```

### 其他
```
进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？
```

### 题解

#### 题解一：暴力枚举

```
//双层for循环暴力枚举求解。

class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] ans = new int[2];
        for(int i=0; i<nums.length; i++) {
            for (int j=i+1; j<nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    ans[0] = i;
                    ans[1] = j;
                    break;
                }
            }
        }
        return ans;
    }
}
```

#### 题解二：哈希表

```
//思路：枚举数组中的每一个数 x，寻找数组中是否存在 target - x，这里可以使用哈希，Map<Integer, Integer>值和下标
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
```

