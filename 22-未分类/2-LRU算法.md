# 题目描述

```text
设计并实现最近最少经⽤（LRU）缓存的数据结构。它应该⽀持以下操作：get 和 put。
  get(key) - 如果键存在于缓存中，则获取键的值（总是正数），否则返回 -1。
  put(key, value) - 如果键不存在，请设置或插⼊值。当缓存达到其容量时，它应该在插⼊新项⽬之前，使最近最少使⽤的项⽬⽆效。
```

# 题目进阶

```text
你是否可以在 O(1) 时间复杂度内执⾏两项操作？
```

# 题目示例

```text
LFUCache cache = new LFUCache( 2 /* capacity (缓存容量) */ );
cache.put(1, 1);
cache.put(2, 2);
cache.get(1); // 返回 1
cache.put(3, 3); // 去除 key 2
cache.get(2); // 返回 -1 (未找到key 2)
cache.get(3); // 返回 3
cache.put(4, 4); // 去除 key 1
cache.get(1); // 返回 -1 (未找到 key 1)
cache.get(3); // 返回 3
cache.get(4); // 返回 4
```

# 题目代码

## 题解一：单链表

```text
我们要删的是最近最少使⽤的节点，⼀种⽐较容易想到的⽅法就是使⽤单链表这种数据结构来存储了。
当我们进⾏ put 操作的时候，会出现以下⼏种情况：
1、如果要 put(key,value) 已经存在于链表之中了（根据key来判断），那么我们需要把链表中久的数据
删除，然后把新的数据插⼊到链表的头部。、
2、如果要 put(key,value) 的数据没有存在于链表之后，我们我们需要判断下缓存区是否已满，如果满
的话，则把链表尾部的节点删除，之后把新的数据插⼊到链表头部。如果没有满的话，直接把数据插⼊
链表头部即可。
对于 get 操作，则会出现以下情况
1、如果要 get(key) 的数据存在于链表中，则把 value 返回，并且把该节点删除，删除之后把它插⼊到
链表的头部。
2、如果要 get(key) 的数据不存在于链表之后，则直接返回 -1 即可。
⼤概的思路就是这样，不要觉得很简单，让你⼿写的话，⼗分钟你不⼀定⼿写的出来。具体的代码，为
了不影响阅读，我在⽂章的最后⾯在放出来。
时间、空间复杂度分析
对于这种⽅法，put 和 get 都需要遍历链表查找数据是否存在，所以时间复杂度为 O(n)。空间复杂度为
O(1)。

// 定义链表节点
class LRUNode{
 String key;
 Object value;
 LRUNode next;
 public LRUNode(String key, Object value) {
 this.key = key;
 this.value = value;
 }
}
// 刚开始把名字写错了，把 LRU写成了RLU
public class LRUCache {
 LRUNode head;
 int size = 0;// 当前⼤⼩
 int capacity = 0; // 最⼤容量
 public LRUCache(int capacity) {
 this.capacity = capacity;
 }
 public Object get(String key) {
 LRUNode cur = head;
 LRUNode pre = head;// 指向要删除节点的前驱
 // 找到对应的节点，并把对应的节点放在链表头部
 // 先考虑特殊情况
 if(head == null)
 return null;
 if(cur.key.equals(key))
 return cur.value;
 // 进⾏查找
 cur = cur.next;
 while (cur != null) {
 if (cur.key.equals(key)) {
 break;
 }
 pre = cur;
 cur = cur.next;
 }
 // 代表没找到了节点
 if (cur == null)
 return null;
 // 进⾏删除
 pre.next = cur.next;
 // 删除之后插⼊头结点
 cur.next = head;
 head = cur;
 return cur.value;
 }
 public void put(String key, Object value) {
 // 如果最⼤容量是 1，那就没办法了，，，，，
 if (capacity == 1) {
 head = new RLUNode(key, value);
 }
 LRUNode cur = head;
 LRUNode pre = head;
 // 先查看链表是否为空
 if (head == null) {
 head = new RLUNode(key, value);
 return;
 }
 // 先查看该节点是否存在
 // 第⼀个节点⽐较特殊，先进⾏判断
 if (head.key.equals(key)) {
 head.value = value;
 return;
 }
 cur = cur.next;
 while (cur != null) {
 if (cur.key.equals(key)) {
 break;
 }
 pre = cur;
 cur = cur.next;
 }
 // 代表要插⼊的节点的 key 已存在，则进⾏ value 的更新
 // 以及把它放到第⼀个节点去
 if (cur != null) {
 cur.value = value;
 pre.next = cur.next;
 cur.next = head;
 head = cur;
 } else {
 // 先创建⼀个节点
 LRUNode tmp = new LRUNode(key, value);
 // 该节点不存在，需要判断插⼊后会不会溢出
 if (size >= capacity) {
 // 直接把最后⼀个节点移除
 cur = head;
 while (cur.next != null && cur.next.next != null) {
 cur = cur.next;
 }
 cur.next = null;
 tmp.next = head;
 head = tmp;
 }
 }
 }
}

```

## 题解二：双向链表+哈希表

```text
// 链表节点的定义
class LRUNode{
 String key;
 Object value;
 LRUNode next;
 LRUNode pre;
 public LRUNode(String key, Object value) {
 this.key = key;
 this.value = value;
 }
}

// LRU
public class LRUCache {
 Map<String, LRUNode> map = new HashMap<>();
 LRUNode head;
 LRUNode tail;
 // 缓存最⼤容量，我们假设最⼤容量⼤于 1，
 // 当然，⼩于等于1的话需要多加⼀些判断另⾏处理
 int capacity;
 public LRUCache(int capacity) {
 this.capacity = capacity;
 }
 public void put(String key, Object value) {
 if (head == null) {
 head = new LRUNode(key, value);
 tail = head;
 map.put(key, head);
 }
 LRUNode node = map.get(key);
 if (node != null) {
 // 更新值
 node.value = value;
 // 把他从链表删除并且插⼊到头结点
 removeAndInsert(node);
 } else {
 LRUNode tmp = new LRUNode(key, value);
 // 如果会溢出
 if (map.size() >= capacity) {
 // 先把它从哈希表中删除
 map.remove(tail.key);
 // 删除尾部节点
 tail = tail.pre;
 tail.next = null;
 }
 map.put(key, tmp);
 // 插⼊
 tmp.next = head;
 head.pre = tmp;
 head = tmp;
 }
 }
 public Object get(String key) {
 LRUNode node = map.get(key);
 if (node != null) {
 // 把这个节点删除并插⼊到头结点
 removeAndInsert(node);
 return node.value;
 }
 return null;
 }
 private void removeAndInsert(LRUNode node) {
 // 特殊情况先判断，例如该节点是头结点或是尾部节点
 if (node == head) {
 return;
 } else if (node == tail) {
 tail = node.pre;
 tail.next = null;
 } else {
 node.pre.next = node.next;
 node.next.pre = node.pre;
 }
 // 插⼊到头结点
 node.next = head;
 node.pre = null;
 head.pre = node;
 head = node;
 }
}

```