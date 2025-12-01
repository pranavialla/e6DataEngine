package com.e6data.engine.recordSorter;

import java.util.ArrayList;
import java.util.List;

import com.e6data.engine.StudentRecord;

/**
 * Sorts records
 * OCP: Can add different sorting strategies (QuickSort, HeapSort, etc.)
 */


public class MergeSortRecordSorter implements RecordSorter {
    @Override
    public List<StudentRecord> sort(List<StudentRecord> records) {
        return mergeSort(records);
    }

    private List<StudentRecord> mergeSort(List<StudentRecord> list) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<StudentRecord> left = mergeSort(new ArrayList<>(list.subList(0, mid)));
        List<StudentRecord> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())));

        return merge(left, right);
    }

    private List<StudentRecord> merge(List<StudentRecord> left, List<StudentRecord> right) {
        List<StudentRecord> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareTo(right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }
}

