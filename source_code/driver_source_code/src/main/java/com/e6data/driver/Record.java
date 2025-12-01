package com.e6data.driver;

/**
 * Record class - Data transfer object
 * Follows SRP
 */
public class Record implements Comparable<Record> {
    public final String studentId;
    final int batchYear;
    final int universityRanking;
    final int batchRanking;

    public Record(String studentId, int batchYear, int universityRanking, int batchRanking) {
        this.studentId = studentId;
        this.batchYear = batchYear;
        this.universityRanking = universityRanking;
        this.batchRanking = batchRanking;
    }

    @Override
    public int compareTo(Record other) {
        int yearComp = Integer.compare(this.batchYear, other.batchYear);
        if (yearComp != 0) return yearComp;

        int uniComp = Integer.compare(this.universityRanking, other.universityRanking);
        if (uniComp != 0) return uniComp;

        return Integer.compare(this.batchRanking, other.batchRanking);
    }
}