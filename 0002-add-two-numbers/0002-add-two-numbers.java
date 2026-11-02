/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int x) { val = x; }
 *     ListNode(int x, ListNode next) { this.next = next; this.val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Create a dummy head to easily anchor and build the result list
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;

        // Continue looping if there are nodes left in l1 OR l2, OR if a carry remains
        while (l1 != null || l2 != null || carry > 0) {
            int sum = carry; // Start with the carry from the previous column

            // Add value from l1 if it exists, then move pointer forward
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            // Add value from l2 if it exists, then move pointer forward
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            // Calculate the new carry and the digit value to store
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            
            // Advance the result list pointer
            curr = curr.next;
        }

        // Return the actual head of the result list (skipping the dummy node)
        return dummy.next;
    }
}
