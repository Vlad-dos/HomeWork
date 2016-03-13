public class BinarySearchSpan {
    // pre: (a[i] >= a[i + 1])
    // post: (a[R] <= x || R == a.length) && a[R - 1] > x
    public static int binSearchIter(int x, int[] a) {
        int l = 0;
        int r = a.length;
        // inv: (0 <= l < r <= a.length) && (a[l] > x || l == 0) && (a[r] <= x || r == a.length)
        while (r - l > 1) {
            int m = (l + r) / 2;
            if (a[m] > x) {
                l = m;
            } else {
                r = m;
            }
        }
        return r;
    }

    // pre: (a[i] >= a[i + 1]) && (0 <= l < r <= a.length) && (a[l] >= x || l == 0) && (a[r] < x || r == a.length)
    // post: (a[L] >= x || L == 0) && a[L + 1] < x
    public static int binSearchReq(int x, int a[], int l, int r) {
        if (r - l <= 1) {
            return l;
        }
        int m = (l + r) / 2;
        if (a[m] >= x) {
            l = m;
        } else {
            r = m;
        }
        return binSearchReq(x, a, l, r);
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a;
        a = new int[args.length];
        for (int i = 1; i < args.length; i++) {
            a[i] = Integer.parseInt(args[i]);
        }

        int pos = binSearchIter(x, a) - 1;
        System.out.print(pos + " ");
        System.out.print(binSearchReq(x, a, 0, a.length) - pos);
    }
}
