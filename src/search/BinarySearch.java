package search;

public class BinarySearch {
    static final boolean USE_ITER = false;

    // pre: (a[i] >= a[i + 1])
    // post: a[R] <= x ∧ a[R - 1] > x
    public static int binSearchIter(int x, int[] a) {
        int l = 0;
        int r = a.length;
        // inv: (0 <= l < r <= a.length) ∧ (a[l] > x) ∧ (a[r] <= x)
        while (r - l > 1) {
            int m = (l + r) / 2;
            if (a[m] > x) {
                l = m;
            } else {
                r = m;
            }
        }
        return r - 1;
    }

    // pre: (a[i] >= a[i + 1]) ∧ (0 <= l < r <= a.length) ∧ (a[l] >= x) ∧ (a[r] < x)
    // post: a[R] <= x ∧ a[R - 1] > x
    public static int binSearchReq(int x, int a[], int l, int r) {
        if (r - l <= 1) {
            return r - 1;
        }
        int m = (l + r) / 2;
        if (a[m] > x) {
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

        if (USE_ITER) {
            System.out.println(binSearchIter(x, a));
        } else {
            System.out.println(binSearchReq(x, a, 0, a.length + 1));
        }
    }
}
