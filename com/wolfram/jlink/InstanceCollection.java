/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Bucket;
import com.wolfram.jlink.Expr;
import java.util.Enumeration;
import java.util.Hashtable;

class InstanceCollection {
    private Hashtable table = new Hashtable(541);

    InstanceCollection() {
    }

    synchronized long keyOf(Object obj) {
        int posHash;
        Bucket b;
        int hash = InstanceCollection.getHashCode(obj);
        if (hash == Integer.MIN_VALUE) {
            ++hash;
        }
        if ((b = (Bucket)this.table.get(new Integer(posHash = Math.abs(hash)))) == null) {
            return 0;
        }
        Integer bucketKey = b.keyOf(obj);
        return bucketKey == null ? 0 : (long)posHash << 24 | (long)bucketKey.intValue();
    }

    synchronized Object get(long key) {
        if (key == 0) {
            return null;
        }
        int keyInt = (int)(key >> 24);
        Bucket b = (Bucket)this.table.get(new Integer(keyInt));
        Integer bucketKey = new Integer((int)(key & 0xFFFFFF));
        return b.get(bucketKey);
    }

    synchronized long put(Object obj) {
        Integer keyInt;
        int posHash;
        Bucket b;
        int hash = InstanceCollection.getHashCode(obj);
        if (hash == Integer.MIN_VALUE) {
            ++hash;
        }
        if ((b = (Bucket)this.table.get(keyInt = new Integer(posHash = Math.abs(hash)))) == null) {
            b = new Bucket();
            this.table.put(keyInt, b);
        }
        Integer bucketKey = b.put(obj);
        return (long)posHash << 24 | (long)bucketKey.intValue();
    }

    synchronized void remove(long key) {
        Integer outerKey = new Integer((int)(key >> 24));
        Bucket b = (Bucket)this.table.get(outerKey);
        if (b != null) {
            if (b.size() == 1) {
                this.table.remove(outerKey);
            } else {
                Integer bucketKey = new Integer((int)(key & 0xFFFFFF));
                b.remove(bucketKey);
            }
        }
    }

    synchronized int size() {
        Enumeration iter = this.keys();
        int count = 0;
        while (iter.hasMoreElements()) {
            ++count;
            iter.nextElement();
        }
        return count;
    }

    synchronized Enumeration keys() {
        return new InstanceCollectionKeyEnumerator(this.table);
    }

    private static int getHashCode(Object obj) {
        return obj instanceof Expr ? ((Expr)obj).inheritedHashCode() : obj.hashCode();
    }

    class InstanceCollectionKeyEnumerator
    implements Enumeration {
        Hashtable table;
        Enumeration hashKeys;
        Integer currHashKey;
        Enumeration keysInCurrentBucket;

        InstanceCollectionKeyEnumerator(Hashtable collectionTable) {
            this.table = collectionTable;
            this.hashKeys = collectionTable.keys();
            this.currHashKey = null;
            this.keysInCurrentBucket = null;
        }

        public boolean hasMoreElements() {
            return this.keysInCurrentBucket != null && this.keysInCurrentBucket.hasMoreElements() || this.hashKeys.hasMoreElements();
        }

        public Object nextElement() {
            if (this.keysInCurrentBucket == null || !this.keysInCurrentBucket.hasMoreElements()) {
                if (!this.hashKeys.hasMoreElements()) {
                    return null;
                }
                this.currHashKey = (Integer)this.hashKeys.nextElement();
                this.keysInCurrentBucket = ((Bucket)this.table.get(this.currHashKey)).keys();
            }
            Integer withinBucketKey = (Integer)this.keysInCurrentBucket.nextElement();
            return new Long(this.currHashKey.longValue() << 24 | (long)withinBucketKey.intValue());
        }
    }

}

