package com.e6data.engine;

import java.util.ArrayList;
import java.util.List;

class MergeSortRecordMerger implements RecordMerger {
    @Override
    public List<Record> merge(List<Record> records) {
        return mergeSort(records);
    }

    private List<Record> mergeSort(List<Record> list) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<Record> left = mergeSort(new ArrayList<>(list.subList(0, mid)));
        List<Record> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())));

        return mergeSortedLists(left, right);
    }

    private List<Record> mergeSortedLists(List<Record> left, List<Record> right) {
        List<Record> result = new ArrayList<>();
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


