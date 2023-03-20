package github.javaxbx.xbx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @Author xiaobiaoxu
 * @Date 2023年03月01日 16:00
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(System.out);
    static long resTemp = Long.MAX_VALUE;
    static Node[] nodes = new Node[10];
    static class Node{
        long res = Long.MAX_VALUE;
        int index = 0;
        int left = 0;
        int leftRemain = 0;
        int right = 0;
        int rightRemain = 0;

        public Node() {
        }

        public Node(long res, int index, int left, int leftRemain, int right, int rightRemain) {
            this.res = res;
            this.index = index;
            this.left = left;
            this.leftRemain = leftRemain;
            this.right = right;
            this.rightRemain = rightRemain;
        }
    }
    public static void main(String[] args) throws Exception{
        StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
        int n = Integer.valueOf(stringTokenizer.nextToken());
        int k = Integer.valueOf(stringTokenizer.nextToken());
        String s = br.readLine();
        int[] ints = new int[10];
        char[] chars = s.toCharArray();
        for (char c : chars) {
            ints[c-'0']++;
        }
        for (int i = 0; i < 10; i++) {
            back(ints, i, k);
        }
        out.println(resTemp);
        String string = null;
        for (int l = 0; l < 10; l++) {
            if (nodes[l].res == resTemp) {
                Node node = nodes[l];
                chars = s.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    int p = chars[i] - '0';
                    if (p == node.left) {
                        if (ints[p] == node.leftRemain && node.leftRemain > 0) {
                            chars[i] = (char) (node.index + '0');
                            node.leftRemain--;
                        }
                        ints[p]--;
                    } else if (p == node.right) {
                        if (node.rightRemain > 0) {
                            chars[i] = (char) (node.index + '0');
                            node.rightRemain--;
                        }
                    } else if (p > node.left && p < node.right) {
                        chars[i] = (char) (node.index + '0');
                    }
                }
                String valueOf = String.valueOf(chars);
                if (string == null || valueOf.compareTo(string) < 0){
                    string = valueOf;
                }
            }
        }
        out.println(string);
        out.close();
    }
    private static void back(int[] ints, int index, int k){
        int left = index-1;
        int leftRemain = 0;
        int right = index+1;
        int rightRemain = 0;
        long temp = 0;
        k -= ints[index];
        while (k > 0){
            if (right < ints.length){
                if (ints[right] >= k){
                    temp += (right-index)*k;
                    rightRemain = k;
                    k = 0;
                }else {
                    temp += (right-index)*ints[right];
                    rightRemain = ints[right];
                    k -= ints[right];
                }
                right++;
            }
            if (left >= 0){
                if (ints[left] >= k){
                    temp += (index-left)*k;
                    leftRemain = k;
                    k = 0;
                }else {
                    temp += (index-left)*ints[left];
                    leftRemain = ints[left];
                    k -= ints[left];
                }
                left--;
            }
        }
        nodes[index] = new Node(temp, index, left+1, leftRemain, right-1, rightRemain);
        if (temp < resTemp){
            resTemp = temp;
        }
    }
}
