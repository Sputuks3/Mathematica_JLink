/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import java.util.Enumeration;
import java.util.Hashtable;

class Bucket
extends Hashtable {
    private int nextKey = 1;
    private static final int largestKey = 16777215;

    Bucket() {
        super(7, 1.0f);
    }

    Integer put(Object obj) {
        Integer withinBucketKey = new Integer(this.nextKey++);
        if (this.nextKey > 16777215) {
            this.nextKey = 1;
        }
        this.put(withinBucketKey, new BucketRec(obj, withinBucketKey));
        return withinBucketKey;
    }

    public Object get(Object key) {
        BucketRec r = (BucketRec)super.get(key);
        return r != null ? r.obj : null;
    }

    Integer keyOf(Object obj) {
        Enumeration elts = this.elements();
        while (elts.hasMoreElements()) {
            BucketRec d = (BucketRec)elts.nextElement();
            if (d.obj != obj) continue;
            return d.key;
        }
        return null;
    }

    class BucketRec {
        Object obj;
        Integer key;

        BucketRec(Object obj, Integer key) {
            this.obj = obj;
            this.key = key;
        }
    }

}

